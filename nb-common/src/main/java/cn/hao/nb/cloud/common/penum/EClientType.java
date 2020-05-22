package cn.hao.nb.cloud.common.penum;

import cn.hao.nb.cloud.common.entity.PEnum;

/**
 * @Auther: hao
 * @Date: 2020/5/22 23:05
 * @Description:
 */
public enum EClientType implements PEnum<String> {
    android("安卓"), ios("ios"), nono("无"), web("无"), wechatMiniApp("无");

    private String desc;

    EClientType(String desc) {
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