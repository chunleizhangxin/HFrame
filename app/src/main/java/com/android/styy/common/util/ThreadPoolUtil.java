package com.android.styy.common.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolUtil {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;


    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "ThreadPool #" + mCount.getAndIncrement());
        }
    };

    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(128);

    /**
     * CORE_POOL_SIZE 核心线程数
     * MAXIMUM_POOL_SIZE 最大线程数
     * KEEP_ALIVE  非核心线程数的超时时间
     * TimeUnit.SECONDS 时间的计量单位
     * sPoolWorkQueue  线程队列
     * sThreadFactory 生成线程的工厂
     */
    public static final Executor THREAD_POOL_EXECUTOR
            = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);

    public static ExecutorService fixedThreadPool;

    public static ExecutorService cachedThreadPool;

    public static ScheduledExecutorService scheduledThreadPool;

    public static ExecutorService singleThreadPool;

    //线程的种类
    public enum Type{
        defaulted,fixed,cached,scheduled,single
    }

    /**
     * 执行任务
     */
    public static void execute(Type type,int count,Runnable runnable){
        switch(type){
            case fixed:
                fixedThreadPool(count).execute(runnable);
                break;
            case cached:
                cachedThreadPool().execute(runnable);
            case single:
                singleThreadPool().execute(runnable);
                break;
            default :
                getDefaultPool().execute(runnable);
                break;
        }
    }

    /**
     *
     * @param count
     * @param delaytime
     * @param ratime  小于0 表示不用重复执行
     * @param runnable
     */
    public static void execute(int count,int delaytime,int ratime,Runnable runnable){
        ScheduledExecutorService service=scheduledThreadPool(count);
        if(ratime<=0){
            service.schedule(runnable,delaytime, TimeUnit.MILLISECONDS);
        }else{
            service.scheduleAtFixedRate(runnable, delaytime, ratime, TimeUnit.MILLISECONDS);
        }
    }


    /**
     * 获取默认的线程池
     * @return
     */
    public static Executor getDefaultPool(){
        return THREAD_POOL_EXECUTOR;
    }

    /**
     * 只有指定的核心线程数,所以,特定是反映速度非常快
     * @param nThreads
     * @return
     */
    public static ExecutorService fixedThreadPool(int nThreads){
        if(fixedThreadPool==null){
            synchronized (ThreadPoolUtil.class) {
                if(fixedThreadPool==null){
                    fixedThreadPool=Executors.newFixedThreadPool(nThreads);
                }
            }
        }
        return fixedThreadPool;
    }

    public static ExecutorService fixedThreadPool(){
        if(fixedThreadPool==null){
            synchronized (ThreadPoolUtil.class) {
                if(fixedThreadPool==null){
                    fixedThreadPool=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                }
            }
        }
        return fixedThreadPool;
    }

    /**
     *  适合执行大量的,耗时较少的任务
     */
    public static ExecutorService cachedThreadPool(){
        if(cachedThreadPool==null){
            synchronized (ThreadPoolUtil.class) {
                if(cachedThreadPool==null){
                    cachedThreadPool=Executors.newCachedThreadPool();
                }
            }
        }
        return cachedThreadPool;
    }

    /**
     * 执行定时任务与具有固定周期的重复任务
     * @param nThreadCount
     * @return
     */
    public static ScheduledExecutorService scheduledThreadPool(int nThreadCount){
        if(scheduledThreadPool==null){
            synchronized (ThreadPoolUtil.class) {
                if(scheduledThreadPool==null){
                    scheduledThreadPool=Executors.newScheduledThreadPool(nThreadCount);
                }
            }
        }
        return scheduledThreadPool;
    }

    /**
     * 保证所有的任务在同一个线程中顺序执行
     * @return
     */
    public static ExecutorService singleThreadPool(){
        if(singleThreadPool==null){
            synchronized (ThreadPoolUtil.class) {
                if(singleThreadPool==null){
                    singleThreadPool= Executors.newSingleThreadExecutor();
                }
            }
        }
        return singleThreadPool;
    }
}
