package cn.hao.nb.cloud.auth.service;

import cn.hao.nb.cloud.auth.entity.*;

import java.util.List;

/**
 * @Auther: sunhao
 * @Date: 2019-05-16 13:53
 * @Description:
 */
public interface IAuthService {
    /**
     * 检查角色是否已使用,使用中的角色无法修改roleCode
     *
     * @param roleCode
     * @return
     */
    Boolean isRoleInUse(String roleCode);

    /**
     * 添加角色
     *
     * @param role
     * @return
     */
    Boolean addRole(SysRole role);

    /**
     * 修改角色
     *
     * @param role
     * @return
     */
    Boolean modifyRole(SysRole role);

    /**
     * 列表查询角色
     *
     * @return
     */
    List<SysRole> listRoles();

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    Boolean delRole(Long roleId);

    Boolean revertRole(Long roleId);

    SysRole getRoleByCode(String roleCode);

    SysPermission getPermissionByCode(String permissionCode);

    SysMenu getMenuByCode(String menuCode);

    Boolean addPermission(SysPermission permission);

    Boolean modifyPermission(SysPermission permission);

    Boolean delPermission(Long permissionId);

    Boolean addMenu(SysMenu menu);

    Boolean modifyMenu(SysMenu menu);

    Boolean delMenu(Long menuId);

    Boolean addRolePermission(String roleCode, String permissionCode);

    boolean addRolePermissions(String roleCode, String permissionCodes);

    boolean delRolePermissionsByRoleCode(String roleCode);

    Boolean delRolePermission(Long rpId);

    Boolean addRoleMenu(String roleCode, String menuCode);

    boolean addRoleMenus(String roleCode, String menuCodes);

    boolean delRoleMenusByRoleCode(String roleCode);

    Boolean delRoleMenu(Long rmId);

    /**
     * 添加用户角色
     *
     * @param userId
     * @param roleId
     * @return
     */
    Boolean addUserRole(Long userId, String roleCode);

    /**
     * 添加用户角色
     *
     * @param userId
     * @param roleId
     * @return
     */
    Boolean addUserRoles(Long userId, String roleCodes);

    /**
     * 删除用户角色
     *
     * @param userId
     * @param roleId
     * @return
     */
    Boolean delUserRole(Long urId);

    Boolean delUserRolesByUserId(Long userId);

    /**
     * 查询用户角色
     *
     * @param userId
     * @return
     */
    List<SysRole> listRolesByUserId(Long userId);

    /**
     * 根据用户id获取用户角色关系
     *
     * @param userId
     * @return
     */
    List<UUserRole> listUserRolesByUserId(Long userId);

    /**
     * 根据角色编码获取用户角色关系
     *
     * @param roleCode
     * @return
     */
    List<UUserRole> listUserRolesByRoleCode(String roleCode);

    /**
     * 根据角色编码获取角色权限关系
     *
     * @param roleCode
     * @return
     */
    List<SysRolePermission> listRolePermissionByRoleCode(String roleCode);

    /**
     * 根据权限编码获取角色权限关系
     *
     * @param permissionCode
     * @return
     */
    List<SysRolePermission> listRolePermissionByPermissionCode(String permissionCode);

    /**
     * 根据角色编码获取菜单角色关系
     *
     * @param roleCode
     * @return
     */
    List<SysRoleMenu> listRoleMenuByRoleCode(String roleCode);

    /**
     * 根据菜单编码获取菜单角色关系
     *
     * @param menuCode
     * @return
     */
    List<SysRoleMenu> listRoleMenuByMenuCode(String menuCode);

    List<SysPermission> listPermissions(String permissionCode, String permissionName);

    List<SysMenu> listMenus(String menuCode, String menuName, String menuPath);

    UUserRole getUserRoleByUserIdAndRoleCode(Long userId, String roleCode);

    boolean delUserRoleByUserIdAndRoleCode(Long userId, String roleCode);

    List<SysRoleMenu> listRoleMenu(String roleCode);

    List<SysRolePermission> listRolePermission(String roleCode);

    List<SysMenu> listModuleAndFuncGroups();

    SysRolePermission getByRoleCodeAndPermissionCode(String roleCode, String permissionCode);

    SysRoleMenu getByRoleCodeAndMenuCode(String roleCode, String menuCode);

    List<SysMenu> menuTree();

}
