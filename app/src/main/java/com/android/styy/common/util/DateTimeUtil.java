package com.android.styy.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class DateTimeUtil {

    // 秒
    public static final long SECOND = 1000;
    // 分
    public static final long MINUTE = SECOND * 60;
    // 时
    public static final long HOUR = MINUTE * 60;
    // 天
    public static final long DAY = HOUR * 24;

    public static String MM_DD = "MM-dd";
    public static String MM$M$DD$D$ = "MM月dd日";
    public static String HH_MM_SS = "HH:mm:ss";
    public static String YYYY_MM_DD = "yyyy-MM-dd";
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static String YYYY$MM$DD_HH_MM = "yyyy.MM.dd HH:mm";

    public static String long2String(long date,String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date(date));
    }

    public static Date string2Date(String date,String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date formatDate(Date date,String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return string2Date(sdf.format(date),pattern);
    }


    public static long getTodayCurrent(){
        long current = System.currentTimeMillis();
        return getFullDateTime(current);
    }

    /**
     * 获取时间戳的整数毫秒
     * @param dateTime
     * @return
     */
    public static long getFullDateTime(long dateTime){
        return dateTime / DAY * DAY  - TimeZone.getDefault().getRawOffset();
    }

    /****
     * 单位 PoiEntity
     * @return
     */
    public static String secondToTime(long second){
        second = second % 86400;            //剩余秒数
        long hours = second / 3600;            //转换小时
        second = second % 3600;                //剩余秒数
        long minutes = second /60;            //转换分钟
        second = second % 60;                //剩余秒数
        return hours + "时" + minutes + "分" + second + "秒";
    }

    /**
     * 判断时间是否在某个时间段
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否为今年
     * @return
     */
    public static boolean checkIsCurrentYear(long currentTime){
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(currentTime);

        int year = date.get(Calendar.YEAR);

        Calendar currentDate = Calendar.getInstance();
        currentDate.setTimeInMillis(System.currentTimeMillis());

        int currentYear = currentDate.get(Calendar.YEAR);

        return year == currentYear;
    }

}
