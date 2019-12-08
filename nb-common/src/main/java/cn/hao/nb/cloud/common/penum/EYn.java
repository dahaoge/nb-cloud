package cn.hao.nb.cloud.common.penum;

import cn.hao.nb.cloud.common.entity.PEnum;

/**
 * 正常、错误码
 */
public enum EYn implements PEnum<Integer> {
    y(1), n(0);

    private int value;

    EYn(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name();
    }

    @Override
    public String toChString() {
        return name();
    }
}
