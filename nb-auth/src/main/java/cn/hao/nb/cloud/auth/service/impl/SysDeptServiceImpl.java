package cn.hao.nb.cloud.auth.service.impl;

import cn.hao.nb.cloud.auth.entity.SysDept;
import cn.hao.nb.cloud.auth.entity.UUserDept;
import cn.hao.nb.cloud.auth.mapper.SysDeptMapper;
import cn.hao.nb.cloud.auth.service.ISysDeptService;
import cn.hao.nb.cloud.auth.service.IUUserDeptService;
import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Qw;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.util.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 组织机构 服务实现类
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    @Autowired
    IDUtil idUtil;
    @Autowired
    SysDeptMapper mapper;
    @Autowired
    IUUserDeptService userDeptService;
    @Autowired
    RedisUtil redisUtil;

    private String REDIS_ALL_DIS_DEPT_BY_PID_KEY = "REDIS_ALL_DIS_DEPT_BY_PID_KEY";
    private long REDIS_ALL_DIS_DEPT_BY_PID_EXPIRE_TIME = 3600;

    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    @Override
    public SysDept addData(SysDept data) {
        this.validData(data);
        data.setDeptId(idUtil.nextId());
        data.setCreateBy(UserUtil.getTokenUser(true).getUserId());
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        this.save(data);
        return data;
    }

    @Override
    public SysDept addData(String deptName, String pId) {
        SysDept dept = new SysDept();
        dept.setDeptName(deptName);
        dept.setPId(pId);
        return this.addData(dept);
    }

    @Override
    public SysDept addData(String deptName) {
        return this.addData(deptName, null);
    }

    /**
     * 增量更新数据
     *
     * @param data
     * @return
     */
    @Override
    public boolean incrementModifyData(SysDept data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getDeptId()
        ))
            throw NBException.create(EErrorCode.missingArg);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        return this.updateById(data);
    }

    /**
     * 全量更新数据
     *
     * @param data
     * @return
     */
    @Override
    public boolean totalAmountModifyData(SysDept data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getDeptId()
        ))
            throw NBException.create(EErrorCode.missingArg);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        return this.update(data, Wrappers.<SysDept>lambdaUpdate()
                .set(SysDept::getUpdateBy, data.getUpdateBy())
                .set(SysDept::getDeptName, data.getDeptName())
                .set(SysDept::getPId, data.getPId())
                .eq(SysDept::getDeptId, data.getDeptId())
        );
    }

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    @Override
    public boolean delData(String id) {
        if (CheckUtil.strIsEmpty(id))
            throw NBException.create(EErrorCode.missingArg);
        if (CheckUtil.objIsNotEmpty(userDeptService.getOne(Qw.create().eq(SysDept.DEPT_ID, id))))
            throw NBException.create(EErrorCode.argCheckErr, "该机构下尚有绑定的用户,无法删除");
        return this.removeById(id);
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    @Override
    public SysDept getDetail(String id) {
        if (CheckUtil.strIsEmpty(id))
            throw NBException.create(EErrorCode.missingArg);
        return this.prepareReturnModel(this.getById(id));
    }

    @Override
    public List<SysDept> listAllDisDeptByUserId(String userId) {
        if (CheckUtil.objIsEmpty(userId))
            throw NBException.create(EErrorCode.missingArg);
        List<SysDept> result = Lists.newArrayList();
        List<UUserDept> userDeptList = userDeptService.listByUserId(userId);
        if (CheckUtil.collectionIsEmpty(userDeptList))
            return result;
        result = this.list(this.lightSelect(Qw.create().in(SysDept.DEPT_ID, ListUtil.getPkList(userDeptList, UUserDept.DEPT_ID))));
        if (CheckUtil.collectionIsEmpty(result))
            return result;
        for (SysDept item : result) {
            result.addAll(this.listAllDisDeptByParent(item));
        }
        return result;
    }

    @Override
    public List<SysDept> listAllDisDeptByParent(SysDept parent) {
        if (CheckUtil.objIsEmpty(parent))
            return Lists.newArrayList();
        return this.listAllDisDeptByParentId(parent.getDeptId());
    }

    @Override
    public List<SysDept> listAllDisDeptByParentId(String pId) {
        if (CheckUtil.objIsEmpty(pId))
            return Lists.newArrayList();
        List<SysDept> result = (List<SysDept>) (Object) redisUtil.hget(this.REDIS_ALL_DIS_DEPT_BY_PID_KEY, pId);
        if (CheckUtil.collectionIsNotEmpty(result))
            return result;
        result = Lists.newArrayList();
        this.recursiveGetDisDept(pId, result);
        redisUtil.hset(this.REDIS_ALL_DIS_DEPT_BY_PID_KEY, pId, result, this.REDIS_ALL_DIS_DEPT_BY_PID_EXPIRE_TIME);
        return result;
    }

    @Override
    public List<SysDept> listDisDeptByParent(SysDept parent) {
        if (CheckUtil.objIsEmpty(parent))
            return Lists.newArrayList();
        return this.listDisDeptByParentId(parent.getDeptId());
    }

    @Override
    public List<SysDept> listDisDeptByParentId(String pId) {
        if (CheckUtil.objIsEmpty(pId))
            return Lists.newArrayList();
        return this.list(this.lightSelect(Qw.create().eq(SysDept.P_ID, pId)));
    }

    private void recursiveGetDisDept(String pId, List<SysDept> all) {
        List<SysDept> childrens = this.listDisDeptByParentId(pId);
        if (CheckUtil.collectionIsNotEmpty(childrens)) {
            all.addAll(childrens);
            childrens.forEach(item -> this.recursiveGetDisDept(item.getDeptId(), all));
        }
    }

    private QueryWrapper lightSelect(Qw qw) {
        if (CheckUtil.objIsEmpty(qw))
            qw = Qw.create();
        return qw.select(SysDept.DEPT_ID, SysDept.DEPT_NAME, SysDept.P_ID);
    }

    /**
     * 分页查询
     *
     * @param pg
     * @param searchParams
     * @return
     */
    @Override
    public IPage<SysDept> pageData(Pg pg, SysDept.SearchParams searchParams) {
        return this.prepareReturnModel(this.page(pg.page(), searchParams.preWrapper(pg.wrapper())));
    }

    /**
     * 列表查询
     *
     * @param searchParams
     * @return
     */
    @Override
    public List<SysDept> listData(SysDept.SearchParams searchParams) {
        return this.prepareReturnModel(this.list(searchParams.preWrapper(null)));
    }

    /**
     * 连表分页查询map数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    @Override
    public IPage
            <Map
                    <String, Object>> pageMapData(Pg pg, SysDept.SearchParams searchParams) {
        return mapper.pageMapData(pg.page(), searchParams);
    }

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    @Override
    public SysDept prepareReturnModel(SysDept data) {
        return data;
    }

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    @Override
    public IPage<SysDept> prepareReturnModel(IPage<SysDept> page) {
        if (CheckUtil.objIsNotEmpty(page) && CheckUtil.collectionIsNotEmpty(page.getRecords()))
            this.prepareReturnModel(page.getRecords());
        return page;
    }

    /**
     * 处理返回值
     *
     * @param list
     * @return
     */
    @Override
    public List<SysDept> prepareReturnModel(List<SysDept> list) {
        if (CheckUtil.collectionIsNotEmpty(list))
            list.forEach(item -> {
                this.prepareReturnModel(item);
            });
        return list;
    }

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    @Override
    public IPage
            <Map
                    <String, Object>> prepareReturnMapModel(IPage
                                                                    <Map
                                                                            <String, Object>> page) {
        return page;
    }

    /**
     * 添加/修改数据前校验数据有效性(强制抛出异常)
     * 如果不需要抛出异常请不用调用该服务
     *
     * @param data
     */
    @Override
    public void validData(SysDept data) {
        if (CheckUtil.objIsEmpty(data, data.getDeptName()))
            throw NBException.create(EErrorCode.missingArg);
        if (CheckUtil.objIsEmpty(data.getPId()))
            data.setPId("0");
    }
}
