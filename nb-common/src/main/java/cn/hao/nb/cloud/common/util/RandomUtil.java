package cn.hao.nb.cloud.common.util;


import cn.hao.nb.cloud.common.entity.Sb;

import java.math.BigDecimal;
import java.util.Random;

/**
 * 随机数工具类
 *
 * @Author: scootXin
 * @Date: 2018/12/5 14:44
 */
public class RandomUtil {
    static final int SECURITY_SIZE = 12;

    private static final String ALLCHAR = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 获取随机盐
     *
     * @return
     */
    public static String getRandomSaltL(int length) {
        BigDecimal l = new BigDecimal("1e" + (length - 1));
        Double v = (Math.random() * 9 + 1) * l.doubleValue();

        return ConvertUtil.toString(v.longValue());
    }

    /**
     * 获取随机盐
     *
     * @return
     */
    public static String getRandomSalt() {
        Double v = (Math.random() * 9 + 1) * 1000;

        return ConvertUtil.toString(v.longValue());
    }

    /**
     * 获取随机密码
     *
     * @return
     */
    public static String getRandomSecurity(String salt) {
        Random random = new Random();
        Sb sb = Sb.create();

        for (int i = 0; i < SECURITY_SIZE; i++) {
            sb.add(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return AesUtil.encryptWithSalt(sb.toString(), salt);
    }

    /**
     * 获取随机数
     *
     * @param length
     * @return
     */
    public static String getRandomStr(int length) {
        Random random = new Random();
        Sb sb = Sb.create();

        for (int i = 0; i < length; i++) {
            sb.add(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }
}
