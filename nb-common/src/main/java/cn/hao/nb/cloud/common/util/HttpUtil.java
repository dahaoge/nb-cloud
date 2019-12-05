package cn.hao.nb.cloud.common.util;

import cn.hao.nb.common.entity.NBException;
import cn.hao.nb.common.penum.EErrorCode;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HttpUtil {

    /**
     * 获取head参数
     *
     * @param request
     * @param targetKey
     * @return
     */
    public static String getHeaderParam(ServletRequest request, String targetKey) {
        if (CheckUtil.objIsEmpty(request) || CheckUtil.strIsEmpty(targetKey))
            return null;
        HttpServletRequest req = (HttpServletRequest) request;
        Enumeration<String> en = req.getHeaderNames();
        targetKey = targetKey.toLowerCase();
        String value = null;
        while (en.hasMoreElements()) {
            String key = en.nextElement();
            if (targetKey.equals(key.toLowerCase()))
                value = req.getHeader(key);
        }
        return value;
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @param request
     * @return
     * @throws IOException
     */
    public final static String getIpAddress(HttpServletRequest request) throws IOException {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * 获取类型和后缀
     *
     * @param url
     * @return
     */
    public static String[] getContentAndRealUrl(String url) {
        if (CheckUtil.strIsEmpty(url) && url.length() <= 1) {
            throw NBException.create(EErrorCode.dataParseError);
        }

        int i = url.indexOf("/", 1);
        if (i < 0) {
            return new String[]{url, ""};
        }

        return new String[]{url.substring(0, i), url.substring(i)};
    }

    public static boolean isUrl(String str) {
        // URL验证规则
        String regEx = "[a-zA-z]+://[^\\\\s]*";
        ;
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();
        return rs;
    }
}
