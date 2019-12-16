package cn.hao.nb.cloud.auth.controller;

import cn.hao.nb.cloud.auth.entity.*;
import cn.hao.nb.cloud.auth.service.*;
import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Rv;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: hao
 * @Date: 2019-12-14 22:04
 * @Description:
 */
@Api(description = "权限接口")
@Slf4j
@RestController
@RequestMapping("/pms")
public class PmsController {

    @Autowired
    ISysRoleService roleService;
    @Autowired
    IUUserRoleService userRoleService;
    @Autowired
    ISysRoleService iSysRoleService;
    @Autowired
    IUUserRoleService iUUserRoleService;
    @Autowired
    ISysMenuService iSysMenuService;
    @Autowired
    ISysPermissionService iSysPermissionService;
    @Autowired
    ISysRoleMenuService iSysRoleMenuService;
    @Autowired
    ISysRolePermissionService iSysRolePermissionService;

    /********************************** 角色 **********************************/

    @ApiOperation(value = "添加角色 ", notes = "添加角色 ")
    @PostMapping(value = "/sysRole/add")
    public Rv addSysRole(SysRole data) {
        return Rv.getInstance(iSysRoleService.addData(data));
    }

    @ApiOperation(value = "修改角色 ", notes = "修改角色 ")
    @PostMapping(value = "/sysRole/modify")
    public Rv modifySysRole(SysRole data) {
        return Rv.getInstance(iSysRoleService.incrementModifyData(data));
    }

    @ApiOperation(value = "删除角色 ", notes = "删除角色 ")
    @PostMapping(value = "/sysRole/del/{id}")
    public Rv delSysRole(@ApiParam(name = "id", value = "角色 id") @PathVariable String id) {
        return Rv.getInstance(iSysRoleService.delData(id));
    }

    @ApiOperation(value = "查询角色 ", notes = "查询角色 ")
    @GetMapping(value = "/sysRole/getById/{id}")
    public Rv getSysRoleById(@ApiParam(name = "id", value = "角色 id") @PathVariable String id) {
        return Rv.getInstance(iSysRoleService.getDetail(id));
    }

    @ApiOperation(value = "查询角色 ", notes = "查询角色 ")
    @GetMapping(value = "/sysRole/getByRoleCode/{roleCode}")
    public Rv getSysRoleByRoleCode(@ApiParam(name = "roleCode", value = "角色 roleCode") @PathVariable String roleCode) {
        return Rv.getInstance(iSysRoleService.getByRoleCode(roleCode));
    }

    @ApiOperation(value = "分页查询角色 ", notes = "分页查询角色 ")
    @GetMapping(value = "/sysRole/page")
    public Rv pageSysRole(Pg pg, SysRole.SearchParams searchParams) {
        return Rv.getInstance(iSysRoleService.pageData(pg, searchParams));
    }

    @ApiOperation(value = "列表查询角色 ", notes = "列表查询角色 ")
    @GetMapping(value = "/sysRole/list")
    public Rv listSysRole(SysRole.SearchParams searchParams) {
        return Rv.getInstance(iSysRoleService.listData(searchParams));
    }

    @ApiOperation(value = "根据用户id列表查询角色 ")
    @GetMapping(value = "/sysRole/listByUserId/{userId}")
    public Rv listSysRoleByUserId(@PathVariable String userId) {
        return Rv.getInstance(iSysRoleService.listByUserId(userId));
    }

    @ApiOperation(value = "分页查询角色 (map数据)", notes = "列表查询角色 (map数据)")
    @GetMapping(value = "/sysRole/pageMap")
    public Rv pageMapSysRole(Pg pg, SysRole.SearchParams searchParams) {
        return Rv.getInstance(iSysRoleService.pageMapData(pg, searchParams));
    }

    /********************************** 用户角色 **********************************/

    @ApiOperation(value = "添加用户角色 ", notes = "添加用户角色 ")
    @PostMapping(value = "/uUserRole/add")
    public Rv addUUserRole(UUserRole data) {
        return Rv.getInstance(iUUserRoleService.addData(data));
    }

    @ApiOperation(value = "添加用户角色列表 ")
    @PostMapping(value = "/uUserRole/addList")
    public Rv addUUserRole(String userId, String roleCodes) {
        return Rv.getInstance(iUUserRoleService.addUserRoles(userId, roleCodes));
    }

