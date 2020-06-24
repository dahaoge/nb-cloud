package cn.hao.nb.cloud.basic.service.impl;

import cn.hao.nb.cloud.basic.entity.UUserAppVersion;
import cn.hao.nb.cloud.basic.mapper.UUserAppVersionMapper;
import cn.hao.nb.cloud.basic.service.IUUserAppVersionService;
import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Qw;
import cn.hao.nb.cloud.common.entity.TokenUser;
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
 * 用户app版本 服务实现类
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2020-06-14
 */
@Service
public class UUserAppVersionServiceImpl extends ServiceImpl<UUserAppVersionMapper, UUserAppVersion> implements IUUserAppVersionService {

    @Autowired
    IDUtil idUtil;
    @Autowired
    UUserAppVersionMapper mapper;

    @Override
    public UUserAppVersion getByUserId(Long userId) {
        if (CheckUtil.objIsEmpty(userId))
            throw NBException.create(EErrorCode.missingArg).plusMsg("userId");
        Qw qw = Qw.create().eq(UUserAppVersion.USER_ID, userId);
        qw.orderByDesc(UUserAppVersion.CREATE_TIME);
        return this.getOne(UUserAppVersion.select(qw));
    }

    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    @Override
    public UUserAppVersion addData(UUserAppVersion data) {
        this.validData(data);
        data.setUserAppId(idUtil.nextId());
        TokenUser tokenUser = UserUtil.getTokenUser(false);
        if (CheckUtil.objIsNotEmpty(tokenUser)) {
            data.setCreateBy(tokenUser.getUserId());
            data.setUpdateBy(tokenUser.getUserId());
        }
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
    public boolean incrementModifyData(UUserAppVersion data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getUserAppId()
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
    public boolean totalAmountModifyData(UUserAppVersion data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getUserAppId()
        ))
            throw NBException.create(EErrorCode.missingArg);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        return this.update(data, Wrappers.<UUserAppVersion>lambdaUpdate()
                .set(UUserAppVersion::getUpdateBy, data.getUpdateBy())
                .set(UUserAppVersion::getUserId, data.getUserId())
                .set(UUserAppVersion::getApp, data.getApp())
                .set(UUserAppVersion::getAppPlatform, data.getAppPlatform())
                .set(UUserAppVersion::getAppVersion, data.getAppVersion())
                .set(UUserAppVersion::getAppVersionType, data.getAppVersionType())
                .set(UUserAppVersion::getVersionNum, data.getVersionNum())
                .eq(UUserAppVersion::getUserAppId, data.getUserAppId())
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
    public UUserAppVersion getDetail(Long id) {
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
    public IPage<UUserAppVersion> pageData(Pg pg, UUserAppVersion.SearchParams searchParams) {
        return this.prepareReturnModel(this.page(pg.page(), searchParams.preWrapper(pg.wrapper())));
    }

    /**
     * 列表查询
     *
     * @param searchParams
     * @return
     */
    @Override
    public List<UUserAppVersion> listData(UUserAppVersion.SearchParams searchParams) {
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
                    <String, Object>> pageMapData(Pg pg, UUserAppVersion.SearchParams searchParams) {
        return mapper.pageMapData(pg.page(), searchParams);
    }

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    @Override
    public UUserAppVersion prepareReturnModel(UUserAppVersion data) {
        return data;
    }

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    @Override
    public IPage<UUserAppVersion> prepareReturnModel(IPage<UUserAppVersion> page) {
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
    public List<UUserAppVersion> prepareReturnModel(List<UUserAppVersion> list) {
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
    public void validData(UUserAppVersion data) {
        if (CheckUtil.objIsEmpty(data))
            throw NBException.create(EErrorCode.missingArg);

        if (CheckUtil.objIsEmpty(data.getUserId()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("userId");
        if (CheckUtil.objIsEmpty(data.getApp()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("app");
        if (CheckUtil.objIsEmpty(data.getAppPlatform()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("appPlatform");
        if (CheckUtil.objIsEmpty(data.getAppVersion()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("appVersion");
        if (CheckUtil.objIsEmpty(data.getAppVersionType()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("appVersionType");
        if (CheckUtil.objIsEmpty(data.getVersionNum()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("versionNum");
    }
}
