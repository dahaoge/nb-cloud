package cn.hao.nb.cloud.ydglMock;

import cn.hao.nb.cloud.common.util.RandomUtil;

/**
 * @Auther: hao
 * @Date: 2020/4/14 17:20
 * @Description:
 */
public class ElecDesc {

    public static String kWDesc(String title) {
        title = title == null ? "" : title;
        return title.concat("负荷(单位kW)--示例:".concat(RandomUtil.getRandomSaltL(3)));
    }

    public static String kWhDesc(String title) {
        title = title == null ? "" : title;
        return title.concat("用电量/功(单位kWh)--示例:".concat(RandomUtil.getRandomSaltL(3)));
    }

    public static String aDesc(String title) {
        title = title == null ? "" : title;
        return title.concat("电流(单位A)--示例:".concat(RandomUtil.getRandomSaltL(3)));
    }

    public static String vDesc(String title) {
        title = title == null ? "" : title;
        return title.concat("电压(单位V)--示例:".concat(RandomUtil.getRandomSaltL(3)));
    }

    public static String kVDesc(String title) {
        title = title == null ? "" : title;
        return title.concat("电压(单位kV)--示例:".concat(RandomUtil.getRandomSaltL(3)));
    }

    public static String yuanDesc(String title) {
        title = title == null ? "" : title;
        return title.concat("金额(单位元)--示例:".concat(RandomUtil.getRandomSaltL(3)));
    }

    public static String dianjiaDesc(String title) {
        title = title == null ? "" : title;
        return title.concat("电价(单位元/kWh)--示例:".concat(RandomUtil.getRandomSaltL(3)));
    }

    public static String timeDesc(String title) {
        title = title == null ? "" : title;
        return title.concat("采集时间,java.util.date类型");
    }
}