    @ApiOperation(value = "修改用户角色 ", notes = "修改用户角色 ")
    @PostMapping(value = "/uUserRole/modify")
    public Rv modifyUUserRole(UUserRole data) {
        return Rv.getInstance(iUUserRoleService.incrementModifyData(data));
    }

    @ApiOperation(value = "删除用户角色 ", notes = "删除用户角色 ")
    @PostMapping(value = "/uUserRole/del/{id}")
    public Rv delUUserRole(@ApiParam(name = "id", value = "用户角色 id") @PathVariable String id) {
        return Rv.getInstance(iUUserRoleService.delData(id));
    }

    @ApiOperation(value = "查询用户角色 ", notes = "查询用户角色 ")
    @GetMapping(value = "/uUserRole/getById/{id}")
    public Rv getUUserRoleById(@ApiParam(name = "id", value = "用户角色 id") @PathVariable String id) {
        return Rv.getInstance(iUUserRoleService.getDetail(id));
    }

    @ApiOperation(value = "分页查询用户角色 ", notes = "分页查询用户角色 ")
    @GetMapping(value = "/uUserRole/page")
    public Rv pageUUserRole(Pg pg, UUserRole.SearchParams searchParams) {
        return Rv.getInstance(iUUserRoleService.pageData(pg, searchParams));
    }

    @ApiOperation(value = "列表查询用户角色 ", notes = "列表查询用户角色 ")
    @GetMapping(value = "/uUserRole/list")
    public Rv listUUserRole(UUserRole.SearchParams searchParams) {
        return Rv.getInstance(iUUserRoleService.listData(searchParams));
    }

    @ApiOperation(value = "分页查询用户角色 (map数据)", notes = "列表查询用户角色 (map数据)")
    @GetMapping(value = "/uUserRole/pageMap")
    public Rv pageMapUUserRole(Pg pg, UUserRole.SearchParams searchParams) {
        return Rv.getInstance(iUUserRoleService.pageMapData(pg, searchParams));
    }


    /********************************** 菜单 **********************************/


    @ApiOperation(value = "添加菜单表 ", notes = "添加菜单表 ")
    @PostMapping(value = "/sysMenu/add")
    public Rv addSysMenu(SysMenu data) {
        return Rv.getInstance(iSysMenuService.addData(data));
    }

    @ApiOperation(value = "修改菜单表 ", notes = "修改菜单表 ")
    @PostMapping(value = "/sysMenu/modify")
    public Rv modifySysMenu(SysMenu data) {
        return Rv.getInstance(iSysMenuService.incrementModifyData(data));
    }

    @ApiOperation(value = "删除菜单表 ", notes = "删除菜单表 ")
    @PostMapping(value = "/sysMenu/del/{id}")
    public Rv delSysMenu(@ApiParam(name = "id", value = "菜单表 id") @PathVariable String id) {
        return Rv.getInstance(iSysMenuService.delData(id));
    }

    @ApiOperation(value = "查询菜单表 ", notes = "查询菜单表 ")
    @GetMapping(value = "/sysMenu/getById/{id}")
    public Rv getSysMenuById(@ApiParam(name = "id", value = "菜单表 id") @PathVariable String id) {
        return Rv.getInstance(iSysMenuService.getDetail(id));
    }

    @ApiOperation(value = "分页查询菜单表 ", notes = "分页查询菜单表 ")
    @GetMapping(value = "/sysMenu/page")
    public Rv pageSysMenu(Pg pg, SysMenu.SearchParams searchParams) {
        return Rv.getInstance(iSysMenuService.pageData(pg, searchParams));
    }

    @ApiOperation(value = "列表查询菜单表 ", notes = "列表查询菜单表 ")
    @GetMapping(value = "/sysMenu/list")
    public Rv listSysMenu(SysMenu.SearchParams searchParams) {
        return Rv.getInstance(iSysMenuService.listData(searchParams));
    }

    @ApiOperation(value = "根据角色编码列表查询菜单表 ")
    @GetMapping(value = "/sysMenu/listByRoleCode/{roleCode}")
    public Rv listSysMenuByRoleCode(@PathVariable String roleCode) {
        return Rv.getInstance(iSysMenuService.listByRoleCode(roleCode));
    }

