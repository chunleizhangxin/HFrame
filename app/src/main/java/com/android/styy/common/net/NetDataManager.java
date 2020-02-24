package com.android.styy.common.net;

import com.android.styy.common.net.impl.NetModel;
import com.android.styy.entity.UserInfoEntity;
import com.android.styy.common.config.AppInterface;
import com.android.styy.common.net.service.UserService;
import com.android.styy.common.rx.RxUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import rx.Observable;

public class NetDataManager {

    static NetDataManager dataManager;

    UserService mUserService;

    static List<String> tokenFilterUrls = new LinkedList<>();

    public synchronized static NetDataManager getInstance() {
        if (dataManager == null) {
            dataManager = new NetDataManager();
        }
        return dataManager;
    }

    public static List<String> tokenFilterUrls() {
        return tokenFilterUrls;
    }

    private NetDataManager() {

        NetWorkManager mNetWorkManager = NetWorkManager.getInstance();

        mUserService = mNetWorkManager.build(UserService.class, AppInterface.BASE_URL_USER);

        Class<AppInterface> clazz = AppInterface.class;
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(TokenFilter.class)) {
                try {
                    String value = (String) field.get(clazz);
                    tokenFilterUrls.add(value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取盐值
     *
     * @return
     */
    public Observable<String> get_pwd_salt(String account) {
        return RxUtils.converterObjMode(mUserService.get_pwd_salt(account));
    }

    /***
     * 通过账户名密码登录
     */
    public Observable<NetModel<UserInfoEntity>> post_login_by_password(String userName, String password, String userType) {
        Map<String, String> map = new HashMap<>();
        map.put("username", userName);
        map.put("password", password);
        map.put("userType", userType + "");
        return RxUtils.converterAllMode(mUserService.post_login_by_password(map));
    }
}
