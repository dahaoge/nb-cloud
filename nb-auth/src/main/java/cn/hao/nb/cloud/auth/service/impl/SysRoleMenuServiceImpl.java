package cn.hao.nb.cloud.auth.service.impl;

import cn.hao.nb.cloud.auth.entity.SysMenu;
import cn.hao.nb.cloud.auth.entity.SysRoleMenu;
import cn.hao.nb.cloud.auth.mapper.SysRoleMenuMapper;
import cn.hao.nb.cloud.auth.service.ISysMenuService;
import cn.hao.nb.cloud.auth.service.ISysRoleMenuService;
import cn.hao.nb.cloud.auth.service.ISysRoleService;
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
 * 角色菜单  服务实现类
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    @Autowired
    IDUtil idUtil;
    @Autowired
    SysRoleMenuMapper mapper;
    @Autowired
    ISysRoleService roleService;
    @Autowired
    ISysMenuService menuService;

    /**
     * 添加数据
     * @param data
     * @return
     */
    @Override
    public SysRoleMenu addData(SysRoleMenu data) {
        this.validData(data);
        data.setRoleMenuId(idUtil.nextId());
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
    public boolean addRoleMenus(String roleCode, String menuCodes) {
        if (CheckUtil.objIsEmpty(roleCode, menuCodes))
            throw NBException.create(EErrorCode.missingArg);
        ListUtil.spliteCreate(menuCodes).forEach(item -> this.addRoleMenu(roleCode, item));
        return true;
    }

    @Override
    public boolean addRoleMenu(String roleCode, String menuCode) {
        if (CheckUtil.objIsEmpty(roleCode, menuCode))
            throw NBException.create(EErrorCode.missingArg);
        SysRoleMenu data = new SysRoleMenu();
        data.setRoleCode(roleCode);
        data.setMenuCode(menuCode);
        this.addData(data);
        return true;
    }

    /**
     * 增量更新数据
     * @param data
     * @return
     */
    @Override
    public boolean incrementModifyData(SysRoleMenu data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getRoleMenuId()
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
    public boolean totalAmountModifyData(SysRoleMenu data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getRoleMenuId()
        ))
            throw NBException.create(EErrorCode.missingArg);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        return this.update(data, Wrappers.<SysRoleMenu>lambdaUpdate()
                .set(SysRoleMenu::getUpdateBy, data.getUpdateBy())
                .set(SysRoleMenu::getRoleCode, data.getRoleCode())
                .set(SysRoleMenu::getMenuCode, data.getMenuCode())
                .eq(SysRoleMenu::getRoleMenuId, data.getRoleMenuId())
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
    public boolean delByRoleCode(String roleCode) {
        if (CheckUtil.objIsEmpty(roleCode))
            throw NBException.create(EErrorCode.missingArg);
        return this.remove(Qw.create().eq(SysRoleMenu.ROLE_CODE, roleCode));
    }

    @Override
    public boolean delByMenuCode(String menuCode) {
        if (CheckUtil.objIsEmpty(menuCode))
            throw NBException.create(EErrorCode.missingArg);
        return this.remove(Qw.create().eq(SysRoleMenu.MENU_CODE, menuCode));
    }

    /**
     * 获取详情
     * @param id
     * @return
     */
    @Override
    public SysRoleMenu getDetail(String id) {
        if (CheckUtil.strIsEmpty(id))
            throw NBException.create(EErrorCode.missingArg);
        return this.prepareReturnModel(this.getById(id));
    }

    @Override
    public List<SysRoleMenu> listByRoleCode(String roleCode) {
        if (CheckUtil.objIsEmpty(roleCode))
            throw NBException.create(EErrorCode.missingArg);
        SysRoleMenu.SearchParams searchParams = new SysRoleMenu().new SearchParams();
        searchParams.setRoleCode(roleCode);
        return this.listData(searchParams);
    }

    /**
     * 分页查询
     * @param pg
     * @param searchParams
     * @return
     */
    @Override
    public IPage<SysRoleMenu> pageData(Pg pg, SysRoleMenu.SearchParams searchParams) {
        return this.prepareReturnModel(this.page(pg.page(), searchParams.preWrapper(pg.wrapper())));
    }

    /**
     * 列表查询
     * @param searchParams
     * @return
     */
    @Override
    public List<SysRoleMenu> listData(SysRoleMenu.SearchParams searchParams) {
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
                    <String, Object>> pageMapData(Pg pg, SysRoleMenu.SearchParams searchParams) {
        return mapper.pageMapData(pg.page(), searchParams);
    }

    /**
     * 处理返回值
     * @param data
     * @return
     */
    @Override
    public SysRoleMenu prepareReturnModel(SysRoleMenu data) {
        if (CheckUtil.objIsNotEmpty(data)) {
            if (CheckUtil.objIsEmpty(data.getRole()))
                data.setRole(roleService.getByRoleCode(data.getRoleCode()));
            if (CheckUtil.objIsNotEmpty(data.getMenu()))
                data.setMenu(menuService.getByMenuCode(data.getMenuCode()));
        }
        return data;
    }

    /**
     * 处理返回值
     * @param page
     * @return
     */
    @Override
    public IPage<SysRoleMenu> prepareReturnModel(IPage<SysRoleMenu> page) {
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
    public List<SysRoleMenu> prepareReturnModel(List<SysRoleMenu> list) {
        if (CheckUtil.collectionIsNotEmpty(list)) {
            List<SysMenu> menus = menuService.list(Qw.create().in(SysMenu.MENU_CODE, ListUtil.getPkList(list, SysRoleMenu.MENU_CODE)));
            list.forEach(item -> {
                menus.forEach(menu -> {
                    if (menu.getMenuCode().equals(item.getMenuCode()))
                        item.setMenu(menu);
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
    public void validData(SysRoleMenu data) {
        if (CheckUtil.objIsEmpty(data))
            throw NBException.create(EErrorCode.missingArg);
    }
}
