package cn.hao.nb.cloud.auth.service;

import cn.hao.nb.cloud.auth.entity.UUserInfo;
import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Qd;
import cn.hao.nb.cloud.common.penum.EYn;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户信息 服务类11
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
public interface IUUserInfoService extends IService<UUserInfo> {

    UUserInfo clientUserRegistByPhone(String phone, String userName);

    UUserInfo clientUserRegistByPhone(String phone, String userName, String deptIds);

    UUserInfo webManagerRegistByPhone(String phone, String userName, String deptIds, String roleCodes);

    UUserInfo loginByPhoneAndPwd(String phone, String pwd);

    Qd getLoginInfo(String userId);

    boolean changeUserLock(String userId, EYn isLocked);

    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    UUserInfo addData(UUserInfo data);

    /**
     * 增量更新数据
     * @param data
     * @return
     */
    boolean incrementModifyData(UUserInfo data);

    /**
     * 全量更新数据
     *
     * @param data
     * @return
     */
    boolean totalAmountModifyData(UUserInfo data);

    boolean modifyNormalInfo(UUserInfo data);

    boolean modifyUserPhone(String userId, String phone);

    boolean modifyUserPhone(String userId, String phone, String smsCheckCode);

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    boolean delData(String id);

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    UUserInfo getDetail(String id);

    UUserInfo getOtherExistUserByPhone(String userId, String phone);

    UUserInfo getExistUserByPhone(String phone);

    /**
     * 分页查询数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage<UUserInfo> pageData(Pg pg, UUserInfo.SearchParams searchParams);

    /**
     * 列表查询数据
     *
     * @param searchParams
     * @return
     */
    List<UUserInfo> listData(UUserInfo.SearchParams searchParams);

    /**
     * 分页查询Map数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage
            <Map
                    <String, Object>> pageMapData(Pg pg, UUserInfo.SearchParams searchParams);

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    UUserInfo prepareReturnModel(UUserInfo data);

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    IPage<UUserInfo> prepareReturnModel(IPage<UUserInfo> page);

    /**
     * 处理返回值
     *
     * @param list
     * @return
     */
    List<UUserInfo> prepareReturnModel(List<UUserInfo> list);

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
    void validData(UUserInfo data);

    void validUserPhone(UUserInfo data);

}
/*

import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Rv;
import com.fgzy.mc.core.entity.UUserInfo;
import com.fgzy.mc.core.service.IUUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@Autowired
IUUserInfoService iUUserInfoService;

@ApiOperation(value = "添加用户信息", notes = "添加用户信息")
@PostMapping(value = "/uUserInfo/add")
public Rv addUUserInfo(UUserInfo data) {
return Rv.getInstance(iUUserInfoService.addData(data));
}

@ApiOperation(value = "修改用户信息", notes = "修改用户信息")
@PostMapping(value = "/uUserInfo/modify")
public Rv modifyUUserInfo(UUserInfo data) {
return Rv.getInstance(iUUserInfoService.modifyData(data));
}

@ApiOperation(value = "删除用户信息", notes = "删除用户信息")
@PostMapping(value = "/uUserInfo/del/{id}")
public Rv delUUserInfo(@ApiParam(name = "id", value = "用户信息id") @PathVariable String id) {
return Rv.getInstance(iUUserInfoService.delData(id));
}

@ApiOperation(value = "查询用户信息", notes = "查询用户信息")
@GetMapping(value = "/uUserInfo/getById/{id}")
public Rv getUUserInfoById(@ApiParam(name = "id", value = "用户信息id") @PathVariable String id) {
return Rv.getInstance(iUUserInfoService.getDetail(id));
}

@ApiOperation(value = "分页查询用户信息", notes = "分页查询用户信息")
@GetMapping(value = "/uUserInfo/page")
public Rv pageUUserInfo(Pg pg,UUserInfo.SearchParams searchParams) {
return Rv.getInstance(iUUserInfoService.pageData(pg,searchParams));
}

@ApiOperation(value = "列表查询用户信息", notes = "列表查询用户信息")
@GetMapping(value = "/uUserInfo/list")
public Rv listUUserInfo(UUserInfo.SearchParams searchParams) {
return Rv.getInstance(iUUserInfoService.listData(searchParams));
}

@ApiOperation(value = "分页查询用户信息(map数据)", notes = "列表查询用户信息(map数据)")
@GetMapping(value = "/uUserInfo/pageMap")
public Rv pageMapUUserInfo(Pg pg,UUserInfo.SearchParams searchParams) {
return Rv.getInstance(iUUserInfoService.pageMapData(pg,searchParams));
}


*/