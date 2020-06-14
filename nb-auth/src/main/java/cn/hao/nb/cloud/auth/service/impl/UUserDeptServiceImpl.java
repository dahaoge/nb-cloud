package cn.hao.nb.cloud.auth.service.impl;

import cn.hao.nb.cloud.auth.entity.UUserDept;
import cn.hao.nb.cloud.auth.mapper.UUserDeptMapper;
import cn.hao.nb.cloud.auth.service.IUUserDeptService;
import cn.hao.nb.cloud.common.component.config.security.JwtTokenUtil;
import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Qw;
import cn.hao.nb.cloud.common.entity.TokenUser;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.common.util.IDUtil;
import cn.hao.nb.cloud.common.util.ListUtil;
import cn.hao.nb.cloud.common.util.UserUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户所属组织机构 服务实现类
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
@Service
public class UUserDeptServiceImpl extends ServiceImpl<UUserDeptMapper, UUserDept> implements IUUserDeptService {

    @Autowired
    IDUtil idUtil;
    @Autowired
    UUserDeptMapper mapper;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    @Override
    public UUserDept addData(UUserDept data) {
        this.validData(data);
        TokenUser tokenUser = UserUtil.getTokenUser(false);
        data.setTId(idUtil.nextId());
        if (CheckUtil.objIsNotEmpty(tokenUser)) {
            data.setCreateBy(tokenUser.getUserId());
            data.setUpdateBy(tokenUser.getUserId());
        }
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        this.save(data);
        jwtTokenUtil.delAllToken(data.getUserId().toString());
        return data;
    }

    @Override
    public UUserDept addData(Long userId, Long deptId) {
        if (CheckUtil.objIsEmpty(userId, deptId))
            throw NBException.create(EErrorCode.missingArg);
        UUserDept userDept = new UUserDept();
        userDept.setUserId(userId);
        userDept.setDeptId(deptId);
        return this.addData(userDept);
    }

    @Override
    public boolean addUser2Depts(Long userId, String deptIds) {
        if (CheckUtil.objIsEmpty(userId, deptIds))
            throw NBException.create(EErrorCode.missingArg);
        ListUtil.spliteCreateLong(deptIds).forEach(item -> {
            this.addData(userId, item);
        });
        return true;
    }

    /**
     * 增量更新数据
     *
     * @param data
     * @return
     */
    @Override
    public boolean incrementModifyData(UUserDept data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getTId()
        ))
            throw NBException.create(EErrorCode.missingArg);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        jwtTokenUtil.delAllToken(data.getUserId().toString());
        return this.updateById(data);
    }

    /**
     * 全量更新数据
     *
     * @param data
     * @return
     */
    @Override
    public boolean totalAmountModifyData(UUserDept data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getTId()
        ))
            throw NBException.create(EErrorCode.missingArg);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        jwtTokenUtil.delAllToken(data.getUserId().toString());
        return this.update(data, Wrappers.<UUserDept>lambdaUpdate()
                .set(UUserDept::getUpdateBy, data.getUpdateBy())
                .set(UUserDept::getUserId, data.getUserId())
                .set(UUserDept::getDeptId, data.getDeptId())
                .eq(UUserDept::getTId, data.getTId())
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
        UUserDept data = this.getById(id);
        if (CheckUtil.objIsEmpty(data))
            throw NBException.create(EErrorCode.noData);
        jwtTokenUtil.delAllToken(data.getUserId().toString());
        return this.removeById(id);
    }

    @Override
    public boolean delByDeptId(Long deptId) {
        if (CheckUtil.objIsEmpty(deptId))
            throw NBException.create(EErrorCode.missingArg).plusMsg("delByDeptId.deptId");
        return this.remove(
                Qw.create()
                        .eq(UUserDept.DEPT_ID, deptId)
        );
    }

    @Override
    public boolean delByDeptIds(List<Long> deptIds) {
        if (CheckUtil.collectionIsEmpty(deptIds))
            throw NBException.create(EErrorCode.missingArg).plusMsg("delByDeptIds.deptIds");
        deptIds.forEach(item -> {
            this.delByDeptId(item);
        });
        return true;
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    @Override
    public UUserDept getDetail(Long id) {
        if (CheckUtil.objIsEmpty(id))
            throw NBException.create(EErrorCode.missingArg);
        return this.prepareReturnModel(this.getById(id));
    }

    @Override
    public List<UUserDept> listByUserId(Long userId) {
        if (CheckUtil.objIsEmpty(userId))
            throw NBException.create(EErrorCode.missingArg);
        return this.list(Qw.create().eq(UUserDept.USER_ID, userId).select(UUserDept.T_ID, UUserDept.USER_ID, UUserDept.DEPT_ID));
    }

    /**
     * 分页查询
     *
     * @param pg
     * @param searchParams
     * @return
     */
    @Override
    public IPage<UUserDept> pageData(Pg pg, UUserDept.SearchParams searchParams) {
        return this.prepareReturnModel(this.page(pg.page(), searchParams.preWrapper(pg.wrapper())));
    }

    /**
     * 列表查询
     *
     * @param searchParams
     * @return
     */
    @Override
    public List<UUserDept> listData(UUserDept.SearchParams searchParams) {
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
                    <String, Object>> pageMapData(Pg pg, UUserDept.SearchParams searchParams) {
        return mapper.pageMapData(pg.page(), searchParams);
    }

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    @Override
    public UUserDept prepareReturnModel(UUserDept data) {
        return data;
    }

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    @Override
    public IPage<UUserDept> prepareReturnModel(IPage<UUserDept> page) {
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
    public List<UUserDept> prepareReturnModel(List<UUserDept> list) {
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
    public void validData(UUserDept data) {
        if (CheckUtil.objIsEmpty(data))
            throw NBException.create(EErrorCode.missingArg);
        if (CheckUtil.objIsEmpty(data.getUserId()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("userId");
        if (CheckUtil.objIsEmpty(data.getDeptId()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("deptId");
        TokenUser tokenUser = UserUtil.getTokenUser(true);
//        if (CheckUtil.collectionIsEmpty(tokenUser.getAuthDeptList()) || !tokenUser.getAuthDeptList().contains(data.getDeptId()))
//            throw NBException.create(EErrorCode.authDecodeError, "没有该组织的数据权限");
    }
}
