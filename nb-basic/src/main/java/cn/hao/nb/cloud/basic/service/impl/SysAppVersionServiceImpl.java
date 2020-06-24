package cn.hao.nb.cloud.basic.service.impl;

import cn.hao.nb.cloud.basic.entity.SysAppVersion;
import cn.hao.nb.cloud.basic.entity.UUserAppVersion;
import cn.hao.nb.cloud.basic.entity.customEntity.AppUpdateRv;
import cn.hao.nb.cloud.basic.mapper.SysAppVersionMapper;
import cn.hao.nb.cloud.basic.service.ISysAppVersionService;
import cn.hao.nb.cloud.basic.service.IUUserAppVersionService;
import cn.hao.nb.cloud.common.entity.*;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.penum.EYn;
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.common.util.IDUtil;
import cn.hao.nb.cloud.common.util.UserUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    @Autowired
    IUUserAppVersionService userAppVersionService;
    @Value("${global.appDownloadUrl}")
    String appDownloadUrl;

    @Override
    public Rv getCurrentVersionRv(SysAppVersion oldVersion) {
        if (CheckUtil.objIsEmpty(oldVersion) || CheckUtil.objIsEmpty(oldVersion.getVersionNum(), oldVersion.getAppPlatform(), oldVersion.getApp(), oldVersion.getAppVersionType(), oldVersion.getAppVersion()))
            return null;
        TokenUser tokenUser = UserUtil.getTokenUser();
        UUserAppVersion temp = null;
        if (CheckUtil.objIsNotEmpty(tokenUser))
            temp = userAppVersionService.getByUserId(tokenUser.getUserId());
        if (CheckUtil.objIsEmpty(temp) && CheckUtil.objIsNotEmpty(tokenUser)) {
            temp = new UUserAppVersion();
            this.setUserAppVersionProps(temp, oldVersion);
            temp.setUserId(UserUtil.getTokenUser().getUserId());
            userAppVersionService.addData(temp);
        } else if (!oldVersion.getVersionNum().equals(temp.getVersionNum()) && CheckUtil.objIsNotEmpty(oldVersion.getVersionNum())) {
            this.setUserAppVersionProps(temp, oldVersion);
            userAppVersionService.incrementModifyData(temp);
        }
        Qw qw = Qw.create().le(SysAppVersion.VERSION_START_TIME, new Date())
                .eq(SysAppVersion.HAS_PUBLISHED, EYn.y.getValue())
                .gt(SysAppVersion.VERSION_NUM, oldVersion.getVersionNum())
                .eq(SysAppVersion.APP_PLATFORM, oldVersion.getAppPlatform())
                .eq(SysAppVersion.APP, oldVersion.getApp())
                .eq(SysAppVersion.APP_VERSION_TYPE, oldVersion.getAppVersionType());
        qw.orderByDesc(SysAppVersion.VERSION_START_TIME);
        SysAppVersion cVersion = this.getOne(qw);
        if (CheckUtil.objIsEmpty(cVersion))
            return null;
        List<SysAppVersion> versions = this.list(qw);
        cVersion.setIsMust(versions.stream().filter(item -> {
            return EYn.y.getValue().equals(item.getIsMust());
        }).count() > 0 ? 1 : 0);
        cVersion.setDownLoadUrl(appDownloadUrl.concat(cVersion.getDownloadUrlHash()));
        return Rv.getInstance(AppUpdateRv.toAppUpdateRv(cVersion)).add("appVersionDetail", cVersion);
    }

    @Override
    public SysAppVersion getCurrentVersion(SysAppVersion oldVersion) {
        if (CheckUtil.objIsEmpty(oldVersion) || CheckUtil.objIsEmpty(oldVersion.getVersionNum(), oldVersion.getAppPlatform(), oldVersion.getApp(), oldVersion.getAppVersionType(), oldVersion.getAppVersion()))
            return null;
        TokenUser tokenUser = UserUtil.getTokenUser();
        UUserAppVersion temp = null;
        if (CheckUtil.objIsNotEmpty(tokenUser))
            temp = userAppVersionService.getByUserId(tokenUser.getUserId());
        if (CheckUtil.objIsEmpty(temp) && CheckUtil.objIsNotEmpty(tokenUser)) {
            temp = new UUserAppVersion();
            this.setUserAppVersionProps(temp, oldVersion);
            temp.setUserId(UserUtil.getTokenUser().getUserId());
            userAppVersionService.addData(temp);
        } else if (!oldVersion.getVersionNum().equals(temp.getVersionNum()) && CheckUtil.objIsNotEmpty(oldVersion.getVersionNum())) {
            this.setUserAppVersionProps(temp, oldVersion);
            userAppVersionService.incrementModifyData(temp);
        }
        Qw qw = Qw.create().le(SysAppVersion.VERSION_START_TIME, new Date())
                .eq(SysAppVersion.HAS_PUBLISHED, EYn.y.getValue())
                .gt(SysAppVersion.VERSION_NUM, oldVersion.getVersionNum())
                .eq(SysAppVersion.APP_PLATFORM, oldVersion.getAppPlatform())
                .eq(SysAppVersion.APP, oldVersion.getApp())
                .eq(SysAppVersion.APP_VERSION_TYPE, oldVersion.getAppVersionType());
        qw.orderByDesc(SysAppVersion.VERSION_START_TIME);
        SysAppVersion cVersion = this.getOne(qw);
        if (CheckUtil.objIsEmpty(cVersion))
            return null;
        List<SysAppVersion> versions = this.list(qw);
        cVersion.setIsMust(versions.stream().filter(item -> {
            return EYn.y.getValue().equals(item.getIsMust());
        }).count() > 0 ? 1 : 0);
        cVersion.setDownLoadUrl(appDownloadUrl.concat(cVersion.getDownloadUrlHash()));
        return cVersion;
    }

    private void setUserAppVersionProps(UUserAppVersion temp, SysAppVersion oldVersion) {
        temp.setAppVersion(oldVersion.getAppVersion());
        temp.setVersionNum(oldVersion.getVersionNum());
        temp.setAppPlatform(oldVersion.getAppPlatform());
        temp.setApp(oldVersion.getApp());
        temp.setAppVersionType(oldVersion.getAppVersionType());
    }

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
    public SysAppVersion getDetail(Long id) {
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
        if (CheckUtil.objIsEmpty(data.getApp()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("app");
//        if (CheckUtil.objIsEmpty(data.getAppPlatform()))
//            throw NBException.create(EErrorCode.missingArg).plusMsg("appPlatform");
//        if (CheckUtil.objIsEmpty(data.getAppDownloadChannel()))
//            throw NBException.create(EErrorCode.missingArg).plusMsg("appDownloadChannel");
        if (CheckUtil.objIsEmpty(data.getAppVersionType()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("appVersionType");
        if (CheckUtil.objIsEmpty(data.getAppVersion()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("appVersion");
        if (CheckUtil.objIsEmpty(data.getIsMust()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("isMust");
        if (CheckUtil.objIsEmpty(data.getAppVersionName()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("appVersionName");
        if (CheckUtil.objIsEmpty(data.getVersionDesc()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("versionDesc");
        if (CheckUtil.objIsEmpty(data.getUpdateDesc()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("updateDesc");
        if (CheckUtil.objIsEmpty(data.getVersionStartTime()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("versionStartTime");
//        if (CheckUtil.objIsEmpty(data.getAppSize()))
//            throw NBException.create(EErrorCode.missingArg).plusMsg("appSize");
//        if (CheckUtil.objIsEmpty(data.getDownloadUrlHash()))
//            throw NBException.create(EErrorCode.missingArg).plusMsg("downloadUrlHash");
        if (CheckUtil.objIsEmpty(data.getHasPublished()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("hasPublished");
        if (CheckUtil.objIsEmpty(data.getVersionNum()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("versionNum");
    }
}
