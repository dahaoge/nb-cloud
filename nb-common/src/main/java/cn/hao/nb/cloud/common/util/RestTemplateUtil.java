package cn.hao.nb.cloud.common.util;

import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.penum.EModuleRequestPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Auther: hao
 * @Date: 2020/4/14 15:01
 * @Description:
 */
@Service
public class RestTemplateUtil {

    @Autowired
    RestTemplate restTemplate;

    public Rv restGetRv(EModuleRequestPrefix moduleRequestPrefix, String requestSuffix, Map<String, Object> params) {
        Rv resp = this.restGet(moduleRequestPrefix, requestSuffix, params, Rv.class);
        if (EErrorCode.success.getValue() != resp.getCode())
            throw NBException.create(EErrorCode.apiErr, resp.getMsg()).plusMsg("errorCode:" + resp.getCode());
        return resp;
    }

    public <T> T restGet(EModuleRequestPrefix moduleRequestPrefix, String requestSuffix, Map<String, Object> params, Class<T> clazz) {
        if (CheckUtil.objIsEmpty(moduleRequestPrefix, requestSuffix))
            throw NBException.create(EErrorCode.missingArg).plusMsg("requestUrl");
        if (CheckUtil.objIsEmpty(clazz))
            throw NBException.create(EErrorCode.missingArg).plusMsg("clazz");
        ResponseEntity responseEntity = restTemplate.getForEntity(HttpUtil.preGetParams(this.getRequestUrl(moduleRequestPrefix, requestSuffix), params)
                , clazz);
        if (responseEntity.getStatusCodeValue() != 200)
            throw NBException.create(EErrorCode.apiErr, "调用第三方服务失败").plusMsg(responseEntity.getStatusCodeValue() + "");
        return (T) responseEntity.getBody();
    }

    public Rv restPostRv(EModuleRequestPrefix moduleRequestPrefix, String requestSuffix, Map<String, Object> params) {
        Rv resp = this.restPost(moduleRequestPrefix, requestSuffix, params, Rv.class);
        if (EErrorCode.success.getValue() != resp.getCode())
            throw NBException.create(EErrorCode.apiErr, resp.getMsg()).plusMsg("errorCode:" + resp.getCode());
        return resp;
    }

    public <T> T restPost(EModuleRequestPrefix moduleRequestPrefix, String requestSuffix, Map<String, Object> params, Class<T> clazz) {
        if (CheckUtil.objIsEmpty(moduleRequestPrefix, requestSuffix))
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
        ResponseEntity responseEntity = restTemplate.postForEntity(this.getRequestUrl(moduleRequestPrefix, requestSuffix),
                new HttpEntity<MultiValueMap<String, Object>>(p, new HttpHeaders()), clazz);
        if (responseEntity.getStatusCodeValue() != 200)
            throw NBException.create(EErrorCode.apiErr, "调用第三方服务失败").plusMsg(responseEntity.getStatusCodeValue() + "");
        return (T) responseEntity.getBody();
    }

    private String getRequestUrl(EModuleRequestPrefix moduleRequestPrefix, String requestSuffix) {
        if (requestSuffix.startsWith("/"))
            return moduleRequestPrefix.getPrefix().concat(requestSuffix);
        else
            return moduleRequestPrefix.getPrefix().concat("/".concat(requestSuffix));
    }
}
