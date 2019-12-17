package cn.hao.nb.cloud.common.util;

import cn.hao.nb.cloud.common.component.SpringUtil;
import cn.hao.nb.cloud.common.component.config.security.JwtTokenUtil;
import cn.hao.nb.cloud.common.component.props.SecurityProps;
import cn.hao.nb.cloud.common.constant.SecurityConstants;
import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.TokenUser;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.penum.ELoginChannelScop;
import cn.hao.nb.cloud.common.penum.ESourceClient;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Map;

/**
 * @Auther: hao
 * @Date: 2019-12-06 20:46
 * @Description:
 */
@Slf4j
public class UserUtil {

    public static long PWD_EXPIRE_TIME = 300000;
    public static long SOURCE_SIGN_EXPIRE_TIME = 86400000;

    public static String encodePwd(String pwd, String salt) {
        if (CheckUtil.objIsEmpty(pwd, salt))
            throw NBException.create(EErrorCode.missingArg);
        return MD5Util.MD5(pwd + salt).toUpperCase();
    }

    public static String decodePwd(String aesPwd, String salt) {
        if (CheckUtil.objIsEmpty(aesPwd, salt))
            throw NBException.create(EErrorCode.missingArg);
        String sk = UserUtil.getSourceVerificationEntity().get(SecurityConstants.SOURCE_VERIFICATION_ENTITY_SK_KEY);
        String decodeStr = AesUtil.decrypt(aesPwd, sk);
        if (decodeStr.indexOf("$$") < 0)
            throw NBException.create(EErrorCode.authIdentityErr);
        String[] ss = decodeStr.split("\\$\\$");
        if (Long.parseLong(ss[1]) < (Calendar.getInstance().getTimeInMillis() - UserUtil.PWD_EXPIRE_TIME))
            throw NBException.create(EErrorCode.authIdentityErr, "认证信息已过期");
        return UserUtil.encodePwd(ss[0], salt);
    }

    // 测试用
    public static String aesPwd(String pwd) {
        String sk = UserUtil.getSourceVerificationEntity().get(SecurityConstants.SOURCE_VERIFICATION_ENTITY_SK_KEY);
        return AesUtil.encrypt(pwd + "$$" + Calendar.getInstance().getTimeInMillis(), sk);
    }

    /**
     * 获取来源加密参数
     *
     * @param httpServletRequest
     * @return
     */
    public static Map<String, String> getSourceVerificationEntity(HttpServletRequest httpServletRequest) {
        String sourceAk = httpServletRequest.getHeader(SecurityConstants.HEADER_SOURCE_AK_KEY);
        if (CheckUtil.objIsEmpty(sourceAk))
            throw NBException.create(EErrorCode.missingAuthArgs);
        Map<String, String> result = SpringUtil.getBean(SecurityProps.class).getSourceClients().get(sourceAk);
        if (CheckUtil.objIsEmpty(result))
            throw NBException.create(EErrorCode.authIdentityErr, "无法获取签名");
        return result;
    }

