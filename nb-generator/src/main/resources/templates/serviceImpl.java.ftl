package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.common.util.IDUtil;
import cn.hao.nb.cloud.common.util.UserUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;


import java.util.List;
import java.util.Map;

/**
* <p>
    * ${table.comment!} 服务实现类
    * </p>
*
* @author ${author}
* @since ${date}
*/
@Service
<#if kotlin>
    open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

    }
<#else>
    public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Autowired
    IDUtil idUtil;
    @Autowired
    ${table.mapperName} mapper;

    /**
    * 添加数据
    * @param data
    * @return
    */
    @Override
    public ${entity} addData(${entity} data) {
    this.validData(data);
    <#list table.fields as field>
        <#if field.keyFlag>
            <#if field.name=='t_id'>
                data.setTId(idUtil.nextId());
            <#else>
                data.set${field.capitalName}(idUtil.nextId());
            </#if>
        </#if>
    </#list>
    TokenUser tokenUser=UserUtil.getTokenUser(false);
    if (CheckUtil.objIsNotEmpty(tokenUser)) {
    data.setCreateBy(tokenUser.getUserId());
    data.setUpdateBy(tokenUser.getUserId());
    }
    data.setVersion(null);
    data.setDeleted(null);
    data.setUpdateTime(null);
    data.setCreateTime(null);
    this.save(data);
    return data;
    }

    /**
    * 增量更新数据
    * @param data
    * @return
    */
    @Override
    public boolean incrementModifyData(${entity} data) {
    if (CheckUtil.objIsEmpty(data)||CheckUtil.objIsEmpty(
    <#list table.fields as field>
        <#if field.keyFlag>
            <#if field.name=='t_id'>
                data.getTId()
            <#else>
                data.get${field.capitalName}()
            </#if>
        </#if>
    </#list>
    ))
    throw NBException.create(EErrorCode.missingArg);
    data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
    data.setVersion(null);
    data.setDeleted(null);
    data.setUpdateTime(null);
    data.setCreateTime(null);
    return this.updateById(data);
    }

    /**
    * 全量更新数据
    * @param data
    * @return
    */
    @Override
    public boolean totalAmountModifyData(${entity} data) {
    if (CheckUtil.objIsEmpty(data)||CheckUtil.objIsEmpty(
    <#list table.fields as field>
        <#if field.keyFlag>
            <#if field.name=='t_id'>
                data.getTId()
            <#else>
                data.get${field.capitalName}()
            </#if>
        </#if>
    </#list>
    ))
    throw NBException.create(EErrorCode.missingArg);
    data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
    data.setVersion(null);
    data.setDeleted(null);
    data.setUpdateTime(null);
    data.setCreateTime(null);
    return this.update(data, Wrappers.<${entity}>lambdaUpdate()
    <#list table.fields as field>
        <#if !field.keyFlag && "version" != field.name && "create_by" != field.name && "create_time" != field.name && "update_time" != field.name && "deleted" != field.name>
            .set(${entity}::get${field.capitalName}, data.get${field.capitalName}())
        </#if>
    </#list>
    <#list table.fields as field>
        <#if field.keyFlag>
            <#if field.name=='t_id'>
                .eq(${entity}::getTId, data.getTId())
            <#else>
                .eq(${entity}::get${field.capitalName}, data.get${field.capitalName}())
            </#if>
        </#if>
    </#list>
    );
    }

    /**
    * 删除数据
    * @param id
    * @return
    */
    @Override
    public boolean delData(Long id) {
    if (CheckUtil.objIsEmpty(id))
    throw NBException.create(EErrorCode.missingArg);
    return this.removeById(id);
    }

    /**
    * 获取详情
    * @param id
    * @return
    */
    @Override
    public ${entity} getDetail(Long id) {
    if (CheckUtil.objIsEmpty(id))
    throw NBException.create(EErrorCode.missingArg);
    return this.prepareReturnModel(this.getById(id));
    }

    /**
    * 分页查询
    * @param pg
    * @param searchParams
    * @return
    */
    @Override
    public IPage<${entity}> pageData(Pg pg, ${entity}.SearchParams searchParams) {
    return this.prepareReturnModel(this.page(pg.page(),searchParams.preWrapper(pg.wrapper())));
    }

    /**
    * 列表查询
    * @param searchParams
    * @return
    */
    @Override
    public List<${entity}> listData(${entity}.SearchParams searchParams) {
    return this.prepareReturnModel(this.list(searchParams.preWrapper(null)));
    }

    /**
    * 连表分页查询map数据
    * @param pg
    * @param searchParams
    * @return
    */
    @Override
    public IPage
    <Map
    <String, Object>> pageMapData(Pg pg, ${entity}.SearchParams searchParams) {
    return mapper.pageMapData(pg.page(),searchParams);
    }

    /**
    * 处理返回值
    * @param data
    * @return
    */
    @Override
    public ${entity} prepareReturnModel(${entity} data) {
    return data;
    }

    /**
    * 处理返回值
    * @param page
    * @return
    */
    @Override
    public IPage<${entity}> prepareReturnModel(IPage<${entity}> page) {
    if (CheckUtil.objIsNotEmpty(page)&&CheckUtil.collectionIsNotEmpty(page.getRecords()))
    this.prepareReturnModel(page.getRecords());
    return page;
    }

    /**
    * 处理返回值
    * @param list
    * @return
    */
    @Override
    public List<${entity}> prepareReturnModel(List<${entity}> list) {
    if (CheckUtil.collectionIsNotEmpty(list))
    list.forEach(item->{
    this.prepareReturnModel(item);
    });
    return list;
    }

    /**
    * 处理返回值
    * @param page
    * @return
    */
    @Override
    public IPage
    <Map
    <String, Object>> prepareReturnMapModel(IPage
    <Map
    <String, Object>> page) {
    return page;
    }

    /**
    * 添加/修改数据前校验数据有效性(强制抛出异常)
    * 如果不需要抛出异常请不用调用该服务
    * @param data
    */
    @Override
    public void validData(${entity} data) {
    if (CheckUtil.objIsEmpty(data))
    throw NBException.create(EErrorCode.missingArg);

    <#list table.fields as field>
        <#if !field.keyFlag && "version" != field.name && "create_by" != field.name && "create_time" != field.name &&
        "update_time" != field.name && "deleted" != field.name&& "update_by" != field.name>
            if (CheckUtil.objIsEmpty(data.get${field.capitalName}()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("${field.propertyName}");
        </#if>
    </#list>
    }
    }
</#if>