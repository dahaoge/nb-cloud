package cn.hao.nb.cloud.basic.service;

import cn.hao.nb.cloud.basic.entity.SysTags;
import cn.hao.nb.cloud.common.entity.Pg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标签  服务类11
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-28
 */
public interface ISysTagsService extends IService<SysTags> {
    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    SysTags addData(SysTags data);

    /**
     * 增量更新数据
     *
     * @param data
     * @return
     */
    boolean incrementModifyData(SysTags data);

    /**
     * 全量更新数据
     *
     * @param data
     * @return
     */
    boolean totalAmountModifyData(SysTags data);

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
    SysTags getDetail(Long id);

    /**
     * 分页查询数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage<SysTags> pageData(Pg pg, SysTags.SearchParams searchParams);

    /**
     * 列表查询数据
     *
     * @param searchParams
     * @return
     */
    List<SysTags> listData(SysTags.SearchParams searchParams);

    /**
     * 分页查询Map数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage
            <Map
                    <String, Object>> pageMapData(Pg pg, SysTags.SearchParams searchParams);

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    SysTags prepareReturnModel(SysTags data);

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    IPage<SysTags> prepareReturnModel(IPage<SysTags> page);

    /**
     * 处理返回值
     *
     * @param list
     * @return
     */
    List<SysTags> prepareReturnModel(List<SysTags> list);

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
    void validData(SysTags data);

}
/*

import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Rv;
import com.fgzy.mc.core.entity.SysTags;
import com.fgzy.mc.core.service.ISysTagsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@Autowired
ISysTagsService iSysTagsService;

@ApiOperation(value = "添加标签 ", notes = "添加标签 ")
@PostMapping(value = "/sysTags/add")
public Rv addSysTags(SysTags data) {
return Rv.getInstance(iSysTagsService.addData(data));
}

@ApiOperation(value = "修改标签 ", notes = "修改标签 ")
@PostMapping(value = "/sysTags/modify")
public Rv modifySysTags(SysTags data) {
return Rv.getInstance(iSysTagsService.modifyData(data));
}

@ApiOperation(value = "删除标签 ", notes = "删除标签 ")
@PostMapping(value = "/sysTags/del/{id}")
public Rv delSysTags(@ApiParam(name = "id", value = "标签 id") @PathVariable Long id) {
return Rv.getInstance(iSysTagsService.delData(id));
}

@ApiOperation(value = "查询标签 ", notes = "查询标签 ")
@GetMapping(value = "/sysTags/getById/{id}")
public Rv getSysTagsById(@ApiParam(name = "id", value = "标签 id") @PathVariable Long id) {
return Rv.getInstance(iSysTagsService.getDetail(id));
}

@ApiOperation(value = "分页查询标签 ", notes = "分页查询标签 ")
@GetMapping(value = "/sysTags/page")
public Rv pageSysTags(Pg pg,SysTags.SearchParams searchParams) {
return Rv.getInstance(iSysTagsService.pageData(pg,searchParams));
}

@ApiOperation(value = "列表查询标签 ", notes = "列表查询标签 ")
@GetMapping(value = "/sysTags/list")
public Rv listSysTags(SysTags.SearchParams searchParams) {
return Rv.getInstance(iSysTagsService.listData(searchParams));
}

@ApiOperation(value = "分页查询标签 (map数据)", notes = "列表查询标签 (map数据)")
@GetMapping(value = "/sysTags/pageMap")
public Rv pageMapSysTags(Pg pg,SysTags.SearchParams searchParams) {
return Rv.getInstance(iSysTagsService.pageMapData(pg,searchParams));
}


*/