    /**
     * 获取来源加密参数
     *
     * @return
     */
    public static Map<String, String> getSourceVerificationEntity() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw NBException.create(EErrorCode.c500);
        }
        return UserUtil.getSourceVerificationEntity(requestAttributes.getRequest());
    }


    /**
     * 获取并验证请求来源
     *
     * @return
     */
    public static ESourceClient getAndValidRequestClient() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw NBException.create(EErrorCode.c500);
        }
        return UserUtil.getAndValidRequestClient(requestAttributes.getRequest());
    }

    /**
     * 获取并验证请求来源
     *
     * @param httpServletRequest
     * @return
     */
    public static ESourceClient getAndValidRequestClient(HttpServletRequest httpServletRequest) {
        String sourceAk = httpServletRequest.getHeader(SecurityConstants.HEADER_SOURCE_AK_KEY);
        String sourceSign = httpServletRequest.getHeader(SecurityConstants.HEADER_SOURCE_SIGN_KEY);
        log.debug("获取并验证请求来源-->sourceAk:{}----sourceSign:{}", sourceAk, sourceSign);
        return UserUtil.getAndValidRequestClient(sourceAk, sourceSign);
    }

    public static ELoginChannelScop getLoginChannelScop(HttpServletRequest httpServletRequest) {
        ESourceClient sourceClient = UserUtil.getAndValidRequestClient(httpServletRequest);
        for (ELoginChannelScop loginChannelScop : ELoginChannelScop.class.getEnumConstants()) {
            if (loginChannelScop.getClients().contains(sourceClient))
                return loginChannelScop;
        }
        throw NBException.create(EErrorCode.authIdentityErr, "未获取到登录渠道");
    }

    public static ELoginChannelScop getLoginChannelScop() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw NBException.create(EErrorCode.c500);
        }
        return UserUtil.getLoginChannelScop(requestAttributes.getRequest());
    }

    /**
     * 获取并验证请求来源
     *
     * @param sourceAk
     * @param sourceSign
     * @return
     */
    public static ESourceClient getAndValidRequestClient(String sourceAk, String sourceSign) {
        if (CheckUtil.objIsEmpty(sourceAk, sourceSign))
            throw NBException.create(EErrorCode.missingAuthArgs);
        log.debug("获取并验证请求来源-->sourceAk:{}----sourceSign:{}", sourceAk, sourceSign);
        Map<String, String> sourceClientMap = SpringUtil.getBean(SecurityProps.class).getSourceClients().get(sourceAk);
        String client = sourceClientMap.get(SecurityConstants.SOURCE_VERIFICATION_ENTITY_NAME_KEY);
        String sk = sourceClientMap.get(SecurityConstants.SOURCE_VERIFICATION_ENTITY_SK_KEY);
        UserUtil.validSourceSign(sk, sourceSign);
        return ESourceClient.valueOf(client);
    }

    /**
     * 获取并验证请求来源
     *
     * @param sk
     * @param sign
     */
    public static void validSourceSign(String sk, String sign) {
        if (CheckUtil.objIsEmpty(sk, sign)) {
            throw NBException.create(EErrorCode.missingAuthArgs);
        }
        String decodeSign = null;
        try {
            decodeSign = AesUtil.decrypt(sign, sk);
        } catch (Exception e) {
            throw NBException.create(EErrorCode.authDecodeError);
        }
        log.debug("获取并验证请求来源--->decodeSign:{}", decodeSign);
        if (CheckUtil.objIsEmpty(decodeSign) || decodeSign.indexOf("$$") < 0)
            throw NBException.create(EErrorCode.authDecodeError);
        String[] ss = decodeSign.split("\\$\\$");
        log.debug("获取并验证请求来源--->decodeSign.split('$$'):{}", JSON.toJSONString(ss));
        if (Long.parseLong(ss[1]) < (Calendar.getInstance().getTimeInMillis() - UserUtil.SOURCE_SIGN_EXPIRE_TIME)) {
            throw NBException.create(EErrorCode.authDenied, "请求头参数过期");
        }
        if (!sk.equals(ss[0])) {
            throw NBException.create(EErrorCode.authDecodeError);
        }
    }

    public static TokenUser getTokenUser(boolean required) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw NBException.create(EErrorCode.c500);
        }


        String authorization = requestAttributes.getRequest().getHeader(SecurityConstants.HEADER_TOKEN_KEY);

        if (!required && CheckUtil.strIsEmpty(authorization)) {
            return null;
        }

        TokenUser tokenUser = UserUtil.getTokenUser(requestAttributes.getRequest());

        return tokenUser;
    }

    public static TokenUser getTokenUser() {

        return UserUtil.getTokenUser(false);
    }

    /**
     * 从request中获取到tokenUser
     *
     * @param httpServletRequest
     * @return
     */
    public static TokenUser getTokenUser(HttpServletRequest httpServletRequest) {

        String token = getToken(httpServletRequest);

        return JwtTokenUtil.getUserFromToken(token);
    }

    /**
     * 获取请求中token
     *
     * @param httpServletRequest request
     * @return token
     */
    public static String getToken(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader(SecurityConstants.HEADER_TOKEN_KEY);

        if (CheckUtil.strIsEmpty(authorization)) {
            throw NBException.create(EErrorCode.missingAuthArgs);
        }

        String token = StringUtils.substringAfter(authorization, SecurityConstants.HEADER_TOKEN_SPLIT);

        if (CheckUtil.strIsEmpty(token)) {
            throw NBException.create(EErrorCode.authErr);
        }

        return token;
    }

}
