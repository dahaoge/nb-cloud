package cn.hao.nb.cloud.auth.service;

import cn.hao.nb.cloud.auth.entity.UUserRole;
import cn.hao.nb.cloud.common.entity.Pg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户角色  服务类11
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
public interface IUUserRoleService extends IService<UUserRole> {
    /**
     * 添加数据
     * @param data
     * @return
     */
    UUserRole addData(UUserRole data);

    /**
     * 修改数据
     * @param data
     * @return
     */
    boolean modifyData(UUserRole data);

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
    UUserRole getDetail(String id);

    /**
     * 分页查询数据
     * @param pg
     * @param searchParams
     * @return
     */
    IPage<UUserRole> pageData(Pg pg, UUserRole.SearchParams searchParams);

    /**
     * 列表查询数据
     * @param searchParams
     * @return
     */
    List<UUserRole> listData(UUserRole.SearchParams searchParams);

    /**
     * 分页查询Map数据
     * @param pg
     * @param searchParams
     * @return
     */
    IPage
            <Map
                    <String, Object>> pageMapData(Pg pg, UUserRole.SearchParams searchParams);

    /**
     * 处理返回值
     * @param data
     * @return
     */
    UUserRole prepareReturnModel(UUserRole data);

    /**
     * 处理返回值
     * @param page
     * @return
     */
    IPage<UUserRole> prepareReturnModel(IPage<UUserRole> page);

    /**
     * 处理返回值
     * @param list
     * @return
     */
    List<UUserRole> prepareReturnModel(List<UUserRole> list);

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
    void validData(UUserRole data);

}
/*

import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Rv;
import com.fgzy.mc.core.entity.UUserRole;
import com.fgzy.mc.core.service.IUUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@Autowired
IUUserRoleService iUUserRoleService;

@ApiOperation(value = "添加用户角色 ", notes = "添加用户角色 ")
@PostMapping(value = "/uUserRole/add")
public Rv addUUserRole(UUserRole data) {
return Rv.getInstance(iUUserRoleService.addData(data));
}

@ApiOperation(value = "修改用户角色 ", notes = "修改用户角色 ")
@PostMapping(value = "/uUserRole/modify")
public Rv modifyUUserRole(UUserRole data) {
return Rv.getInstance(iUUserRoleService.modifyData(data));
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
public Rv pageUUserRole(Pg pg,UUserRole.SearchParams searchParams) {
return Rv.getInstance(iUUserRoleService.pageData(pg,searchParams));
}

@ApiOperation(value = "列表查询用户角色 ", notes = "列表查询用户角色 ")
@GetMapping(value = "/uUserRole/list")
public Rv listUUserRole(UUserRole.SearchParams searchParams) {
return Rv.getInstance(iUUserRoleService.listData(searchParams));
}

@ApiOperation(value = "分页查询用户角色 (map数据)", notes = "列表查询用户角色 (map数据)")
@GetMapping(value = "/uUserRole/pageMap")
public Rv pageMapUUserRole(Pg pg,UUserRole.SearchParams searchParams) {
return Rv.getInstance(iUUserRoleService.pageMapData(pg,searchParams));
}


*/