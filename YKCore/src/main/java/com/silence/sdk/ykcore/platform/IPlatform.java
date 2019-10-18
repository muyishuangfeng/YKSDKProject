package com.silence.sdk.ykcore.platform;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.silence.sdk.ykcore.impl.OnLoginStateListener;
import com.silence.sdk.ykcore.impl.OnRechargeListener;
import com.silence.sdk.ykcore.impl.Recyclable;
import com.silence.sdk.ykcore.model.LoginObject;
import com.silence.sdk.ykcore.model.RechargeObject;
import com.silence.sdk.ykcore.uikit.BaseActionActivity;


/**
 * 平台基类接口
 */
public interface IPlatform extends Recyclable {


    /**
     * 获取交互的Activity的class
     *
     * @return 交互的Activity
     */
    Class getUIKitClazz();

    /**
     * 接收 Activity Intent
     *
     * @param intent 接收的intent
     */
    void handleIntent(Activity intent);

    /**
     * 登录返回的结果
     *
     * @param response 返回的结果
     */
    void onResponse(Object response);

    /**
     * 是否安装
     *
     * @param context 上下文
     * @return 是否安装
     */
    boolean isInstall(Context context);

    /**
     * 登录
     *
     * @param activity activity
     * @param target   登录目标
     * @param object   登录参数
     * @param listener 登录回调
     */
    void login(Activity activity, int target, LoginObject object, OnLoginStateListener listener);

    /**
     * 购买（支付）
     *
     * @param activity activity
     * @param target   支付目标
     * @param object   支付参数
     * @param listener 支付回调
     */
    void recharge(Activity activity, int target, RechargeObject object, OnRechargeListener listener);

    /**
     * 回调
     *
     * @param activity    activity
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        数据
     */
    void onActivityResult(BaseActionActivity activity, int requestCode, int resultCode, Intent data);
}
