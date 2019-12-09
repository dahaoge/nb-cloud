package cn.hao.nb.cloud.auth.service.impl;

import cn.hao.nb.cloud.auth.entity.*;
import cn.hao.nb.cloud.auth.mapper.AuthMapper;
import cn.hao.nb.cloud.auth.mapper.UUserInfoMapper;
import cn.hao.nb.cloud.auth.service.*;
import cn.hao.nb.cloud.common.component.config.security.JwtTokenUtil;
import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Pg;
import cn.hao.nb.cloud.common.entity.Qd;
import cn.hao.nb.cloud.common.entity.TokenUser;
import cn.hao.nb.cloud.common.penum.*;
import cn.hao.nb.cloud.common.util.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUserInfo clientUserRegistByPhone(String phone, String userName) {
        return this.clientUserRegistByPhone(phone, userName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUserInfo clientUserRegistByPhone(String phone, String userName, String deptId) {
        if (CheckUtil.objIsEmpty(phone, userName, deptId))
            throw NBException.create(EErrorCode.missingArg);
        UUserInfo userInfo = this.phoneRegist(phone, userName);
        if (CheckUtil.objIsNotEmpty(deptId))
            userDeptService.addData(userInfo.getUserId(), deptId);
        return userInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUserInfo webManagerRegistByPhone(String phone, String userName, String deptIds, String roleCodes) {
        if (CheckUtil.objIsEmpty(phone, userName, deptIds, roleCodes))
            throw NBException.create(EErrorCode.missingArg);
        UUserInfo userInfo = this.phoneRegist(phone, userName);
        userDeptService.addUser2Depts(userInfo.getUserId(), deptIds);
        authService.addUserRoles(userInfo.getUserId(), roleCodes);
        return userInfo;
    }

    @Override
    public UUserInfo loginByPhoneAndPwd(String phone, String pwd) {
        ESourceClient sourceClient = UserUtil.getAndValidRequestClient();
        ELoginChannelScop loginChannelScop = ESourceClient.clientApp == sourceClient ? ELoginChannelScop.CClient :
                ESourceClient.webManageClient == sourceClient ? ELoginChannelScop.manageClient : null;
        if (CheckUtil.objIsEmpty(loginChannelScop))
            throw NBException.create(EErrorCode.authDenied, "未开通登录的来源");
        ULoginChannel loginChannel = loginChannelService.getByTypeAndChannelScope(phone, ELoginType.pwd, loginChannelScop);
        if (CheckUtil.objIsEmpty(loginChannel))
            throw NBException.create(EErrorCode.authIdentityErr, "错误的登录信息");
        UUserInfo result = this.getById(loginChannel.getUserId());
        if (CheckUtil.objIsEmpty(result))
            throw NBException.create(EErrorCode.authIdentityErr, "错误的用户信息");

        //锁定状态
        if (EYn.y.getValue().equals(result.getIsLocked())) {
            if (CheckUtil.objIsNotEmpty(result.getUnlockTime()) && Calendar.getInstance().getTimeInMillis() < result.getUnlockTime().getTime())
                this.changeUserLock(result.getUserId(), EYn.n);
            else
                throw NBException.create(EErrorCode.authDenied, "账户锁定");
        }

        // 验证密码
        String md5Pwd = UserUtil.decodePwd(pwd, result.getSalt());
        if (!md5Pwd.equals(result.getLoginPwd()))
            throw NBException.create(EErrorCode.authIdentityErr, "用户名或密码错误 ");

        return result;
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

    private UUserInfo phoneRegist(String phone, String userName) {
        if (CheckUtil.objIsEmpty(phone, userName))
            throw NBException.create(EErrorCode.missingArg);
        UUserInfo userInfo = this.preUser();
        userInfo.setPhone(phone);
        userInfo.setUserName(userName);
        userInfo.setLoginPwd(UserUtil.encodePwd(phone.substring(3), userInfo.getSalt()));
        this.addData(userInfo);
        loginChannelService.addPhoneChannel(userInfo.getUserId(), phone, ELoginChannelScop.CClient);
        return userInfo;
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
     * 修改数据
     *
     * @param data
     * @return
     */
    @Override
    public boolean modifyData(UUserInfo data) {
        this.validData(data);
        data.setUpdateBy(UserUtil.getTokenUser(true).getUserId());
        data.setVersion(null);
        data.setDeleted(null);
        data.setUpdateTime(null);
        data.setCreateTime(null);
        return this.updateById(data);
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
        if (CheckUtil.objIsEmpty(data) || CheckUtil.objIsEmpty(data.getPhone(), data.getUserName(), data.getIsLocked(), data.getSalt()))
            throw NBException.create(EErrorCode.missingArg);
    }
}
