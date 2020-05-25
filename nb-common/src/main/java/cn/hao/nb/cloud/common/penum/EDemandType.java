package cn.hao.nb.cloud.common.penum;

import cn.hao.nb.cloud.common.entity.PEnum;

/**
 * @Auther: hao
 * @Date: 2020/5/25 22:42
 * @Description:
 */
public enum EDemandType implements PEnum<String> {
    f("削峰"),
    g("填谷");

    private String desc;

    EDemandType(String desc) {
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