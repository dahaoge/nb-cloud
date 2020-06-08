package cn.hao.nb.cloud.common.util;

import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class HttpUtil {

    public static RestTemplate getRestTemplate() {
        HttpComponentsClientHttpRequestFactory factory = HttpUtil.clientHttpRequestFactory;
        if (CheckUtil.objIsEmpty(factory)) {
            TrustStrategy acceptingTrustStrategy = (x509Certificates, authType) -> true;
            SSLContext sslContext = null;
            try {
                sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            } catch (Exception e) {
                throw NBException.create(EErrorCode.c500, "请求创建失败");
            }
            SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());

            HttpClientBuilder httpClientBuilder = HttpClients.custom();
            httpClientBuilder.setSSLSocketFactory(connectionSocketFactory);
            CloseableHttpClient httpClient = httpClientBuilder.build();
            factory = new HttpComponentsClientHttpRequestFactory();
            factory.setHttpClient(httpClient);
            factory.setConnectTimeout(15000);
            factory.setReadTimeout(5000);
            HttpUtil.clientHttpRequestFactory = factory;
        }

        return new RestTemplate(factory);
    }

    public static HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = null;


    public static Rv httpGetRv(String requestUrl, Map<String, Object> params) {
        Rv resp = HttpUtil.httpGet(requestUrl, params, Rv.class);
        if (EErrorCode.success.getValue() != resp.getCode())
            throw NBException.create(EErrorCode.apiErr, resp.getMsg()).plusMsg("errorCode:" + resp.getCode());
        return resp;
    }

    public static <T> T httpGet(String requestUrl, Map<String, Object> params, Class<T> clazz) {
        if (CheckUtil.strIsEmpty(requestUrl))
            throw NBException.create(EErrorCode.missingArg).plusMsg("requestUrl");
        if (CheckUtil.objIsEmpty(clazz))
            throw NBException.create(EErrorCode.missingArg).plusMsg("clazz");

        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity httpEntity = new HttpEntity(null, headers);
        requestUrl = HttpUtil.preGetParams(requestUrl, params);
        log.info(requestUrl);
        ResponseEntity responseEntity = HttpUtil.getRestTemplate().exchange(requestUrl, HttpMethod.GET, httpEntity, clazz);
        if (responseEntity.getStatusCodeValue() != 200)
            throw NBException.create(EErrorCode.apiErr, "调用第三方服务失败").plusMsg(responseEntity.getStatusCodeValue() + "");
        return (T) responseEntity.getBody();
    }

    public static Rv httpPostRv(String requestUrl, Map<String, Object> params) {
        Rv resp = HttpUtil.httpPost(requestUrl, params, Rv.class);
        if (EErrorCode.success.getValue() != resp.getCode())
            throw NBException.create(EErrorCode.apiErr, resp.getMsg()).plusMsg("errorCode:" + resp.getCode());
        return resp;
    }

    public static <T> T httpPost(String requestUrl, Map<String, Object> params, Class<T> clazz) {
        if (CheckUtil.strIsEmpty(requestUrl))
            throw NBException.create(EErrorCode.missingArg).plusMsg("requestUrl");
        if (CheckUtil.objIsEmpty(clazz))
            throw NBException.create(EErrorCode.missingArg).plusMsg("clazz");
        MultiValueMap<String, Object> p = null;
        if (CheckUtil.objIsNotEmpty(params)) {
            if (MultiValueMap.class.isAssignableFrom(params.getClass()))
                p = (MultiValueMap) params;
            else {
                p = new LinkedMultiValueMap<String, Object>();
                for (String key : params.keySet()) {
                    p.add(key, params.get(key));
                }
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity httpEntity = new HttpEntity<MultiValueMap<String, Object>>(p, headers);
        log.info(requestUrl);
        ResponseEntity responseEntity = HttpUtil.getRestTemplate().exchange(requestUrl, HttpMethod.POST, httpEntity, clazz);
        if (responseEntity.getStatusCodeValue() != 200)
            throw NBException.create(EErrorCode.apiErr, "调用第三方服务失败").plusMsg(responseEntity.getStatusCodeValue() + "");
        return (T) responseEntity.getBody();
    }

    public static String preGetParams(String url, Map params) {
        if (CheckUtil.objIsEmpty(params) || CheckUtil.collectionIsEmpty(params.keySet()))
            return url;
        List list = Lists.newArrayList();
        params.keySet().forEach(key -> {
            if (params.get(key) instanceof Date) {
                params.put(key, DateUtil.format((Date) params.get(key), "yyyy-MM-dd HH:mm:ss"));
            }
            if (CheckUtil.objIsNotEmpty(params.get(key))) {
                try {
                    list.add(key.toString().concat("=".concat(URLEncoder.encode(params.get(key).toString(), "UTF-8"))));
                } catch (UnsupportedEncodingException e) {
                    throw NBException.create(EErrorCode.c500, "url编码失败");
                }
            }
        });
        String result = url.concat("?".concat(ListUtil.join(list, "&")));
        return result;
    }


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
