package cn.hao.nb.cloud.auth.service.impl;

import cn.hao.nb.cloud.auth.entity.*;
import cn.hao.nb.cloud.auth.mapper.AuthMapper;
import cn.hao.nb.cloud.auth.mapper.UUserInfoMapper;
import cn.hao.nb.cloud.auth.service.*;
import cn.hao.nb.cloud.common.component.config.security.JwtTokenUtil;
import cn.hao.nb.cloud.common.entity.*;
import cn.hao.nb.cloud.common.penum.*;
import cn.hao.nb.cloud.common.util.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author hao@179314039@qq.com
 * @since 2019-12-08
 */
@Service
public class UUserInfoServiceImpl extends ServiceImpl<UUserInfoMapper, UUserInfo> implements IUUserInfoService {

    @Autowired
    IDUtil idUtil;
    @Autowired
    UUserInfoMapper mapper;
    @Autowired
    IUUserDeptService userDeptService;
    @Autowired
    AuthMapper authMapper;
    @Autowired
    IULoginChannelService loginChannelService;
    @Autowired
    IAuthService authService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    ISysDeptService deptService;
    @Autowired
    AliSmsUtil smsUtil;
    @Autowired
    CommonService commonService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUserInfo clientUserRegistByPhone(String phone, String userName) {
        return this.clientUserRegistByPhone(phone, userName, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUserInfo clientUserRegistByPhone(String phone, String userName, String deptIds) {
        if (CheckUtil.objIsEmpty(phone, userName))
            throw NBException.create(EErrorCode.missingArg);
        // 添加用户信息
        UUserInfo userInfo = this.phoneRegist(phone, userName);
        // 添加C端登录渠道
        loginChannelService.addPhoneChannel(userInfo.getUserId(), phone, ELoginChannelScop.CClient);
        if (CheckUtil.objIsNotEmpty(deptIds))
            userDeptService.addUser2Depts(userInfo.getUserId(), deptIds);
        return userInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUserInfo webManagerRegistByPhone(String phone, String userName, String deptIds, String roleCodes) {
        if (CheckUtil.objIsEmpty(phone, userName))
            throw NBException.create(EErrorCode.missingArg);
        // 添加用户信息
        UUserInfo userInfo = this.phoneRegist(phone, userName);
        //添加管理端登录渠道
        loginChannelService.addPhoneChannel(userInfo.getUserId(), phone, ELoginChannelScop.manageClient);
        // 添加端登录渠道
        loginChannelService.addPhoneChannel(userInfo.getUserId(), phone, ELoginChannelScop.CClient);
        if (CheckUtil.objIsNotEmpty(deptIds))
            userDeptService.addUser2Depts(userInfo.getUserId(), deptIds);
        if (CheckUtil.objIsNotEmpty(roleCodes))
            authService.addUserRoles(userInfo.getUserId(), roleCodes);
        return userInfo;
    }

    @Override
    public UUserInfo loginByPwd(String loginId, String pwd) {
        ELoginChannelScop loginChannelScop = UserUtil.getLoginChannelScop();
        if (CheckUtil.objIsEmpty(loginChannelScop))
            throw NBException.create(EErrorCode.authDenied, "未开通的登录渠道");
        ULoginChannel loginChannel = loginChannelService.getByTypeAndChannelScope(loginId, ELoginType.pwd, loginChannelScop);
        if (CheckUtil.objIsEmpty(loginChannel))
            throw NBException.create(EErrorCode.authIdentityErr, "账号或密码错误");
        UUserInfo result = this.getById(loginChannel.getUserId());
        if (CheckUtil.objIsEmpty(result))
            throw NBException.create(EErrorCode.authIdentityErr, "数据错误,没有有效的用户信息");

        this.loginLockCheck(result);

        if (!this.isMatchDBPwd(result, pwd))
            throw NBException.create(EErrorCode.authIdentityErr, "用户名或密码错误 ");

        return result;
    }

    @Override
    public UUserInfo loginByCheckSms(String phone, String smsCheckCode) {
        if (CheckUtil.objIsEmpty(phone, smsCheckCode))
            throw NBException.create(EErrorCode.missingArg);
        if (!smsUtil.checkSms(phone, smsCheckCode))
            throw NBException.create(EErrorCode.argCheckErr, "短信验证码错误");
        ULoginChannel loginChannel = loginChannelService.getByTypeAndChannelScope(phone, ELoginType.checkSms, UserUtil.getLoginChannelScop());
        if (CheckUtil.objIsEmpty(loginChannel))
            throw NBException.create(EErrorCode.authIdentityErr, "请先注册");

        UUserInfo result = this.getById(loginChannel.getUserId());
        this.loginLockCheck(result);

        return result;
    }

    private void loginLockCheck(UUserInfo uUserInfo) {
        //锁定状态
        if (EYn.y.getValue().equals(uUserInfo.getIsLocked())) {
            if (CheckUtil.objIsNotEmpty(uUserInfo.getUnlockTime()) && Calendar.getInstance().getTimeInMillis() < uUserInfo.getUnlockTime().getTime())
                this.unLockUser(uUserInfo.getUserId());
            else
                throw NBException.create(EErrorCode.authDenied, "账户锁定");
        }
    }

    @Override
    public Qd getLoginInfo(String userId) {
        TokenUser tokenUser = authMapper.getTokenUserById(userId);
        if (CheckUtil.objIsEmpty(tokenUser))
            throw NBException.create(EErrorCode.authIdentityErr, "查询不到用户信息");
        Qd result = Qd.create();

        if (UserUtil.getAndValidRequestClient() == ESourceClient.webManageClient) {
            List<String> roleList = ListUtil.getPkList(authMapper.getUserRoles(tokenUser.getUserId()), SysRole.ROLE_CODE);
            tokenUser.setRoleList(roleList);

            tokenUser.setPermissionList(ListUtil.getPkList(authMapper.getUserPermission(tokenUser.getUserId()), SysPermission.PERMISSION_CODE));
            tokenUser.setMenuList(ListUtil.getPkList(authMapper.getUserMenus(tokenUser.getUserId()), SysMenu.MENU_CODE));
        }
        tokenUser.setAuthDeptList(ListUtil.getPkList(deptService.listAllDisDeptByUserId(userId), SysDept.DEPT_ID));
        result.add("tokenUser", tokenUser).add("token", jwtTokenUtil.generateToken(tokenUser));
        return result;
    }

    @Override
    public boolean changeUserLock(String userId, EYn isLocked) {
        if (CheckUtil.objIsEmpty(userId, isLocked))
            throw NBException.create(EErrorCode.missingArg);
        UUserInfo data = new UUserInfo();
        data.setIsLocked(isLocked.getValue());
        data.setUpdateBy(UserUtil.getTokenUser().getUserId());
        data.setUserId(userId);
        return this.updateById(data);
    }

    @Override
    public boolean lockUser(String userId) {
        return this.changeUserLock(userId, EYn.y);
    }

    @Override
    public boolean unLockUser(String userId) {
        return this.changeUserLock(userId, EYn.n);
    }

    private UUserInfo phoneRegist(String phone, String userName) {
        if (CheckUtil.objIsEmpty(phone, userName))
            throw NBException.create(EErrorCode.missingArg);
        UUserInfo userInfo = this.preUser();
        userInfo.setPhone(phone);
        userInfo.setUserName(userName);
        userInfo.setLoginPwd(this.getDefaultPwd(phone, userInfo.getSalt()));
        this.addData(userInfo);

        return userInfo;
    }

    private String getDefaultPwd(String phone, String salt) {
        return UserUtil.encodePwd(phone.substring(3), salt);
    }


    private UUserInfo preUser() {
        UUserInfo userInfo = new UUserInfo();
        userInfo.setSalt(RandomUtil.getRandomSaltL(6));
        userInfo.setIsLocked(0);
        return userInfo;
    }

    /**
     * 添加数据
     *
     * @param data
     * @return
     */
    @Override
    public UUserInfo addData(UUserInfo data) {
        this.validData(data);
        data.setUserId(idUtil.nextId());
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
    public boolean incrementModifyData(UUserInfo data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getUserId()
        ))
            throw NBException.create(EErrorCode.missingArg);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        if (this.updateById(data)) {
            if (CheckUtil.objIsNotEmpty(data.getPhone()) || CheckUtil.objIsNotEmpty(data.getLoginId()))
                this.getLoginInfo(data.getUserId());
        }
        commonService.refreshRedisUser(data.getUserId());
        return true;
    }

    /**
     * 全量更新数据
     *
     * @param data
     * @return
     */
    @Override
    public boolean totalAmountModifyData(UUserInfo data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(
                data.getUserId()
        ))
            throw NBException.create(EErrorCode.missingArg);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        this.update(data, Wrappers.<UUserInfo>lambdaUpdate()
                .set(UUserInfo::getUpdateBy, data.getUpdateBy())
                .set(UUserInfo::getUserName, data.getUserName())
                .set(UUserInfo::getIcnum, data.getIcnum())
                .set(UUserInfo::getIcon, data.getIcon())
                .eq(UUserInfo::getUserId, data.getUserId())
        );
        commonService.refreshRedisUser(data.getUserId());
        return true;
    }

    @Override
    public boolean modifyOrdinaryInfo(UUserInfo data) {
        return this.totalAmountModifyData(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean modifyUserPhone(String userId, String phone) {
        if (CheckUtil.objIsEmpty(userId, phone))
            throw NBException.create(EErrorCode.missingArg);
        TokenUser tokenUser = UserUtil.getTokenUser(true);
        ELoginChannelScop loginChannelScop = UserUtil.getLoginChannelScop();
        if (ELoginChannelScop.CClient == loginChannelScop && !userId.equals(tokenUser.getUserId())) {
            throw NBException.create(EErrorCode.authErr, "只能修改自己的手机号");
        }
        UUserInfo data = new UUserInfo();
        data.setUserId(userId);
        data.setPhone(phone);
        this.validUserPhone(data);
        this.incrementModifyData(data);
        List<ULoginChannel> loginChannelList = loginChannelService.listByUserId(userId);
        loginChannelList.forEach(item -> {
            if (tokenUser.getPhone().equals(item.getLoginId()))
                loginChannelService.modifyLoginId(item.getTId(), phone, userId);
        });
        return this.incrementModifyData(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean modifyUserPhone(String userId, String phone, String smsCheckCode) {
        if (CheckUtil.objIsEmpty(userId, phone, smsCheckCode))
            throw NBException.create(EErrorCode.missingArg);
        if (!smsUtil.checkSms(phone, smsCheckCode))
            throw NBException.create(EErrorCode.argCheckErr, "短信验证码错误");
        return this.modifyUserPhone(userId, phone);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean initLoginId(String loginId) {
        if (CheckUtil.objIsEmpty(loginId))
            throw NBException.create(EErrorCode.missingArg);
        if (CheckUtil.isPhone(loginId))
            throw NBException.create(EErrorCode.argCheckErr, "请不要将手机号码设置为登录id");
        TokenUser tokenUser = UserUtil.getTokenUser(true);
        UUserInfo userInfo = this.getById(tokenUser.getUserId());
        if (CheckUtil.objIsEmpty(userInfo))
            throw NBException.create(EErrorCode.authIdentityErr, "账号异常,无法查询用户信息");
        if (CheckUtil.objIsNotEmpty(userInfo.getLoginId()))
            throw NBException.create(EErrorCode.repeat, "您之前设置过登录ID,无法修改");
        // 验证登录id唯一性
        if (loginChannelService.countByLoginId(loginId) > 0)
            throw NBException.create(EErrorCode.beUsed, "很抱歉,你输入的用户ID已被占用");

        UUserInfo temp = new UUserInfo();
        temp.setUserId(userInfo.getUserId());
        temp.setLoginId(loginId);
        this.incrementModifyData(temp);
        List<ULoginChannel> loginChannelList = loginChannelService.listByUserId(tokenUser.getUserId());
        List<ELoginChannelScop> loginChannelScops = Lists.newArrayList();
        if (CheckUtil.objIsEmpty(loginChannelList))
            throw NBException.create(EErrorCode.authErr, "数据异常,没有登录渠道数据");
        for (ULoginChannel loginChannel : loginChannelList) {
            if (!loginChannelScops.contains(loginChannel.getLoginChannelScope()))
                loginChannelScops.add(loginChannel.getLoginChannelScope());
        }
        loginChannelScops.forEach(item -> {
            loginChannelService.addLoginChannel(tokenUser.getUserId(), ELoginType.pwd, loginId, item);
        });

        return false;
    }

    @Override
    public boolean modifySelfPwd(String oldPwd, String newPwd1, String newPwd2) {
        if (CheckUtil.objIsEmpty(oldPwd, newPwd1, newPwd2))
            throw NBException.create(EErrorCode.missingArg);
        if (!newPwd1.equals(newPwd2))
            throw NBException.create(EErrorCode.argCheckErr, "两次输入的密码不一致");
        TokenUser tokenUser = UserUtil.getTokenUser(true);
        if (!this.isMatchDBPwd(tokenUser.getUserId(), oldPwd))
            throw NBException.create(EErrorCode.argCheckErr, "原始密码错误");
        UUserInfo temp = new UUserInfo();
        temp.setUserId(tokenUser.getUserId());
        temp.setLoginPwd(newPwd1);
        return this.incrementModifyData(temp);
    }

    @Override
    public boolean managerResetUserPwd(String userId) {
        if (CheckUtil.objIsEmpty(userId))
            throw NBException.create(EErrorCode.missingArg);
        UUserInfo uUserInfo = this.getById(userId);
        if (CheckUtil.objIsEmpty(uUserInfo))
            throw NBException.create(EErrorCode.noData);
        UUserInfo temp = new UUserInfo();
        temp.setUserId(userId);
        temp.setLoginPwd(this.getDefaultPwd(uUserInfo.getPhone(), uUserInfo.getSalt()));
        return this.incrementModifyData(temp);
    }

    @Override
    public boolean resetSelfPwd() {
        return this.managerResetUserPwd(UserUtil.getTokenUser(true).getUserId());
    }


    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delData(String id) {
        if (CheckUtil.strIsEmpty(id))
            throw NBException.create(EErrorCode.missingArg);
        loginChannelService.delByUserId(id);
        return this.removeById(id);
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    @Override
    public UUserInfo getDetail(String id) {
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
    public IPage<UUserInfo> pageData(Pg pg, UUserInfo.SearchParams searchParams) {
        return this.prepareReturnModel(this.page(pg.page(), searchParams.preWrapper(pg.wrapper())));
    }

    /**
     * 列表查询
     *
     * @param searchParams
     * @return
     */
    @Override
    public List<UUserInfo> listData(UUserInfo.SearchParams searchParams) {
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
                    <String, Object>> pageMapData(Pg pg, UUserInfo.SearchParams searchParams) {
        return mapper.pageMapData(pg.page(), searchParams);
    }

    /**
     * 处理返回值
     *
     * @param data
     * @return
     */
    @Override
    public UUserInfo prepareReturnModel(UUserInfo data) {
        return data;
    }

    /**
     * 处理返回值
     *
     * @param page
     * @return
     */
    @Override
    public IPage<UUserInfo> prepareReturnModel(IPage<UUserInfo> page) {
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
    public List<UUserInfo> prepareReturnModel(List<UUserInfo> list) {
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
    public void validData(UUserInfo data) {
        if (CheckUtil.objIsEmpty(data))
            throw NBException.create(EErrorCode.missingArg);
        if (CheckUtil.objIsEmpty(data.getUserId())) {
            if (CheckUtil.objIsEmpty(data.getPhone(), data.getUserName(), data.getIsLocked(), data.getIsLocked()))
                throw NBException.create(EErrorCode.missingArg);
            this.validUserPhone(data);
        }
    }

    @Override
    public void validUserPhone(UUserInfo data) {
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(data.getPhone()))
            throw NBException.create(EErrorCode.missingArg);
        Qw qw = Qw.create().eq(UUserInfo.PHONE, data.getPhone());
        if (CheckUtil.objIsNotEmpty(data.getUserId()))
            qw.ne(UUserInfo.USER_ID, data.getUserId());
        if (this.count(qw) > 0)
            throw NBException.create(EErrorCode.beUsed, "手机号已被使用");
    }

    @Override
    public boolean isMatchDBPwd(String pwd) {
        return this.isMatchDBPwd(UserUtil.getTokenUser(true).getUserId(), pwd);
    }

    @Override
    public boolean isMatchDBPwd(String userId, String pwd) {
        if (CheckUtil.objIsEmpty(userId, pwd))
            throw NBException.create(EErrorCode.missingArg);
        UUserInfo userInfo = this.getById(userId);
        if (CheckUtil.objIsEmpty(userInfo))
            throw NBException.create(EErrorCode.noData);
        return this.isMatchDBPwd(userInfo, pwd);
    }

    @Override
    public boolean isMatchDBPwd(UUserInfo userInfo, String pwd) {
        if (CheckUtil.objIsEmpty(userInfo, pwd))
            throw NBException.create(EErrorCode.missingArg);
        String md5Pwd = UserUtil.decodePwd(pwd, userInfo.getSalt());
        return md5Pwd.equals(userInfo.getLoginPwd());
    }

}
