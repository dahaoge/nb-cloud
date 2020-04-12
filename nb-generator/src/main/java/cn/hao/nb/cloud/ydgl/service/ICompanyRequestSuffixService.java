package cn.hao.nb.cloud.ydgl.service;

import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.ydgl.entity.CompanyRequestSuffix;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公司请求后缀  服务类11
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2020-04-12
 */
public interface ICompanyRequestSuffixService extends IService<CompanyRequestSuffix> {
    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    CompanyRequestSuffix addData(CompanyRequestSuffix data);

    /**
     * 增量更新数据
     *
     * @param data
     * @return
     */
    boolean incrementModifyData(CompanyRequestSuffix data);

    /**
     * 全量更新数据
     *
     * @param data
     * @return
     */
    boolean totalAmountModifyData(CompanyRequestSuffix data);

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
    CompanyRequestSuffix getDetail(Long id);

    /**
     * 分页查询数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage<CompanyRequestSuffix> pageData(Pg pg, CompanyRequestSuffix.SearchParams searchParams);

    /**
     * 列表查询数据
     *
     * @param searchParams
     * @return
     */
    List<CompanyRequestSuffix> listData(CompanyRequestSuffix.SearchParams searchParams);

    /**
     * 分页查询Map数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage
            <Map
                    <String, Object>> pageMapData(Pg pg, CompanyRequestSuffix.SearchParams searchParams);

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    CompanyRequestSuffix prepareReturnModel(CompanyRequestSuffix data);

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    IPage<CompanyRequestSuffix> prepareReturnModel(IPage<CompanyRequestSuffix> page);

    /**
     * 处理返回值
     *
     * @param list
     * @return
     */
    List<CompanyRequestSuffix> prepareReturnModel(List<CompanyRequestSuffix> list);

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
    void validData(CompanyRequestSuffix data);

}
/*

import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Rv;
import com.fgzy.mc.core.entity.CompanyRequestSuffix;
import com.fgzy.mc.core.service.ICompanyRequestSuffixService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@Autowired
ICompanyRequestSuffixService iCompanyRequestSuffixService;

@ApiOperation(value = "添加公司请求后缀 ", notes = "添加公司请求后缀 ")
@PostMapping(value = "/companyRequestSuffix/add")
public Rv addCompanyRequestSuffix(CompanyRequestSuffix data) {
return Rv.getInstance(iCompanyRequestSuffixService.addData(data));
}

@ApiOperation(value = "修改公司请求后缀 ", notes = "修改公司请求后缀 ")
@PostMapping(value = "/companyRequestSuffix/modify")
public Rv modifyCompanyRequestSuffix(CompanyRequestSuffix data) {
return Rv.getInstance(iCompanyRequestSuffixService.modifyData(data));
}

@ApiOperation(value = "删除公司请求后缀 ", notes = "删除公司请求后缀 ")
@PostMapping(value = "/companyRequestSuffix/del/{id}")
public Rv delCompanyRequestSuffix(@ApiParam(name = "id", value = "公司请求后缀 id") @PathVariable Long id) {
return Rv.getInstance(iCompanyRequestSuffixService.delData(id));
}

@ApiOperation(value = "查询公司请求后缀 ", notes = "查询公司请求后缀 ")
@GetMapping(value = "/companyRequestSuffix/getById/{id}")
public Rv getCompanyRequestSuffixById(@ApiParam(name = "id", value = "公司请求后缀 id") @PathVariable Long id) {
return Rv.getInstance(iCompanyRequestSuffixService.getDetail(id));
}

@ApiOperation(value = "分页查询公司请求后缀 ", notes = "分页查询公司请求后缀 ")
@GetMapping(value = "/companyRequestSuffix/page")
public Rv pageCompanyRequestSuffix(Pg pg,CompanyRequestSuffix.SearchParams searchParams) {
return Rv.getInstance(iCompanyRequestSuffixService.pageData(pg,searchParams));
}

@ApiOperation(value = "列表查询公司请求后缀 ", notes = "列表查询公司请求后缀 ")
@GetMapping(value = "/companyRequestSuffix/list")
public Rv listCompanyRequestSuffix(CompanyRequestSuffix.SearchParams searchParams) {
return Rv.getInstance(iCompanyRequestSuffixService.listData(searchParams));
}

@ApiOperation(value = "分页查询公司请求后缀 (map数据)", notes = "列表查询公司请求后缀 (map数据)")
@GetMapping(value = "/companyRequestSuffix/pageMap")
public Rv pageMapCompanyRequestSuffix(Pg pg,CompanyRequestSuffix.SearchParams searchParams) {
return Rv.getInstance(iCompanyRequestSuffixService.pageMapData(pg,searchParams));
}


*/