package com.xuxiang.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created  on 2018/6/27 0027
 *
 * @describe 时间相关工具类
 */
public class TimeUtils {
    private static String mYear;
    private static String mMonth;
    private static String mDay;
    private static String mWay;

    /**
     * 获取当前日期
     *
     * @return
     */
    public static Date getNowDate() {
        return Calendar.getInstance().getTime();
    }

    public static String longToStr(long time) {
        Date date = new Date(time);
        return dateToStr(date);
    }

    /**
     * date  to  string
     *
     * @param dateDate
     * @return
     */
    public static String dateToStr(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    public static String dateToStr2(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    public static String dateToStr3(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * date  to  string
     *
     * @param dateDate
     * @return
     */
    public static String dateToStr1(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 工具-日期转换
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrM(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    public static String dateToStrHMS(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    public static String dateToStrs(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    public static String dateToyear(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * string   to  date
     */
    public static Date strToDate(String string) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 传入年月日获取时间戳
     *
     * @param year
     * @param month
     * @param dayOfMonth
     * @return
     */
    public static Date getDateByNyr(int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, dayOfMonth);
        return cal.getTime();
    }

    /**
     * 获取前一天
     *
     * @param date
     * @return
     */
    public static Date getBeforeDay(Date date) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        int day = cl.get(Calendar.DATE);
        cl.set(Calendar.DATE, day - 1);
        return cl.getTime();
    }

    /**
     * 获取后一天
     *
     * @param date
     * @return
     */
    public static Date getTomorrowDay(Date date) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        int day = cl.get(Calendar.DATE);
        cl.set(Calendar.DATE, day + 1);
        return cl.getTime();
    }

    /**
     * 获取当前年月日字符串
     *
     * @return
     */
    public static String getNowNyr() {
        return dateToStr(new Date());
    }

    /**
     * 获取当前星期几
     *
     * @return
     */
    public static String getWeekDay() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        return "星期" + mWay;
    }

    /**
     * 判断两个日期是否为同一个页面
     *
     * @param dayOne
     * @param dayTwo
     * @return
     */
    public static boolean isOnemMonth(String dayOne, String dayTwo) {
        Date dateOne = strToDateTwo(dayOne);
        Date dateTwo = strToDateTwo(dayTwo);
        Calendar c1 = Calendar.getInstance();
        c1.setTime(dateOne);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(dateTwo);
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH);
    }

    public static long getRang(String startDay, String endDay) {
        Date start = stringToDate(startDay, "yyyy-MM-dd HH:mm:ss");
        Date end = stringToDate(endDay, "yyyy-MM-dd HH:mm:ss");
        return end.getTime() - start.getTime();
    }

    public static String getRangStr(long start, long end) {
        long diff = end - start;//这样得到的差值是微秒级别
        long minutes = diff / (1000 * 60);
        long second = (diff - minutes * (1000 * 60)) / 1000;
        String result = "";
        if (minutes != 0 && second != 0) {
            result = minutes + "分钟" + second + "秒";
        }else if(minutes != 0 && second == 0) {
            result = minutes + "分钟";
        }else if (minutes == 0 && second != 0) {
            result = second + "秒";
        }
        return result;
    }

    public static String getRangMinuteStr(long start, long end) {
        long diff = end - start;//这样得到的差值是微秒级别
        long minutes = diff / (1000 * 60);
        String result = minutes + "分钟";
        return result;
    }



    public static String getRangStr(int diff) {
        long minutes = diff / 60;
        long second = diff - minutes * 60;
        String result = "";
        if (minutes != 0 && second != 0) {
            result = minutes + "分钟" + second + "秒";
        }else if(minutes != 0 && second == 0) {
            result = minutes + "分钟";
        }else if (minutes == 0 && second != 0) {
            result = second + "秒";
        }
        return result;
    }

    public static Date strToDateTwo(String string) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 截取年月
     *
     * @return
     */
    public static String getMonth(String str) {
        Date date = strToDateTwo(str);
        SimpleDateFormat formatter = new SimpleDateFormat("MM月");
        String dateString = formatter.format(date);
        Date nowDate = new Date();
        String nowM = formatter.format(nowDate);
        if (dateString.equals(nowM)) {
            return "本月";
        }
        return dateString;
    }

    /**
     * 获取年月字符串
     *
     * @return
     */
    public static String getNy() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        return formatter.format(calendar.getTime());
    }

    public static String getNyStr() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月");
        return formatter.format(calendar.getTime());
    }

    /**
     * 获取年月字符串
     *
     * @return
     */
    public static String getNy(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        return formatter.format(date);
    }

    public static String getNyStr(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月");
        return formatter.format(date);
    }


    /**
     * 获取年月日字符串
     *
     * @return
     */
    public static String getNyr(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    /**
     * 截取年
     *
     * @param string
     * @return
     */
    public static int[] getStrYyr(String string) {
        int[] arr = new int[3];
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        arr[0] = calendar.get(Calendar.YEAR);
        arr[1] = calendar.get(Calendar.MONTH) + 1;
        arr[2] = calendar.get(Calendar.DAY_OF_MONTH);

        return arr;
    }

    public static String getStrByNyr(int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, dayOfMonth);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.format(cal.getTime());
    }

    public static String getStrByNy(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");

        return formatter.format(cal.getTime());
    }


    /**
     * 更根据当前月获取前一个月
     *
     * @param nowMonth
     * @return
     */
    public static String getBeforMonthint(String nowMonth) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = formatter.parse(nowMonth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);

        calendar.set(Calendar.MONTH, month - 1);
        return formatter.format(calendar.getTime());
    }

