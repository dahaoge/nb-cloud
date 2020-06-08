package cn.hao.nb.cloud.ydgl.service.impl;

import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Qd;
import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.common.entity.TokenUser;
import cn.hao.nb.cloud.common.penum.ECompanyRequestSuffix;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.util.*;
import cn.hao.nb.cloud.ydgl.constant.RedisKey;
import cn.hao.nb.cloud.ydgl.entity.Company;
import cn.hao.nb.cloud.ydgl.service.ICompanyRequestSuffixService;
import cn.hao.nb.cloud.ydgl.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Auther: hao
 * @Date: 2020/6/5 15:37
 * @Description:
 */
@Service
public class CommonService {

    @Autowired
    ICompanyService iCompanyService;
    @Autowired
    ICompanyRequestSuffixService iCompanyRequestSuffixService;
    @Autowired
    RedisUtil redisUtil;


    public String getRequestUrl(Long comId, ECompanyRequestSuffix requestSuffixKey) {
        Company company = iCompanyService.getById(comId);
        if (CheckUtil.objIsEmpty(company))
            throw NBException.create(EErrorCode.noData).plusMsg("company");
        if (CheckUtil.strIsEmpty(company.getBaseUrl()))
            throw NBException.create(EErrorCode.noData).plusMsg("company.baseUrl");
        return company.getBaseUrl().concat(iCompanyRequestSuffixService.getRequestSuffix(comId, requestSuffixKey));
    }

    public Rv sendYdglRequest(ECompanyRequestSuffix requestSuffix, Qd params) {
        TokenUser tokenUser = UserUtil.getTokenUser(true);
        // 统一将组织机构id改成外部组织机构id
        if (CheckUtil.objIsNotEmpty(params)) {
            if (CheckUtil.objIsNotEmpty(params.get("deptId"))) {
                if (CheckUtil.collectionIsEmpty(tokenUser.getAuthDeptList()))
                    throw NBException.create(EErrorCode.authDenied, "请联系管理员添加授权的组织机构");
                tokenUser.getAuthDeptList().forEach(item -> {
                    if (params.get("deptId").equals(item.getDeptId()))
                        params.add("deptId", item.getExternalDeptId());
                });
            }
            params.keySet().forEach(key -> {
                if (params.get(key) instanceof Date) {
                    params.add(key, DateUtil.format((Date) params.get(key), "yyyy-MM-dd HH:mm:ss"));
                }

            });
        }
        String hash = "" + params.hashCode();
        Rv result = (Rv) redisUtil.hget(RedisKey.REDIS_REQUEST_RESULT.concat(requestSuffix.getValue()), hash);
        if (CheckUtil.objIsNotEmpty(result))
            return result;

        result = HttpUtil.httpGetRv(this.getRequestUrl(tokenUser.getCompanyId(), requestSuffix), params);
        if (CheckUtil.objIsNotEmpty(result))
            redisUtil.hset(RedisKey.REDIS_REQUEST_RESULT.concat(requestSuffix.getValue()), hash, result, requestSuffix.getRedisTime());
        return result;
    }
}
