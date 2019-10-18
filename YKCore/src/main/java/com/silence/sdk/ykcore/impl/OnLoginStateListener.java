package com.silence.sdk.ykcore.impl;

import android.app.Activity;

import com.silence.sdk.ykcore.model.LoginResult;


public interface OnLoginStateListener {
    /**
     * 登录状态
     *
     * @param activity 上下文
     * @param result   登录结果
     */
    void onState(Activity activity, LoginResult result);
}
