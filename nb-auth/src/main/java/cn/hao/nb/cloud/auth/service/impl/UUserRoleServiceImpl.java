package cn.hao.nb.cloud.auth.service.impl;

import cn.hao.nb.cloud.auth.entity.SysRole;
import cn.hao.nb.cloud.auth.entity.UUserRole;
import cn.hao.nb.cloud.auth.mapper.UUserRoleMapper;
import cn.hao.nb.cloud.auth.service.CommonService;
import cn.hao.nb.cloud.auth.service.ISysRoleService;
import cn.hao.nb.cloud.auth.service.IUUserRoleService;
import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Qw;
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
 * 用户角色  服务实现类
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
@Service
public class UUserRoleServiceImpl extends ServiceImpl<UUserRoleMapper, UUserRole> implements IUUserRoleService {

    @Autowired
    IDUtil idUtil;
    @Autowired
    UUserRoleMapper mapper;
    @Autowired
    CommonService commonService;
    @Autowired
    ISysRoleService roleService;

    /**
     * 添加数据
     * @param data
     * @return
     */
    @Override
    public UUserRole addData(UUserRole data) {
        this.validData(data);
        data.setUrId(idUtil.nextId());
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
    public UUserRole addUserRole(String userId, String roleCode) {
        if (CheckUtil.objIsEmpty(userId, roleCode))
            throw NBException.create(EErrorCode.missingArg);
        UUserRole data = new UUserRole();
        data.setUserId(userId);
        data.setRoleCode(roleCode);
        return this.addData(data);
    }

    @Override
    public boolean addUserRoles(String userId, String roleCodes) {
        if (CheckUtil.objIsEmpty(userId, roleCodes))
            throw NBException.create(EErrorCode.missingArg);
        this.delByUserId(userId);
        ListUtil.spliteCreate(roleCodes).forEach(item -> this.addUserRole(userId, item));
        return true;
    }

    /**
     * 增量更新数据
     * @param data
     * @return
     */
    @Override
    public boolean incrementModifyData(UUserRole data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getUrId()
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
    public boolean totalAmountModifyData(UUserRole data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getUrId()
        ))
            throw NBException.create(EErrorCode.missingArg);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        return this.update(data, Wrappers.<UUserRole>lambdaUpdate()
                .set(UUserRole::getUpdateBy, data.getUpdateBy())
                .set(UUserRole::getUserId, data.getUserId())
                .set(UUserRole::getRoleCode, data.getRoleCode())
                .eq(UUserRole::getUrId, data.getUrId())
        );
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @Override
    public boolean delData(String id) {
        if (CheckUtil.strIsEmpty(id))
            throw NBException.create(EErrorCode.missingArg);
        return this.removeById(id);
    }

    @Override
    public boolean delByUserId(String userId) {
        if (CheckUtil.objIsEmpty(userId))
            throw NBException.create(EErrorCode.missingArg);
        return this.remove(Qw.create().eq(UUserRole.USER_ID, userId));
    }

    @Override
    public boolean delByRoleCode(String roleCode) {
        if (CheckUtil.objIsEmpty(roleCode))
            throw NBException.create(EErrorCode.missingArg);
        return this.remove(Qw.create().eq(UUserRole.ROLE_CODE, roleCode));
    }

    /**
     * 获取详情
     * @param id
     * @return
     */
    @Override
    public UUserRole getDetail(String id) {
        if (CheckUtil.strIsEmpty(id))
            throw NBException.create(EErrorCode.missingArg);
        return this.prepareReturnModel(this.getById(id));
    }

    @Override
    public List<UUserRole> listByUserId(String userId) {
        if (CheckUtil.objIsEmpty(userId))
            throw NBException.create(EErrorCode.missingArg);
        UUserRole.SearchParams searchParams = new UUserRole().new SearchParams();
        searchParams.setUserId(userId);
        return this.listData(searchParams);
    }

    /**
     * 分页查询
     * @param pg
     * @param searchParams
     * @return
     */
    @Override
    public IPage<UUserRole> pageData(Pg pg, UUserRole.SearchParams searchParams) {
        return this.prepareReturnModel(this.page(pg.page(), searchParams.preWrapper(pg.wrapper())));
    }

    /**
     * 列表查询
     * @param searchParams
     * @return
     */
    @Override
    public List<UUserRole> listData(UUserRole.SearchParams searchParams) {
        return this.prepareReturnModel(this.list(searchParams.preWrapper(null)));
    }

    /**
     * 连表分页查询map数据
     * @param pg
     * @param searchParams
     * @return
     */
    @Override
    public IPage
            <Map
                    <String, Object>> pageMapData(Pg pg, UUserRole.SearchParams searchParams) {
        return mapper.pageMapData(pg.page(), searchParams);
    }

    /**
     * 处理返回值
     * @param data
     * @return
     */
    @Override
    public UUserRole prepareReturnModel(UUserRole data) {
        if (CheckUtil.objIsNotEmpty(data)) {
            if (CheckUtil.objIsEmpty(data.getUserInfo()))
                data.setUserInfo(commonService.getRedisUser(data.getUserId()));
            if (CheckUtil.objIsEmpty(data.getRole()))
                data.setRole(roleService.getByRoleCode(data.getRoleCode()));
        }
        return data;
    }

    /**
     * 处理返回值
     * @param page
     * @return
     */
    @Override
    public IPage<UUserRole> prepareReturnModel(IPage<UUserRole> page) {
        if (CheckUtil.objIsNotEmpty(page) && CheckUtil.collectionIsNotEmpty(page.getRecords()))
            this.prepareReturnModel(page.getRecords());
        return page;
    }

    /**
     * 处理返回值
     * @param list
     * @return
     */
    @Override
    public List<UUserRole> prepareReturnModel(List<UUserRole> list) {
        if (CheckUtil.collectionIsNotEmpty(list)) {
            List<SysRole> roles = roleService.list(Qw.create().in(SysRole.ROLE_CODE, ListUtil.getPkList(list, UUserRole.ROLE_CODE)));
            list.forEach(item -> {
                roles.forEach(role -> {
                    if (role.getRoleCode().equals(item.getRoleCode()))
                        item.setRole(role);
                });
                this.prepareReturnModel(item);
            });
        }
        return list;
    }

    /**
     * 处理返回值
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
     * @param data
     */
    @Override
    public void validData(UUserRole data) {
        if (CheckUtil.objIsEmpty(data))
            throw NBException.create(EErrorCode.missingArg);
    }
}
