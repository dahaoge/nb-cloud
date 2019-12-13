package cn.hao.nb.cloud.auth.service;

import cn.hao.nb.cloud.auth.entity.ULoginChannel;
import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.penum.ELoginChannelScop;
import cn.hao.nb.cloud.common.penum.ELoginType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 登录渠道 服务类11
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
public interface IULoginChannelService extends IService<ULoginChannel> {


    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    ULoginChannel addData(ULoginChannel data);

    ULoginChannel addLoginChannel(String userId, ELoginType loginType, String loginId, ELoginChannelScop loginChannelScop);

    boolean addPhoneChannel(String userId, String phone, ELoginChannelScop loginChannelScop);

    /**
     * 修改数据
     *
     * @param data
     * @return
     */
    boolean modifyData(ULoginChannel data);

    boolean modifyLoginId(ULoginChannel data);

    boolean modifyLoginId(String tId, String loginId, String userId);

    boolean modifyLoginId(String tId, String loginId);

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    boolean delData(String id);

    boolean delByUserId(String userId);

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    ULoginChannel getDetail(String id);

    ULoginChannel getByTypeAndChannelScope(String loginId, ELoginType loginType, ELoginChannelScop loginChannelScop);

    /**
     * 分页查询数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage<ULoginChannel> pageData(Pg pg, ULoginChannel.SearchParams searchParams);

    /**
     * 列表查询数据
     *
     * @param searchParams
     * @return
     */
    List<ULoginChannel> listData(ULoginChannel.SearchParams searchParams);

    List<ULoginChannel> listByUserId(String userId);

    /**
     * 分页查询Map数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage
            <Map
                    <String, Object>> pageMapData(Pg pg, ULoginChannel.SearchParams searchParams);

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    ULoginChannel prepareReturnModel(ULoginChannel data);

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    IPage<ULoginChannel> prepareReturnModel(IPage<ULoginChannel> page);

    /**
     * 处理返回值
     *
     * @param list
     * @return
     */
    List<ULoginChannel> prepareReturnModel(List<ULoginChannel> list);

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
    void validData(ULoginChannel data);

    void validLoginId(ULoginChannel data);

}
/*

import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Rv;
import com.fgzy.mc.core.entity.ULoginChannel;
import com.fgzy.mc.core.service.IULoginChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@Autowired
IULoginChannelService iULoginChannelService;

@ApiOperation(value = "添加登录渠道", notes = "添加登录渠道")
@PostMapping(value = "/uLoginChannel/add")
public Rv addULoginChannel(ULoginChannel data) {
return Rv.getInstance(iULoginChannelService.addData(data));
}

@ApiOperation(value = "修改登录渠道", notes = "修改登录渠道")
@PostMapping(value = "/uLoginChannel/modify")
public Rv modifyULoginChannel(ULoginChannel data) {
return Rv.getInstance(iULoginChannelService.modifyData(data));
}

@ApiOperation(value = "删除登录渠道", notes = "删除登录渠道")
@PostMapping(value = "/uLoginChannel/del/{id}")
public Rv delULoginChannel(@ApiParam(name = "id", value = "登录渠道id") @PathVariable String id) {
return Rv.getInstance(iULoginChannelService.delData(id));
}

@ApiOperation(value = "查询登录渠道", notes = "查询登录渠道")
@GetMapping(value = "/uLoginChannel/getById/{id}")
public Rv getULoginChannelById(@ApiParam(name = "id", value = "登录渠道id") @PathVariable String id) {
return Rv.getInstance(iULoginChannelService.getDetail(id));
}

@ApiOperation(value = "分页查询登录渠道", notes = "分页查询登录渠道")
@GetMapping(value = "/uLoginChannel/page")
public Rv pageULoginChannel(Pg pg,ULoginChannel.SearchParams searchParams) {
return Rv.getInstance(iULoginChannelService.pageData(pg,searchParams));
}

@ApiOperation(value = "列表查询登录渠道", notes = "列表查询登录渠道")
@GetMapping(value = "/uLoginChannel/list")
public Rv listULoginChannel(ULoginChannel.SearchParams searchParams) {
return Rv.getInstance(iULoginChannelService.listData(searchParams));
}

@ApiOperation(value = "分页查询登录渠道(map数据)", notes = "列表查询登录渠道(map数据)")
@GetMapping(value = "/uLoginChannel/pageMap")
public Rv pageMapULoginChannel(Pg pg,ULoginChannel.SearchParams searchParams) {
return Rv.getInstance(iULoginChannelService.pageMapData(pg,searchParams));
}


*/