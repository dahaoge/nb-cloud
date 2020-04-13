package cn.hao.nb.cloud.ydgl.service;

import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.ydgl.entity.Company;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公司管理  服务类11
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2020-04-10
 */
public interface ICompanyService extends IService<Company> {
    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    Company addData(Company data);

    /**
     * 刷新公司内部组织机构
     *
     * @param comId
     * @return
     */
    boolean refreshComDept(Long comId);

    /**
     * 增量更新数据
     *
     * @param data
     * @return
     */
    boolean incrementModifyData(Company data);

    /**
     * 全量更新数据
     *
     * @param data
     * @return
     */
    boolean totalAmountModifyData(Company data);

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    boolean delData(Long id);

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    Company getDetail(Long id);

    /**
     * 分页查询数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage<Company> pageData(Pg pg, Company.SearchParams searchParams);

    /**
     * 列表查询数据
     *
     * @param searchParams
     * @return
     */
    List<Company> listData(Company.SearchParams searchParams);

    /**
     * 分页查询Map数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage
            <Map
                    <String, Object>> pageMapData(Pg pg, Company.SearchParams searchParams);

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    Company prepareReturnModel(Company data);

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    IPage<Company> prepareReturnModel(IPage<Company> page);

    /**
     * 处理返回值
     *
     * @param list
     * @return
     */
    List<Company> prepareReturnModel(List<Company> list);

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
    void validData(Company data);

}
/*

import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Rv;
import com.fgzy.mc.core.entity.Company;
import com.fgzy.mc.core.service.ICompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

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
return Rv.getInstance(iCompanyService.modifyData(data));
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
public Rv pageCompany(Pg pg,Company.SearchParams searchParams) {
return Rv.getInstance(iCompanyService.pageData(pg,searchParams));
}

@ApiOperation(value = "列表查询公司管理 ", notes = "列表查询公司管理 ")
@GetMapping(value = "/company/list")
public Rv listCompany(Company.SearchParams searchParams) {
return Rv.getInstance(iCompanyService.listData(searchParams));
}

@ApiOperation(value = "分页查询公司管理 (map数据)", notes = "列表查询公司管理 (map数据)")
@GetMapping(value = "/company/pageMap")
public Rv pageMapCompany(Pg pg,Company.SearchParams searchParams) {
return Rv.getInstance(iCompanyService.pageMapData(pg,searchParams));
}


*/