    @ApiOperation(value = "分页查询菜单表 (map数据)", notes = "列表查询菜单表 (map数据)")
    @GetMapping(value = "/sysMenu/pageMap")
    public Rv pageMapSysMenu(Pg pg, SysMenu.SearchParams searchParams) {
        return Rv.getInstance(iSysMenuService.pageMapData(pg, searchParams));
    }

    @ApiOperation(value = "列表查询下级菜单")
    @GetMapping("/sysMenu/getDisMenu/{menuCode}")
    public Rv getDisMenu(@PathVariable String menuCode) {
        return Rv.getInstance(iSysMenuService.getDisMenu(menuCode));
    }

    @ApiOperation(value = "菜单树")
    @GetMapping("/sysMenu/tree")
    public Rv getMenuTree() {
        return Rv.getInstance(iSysMenuService.menuTree());
    }


    /********************************** 权限 **********************************/


    @ApiOperation(value = "添加权限表 ", notes = "添加权限表 ")
    @PostMapping(value = "/sysPermission/add")
    public Rv addSysPermission(SysPermission data) {
        return Rv.getInstance(iSysPermissionService.addData(data));
    }

    @ApiOperation(value = "修改权限表 ", notes = "修改权限表 ")
    @PostMapping(value = "/sysPermission/modify")
    public Rv modifySysPermission(SysPermission data) {
        return Rv.getInstance(iSysPermissionService.incrementModifyData(data));
    }

    @ApiOperation(value = "删除权限表 ", notes = "删除权限表 ")
    @PostMapping(value = "/sysPermission/del/{id}")
    public Rv delSysPermission(@ApiParam(name = "id", value = "权限表 id") @PathVariable String id) {
        return Rv.getInstance(iSysPermissionService.delData(id));
    }

    @ApiOperation(value = "查询权限表 ", notes = "查询权限表 ")
    @GetMapping(value = "/sysPermission/getById/{id}")
    public Rv getSysPermissionById(@ApiParam(name = "id", value = "权限表 id") @PathVariable String id) {
        return Rv.getInstance(iSysPermissionService.getDetail(id));
    }

    @ApiOperation(value = "分页查询权限表 ", notes = "分页查询权限表 ")
    @GetMapping(value = "/sysPermission/page")
    public Rv pageSysPermission(Pg pg, SysPermission.SearchParams searchParams) {
        return Rv.getInstance(iSysPermissionService.pageData(pg, searchParams));
    }

    @ApiOperation(value = "列表查询权限表 ", notes = "列表查询权限表 ")
    @GetMapping(value = "/sysPermission/list")
    public Rv listSysPermission(SysPermission.SearchParams searchParams) {
        return Rv.getInstance(iSysPermissionService.listData(searchParams));
    }

    @ApiOperation(value = "列表查询权限表 ", notes = "列表查询权限表 ")
    @GetMapping(value = "/sysPermission/listByRoleCode/{roleCode}")
    public Rv listSysPermissionByRoleCode(@PathVariable String roleCode) {
        return Rv.getInstance(iSysPermissionService.listByRoleCode(roleCode));
    }

    @ApiOperation(value = "分页查询权限表 (map数据)", notes = "列表查询权限表 (map数据)")
    @GetMapping(value = "/sysPermission/pageMap")
    public Rv pageMapSysPermission(Pg pg, SysPermission.SearchParams searchParams) {
        return Rv.getInstance(iSysPermissionService.pageMapData(pg, searchParams));
    }


    /********************************** 角色菜单 **********************************/


    @ApiOperation(value = "添加角色菜单 ", notes = "添加角色菜单 ")
    @PostMapping(value = "/sysRoleMenu/add")
    public Rv addSysRoleMenu(SysRoleMenu data) {
        return Rv.getInstance(iSysRoleMenuService.addData(data));
    }

    @ApiOperation(value = "添加角色菜单列表 ", notes = "添加角色菜单列表 ")
    @PostMapping(value = "/sysRoleMenu/addList")
    public Rv addSysRoleMenuList(String roleCode, String menuCodes) {
        return Rv.getInstance(iSysRoleMenuService.addRoleMenus(roleCode, menuCodes));
    }

    @ApiOperation(value = "修改角色菜单 ", notes = "修改角色菜单 ")
    @PostMapping(value = "/sysRoleMenu/modify")
    public Rv modifySysRoleMenu(SysRoleMenu data) {
        return Rv.getInstance(iSysRoleMenuService.incrementModifyData(data));
    }

