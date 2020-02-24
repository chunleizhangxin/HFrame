package com.android.styy.base;

import java.io.Serializable;

public interface BaseDependView<P extends BasePresenter> {

    void onBusinessFinish(Serializable serializable);

    void showMsg(String msg);
}
