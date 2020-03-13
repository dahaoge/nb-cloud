package cn.hao.nb.cloud.basic.service.impl;

import cn.hao.nb.cloud.basic.entity.SysTags;
import cn.hao.nb.cloud.basic.mapper.SysTagsMapper;
import cn.hao.nb.cloud.basic.service.ISysTagsService;
import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.common.util.IDUtil;
import cn.hao.nb.cloud.common.util.UserUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标签  服务实现类
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-28
 */
@Service
public class SysTagsServiceImpl extends ServiceImpl<SysTagsMapper, SysTags> implements ISysTagsService {

    @Autowired
    IDUtil idUtil;
    @Autowired
    SysTagsMapper mapper;

    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    @Override
    public SysTags addData(SysTags data) {
        this.validData(data);
        data.setTagId(idUtil.nextId());
        data.setCreateBy(UserUtil.getTokenUser(true).getUserId());
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        this.save(data);
        return data;
    }

    /**
     * 增量更新数据
     *
     * @param data
     * @return
     */
    @Override
    public boolean incrementModifyData(SysTags data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getTagId()
        ))
            throw NBException.create(EErrorCode.missingArg);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        return this.updateById(data);
    }

    /**
     * 全量更新数据
     *
     * @param data
     * @return
     */
    @Override
    public boolean totalAmountModifyData(SysTags data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getTagId()
        ))
            throw NBException.create(EErrorCode.missingArg);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        return this.update(data, Wrappers.<SysTags>lambdaUpdate()
                .set(SysTags::getUpdateBy, data.getUpdateBy())
                .set(SysTags::getTagCode, data.getTagCode())
                .set(SysTags::getTagLabel, data.getTagLabel())
                .set(SysTags::getTagTarget, data.getTagTarget())
                .set(SysTags::getTagType, data.getTagType())
                .set(SysTags::getTagIcon, data.getTagIcon())
                .set(SysTags::getTagIconActive, data.getTagIconActive())
                .set(SysTags::getTagIndex, data.getTagIndex())
                .set(SysTags::getTagGroup, data.getTagGroup())
                .set(SysTags::getTagGroupName, data.getTagGroupName())
                .set(SysTags::getAppStyleNormal, data.getAppStyleNormal())
                .set(SysTags::getAppStyleActive, data.getAppStyleActive())
                .set(SysTags::getMiniAppStyleNormal, data.getMiniAppStyleNormal())
                .set(SysTags::getMiniAppStyleActive, data.getMiniAppStyleActive())
                .set(SysTags::getWebStyleNormal, data.getWebStyleNormal())
                .set(SysTags::getWebStyleActive, data.getWebStyleActive())
                .eq(SysTags::getTagId, data.getTagId())
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
    public SysTags getDetail(Long id) {
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
    public IPage<SysTags> pageData(Pg pg, SysTags.SearchParams searchParams) {
        return this.prepareReturnModel(this.page(pg.page(), searchParams.preWrapper(pg.wrapper())));
    }

    /**
     * 列表查询
     *
     * @param searchParams
     * @return
     */
    @Override
    public List<SysTags> listData(SysTags.SearchParams searchParams) {
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
                    <String, Object>> pageMapData(Pg pg, SysTags.SearchParams searchParams) {
        return mapper.pageMapData(pg.page(), searchParams);
    }

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    @Override
    public SysTags prepareReturnModel(SysTags data) {
        return data;
    }

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    @Override
    public IPage<SysTags> prepareReturnModel(IPage<SysTags> page) {
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
    public List<SysTags> prepareReturnModel(List<SysTags> list) {
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
    public void validData(SysTags data) {
        if (CheckUtil.objIsEmpty(data))
            throw NBException.create(EErrorCode.missingArg);
    }
}
