package cn.hao.nb.cloud.common.penum;

import cn.hao.nb.cloud.common.entity.PEnum;

/**
 * @Auther: hao
 * @Date: 2020/5/21 20:26
 * @Description:
 */
public enum EDateType implements PEnum<String> {
    year, month, week, day, hour, minute, second;


    EDateType() {

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
        return name();
    }
}