package cn.hao.nb.cloud.common.util;

import cn.hao.nb.cloud.common.component.SpringUtil;
import cn.hao.nb.cloud.common.component.props.SecurityProps;
import cn.hao.nb.cloud.common.constant.SecurityConstants;
import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.penum.ESourceClient;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Map;

/**
 * @Auther: hao
 * @Date: 2019-12-06 20:46
 * @Description:
 */
public class SecurityUtil {

    public static ESourceClient getAndValidRequestClient(HttpServletRequest httpServletRequest) {
        String sourceAk = httpServletRequest.getHeader(SecurityConstants.HEADER_SOURCE_AK_KEY);
        String sourceSign = httpServletRequest.getHeader(SecurityConstants.HEADER_SOURCE_SIGN_KEY);
        return SecurityUtil.getAndValidRequestClient(sourceAk, sourceSign);
    }

    public static ESourceClient getAndValidRequestClient(String sourceAk, String sourceSign) {
        Map<String, String> sourceClientMap = SpringUtil.getBean(SecurityProps.class).getSourceClients().get(sourceAk);
        String client = sourceClientMap.get("name");
        String sk = sourceClientMap.get("sk");
        SecurityUtil.validSourceSign(sk, sourceSign);
        return ESourceClient.valueOf(client);
    }

    public static void validSourceSign(String sk, String sign) {
        if (CheckUtil.objIsEmpty(sk, sign))
            throw NBException.create(EErrorCode.missingAuthArgs);
        try {
            String decodeSign = AesUtil.decrypt(sign, sk);
            if (CheckUtil.objIsEmpty(decodeSign) || decodeSign.indexOf("$$") < 0)
                throw NBException.create(EErrorCode.authDecodeError);
            String[] ss = decodeSign.split("$$");
            if (Long.parseLong(ss[1]) < Calendar.getInstance().getTimeInMillis())
                throw NBException.create(EErrorCode.authDenied, "权限已过期");
            if (!sk.equals(ss[0]))
                throw NBException.create(EErrorCode.authDecodeError);

        } catch (Exception e) {
            throw NBException.create(EErrorCode.authDecodeError);
        }
    }

}
