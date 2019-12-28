package cn.hao.nb.cloud.basic.service;

import cn.hao.nb.cloud.basic.entity.SysDict;
import cn.hao.nb.cloud.common.entity.Pg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据字典  服务类11
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-28
 */
public interface ISysDictService extends IService<SysDict> {
    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    SysDict addData(SysDict data);

    /**
     * 增量更新数据
     *
     * @param data
     * @return
     */
    boolean incrementModifyData(SysDict data);

    /**
     * 全量更新数据
     *
     * @param data
     * @return
     */
    boolean totalAmountModifyData(SysDict data);

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
    SysDict getDetail(String id);

    /**
     * 分页查询数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage<SysDict> pageData(Pg pg, SysDict.SearchParams searchParams);

    /**
     * 列表查询数据
     *
     * @param searchParams
     * @return
     */
    List<SysDict> listData(SysDict.SearchParams searchParams);

    /**
     * 分页查询Map数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage
            <Map
                    <String, Object>> pageMapData(Pg pg, SysDict.SearchParams searchParams);

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    SysDict prepareReturnModel(SysDict data);

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    IPage<SysDict> prepareReturnModel(IPage<SysDict> page);

    /**
     * 处理返回值
     *
     * @param list
     * @return
     */
    List<SysDict> prepareReturnModel(List<SysDict> list);

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
    void validData(SysDict data);

}
/*

import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Rv;
import com.fgzy.mc.core.entity.SysDict;
import com.fgzy.mc.core.service.ISysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@Autowired
ISysDictService iSysDictService;

@ApiOperation(value = "添加数据字典 ", notes = "添加数据字典 ")
@PostMapping(value = "/sysDict/add")
public Rv addSysDict(SysDict data) {
return Rv.getInstance(iSysDictService.addData(data));
}

@ApiOperation(value = "修改数据字典 ", notes = "修改数据字典 ")
@PostMapping(value = "/sysDict/modify")
public Rv modifySysDict(SysDict data) {
return Rv.getInstance(iSysDictService.modifyData(data));
}

@ApiOperation(value = "删除数据字典 ", notes = "删除数据字典 ")
@PostMapping(value = "/sysDict/del/{id}")
public Rv delSysDict(@ApiParam(name = "id", value = "数据字典 id") @PathVariable String id) {
return Rv.getInstance(iSysDictService.delData(id));
}

@ApiOperation(value = "查询数据字典 ", notes = "查询数据字典 ")
@GetMapping(value = "/sysDict/getById/{id}")
public Rv getSysDictById(@ApiParam(name = "id", value = "数据字典 id") @PathVariable String id) {
return Rv.getInstance(iSysDictService.getDetail(id));
}

@ApiOperation(value = "分页查询数据字典 ", notes = "分页查询数据字典 ")
@GetMapping(value = "/sysDict/page")
public Rv pageSysDict(Pg pg,SysDict.SearchParams searchParams) {
return Rv.getInstance(iSysDictService.pageData(pg,searchParams));
}

@ApiOperation(value = "列表查询数据字典 ", notes = "列表查询数据字典 ")
@GetMapping(value = "/sysDict/list")
public Rv listSysDict(SysDict.SearchParams searchParams) {
return Rv.getInstance(iSysDictService.listData(searchParams));
}

@ApiOperation(value = "分页查询数据字典 (map数据)", notes = "列表查询数据字典 (map数据)")
@GetMapping(value = "/sysDict/pageMap")
public Rv pageMapSysDict(Pg pg,SysDict.SearchParams searchParams) {
return Rv.getInstance(iSysDictService.pageMapData(pg,searchParams));
}


*/