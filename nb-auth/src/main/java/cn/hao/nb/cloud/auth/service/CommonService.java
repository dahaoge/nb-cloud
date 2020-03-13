package cn.hao.nb.cloud.auth.service;

import cn.hao.nb.cloud.auth.entity.UUserInfo;
import cn.hao.nb.cloud.common.constant.CommonConstant;
import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.RedisUser;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.common.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: hao
 * @Date: 2019-12-15 14:25
 * @Description:
 */
@Slf4j
@Service
public class CommonService {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    IUUserInfoService iuUserInfoService;

    public RedisUser getRedisUser(Long userId) {
        if (CheckUtil.objIsEmpty(userId))
            throw NBException.create(EErrorCode.missingArg);
        RedisUser user = (RedisUser) redisUtil.hget(CommonConstant.REDIS_USER_KEY, userId.toString());
        if (CheckUtil.objIsEmpty(user)) {
            UUserInfo temp = iuUserInfoService.getById(userId);
            if (CheckUtil.objIsNotEmpty(temp)) {
                user = new RedisUser();
                user.setUserId(userId);
                user.setIcnum(temp.getIcnum());
                user.setIcon(temp.getIcon());
                user.setLoginId(temp.getLoginId());
                user.setPhone(temp.getPhone());
                user.setUserName(temp.getUserName());
                redisUtil.hset(CommonConstant.REDIS_USER_KEY, userId.toString(), CommonConstant.REDIS_USER_EXPIRE_TIME);
            }
        }
        return user;
    }

    public void refreshRedisUser(Long userId) {
        if (CheckUtil.objIsEmpty(userId))
            throw NBException.create(EErrorCode.missingArg);
        redisUtil.hdel(CommonConstant.REDIS_USER_KEY, userId);
    }

}
