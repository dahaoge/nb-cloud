package cn.hao.nb.cloud.auth.service.impl;

import cn.hao.nb.cloud.auth.entity.SysDept;
import cn.hao.nb.cloud.auth.entity.UUserDept;
import cn.hao.nb.cloud.auth.mapper.SysDeptMapper;
import cn.hao.nb.cloud.auth.service.ISysDeptService;
import cn.hao.nb.cloud.auth.service.IUUserDeptService;
import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Qw;
import cn.hao.nb.cloud.common.entity.TokenUser;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.util.*;
import cn.hao.nb.cloud.ydglExternalApi.entity.ExternalDepartment;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.google.gson.GsonBuilder;
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
    private String REDIS_DEPT_TREE_KEY = "REDIS_DEPT_TREE_KEY";
    private long REDIS_DEPT_TREE_EXPIRE_TIME = 86400;


    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    @Override
    public SysDept addData(SysDept data) {
        this.validData(data);
        if (CheckUtil.objIsEmpty(data.getDeptId()))
            data.setDeptId(idUtil.nextId());
        TokenUser tokenUser = UserUtil.getTokenUser(false);
        if (CheckUtil.objIsNotEmpty(tokenUser)) {
            data.setCreateBy(tokenUser.getUserId());
            data.setUpdateBy(tokenUser.getUserId());
        }
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        if (CheckUtil.objIsEmpty(data.getPId()))
            data.setPId(0L);
        this.save(data);
        return data;
    }

    @Override
    public SysDept addData(String deptName, Long pId) {
        SysDept dept = new SysDept();
        dept.setDeptName(deptName);
        dept.setPId(pId);
        return this.addData(dept);
    }

    @Override
    public SysDept addData(String deptName) {
        return this.addData(deptName, null);
    }

    @Override
    public SysDept newFromExternalDept(ExternalDepartment externalDepartment) {
        SysDept data = new SysDept();
        data.setDeptName(externalDepartment.getDeptName());
        data.setExternalDeptId(externalDepartment.getDeptId());
        return data;
    }

    @Override
    public SysDept refreshCompanyDeptByExternalDepartment(long companyId, String externalDeptJsonList) {
        if (CheckUtil.objIsEmpty(companyId, externalDeptJsonList))
            throw NBException.create(EErrorCode.missingArg);
        ExternalDepartment externalDepartment = new GsonBuilder().create().fromJson(externalDeptJsonList, new TypeToken<ExternalDepartment>() {
        }.getType());
        // 删除之前的数据
        this.delByCompanyId(companyId);
        SysDept root = this.newFromExternalDept(externalDepartment);
        root.setPId(0L);
        root.setCompanyId(companyId);
        this.addChildByExternalDept(this.addData(root), externalDepartment);
        return root;
    }

    private void addChildByExternalDept(SysDept parent, ExternalDepartment externalParent) {
        if (CheckUtil.objIsEmpty(parent, externalParent) || CheckUtil.objIsEmpty(externalParent.getChildren()))
            return;
        SysDept children = this.newFromExternalDept(externalParent.getChildren());
        children.setPId(parent.getDeptId());
        children.setCompanyId(parent.getCompanyId());
        this.addChildByExternalDept(this.addData(children), externalParent.getChildren());
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
    public boolean delData(Long id) {
        if (CheckUtil.objIsEmpty(id))
            throw NBException.create(EErrorCode.missingArg);
        if (CheckUtil.objIsNotEmpty(userDeptService.getOne(Qw.create().eq(SysDept.DEPT_ID, id))))
            throw NBException.create(EErrorCode.argCheckErr, "该机构下尚有绑定的用户,无法删除");
        return this.removeById(id);
    }

    @Override
    public boolean delByCompanyId(Long companyId) {
        if (CheckUtil.objIsEmpty(companyId))
            throw NBException.create(EErrorCode.missingArg).plusMsg("companyId");
        List<SysDept> depts = this.list(Qw.create().eq(SysDept.COMPANY_ID, companyId).select(SysDept.DEPT_ID));
        if (CheckUtil.objIsNotEmpty(userDeptService.getOne(
                Qw.create().in(UUserDept.DEPT_ID, ListUtil.getPkList(depts, SysDept.DEPT_ID)))))
            throw NBException.create(EErrorCode.noData, "该公司下的组织机构已绑定了用户,请先将绑定的用户妥善处理后再删除组织机构信息");
        return this.remove(Qw.create().eq(SysDept.COMPANY_ID, companyId));
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    @Override
    public SysDept getDetail(Long id) {
        if (CheckUtil.objIsEmpty(id))
            throw NBException.create(EErrorCode.missingArg);
        return this.prepareReturnModel(this.getById(id));
    }

    @Override
    public List<SysDept> listAllDisDeptByUserId(Long userId) {
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
    public List<SysDept> listAllDisDeptByParentId(Long pId) {
        if (CheckUtil.objIsEmpty(pId))
            return Lists.newArrayList();
        List<SysDept> result = (List<SysDept>) (Object) redisUtil.hget(this.REDIS_ALL_DIS_DEPT_BY_PID_KEY, pId.toString());
        if (CheckUtil.collectionIsNotEmpty(result))
            return result;
        result = Lists.newArrayList();
        this.recursiveGetDisDept(pId, result);
        redisUtil.hset(this.REDIS_ALL_DIS_DEPT_BY_PID_KEY, pId.toString(), result, this.REDIS_ALL_DIS_DEPT_BY_PID_EXPIRE_TIME);
        return result;
    }

    @Override
    public List<SysDept> listDisDeptByParent(SysDept parent) {
        if (CheckUtil.objIsEmpty(parent))
            return Lists.newArrayList();
        return this.listDisDeptByParentId(parent.getDeptId());
    }

    @Override
    public List<SysDept> listDisDeptByParentId(Long pId) {
        if (CheckUtil.objIsEmpty(pId))
            return Lists.newArrayList();
        return this.list(this.lightSelect(Qw.create().eq(SysDept.P_ID, pId)));
    }

    @Override
    public List<SysDept> deptTree(Long deptId) {
        deptId = CheckUtil.objIsEmpty(deptId) ? 0L : deptId;
        List<SysDept> result = (List<SysDept>) redisUtil.hget(this.REDIS_DEPT_TREE_KEY, deptId.toString());
        if (CheckUtil.objIsNotEmpty(result))
            return result;

        result = this.listDisDeptByParentId(deptId);
        result.forEach(item -> this.recursiveSetChildren(item));
        redisUtil.hset(this.REDIS_DEPT_TREE_KEY, deptId.toString(), result, this.REDIS_DEPT_TREE_EXPIRE_TIME);
        return result;
    }

    @Override
    public List<SysDept> userDeptTree(Long userId) {
        if (CheckUtil.objIsEmpty(userId))
            throw NBException.create(EErrorCode.missingArg);
        List<SysDept> result = this.listByUserId(userId);
        if (CheckUtil.objIsNotEmpty(result))
            result.forEach(item -> this.deptTree(item.getDeptId()));
        return result;
    }

    @Override
    public List<SysDept> listByUserId(Long userId) {
        if (CheckUtil.objIsEmpty(userId))
            throw NBException.create(EErrorCode.missingArg);
        return mapper.listByUserId(userId);
    }

    private void recursiveGetDisDept(Long pId, List<SysDept> all) {
        List<SysDept> childrens = this.listDisDeptByParentId(pId);
        if (CheckUtil.collectionIsNotEmpty(childrens)) {
            all.addAll(childrens);
            childrens.forEach(item -> this.recursiveGetDisDept(item.getDeptId(), all));
        }
    }

    private void recursiveSetChildren(SysDept dept) {
        if (CheckUtil.objIsNotEmpty(dept)) {
            dept.setChildren(this.listDisDeptByParentId(dept.getDeptId()));
            if (CheckUtil.objIsNotEmpty(dept.getChildren()))
                dept.getChildren().forEach(item -> this.recursiveSetChildren(item));
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
            data.setPId(0L);
    }
}
