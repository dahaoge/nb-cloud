package cn.hao.nb.cloud.auth.service.impl;

import cn.hao.nb.cloud.auth.entity.SysRolePermission;
import cn.hao.nb.cloud.auth.mapper.SysRolePermissionMapper;
import cn.hao.nb.cloud.auth.service.ISysRolePermissionService;
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
 * 角色权限  服务实现类
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {

    @Autowired
    IDUtil idUtil;
    @Autowired
    SysRolePermissionMapper mapper;

    /**
     * 添加数据
     * @param data
     * @return
     */
    @Override
    public SysRolePermission addData(SysRolePermission data) {
        this.validData(data);
        data.setRolePermissionId(idUtil.nextId());
        TokenUser tokenUser = UserUtil.getTokenUser(false);
        if (CheckUtil.objIsNotEmpty(tokenUser)) {
            data.setCreateBy(tokenUser.getUserId());
            data.setUpdateBy(tokenUser.getUserId());
        }
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        this.save(data);
        return data;
    }

    @Override
    public SysRolePermission addRolePermission(String roleCode, String permissionCode) {
        if (CheckUtil.objIsEmpty(roleCode, permissionCode))
            throw NBException.create(EErrorCode.missingArg);
        SysRolePermission data = new SysRolePermission();
        data.setRoleCode(roleCode);
        data.setPermissionCode(permissionCode);
        return this.addData(data);
    }

    @Override
    public boolean addRolePermissions(String roleCode, String permissionCodes) {
        if (CheckUtil.objIsEmpty(roleCode, permissionCodes))
            throw NBException.create(EErrorCode.missingArg);
        ListUtil.spliteCreate(permissionCodes).forEach(item -> this.addRolePermission(roleCode, item));
        return true;
    }

    /**
     * 增量更新数据
     * @param data
     * @return
     */
    @Override
    public boolean incrementModifyData(SysRolePermission data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getRolePermissionId()
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
    public boolean totalAmountModifyData(SysRolePermission data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getRolePermissionId()
        ))
            throw NBException.create(EErrorCode.missingArg);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        return this.update(data, Wrappers.<SysRolePermission>lambdaUpdate()
                .set(SysRolePermission::getUpdateBy, data.getUpdateBy())
                .set(SysRolePermission::getRoleCode, data.getRoleCode())
                .set(SysRolePermission::getPermissionCode, data.getPermissionCode())
                .eq(SysRolePermission::getRolePermissionId, data.getRolePermissionId())
        );
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @Override
    public boolean delData(Long id) {
        if (CheckUtil.objIsEmpty(id))
            throw NBException.create(EErrorCode.missingArg);
        return this.removeById(id);
    }

    @Override
    public boolean delByRoleCode(String roleCode) {
        if (CheckUtil.objIsEmpty(roleCode))
            throw NBException.create(EErrorCode.missingArg);
        return this.remove(Qw.create().eq(SysRolePermission.ROLE_CODE, roleCode));
    }

    @Override
    public boolean delByPermissionCode(String permissionCode) {
        if (CheckUtil.objIsEmpty(permissionCode))
            throw NBException.create(EErrorCode.missingArg);
        return this.remove(Qw.create().eq(SysRolePermission.PERMISSION_CODE, permissionCode));
    }

    /**
     * 获取详情
     * @param id
     * @return
     */
    @Override
    public SysRolePermission getDetail(Long id) {
        if (CheckUtil.objIsEmpty(id))
            throw NBException.create(EErrorCode.missingArg);
        return this.prepareReturnModel(this.getById(id));
    }

    /**
     * 分页查询
     * @param pg
     * @param searchParams
     * @return
     */
    @Override
    public IPage<SysRolePermission> pageData(Pg pg, SysRolePermission.SearchParams searchParams) {
        return this.prepareReturnModel(this.page(pg.page(), searchParams.preWrapper(pg.wrapper())));
    }

    /**
     * 列表查询
     * @param searchParams
     * @return
     */
    @Override
    public List<SysRolePermission> listData(SysRolePermission.SearchParams searchParams) {
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
                    <String, Object>> pageMapData(Pg pg, SysRolePermission.SearchParams searchParams) {
        return mapper.pageMapData(pg.page(), searchParams);
    }

    /**
     * 处理返回值
     * @param data
     * @return
     */
    @Override
    public SysRolePermission prepareReturnModel(SysRolePermission data) {
        return data;
    }

    /**
     * 处理返回值
     * @param page
     * @return
     */
    @Override
    public IPage<SysRolePermission> prepareReturnModel(IPage<SysRolePermission> page) {
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
    public List<SysRolePermission> prepareReturnModel(List<SysRolePermission> list) {
        if (CheckUtil.collectionIsNotEmpty(list))
            list.forEach(item -> {
                this.prepareReturnModel(item);
            });
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
    public void validData(SysRolePermission data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(data.getPermissionCode(), data.getRoleCode()))
            throw NBException.create(EErrorCode.missingArg);
    }
}
