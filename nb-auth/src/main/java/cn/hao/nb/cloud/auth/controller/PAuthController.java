package cn.hao.nb.cloud.auth.controller;

import cn.hao.nb.cloud.auth.entity.UUserInfo;
import cn.hao.nb.cloud.auth.service.IULoginChannelService;
import cn.hao.nb.cloud.auth.service.IUUserInfoService;
import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.penum.ELoginType;
import cn.hao.nb.cloud.common.util.AliSmsUtil;
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.common.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: hao
 * @Date: 2019-12-12 16:43
 * @Description:
 */
@Api(description = "登录注册")
@Slf4j
@RestController
@RequestMapping("/pauth")
public class PAuthController {

    @Autowired
    IUUserInfoService iUUserInfoService;
    @Autowired
    AliSmsUtil smsUtil;
    @Autowired
    IULoginChannelService loginChannelService;

    @ApiOperation(value = "手机号或账号+密码登录(默认密码为手机号后8位)")
    @GetMapping("/login/byPwd")
    public Rv loginByPwd(@RequestParam String loginId, @RequestParam String pwd) {
        UUserInfo userInfo = iUUserInfoService.loginByPwd(loginId, UserUtil.aesPwd(pwd));
        return Rv.getInstance(iUUserInfoService.getLoginInfo(userInfo.getUserId()));
    }

    @ApiOperation(value = "短信验证码登录")
    @GetMapping("/login/bySmsCheckCode")
    public Rv loginBySmsCheckCode(@RequestParam String phone, @RequestParam String smsCheckCode) {
        UUserInfo userInfo = iUUserInfoService.loginByCheckSms(phone, smsCheckCode);
        return Rv.getInstance(iUUserInfoService.getLoginInfo(userInfo.getUserId()));
    }

    @ApiOperation(value = "添加管理员账号")
    @PostMapping("/manager/add")
    public Rv addManager(String phone, String loginId, String userName, String deptIds, String roleCodes) {
        return Rv.getInstance(iUUserInfoService.getLoginInfo(
                iUUserInfoService.addManager(phone, loginId, userName, deptIds, null, roleCodes).getUserId()));
    }

    @ApiOperation(value = "添加B端用户")
    @PostMapping("/bUser/add")
    public Rv addBUser(String phone, String loginId, String userName, String deptIds) {
        return Rv.getInstance(iUUserInfoService.getLoginInfo(iUUserInfoService.addBUser(phone, loginId, userName, deptIds, null).getUserId()));
    }

    @ApiOperation(value = "C端或B端用户手机号注册")
    @PostMapping("/bcUser/registByPhone")
    public Rv bcUserRegistByPhone(String phone, String smsCheckCode) {
        return Rv.getInstance(iUUserInfoService.registByPhone(phone, smsCheckCode));
    }

    @ApiOperation(value = "C端用户注册")
    @PostMapping("/bcUser/registByLoginId")
    public Rv bcUserRegistByLoginId(String loginId, String userName, String pwd1, String pwd2) {
        return Rv.getInstance(iUUserInfoService.registByLoginId(loginId, userName, pwd1, pwd2));
    }

    @ApiOperation(value = "获取登录信息(会刷新token)")
    @GetMapping("/refreshLoginInfo")
    public Rv refreshLoginInfo() {
        return Rv.getInstance(iUUserInfoService.getLoginInfo(UserUtil.getTokenUser(true).getUserId()));
    }

    /**
     * 获取手机验证码短信
     *
     * @return
     */
    @ApiOperation(value = "获取登录手机验证码短信", notes = "获取登录手机验证码短信")
    @GetMapping("/getLoginSms")
    public Rv getLoginSms(@RequestParam("phone") String phone) {
        if (CheckUtil.objIsEmpty(phone)) {
            throw NBException.create(EErrorCode.missingArg);
        }
        if (CheckUtil.objIsNotEmpty(loginChannelService.getByTypeAndChannelScope(phone, ELoginType.checkSms, UserUtil.getLoginChannelScop())))
            throw NBException.create(EErrorCode.noData, "请先注册");
        return Rv.getInstance(smsUtil.sendLoginCheckCode(phone));
    }

    /**
     * 获取手机验证码短信
     *
     * @return
     */
    @ApiOperation(value = "获取注册手机验证码短信", notes = "获取注册手机验证码短信")
    @GetMapping("/getRegisteSms")
    public Rv getRegisteSms(@RequestParam("phone") String phone) {
        if (CheckUtil.objIsEmpty(phone)) {
            throw NBException.create(EErrorCode.missingArg);
        }

        return Rv.getInstance(smsUtil.sendRegisteCheckCode(phone));
    }

    /**
     * 验证手机验证码短信
     *
     * @return
     */
    @ApiOperation(value = "验证手机验证码短信", notes = "验证手机验证码短信")
    @GetMapping("/checkSms")
    public Rv checkSms(@RequestParam("phone") String phone, @RequestParam("code") String code) {

        if (CheckUtil.objIsEmpty(phone, code)) {
            throw NBException.create(EErrorCode.missingArg);
        }

        return Rv.getInstance(smsUtil.checkSms(phone, code));
    }
}
