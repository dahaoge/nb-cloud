package cn.hao.nb.cloud.ydgl.service.impl;

import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Qd;
import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.common.entity.TokenUser;
import cn.hao.nb.cloud.common.penum.ECompanyRequestSuffix;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.common.util.HttpUtil;
import cn.hao.nb.cloud.common.util.UserUtil;
import cn.hao.nb.cloud.ydgl.entity.Company;
import cn.hao.nb.cloud.ydgl.service.ICompanyRequestSuffixService;
import cn.hao.nb.cloud.ydgl.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (CheckUtil.objIsNotEmpty(params) && CheckUtil.objIsNotEmpty(params.get("deptId"))) {
            if (CheckUtil.collectionIsEmpty(tokenUser.getAuthDeptList()))
                throw NBException.create(EErrorCode.authDenied, "请联系管理员添加授权的组织机构");
            tokenUser.getAuthDeptList().forEach(item -> {
                if (params.get("deptId").equals(item.getDeptId()))
                    params.add("deptId", item.getExternalDeptId());
            });
        }
        return HttpUtil.httpGetRv(this.getRequestUrl(tokenUser.getCompanyId(), requestSuffix), params);
    }
}
