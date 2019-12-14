package cn.hao.nb.cloud.auth.service;

import cn.hao.nb.cloud.auth.entity.SysRoleMenu;
import cn.hao.nb.cloud.common.entity.Pg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色菜单  服务类11
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {
    /**
     * 添加数据
     * @param data
     * @return
     */
    SysRoleMenu addData(SysRoleMenu data);

    /**
     * 增量更新数据
     * @param data
     * @return
     */
    boolean incrementModifyData(SysRoleMenu data);

    /**
     * 全量更新数据
     *
     * @param data
     * @return
     */
    boolean totalAmountModifyData(SysRoleMenu data);

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
    SysRoleMenu getDetail(String id);

    /**
     * 分页查询数据
     * @param pg
     * @param searchParams
     * @return
     */
    IPage<SysRoleMenu> pageData(Pg pg, SysRoleMenu.SearchParams searchParams);

    /**
     * 列表查询数据
     * @param searchParams
     * @return
     */
    List<SysRoleMenu> listData(SysRoleMenu.SearchParams searchParams);

    /**
     * 分页查询Map数据
     * @param pg
     * @param searchParams
     * @return
     */
    IPage
            <Map
                    <String, Object>> pageMapData(Pg pg, SysRoleMenu.SearchParams searchParams);

    /**
     * 处理返回值
     * @param data
     * @return
     */
    SysRoleMenu prepareReturnModel(SysRoleMenu data);

    /**
     * 处理返回值
     * @param page
     * @return
     */
    IPage<SysRoleMenu> prepareReturnModel(IPage<SysRoleMenu> page);

    /**
     * 处理返回值
     * @param list
     * @return
     */
    List<SysRoleMenu> prepareReturnModel(List<SysRoleMenu> list);

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
    void validData(SysRoleMenu data);

}
/*

import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Rv;
import com.fgzy.mc.core.entity.SysRoleMenu;
import com.fgzy.mc.core.service.ISysRoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@Autowired
ISysRoleMenuService iSysRoleMenuService;

@ApiOperation(value = "添加角色菜单 ", notes = "添加角色菜单 ")
@PostMapping(value = "/sysRoleMenu/add")
public Rv addSysRoleMenu(SysRoleMenu data) {
return Rv.getInstance(iSysRoleMenuService.addData(data));
}

@ApiOperation(value = "修改角色菜单 ", notes = "修改角色菜单 ")
@PostMapping(value = "/sysRoleMenu/modify")
public Rv modifySysRoleMenu(SysRoleMenu data) {
return Rv.getInstance(iSysRoleMenuService.modifyData(data));
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
public Rv pageSysRoleMenu(Pg pg,SysRoleMenu.SearchParams searchParams) {
return Rv.getInstance(iSysRoleMenuService.pageData(pg,searchParams));
}

@ApiOperation(value = "列表查询角色菜单 ", notes = "列表查询角色菜单 ")
@GetMapping(value = "/sysRoleMenu/list")
public Rv listSysRoleMenu(SysRoleMenu.SearchParams searchParams) {
return Rv.getInstance(iSysRoleMenuService.listData(searchParams));
}

@ApiOperation(value = "分页查询角色菜单 (map数据)", notes = "列表查询角色菜单 (map数据)")
@GetMapping(value = "/sysRoleMenu/pageMap")
public Rv pageMapSysRoleMenu(Pg pg,SysRoleMenu.SearchParams searchParams) {
return Rv.getInstance(iSysRoleMenuService.pageMapData(pg,searchParams));
}


*/