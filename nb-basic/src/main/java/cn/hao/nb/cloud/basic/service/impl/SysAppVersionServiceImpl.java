package cn.hao.nb.cloud.basic.service.impl;

import cn.hao.nb.cloud.basic.entity.SysAppVersion;
import cn.hao.nb.cloud.basic.mapper.SysAppVersionMapper;
import cn.hao.nb.cloud.basic.service.ISysAppVersionService;
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
 * app版本管理  服务实现类
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-28
 */
@Service
public class SysAppVersionServiceImpl extends ServiceImpl<SysAppVersionMapper, SysAppVersion> implements ISysAppVersionService {

    @Autowired
    IDUtil idUtil;
    @Autowired
    SysAppVersionMapper mapper;

    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    @Override
    public SysAppVersion addData(SysAppVersion data) {
        this.validData(data);
        data.setAppVersionId(idUtil.nextId());
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
    public boolean incrementModifyData(SysAppVersion data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getAppVersionId()
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
    public boolean totalAmountModifyData(SysAppVersion data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getAppVersionId()
        ))
            throw NBException.create(EErrorCode.missingArg);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        return this.update(data, Wrappers.<SysAppVersion>lambdaUpdate()
                .set(SysAppVersion::getUpdateBy, data.getUpdateBy())
                .set(SysAppVersion::getApp, data.getApp())
                .set(SysAppVersion::getAppPlatform, data.getAppPlatform())
                .set(SysAppVersion::getAppDownloadChannel, data.getAppDownloadChannel())
                .set(SysAppVersion::getAppVersionType, data.getAppVersionType())
                .set(SysAppVersion::getAppVersion, data.getAppVersion())
                .set(SysAppVersion::getIsMust, data.getIsMust())
                .set(SysAppVersion::getAppVersionName, data.getAppVersionName())
                .set(SysAppVersion::getVersionDesc, data.getVersionDesc())
                .set(SysAppVersion::getUpdateDesc, data.getUpdateDesc())
                .set(SysAppVersion::getVersionStartTime, data.getVersionStartTime())
                .set(SysAppVersion::getPayLock, data.getPayLock())
                .set(SysAppVersion::getAppSize, data.getAppSize())
                .set(SysAppVersion::getDownloadUrlHash, data.getDownloadUrlHash())
                .set(SysAppVersion::getHasPublished, data.getHasPublished())
                .set(SysAppVersion::getVersionNum, data.getVersionNum())
                .eq(SysAppVersion::getAppVersionId, data.getAppVersionId())
        );
    }

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    @Override
    public boolean delData(String id) {
        if (CheckUtil.strIsEmpty(id))
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
    public SysAppVersion getDetail(String id) {
        if (CheckUtil.strIsEmpty(id))
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
    public IPage<SysAppVersion> pageData(Pg pg, SysAppVersion.SearchParams searchParams) {
        return this.prepareReturnModel(this.page(pg.page(), searchParams.preWrapper(pg.wrapper())));
    }

    /**
     * 列表查询
     *
     * @param searchParams
     * @return
     */
    @Override
    public List<SysAppVersion> listData(SysAppVersion.SearchParams searchParams) {
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
                    <String, Object>> pageMapData(Pg pg, SysAppVersion.SearchParams searchParams) {
        return mapper.pageMapData(pg.page(), searchParams);
    }

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    @Override
    public SysAppVersion prepareReturnModel(SysAppVersion data) {
        return data;
    }

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    @Override
    public IPage<SysAppVersion> prepareReturnModel(IPage<SysAppVersion> page) {
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
    public List<SysAppVersion> prepareReturnModel(List<SysAppVersion> list) {
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
    public void validData(SysAppVersion data) {
        if (CheckUtil.objIsEmpty(data))
            throw NBException.create(EErrorCode.missingArg);
    }
}
