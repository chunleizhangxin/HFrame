package com.android.styy.common.helper;

import com.android.styy.LaunchApp;
import com.android.styy.common.dao.UserEntity;
import com.android.styy.common.dao.UserEntityDao;

import java.util.List;

public class DBUserHelper {

    public static long onInsertUser(UserEntity entity){
        UserEntityDao userEntityDao = LaunchApp.mDBManager
                .getDaoSession()
                .getUserEntityDao();
        userEntityDao.deleteAll();
        return userEntityDao.insert(entity);
    }

    public static void deleteAll(){
        UserEntityDao userEntityDao = LaunchApp.mDBManager
                .getDaoSession()
                .getUserEntityDao();
        userEntityDao.deleteAll();
    }

    public static UserEntity onLoadUser(){
        if(isEmpty()){
            return null;
        }
        return LaunchApp.mDBManager
                .getDaoSession()
                .getUserEntityDao()
                .loadAll()
                .get(0);
    }

    public static UserEntity onLoadLoginUser(){

        if(isEmpty()){
            return null;
        }

        List<UserEntity> list = LaunchApp.mDBManager
                .getDaoSession()
                .getUserEntityDao()
                .queryBuilder()
                .where(UserEntityDao.Properties.Logout.eq(false))
                .list();
        if(list.isEmpty()){
            return null;
        }else {
            return list.get(0);
        }
    }

    public static String onLoadLoginUserId(){
        UserEntity entity = onLoadLoginUser();
        if(null != entity){
            return entity.getId();
        }
        return null;
    }

    public static boolean isEmpty(){
        return LaunchApp.mDBManager
                .getDaoSession()
                .getUserEntityDao()
                .queryBuilder()
                .count() == 0;
    }

    public static void updateUser(UserEntity entity){
        if(null == entity){
            return;
        }
        LaunchApp.mDBManager
                .getDaoSession()
                .getUserEntityDao()
                .update(entity);
    }
//    public static UserEntity updateHeaderLogo(String logo){
//        UserEntity entity = onLoadUser();
//        if(null == entity){
//            return null;
//        }
//        entity.setLogoUrl(logo);
//        updateUser(entity);
//        return entity;
//    }

    public static UserEntity updateNickName(String nickName){
        UserEntity entity = onLoadUser();
        if(null == entity){
            return null;
        }
        entity.setNickName(nickName);
        updateUser(entity);
        return entity;
    }

//    public static UserEntity updateMobile(String mobile){
//        UserEntity entity = onLoadUser();
//        if(null == entity){
//            return null;
//        }
//        entity.setMobile(mobile);
//        updateUser(entity);
//        return entity;
//    }

//    public static UserEntity updatePwd(String pwd,String passwd){
//        UserEntity entity = onLoadUser();
//        if(null == entity){
//            return null;
//        }
//        entity.setPasswd(passwd);
//        entity.setPwd(pwd);
//        updateUser(entity);
//        return entity;
//    }
}
