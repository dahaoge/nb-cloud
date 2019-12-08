package cn.hao.nb.cloud.auth.controller;

import cn.hao.nb.cloud.auth.entity.UUserInfo;
import cn.hao.nb.cloud.auth.mapper.AuthMapper;
import cn.hao.nb.cloud.auth.service.IUUserInfoService;
import cn.hao.nb.cloud.common.component.config.security.JwtTokenUtil;
import cn.hao.nb.cloud.common.component.props.SecurityProps;
import cn.hao.nb.cloud.common.constant.SecurityConstants;
import cn.hao.nb.cloud.common.entity.*;
import cn.hao.nb.cloud.common.penum.ESourceClient;
import cn.hao.nb.cloud.common.util.AesUtil;
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.common.util.UserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Map;

/**
 * @Auther: hao
 * @Date: 2019-11-22 17:29
 * @Description:
 */
@Api(description = "没啥用")
@Slf4j
@RestController
@RequestMapping("/temp")
public class TempController {

    @Autowired
    IUUserInfoService userInfoService;
    @Autowired
    SecurityProps securityProps;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    AuthMapper authMapper;

    @GetMapping("/test/{str}")
    public String test(@PathVariable String str) {
        return "hellow " + str;
    }

    @GetMapping("/getUserByPhone/{phone}")
    public Object getUserByPhone(@PathVariable String phone) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like(UUserInfo.PHONE, phone);
        return userInfoService.page(Pg.page(1, 10), queryWrapper);
    }

    /**
     * 获取登录信息
     *
     * @param phone
     * @param sourceClient
     * @return
     */
    @GetMapping("/genLoginInfoByPhoneOrUserId")
    public Rv genLoginInfo(String phone, ESourceClient sourceClient, String userId) {
        Qd result = Qd.create();
        userId = CheckUtil.objIsEmpty(userId) ? userInfoService.getOne(Qw.create().eq(UUserInfo.PHONE, phone)).getUserId() : userId;
        TokenUser tokenUser = authMapper.getTokenUserById(userId);
        Map<String, String> sourceEntity = null;
        for (String key : securityProps.getSourceClients().keySet()) {
            if (sourceClient.getValue().equals(securityProps.getSourceClients().get(key).get(SecurityConstants.SOURCE_VERIFICATION_ENTITY_NAME_KEY))) {
                sourceEntity = securityProps.getSourceClients().get(key);
            }
        }
        String ak = sourceEntity.get(SecurityConstants.SOURCE_VERIFICATION_ENTITY_AK_KEY);
        String sk = sourceEntity.get(SecurityConstants.SOURCE_VERIFICATION_ENTITY_SK_KEY);
        long timeSalt = Calendar.getInstance().getTimeInMillis() + 7200000;
        String sign = AesUtil.encrypt(sk + "$$" + timeSalt, sk);
        result.add(SecurityConstants.HEADER_SOURCE_AK_KEY, ak)
                .add(SecurityConstants.HEADER_SOURCE_SIGN_KEY, sign)
                .add(SecurityConstants.HEADER_TOKEN_KEY, SecurityConstants.HEADER_TOKEN_SPLIT + (String) jwtTokenUtil.generateToken(tokenUser));
        return Rv.getInstance(result);
    }

    @GetMapping("/LoginByPhonePwd")
    public Rv LoginByPhonePwd(String phone, String pwd) {
        UUserInfo userInfo = userInfoService.loginByPhoneAndPwd(phone, UserUtil.aesPwd(pwd));
        return Rv.getInstance(userInfoService.getLoginInfo(userInfo.getUserId()));
    }

}
