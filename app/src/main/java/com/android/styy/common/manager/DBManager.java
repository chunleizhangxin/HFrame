package com.android.styy.common.manager;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.android.styy.common.dao.DaoMaster;
import com.android.styy.common.dao.DaoSession;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private DaoMaster.DevOpenHelper mHelper;
    private DaoMaster mDaoMaster;
    private SQLiteDatabase mSQLiteDatabase;

    private DBManager(Builder builder){
        mHelper = new DaoMaster.DevOpenHelper(builder.application,"xiaoq-db",null);
        mSQLiteDatabase = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(mSQLiteDatabase);

        for(Interceptor interceptor : builder.mInterceptors){
            interceptor.interceptorDao(getDaoSession());
        }
    }

   public static Builder newBuilder(Application application){
       return new Builder(application);
   }

   public DaoMaster.DevOpenHelper getDevOpenHelper(){
       return mHelper;
   }

   public DaoMaster getDaoMaster(){
       return mDaoMaster;
   }

   public DaoSession getDaoSession(){
       return mDaoMaster.newSession();
   }

   public SQLiteDatabase getSQLiteDatabase(){
       return mSQLiteDatabase;
   }

   public static class Builder{

       private Application application;
       private List<Interceptor> mInterceptors;

       public Builder(Application application){
           this.application = application;
           this.mInterceptors = new ArrayList<>();
       }

       public Application getApplication() {
           return application;
       }

       public Builder addInterceptor(Interceptor mInterceptor){
           this.mInterceptors.add(mInterceptor);
           return this;
       }

       public DBManager build(){
           return new DBManager(this);
       }
   }

   public interface Interceptor{
       void interceptorDao(DaoSession mDaoSession);
   }
}
