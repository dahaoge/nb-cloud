package cn.hao.nb.cloud.auth.service.impl;

import cn.hao.nb.cloud.auth.entity.SysRole;
import cn.hao.nb.cloud.auth.entity.SysRoleMenu;
import cn.hao.nb.cloud.auth.entity.SysRolePermission;
import cn.hao.nb.cloud.auth.entity.UUserRole;
import cn.hao.nb.cloud.auth.mapper.SysRoleMapper;
import cn.hao.nb.cloud.auth.service.ISysRoleMenuService;
import cn.hao.nb.cloud.auth.service.ISysRolePermissionService;
import cn.hao.nb.cloud.auth.service.ISysRoleService;
import cn.hao.nb.cloud.auth.service.IUUserRoleService;
import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Qw;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.common.util.IDUtil;
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
 * 角色  服务实现类
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    IDUtil idUtil;
    @Autowired
    SysRoleMapper mapper;
    @Autowired
    ISysRoleMenuService roleMenuService;
    @Autowired
    ISysRolePermissionService rolePermissionService;
    @Autowired
    IUUserRoleService userRoleService;

    /**
     * 添加数据
     * @param data
     * @return
     */
    @Override
    public SysRole addData(SysRole data) {
        this.validData(data);
        data.setRoleId(idUtil.nextId());
        data.setCreateBy(UserUtil.getTokenUser(true).getUserId());
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        this.save(data);
        return data;
    }

    /**
     * 增量更新数据
     * @param data
     * @return
     */
    @Override
    public boolean incrementModifyData(SysRole data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getRoleId()
        ))
            throw NBException.create(EErrorCode.missingArg);
        if (CheckUtil.objIsNotEmpty(data.getRoleCode()))
            this.beUsedCheck(data);
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
    public boolean totalAmountModifyData(SysRole data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getRoleId(), data.getRoleCode(), data.getRoleName()
        ))
            throw NBException.create(EErrorCode.missingArg);
        this.beUsedCheck(data);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        return this.update(data, Wrappers.<SysRole>lambdaUpdate()
                .set(SysRole::getUpdateBy, data.getUpdateBy())
                .set(SysRole::getRoleCode, data.getRoleCode())
                .set(SysRole::getRoleName, data.getRoleName())
                .eq(SysRole::getRoleId, data.getRoleId())
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
        SysRole data = this.getById(id);
        if (CheckUtil.objIsEmpty(data))
            throw NBException.create(EErrorCode.noData);
        this.beUsedCheck(data);
        return this.removeById(id);
    }

    /**
     * 获取详情
     * @param id
     * @return
     */
    @Override
    public SysRole getDetail(String id) {
        if (CheckUtil.strIsEmpty(id))
            throw NBException.create(EErrorCode.missingArg);
        return this.prepareReturnModel(this.getById(id));
    }

    @Override
    public SysRole getByRoleCode(String roleCode) {
        if (CheckUtil.objIsEmpty(roleCode))
            throw NBException.create(EErrorCode.missingArg);
        return this.getOne(Qw.create().eq(SysRole.ROLE_CODE, roleCode));
    }

    /**
     * 分页查询
     * @param pg
     * @param searchParams
     * @return
     */
    @Override
    public IPage<SysRole> pageData(Pg pg, SysRole.SearchParams searchParams) {
        return this.prepareReturnModel(this.page(pg.page(), searchParams.preWrapper(pg.wrapper())));
    }

    /**
     * 列表查询
     * @param searchParams
     * @return
     */
    @Override
    public List<SysRole> listData(SysRole.SearchParams searchParams) {
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
                    <String, Object>> pageMapData(Pg pg, SysRole.SearchParams searchParams) {
        return mapper.pageMapData(pg.page(), searchParams);
    }

    /**
     * 处理返回值
     * @param data
     * @return
     */
    @Override
    public SysRole prepareReturnModel(SysRole data) {
        return data;
    }

    /**
     * 处理返回值
     * @param page
     * @return
     */
    @Override
    public IPage<SysRole> prepareReturnModel(IPage<SysRole> page) {
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
    public List<SysRole> prepareReturnModel(List<SysRole> list) {
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
    public void validData(SysRole data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(data.getRoleCode(), data.getRoleName()))
            throw NBException.create(EErrorCode.missingArg);
        this.beUsedCheck(data);
    }

    private void beUsedCheck(SysRole data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(data.getRoleCode()))
            throw NBException.create(EErrorCode.missingArg);
        Qw qw = Qw.create().eq(SysRole.ROLE_CODE, data.getRoleCode());
        if (CheckUtil.objIsNotEmpty(data.getRoleId())) {
            qw.ne(SysRole.ROLE_ID, data.getRoleId());
            SysRole dbData = this.getById(data.getRoleId());
            if (roleMenuService.count(Qw.create().eq(SysRoleMenu.ROLE_CODE, dbData.getRoleCode())) > 0)
                throw NBException.create(EErrorCode.beUsed, "无法修改使用中的角色编码");
            if (rolePermissionService.count(Qw.create().eq(SysRolePermission.ROLE_CODE, dbData.getRoleCode())) > 0)
                throw NBException.create(EErrorCode.beUsed, "无法修改使用中的角色编码");
            if (userRoleService.count(Qw.create().eq(UUserRole.ROLE_CODE, dbData.getRoleCode())) > 0)
                throw NBException.create(EErrorCode.beUsed, "无法修改使用中的角色编码");
        }
        if (CheckUtil.objIsNotEmpty(this.getOne(qw)))
            throw NBException.create(EErrorCode.beUsed, "该角色编码已被使用");
    }
}
