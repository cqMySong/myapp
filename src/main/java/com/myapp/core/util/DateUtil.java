package com.myapp.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static String DATEFORMT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static String DATEFORMT_YMD = "yyyy-MM-dd";
    /**
     * 日期大小判断
     */
    public static boolean isBeforeDate(Date begDate, Date endDate) {
        return begDate.before(endDate);
    }

    /**
     * 按照默认格式 格式化日期为文本字符
     */
    public static String formatDate(Date date) {
        return formatDateByFormat(date, DATEFORMT_YMD);
    }

    /**
     * 按照自定义格式 格式化日期为文本字符
     */
    public static String formatDateByFormat(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    /**
     * 按照自定义格式文本日期
     */
    public static Date parseDate(String dateStr, String format) {
        Date date = null;
        try {
        	if(BaseUtil.isEmpty(dateStr)) return date;
            DateFormat df = new SimpleDateFormat(format);
            date = (Date) df.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, DATEFORMT_YMD);
    }
    /**
     *返回年
     */
    public static int getYear(Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.YEAR);
    }

    /**
     *返回月
     */
    public static int getMonth(Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.MONTH) + 1;
    }

    /**
     *返回日
     */
    public static int getDay(Date date) {
        java.util.Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.DAY_OF_MONTH);
    }

    /**
     *返回小时
     */
    public static int getHour(Date date) {
        java.util.Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(java.util.Calendar.HOUR_OF_DAY);
    }
    /**
     *返回分钟
     */
    public static int getMinute(Date date) {
        java.util.Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    /**
     *返回秒
     */
    public static int getSecond(java.util.Date date) {
        java.util.Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.SECOND);
    }
    /**
     *返回日期的毫秒数
     */
    public static long getMillis(java.util.Date date) {
        java.util.Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    public Date getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int min = calendar.getActualMinimum(7);
        int current = calendar.get(7);
        calendar.add(7, min - current);
        Date start = calendar.getTime();
        return start;
    }

    public static Long dateDiff(Date date1, Date date2) {
        long times1 = date1 == null ? 0L : date1.getTime();
        long times2 = date2 == null ? 0L : date2.getTime();

        return Long.valueOf(times1 - times2);
    }

    public static Date getLastDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int min = calendar.getActualMinimum(7);
        int current = calendar.get(7);
        calendar.add(7, min - current);
        calendar.add(7, 6);
        Date end = calendar.getTime();
        return end;
    }

    public static Date addYear(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(1, calendar.get(1) + year);
        return calendar.getTime();
    }

    public static Date addMonth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(2, calendar.get(2) + month);
        return calendar.getTime();
    }

    public static Date addDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, calendar.get(5) + day);
        return calendar.getTime();
    }

    public static Date addHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(11, calendar.get(11) + hour);
        return calendar.getTime();
    }

    public Date addMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(12, calendar.get(12) + minute);
        return calendar.getTime();
    }

    public static Date addSecond(Date date, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(13, calendar.get(13) + second);
        return calendar.getTime();
    }

    public static Date addMillisecond(Date date, int millisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(14, calendar.get(14) + millisecond);
        return calendar.getTime();
    }

    public static long getDaysBetween(Date left, Date right) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        calendar.setTime(left);
        calendar2.setTime(right);

        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);

        calendar2.set(11, 0);
        calendar2.set(12, 0);
        calendar2.set(13, 0);
        calendar2.set(14, 0);

        long leftL = calendar.getTimeInMillis();
        long rightL = calendar2.getTimeInMillis();
        long result = rightL - leftL;
        return result / 86400000L;
    }
    
    
}
