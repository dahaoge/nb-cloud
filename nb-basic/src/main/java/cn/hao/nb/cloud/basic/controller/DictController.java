package cn.hao.nb.cloud.basic.controller;

import cn.hao.nb.cloud.basic.entity.SysDict;
import cn.hao.nb.cloud.basic.service.ISysDictService;
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
 * @Date: 2020/4/22 17:51
 * @Description:
 */
@Api(description = "字典接口")
@Slf4j
@RestController
@RequestMapping("/dict")
public class DictController {

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
        return Rv.getInstance(iSysDictService.incrementModifyData(data));
    }

    @ApiOperation(value = "删除数据字典 ", notes = "删除数据字典 ")
    @PostMapping(value = "/sysDict/del/{id}")
    public Rv delSysDict(@ApiParam(name = "id", value = "数据字典 id") @PathVariable Long id) {
        return Rv.getInstance(iSysDictService.delData(id));
    }

    @ApiOperation(value = "查询数据字典 ", notes = "查询数据字典 ")
    @GetMapping(value = "/sysDict/getById/{id}")
    public Rv getSysDictById(@ApiParam(name = "id", value = "数据字典 id") @PathVariable Long id) {
        return Rv.getInstance(iSysDictService.getDetail(id));
    }

    @ApiOperation(value = "分页查询数据字典 ", notes = "分页查询数据字典 ")
    @GetMapping(value = "/sysDict/page")
    public Rv pageSysDict(Pg pg, SysDict.SearchParams searchParams) {
        return Rv.getInstance(iSysDictService.pageData(pg, searchParams));
    }

    @ApiOperation(value = "列表查询数据字典 ", notes = "列表查询数据字典 ")
    @GetMapping(value = "/sysDict/list")
    public Rv listSysDict(SysDict.SearchParams searchParams) {
        return Rv.getInstance(iSysDictService.listData(searchParams));
    }

    @ApiOperation(value = "根据类型获取列表", notes = "根据类型获取列表")
    @GetMapping(value = "/sysDict/getByType")
    public Rv getByType(String dictType) {
        return Rv.getInstance(iSysDictService.getByType(dictType));
    }
}
