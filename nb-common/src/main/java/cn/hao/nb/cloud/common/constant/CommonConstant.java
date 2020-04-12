package cn.hao.nb.cloud.common.constant;

import cn.hao.nb.cloud.common.penum.ECompanyRequestSuffixKey;
import com.google.common.collect.ImmutableMap;

/**
 * @Auther: hao
 * @Date: 2019-12-07 15:55
 * @Description:
 */
public interface CommonConstant {

    /**
     * 编码
     */
    String UTF8 = "UTF-8";

    /**
     * JSON 资源
     */
    String CONTENT_TYPE = "application/json; charset=utf-8";

    ImmutableMap DEFAULT_COMPANY_REQUEST_SUFFIX = ImmutableMap.of(
            ECompanyRequestSuffixKey.loadDept, "/ydgl/company/loadDept"
    );
}
