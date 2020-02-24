package com.android.styy.common.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ClipData;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import com.android.styy.LaunchApp;
import com.android.styy.R;

public class DeviceUtils {

    /**
     * dp 转化为 px
     *
     * @param dpValue dpValue
     * @return int
     */
    public static int dp2px(float dpValue) {
        final float scale = LaunchApp.Instance.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px 转化为 dp
     *
     * @param pxValue pxValue
     * @return
     */
    public static int px2dp(float pxValue) {
        final float scale = LaunchApp.Instance.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2sp( float pxValue) {
        final float fontScale = LaunchApp.Instance.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(float spValue) {
        final float fontScale = LaunchApp.Instance.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取设备宽度（px）
     *
     * @return int
     */
    public static int deviceWidth() {
        return LaunchApp.Instance.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取设备高度（px）
     *
     * @return
     */
    public static int deviceHeight() {
        return LaunchApp.Instance.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 状态栏高度
     * @return
     */
    public static int getStatusBarHeight() {
        // 获得状态栏高度
        int resourceId = LaunchApp.Instance.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return LaunchApp.Instance.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * SD卡判断
     *
     * @return boolean
     */
    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 是否有网
     *
     * @return boolean
     */
    public static boolean isNetworkConnected() {
        if (LaunchApp.Instance != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) LaunchApp.Instance
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    /****
     * 获取颜色
     * */
    public static final int getColor(int id) {
        return ContextCompat.getColor(LaunchApp.Instance,id);
    }
    /**
     * 返回版本名字
     * 对应build.gradle中的versionName
     *
     * @return String
     */
    public static String getVersionName() {
        String versionName = "";
        try {
            PackageManager packageManager = LaunchApp.Instance.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(LaunchApp.Instance.getPackageName(), 0);
            versionName = packInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }


    /**
     * 获取设备的唯一标识，deviceId
     * imei
     * @return String
     */
    public static String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) LaunchApp.Instance.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            String deviceId = tm.getDeviceId();
            if (deviceId == null) {
                return "-";
            } else {
                return deviceId;
            }
        } catch (Exception ex) {
            return "-";
        }
    }

    /***
     * 获取包名
     * @return
     */
    public static String getPackageName(){
        return LaunchApp.Instance.getPackageName();
    }
    /**
     * 获取手机品牌
     *
     * @return String
     */
    public static String getPhoneBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机型号
     *
     * @return String
     */
    public static String getPhoneModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机Android API等级（22、23 ...）
     *
     * @return int
     */
    public static int getBuildLevel() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机Android 版本（4.4、5.0、5.1 ...）
     *
     * @return String
     */
    public static String getBuildVersion() {
        return Build.VERSION.RELEASE;
    }

    /***
     * 收起键盘
     */
    public static void hideInputKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager)activity.
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(),0);
        }
    }

    /***
     * 弹出键盘
     */
    public static void showInputKeyBoard(Context mContext) {
        InputMethodManager imm = (InputMethodManager)mContext.
                getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    /**
     * 获取当前App进程的id
     *
     * @return int
     */
    public static int getAppProcessId() {
        return android.os.Process.myPid();
    }

    public static boolean shouldInit() {
        ActivityManager am = ((ActivityManager) LaunchApp.Instance.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = getAppProcessId();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 获取当前App进程的Name
     *
     * @param processId processId
     * @return String
     */
    public static String getAppProcessName(int processId) {
        String processName = null;
        ActivityManager am = (ActivityManager) LaunchApp.Instance.getSystemService(Context.ACTIVITY_SERVICE);
        // 获取所有运行App的进程集合
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = LaunchApp.Instance.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == processId) {
                    CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));

                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                Log.e(DeviceUtils.class.getName(), e.getMessage(), e);
            }
        }
        return processName;
    }

    /***
     * 读取 Assets 下的文件内容
     * @param fileName
     * @return
     */
    public static String getStringFromAssets(String fileName){
        try {
            InputStream inputStream = LaunchApp.Instance.getResources().getAssets().open(fileName);
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            String str = result.toString(StandardCharsets.UTF_8.name());
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 创建App文件夹
     *
     * @param appName     appName
     * @param application application
     * @return String
     */
    public static String createAPPFolder(String appName, Application application) {
        return createAPPFolder(appName, application, null);
    }

    /**
     * 创建App文件夹
     *
     * @param appName     appName
     * @param application application
     * @param folderName  folderName
     * @return String
     */
    public static String createAPPFolder(String appName, Application application, String folderName) {
        File root = Environment.getExternalStorageDirectory();
        File folder;
        /**
         * 如果存在SD卡
         */
        if (DeviceUtils.isSDCardAvailable() && root != null) {
            folder = new File(root, appName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        } else {
            /**
             * 不存在SD卡，就放到缓存文件夹内
             */
            root = application.getCacheDir();
            folder = new File(root, appName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        }
        if (folderName != null) {
            folder = new File(folder, folderName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        }
        return folder.getAbsolutePath();
    }

    //删除文件夹和文件夹里面的文件
    public static void deleteDir(String path) {
        File dir = new File( path);
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete(); // 删除所有文件
            }else if (file.isDirectory()) {
                deleteDir(dir.getAbsolutePath()); // 递规的方式删除文件夹
            }
        }
        dir.delete();// 删除目录本身
    }


    /**
     * 通过Uri找到File
     *
     * @param context context
     * @param uri     uri
     * @return File
     */
    public static File uri2File(Activity context, Uri uri) {
        File file;
        String[] project = {MediaStore.Images.Media.DATA};
        Cursor actualImageCursor = context.getContentResolver().query(uri, project, null, null, null);
        if (actualImageCursor != null) {
            int actual_image_column_index = actualImageCursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualImageCursor.moveToFirst();
            String img_path = actualImageCursor
                    .getString(actual_image_column_index);
            file = new File(img_path);
        } else {
            file = new File(uri.getPath());
        }
        if (actualImageCursor != null) actualImageCursor.close();
        return file;
    }

    /**
     * 获取AndroidManifest.xml里 <meta-data>的值
     *
     * @param name    name
     * @return String
     */
    public static String getMetaData(String name) {
        String value = null;
        try {
            ApplicationInfo appInfo = LaunchApp.Instance.getPackageManager().getApplicationInfo(
                    LaunchApp.Instance.getPackageName(), PackageManager.GET_META_DATA);
            value = appInfo.metaData.getString(name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 复制到剪贴板
     *
     * @param content content
     */
    public static void copy2Clipboard(String content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) LaunchApp.Instance.getSystemService(
                    Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(LaunchApp.Instance.getString(R.string.app_name), content);
            clipboardManager.setPrimaryClip(clipData);
        } else {
            android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) LaunchApp.Instance.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setText(content);
        }
    }
    public static String getCachePath() {
        File cacheDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            cacheDir = LaunchApp.Instance.getExternalCacheDir();
        else
            cacheDir = LaunchApp.Instance.getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();
        return cacheDir.getAbsolutePath();
    }

    /**
     * 获取IP地址
     * @return
     */
    public static String getIpAddress(){
        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return hostIp;
    }

    /**
     * 获取文件夹大小
     * @param file
     * @return
     */
    public static String getFileSize(File file){
        String size = "0KB";
        if(file.exists() && file.isFile()){
            long fileS = file.length();
            DecimalFormat df = new DecimalFormat("#.00");
            if (fileS < 1024) {
                size = df.format((double) fileS) + "BT";
            } else if (fileS < 1048576) {
                size = df.format((double) fileS / 1024) + "KB";
            } else if (fileS < 1073741824) {
                size = df.format((double) fileS / 1048576) + "MB";
            } else {
                size = df.format((double) fileS / 1073741824) +"GB";
            }
        }else if(file.exists() && file.isDirectory()){
            size = "0KB";
        }else{
            size = "0BT";
        }
        return size;
    }



    /**
     * 判断服务是否开启
     *
     * @return
     */
    public static boolean isServiceRunning(String ServiceName) {
        if (TextUtil.isEmpty(ServiceName)) {
            return false;
        }
        ActivityManager myManager = (ActivityManager) LaunchApp.Instance
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals(ServiceName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * qq 是否安装
     * @return
     */
    public static boolean qqIsInstall(){
        return appIsInstall("com.tencent.qqlite") || appIsInstall("com.tencent.mobileqq");
    }

    /**
     * qq 是否安装
     * @return
     */
    public static boolean wechatIsInstall(){
        return appIsInstall("com.tencent.mm") ;
    }

    /**
     * 新浪 是否安装
     * @return
     */
    public static boolean sinaIsInstall(){
        return appIsInstall("com.sina.weibo") ;
    }

    /***
     * 判断app 是否安装
     * @param packageName
     * @return
     */
    public static boolean appIsInstall(String packageName){
        final PackageManager packageManager = LaunchApp.Instance.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

}
