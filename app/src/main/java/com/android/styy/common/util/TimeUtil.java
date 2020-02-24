package com.android.styy.common.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转换工具
 */
public class TimeUtil {

    private TimeUtil(){}

    public static String getHourMTime(long tempTime){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(tempTime);
    }

    public static String getMonthDayTime(long tempTime){
        SimpleDateFormat sdf = new SimpleDateFormat("MM"+"月"+"dd" + "日");
        return sdf.format(tempTime);
    }

    public static String getYearMonthDayTime(long tempTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        return sdf.format(tempTime);
    }

    public static String getYearMonthDayTime(String tempTime){
        if(TextUtils.isEmpty(tempTime)){
            return tempTime;
        }
        try{
            long time = Long.parseLong(tempTime);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            return sdf.format(time);
        }catch (NumberFormatException ex){
            return "";
        }

    }

    public static String getStrTime(String tempTime, String pattern){
        try{
            long time = Long.parseLong(tempTime);
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(time);
        }catch (NumberFormatException ex){

        }
        return null;
    }
}