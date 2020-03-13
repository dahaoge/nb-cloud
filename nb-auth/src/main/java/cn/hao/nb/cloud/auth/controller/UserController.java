package cn.hao.nb.cloud.auth.controller;

import cn.hao.nb.cloud.auth.entity.UUserInfo;
import cn.hao.nb.cloud.auth.service.IUUserInfoService;
import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.common.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: hao
 * @Date: 2019-12-14 20:59
 * @Description:
 */
@Api(description = "用户接口")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUUserInfoService iUUserInfoService;

    @PreAuthorize("hasAuthority('user:mange')")
    @ApiOperation(value = "锁定账号")
    @PostMapping("/manage/uUserInfo/lockUser/{userId}")
    public Rv lockUser(@PathVariable Long userId) {
        return Rv.getInstance(iUUserInfoService.lockUser(userId));
    }

    @PreAuthorize("hasAuthority('user:mange')")
    @ApiOperation(value = "解除用户锁定")
    @PostMapping("/manage/uUserInfo/unLockUser/{userId}")
    public Rv unLockUser(@PathVariable Long userId) {
        return Rv.getInstance(iUUserInfoService.unLockUser(userId));
    }

    @ApiOperation(value = "修改用户基本信息,包含用户名&头像&身份证号等基本信息")
    @PostMapping("/uUserInfo/modify/userOrdinaryInfo")
    public Rv modifyUserOrdinaryInfo(UUserInfo userInfo) {
        return Rv.getInstance(iUUserInfoService.modifyOrdinaryInfo(userInfo));
    }

    @PreAuthorize("hasAuthority('user:mange')")
    @ApiOperation(value = "管理员修改用户手机号")
    @PostMapping("/manage/uUserInfo/modify/userPhone")
    public Rv manageModifyUserPhone(@RequestParam Long userId, @RequestParam String phone) {
        return Rv.getInstance(iUUserInfoService.modifyUserPhone(userId, phone));
    }

    @ApiOperation(value = "用户修改自己的手机号")
    @PostMapping("/uUserInfo/modify/phone")
    public Rv modifyPhone(@RequestParam String phone, @RequestParam String smsCheckCode) {
        return Rv.getInstance(iUUserInfoService.modifyUserPhone(UserUtil.getTokenUser(true).getUserId(), phone, smsCheckCode));
    }

    @ApiOperation(value = "初始化账号(loginId)")
    @PostMapping("/uUserInfo/loginId/init")
    public Rv initUserLoginId(@RequestParam String loginId) {
        return Rv.getInstance(iUUserInfoService.initLoginId(loginId));
    }

    @ApiOperation(value = "修改密码")
    @PostMapping("/uUserInfo/modify/selfPwd")
    public Rv modifySelfPwd(String oldPwd, String newPwd1, String newPwd2) {
        return Rv.getInstance(iUUserInfoService.modifySelfPwd(oldPwd, newPwd1, newPwd2));
    }

    @PreAuthorize("hasAuthority('user:mange')")
    @ApiOperation(value = "重置用户密码")
    @PostMapping("/manage/uUserInfo/resetUserPwd/{userId}")
    public Rv resetUserPwd(@PathVariable Long userId) {
        return Rv.getInstance(iUUserInfoService.managerResetUserPwd(userId));
    }

    @ApiOperation(value = "重置密码")
    @PostMapping("/uUserInfo/resetPwd")
    public Rv resetPwd() {
        return Rv.getInstance(iUUserInfoService.resetSelfPwd());
    }

    @ApiOperation(value = "查询用户信息", notes = "查询用户信息")
    @GetMapping(value = "/uUserInfo/getById/{id}")
    public Rv getUUserInfoById(@ApiParam(name = "id", value = "用户信息id") @PathVariable Long id) {
        return Rv.getInstance(iUUserInfoService.getDetail(id));
    }

    @ApiOperation(value = "分页查询用户信息", notes = "分页查询用户信息")
    @GetMapping(value = "/uUserInfo/page")
    public Rv pageUUserInfo(Pg pg, UUserInfo.SearchParams searchParams) {
        return Rv.getInstance(iUUserInfoService.pageData(pg, searchParams));
    }

    @ApiOperation(value = "列表查询用户信息", notes = "列表查询用户信息")
    @GetMapping(value = "/uUserInfo/list")
    public Rv listUUserInfo(UUserInfo.SearchParams searchParams) {
        return Rv.getInstance(iUUserInfoService.listData(searchParams));
    }

    @ApiOperation(value = "分页查询用户信息(map数据)", notes = "列表查询用户信息(map数据)")
    @GetMapping(value = "/uUserInfo/pageMap")
    public Rv pageMapUUserInfo(Pg pg, UUserInfo.SearchParams searchParams) {
        return Rv.getInstance(iUUserInfoService.pageMapData(pg, searchParams));
    }

}
