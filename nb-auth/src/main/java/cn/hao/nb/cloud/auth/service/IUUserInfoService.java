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

    /**
     * 绑定用户公司
     *
     * @param userId
     * @param companyId
     * @return
     */
    boolean bindUser2Company(Long userId, Long companyId);

    /**
     * C端用户注册(添加)
     *
     * @param phone
     * @param userName
     * @return
     */
    UUserInfo addCUser(String phone, String loginId, String userName, String pwd);

    /**
     * B端用户注册(添加)
     * @param phone
     * @param userName
     * @param deptIds
     * @return
     */
    UUserInfo addBUser(String phone, String loginId, String userName, String deptIds, String pwd);

    /**
     * 管理端用户注册(添加)
     * @param phone
     * @param userName
     * @param deptIds
     * @param roleCodes
     * @return
     */
    UUserInfo addManager(String phone, String loginId, String userName, String deptIds, String pwd, String roleCodes);

    /**
     * 根据手机号和密码登录
     * @param phone
     * @param pwd
     * @return
     */
    UUserInfo loginByPwd(String loginId, String pwd);

    UUserInfo loginByCheckSms(String phone, String smsCheckCode);

    /**
     * 获取登录信息(会刷新token)
     * @param userId
     * @return
     */
    Qd getLoginInfo(Long userId);

    /**
     * 改变用户锁定状态
     * @param userId
     * @param isLocked
     * @return
     */
    boolean changeUserLock(Long userId, EYn isLocked);

    /**
     * 锁定用户账号
     *
     * @param userId
     * @return
     */
    boolean lockUser(Long userId);

    /**
     * 解锁用户账号
     *
     * @param userId
     * @return
     */
    boolean unLockUser(Long userId);

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

    /**
     * 修改用户基本信息
     * @param data
     * @return
     */
    boolean modifyOrdinaryInfo(UUserInfo data);

    /**
     * 修改用户手机号
     * @param userId
     * @param phone
     * @return
     */
    boolean modifyUserPhone(Long userId, String phone);

    /**
     * 根据短信验证码修改用户手机号
     * @param userId
     * @param phone
     * @param smsCheckCode
     * @return
     */
    boolean modifyUserPhone(Long userId, String phone, String smsCheckCode);

    /**
     * 初始化登录id(登录id一旦添加无法修改)
     *
     * @param loginId
     * @return
     */
    boolean initLoginId(String loginId);

    boolean modifySelfPwd(String oldPwd, String newPwd1, String newPwd2);

    boolean managerResetUserPwd(Long userId);

    boolean resetSelfPwd();

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
    UUserInfo getDetail(Long id);

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

    /**
     * 验证用户手机号是否可用
     * @param data
     */
    void validUserPhone(UUserInfo data);

    /**
     * 验证明文密码是否匹配登录账号
     *
     * @param pwd
     */
    boolean isMatchDBPwd(String pwd);

    /**
     * 验证明文密码是否匹配登录账号
     *
     * @param userId
     * @param pwd
     * @return
     */
    boolean isMatchDBPwd(Long userId, String pwd);

    boolean isMatchDBPwd(UUserInfo userInfo,String pwd);

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
public Rv delUUserInfo(@ApiParam(name = "id", value = "用户信息id") @PathVariable Long id) {
return Rv.getInstance(iUUserInfoService.delData(id));
}

@ApiOperation(value = "查询用户信息", notes = "查询用户信息")
@GetMapping(value = "/uUserInfo/getById/{id}")
public Rv getUUserInfoById(@ApiParam(name = "id", value = "用户信息id") @PathVariable Long id) {
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