    @ApiOperation(value = "删除角色菜单 ", notes = "删除角色菜单 ")
    @PostMapping(value = "/sysRoleMenu/del/{id}")
    public Rv delSysRoleMenu(@ApiParam(name = "id", value = "角色菜单 id") @PathVariable String id) {
        return Rv.getInstance(iSysRoleMenuService.delData(id));
    }

    @ApiOperation(value = "查询角色菜单 ", notes = "查询角色菜单 ")
    @GetMapping(value = "/sysRoleMenu/getById/{id}")
    public Rv getSysRoleMenuById(@ApiParam(name = "id", value = "角色菜单 id") @PathVariable String id) {
        return Rv.getInstance(iSysRoleMenuService.getDetail(id));
    }

    @ApiOperation(value = "分页查询角色菜单 ", notes = "分页查询角色菜单 ")
    @GetMapping(value = "/sysRoleMenu/page")
    public Rv pageSysRoleMenu(Pg pg, SysRoleMenu.SearchParams searchParams) {
        return Rv.getInstance(iSysRoleMenuService.pageData(pg, searchParams));
    }

    @ApiOperation(value = "列表查询角色菜单 ", notes = "列表查询角色菜单 ")
    @GetMapping(value = "/sysRoleMenu/list")
    public Rv listSysRoleMenu(SysRoleMenu.SearchParams searchParams) {
        return Rv.getInstance(iSysRoleMenuService.listData(searchParams));
    }

    @ApiOperation(value = "分页查询角色菜单 (map数据)", notes = "列表查询角色菜单 (map数据)")
    @GetMapping(value = "/sysRoleMenu/pageMap")
    public Rv pageMapSysRoleMenu(Pg pg, SysRoleMenu.SearchParams searchParams) {
        return Rv.getInstance(iSysRoleMenuService.pageMapData(pg, searchParams));
    }


    /********************************** 角色菜单 **********************************/


    @ApiOperation(value = "添加角色权限 ", notes = "添加角色权限 ")
    @PostMapping(value = "/sysRolePermission/add")
    public Rv addSysRolePermission(SysRolePermission data) {
        return Rv.getInstance(iSysRolePermissionService.addData(data));
    }

    @ApiOperation(value = "添加角色权限列表 ")
    @PostMapping(value = "/sysRolePermission/addList")
    public Rv addSysRolePermissionList(String roleCode, String permissionCodes) {
        return Rv.getInstance(iSysRolePermissionService.addRolePermissions(roleCode, permissionCodes));
    }

    @ApiOperation(value = "删除角色权限 ", notes = "删除角色权限 ")
    @PostMapping(value = "/sysRolePermission/del/{id}")
    public Rv delSysRolePermission(@ApiParam(name = "id", value = "角色权限 id") @PathVariable String id) {
        return Rv.getInstance(iSysRolePermissionService.delData(id));
    }

    @ApiOperation(value = "查询角色权限 ", notes = "查询角色权限 ")
    @GetMapping(value = "/sysRolePermission/getById/{id}")
    public Rv getSysRolePermissionById(@ApiParam(name = "id", value = "角色权限 id") @PathVariable String id) {
        return Rv.getInstance(iSysRolePermissionService.getDetail(id));
    }

    @ApiOperation(value = "分页查询角色权限 ", notes = "分页查询角色权限 ")
    @GetMapping(value = "/sysRolePermission/page")
    public Rv pageSysRolePermission(Pg pg, SysRolePermission.SearchParams searchParams) {
        return Rv.getInstance(iSysRolePermissionService.pageData(pg, searchParams));
    }

    @ApiOperation(value = "列表查询角色权限 ", notes = "列表查询角色权限 ")
    @GetMapping(value = "/sysRolePermission/list")
    public Rv listSysRolePermission(SysRolePermission.SearchParams searchParams) {
        return Rv.getInstance(iSysRolePermissionService.listData(searchParams));
    }

    @ApiOperation(value = "分页查询角色权限 (map数据)", notes = "列表查询角色权限 (map数据)")
    @GetMapping(value = "/sysRolePermission/pageMap")
    public Rv pageMapSysRolePermission(Pg pg, SysRolePermission.SearchParams searchParams) {
        return Rv.getInstance(iSysRolePermissionService.pageMapData(pg, searchParams));
    }

}
