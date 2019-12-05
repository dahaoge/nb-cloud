package cn.hao.nb.cloud.common.util;

import com.alibaba.fastjson.JSONObject;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * 检测
 */
public class CheckUtil {

    /**
     * 判断字符串是否为空, 有一个为空则返回true
     *
     * @param strs
     * @return
     */
    public static boolean strIsEmpty(String... strs) {
        if (strs == null || strs.length == 0) {
            return true;
        }

        for (String str : strs) {
            if (str == null || str.trim().length() == 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断字符串非空, 有一个为空返回false
     *
     * @return
     */
    public static boolean strIsNotEmpty(String... strs) {
        if (strs == null || strs.length == 0) {
            return false;
        }

        for (String str : strs) {
            if (str == null || str.trim().length() == 0) {
                return false;
            }
        }

        return true;

    }

    /**
     * 判断对象是否为空, 有一个为空则返回true
     *
     * @param objs
     * @return
     */
    public static boolean objIsEmpty(Object... objs) {
        if (objs == null || objs.length == 0) {
            return true;
        }

        for (Object obj : objs) {
            if (Objects.isNull(obj)) {
                return true;
            } else if (obj instanceof String && strIsEmpty(obj.toString())) {
                return true;
            } else if (obj instanceof Collection && collectionIsEmpty((Collection) obj)) {
                return true;
            } else if (obj instanceof Map && mapIsEmpty((Map) obj)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断对象非空, 有一个为空返回false
     *
     * @param objs
     * @return
     */
    public static boolean objIsNotEmpty(Object... objs) {
        if (objs == null || objs.length == 0) {
            return false;
        }

        for (Object obj : objs) {
            if (Objects.isNull(obj)) {
                return false;
            } else if (obj instanceof String && strIsEmpty(obj.toString())) {
                return false;
            } else if (obj instanceof Collection && collectionIsEmpty((Collection) obj)) {
                return false;
            } else if (obj instanceof Map && mapIsEmpty((Map) obj)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断集合对象是否为空
     *
     * @param cs
     * @return
     */
    public static boolean collectionIsEmpty(Collection... cs) {
        if (cs == null || cs.length == 0) {
            return true;
        }

        for (Collection c : cs) {
            if (c == null || c.size() <= 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断集合对象是否不为空
     *
     * @param cs
     * @return
     */
    public static boolean collectionIsNotEmpty(Collection... cs) {
        if (cs == null || cs.length == 0) {
            return false;
        }

        for (Collection c : cs) {
            if (c == null || c.size() <= 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断Map对象是否为空
     *
     * @param ms
     * @return
     */
    public static boolean mapIsEmpty(Map... ms) {
        if (ms == null || ms.length == 0) {
            return true;
        }

        for (Map m : ms) {
            if (m == null || m.size() <= 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断Map对象是否不为空
     *
     * @param ms
     * @return
     */
    public static boolean mapIsNotEmpty(Map... ms) {
        if (ms == null || ms.length == 0) {
            return false;
        }

        for (Map m : ms) {
            if (m == null || m.size() <= 0) {
                return false;
            }
        }

        return true;
    }


    /**
     * 判断数组对象是否为空
     *
     * @param os
     * @return
     */
    public static boolean arrayIsEmpty(Object[]... os) {
        if (os == null || os.length == 0) {
            return true;
        }

        for (Object[] o : os) {
            if (o == null || o.length == 0) {
                return true;
            }
        }

        return false;
    }


    /**
     * 判判断数组对象是否不为空
     *
     * @param os
     * @return
     */
    public static boolean arrayIsNotEmpty(Object[]... os) {
        if (os == null || os.length == 0) {
            return false;
        }

        for (Object[] o : os) {
            if (o == null || o.length == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断json是否正确
     *
     * @param jsonInString
     * @return
     */
    public final static boolean isJson(String jsonInString) {
        try {
            JSONObject.parse(jsonInString);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 经纬度校验
     * 经度longitude: (?:[0-9]|[1-9][0-9]|1[0-7][0-9]|180)\\.([0-9]{6})
     * 纬度latitude：  (?:[0-9]|[1-8][0-9]|90)\\.([0-9]{6})
     *
     * @return
     */
    public static boolean isPosition(String longitude, String latitude) {
        String reglo = "((?:[0-9]|[1-9][0-9]|1[0-7][0-9])\\.([0-9]{0,6}))|((?:180)\\.([0]{0,6}))";
        String regla = "((?:[0-9]|[1-8][0-9])\\.([0-9]{0,6}))|((?:90)\\.([0]{0,6}))";
        longitude = longitude.trim();
        latitude = latitude.trim();
        return longitude.matches(reglo) == true ? latitude.matches(regla) : false;
    }

    public static boolean isPhone(String phoneNum) {
        Long phone = Long.parseLong(phoneNum);
        if (phone > 13000000000L && phone < 19999999999L)
            return true;
        return false;
    }

    public static boolean isDateBetween(Date date, Date start, Date end) {
        if (CheckUtil.objIsEmpty(date, start, end))
            return false;
        return date.getTime() >= start.getTime() && date.getTime() <= end.getTime();
    }

    public static boolean isDateBetweenADay(Date date, Date aDay) {
        if (CheckUtil.objIsEmpty(date, aDay))
            return false;
        return CheckUtil.isDateBetween(date, DateUtil.getDayStartTime(aDay), DateUtil.getDayEndTime(aDay));
    }
}
