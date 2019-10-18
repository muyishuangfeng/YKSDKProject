package com.silence.sdk.ykfacebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.FacebookSdkNotInitializedException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.silence.sdk.ykcore.base.BaseEntry;
import com.silence.sdk.ykcore.impl.OnLoginStateListener;
import com.silence.sdk.ykcore.model.YKGameValues;

import java.lang.ref.WeakReference;
import java.util.Arrays;

class FacebookLoginHelper {

    private static final String TAG = FacebookLoginHelper.class.getSimpleName();


    private CallbackManager mFaceBookCallBack;
    private int mLoginTarget;
    private WeakReference<Activity> mActivityRef;
    private OnLoginStateListener mListener;


    FacebookLoginHelper(Activity activity, OnLoginStateListener listener, int loginTarget) {
        this.mActivityRef = new WeakReference<>(activity);
        this.mListener = listener;
        this.mLoginTarget = loginTarget;
    }


    /**
     * 初始化
     */
    void login(String appID, Context context, boolean isLoginOut) {
        FacebookSdk.setApplicationId(appID);
        FacebookSdk.sdkInitialize(context);
        if (isLoginOut) {
            loginOutAction();
        } else {
            loginAction();
        }

    }


    /**
     * 设置登录结果回调
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        数据
     */
    void setOnActivityResult(int requestCode, int resultCode, Intent data) {
        if (mFaceBookCallBack != null) {
            mFaceBookCallBack.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 登录
     */
    private void loginAction() {
        try {
            mFaceBookCallBack = CallbackManager.Factory.create();
            LoginManager.getInstance()
                    .logInWithReadPermissions(mActivityRef.get(),
                            Arrays.asList("public_profile"));
            if (mFaceBookCallBack != null) {
                LoginManager.getInstance().registerCallback(mFaceBookCallBack,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                if (loginResult != null) {
                                    BaseEntry entry = new BaseEntry();
                                    entry.setCode(YKGameValues.FACEBOOK_LOGIN_SUCCESS);
                                    entry.setResult(loginResult.getAccessToken().getToken());
                                    mListener.onState(mActivityRef.get(), com.silence.sdk.ykcore.model.LoginResult.stateOf(entry));
                                }

                            }

                            @Override
                            public void onCancel() {
                                BaseEntry entry = new BaseEntry();
                                entry.setCode(YKGameValues.FACEBOOK_LOGIN_CANCEL);
                                mListener.onState(mActivityRef.get(), com.silence.sdk.ykcore.model.LoginResult.stateOf(entry));
                            }

                            @Override
                            public void onError(FacebookException error) {
                                BaseEntry entry = new BaseEntry();
                                entry.setCode(YKGameValues.FACEBOOK_LOGIN_FAILED);
                                entry.setResult(error.toString());
                                mListener.onState(mActivityRef.get(), com.silence.sdk.ykcore.model.LoginResult.stateOf(entry));
                            }
                        });
            }
        } catch (FacebookSdkNotInitializedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 退出登录
     */
    private void loginOutAction() {
        LoginManager.getInstance().logOut();
        BaseEntry entry = new BaseEntry();
        entry.setCode(YKGameValues.FACEBOOK_LOGIN_OUT);
        entry.setResult("Facebook LoginOut");
        mListener.onState(mActivityRef.get(), com.silence.sdk.ykcore.model.LoginResult.stateOf(entry));
        mActivityRef.get().finish();
    }

}
