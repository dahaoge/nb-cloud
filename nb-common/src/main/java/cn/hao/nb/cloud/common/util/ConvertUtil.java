package cn.hao.nb.cloud.common.util;

import cn.hao.nb.cloud.common.entity.NBException;
import cn.hao.nb.cloud.common.entity.Qd;
import cn.hao.nb.cloud.common.penum.EErrorCode;
import cn.hao.nb.cloud.common.penum.EMethod;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import org.springframework.util.MultiValueMap;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 转换工具类
 */
public class ConvertUtil {

    public static void copyProps(Object from, Object to) {
        if (CheckUtil.objIsEmpty(from, to))
            return;
        Arrays.asList(from.getClass().getMethods()).forEach(fm -> {
            try {
                if (fm.getName().indexOf("get") == 0 && CheckUtil.objIsNotEmpty(fm.invoke(from))) {
                    Arrays.asList(to.getClass().getMethods()).forEach(tm -> {
                        if (tm.getName().equals(fm.getName().replace("get", "set"))) {
                            try {
                                tm.invoke(to, fm.invoke(from));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 转换实体
     *
     * @param obj
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T changeObj(byte[] obj, Class<T> tClass) throws UnsupportedEncodingException {
        return JSON.parseObject(new String(obj, "UTF-8"), tClass);
    }

    /**
     * 转换实体
     *
     * @param obj
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T changeObj(Object obj, Class<T> tClass) {
        if (obj instanceof Serializable) {
            return JSON.parseObject(JSON.toJSONString(obj), tClass);
        } else {
            return JSON.parseObject(obj.toString(), tClass);
        }

    }

    public static <T> T changeObj(Object obj, TypeReference<T> tClass) {
        if (obj instanceof Serializable) {
            return JSON.parseObject(JSON.toJSONString(obj), tClass);
        } else {
            return JSON.parseObject(obj.toString(), tClass);
        }
    }

    public static Map<String, Object> houseVisitLogRangeUnit(Map<String, Object> map, String col, String unit) {
        if (CheckUtil.objIsEmpty(map, col, unit))
            return map;
        String colVal = (String) map.get(col);
        if (CheckUtil.strIsEmpty(colVal))
            return map;
        map.put(col, colVal.indexOf("lt") > -1 ? colVal.replace("lt", "") + unit + "以下" : colVal.indexOf("gt") > -1 ? colVal.replace("gt", "") + unit + "以上" :
                colVal.replace("to", "-") + unit);
        return map;
    }

    public static Map<String, Object> mapCnt2Int(Map<String, Object> map) {
        if (CheckUtil.objIsEmpty(map))
            return map;
        String cntK = "cnt";
        for (String key : map.keySet()) {
            if (key.toLowerCase().indexOf("cnt") > -1)
                cntK = key;
        }
        return mapCnt2Int(map, cntK);
    }

    public static Map<String, Object> mapCnt2Int(Map<String, Object> map, String cntK) {
        if (CheckUtil.objIsEmpty(map))
            return map;
        if (CheckUtil.strIsEmpty(cntK))
            cntK = "cnt";
        if (CheckUtil.objIsEmpty(map.get(cntK)))
            map.put(cntK, 0);
        else {
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(map.get(cntK).toString());
            if (isNum.matches())
                map.put(cntK, Integer.parseInt(map.get(cntK).toString()));
            else
                map.put(cntK, 0);
        }
        return map;
    }

    public static List<Map<String, Object>> convertEnum2MapNonException(String enumName) {
        String packageName = "cn.hao.nb.cloud.common.penum.";
        //需要的参数
        Class<?> clazz1 = null;
        List<Map<String, Object>> result = Lists.newArrayList();
        try {
            clazz1 = Class.forName(packageName + enumName);

            //获取内部内的类名
            String simpleName = clazz1.getSimpleName();
            if (simpleName.equals(enumName)) {
                //判断类是不是枚举类
                if (clazz1.isEnum()) {
                    //反射获取枚举类
                    Class<Enum> clazz = (Class<Enum>) Class.forName(clazz1.getName());
                    //获取所有枚举实例
                    Enum[] enumConstants = clazz.getEnumConstants();
                    //根据方法名获取方法
                    for (Enum enum1 : enumConstants) {
                        //执行枚举方法获得枚举实例对应的值
                        result.add(Qd.create().add("val", clazz.getMethod("getValue").invoke(enum1)).add("desc", clazz.getMethod("toChString").invoke(enum1)));
                    }
                }
            }
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    public static List<Object> convertEnumVal2ObjNonException(String enumName) {
        String packageName = "cn.hao.nb.cloud.common.penum.";
        //需要的参数
        Class<?> clazz1 = null;
        List<Object> result = Lists.newArrayList();
        try {
            clazz1 = Class.forName(packageName + enumName);

            //获取内部内的类名
            String simpleName = clazz1.getSimpleName();
            if (simpleName.equals(enumName)) {
                //判断类是不是枚举类
                if (clazz1.isEnum()) {
                    //反射获取枚举类
                    Class<Enum> clazz = (Class<Enum>) Class.forName(clazz1.getName());
                    //获取所有枚举实例
                    Enum[] enumConstants = clazz.getEnumConstants();
                    //根据方法名获取方法
                    for (Enum enum1 : enumConstants) {
                        //执行枚举方法获得枚举实例对应的值
                        result.add(clazz.getMethod("getValue").invoke(enum1));
                    }
                }
            }
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    /**
     * 转换MulitMap
     *
     * @param map
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> List<Qd> convertMulitMap(MultiValueMap<T, V> map) {
        if (CheckUtil.objIsEmpty(map)) {
            return null;
        }

        List<Qd> rtnList = Lists.newLinkedList();

        for (T key : map.keySet()) {
            rtnList.add(Qd.create().add("key", key).add("value", map.get(key)));
        }

        return rtnList;
    }

    /**
     * 转换int，无法转换返回null
     *
     * @param o
     * @return
     */
    public static Integer toIntNonException(Object o) {
        if (o == null) {
            return null;
        }

        try {
            return Integer.parseInt(o.toString());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 转换为int
     *
     * @param o
     * @return
     */
    public static int toInt(Object o) {
        if (o == null) {
            throw new NBException(EErrorCode.missingArg);
        }

        String str = o.toString();
        //转换为空
        if (CheckUtil.strIsEmpty(str)) {
            throw new NBException(EErrorCode.missingArg);
        }

        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            throw new NBException(EErrorCode.unkown, "err to parse:" + str);
        }
    }

    /**
     * 转换为long
     *
     * @param o
     * @return
     */
    public static long toLong(Object o) {
        if (o == null) {
            throw new NBException(EErrorCode.missingArg);
        }

        String str = o.toString();
        //转换为空
        if (CheckUtil.strIsEmpty(str)) {
            throw new NBException(EErrorCode.missingArg);
        }

        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            throw new NBException(EErrorCode.unkown, "err to parse:" + str);
        }
    }

    /**
     * 转换为double
     *
     * @param o
     * @return
     */
    public static double toDouble(Object o) {
        if (o == null) {
            throw new NBException(EErrorCode.missingArg);
        }

        String str = o.toString();
        //转换为空
        if (CheckUtil.strIsEmpty(str)) {
            throw new NBException(EErrorCode.missingArg);
        }

        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            throw new NBException(EErrorCode.unkown, "err to parse:" + str);
        }
    }

    /**
     * 转换为boolean
     *
     * @param o
     * @return
     */
    public static boolean toBoolean(Object o) {
        if (o == null) {
            throw new NBException(EErrorCode.missingArg);
        }

        String str = o.toString();
        //转换为空
        if (CheckUtil.strIsEmpty(str)) {
            throw new NBException(EErrorCode.missingArg);
        }

        try {
            return Boolean.parseBoolean(str);
        } catch (Exception e) {
            throw new NBException(EErrorCode.unkown, "err to parse:" + str);
        }
    }


    /**
     * 转换为string
     *
     * @param o
     * @return
     */
    public static String toString(Object o) {
        if (o == null) {
            return null;
        }

        String str = o.toString();
        //转换为空
        if (CheckUtil.strIsEmpty(str)) {
            throw new NBException(EErrorCode.missingArg);
        }

        try {
            return str;
        } catch (Exception e) {
            throw new NBException(EErrorCode.unkown, "err to parse:" + str);
        }
    }

    /**
     * 下划线转驼峰
     *
     * @return
     */
    public static String toCamel(String source) {
        if (CheckUtil.strIsEmpty(source)) {
            return source;
        }

        if (source.indexOf("_") > 0) {
            return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, source);
        }

        return source;
    }

    /**
     * 驼峰转下划线
     *
     * @param str
     * @return
     */
    public static String toUnderLine(String str) {
        return ConvertUtil.toUnderLine(new StringBuffer(str)).toString();
    }

    public static StringBuffer toUnderLine(StringBuffer str) {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if (matcher.find()) {
            sb = new StringBuffer();
            //将当前匹配子串替换为指定字符串，并且将替换后的子串以及其之前到上次匹配子串之后的字符串段添加到一个StringBuffer对象里。
            //正则之前的字符和被替换的字符
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
            //把之后的也添加到StringBuffer对象里
            matcher.appendTail(sb);
        } else {
            return sb;
        }
        return toUnderLine(sb);
    }

    /**
     * 获取输入值的get方法
     *
     * @param resource
     * @return
     */
    public static String makeMethodName(String resource, EMethod method) {
        if (CheckUtil.objIsEmpty(resource)) {
            return resource;
        }

        //需要进行下划线转驼峰
        StringBuilder sb = new StringBuilder(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, resource));
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        sb.insert(0, method.name());

        return sb.toString();
    }

    /**
     * 获取精确到秒的时间戳
     *
     * @return
     */
    public static int getSecondTimestamp() {
        return getSecondTimestamp(new Date());
    }

    /**
     * 获取精确到秒的时间戳
     *
     * @return
     */
    public static int getSecondTimestamp(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0, length - 3));
        } else {
            return 0;
        }
    }


    /**
     * 去除字符串中的\r\n\t
     *
     * @return
     */
    public static String replaceRnt(String input) {
        if (CheckUtil.objIsEmpty(input)) {
            return input;
        }

        return input.replaceAll("\r|\n", "").replaceAll("\t", "");
    }

    /**
     * * 检测是否有emoji字符
     * * @param source 需要判断的字符串
     * * @return 一旦含有就抛出
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!notisEmojiCharacter(codePoint)) {
                //判断确认有表情字符
                return true;
            }
        }
        return false;
    }

    /**
     * * 非emoji表情字符判断
     * * @param codePoint
     * * @return
     */
    private static boolean notisEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <=
                0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    public static String filterEmoji(String source) {

        if (CheckUtil.strIsEmpty(source)) {
            return source;
        }

        source = source.replaceAll("[\ue000-\uefff]", "");

        if (!containsEmoji(source)) {
            return source;//如果不包含，直接返回
        }

        StringBuilder buf = null;//该buf保存非emoji的字符
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (notisEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            }
        }

        if (buf == null) {
            return "";//如果没有找到非emoji的字符，则返回无内容的字符串
        } else {
            if (buf.length() == len) {
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
    }

}