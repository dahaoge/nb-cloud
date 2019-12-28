package cn.hao.nb.cloud.basic.service;

import cn.hao.nb.cloud.basic.entity.SysAppVersion;
import cn.hao.nb.cloud.common.entity.Pg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * app版本管理  服务类11
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-28
 */
public interface ISysAppVersionService extends IService<SysAppVersion> {
    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    SysAppVersion addData(SysAppVersion data);

    /**
     * 增量更新数据
     *
     * @param data
     * @return
     */
    boolean incrementModifyData(SysAppVersion data);

    /**
     * 全量更新数据
     *
     * @param data
     * @return
     */
    boolean totalAmountModifyData(SysAppVersion data);

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    boolean delData(String id);

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    SysAppVersion getDetail(String id);

    /**
     * 分页查询数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage<SysAppVersion> pageData(Pg pg, SysAppVersion.SearchParams searchParams);

    /**
     * 列表查询数据
     *
     * @param searchParams
     * @return
     */
    List<SysAppVersion> listData(SysAppVersion.SearchParams searchParams);

    /**
     * 分页查询Map数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage
            <Map
                    <String, Object>> pageMapData(Pg pg, SysAppVersion.SearchParams searchParams);

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    SysAppVersion prepareReturnModel(SysAppVersion data);

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    IPage<SysAppVersion> prepareReturnModel(IPage<SysAppVersion> page);

    /**
     * 处理返回值
     *
     * @param list
     * @return
     */
    List<SysAppVersion> prepareReturnModel(List<SysAppVersion> list);

    /**
     * 处理返回值
     *
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
     *
     * @param data
     */
    void validData(SysAppVersion data);

}
/*

import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Rv;
import com.fgzy.mc.core.entity.SysAppVersion;
import com.fgzy.mc.core.service.ISysAppVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@Autowired
ISysAppVersionService iSysAppVersionService;

@ApiOperation(value = "添加app版本管理 ", notes = "添加app版本管理 ")
@PostMapping(value = "/sysAppVersion/add")
public Rv addSysAppVersion(SysAppVersion data) {
return Rv.getInstance(iSysAppVersionService.addData(data));
}

@ApiOperation(value = "修改app版本管理 ", notes = "修改app版本管理 ")
@PostMapping(value = "/sysAppVersion/modify")
public Rv modifySysAppVersion(SysAppVersion data) {
return Rv.getInstance(iSysAppVersionService.modifyData(data));
}

@ApiOperation(value = "删除app版本管理 ", notes = "删除app版本管理 ")
@PostMapping(value = "/sysAppVersion/del/{id}")
public Rv delSysAppVersion(@ApiParam(name = "id", value = "app版本管理 id") @PathVariable String id) {
return Rv.getInstance(iSysAppVersionService.delData(id));
}

@ApiOperation(value = "查询app版本管理 ", notes = "查询app版本管理 ")
@GetMapping(value = "/sysAppVersion/getById/{id}")
public Rv getSysAppVersionById(@ApiParam(name = "id", value = "app版本管理 id") @PathVariable String id) {
return Rv.getInstance(iSysAppVersionService.getDetail(id));
}

@ApiOperation(value = "分页查询app版本管理 ", notes = "分页查询app版本管理 ")
@GetMapping(value = "/sysAppVersion/page")
public Rv pageSysAppVersion(Pg pg,SysAppVersion.SearchParams searchParams) {
return Rv.getInstance(iSysAppVersionService.pageData(pg,searchParams));
}

@ApiOperation(value = "列表查询app版本管理 ", notes = "列表查询app版本管理 ")
@GetMapping(value = "/sysAppVersion/list")
public Rv listSysAppVersion(SysAppVersion.SearchParams searchParams) {
return Rv.getInstance(iSysAppVersionService.listData(searchParams));
}

@ApiOperation(value = "分页查询app版本管理 (map数据)", notes = "列表查询app版本管理 (map数据)")
@GetMapping(value = "/sysAppVersion/pageMap")
public Rv pageMapSysAppVersion(Pg pg,SysAppVersion.SearchParams searchParams) {
return Rv.getInstance(iSysAppVersionService.pageMapData(pg,searchParams));
}


*/