    /**
     * 更根据当前月获取后一个月
     *
     * @param nowMonth
     * @return
     */
    public static String getNextMonthint(String nowMonth) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = formatter.parse(nowMonth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);

        calendar.set(Calendar.MONTH, month + 1);
        return formatter.format(calendar.getTime());
    }

    /**
     * 获取两个日期之间的间隔天数
     *
     * @return
     */
    public static int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);
        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);
        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * 获取两个日期之间的间隔天数
     *
     * @return
     */
    public static String getGapCount1(String startDate, String endDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = df.parse(endDate);
            d2 = df.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
        String result = "";
//        if (minutes == 0 && hours != 0) {
//            result = days + "天" + hours + "小时";
//        } else if (hours == 0 && minutes == 0) {
//            result = days + "天";
//        } else {
//            result = days + "天" + hours + "小时" + minutes + "分";
//        }
        if (minutes == 0 && hours != 0) {
            result = days + "天" + hours + "小时";
        } else if (hours == 0 && minutes == 0) {
            result = days + "天";
        } else {
            result = days + "天" + hours + "小时";
        }
        return result;
    }



    /**
     * 截取年月日
     *
     * @param str
     * @return
     */
    public static String getNyr(String str) {
        Date date = strToDateTwo(str);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 截取年月日
     *
     * @param str
     * @return
     */
    public static String getNyrStr(String str) {
        Date date = strToDateTwo(str);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        String dateString = formatter.format(date);
        return dateString;
    }


    /**
     * 截取时分
     *
     * @param str
     * @return
     */
    public static String getHm(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = formatter.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
        String dateString = formatter2.format(date);
        return dateString;
    }

    public static String getYear() {
        Date nowDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String nowY = formatter.format(nowDate);
        return nowY;
    }

    public static String getYearStr() {
        Date nowDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年");
        String nowY = formatter.format(nowDate);
        return nowY;
    }

    public static String getYear(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        return formatter.format(date);
    }

    public static String getYearStr(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年");
        return formatter.format(date);
    }

    /**
     * 截取日字符串
     *
     * @return
     */
    public static String getDay() {
        Date nowDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        String nowD = formatter.format(nowDate);
        return nowD;
    }

    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }


    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            date = null;
        }
        return date;
    }

    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    public static long stringToLong(String strTime, String formatType) {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

}
