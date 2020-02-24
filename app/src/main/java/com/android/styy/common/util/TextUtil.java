package com.android.styy.common.util;

import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;

//import com.baidu.mapapi.model.LatLng;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.regex.Pattern;

public class TextUtil {

    /**
     * 是否为空
     *
     * @param value
     * @return
     */
    public static boolean isEmpty(String value) {
        return TextUtils.isEmpty(value);
    }

    public static int getScaleValue(float ft, int scale) {
        BigDecimal bd = new BigDecimal((double) ft);
        bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return bd.intValue();
    }

    public static String fomat(Object obj){
        return fomat(obj,"#.##");
    }
    public static String fomat(Object obj,String pattern){
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(obj);
    }

    public static float toFloat(String value, float def) {
        if (value == null)
            return def;
        try {
            return Float.valueOf(value);
        } catch (Exception e) {
        }
        return def;
    }

    public static int toInt(String value, int def) {
        if (value == null)
            return def;
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
        }
        return def;
    }

    /**
     * 检查密码
     *
     * @param pwd
     * @return
     */
    public static boolean isCheckPwd(String pwd) {
        String pattern = "^(?![0-9]*$)[a-zA-Z0-9]{6,20}$";
        return pwd.matches(pattern);
    }

    /**
     * 检查验证码
     *
     * @return
     */
    public static boolean isCheckACode(String acode) {
        if (acode == null)
            return false;
        return acode.matches("[0-9]{6}");
    }

    /****
     * 检查手机号
     *
     * 正确返回 true  ， 错误返回 false
     */
    public static boolean isCheckPhone(String mobile){
        String pattern = "^1[0-9]{10}$";
        return mobile.matches(pattern);
    }

    /***
     * 删除线
     */
    public static SpannableString strickout(String data){
        SpannableString sp = new SpannableString(data);
        sp.setSpan(new StrikethroughSpan(), 0, data.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sp;
    }

    /**
     * Spanned 设置颜色
     */
    public static Spanned spanTxtColor(String contentStart, String data, String color, String contentEnd){
        return Html.fromHtml(
                contentStart + "<font color='"+color+"'>"+data+"</font>"+contentEnd);
    }

//    public static String locationTxt(LatLng start, LatLng end){
//        double distance = AreaUtil.getDistance(start,end);
//
//        String distanceString;
//
//        if(distance >= 1000){
//            distanceString = fomat((distance / 1000)) + "km";
//        }else{
//            distanceString = fomat(distance,"#") + "m";
//        }
//        return distanceString;
//    }


    public static String Iterator2String(Iterator<String> data, String rgx){
        StringBuffer buffer = new StringBuffer();
        while (data.hasNext()){
            buffer.append(data.next())
                    .append(rgx);
        }
        return buffer.toString();
    }

    /***
     * 是否为 URL
     */
    public static boolean checkUrl(String str){
        str = str.toLowerCase();
        String regex = "^((https|http|ftp|rtsp|mms)?://)"
                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
                + "|" // 允许IP和DOMAIN（域名）
                + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
                + "[a-z]{2,6})" // first level domain- .com or .museum
                + "(:[0-9]{1,4})?" // 端口- :80
                + "((/?)|" // a slash isn't required if there is no file name
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";

        Pattern pattern = Pattern
                .compile(regex);


        return pattern.matcher(str).matches();
    }

    public static boolean checkEmail(String str){
        Pattern pattern = Pattern
                .compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        return pattern.matcher(str).matches();
    }
}
