package com.android.styy.common.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.styy.LaunchApp;
import com.android.styy.common.config.AppConfig;

import java.util.Iterator;
import java.util.Map;

public class SharedPreferencesUtils {

    private static final String INFO_NAME = AppConfig.CACHENAME;

    private static SharedPreferences sp = LaunchApp.Instance.getSharedPreferences(INFO_NAME,
            Context.MODE_PRIVATE);
    private static SharedPreferences.Editor editor = sp.edit();

    public static void write(Map<String, String> params) {
        Iterator<String> iterator = params.keySet().iterator();
        for (; iterator.hasNext();) {
            String key = iterator.next();
            editor.putString(key, params.get(key));
        }
        editor.commit();
    }

    /**
     * 写文件
     *
     * @param key
     * @param value
     */
    public static void write(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 写文件
     *
     * @param key
     * @param value
     */
    public static void writeBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void writeInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public static void writeLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public static void writeDouble(String key, double value) {
        editor.putFloat(key, (float) value);
        editor.commit();
    }

    /**
     * 读取字符串
     *
     * @param key
     * @return
     */

    public static String read(String key) {
        return read(key, null);
    }
    /**
     * 读取字符串
     *
     * @param key
     * @return
     */

    public static String read(String key, String def) {
        return sp.getString(key, def);
    }

    /**
     * 读取布尔型数据，默认为false
     *
     * @param key
     * @return
     */
    public static boolean readBoolean(String key) {
        return readBoolean(key, false);
    }

    /**
     * 读取布尔型数据
     *
     * @param key
     * @param def
     * @return
     */
    public static boolean readBoolean(String key, boolean def) {
        return sp.getBoolean(key, def);
    }

    /**
     * 读取整形数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public static int readInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public static double readDouble(String key, float defValue) {
        return sp.getFloat(key, defValue);
    }

    /**
     * 读取长整形数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public static long readLong(String key, long defValue) {
        return sp.getLong(key, defValue);
    }

    public static void clear(String key){
        editor.remove(key);
        editor.commit();
    }

    public static void clearAll(){
        editor.clear();
        editor.commit();
    }

}
