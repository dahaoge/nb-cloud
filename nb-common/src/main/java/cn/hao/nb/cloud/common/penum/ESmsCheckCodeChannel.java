package cn.hao.nb.cloud.common.penum;


import cn.hao.nb.cloud.common.entity.PEnum;

/**
 * @Auther: sunhao
 * @Date: 2019-05-01 14:01
 * @Description:
 */
public enum ESmsCheckCodeChannel implements PEnum<String> {
    login("登录"), registe("注册"), other("其他");

    private String desc;

    ESmsCheckCodeChannel(String desc) {
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