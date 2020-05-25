package cn.hao.nb.cloud.ydgl.service.impl;

import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Qw;
import cn.hao.nb.cloud.common.entity.TokenUser;
import cn.hao.nb.cloud.common.penum.ECompanyRequestSuffix;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.common.util.IDUtil;
import cn.hao.nb.cloud.common.util.RedisUtil;
import cn.hao.nb.cloud.common.util.UserUtil;
import cn.hao.nb.cloud.ydgl.constant.RedisKey;
import cn.hao.nb.cloud.ydgl.entity.CompanyRequestSuffix;
import cn.hao.nb.cloud.ydgl.mapper.CompanyRequestSuffixMapper;
import cn.hao.nb.cloud.ydgl.service.ICompanyRequestSuffixService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公司请求后缀  服务实现类
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2020-04-12
 */
@Service
public class CompanyRequestSuffixServiceImpl extends ServiceImpl<CompanyRequestSuffixMapper, CompanyRequestSuffix> implements ICompanyRequestSuffixService {

    @Autowired
    IDUtil idUtil;
    @Autowired
    CompanyRequestSuffixMapper mapper;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public Map<ECompanyRequestSuffix, String> getCompanyRequestSuffix(Long comId) {
        if (CheckUtil.objIsEmpty(comId))
            throw NBException.create(EErrorCode.missingArg).plusMsg("comId");
        Map<ECompanyRequestSuffix, String> result = (Map<ECompanyRequestSuffix, String>) redisUtil.hget(RedisKey.REDIS_COMPANY_REQUEST_SUFFIX, comId.toString());
        if (CheckUtil.objIsEmpty(result)) {
            result = Maps.newHashMap();

            result.putAll(ECompanyRequestSuffix.toMap());

            List<CompanyRequestSuffix> dbList = this.list(
                    Qw.create().eq(CompanyRequestSuffix.COM_ID, comId)
                            .select(CompanyRequestSuffix.ENUM_KEY, CompanyRequestSuffix.REQUEST_SUFFIX)
            );

            if (CheckUtil.collectionIsNotEmpty(dbList)) {
                Map<ECompanyRequestSuffix, CompanyRequestSuffix> dbMap = Maps.newHashMap();
                dbMap = Maps.uniqueIndex(dbList.iterator(), new Function<CompanyRequestSuffix, ECompanyRequestSuffix>() {
                    @Nullable
                    @Override
                    public ECompanyRequestSuffix apply(@Nullable CompanyRequestSuffix item) {
                        return item.getEnumKey();
                    }
                });
                for (ECompanyRequestSuffix key : ECompanyRequestSuffix.values()) {
                    if (CheckUtil.objIsNotEmpty(dbMap.get(key)))
                        result.put(key, dbMap.get(key).getRequestSuffix());
                }
            }
            redisUtil.hset(RedisKey.REDIS_COMPANY_REQUEST_SUFFIX, comId.toString(), result);
        }
        return result;
    }

    @Override
    public String getRequestSuffix(Long comId, ECompanyRequestSuffix enumKey) {
        if (CheckUtil.objIsEmpty(comId, enumKey))
            throw NBException.create(EErrorCode.missingArg).plusMsg("comId|enumKey");
        Map<ECompanyRequestSuffix, String> map = this.getCompanyRequestSuffix(comId);
        if (CheckUtil.objIsEmpty(map))
            throw NBException.create(EErrorCode.noData, "公司请求后缀配置异常");
        String result = map.get(enumKey);
        if (CheckUtil.strIsEmpty(result))
            throw NBException.create(EErrorCode.noData, "公司请求后缀配置异常").plusMsg(enumKey.getValue());
        return result;
    }

    @Override
    public boolean refreshRedisCompanyRequestSuffixByComId(Long comId) {
        if (CheckUtil.strIsEmpty(comId.toString()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("comId");
        redisUtil.hdel(RedisKey.REDIS_COMPANY_REQUEST_SUFFIX, comId);
        return true;
    }

    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    @Override
    public CompanyRequestSuffix addData(CompanyRequestSuffix data) {
        this.validData(data);
        data.setTId(idUtil.nextId());
        TokenUser tokenUser = UserUtil.getTokenUser(false);
        if (CheckUtil.objIsNotEmpty(tokenUser)) {
            data.setCreateBy(tokenUser.getUserId());
            data.setUpdateBy(tokenUser.getUserId());
        }
        if (!data.getRequestSuffix().startsWith("/"))
            data.setRequestSuffix("/" + data.getRequestSuffix());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        this.save(data);
        this.refreshRedisCompanyRequestSuffixByComId(data.getComId());
        return data;
    }

    /**
     * 增量更新数据
     *
     * @param data
     * @return
     */
    @Override
    public boolean incrementModifyData(CompanyRequestSuffix data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getTId()
        ))
            throw NBException.create(EErrorCode.missingArg);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        return this.updateById(data) && this.refreshRedisCompanyRequestSuffixByComId(data.getComId());
    }

    /**
     * 全量更新数据
     *
     * @param data
     * @return
     */
    @Override
    public boolean totalAmountModifyData(CompanyRequestSuffix data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getTId()
        ))
            throw NBException.create(EErrorCode.missingArg);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        return this.update(data, Wrappers.<CompanyRequestSuffix>lambdaUpdate()
                .set(CompanyRequestSuffix::getUpdateBy, data.getUpdateBy())
                .set(CompanyRequestSuffix::getComId, data.getComId())
                .set(CompanyRequestSuffix::getEnumKey, data.getEnumKey())
                .set(CompanyRequestSuffix::getRequestSuffix, data.getRequestSuffix())
                .eq(CompanyRequestSuffix::getTId, data.getTId())
        ) && this.refreshRedisCompanyRequestSuffixByComId(data.getComId());
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
        CompanyRequestSuffix data = this.getById(id);
        if (CheckUtil.objIsEmpty(data))
            throw NBException.create(EErrorCode.noData);
        return this.removeById(id) && this.refreshRedisCompanyRequestSuffixByComId(data.getComId());
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    @Override
    public CompanyRequestSuffix getDetail(Long id) {
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
    public IPage<CompanyRequestSuffix> pageData(Pg pg, CompanyRequestSuffix.SearchParams searchParams) {
        return this.prepareReturnModel(this.page(pg.page(), searchParams.preWrapper(pg.wrapper())));
    }

    /**
     * 列表查询
     *
     * @param searchParams
     * @return
     */
    @Override
    public List<CompanyRequestSuffix> listData(CompanyRequestSuffix.SearchParams searchParams) {
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
                    <String, Object>> pageMapData(Pg pg, CompanyRequestSuffix.SearchParams searchParams) {
        return mapper.pageMapData(pg.page(), searchParams);
    }

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    @Override
    public CompanyRequestSuffix prepareReturnModel(CompanyRequestSuffix data) {
        return data;
    }

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    @Override
    public IPage<CompanyRequestSuffix> prepareReturnModel(IPage<CompanyRequestSuffix> page) {
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
    public List<CompanyRequestSuffix> prepareReturnModel(List<CompanyRequestSuffix> list) {
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
    public void validData(CompanyRequestSuffix data) {
        if (CheckUtil.objIsEmpty(data))
            throw NBException.create(EErrorCode.missingArg);
        if (CheckUtil.objIsEmpty(data.getComId()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("comId");
        if (CheckUtil.objIsEmpty(data.getEnumKey()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("enumKey");
        if (CheckUtil.objIsEmpty(data.getRequestSuffix()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("requestSuffix");
    }
}
