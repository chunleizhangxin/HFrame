package com.android.styy.common.rx;

import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import com.android.styy.common.config.AppConfig;
import com.android.styy.common.exception.ServiceException;
import com.android.styy.common.net.impl.NetModel;

public class RxUtils {

    private static Observable.Transformer ioToMainThreadSchedulerTransformer;

    static {
        ioToMainThreadSchedulerTransformer = createIOToMainThreadScheduler();
    }

    private static <T> Observable.Transformer<T,T> createIOToMainThreadScheduler(){
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T>Observable<T> converterObjMode(final Observable<NetModel<T>> observable){
        return observable.flatMap(new Func1<NetModel<T>, Observable<T>>() {
            @Override
            public Observable<T> call(final NetModel<T> tNetModel) {
                return Observable.create(new Observable.OnSubscribe<T>(){
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        if(null == tNetModel){
                            subscriber.onError(new ServiceException(AppConfig.NET_ERROR));
                        }else {
                            if(!(AppConfig.NET_SUCCESS_CODE == tNetModel.getStatus())){
                                subscriber.onError(new ServiceException(tNetModel.getMessage()));
                            }else{
                                subscriber.onNext(tNetModel.getData());
                                subscriber.onCompleted();
                            }
                        }
                    }
                });
            }
        }).compose(ioToMainThreadSchedulerTransformer);
    }

    public static <T>Observable<List<T>> converterListMode(final Observable<NetModel<T>> observable){
        return observable.flatMap(new Func1<NetModel<T>, Observable<List<T>>>() {
            @Override
            public Observable<List<T>> call(final NetModel<T> tNetModel) {
                return Observable.create(new Observable.OnSubscribe<List<T>>() {
                    @Override
                    public void call(Subscriber<? super List<T>> subscriber) {
                        if(null == tNetModel){
                            subscriber.onError(new ServiceException(AppConfig.NET_ERROR));
                        }else {
                            if(!(AppConfig.NET_SUCCESS_CODE == tNetModel.getStatus())){
                                subscriber.onError(new ServiceException(tNetModel.getMessage()));
                            }else{
                                subscriber.onNext(tNetModel.getList());
                                subscriber.onCompleted();
                            }
                        }
                    }
                });
            }
        }).compose(ioToMainThreadSchedulerTransformer);
    }

    public static <T>Observable<NetModel<T>> converterAllMode(final Observable<NetModel<T>> observable){
        return observable.flatMap(new Func1<NetModel<T>, Observable<NetModel<T>>>() {
            @Override
            public Observable<NetModel<T>> call(final NetModel<T> tNetModel) {
                return Observable.create(new Observable.OnSubscribe<NetModel<T>>() {
                    @Override
                    public void call(Subscriber<? super NetModel<T>> subscriber) {
                        if(null == tNetModel){
                            subscriber.onError(new ServiceException(AppConfig.NET_ERROR));
                        }else {
                            if(!(AppConfig.NET_SUCCESS_CODE == tNetModel.getStatus())){
                                subscriber.onError(new ServiceException(tNetModel.getMessage()));
                            }else{
                                subscriber.onNext(tNetModel);
                                subscriber.onCompleted();
                            }
                        }
                    }
                });
            }
        }).compose(ioToMainThreadSchedulerTransformer);
    }

    public static <T>Observable<NetModel<T>> converterMode(final Observable<NetModel<T>> observable){
        return observable.compose(ioToMainThreadSchedulerTransformer);
    }


    public static <T> Observable.Transformer<T, T> applyIOToMainThreadSchedulers() {
        return ioToMainThreadSchedulerTransformer;
    }

    public static Observable<Integer> countdown(int time) {
        if (time < 0) time = 0;

        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(new Func1<Long, Integer>() {
                    @Override
                    public Integer call(Long increaseTime) {
                        return countTime - increaseTime.intValue();
                    }
                })
                .take(countTime + 1)
                .compose(ioToMainThreadSchedulerTransformer);

    }
}
