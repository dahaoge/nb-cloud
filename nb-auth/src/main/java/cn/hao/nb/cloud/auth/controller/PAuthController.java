package cn.hao.nb.cloud.auth.controller;

import cn.hao.nb.cloud.auth.entity.UUserInfo;
import cn.hao.nb.cloud.auth.service.IUUserInfoService;
import cn.hao.nb.cloud.common.entity.Rv;
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
@Api(description = "没啥用")
@Slf4j
@RestController
@RequestMapping("/pauth")
public class PAuthController {

    @Autowired
    IUUserInfoService userInfoService;

    @ApiOperation(value = "手机号+密码登录(默认密码为手机号后8位)")
    @GetMapping("/login/byPhonePwd")
    public Rv LoginByPhonePwd(@RequestParam(required = true) String phone, @RequestParam(required = true) String pwd) {
        UUserInfo userInfo = userInfoService.loginByPhoneAndPwd(phone, UserUtil.aesPwd(pwd));
        return Rv.getInstance(userInfoService.getLoginInfo(userInfo.getUserId()));
    }

    @ApiOperation(value = "姓名+手机号注册(添加)C端用户")
    @PostMapping("/regist/clientUser/byPhone")
    public Rv clientUserRegistByPhone(@RequestParam(required = true) String phone, @RequestParam(required = true) String userName) {
        return Rv.getInstance(userInfoService.getLoginInfo(userInfoService.clientUserRegistByPhone(phone, userName).getUserId()));
    }

    @ApiOperation(value = "姓名+手机号+组织机构编码注册(添加)C端用户")
    @PostMapping("/regist/clientUser/byPhoneWithDept")
    public Rv clientUserRegistByPhone(@RequestParam(required = true) String phone, @RequestParam(required = true) String userName, String deptIds) {
        return Rv.getInstance(userInfoService.getLoginInfo(userInfoService.clientUserRegistByPhone(phone, userName, deptIds).getUserId()));
    }

    @ApiOperation(value = "姓名+手机号+组织机构+角色编码注册(添加)管理员用户")
    @PostMapping("/regist/webManager/byPudr")
    public Rv webManagerRegistByPhone(@RequestParam(required = true) String phone, @RequestParam(required = true) String userName,
                                      String deptIds, String roleCodes) {
        return Rv.getInstance(userInfoService.getLoginInfo(userInfoService.webManagerRegistByPhone(phone, userName, deptIds, roleCodes).getUserId()));
    }

    @ApiOperation(value = "获取登录信息(会刷新token)")
    @GetMapping("/getLoginInfo")
    public Rv getLoginInfo() {
        return Rv.getInstance(userInfoService.getLoginInfo(UserUtil.getTokenUser(true).getUserId()));
    }


//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")
//    @ApiOperation(value="")


}
