package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fgzy.mc.common.entity.Pg;
import com.fgzy.mc.common.entity.Qd;
import com.fgzy.mc.common.entity.Qw;
import com.fgzy.mc.common.exception.PangException;
import com.fgzy.mc.common.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.fgzy.mc.common.penum.EErrorCode;
import com.fgzy.mc.core.component.annotation.JoinCreateUser;

import java.util.Date;
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
            data.set${field.capitalName}(idUtil.nextId());
        </#if>
    </#list>
    data.setCreateBy(UserUtil.getTokenUser(true).getUserId());
    data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
    data.setVersion(null);
    data.setDeleted(null);
    data.setUpdateTime(null);
    data.setCreateTime(null);
    this.save(data);
    return data;
    }

    /**
    * 修改数据
    * @param data
    * @return
    */
    @Override
    public boolean modifyData(${entity} data) {
    this.validData(data);
    data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
    data.setVersion(null);
    data.setDeleted(null);
    data.setUpdateTime(null);
    data.setCreateTime(null);
    return this.updateById(data);
    }

    /**
    * 删除数据
    * @param id
    * @return
    */
    @Override
    public boolean delData(String id) {
    if (CheckUtil.strIsEmpty(id))
    throw PangException.create(EErrorCode.missingArg);
    return this.removeById(id);
    }

    /**
    * 获取详情
    * @param id
    * @return
    */
    @Override
    public ${entity} getDetail(String id) {
    if (CheckUtil.strIsEmpty(id))
    throw PangException.create(EErrorCode.missingArg);
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
    @JoinCreateUser
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
    @JoinCreateUser
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
    throw PangException.create(EErrorCode.missingArg);
    }
    }
</#if>