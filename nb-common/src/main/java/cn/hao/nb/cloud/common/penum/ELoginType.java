package cn.hao.nb.cloud.common.penum;

import cn.hao.nb.cloud.common.entity.PEnum;

/**
 * @Auther: hao
 * @Date: 2019-12-08 14:01
 * @Description:
 */
public enum ELoginType implements PEnum<String> {
    checkSms("短信验证码登录"), pwd("密码登录"),
    wechatUnionid("微信unionId"),
    wechatMpOpenid("微信小程序openid"),
    wechatPnOpenid("微信公众号openid"),
    wechatAppOpenid("微信开放平台openId");

    private String desc;

    ELoginType(String desc) {
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return name();
    }

    @Override
    public String toString() {
        return name();
    }

    @Override
    public String toChString() {
        return desc;
    }
}