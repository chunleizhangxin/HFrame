package com.android.styy.common.manager;

import android.util.Log;

import com.orhanobut.logger.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorManager {

    String TAG = getClass().getSimpleName();

    static ExecutorManager instance;

    static ExecutorService executorService;

    private ExecutorManager(){}

    public static ExecutorManager newInstance(){
//        if(null == instance){
//            instance = new ExecutorManager();
//        }
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        return new ExecutorManager();
    }

    public void execute(Runnable runnable){
        executorService.execute(runnable);
    }

    public void shutdownNow(){
        try {
            executorService.shutdownNow();
        }catch (Exception e){
            Log.e(TAG,String.valueOf(e));
        }
    }
}
