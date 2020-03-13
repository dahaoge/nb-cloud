package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Qd;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* <p>
    * ${table.comment!} 服务类11
    * </p>
*
* @author ${author}
* @since ${date}
*/
<#if kotlin>
    interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
    public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {
    /**
    * 添加数据
    * @param data
    * @return
    */
    ${entity} addData(${entity} data);

    /**
    * 增量更新数据
    * @param data
    * @return
    */
    boolean incrementModifyData(${entity} data);

    /**
    * 全量更新数据
    *
    * @param data
    * @return
    */
    boolean totalAmountModifyData(${entity} data);

    /**
    * 删除数据
    * @param id
    * @return
    */
    boolean delData(Long id);

    /**
    * 查询详情
    * @param id
    * @return
    */
    ${entity} getDetail(Long id);

    /**
    * 分页查询数据
    * @param pg
    * @param searchParams
    * @return
    */
    IPage<${entity}> pageData(Pg pg,${entity}.SearchParams searchParams);

    /**
    * 列表查询数据
    * @param searchParams
    * @return
    */
    List<${entity}> listData(${entity}.SearchParams searchParams);

    /**
    * 分页查询Map数据
    * @param pg
    * @param searchParams
    * @return
    */
    IPage
    <Map
    <String,Object>> pageMapData(Pg pg, ${entity}.SearchParams searchParams);

    /**
    * 处理返回值
    * @param data
    * @return
    */
    ${entity} prepareReturnModel(${entity} data);

    /**
    * 处理返回值
    * @param page
    * @return
    */
    IPage<${entity}> prepareReturnModel(IPage<${entity}> page);

    /**
    * 处理返回值
    * @param list
    * @return
    */
    List<${entity}> prepareReturnModel(List<${entity}> list);

    /**
    * 处理返回值
    * @param page
    * @return
    */
    IPage
    <Map
    <String,Object>> prepareReturnMapModel(IPage
    <Map
    <String,Object>> page);

    /**
    * 添加/修改数据前校验数据有效性(强制抛出异常)
    * 如果不需要抛出异常请不用调用该服务
    * @param data
    */
    void validData(${entity} data);

    }
</#if>
/*

import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Rv;
import com.fgzy.mc.core.entity.${entity};
import com.fgzy.mc.core.service.${table.serviceName};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@Autowired
${table.serviceName} ${table.serviceName?uncap_first};

@ApiOperation(value = "添加${table.comment!}", notes = "添加${table.comment!}")
@PostMapping(value = "/${entity?uncap_first}/add")
public Rv add${entity}(${entity} data) {
return Rv.getInstance(${table.serviceName?uncap_first}.addData(data));
}

@ApiOperation(value = "修改${table.comment!}", notes = "修改${table.comment!}")
@PostMapping(value = "/${entity?uncap_first}/modify")
public Rv modify${entity}(${entity} data) {
return Rv.getInstance(${table.serviceName?uncap_first}.modifyData(data));
}

@ApiOperation(value = "删除${table.comment!}", notes = "删除${table.comment!}")
@PostMapping(value = "/${entity?uncap_first}/del/{id}")
public Rv del${entity}(@ApiParam(name = "id", value = "${table.comment!}id") @PathVariable Long id) {
return Rv.getInstance(${table.serviceName?uncap_first}.delData(id));
}

@ApiOperation(value = "查询${table.comment!}", notes = "查询${table.comment!}")
@GetMapping(value = "/${entity?uncap_first}/getById/{id}")
public Rv get${entity}ById(@ApiParam(name = "id", value = "${table.comment!}id") @PathVariable Long id) {
return Rv.getInstance(${table.serviceName?uncap_first}.getDetail(id));
}

@ApiOperation(value = "分页查询${table.comment!}", notes = "分页查询${table.comment!}")
@GetMapping(value = "/${entity?uncap_first}/page")
public Rv page${entity}(Pg pg,${entity}.SearchParams searchParams) {
return Rv.getInstance(${table.serviceName?uncap_first}.pageData(pg,searchParams));
}

@ApiOperation(value = "列表查询${table.comment!}", notes = "列表查询${table.comment!}")
@GetMapping(value = "/${entity?uncap_first}/list")
public Rv list${entity}(${entity}.SearchParams searchParams) {
return Rv.getInstance(${table.serviceName?uncap_first}.listData(searchParams));
}

@ApiOperation(value = "分页查询${table.comment!}(map数据)", notes = "列表查询${table.comment!}(map数据)")
@GetMapping(value = "/${entity?uncap_first}/pageMap")
public Rv pageMap${entity}(Pg pg,${entity}.SearchParams searchParams) {
return Rv.getInstance(${table.serviceName?uncap_first}.pageMapData(pg,searchParams));
}


*/