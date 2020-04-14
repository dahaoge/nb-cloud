package cn.hao.nb.cloud.common.penum;

import cn.hao.nb.cloud.common.entity.PEnum;

/**
 * @Auther: hao
 * @Date: 2020/4/14 18:00
 * @Description:
 */
public enum EYdglDataCollectionCycle implements PEnum<String> {
    m5("5分钟"),
    m10("10分钟"),
    m15("15分钟"),
    m30("30分钟"),
    h1("1小时"),
    h2("2小时"),
    h6("6小时"),
    h12("12小时"),
    d1("1天"),
    w1("1周"),
    month1("1月"),
    year1("1年");

    private String desc;

    EYdglDataCollectionCycle(String desc) {
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