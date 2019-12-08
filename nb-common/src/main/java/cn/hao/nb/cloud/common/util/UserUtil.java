package cn.hao.nb.cloud.common.util;

import cn.hao.nb.cloud.common.component.SpringUtil;
import cn.hao.nb.cloud.common.component.config.security.JwtTokenUtil;
import cn.hao.nb.cloud.common.component.props.SecurityProps;
import cn.hao.nb.cloud.common.constant.SecurityConstants;
import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.TokenUser;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.penum.ESourceClient;
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
public class UserUtil {

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
        return UserUtil.getAndValidRequestClient(sourceAk, sourceSign);
    }

    /**
     * 获取并验证请求来源
     * @param sourceAk
     * @param sourceSign
     * @return
     */
    public static ESourceClient getAndValidRequestClient(String sourceAk, String sourceSign) {
        Map<String, String> sourceClientMap = SpringUtil.getBean(SecurityProps.class).getSourceClients().get(sourceAk);
        String client = sourceClientMap.get("name");
        String sk = sourceClientMap.get("sk");
        UserUtil.validSourceSign(sk, sourceSign);
        return ESourceClient.valueOf(client);
    }

    /**
     * 获取并验证请求来源
     * @param sk
     * @param sign
     */
    public static void validSourceSign(String sk, String sign) {
        if (CheckUtil.objIsEmpty(sk, sign)) {
            throw NBException.create(EErrorCode.missingAuthArgs);
        }
        try {
            String decodeSign = AesUtil.decrypt(sign, sk);
            if (CheckUtil.objIsEmpty(decodeSign) || decodeSign.indexOf("$$") < 0)
                throw NBException.create(EErrorCode.authDecodeError);
            String[] ss = decodeSign.split("$$");
            if (Long.parseLong(ss[1]) < Calendar.getInstance().getTimeInMillis()) {
                throw NBException.create(EErrorCode.authDenied, "权限已过期");
            }
            if (!sk.equals(ss[0])) {
                throw NBException.create(EErrorCode.authDecodeError);
            }

        } catch (Exception e) {
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
