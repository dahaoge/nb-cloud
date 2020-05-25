package cn.hao.nb.cloud.ydgl.service;

import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.penum.ECompanyRequestSuffix;
import cn.hao.nb.cloud.ydgl.entity.Company;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公司管理  服务类11
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2020-04-10
 */
public interface ICompanyService extends IService<Company> {

    String getRequestUrl(Long comId, ECompanyRequestSuffix requestSuffixKey);

    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    Company addData(Company data);

    /**
     * 刷新公司内部组织机构
     *
     * @param comId
     * @return
     */
    boolean refreshComDept(Long comId);

    /**
     * 增量更新数据
     *
     * @param data
     * @return
     */
    boolean incrementModifyData(Company data);

    /**
     * 全量更新数据
     *
     * @param data
     * @return
     */
    boolean totalAmountModifyData(Company data);

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    boolean delData(Long id);

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    Company getDetail(Long id);

    /**
     * 分页查询数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage<Company> pageData(Pg pg, Company.SearchParams searchParams);

    /**
     * 列表查询数据
     *
     * @param searchParams
     * @return
     */
    List<Company> listData(Company.SearchParams searchParams);

    /**
     * 分页查询Map数据
     *
     * @param pg
     * @param searchParams
     * @return
     */
    IPage
            <Map
                    <String, Object>> pageMapData(Pg pg, Company.SearchParams searchParams);

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    Company prepareReturnModel(Company data);

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    IPage<Company> prepareReturnModel(IPage<Company> page);

    /**
     * 处理返回值
     *
     * @param list
     * @return
     */
    List<Company> prepareReturnModel(List<Company> list);

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    IPage
            <Map
                    <String, Object>> prepareReturnMapModel(IPage
                                                                    <Map
                                                                            <String, Object>> page);

    /**
     * 添加/修改数据前校验数据有效性(强制抛出异常)
     * 如果不需要抛出异常请不用调用该服务
     *
     * @param data
     */
    void validData(Company data);

}