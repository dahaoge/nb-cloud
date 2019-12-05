package cn.hao.nb.cloud.common.util;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author: scootXin
 * @Date: 2019/1/27 11:27
 */
public class MathUtil {

    /**
     * 判断金额是否相等
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean eq(Double a, Double b) {
        BigDecimal aDecimal = BigDecimal.valueOf(a);
        BigDecimal bDecimal = BigDecimal.valueOf(b);

        Double aDouble = aDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue() * 100;
        Double bDouble = bDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue() * 100;

        return aDouble.intValue() == bDouble.intValue();
    }

    /**
     * Double 减法
     *
     * @param a
     * @param b
     * @return
     */
    public static Double sub(Double a, Double b) {
        BigDecimal aDecimal = BigDecimal.valueOf(a);
        BigDecimal bDecimal = BigDecimal.valueOf(b);

        aDecimal.subtract(bDecimal);
        return aDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    public static Double multi(Double a, Integer b) {
        BigDecimal aDecimal = BigDecimal.valueOf(a);
        BigDecimal bDecimal = BigDecimal.valueOf(b);

        aDecimal.multiply(bDecimal);

        return aDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    /**
     * 创建double
     *
     * @param d
     * @return
     */
    public static Double createDoubleWith2Accuracy(Double d) {
        if (d == null) {
            return d;
        }

        BigDecimal bigDecimal = BigDecimal.valueOf(d);

        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    /**
     * double类型的减法
     *
     * @param a
     * @param b
     * @return
     */
    public static Double subtractionWith2Accuracy(Double a, Double b) {
        if (a == null) {
            return null;
        }

        BigDecimal aDecimal = BigDecimal.valueOf(a);

        if (b == null) {
            return aDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        }

        BigDecimal bDecimal = BigDecimal.valueOf(b);

        return aDecimal.subtract(bDecimal).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

}
