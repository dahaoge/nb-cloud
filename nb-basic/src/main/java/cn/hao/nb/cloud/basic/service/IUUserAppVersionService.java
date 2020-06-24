package cn.hao.nb.cloud.basic.service;

import cn.hao.nb.cloud.basic.entity.UUserAppVersion;
import cn.hao.nb.cloud.common.entity.Pg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户app版本 服务类11
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2020-06-14
 */
public interface IUUserAppVersionService extends IService<UUserAppVersion> {

    UUserAppVersion getByUserId(Long userId);

    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    UUserAppVersion addData(UUserAppVersion data);

    /**
     * 增量更新数据
     *
     * @param data
     * @return
     */
    boolean incrementModifyData(UUserAppVersion data);

    /**
     * 全量更新数据
     *
     * @param data
     * @return
     */
    boolean totalAmountModifyData(UUserAppVersion data);

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
    UUserAppVersion getDetail(Long id);

    /**
     * 分页查询数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage<UUserAppVersion> pageData(Pg pg, UUserAppVersion.SearchParams searchParams);

    /**
     * 列表查询数据
     *
     * @param searchParams
     * @return
     */
    List<UUserAppVersion> listData(UUserAppVersion.SearchParams searchParams);

    /**
     * 分页查询Map数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage
            <Map
                    <String, Object>> pageMapData(Pg pg, UUserAppVersion.SearchParams searchParams);

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    UUserAppVersion prepareReturnModel(UUserAppVersion data);

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    IPage<UUserAppVersion> prepareReturnModel(IPage<UUserAppVersion> page);

    /**
     * 处理返回值
     *
     * @param list
     * @return
     */
    List<UUserAppVersion> prepareReturnModel(List<UUserAppVersion> list);

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
    void validData(UUserAppVersion data);

}
/*

import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Rv;
import com.fgzy.mc.core.entity.UUserAppVersion;
import com.fgzy.mc.core.service.IUUserAppVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@Autowired
IUUserAppVersionService iUUserAppVersionService;

@ApiOperation(value = "添加用户app版本", notes = "添加用户app版本")
@PostMapping(value = "/uUserAppVersion/add")
public Rv addUUserAppVersion(UUserAppVersion data) {
return Rv.getInstance(iUUserAppVersionService.addData(data));
}

@ApiOperation(value = "修改用户app版本", notes = "修改用户app版本")
@PostMapping(value = "/uUserAppVersion/modify")
public Rv modifyUUserAppVersion(UUserAppVersion data) {
return Rv.getInstance(iUUserAppVersionService.incrementModifyData(data));
}

@ApiOperation(value = "删除用户app版本", notes = "删除用户app版本")
@PostMapping(value = "/uUserAppVersion/del/{id}")
public Rv delUUserAppVersion(@ApiParam(name = "id", value = "用户app版本id") @PathVariable Long id) {
return Rv.getInstance(iUUserAppVersionService.delData(id));
}

@ApiOperation(value = "查询用户app版本", notes = "查询用户app版本")
@GetMapping(value = "/uUserAppVersion/getById/{id}")
public Rv getUUserAppVersionById(@ApiParam(name = "id", value = "用户app版本id") @PathVariable Long id) {
return Rv.getInstance(iUUserAppVersionService.getDetail(id));
}

@ApiOperation(value = "分页查询用户app版本", notes = "分页查询用户app版本")
@GetMapping(value = "/uUserAppVersion/page")
public Rv pageUUserAppVersion(Pg pg,UUserAppVersion.SearchParams searchParams) {
return Rv.getInstance(iUUserAppVersionService.pageData(pg,searchParams));
}

@ApiOperation(value = "列表查询用户app版本", notes = "列表查询用户app版本")
@GetMapping(value = "/uUserAppVersion/list")
public Rv listUUserAppVersion(UUserAppVersion.SearchParams searchParams) {
return Rv.getInstance(iUUserAppVersionService.listData(searchParams));
}

@ApiOperation(value = "分页查询用户app版本(map数据)", notes = "列表查询用户app版本(map数据)")
@GetMapping(value = "/uUserAppVersion/pageMap")
public Rv pageMapUUserAppVersion(Pg pg,UUserAppVersion.SearchParams searchParams) {
return Rv.getInstance(iUUserAppVersionService.pageMapData(pg,searchParams));
}


*/