package com.example.demo.utils.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    /**
     * 格式化日期
     *
     * @param sdate  日期字符串
     * @param format 要格式化的日期格式
     * @return 格式化后的日期字符串
     */
    public static String format(String sdate, String format) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            date = df.parse(sdate);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return df.format(date);
    }

    /**
     * 一个日期是否是指定的日期格式
     *
     * @param dateStr 日期字符串
     * @param pattern 验证的日期格式
     * @return 是否是指定的日期格式
     */
    public static boolean isValidDate(String dateStr, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            df.setLenient(false);
            df.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 将字符串按指定的格式转换为日期类型
     *
     * @param str    日期字符串
     * @param format 指定格式
     * @return 格式化后的日期对象
     */
    public static Date strToDate(String str, String format) {
        SimpleDateFormat dtFormat = null;
        try {
            dtFormat = new SimpleDateFormat(format);
            return dtFormat.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对一个日期进行偏移
     *
     * @param date   日期
     * @param offset 偏移两
     * @return 偏移后的日期
     */
    public static Date addDayByDate(Date date, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_YEAR);
        cal.set(Calendar.DAY_OF_YEAR, day + offset);
        return cal.getTime();
    }

    /**
     * 将日期格式化为<字符串类型>
     *
     * @param dateFormat 日期格式
     * @return 当前日期<字符串类型>
     */
    public static String dataToStr(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    /**
     * 得到当前日期<字符串类型>
     *
     * @param dateFormat 日期格式
     * @return 当前日期<字符串类型>
     */
    public static String getCurrDate(String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(new Date());
    }

    /**
     * 得到当前日期<java.util.Date类型>
     *
     * @param dateFormat 日期格式
     * @return 当前日期<java.util.Date类型>
     */
    public static Date getCurrentDate(String dateFormat) {
        return strToDate(getCurrDate(dateFormat), dateFormat);
    }

    /**
     * 将一个日期转换为指定格式的日期类型
     *
     * @param date       要转换的日期
     * @param dateFormat 日期格式
     * @return 转换后的日期对象
     */
    public static Date formatDate(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return strToDate(sdf.format(date), dateFormat);
    }

    /**
     * 对格式为20080101类型的字符串进行日期格式化
     *
     * @param dateStr    要格式化的字符串
     * @param formatChar 连接字符
     * @param dateFormat 日期格式
     * @return 格式后的日期字符串
     */
    public static String format(String dateStr, String formatChar,
                                String dateFormat) {
        try {
            dateStr = dateStr.substring(0, 4) + formatChar
                    + dateStr.substring(4, 6) + formatChar
                    + dateStr.substring(6, 8);
            return format(dateStr, dateFormat);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 对格式为20080101类型的字符串进行日期格式化
     *
     * @param dateStr    要格式化的字符串
     * @param formatChar 连接字符
     * @param dateFormat 日期格式
     * @return 格式后的日期对象
     */
    public static Date formatDate(String dateStr, String formatChar, String dateFormat) {
        try {
            dateStr = dateStr.substring(0, 4) + formatChar
                    + dateStr.substring(4, 6) + formatChar
                    + dateStr.substring(6, 8);
            return strToDate(dateStr, dateFormat);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获得某一个月份的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayByMonth(Date date) {
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.set(Calendar.DAY_OF_MONTH, 1);
        return formatDate(gc.getTime(), "yyyy-MM-dd");
    }

    /**
     * 获得某一个月份的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayByMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);// 设为当前月的1号
        cal.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        cal.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
        return formatDate(cal.getTime(), "yyyy-MM-dd");
    }

    /**
     * 获得指定日期的年份
     *
     * @param date
     * @return
     */
    public static int getYearByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获得指定日期的月份
     *
     * @param date
     * @return
     */
    public static int getMonthByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    /**
     * 获得指定日期的所在月份当前的天数
     *
     * @param date
     * @return
     */
    public static int getDayInMonthByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得当前传入日期的上一个月份的当前日期
     *
     * @param date
     * @return
     */
    public static Date getPreviousDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        return DateUtil.formatDate(cal.getTime(), "yyyy-MM-dd");
    }

    /**
     * 获得当前传入日期的上N个月份的当前日期
     *
     * @param date
     * @return
     */
    public static Date getPreviousDate(Date date, int num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -num);
        return DateUtil.formatDate(cal.getTime(), "yyyy-MM-dd");
    }

    public static long compareDateDayValue(Date fDate, Date sDate) {
        long l1 = fDate.getTime();
        long l2 = sDate.getTime();
        long diff = (Math.abs(l2 - l1)) / 1000 / 24 / 60 / 60;
        return diff;
    }

    /**
     * 获得当前传入日期的下一个月份的当前日期
     *
     * @param date
     * @return
     */
    public static Date getLastMonthDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        return DateUtil.formatDate(cal.getTime(), "yyyy-MM-dd");
    }

    /**
     * 计算两个日期相差的月数（具体细分到天数的差别）
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getDiffMonths(Date date1, Date date2) {
        int iMonth = 0;
        int flag = 0;
        try {
            Calendar objCalendarDate1 = Calendar.getInstance();
            objCalendarDate1.setTime(date1);
            Calendar objCalendarDate2 = Calendar.getInstance();
            objCalendarDate2.setTime(date2);
            if (objCalendarDate2.equals(objCalendarDate1))
                return 0;
            if (objCalendarDate1.after(objCalendarDate2)) {
                Calendar temp = objCalendarDate1;
                objCalendarDate1 = objCalendarDate2;
                objCalendarDate2 = temp;
            }
            if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1
                    .get(Calendar.DAY_OF_MONTH))
                flag = 1;
            if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1.get(Calendar.YEAR))
                iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1.get(Calendar.YEAR))
                        * 12 + objCalendarDate2.get(Calendar.MONTH) - flag)
                        - objCalendarDate1.get(Calendar.MONTH);
            else
                iMonth = objCalendarDate2.get(Calendar.MONTH) - objCalendarDate1.get(Calendar.MONTH) - flag;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iMonth;
    }

    /**
     * 计算两个日期月数的差别，不计算详细到天数的差别
     * 日期大的为参数一，结果为正数，反之为负数
     *
     * @return
     */
    public static int getDiffMonth(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        int diffyaer = calendar1.get(Calendar.YEAR) - calendar2.get(Calendar.YEAR);
        int diffmonth = calendar1.get(Calendar.MONTH) - calendar2.get(Calendar.MONTH);
        return diffyaer * 12 + diffmonth;
    }


    public static int getMonth(Date start, Date end) {
        if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(Calendar.DATE, 1);
        int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
        if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month + 1;
        } else if ((startCalendar.get(Calendar.DATE) != 1) && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month;
        } else if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) != 1)) {
            return year * 12 + month;
        } else {
            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
        }
    }

    public static long getDateDeff(Date startDate, Date endDate) {
        long intValue = 0;
        try {
            String df = new String("yyyy-MM-dd");
            startDate = DateUtil.strToDate(DateUtil.dataToStr(startDate, df), df);
            endDate = DateUtil.strToDate(DateUtil.dataToStr(endDate, df), df);
            intValue = (startDate.getTime() - endDate.getTime()) / 86400000;
        } catch (Exception e) {
            System.err.println("getDateDeff error");
        }
        return intValue;
    }

    public static String gethhssmmmssSSS(Date date) {
        String result = DateUtil.dataToStr(date, "yyyy-MM-dd:HH:mm:ss:SSS").replace(":", "");
        return result.substring(10, result.length());
    }


    /**
     * 通过生日获取年龄
     *
     * @return
     */
    public static int getAgeByBirth(Date birthday) {
        //获取当前系统时间
        Calendar cal = Calendar.getInstance();
        if (null == birthday) {
            return 0;
        }
        //如果出生日期大于当前时间，则抛出异常
        if (cal.before(birthday)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        //取出系统当前时间的年、月、日部分
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        //将日期设置为出生日期
        cal.setTime(birthday);
        //取出出生日期的年、月、日部分  
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        //当前年份与出生年份相减，初步计算年龄
        int age = yearNow - yearBirth;
        //当前月份与出生日期的月份相比，如果月份小于出生月份，则年龄上减1，表示不满多少周岁
        if (monthNow <= monthBirth) {
            //如果月份相等，在比较日期，如果当前日，小于出生日，也减1，表示不满多少周岁
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age;
    }


    /**
     * 将时间转换为时间戳
     */
    public static String dateToStamp(Date date) throws ParseException {
        long ts = date.getTime();
        String res = String.valueOf(ts);
        return res;
    }

    /**
     * 将时间戳转换为时间
     *
     * @throws ParseException
     * @throws NumberFormatException
     */
    public static Date stampToDate(String stamp) throws NumberFormatException, ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long time = Long.parseLong(stamp);
        String d = format.format(time);
        Date date = format.parse(d);
        return date;
    }
    public static String  stampToString(String stamp) throws NumberFormatException, ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long time = Long.parseLong(stamp);
        String d = format.format(time);
        return d;
    }
    /**
     * 当前时间+30秒
     */
    public static Date getNewTimeAfterAdd() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        calendar.add(Calendar.SECOND, 30);
        return calendar.getTime();
    }

    /**
     * 当前时间+30秒
     */
    public static Date getNewTime(Date date, int deffHour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.HOUR_OF_DAY, deffHour);
        return calendar.getTime();
    }

    /**
     * 获取当前时间到24时的秒数
     */
    public static long getSeconds() {
        long seconds24 = DateUtil.strToDate("24:00:00", "HH:mm:ss").getTime() / 1000;
        long nowSeconds = DateUtil.formatDate(new Date(), "HH:mm:ss").getTime() / 1000;
        return seconds24 - nowSeconds;
    }

    /**
     * 获取今天是星期几 1是星期一 7是星期天。
     * @return
     */
    public static String getTodayWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w == 0) {
            w = 7;
        }
        return w + "";
    }

//    public static void main(String[] args) throws ParseException {
//        int i = 2;
//        System.out.println(getNewTime(new Date(), -1));
//        System.out.println(stampToDate("14134132"));
//    }
}
