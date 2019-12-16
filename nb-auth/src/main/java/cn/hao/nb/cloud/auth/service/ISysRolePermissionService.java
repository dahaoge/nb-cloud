package cn.hao.nb.cloud.auth.service;

import cn.hao.nb.cloud.auth.entity.SysRolePermission;
import cn.hao.nb.cloud.common.entity.Pg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色权限  服务类11
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
public interface ISysRolePermissionService extends IService<SysRolePermission> {
    /**
     * 添加数据
     * @param data
     * @return
     */
    SysRolePermission addData(SysRolePermission data);

    SysRolePermission addRolePermission(String roleCode, String permissionCode);

    boolean addRolePermissions(String roleCode, String permissionCodes);

    /**
     * 增量更新数据
     * @param data
     * @return
     */
    boolean incrementModifyData(SysRolePermission data);

    /**
     * 全量更新数据
     *
     * @param data
     * @return
     */
    boolean totalAmountModifyData(SysRolePermission data);

    /**
     * 删除数据
     * @param id
     * @return
     */
    boolean delData(String id);

    boolean delByRoleCode(String roleCode);

    boolean delByPermissionCode(String permissionCode);

    /**
     * 查询详情
     * @param id
     * @return
     */
    SysRolePermission getDetail(String id);

    /**
     * 分页查询数据
     * @param pg
     * @param searchParams
     * @return
     */
    IPage<SysRolePermission> pageData(Pg pg, SysRolePermission.SearchParams searchParams);

    /**
     * 列表查询数据
     * @param searchParams
     * @return
     */
    List<SysRolePermission> listData(SysRolePermission.SearchParams searchParams);

    /**
     * 分页查询Map数据
     * @param pg
     * @param searchParams
     * @return
     */
    IPage
            <Map
                    <String, Object>> pageMapData(Pg pg, SysRolePermission.SearchParams searchParams);

    /**
     * 处理返回值
     * @param data
     * @return
     */
    SysRolePermission prepareReturnModel(SysRolePermission data);

    /**
     * 处理返回值
     * @param page
     * @return
     */
    IPage<SysRolePermission> prepareReturnModel(IPage<SysRolePermission> page);

    /**
     * 处理返回值
     * @param list
     * @return
     */
    List<SysRolePermission> prepareReturnModel(List<SysRolePermission> list);

    /**
     * 处理返回值
     * @param page
     * @return
     */
    IPage
            <Map
                    <String, Object>> prepareReturnMapModel(IPage
                                                                    <Map
                                                                            <String, Object>> page);

    /**
     * 添加/修改数据前校验数据有效性(强制抛出异常)
     * 如果不需要抛出异常请不用调用该服务
     * @param data
     */
    void validData(SysRolePermission data);

}
/*

import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Rv;
import com.fgzy.mc.core.entity.SysRolePermission;
import com.fgzy.mc.core.service.ISysRolePermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@Autowired
ISysRolePermissionService iSysRolePermissionService;

@ApiOperation(value = "添加角色权限 ", notes = "添加角色权限 ")
@PostMapping(value = "/sysRolePermission/add")
public Rv addSysRolePermission(SysRolePermission data) {
return Rv.getInstance(iSysRolePermissionService.addData(data));
}

@ApiOperation(value = "修改角色权限 ", notes = "修改角色权限 ")
@PostMapping(value = "/sysRolePermission/modify")
public Rv modifySysRolePermission(SysRolePermission data) {
return Rv.getInstance(iSysRolePermissionService.modifyData(data));
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
public Rv pageSysRolePermission(Pg pg,SysRolePermission.SearchParams searchParams) {
return Rv.getInstance(iSysRolePermissionService.pageData(pg,searchParams));
}

@ApiOperation(value = "列表查询角色权限 ", notes = "列表查询角色权限 ")
@GetMapping(value = "/sysRolePermission/list")
public Rv listSysRolePermission(SysRolePermission.SearchParams searchParams) {
return Rv.getInstance(iSysRolePermissionService.listData(searchParams));
}

@ApiOperation(value = "分页查询角色权限 (map数据)", notes = "列表查询角色权限 (map数据)")
@GetMapping(value = "/sysRolePermission/pageMap")
public Rv pageMapSysRolePermission(Pg pg,SysRolePermission.SearchParams searchParams) {
return Rv.getInstance(iSysRolePermissionService.pageMapData(pg,searchParams));
}


*/