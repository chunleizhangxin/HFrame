package com.android.styy.common.exception;

import java.io.IOException;

public class ServiceException extends IOException {

    private int code;

    public ServiceException(int code,String message){
        super(message);
        this.code = code;
    }

    public ServiceException(String message) {
        super(message);
    }

    public int getCode() {
        return code;
    }
}
