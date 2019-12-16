package cn.hao.nb.cloud.auth.service;

import cn.hao.nb.cloud.auth.entity.SysPermission;
import cn.hao.nb.cloud.common.entity.Pg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限表  服务类11
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
public interface ISysPermissionService extends IService<SysPermission> {
    /**
     * 添加数据
     * @param data
     * @return
     */
    SysPermission addData(SysPermission data);

    /**
     * 增量更新数据
     * @param data
     * @return
     */
    boolean incrementModifyData(SysPermission data);

    /**
     * 全量更新数据
     *
     * @param data
     * @return
     */
    boolean totalAmountModifyData(SysPermission data);

    /**
     * 删除数据
     * @param id
     * @return
     */
    boolean delData(String id);

    /**
     * 查询详情
     * @param id
     * @return
     */
    SysPermission getDetail(String id);

    /**
     * 分页查询数据
     * @param pg
     * @param searchParams
     * @return
     */
    IPage<SysPermission> pageData(Pg pg, SysPermission.SearchParams searchParams);

    /**
     * 列表查询数据
     * @param searchParams
     * @return
     */
    List<SysPermission> listData(SysPermission.SearchParams searchParams);

    List<SysPermission> listByRoleCode(String roleCode);

    /**
     * 分页查询Map数据
     * @param pg
     * @param searchParams
     * @return
     */
    IPage
            <Map
                    <String, Object>> pageMapData(Pg pg, SysPermission.SearchParams searchParams);

    /**
     * 处理返回值
     * @param data
     * @return
     */
    SysPermission prepareReturnModel(SysPermission data);

    /**
     * 处理返回值
     * @param page
     * @return
     */
    IPage<SysPermission> prepareReturnModel(IPage<SysPermission> page);

    /**
     * 处理返回值
     * @param list
     * @return
     */
    List<SysPermission> prepareReturnModel(List<SysPermission> list);

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
    void validData(SysPermission data);

}
/*

import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Rv;
import com.fgzy.mc.core.entity.SysPermission;
import com.fgzy.mc.core.service.ISysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@Autowired
ISysPermissionService iSysPermissionService;

@ApiOperation(value = "添加权限表 ", notes = "添加权限表 ")
@PostMapping(value = "/sysPermission/add")
public Rv addSysPermission(SysPermission data) {
return Rv.getInstance(iSysPermissionService.addData(data));
}

@ApiOperation(value = "修改权限表 ", notes = "修改权限表 ")
@PostMapping(value = "/sysPermission/modify")
public Rv modifySysPermission(SysPermission data) {
return Rv.getInstance(iSysPermissionService.modifyData(data));
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
public Rv pageSysPermission(Pg pg,SysPermission.SearchParams searchParams) {
return Rv.getInstance(iSysPermissionService.pageData(pg,searchParams));
}

@ApiOperation(value = "列表查询权限表 ", notes = "列表查询权限表 ")
@GetMapping(value = "/sysPermission/list")
public Rv listSysPermission(SysPermission.SearchParams searchParams) {
return Rv.getInstance(iSysPermissionService.listData(searchParams));
}

@ApiOperation(value = "分页查询权限表 (map数据)", notes = "列表查询权限表 (map数据)")
@GetMapping(value = "/sysPermission/pageMap")
public Rv pageMapSysPermission(Pg pg,SysPermission.SearchParams searchParams) {
return Rv.getInstance(iSysPermissionService.pageMapData(pg,searchParams));
}


*/