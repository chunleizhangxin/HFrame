package com.android.styy.common.manager;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import com.android.styy.LaunchApp;

import java.util.ArrayList;
import java.util.List;


public class ActivitiesManager {

    public static final List<Activity> activityList = new ArrayList<>();

    public static void add(Activity activity){
        if(!activityList.contains(activity)){
            activityList.add(activity);
        }
    }

    public static void clear(){

        for(Activity activity : activityList){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }

        activityList.clear();
    }

    public static Activity getActivity(Class clazz){
        for(Activity activity : activityList){
            if(activity.getClass().getName().equals(clazz.getName())){
                return activity;
            }
        }
        return null;
    }

    public static Activity getActivity(int index){
        return activityList.get(index);
    }

    public static String getTopActivity(){
        ActivityManager manager = (ActivityManager)
                LaunchApp.Instance.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = manager .getRunningTasks(1);
        ActivityManager.RunningTaskInfo cinfo = runningTasks.get(0);
        ComponentName component = cinfo.topActivity;
        return component.getClassName();
    }
}
