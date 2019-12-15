package cn.hao.nb.cloud.auth.controller;

import cn.hao.nb.cloud.auth.entity.SysMenu;
import cn.hao.nb.cloud.auth.entity.SysRole;
import cn.hao.nb.cloud.auth.entity.UUserRole;
import cn.hao.nb.cloud.auth.service.ISysMenuService;
import cn.hao.nb.cloud.auth.service.ISysRoleService;
import cn.hao.nb.cloud.auth.service.IUUserRoleService;
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

}
