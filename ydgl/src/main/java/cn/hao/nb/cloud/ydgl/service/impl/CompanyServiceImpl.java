package cn.hao.nb.cloud.ydgl.service.impl;

import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.common.entity.TokenUser;
import cn.hao.nb.cloud.common.penum.ECompanyRequestSuffixKey;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.common.util.IDUtil;
import cn.hao.nb.cloud.common.util.UserUtil;
import cn.hao.nb.cloud.ydgl.entity.Company;
import cn.hao.nb.cloud.ydgl.mapper.CompanyMapper;
import cn.hao.nb.cloud.ydgl.service.ICompanyRequestSuffixService;
import cn.hao.nb.cloud.ydgl.service.ICompanyService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公司管理  服务实现类
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2020-04-10
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements ICompanyService {

    @Autowired
    IDUtil idUtil;
    @Autowired
    CompanyMapper mapper;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ICompanyRequestSuffixService requestSuffixService;

    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    @Override
    public Company addData(Company data) {
        this.validData(data);
        data.setComId(idUtil.nextId());
        TokenUser tokenUser = UserUtil.getTokenUser(false);
        if (CheckUtil.objIsNotEmpty(tokenUser)) {
            data.setCreateBy(tokenUser.getUserId());
            data.setUpdateBy(tokenUser.getUserId());
        }
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        data.setRootDept(null);
        this.save(data);
        return data;
    }

    @Override
    public boolean refreshComDept(Long comId) {
        if (CheckUtil.objIsEmpty(comId))
            throw NBException.create(EErrorCode.missingArg).plusMsg("comId");
        Company company = this.getById(comId);
        if (CheckUtil.objIsEmpty(company))
            throw NBException.create(EErrorCode.noData).plusMsg("company");
        if (CheckUtil.strIsEmpty(company.getBaseUrl()))
            throw NBException.create(EErrorCode.noData).plusMsg("company.baseUrl");
        Rv resp = new RestTemplate().getForObject(company.getBaseUrl()
                + requestSuffixService.getRequestSuffix(comId, ECompanyRequestSuffixKey.loadDept), Rv.class);
        return false;
    }

    /**
     * 增量更新数据
     *
     * @param data
     * @return
     */
    @Override
    public boolean incrementModifyData(Company data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getComId()
        ))
            throw NBException.create(EErrorCode.missingArg);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        data.setRootDept(null);
        return this.updateById(data);
    }

    /**
     * 全量更新数据
     *
     * @param data
     * @return
     */
    @Override
    public boolean totalAmountModifyData(Company data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getComId()
        ))
            throw NBException.create(EErrorCode.missingArg);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        return this.update(data, Wrappers.<Company>lambdaUpdate()
                .set(Company::getUpdateBy, data.getUpdateBy())
                .set(Company::getComName, data.getComName())
                .set(Company::getRootDept, data.getRootDept())
                .set(Company::getBaseUrl, data.getBaseUrl())
                .eq(Company::getComId, data.getComId())
        );
    }

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    @Override
    public boolean delData(Long id) {
        if (CheckUtil.objIsEmpty(id))
            throw NBException.create(EErrorCode.missingArg);
        return this.removeById(id);
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    @Override
    public Company getDetail(Long id) {
        if (CheckUtil.objIsEmpty(id))
            throw NBException.create(EErrorCode.missingArg);
        return this.prepareReturnModel(this.getById(id));
    }

    /**
     * 分页查询
     *
     * @param pg
     * @param searchParams
     * @return
     */
    @Override
    public IPage<Company> pageData(Pg pg, Company.SearchParams searchParams) {
        return this.prepareReturnModel(this.page(pg.page(), searchParams.preWrapper(pg.wrapper())));
    }

    /**
     * 列表查询
     *
     * @param searchParams
     * @return
     */
    @Override
    public List<Company> listData(Company.SearchParams searchParams) {
        return this.prepareReturnModel(this.list(searchParams.preWrapper(null)));
    }

    /**
     * 连表分页查询map数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    @Override
    public IPage
            <Map
                    <String, Object>> pageMapData(Pg pg, Company.SearchParams searchParams) {
        return mapper.pageMapData(pg.page(), searchParams);
    }

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    @Override
    public Company prepareReturnModel(Company data) {
        return data;
    }

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    @Override
    public IPage<Company> prepareReturnModel(IPage<Company> page) {
        if (CheckUtil.objIsNotEmpty(page) && CheckUtil.collectionIsNotEmpty(page.getRecords()))
            this.prepareReturnModel(page.getRecords());
        return page;
    }

    /**
     * 处理返回值
     *
     * @param list
     * @return
     */
    @Override
    public List<Company> prepareReturnModel(List<Company> list) {
        if (CheckUtil.collectionIsNotEmpty(list))
            list.forEach(item -> {
                this.prepareReturnModel(item);
            });
        return list;
    }

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    @Override
    public IPage
            <Map
                    <String, Object>> prepareReturnMapModel(IPage
                                                                    <Map
                                                                            <String, Object>> page) {
        return page;
    }

    /**
     * 添加/修改数据前校验数据有效性(强制抛出异常)
     * 如果不需要抛出异常请不用调用该服务
     *
     * @param data
     */
    @Override
    public void validData(Company data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(data.getComName(), data.getBaseUrl()))
            throw NBException.create(EErrorCode.missingArg);
    }
}
