package cn.hao.nb.cloud.ydgl.controller;

import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.ydgl.entity.Company;
import cn.hao.nb.cloud.ydgl.service.ICompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: hao
 * @Date: 2020/4/21 16:13
 * @Description:
 */
@Api(description = "公司管理")
@Slf4j
@RestController
@RequestMapping("/ydgl")
public class CompanyController {

    @Autowired
    ICompanyService iCompanyService;

    @ApiOperation(value = "添加公司管理 ", notes = "添加公司管理 ")
    @PostMapping(value = "/company/add")
    public Rv addCompany(Company data) {
        return Rv.getInstance(iCompanyService.addData(data));
    }

    @ApiOperation(value = "修改公司管理 ", notes = "修改公司管理 ")
    @PostMapping(value = "/company/modify")
    public Rv modifyCompany(Company data) {
        return Rv.getInstance(iCompanyService.incrementModifyData(data));
    }

    @ApiOperation(value = "删除公司管理 ", notes = "删除公司管理 ")
    @PostMapping(value = "/company/del/{id}")
    public Rv delCompany(@ApiParam(name = "id", value = "公司管理 id") @PathVariable Long id) {
        return Rv.getInstance(iCompanyService.delData(id));
    }

    @ApiOperation(value = "查询公司管理 ", notes = "查询公司管理 ")
    @GetMapping(value = "/company/getById/{id}")
    public Rv getCompanyById(@ApiParam(name = "id", value = "公司管理 id") @PathVariable Long id) {
        return Rv.getInstance(iCompanyService.getDetail(id));
    }

    @ApiOperation(value = "分页查询公司管理 ", notes = "分页查询公司管理 ")
    @GetMapping(value = "/company/page")
    public Rv pageCompany(Pg pg, Company.SearchParams searchParams) {
        return Rv.getInstance(iCompanyService.pageData(pg, searchParams));
    }

    @ApiOperation(value = "刷新公司组织机构(谨慎操作)", notes = "刷新公司组织机构(谨慎操作) ")
    @GetMapping(value = "/company/refreshComDept/{comId}")
    public Rv refreshComDept(@PathVariable Long comId) {
        return Rv.getInstance(iCompanyService.refreshComDept(comId));
    }

}
