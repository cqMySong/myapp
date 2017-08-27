package com.myapp.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static String DATEFORMT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static String DATEFORMT_YMD = "yyyy-MM-dd";
	private static int DAY = 24;
	private static int HOUR = 60;
	private static int MINUTE = 60;
	private static int SECOND = 1000;
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

	public static Date getFirstDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
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

    public static Long dateDiff(Date date1, Date date2) {
        long times1 = date1 == null ? 0L : date1.getTime();
        long times2 = date2 == null ? 0L : date2.getTime();
        return Long.valueOf(times1 - times2);
    }

    public static Date getLastDayOfWeek(Date date) {
    	return addDay(getFirstDayOfWeek(date),6);
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
	/**
	 * 两日期相差天数
	 * @return
	 */
    public static long betweenDays(Date edate, Date bdate) {
    	if(BaseUtil.isEmpty(bdate)||BaseUtil.isEmpty(edate)) return 0;
    	Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		if(bdate.before(edate)){
			c1.setTime(bdate);
			c2.setTime(edate);
		}else{
			c2.setTime(bdate);
			c1.setTime(edate);
		}
		return (int) ((c2.getTimeInMillis()-c1.getTimeInMillis())/(DAY * HOUR*MINUTE * SECOND));
    }
    /**
	 * 当前日期月份对应的第一天日期
	 */
	public static Date getMonthFirstDay(Date date){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		c1.set(Calendar.DAY_OF_MONTH, 1);
		return c1.getTime();
	}
	/**
	 * 返回两个日期相差的月数
	 */
	public static int betweenMonths(Date bdate, Date edate,boolean hasDay){
		if(BaseUtil.isEmpty(bdate)||BaseUtil.isEmpty(edate)) return 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		
		if(bdate.before(edate)){
			c1.setTime(bdate);
			c2.setTime(edate);
		}else{
			c1.setTime(edate);
			c2.setTime(bdate);
		}
		int months = (c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR))*12+(c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH));
		if(hasDay){
			if(c2.get(Calendar.DAY_OF_MONTH)+1<c1.get(Calendar.DAY_OF_MONTH)){
				months = months-1;
			}
		}
		return months;
	}
	
	
	/**
	 * 返回当期月的下一个月1号
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getNextFirstDate(int curYear,int curMonth){
		Calendar c = Calendar.getInstance();
		c.set(curYear, curMonth, 1);
		Date curDate = c.getTime();
		return curDate;
	}
	
	/**
	 * 获得上一个月1号
	 * @param curYear
	 * @param curMonth
	 * @return
	 */
	public static Date getPreFirstDate(int curYear,int curMonth){
		Calendar c = Calendar.getInstance();
		c.set(curYear, curMonth, 1);
		Date curDate = c.getTime();
		return addMonth(curDate, -1);
	}
	/**
	 * 获取当前日期的总天数
	 * @param curDate
	 * @return
	 */
	public static int getCurMonthDays(Date curDate){
		Calendar c = Calendar.getInstance();
		c.set(getYear(curDate), getMonth(curDate), 1);
		Date curMm = addDay(c.getTime(), -1);
		return getDay(curMm);
	}
	
	/**
	 * 得到指定月的天数
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getMonthDays(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}
    
    public static void main(String[] args){
    	System.out.println(formatDate(getLastDayOfWeek(new Date())));
    }
    
}
