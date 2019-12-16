package cn.hao.nb.cloud.auth.service;

import cn.hao.nb.cloud.auth.entity.SysRole;
import cn.hao.nb.cloud.common.entity.Pg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色  服务类11
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
public interface ISysRoleService extends IService<SysRole> {
    /**
     * 添加数据
     * @param data
     * @return
     */
    SysRole addData(SysRole data);

    /**
     * 增量更新数据
     * @param data
     * @return
     */
    boolean incrementModifyData(SysRole data);

    /**
     * 全量更新数据
     *
     * @param data
     * @return
     */
    boolean totalAmountModifyData(SysRole data);

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
    SysRole getDetail(String id);

    /**
     * 根据编码获取详情
     *
     * @param roleCode
     * @return
     */
    SysRole getByRoleCode(String roleCode);

    /**
     * 分页查询数据
     * @param pg
     * @param searchParams
     * @return
     */
    IPage<SysRole> pageData(Pg pg, SysRole.SearchParams searchParams);

    /**
     * 列表查询数据
     * @param searchParams
     * @return
     */
    List<SysRole> listData(SysRole.SearchParams searchParams);

    List<SysRole> listByUserId(String userId);

    /**
     * 分页查询Map数据
     * @param pg
     * @param searchParams
     * @return
     */
    IPage
            <Map
                    <String, Object>> pageMapData(Pg pg, SysRole.SearchParams searchParams);

    /**
     * 处理返回值
     * @param data
     * @return
     */
    SysRole prepareReturnModel(SysRole data);

    /**
     * 处理返回值
     * @param page
     * @return
     */
    IPage<SysRole> prepareReturnModel(IPage<SysRole> page);

    /**
     * 处理返回值
     * @param list
     * @return
     */
    List<SysRole> prepareReturnModel(List<SysRole> list);

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
    void validData(SysRole data);

}
/*

import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Rv;
import com.fgzy.mc.core.entity.SysRole;
import com.fgzy.mc.core.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@Autowired
ISysRoleService iSysRoleService;

@ApiOperation(value = "添加角色 ", notes = "添加角色 ")
@PostMapping(value = "/sysRole/add")
public Rv addSysRole(SysRole data) {
return Rv.getInstance(iSysRoleService.addData(data));
}

@ApiOperation(value = "修改角色 ", notes = "修改角色 ")
@PostMapping(value = "/sysRole/modify")
public Rv modifySysRole(SysRole data) {
return Rv.getInstance(iSysRoleService.modifyData(data));
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

@ApiOperation(value = "分页查询角色 ", notes = "分页查询角色 ")
@GetMapping(value = "/sysRole/page")
public Rv pageSysRole(Pg pg,SysRole.SearchParams searchParams) {
return Rv.getInstance(iSysRoleService.pageData(pg,searchParams));
}

@ApiOperation(value = "列表查询角色 ", notes = "列表查询角色 ")
@GetMapping(value = "/sysRole/list")
public Rv listSysRole(SysRole.SearchParams searchParams) {
return Rv.getInstance(iSysRoleService.listData(searchParams));
}

@ApiOperation(value = "分页查询角色 (map数据)", notes = "列表查询角色 (map数据)")
@GetMapping(value = "/sysRole/pageMap")
public Rv pageMapSysRole(Pg pg,SysRole.SearchParams searchParams) {
return Rv.getInstance(iSysRoleService.pageMapData(pg,searchParams));
}


*/