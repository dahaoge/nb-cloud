package cn.hao.nb.cloud.common.penum;

import cn.hao.nb.cloud.common.entity.PEnum;

/**
 * @Auther: hao
 * @Date: 2020/5/9 11:17
 * @Description:
 */
public enum EYdglDeviceType implements PEnum<String> {
    stationHouse("站房"),
    transformer("变压器"),
    line("线路"),
    motor("电机"),
    elecSwitch("开关");


    private String desc;

    EYdglDeviceType(String desc) {
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