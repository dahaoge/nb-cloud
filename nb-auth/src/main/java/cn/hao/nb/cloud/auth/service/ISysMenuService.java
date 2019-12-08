package cn.hao.nb.cloud.auth.service;

import cn.hao.nb.cloud.auth.entity.SysMenu;
import cn.hao.nb.cloud.common.entity.Pg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单表  服务类11
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
public interface ISysMenuService extends IService<SysMenu> {
    /**
     * 添加数据
     * @param data
     * @return
     */
    SysMenu addData(SysMenu data);

    /**
     * 修改数据
     * @param data
     * @return
     */
    boolean modifyData(SysMenu data);

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
    SysMenu getDetail(String id);

    /**
     * 分页查询数据
     * @param pg
     * @param searchParams
     * @return
     */
    IPage<SysMenu> pageData(Pg pg, SysMenu.SearchParams searchParams);

    /**
     * 列表查询数据
     * @param searchParams
     * @return
     */
    List<SysMenu> listData(SysMenu.SearchParams searchParams);

    /**
     * 分页查询Map数据
     * @param pg
     * @param searchParams
     * @return
     */
    IPage
            <Map
                    <String, Object>> pageMapData(Pg pg, SysMenu.SearchParams searchParams);

    /**
     * 处理返回值
     * @param data
     * @return
     */
    SysMenu prepareReturnModel(SysMenu data);

    /**
     * 处理返回值
     * @param page
     * @return
     */
    IPage<SysMenu> prepareReturnModel(IPage<SysMenu> page);

    /**
     * 处理返回值
     * @param list
     * @return
     */
    List<SysMenu> prepareReturnModel(List<SysMenu> list);

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
    void validData(SysMenu data);

}
/*

import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Rv;
import com.fgzy.mc.core.entity.SysMenu;
import com.fgzy.mc.core.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@Autowired
ISysMenuService iSysMenuService;

@ApiOperation(value = "添加菜单表 ", notes = "添加菜单表 ")
@PostMapping(value = "/sysMenu/add")
public Rv addSysMenu(SysMenu data) {
return Rv.getInstance(iSysMenuService.addData(data));
}

@ApiOperation(value = "修改菜单表 ", notes = "修改菜单表 ")
@PostMapping(value = "/sysMenu/modify")
public Rv modifySysMenu(SysMenu data) {
return Rv.getInstance(iSysMenuService.modifyData(data));
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
public Rv pageSysMenu(Pg pg,SysMenu.SearchParams searchParams) {
return Rv.getInstance(iSysMenuService.pageData(pg,searchParams));
}

@ApiOperation(value = "列表查询菜单表 ", notes = "列表查询菜单表 ")
@GetMapping(value = "/sysMenu/list")
public Rv listSysMenu(SysMenu.SearchParams searchParams) {
return Rv.getInstance(iSysMenuService.listData(searchParams));
}

@ApiOperation(value = "分页查询菜单表 (map数据)", notes = "列表查询菜单表 (map数据)")
@GetMapping(value = "/sysMenu/pageMap")
public Rv pageMapSysMenu(Pg pg,SysMenu.SearchParams searchParams) {
return Rv.getInstance(iSysMenuService.pageMapData(pg,searchParams));
}


*/