package cn.hao.nb.cloud.common.penum;

import cn.hao.nb.cloud.common.entity.PEnum;

/**
 * @Auther: hao
 * @Date: 2020/5/25 22:43
 * @Description:
 */
public enum EDemandRespType implements PEnum<String> {
    realTime("实时"),
    promiss("约定");

    private String desc;

    EDemandRespType(String desc) {
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