package cn.hao.nb.cloud.auth.service.impl;

import cn.hao.nb.cloud.auth.entity.SysPermission;
import cn.hao.nb.cloud.auth.entity.SysRolePermission;
import cn.hao.nb.cloud.auth.mapper.SysPermissionMapper;
import cn.hao.nb.cloud.auth.service.ISysPermissionService;
import cn.hao.nb.cloud.auth.service.ISysRolePermissionService;
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
 * 权限表  服务实现类
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Autowired
    IDUtil idUtil;
    @Autowired
    SysPermissionMapper mapper;
    @Autowired
    ISysRolePermissionService rolePermissionService;

    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    @Override
    public SysPermission addData(SysPermission data) {
        this.validData(data);
        data.setPermissionId(idUtil.nextId());
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
     *
     * @param data
     * @return
     */
    @Override
    public boolean incrementModifyData(SysPermission data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getPermissionId()
        ))
            throw NBException.create(EErrorCode.missingArg);

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
    public boolean totalAmountModifyData(SysPermission data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getPermissionId()
        ))
            throw NBException.create(EErrorCode.missingArg);

        this.beUsedCheck(data);

        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        return this.update(data, Wrappers.<SysPermission>lambdaUpdate()
                .set(SysPermission::getUpdateBy, data.getUpdateBy())
                .set(SysPermission::getPermissionCode, data.getPermissionCode())
                .set(SysPermission::getPermissionName, data.getPermissionName())
                .eq(SysPermission::getPermissionId, data.getPermissionId())
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
        SysPermission data = this.getById(id);
        if (CheckUtil.objIsEmpty(data))
            throw NBException.create(EErrorCode.noData);
        this.beUsedCheck(data);
        return this.removeById(id);
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    @Override
    public SysPermission getDetail(Long id) {
        if (CheckUtil.objIsEmpty(id))
            throw NBException.create(EErrorCode.missingArg);
        return this.prepareReturnModel(this.getById(id));
    }

    /**
     * 分页查询
     *
     * @param pg
     * @param searchParams
     * @return
     */
    @Override
    public IPage<SysPermission> pageData(Pg pg, SysPermission.SearchParams searchParams) {
        return this.prepareReturnModel(this.page(pg.page(), searchParams.preWrapper(pg.wrapper())));
    }

    /**
     * 列表查询
     *
     * @param searchParams
     * @return
     */
    @Override
    public List<SysPermission> listData(SysPermission.SearchParams searchParams) {
        return this.prepareReturnModel(this.list(searchParams.preWrapper(null)));
    }

    @Override
    public List<SysPermission> listByRoleCode(String roleCode) {
        if (CheckUtil.objIsEmpty(roleCode))
            throw NBException.create(EErrorCode.missingArg);
        return mapper.listByRoleCode(roleCode);
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
                    <String, Object>> pageMapData(Pg pg, SysPermission.SearchParams searchParams) {
        return mapper.pageMapData(pg.page(), searchParams);
    }

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    @Override
    public SysPermission prepareReturnModel(SysPermission data) {
        return data;
    }

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    @Override
    public IPage<SysPermission> prepareReturnModel(IPage<SysPermission> page) {
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
    public List<SysPermission> prepareReturnModel(List<SysPermission> list) {
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
    public void validData(SysPermission data) {
        if (CheckUtil.objIsEmpty(data))
            throw NBException.create(EErrorCode.missingArg);
        if (CheckUtil.objIsEmpty(data.getPermissionId()) && CheckUtil.objIsEmpty(data.getPermissionCode(), data.getPermissionName()))
            throw NBException.create(EErrorCode.missingArg);
        this.beUsedCheck(data);
    }

    private void beUsedCheck(SysPermission data) {
        if (CheckUtil.objIsEmpty(data))
            throw NBException.create(EErrorCode.missingArg);
        if (CheckUtil.objIsNotEmpty(data.getPermissionCode())) {
            Qw qw = Qw.create().eq(SysPermission.PERMISSION_CODE, data.getPermissionCode());
            if (CheckUtil.objIsNotEmpty(data.getPermissionId())) {
                qw.ne(SysPermission.PERMISSION_ID, data.getPermissionId());
                SysPermission dbData = this.getById(data.getPermissionId());
                if (rolePermissionService.count(Qw.create().eq(SysRolePermission.PERMISSION_CODE, dbData.getPermissionCode())) > 0)
                    throw NBException.create(EErrorCode.beUsed, "无法修改使用中菜单编码");
            }
            if (this.count(qw) > 0)
                throw NBException.create(EErrorCode.beUsed);
        }
    }
}
