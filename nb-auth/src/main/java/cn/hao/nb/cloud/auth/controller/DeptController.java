package cn.hao.nb.cloud.auth.controller;

import cn.hao.nb.cloud.auth.entity.SysDept;
import cn.hao.nb.cloud.auth.entity.UUserDept;
import cn.hao.nb.cloud.auth.service.ISysDeptService;
import cn.hao.nb.cloud.auth.service.IUUserDeptService;
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
 * @Date: 2019-12-17 09:59
 * @Description:
 */
@Api(description = "组织机构")
@Slf4j
@RestController
@RequestMapping("/")
public class DeptController {


    @Autowired
    IUUserDeptService iUUserDeptService;
    @Autowired
    ISysDeptService iSysDeptService;

    @ApiOperation(value = "根据外部组织机构刷新公司组织机构", notes = "根据外部组织机构刷新公司组织机构")
    @PostMapping(value = "/sysDept/refreshCompanyDeptByExternalDepartment")
    public Rv refreshCompanyDeptByExternalDepartment(Long companyId, String externalDeptJsonList) {
        return Rv.getInstance(iSysDeptService.refreshCompanyDeptByExternalDepartment(companyId, externalDeptJsonList));
    }

    @ApiOperation(value = "添加组织机构", notes = "添加组织机构")
    @PostMapping(value = "/sysDept/add")
    public Rv addSysDept(SysDept data) {
        return Rv.getInstance(iSysDeptService.addData(data));
    }

    @ApiOperation(value = "修改组织机构", notes = "修改组织机构")
    @PostMapping(value = "/sysDept/modify")
    public Rv modifySysDept(SysDept data) {
        return Rv.getInstance(iSysDeptService.incrementModifyData(data));
    }

    @ApiOperation(value = "删除组织机构", notes = "删除组织机构")
    @PostMapping(value = "/sysDept/del/{id}")
    public Rv delSysDept(@ApiParam(name = "id", value = "组织机构id") @PathVariable Long id) {
        return Rv.getInstance(iSysDeptService.delData(id));
    }

    @ApiOperation(value = "查询组织机构", notes = "查询组织机构")
    @GetMapping(value = "/sysDept/getById/{id}")
    public Rv getSysDeptById(@ApiParam(name = "id", value = "组织机构id") @PathVariable Long id) {
        return Rv.getInstance(iSysDeptService.getDetail(id));
    }

    @ApiOperation(value = "分页查询组织机构", notes = "分页查询组织机构")
    @GetMapping(value = "/sysDept/page")
    public Rv pageSysDept(Pg pg, SysDept.SearchParams searchParams) {
        return Rv.getInstance(iSysDeptService.pageData(pg, searchParams));
    }

    @ApiOperation(value = "列表查询组织机构", notes = "列表查询组织机构")
    @GetMapping(value = "/sysDept/list")
    public Rv listSysDept(SysDept.SearchParams searchParams) {
        return Rv.getInstance(iSysDeptService.listData(searchParams));
    }

    @ApiOperation(value = "分页查询组织机构(map数据)", notes = "列表查询组织机构(map数据)")
    @GetMapping(value = "/sysDept/pageMap")
    public Rv pageMapSysDept(Pg pg, SysDept.SearchParams searchParams) {
        return Rv.getInstance(iSysDeptService.pageMapData(pg, searchParams));
    }

    @ApiOperation(value = "根据用户id列表查询所有组织机构")
    @GetMapping("/sysDept/listAllDisDeptByUserId/{userId}")
    public Rv listAllDisDeptByUserId(@PathVariable Long userId) {
        return Rv.getInstance(iSysDeptService.listAllDisDeptByUserId(userId));
    }

    @ApiOperation(value = "根据父id查询列表查询所有组织机构")
    @GetMapping("/sysDept/listAllDisDeptByParentId/{pId}")
    public Rv listAllDisDeptByParentId(@PathVariable Long pId) {
        return Rv.getInstance(iSysDeptService.listAllDisDeptByParentId(pId));
    }

    @ApiOperation(value = "根据父id查询下级组织机构")
    @GetMapping("/sysDept/listDisDeptByParentId/{pId}")
    public Rv listDisDeptByParentId(@PathVariable Long pId) {
        return Rv.getInstance(iSysDeptService.listDisDeptByParentId(pId));
    }

    @ApiOperation(value = "组织机构树(deptId为空的时候返回以root节点为起始节点的树)")
    @GetMapping("/sysDept/deptTree")
    public Rv deptTree(Long deptId) {
        return Rv.getInstance(iSysDeptService.deptTree(deptId));
    }

    @ApiOperation(value = "用户组织机构树")
    @GetMapping("/sysDept/userDeptTree/{userId}")
    public Rv userDeptTree(@PathVariable Long userId) {
        return Rv.getInstance(iSysDeptService.userDeptTree(userId));
    }

    @ApiOperation(value = "根据用户id获取组织机构")
    @GetMapping("/sysDept/listByUserId/{userId}")
    public Rv listDeptByUserId(@PathVariable Long userId) {
        return Rv.getInstance(iSysDeptService.listByUserId(userId));
    }

    @ApiOperation(value = "添加用户所属组织机构", notes = "添加用户所属组织机构")
    @PostMapping(value = "/uUserDept/add")
    public Rv addUUserDept(UUserDept data) {
        return Rv.getInstance(iUUserDeptService.addData(data));
    }

    @ApiOperation(value = "修改用户所属组织机构", notes = "修改用户所属组织机构")
    @PostMapping(value = "/uUserDept/modify")
    public Rv modifyUUserDept(UUserDept data) {
        return Rv.getInstance(iUUserDeptService.incrementModifyData(data));
    }

    @ApiOperation(value = "删除用户所属组织机构", notes = "删除用户所属组织机构")
    @PostMapping(value = "/uUserDept/del/{id}")
    public Rv delUUserDept(@ApiParam(name = "id", value = "用户所属组织机构id") @PathVariable Long id) {
        return Rv.getInstance(iUUserDeptService.delData(id));
    }

    @ApiOperation(value = "查询用户所属组织机构", notes = "查询用户所属组织机构")
    @GetMapping(value = "/uUserDept/getById/{id}")
    public Rv getUUserDeptById(@ApiParam(name = "id", value = "用户所属组织机构id") @PathVariable Long id) {
        return Rv.getInstance(iUUserDeptService.getDetail(id));
    }

    @ApiOperation(value = "分页查询用户所属组织机构", notes = "分页查询用户所属组织机构")
    @GetMapping(value = "/uUserDept/page")
    public Rv pageUUserDept(Pg pg, UUserDept.SearchParams searchParams) {
        return Rv.getInstance(iUUserDeptService.pageData(pg, searchParams));
    }

    @ApiOperation(value = "列表查询用户所属组织机构", notes = "列表查询用户所属组织机构")
    @GetMapping(value = "/uUserDept/list")
    public Rv listUUserDept(UUserDept.SearchParams searchParams) {
        return Rv.getInstance(iUUserDeptService.listData(searchParams));
    }

    @ApiOperation(value = "分页查询用户所属组织机构(map数据)", notes = "列表查询用户所属组织机构(map数据)")
    @GetMapping(value = "/uUserDept/pageMap")
    public Rv pageMapUUserDept(Pg pg, UUserDept.SearchParams searchParams) {
        return Rv.getInstance(iUUserDeptService.pageMapData(pg, searchParams));
    }
}
