package cn.hao.nb.cloud.common.util;


import cn.hao.nb.cloud.common.entity.NBException;
import com.google.common.base.Strings;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 对称加密utils
 */
public class AesUtil {

    public static final String AESKEY = "X81sC3xy150piuix";

    /**
     * 加密
     *
     * @param sSrc   需加密串
     * @param aesStr 加密盐
     * @return
     */
    public static String encrypt(String sSrc, String aesStr) {
        if (CheckUtil.strIsEmpty(sSrc)) {
            return null;
        }

        try {
            SecretKeySpec skeySpec = new SecretKeySpec(aesStr.getBytes("utf-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

            return new Base64().encodeToString(encrypted);
        } catch (Exception e) {
            throw NBException.create(e);
        }
    }

    /**
     * 解密
     *
     * @param sSrc   需解密串
     * @param aesStr 加密盐
     * @return
     */
    public static String decrypt(String sSrc, String aesStr) {
        if (CheckUtil.strIsEmpty(sSrc)) {
            return null;
        }

        try {
            SecretKeySpec skeySpec = new SecretKeySpec(aesStr.getBytes("utf-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new Base64().decode(sSrc);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");

            return originalString;
        } catch (Exception e) {
            throw NBException.create(e);
        }
    }

    /**
     * 带加密盐的加解密
     *
     * @return
     */
    public static String encryptWithSalt(String sSrc, String salt) {
        return encrypt(sSrc + salt, AESKEY);
    }

    /**
     * 带加密盐的加解密
     *
     * @return
     */
    public static String decryptWithSalt(String sSrc, String salt) {
        if (CheckUtil.strIsEmpty(sSrc)) {
            return null;
        }

        String decrypt = decrypt(sSrc, AESKEY);

        decrypt = decrypt.substring(0, decrypt.length() - salt.length());

        return decrypt;
    }

    /**
     * 进行md5加密
     *
     * @param str
     * @return
     */
    public static String getMD5String(String str) {
        return MD5Util.MD5(str);
    }

    /**
     * 进行星星加密
     *
     * @param str
     * @return
     */
    public static String getSecrecyStr(String str, int start, int end) {
        if (Strings.isNullOrEmpty(str) || str.length() < start || start >= end) {
            return str;
        }

        StringBuilder sb = new StringBuilder(str);
        StringBuilder startSb = new StringBuilder();

        for (int i = start; i < end; i++) {
            startSb.append("*");
        }
        sb.replace(start, end, startSb.toString());

        return sb.toString();
    }
}
