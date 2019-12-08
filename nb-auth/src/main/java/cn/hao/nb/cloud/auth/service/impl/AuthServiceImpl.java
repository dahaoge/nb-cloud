package cn.hao.nb.cloud.auth.service.impl;

import cn.hao.nb.cloud.auth.entity.*;
import cn.hao.nb.cloud.auth.mapper.AuthMapper;
import cn.hao.nb.cloud.auth.service.*;
import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Qw;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.penum.EMenuType;
import cn.hao.nb.cloud.common.penum.EYn;
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.common.util.IDUtil;
import cn.hao.nb.cloud.common.util.ListUtil;
import cn.hao.nb.cloud.common.util.UserUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: sunhao
 * @Date: 2019-05-16 13:53
 * @Description:
 */
@Slf4j
@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    IDUtil idUtil;
    @Autowired
    ISysRoleService roleService;
    @Autowired
    ISysMenuService menuService;
    @Autowired
    ISysPermissionService permissionService;
    @Autowired
    IUUserInfoService userInfoService;
    @Autowired
    ISysRoleMenuService roleMenuService;
    @Autowired
    ISysRolePermissionService rolePermissionService;
    @Autowired
    IUUserRoleService userRoleService;
    @Autowired
    AuthMapper authMapper;

    @Override
    public Boolean isRoleInUse(String roleCode) {
        if (CheckUtil.strIsEmpty(roleCode))
            throw NBException.create(EErrorCode.missingArg);
        return CheckUtil.collectionIsNotEmpty(this.listUserRolesByRoleCode(roleCode))
                || CheckUtil.collectionIsNotEmpty(this.listRoleMenuByRoleCode(roleCode))
                || CheckUtil.collectionIsNotEmpty(this.listRolePermissionByRoleCode(roleCode));
    }

    @Override
    public Boolean addRole(SysRole role) {
        if (CheckUtil.objIsEmpty(role) || CheckUtil.objIsEmpty(role.getRoleCode(), role.getRoleName()))
            throw NBException.create(EErrorCode.missingArg);
        if (CheckUtil.objIsNotEmpty(this.getRoleByCode(role.getRoleCode())))
            throw NBException.create(EErrorCode.repeat, "重复的角色编码");
        role.setRoleId(idUtil.nextId());
        role.setCreateBy(UserUtil.getTokenUser().getUserId());
        return roleService.save(role);
    }

    @Override
    public Boolean modifyRole(SysRole role) {
        if (CheckUtil.objIsEmpty(role) || CheckUtil.objIsEmpty(role.getRoleCode(), role.getRoleName(), role.getRoleId()))
            throw NBException.create(EErrorCode.missingArg);
        SysRole oldRole = roleService.getById(role.getRoleId());
        if (CheckUtil.objIsEmpty(oldRole))
            throw NBException.create(EErrorCode.noData);
        SysRole temp = this.getRoleByCode(role.getRoleCode());
        if (CheckUtil.objIsNotEmpty(temp) && !role.getRoleId().equals(temp.getRoleId()))
            throw NBException.create(EErrorCode.repeat, "重复的角色编码");
        if (this.isRoleInUse(oldRole.getRoleCode())) {
            throw NBException.create(EErrorCode.beUsed, "无法修改在用的角色");
        }
        role.setUpdateBy(UserUtil.getTokenUser().getUserId());
        return roleService.updateById(role);
    }

    @Override

    public List<SysRole> listRoles() {
        List<SysRole> list = Lists.newArrayList();
        List<SysRole> list1 = roleService.list();
        List<SysRole> list2 = roleService.list(Qw.create().eq(SysRole.DELETED, EYn.y.getValue()));
        if (CheckUtil.collectionIsNotEmpty(list1))
            list.addAll(list1);
        if (CheckUtil.collectionIsNotEmpty(list2))
            list.addAll(list2);
        return list;
    }

    @Override
    public Boolean delRole(String roleId) {
        if (CheckUtil.strIsEmpty(roleId))
            throw NBException.create(EErrorCode.missingArg);
        SysRole role = roleService.getById(roleId);
        if (CheckUtil.objIsEmpty(role))
            throw NBException.create(EErrorCode.noData);
//        if (this.isRoleInUse(role.getRoleCode())) {
//            throw NBException.create(EErrorCode.beUsed, "无法删除在用的角色");
//        }
        return roleService.removeById(role.getRoleId());
    }

    @Override
    public Boolean revertRole(String roleId) {
        SysRole role = new SysRole();
        role.setRoleId(roleId);
        role.setDeleted(0);
        return roleService.updateById(role);
    }

    @Override

    public SysRole getRoleByCode(String roleCode) {
        if (CheckUtil.strIsEmpty(roleCode))
            throw NBException.create(EErrorCode.missingArg);
        return roleService.getOne(Qw.create().eq(SysRole.ROLE_CODE, roleCode));
    }

    @Override

    public SysPermission getPermissionByCode(String permissionCode) {
        if (CheckUtil.strIsEmpty(permissionCode))
            throw NBException.create(EErrorCode.missingArg);
        return permissionService.getOne(Qw.create().eq(SysPermission.PERMISSION_CODE, permissionCode));
    }

    @Override

    public SysMenu getMenuByCode(String menuCode) {
        if (CheckUtil.strIsEmpty(menuCode))
            throw NBException.create(EErrorCode.missingArg);
        return menuService.getOne(Qw.create().eq(SysMenu.MENU_CODE, menuCode));
    }

    @Override
    public Boolean addPermission(SysPermission permission) {
        if (CheckUtil.objIsEmpty(permission) || CheckUtil.objIsEmpty(permission.getPermissionCode(), permission.getPermissionName()))
            throw NBException.create(EErrorCode.missingArg);
        if (CheckUtil.objIsNotEmpty(this.getPermissionByCode(permission.getPermissionCode())))
            throw NBException.create(EErrorCode.repeat, "重复的权限编码");
        permission.setCreateBy(UserUtil.getTokenUser().getUserId());
        permission.setPermissionId(idUtil.nextId());
        return permissionService.save(permission);
    }

    @Override
    public Boolean modifyPermission(SysPermission permission) {
        if (CheckUtil.objIsEmpty(permission) || CheckUtil.objIsEmpty(permission.getPermissionCode(), permission.getPermissionName(), permission.getPermissionId()))
            throw NBException.create(EErrorCode.missingArg);
        SysPermission oldPermission = permissionService.getById(permission.getPermissionId());
        if (CheckUtil.objIsEmpty(oldPermission))
            throw NBException.create(EErrorCode.noData);
        SysPermission temp = this.getPermissionByCode(permission.getPermissionCode());
        if (CheckUtil.objIsNotEmpty(temp) && !permission.getPermissionId().equals(temp.getPermissionId()))
            throw NBException.create(EErrorCode.repeat, "重复的权限编码");
        if (CheckUtil.collectionIsNotEmpty(this.listRolePermissionByPermissionCode(oldPermission.getPermissionCode())))
            throw NBException.create(EErrorCode.beUsed, "无法修改在用的权限");
        permission.setUpdateBy(UserUtil.getTokenUser().getUserId());
        return permissionService.updateById(permission);
    }

    @Override
    public Boolean delPermission(String permissionId) {
        if (CheckUtil.strIsEmpty(permissionId))
            throw NBException.create(EErrorCode.missingArg);
        SysPermission oldPermission = permissionService.getById(permissionId);
        if (CheckUtil.objIsEmpty(oldPermission))
            throw NBException.create(EErrorCode.noData);
        if (CheckUtil.collectionIsNotEmpty(this.listRolePermissionByPermissionCode(oldPermission.getPermissionCode())))
            throw NBException.create(EErrorCode.beUsed, "无法删除在用的权限");
        return permissionService.removeById(permissionId);
    }

    @Override
    public Boolean addMenu(SysMenu menu) {
        if (CheckUtil.objIsEmpty(menu) || CheckUtil.objIsEmpty(menu.getMenuCode(), menu.getMenuName(), menu.getMenuPath()))
            throw NBException.create(EErrorCode.missingArg);
        if (CheckUtil.objIsNotEmpty(this.getMenuByCode(menu.getMenuCode())))
            throw NBException.create(EErrorCode.repeat, "重复的菜单编码");
        menu.setCreateBy(UserUtil.getTokenUser().getUserId());
        menu.setMenuId(idUtil.nextId());
        return menuService.save(menu);
    }

    @Override
    public Boolean modifyMenu(SysMenu menu) {
        if (CheckUtil.objIsEmpty(menu) || CheckUtil.objIsEmpty(menu.getMenuCode(), menu.getMenuName(), menu.getMenuPath(), menu.getMenuId()))
            throw NBException.create(EErrorCode.missingArg);
        SysMenu oldMenu = menuService.getById(menu.getMenuId());
        if (CheckUtil.objIsEmpty(oldMenu))
            throw NBException.create(EErrorCode.noData);
        SysMenu temp = this.getMenuByCode(menu.getMenuCode());
        if (CheckUtil.objIsNotEmpty(temp) && !menu.getMenuId().equals(temp.getMenuId()))
            throw NBException.create(EErrorCode.repeat, "重复的菜单编码");
        if (CheckUtil.collectionIsNotEmpty(this.listRoleMenuByMenuCode(oldMenu.getMenuCode())))
            throw NBException.create(EErrorCode.beUsed, "无法修改在用的菜单");
        menu.setUpdateBy(UserUtil.getTokenUser().getUserId());
        return menuService.updateById(menu);
    }

    @Override
    public Boolean delMenu(String menuId) {
        if (CheckUtil.strIsEmpty(menuId))
            throw NBException.create(EErrorCode.missingArg);
        SysMenu oldMenu = menuService.getById(menuId);
        if (CheckUtil.objIsEmpty(oldMenu))
            throw NBException.create(EErrorCode.noData);
        if (CheckUtil.collectionIsNotEmpty(this.listRoleMenuByMenuCode(oldMenu.getMenuCode())))
            throw NBException.create(EErrorCode.beUsed, "无法删除在用的菜单");
        return menuService.removeById(menuId);
    }

    @Override
    public Boolean addRolePermission(String roleCode, String permissionCode) {
        if (CheckUtil.strIsEmpty(roleCode, permissionCode))
            throw NBException.create(EErrorCode.missingArg);
        if (CheckUtil.objIsNotEmpty(this.getByRoleCodeAndPermissionCode(roleCode, permissionCode)))
            throw NBException.create(EErrorCode.repeat, "该角色已拥有此权限");
        SysRolePermission rolePermission = new SysRolePermission();
        rolePermission.setPermissionCode(permissionCode);
        rolePermission.setRoleCode(roleCode);
        rolePermission.setRolePermissionId(idUtil.nextId());
        rolePermission.setCreateBy(UserUtil.getTokenUser().getUserId());
        return rolePermissionService.save(rolePermission);
    }

    @Override
    public boolean addRolePermissions(String roleCode, String permissionCodes) {
        if (CheckUtil.objIsEmpty(roleCode, permissionCodes))
            throw NBException.create(EErrorCode.missingArg);
        this.delRolePermissionsByRoleCode(roleCode);
        ListUtil.spliteCreate(permissionCodes).forEach(permissionCode -> {
            SysRolePermission rolePermission = new SysRolePermission();
            rolePermission.setPermissionCode(permissionCode);
            rolePermission.setRoleCode(roleCode);
            rolePermission.setRolePermissionId(idUtil.nextId());
            rolePermission.setCreateBy(UserUtil.getTokenUser().getUserId());
            rolePermissionService.save(rolePermission);
        });
        return true;
    }

    @Override
    public boolean delRolePermissionsByRoleCode(String roleCode) {
        if (CheckUtil.objIsEmpty(roleCode))
            throw NBException.create(EErrorCode.missingArg);
        return rolePermissionService.remove(Qw.create().eq(SysRolePermission.ROLE_CODE, roleCode));
    }

    @Override
    public Boolean delRolePermission(String rpId) {
        if (CheckUtil.strIsEmpty(rpId))
            throw NBException.create(EErrorCode.missingArg);
        return rolePermissionService.removeById(rpId);
    }

    @Override
    public Boolean addRoleMenu(String roleCode, String menuCode) {
        if (CheckUtil.strIsEmpty(roleCode, menuCode))
            throw NBException.create(EErrorCode.missingArg);
        if (CheckUtil.objIsNotEmpty(this.getByRoleCodeAndMenuCode(roleCode, menuCode)))
            throw NBException.create(EErrorCode.repeat, "该角色已拥有该菜单");
        SysRoleMenu roleMenu = new SysRoleMenu();
        roleMenu.setRoleCode(roleCode);
        roleMenu.setMenuCode(menuCode);
        roleMenu.setRoleMenuId(idUtil.nextId());
        roleMenu.setCreateBy(UserUtil.getTokenUser().getUserId());
        return roleMenuService.save(roleMenu);
    }

    @Override
    public boolean addRoleMenus(String roleCode, String menuCodes) {
        if (CheckUtil.objIsEmpty(roleCode, menuCodes))
            throw NBException.create(EErrorCode.missingArg);
        ListUtil.spliteCreate(menuCodes).forEach(menuCode -> {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleCode(roleCode);
            roleMenu.setMenuCode(menuCode);
            roleMenu.setRoleMenuId(idUtil.nextId());
            roleMenu.setCreateBy(UserUtil.getTokenUser().getUserId());
            roleMenuService.save(roleMenu);
        });
        return true;
    }

    @Override
    public boolean delRoleMenusByRoleCode(String roleCode) {
        if (CheckUtil.objIsEmpty(roleCode))
            throw NBException.create(EErrorCode.missingArg);
        return roleMenuService.remove(Qw.create().eq(SysRoleMenu.ROLE_CODE, roleCode));
    }

    @Override
    public Boolean delRoleMenu(String rmId) {
        if (CheckUtil.strIsEmpty(rmId))
            throw NBException.create(EErrorCode.missingArg);
        return roleMenuService.removeById(rmId);
    }

    @Override
    public Boolean addUserRole(String userId, String roleCode) {
        if (CheckUtil.strIsEmpty(userId, roleCode))
            throw NBException.create(EErrorCode.missingArg);
        this.roleCodeIfExist(roleCode);
        if (CheckUtil.objIsNotEmpty(this.getUserRoleByUserIdAndRoleCode(userId, roleCode)))
            throw NBException.create(EErrorCode.repeat, "重复的添加");
        if (CheckUtil.objIsNotEmpty(this.getUserRoleByUserIdAndRoleCode(userId, roleCode)))
            throw NBException.create(EErrorCode.repeat, "该用户已经拥有该角色");
        UUserRole userRole = new UUserRole();
        userRole.setUrId(idUtil.nextId());
        userRole.setUserId(userId);
        userRole.setCreateBy(UserUtil.getTokenUser().getUserId());
        userRole.setRoleCode(roleCode);
        return userRoleService.save(userRole);
    }

    private void roleCodeIfExist(String roleCode) {
        if (CheckUtil.objIsEmpty(this.getRoleByCode(roleCode)))
            throw NBException.create(EErrorCode.noData, "角色不存在");
    }

    @Override
    public Boolean addUserRoles(String userId, String roleCodes) {
        if (CheckUtil.strIsEmpty(userId, roleCodes))
            throw NBException.create(EErrorCode.missingArg);
        for (String roleCode : roleCodes.split(",")) {
            this.addUserRole(userId, roleCode);
        }
        return true;
    }

    @Override
    public Boolean delUserRole(String urId) {
        if (CheckUtil.strIsEmpty(urId))
            throw NBException.create(EErrorCode.missingArg);

        //查询用户角色
        UUserRole userRole = userRoleService.getById(urId);
        if (userRole == null) {
            throw NBException.create(EErrorCode.noData, "查无此用户角色" + urId);
        }

        //删除角色
        boolean isDel = userRoleService.removeById(urId);
        if (!isDel) {
            throw NBException.create(EErrorCode.noData, "删除失败" + urId);
        }

        //查询用户角色列表
        List<UUserRole> roleList = userRoleService.list(Qw.create().eq(UUserRole.USER_ID, userRole.getUserId()));
        if (roleList.isEmpty()) {
            //角色为空, 删除用户
            userInfoService.delData(userRole.getUserId());
        }

        return true;
    }

    @Override
    public Boolean delUserRolesByUserId(String userId) {
        if (CheckUtil.strIsEmpty(userId))
            throw NBException.create(EErrorCode.missingArg);
        return userRoleService.remove(Qw.create().eq(UUserRole.USER_ID, userId));
    }

    @Override

    public List<SysRole> listRolesByUserId(String userId) {
        if (CheckUtil.strIsEmpty(userId))
            throw NBException.create(EErrorCode.missingArg);
        List<UUserRole> userRoles = this.listUserRolesByUserId(userId);
        List<SysRole> result = authMapper.getUserRoles(userId);
        result.forEach(role -> {
            userRoles.forEach(ur -> {
                if (ur.getRoleCode().equals(role.getRoleCode()))
                    role.setUserRole(ur);
            });
        });
        return result;
    }

    @Override

    public List<UUserRole> listUserRolesByUserId(String userId) {
        if (CheckUtil.strIsEmpty(userId))
            throw NBException.create(EErrorCode.missingArg);
        return userRoleService.list(Qw.create().eq(UUserRole.USER_ID, userId));
    }

    @Override

    public List<UUserRole> listUserRolesByRoleCode(String roleCode) {
        if (CheckUtil.strIsEmpty(roleCode))
            throw NBException.create(EErrorCode.missingArg);
        return userRoleService.list(Qw.create().eq(UUserRole.ROLE_CODE, roleCode));
    }

    @Override

    public List<SysRolePermission> listRolePermissionByRoleCode(String roleCode) {
        if (CheckUtil.strIsEmpty(roleCode))
            throw NBException.create(EErrorCode.missingArg);
        return rolePermissionService.list(Qw.create().eq(SysRolePermission.ROLE_CODE, roleCode));
    }

    @Override

    public List<SysRolePermission> listRolePermissionByPermissionCode(String permissionCode) {
        if (CheckUtil.strIsEmpty(permissionCode))
            throw NBException.create(EErrorCode.missingArg);
        return rolePermissionService.list(Qw.create().eq(SysRolePermission.PERMISSION_CODE, permissionCode));
    }

    @Override

    public List<SysRoleMenu> listRoleMenuByRoleCode(String roleCode) {
        if (CheckUtil.strIsEmpty(roleCode))
            throw NBException.create(EErrorCode.missingArg);
        return roleMenuService.list(Qw.create().eq(SysRoleMenu.ROLE_CODE, roleCode));
    }

    @Override

    public List<SysRoleMenu> listRoleMenuByMenuCode(String menuCode) {
        if (CheckUtil.strIsEmpty(menuCode))
            throw NBException.create(EErrorCode.missingArg);
        return roleMenuService.list(Qw.create().eq(SysRoleMenu.MENU_CODE, menuCode));
    }

    @Override

    public List<SysPermission> listPermissions(String permissionCode, String permissionName) {
        Qw qw = Qw.create();
        if (CheckUtil.strIsNotEmpty(permissionCode))
            qw.like(SysPermission.PERMISSION_CODE, permissionCode);
        if (CheckUtil.strIsNotEmpty(permissionName))
            qw.like(SysPermission.PERMISSION_NAME, permissionName);
        return permissionService.list(qw);
    }

    @Override

    public List<SysMenu> listMenus(String menuCode, String menuName, String menuPath) {
        Qw qw = Qw.create();
        if (CheckUtil.strIsNotEmpty(menuCode))
            qw.like(SysMenu.MENU_CODE, menuCode);
        if (CheckUtil.strIsNotEmpty(menuName))
            qw.like(SysMenu.MENU_NAME, menuName);
        if (CheckUtil.strIsNotEmpty(menuPath))
            qw.like(SysMenu.MENU_PATH, menuPath);
        return menuService.list(qw);
    }

    @Override

    public UUserRole getUserRoleByUserIdAndRoleCode(String userId, String roleCode) {
        if (CheckUtil.strIsEmpty(userId, roleCode))
            throw NBException.create(EErrorCode.missingArg);
        return userRoleService.getOne(Qw.create().eq(UUserRole.USER_ID, userId).eq(UUserRole.ROLE_CODE, roleCode));
    }

    @Override
    public boolean delUserRoleByUserIdAndRoleCode(String userId, String roleCode) {
        if (CheckUtil.strIsEmpty(userId, roleCode))
            throw NBException.create(EErrorCode.missingArg);
        return userRoleService.remove(Qw.create().eq(UUserRole.USER_ID, userId).eq(UUserRole.ROLE_CODE, roleCode));
    }

    @Override

    public List<SysRoleMenu> listRoleMenu(String roleCode) {
        List<SysRoleMenu> result = this.listRoleMenuByRoleCode(roleCode);
        List<SysMenu> menus = menuService.list(Qw.create().in(SysMenu.MENU_CODE, ListUtil.getPkList(result, SysRoleMenu.MENU_CODE)));
        result.forEach(item -> {
            menus.forEach(menu -> {
                if (item.getMenuCode().equals(menu.getMenuCode()))
                    item.setMenu(menu);
            });
        });
        return result;
    }

    @Override

    public List<SysRolePermission> listRolePermission(String roleCode) {
        List<SysRolePermission> result = this.listRolePermissionByRoleCode(roleCode);
        List<SysPermission> permissions = permissionService.list(Qw.create().in(SysPermission.PERMISSION_CODE, ListUtil.getPkList(result, SysRolePermission.PERMISSION_CODE)));
        result.forEach(item -> {
            permissions.forEach(permission -> {
                if (item.getPermissionCode().equals(permission.getPermissionCode()))
                    item.setPermission(permission);
            });
        });
        return result;
    }

    @Override

    public List<SysMenu> listModuleAndFuncGroups() {
        return menuService.list(Qw.create().in(SysMenu.MENU_TYPE, Lists.newArrayList(EMenuType.module, EMenuType.func_group)));
    }

    @Override

    public SysRolePermission getByRoleCodeAndPermissionCode(String roleCode, String permissionCode) {
        return rolePermissionService.getOne(Qw.create().eq(SysRolePermission.ROLE_CODE, roleCode).eq(SysRolePermission.PERMISSION_CODE, permissionCode));
    }

    @Override

    public SysRoleMenu getByRoleCodeAndMenuCode(String roleCode, String menuCode) {
        return roleMenuService.getOne(Qw.create().eq(SysRoleMenu.ROLE_CODE, roleCode).eq(SysRoleMenu.MENU_CODE, menuCode));
    }

    @Override
    public List<SysMenu> menuTree() {
        List<SysMenu> all = menuService.list();
        List<SysMenu> result = Lists.newArrayList();
        for (SysMenu menu : all) {
            if (EMenuType.module == menu.getMenuType())
                result.add(menu);
        }
        this.genMenuTree(result, all);
        return result;
    }

    private void genMenuTree(List<SysMenu> parentList, List<SysMenu> all) {
        if (CheckUtil.collectionIsEmpty(parentList, all))
            return;

        parentList.forEach(p -> {
            all.forEach(item -> {
                if (p.getMenuCode().equals(item.getParentMenuCode())) {
                    if (CheckUtil.collectionIsEmpty(p.getChildren()))
                        p.setChildren(Lists.newArrayList());
                    p.getChildren().add(item);
                }
            });
            this.genMenuTree(p.getChildren(), all);
        });
    }


}
