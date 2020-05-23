package cn.hao.nb.cloud.auth.service.impl;

import cn.hao.nb.cloud.auth.entity.ULoginChannel;
import cn.hao.nb.cloud.auth.entity.UUserInfo;
import cn.hao.nb.cloud.auth.mapper.ULoginChannelMapper;
import cn.hao.nb.cloud.auth.service.IULoginChannelService;
import cn.hao.nb.cloud.auth.service.IUUserInfoService;
import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Qw;
import cn.hao.nb.cloud.common.entity.TokenUser;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.penum.ELoginChannelScop;
import cn.hao.nb.cloud.common.penum.ELoginType;
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
 * 登录渠道 服务实现类
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
@Service
public class ULoginChannelServiceImpl extends ServiceImpl<ULoginChannelMapper, ULoginChannel> implements IULoginChannelService {

    @Autowired
    IDUtil idUtil;
    @Autowired
    ULoginChannelMapper mapper;
    @Autowired
    IUUserInfoService userInfoService;

    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    @Override
    public ULoginChannel addData(ULoginChannel data) {
        this.validData(data);
        data.setTId(idUtil.nextId());
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

    @Override
    public ULoginChannel addLoginChannel(Long userId, ELoginType loginType, String loginId, ELoginChannelScop loginChannelScop) {
        if (CheckUtil.objIsEmpty(userId, loginId, loginType, loginChannelScop))
            throw NBException.create(EErrorCode.missingArg);
        ULoginChannel loginChannel = new ULoginChannel();
        loginChannel.setUserId(userId);
        loginChannel.setLoginType(loginType);
        loginChannel.setLoginId(loginId);
        loginChannel.setLoginChannelScope(loginChannelScop);
        return this.addData(loginChannel);
    }

    @Override
    public boolean addPhoneChannel(Long userId, String phone, ELoginChannelScop loginChannelScop) {
        this.addLoginChannel(userId, ELoginType.checkSms, phone, loginChannelScop);
        this.addLoginChannel(userId, ELoginType.pwd, phone, loginChannelScop);
        return true;
    }

    @Override
    public boolean modifyUserPhone(Long userId, String phone) {
        if (CheckUtil.objIsEmpty(userId, phone))
            throw NBException.create(EErrorCode.missingArg);
        List<ULoginChannel> loginChannels = this.getUserLoginChannelByLoginType(userId, ELoginType.checkSms);
        if (CheckUtil.collectionIsNotEmpty(loginChannels)) {
            loginChannels.forEach(item -> {
                item.setLoginId(phone);
                this.incrementModifyData(item);
            });
        }
        return true;
    }

    @Override
    public boolean addByUser(UUserInfo user) {
        if (CheckUtil.objIsEmpty(user))
            throw NBException.create(EErrorCode.missingArg).plusMsg("addByUser:user");
        if (CheckUtil.objIsEmpty(user.getUserType()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("addByUser:userType");
        if (CheckUtil.objIsEmpty(user.getUserId()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("addByUser:userId");
        user.getUserType().getLoginChannelScops().forEach(item -> {
            if (CheckUtil.strIsNotEmpty(user.getPhone())) {
                this.addPhoneChannel(user.getUserId(), user.getPhone(), item);
            }
            if (CheckUtil.strIsNotEmpty(user.getLoginId()))
                this.addLoginChannel(user.getUserId(), ELoginType.pwd, user.getLoginId(), item);
            if (CheckUtil.strIsNotEmpty(user.getWechatAppOpenid()))
                this.addLoginChannel(user.getUserId(), ELoginType.wechatAppOpenid, user.getWechatAppOpenid(), item);
            if (CheckUtil.strIsNotEmpty(user.getWechatMpOpenid()))
                this.addLoginChannel(user.getUserId(), ELoginType.wechatAppOpenid, user.getWechatMpOpenid(), item);
            if (CheckUtil.strIsNotEmpty(user.getWechatPnOpenid()))
                this.addLoginChannel(user.getUserId(), ELoginType.wechatAppOpenid, user.getWechatPnOpenid(), item);
            if (CheckUtil.strIsNotEmpty(user.getWechatUnionid()))
                this.addLoginChannel(user.getUserId(), ELoginType.wechatAppOpenid, user.getWechatUnionid(), item);
        });

        return true;
    }

    @Override
    public List<ULoginChannel> getUserLoginChannelByLoginType(Long userId, ELoginType loginType) {
        if (CheckUtil.objIsEmpty(userId, loginType))
            throw NBException.create(EErrorCode.missingArg).plusMsg("getUserLoginChannelByLoginType");
        return this.list(Qw.create().eq(ULoginChannel.USER_ID, userId).eq(ULoginChannel.LOGIN_TYPE, loginType));
    }

    @Override
    public boolean resetUserLoginChannel(Long userId) {
        if (CheckUtil.objIsEmpty(userId))
            throw NBException.create(EErrorCode.missingArg).plusMsg("resetUserLoginChannel:userId");
        this.delByUserId(userId);
        UUserInfo userInfo = userInfoService.getById(userId);
        return this.addByUser(userInfo);
    }

    /**
     * 增量更新数据
     * @param data
     * @return
     */
    @Override
    public boolean incrementModifyData(ULoginChannel data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(data.getTId()))
            throw NBException.create(EErrorCode.missingArg);
        if (CheckUtil.objIsNotEmpty(data.getLoginId()))
            this.validLoginId(data);
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
    public boolean totalAmountModifyData(ULoginChannel data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getTId()
        ))
            throw NBException.create(EErrorCode.missingArg);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        return this.update(data, Wrappers.<ULoginChannel>lambdaUpdate()
                .set(ULoginChannel::getUpdateBy, data.getUpdateBy())
                .set(ULoginChannel::getUserId, data.getUserId())
                .set(ULoginChannel::getLoginType, data.getLoginType())
                .set(ULoginChannel::getLoginId, data.getLoginId())
                .set(ULoginChannel::getLoginChannelScope, data.getLoginChannelScope())
                .eq(ULoginChannel::getTId, data.getTId())
        );
    }

    @Override
    public boolean modifyLoginId(ULoginChannel data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(data.getTId(), data.getLoginId(), data.getUserId()))
            throw NBException.create(EErrorCode.missingArg);
        this.validLoginId(data);

        ULoginChannel temp = new ULoginChannel();
        temp.setTId(data.getTId());
        temp.setLoginId(data.getLoginId());
        temp.setUserId(data.getUserId());
        return this.incrementModifyData(temp);
    }

    @Override
    public boolean modifyLoginId(Long tId, String loginId, Long userId) {
        if (CheckUtil.objIsEmpty(tId, loginId))
            throw NBException.create(EErrorCode.missingArg);
        ULoginChannel loginChannel = new ULoginChannel();
        loginChannel.setTId(tId);
        loginChannel.setLoginId(loginId);
        loginChannel.setUserId(userId);
        return this.modifyLoginId(loginChannel);
    }

    @Override
    public boolean modifyLoginId(Long tId, String loginId) {
        if (CheckUtil.objIsEmpty(tId, loginId))
            throw NBException.create(EErrorCode.missingArg);
        ULoginChannel dbData = this.getById(tId);
        if (CheckUtil.objIsEmpty(dbData))
            throw NBException.create(EErrorCode.noData);
        return this.modifyLoginId(tId, loginId, dbData.getUserId());
    }

    @Override
    public int countByLoginId(String loginId) {
        if (CheckUtil.objIsEmpty(loginId))
            throw NBException.create(EErrorCode.missingArg);
        return this.count(Qw.create().eq(ULoginChannel.LOGIN_ID, loginId));
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

    @Override
    public boolean delByUserId(Long userId) {
        if (CheckUtil.objIsEmpty(userId))
            throw NBException.create(EErrorCode.missingArg);
        return this.delByUserId(userId);
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    @Override
    public ULoginChannel getDetail(Long id) {
        if (CheckUtil.objIsEmpty(id))
            throw NBException.create(EErrorCode.missingArg);
        return this.prepareReturnModel(this.getById(id));
    }

    @Override
    public ULoginChannel getByTypeAndChannelScope(String loginId, ELoginType loginType, ELoginChannelScop loginChannelScop) {
        if (CheckUtil.objIsEmpty(loginId, loginType, loginChannelScop))
            throw NBException.create(EErrorCode.missingArg);
        return this.getOne(Qw.create()
                .eq(ULoginChannel.LOGIN_ID, loginId)
                .eq(ULoginChannel.LOGIN_TYPE, loginType)
                .eq(ULoginChannel.LOGIN_CHANNEL_SCOPE, loginChannelScop));
    }

    @Override
    public ULoginChannel getByWechatUnionId(String unionId) {
        if (CheckUtil.strIsEmpty(unionId))
            throw NBException.create(EErrorCode.missingArg).plusMsg("getByWechatUnionId");
        return this.getOne(Qw.create().eq(ULoginChannel.LOGIN_ID, unionId).eq(ULoginChannel.LOGIN_TYPE, ELoginType.wechatUnionid));
    }

    @Override
    public ULoginChannel getByWechatMpOpenId(String openId) {
        if (CheckUtil.strIsEmpty(openId))
            throw NBException.create(EErrorCode.missingArg).plusMsg("getByWechatMpOpenId");
        return this.getOne(Qw.create().eq(ULoginChannel.LOGIN_ID, openId).eq(ULoginChannel.LOGIN_TYPE, ELoginType.wechatMpOpenid));
    }

    @Override
    public ULoginChannel getByWechatPNOpenId(String openId) {
        if (CheckUtil.strIsEmpty(openId))
            throw NBException.create(EErrorCode.missingArg).plusMsg("getByWechatPNOpenId");
        return this.getOne(Qw.create().eq(ULoginChannel.LOGIN_ID, openId).eq(ULoginChannel.LOGIN_TYPE, ELoginType.wechatPnOpenid));
    }

    @Override
    public ULoginChannel getByWechatOpenAppOpenId(String openId) {
        if (CheckUtil.strIsEmpty(openId))
            throw NBException.create(EErrorCode.missingArg).plusMsg("getByWechatOpenAppOpenId");
        return this.getOne(Qw.create().eq(ULoginChannel.LOGIN_ID, openId).eq(ULoginChannel.LOGIN_TYPE, ELoginType.wechatAppOpenid));
    }

    /**
     * 分页查询
     *
     * @param pg
     * @param searchParams
     * @return
     */
    @Override
    public IPage<ULoginChannel> pageData(Pg pg, ULoginChannel.SearchParams searchParams) {
        return this.prepareReturnModel(this.page(pg.page(), searchParams.preWrapper(pg.wrapper())));
    }

    /**
     * 列表查询
     *
     * @param searchParams
     * @return
     */
    @Override
    public List<ULoginChannel> listData(ULoginChannel.SearchParams searchParams) {
        return this.prepareReturnModel(this.list(searchParams.preWrapper(null)));
    }

    @Override
    public List<ULoginChannel> listByUserId(Long userId) {
        if (CheckUtil.objIsEmpty(userId))
            throw NBException.create(EErrorCode.missingArg);
        return this.list(Qw.create().eq(ULoginChannel.USER_ID, userId));
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
                    <String, Object>> pageMapData(Pg pg, ULoginChannel.SearchParams searchParams) {
        return mapper.pageMapData(pg.page(), searchParams);
    }

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    @Override
    public ULoginChannel prepareReturnModel(ULoginChannel data) {
        return data;
    }

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    @Override
    public IPage<ULoginChannel> prepareReturnModel(IPage<ULoginChannel> page) {
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
    public List<ULoginChannel> prepareReturnModel(List<ULoginChannel> list) {
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
    public void validData(ULoginChannel data) {
        if (CheckUtil.objIsEmpty(data))
            throw NBException.create(EErrorCode.missingArg);
        if (CheckUtil.objIsEmpty(data.getUserId()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("userId");
        if (CheckUtil.objIsEmpty(data.getLoginType()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("loginType");
        if (CheckUtil.objIsEmpty(data.getLoginId()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("loginId");
        if (CheckUtil.objIsEmpty(data.getLoginChannelScope()))
            throw NBException.create(EErrorCode.missingArg).plusMsg("loginChannelScope");
        this.validLoginId(data);
    }

    @Override
    public void validLoginId(ULoginChannel data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(data.getLoginId()))
            throw NBException.create(EErrorCode.missingArg);
        Qw qw = Qw.create().eq(ULoginChannel.LOGIN_ID, data.getLoginId());
        if (CheckUtil.objIsNotEmpty(data.getUserId()))
            qw.ne(ULoginChannel.USER_ID, data.getUserId());
        if (this.count(qw) > 0)
            throw NBException.create(EErrorCode.beUsed, "该登录id已被使用");
    }
}
