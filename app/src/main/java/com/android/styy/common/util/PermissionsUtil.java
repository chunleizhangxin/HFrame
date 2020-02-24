package com.android.styy.common.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import com.orhanobut.logger.Logger;

import com.android.styy.LaunchApp;
import com.android.styy.R;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class PermissionsUtil {
    private static final String TAG = "PermissionsUtil";
    private static final String APP_PACKAGE = LaunchApp.Instance.getPackageName();
    public static final String[] NECESSARY_PERMISSION = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    public static final String[] LOCATION_PERMISSION = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };


    public static boolean checkPermissions(Context context, String... permissions){
        if (context == null || permissions == null || permissions.length == 0) {
            return false;
        }
        for (String permission : permissions) {
            int hasWriteContactsPermission = context.checkSelfPermission(permission);//权限检查
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkLocationPermissions(Context context){
        return checkPermissions(context, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public static void checkDynamicPermissions(RxPermissions rxPermissions,
                                               final CheckPermissionsListener listener, String... permissions) {
        boolean permissionsEffective = (permissions != null) && (permissions.length > 0);
        if((rxPermissions == null) || !permissionsEffective){
            Logger.e(TAG, "checkPermissions params error.");
            return;
        }
        rxPermissions.request(permissions).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (listener != null) {
                    listener.checkPermissionsResult(aBoolean);
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public static AlertDialog createPermissionDialog(Activity activity,
                                                     String remindStr,
                                                     final PermissionDialogListener listener){
        if(activity == null || activity.isFinishing()){
            Logger.e(TAG, "createPermissionDialog activity error.");
            return null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(remindStr)
                .setCancelable(false)
                .setPositiveButton(R.string.permission_dialog_positive_btn,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (listener != null) {
                                    listener.positiveButtonClick();
                                }
                            }
                        })
                .setNegativeButton(R.string.permission_dialog_negative_btn,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (listener != null) {
                                    listener.negativeButtonClick();
                                }
                            }
                        });
        return builder.create();
    }

    public static Intent getSettingPermissionItent(){
        Intent intent = null;
        String name = Build.MANUFACTURER;
        switch (name) {
            case "HUAWEI":
                intent = getIntnetHuaWeiMainager();
                break;
//            case "vivo":
//                intent = getIntentVivoMainager();
//                break;
//            case "OPPO":
//                intent = getIntentOppoMainager();
//                break;
            case "Meizu":
                intent = getIntentMeozuManager();
                break;
            case "Xiaomi":
                intent = getIntentMinu8Manager();
                break;
            default:
                intent =getAppSettingManager();
                break;
        }
        return intent;
    }

    private static Intent getAppSettingManager() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        localIntent.setData(Uri.fromParts("package", APP_PACKAGE, null));
        return localIntent;
    }

    private static Intent getIntentSangXinMainager(){
        return null;
    }

    private static Intent getIntnetHuaWeiMainager(){
        Intent intent = new Intent(APP_PACKAGE);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
        intent.setComponent(comp);
        return intent;
    }

    private static Intent getIntentVivoMainager() {
        return null;
    }

    private static Intent getIntentOppoMainager() {
        return null;
    }

    private static Intent getIntentMeozuManager(){
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", APP_PACKAGE);
        return intent;
    }

    private static Intent getIntentMinu8Manager() {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter",
                "com.miui.permcenter.permissions.PermissionsEditorActivity");
        intent.putExtra("extra_pkgname", APP_PACKAGE);
        return intent;
    }

    private static Intent getIntentMinu5Manager() {
        Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        localIntent.setClassName("com.miui.securitycenter",
                "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        localIntent.putExtra("extra_pkgname", APP_PACKAGE);
        return localIntent;
    }

    public interface PermissionDialogListener{
        void positiveButtonClick();
        void negativeButtonClick();
    }

    public interface CheckPermissionsListener{
        void checkPermissionsResult(boolean result);
    }
}