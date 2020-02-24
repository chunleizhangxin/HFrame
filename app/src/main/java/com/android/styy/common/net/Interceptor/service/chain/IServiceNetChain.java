package com.android.styy.common.net.Interceptor.service.chain;

import com.android.styy.common.dao.TokenEntity;
import com.android.styy.common.exception.ServiceException;
import com.android.styy.common.net.impl.NetModel;

import okhttp3.Request;
import okhttp3.Response;

public interface IServiceNetChain {

    // 执行 业务逻辑
    Response executeBusiness(NetModel netModel, TokenEntity tokenEntity, Request oldReq,Response response) throws ServiceException;
}
