package cn.hao.nb.cloud.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具
 */
public class DateUtil {

    public static Integer subtractTime(Date t1, Date t2) {
        if (CheckUtil.objIsNotEmpty(t1, t2))
            return new Integer(((t1.getTime() - t2.getTime()) / 1000) + "");
        return 0;
    }

    //获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        if (d == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        if (d == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 0);

        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取月开始时间
    public static Date getDayOfMonthStart(Date date) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    //获取月结束时间
    public static Date getDayOfMonthEnd(Date date) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date getDayOfWeekStart(Date date) {
        return DateUtil.getDayStartTime(DateUtil.getWeekMonday(date));
    }

    public static Date getDayOfWeekEnd(Date date) {
        return DateUtil.getDayEndTime(DateUtil.getWeekSunDay(date));
    }

    //日期增加天数
    public static Date dateAddDays(Date date, int days) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);

        return calendar.getTime();
    }

    //时间增加分钟数
    public static Date dateAddMinutes(Date d, int minutes) {
        if (d == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MINUTE, minutes);

        return calendar.getTime();
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException calendar 对日期进行时间操作
     *                        getTimeInMillis() 获取日期的毫秒显示形式
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {

        if (CheckUtil.objIsEmpty(smdate, bdate))
            return 0;

        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }


    /**
     * 获取(本周一),时间是当前时间哦
     *
     * @param date
     * @return
     */
    public static Date getWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    /**
     * 获取周日24点,时间是当前时间哦
     *
     * @param date
     * @return
     */
    public static Date getWeekSunDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getWeekMonday(date));
        cal.add(Calendar.DATE, 6);
        return cal.getTime();
    }

    /**
     * 获取下周一,时间是当前时间哦
     *
     * @param date
     * @return
     */
    public static Date getNextMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getWeekMonday(date));
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }

    /**
     * 获取上周周一,时间是当前时间
     *
     * @param date
     * @return
     */
    public static Date getLastWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getWeekMonday(date));
        cal.add(Calendar.DATE, -7);
        return cal.getTime();
    }

    public static String format(Date date, String fmt) {
        if (CheckUtil.objIsEmpty(date))
            return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fmt);
        return simpleDateFormat.format(date);
    }

}
