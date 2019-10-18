package com.silence.sdk.ykcore.platform;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;


import com.silence.sdk.ykcore.impl.OnLoginStateListener;
import com.silence.sdk.ykcore.model.LoginObject;
import com.silence.sdk.ykcore.uikit.BaseActionActivity;

import java.util.Map;

public abstract class AbsPlatform implements IPlatform {


    protected String mAppId;
    protected String mAppName;
    protected int mTarget;
    protected String mAppKey;
    protected String mClientID;
    protected String mBaseUrl;
    protected String mAdID;
    protected String mPackageID;
    protected int mSelfRequestCode;
    protected int mPayTest;
    protected String mSku;
    protected Map<String, Object> mParams;
    protected String mPublicKey;
    protected String mProductID;
    protected String mPayType;
    protected String mPhone;
    protected String mPassword;
    protected String mLoginCode;
    protected String mQqAppID;


    public AbsPlatform(Context context, String appId, String appName, String appKey, int target) {
        mAppId = appId;
        mAppName = appName;
        mAppKey = appKey;
        mTarget = target;
    }

    public AbsPlatform(Context context, String clientID, int selfRequestCode, int target) {
        mClientID = clientID;
        mSelfRequestCode = selfRequestCode;
        mTarget = target;
    }

    public AbsPlatform(Context context, int target) {
        mTarget = target;
    }

    public AbsPlatform(Context context, String appId, String appKey, int payTest, String publicKey,
                       int selfRequestCode, String sku, String productID, Map<String, Object> params,
                       String payType, int target) {
        mAppId = appId;
        mAppKey = appKey;
        mPayTest = payTest;
        mPublicKey = publicKey;
        mProductID = productID;
        mPayType = payType;
        mSelfRequestCode = selfRequestCode;
        mSku = sku;
        mParams = params;
        mTarget = target;
    }

    public AbsPlatform(Context context, String publicKey, int payTest,
                       int selfRequestCode, String sku, String productID, int target) {
        mPayTest = payTest;
        mPublicKey = publicKey;
        mSelfRequestCode = selfRequestCode;
        mSku = sku;
        mProductID = productID;
        mTarget = target;
    }

    public AbsPlatform(Context context, String baseUrl, String appId, String appKey,
                       String qqAppID, int target) {
        mAppId = appId;
        mAppKey = appKey;
        mBaseUrl = baseUrl;
        mQqAppID = qqAppID;
        mTarget = target;
    }

    public AbsPlatform(Context context, String appId, String appKey, int payTest, String publicKey,
                       int selfRequestCode, String sku, String productID, Map<String, Object> params,
                       int target) {
        mAppId = appId;
        mAppKey = appKey;
        mPayTest = payTest;
        mPublicKey = publicKey;
        mProductID = productID;
        mSelfRequestCode = selfRequestCode;
        mSku = sku;
        mParams = params;
        mTarget = target;
    }

    public AbsPlatform(Context context, String appId, String appKey, String baseUrl, String phone,
                       String password, String loginCode, int target) {
        mAppId = appId;
        mAppKey = appKey;
        mBaseUrl = baseUrl;
        mLoginCode = loginCode;
        mPhone = phone;
        mPassword = password;
        mTarget = target;
    }

    public AbsPlatform(Context context, int payTest, String publicKey,
                       int selfRequestCode, String sku, String productID, String payType, int target) {
        mPayTest = payTest;
        mPublicKey = publicKey;
        mSelfRequestCode = selfRequestCode;
        mSku = sku;
        mProductID = productID;
        mPayType = payType;
        mTarget = target;
    }

    public AbsPlatform(Context context, String baseUrl, String appId, String appKey, String clientID,
                       String adID, String packageID, int selfRequestCode, int target) {
        mBaseUrl = baseUrl;
        mAppId = appId;
        mAppKey = appKey;
        mClientID = clientID;
        mAdID = adID;
        mPackageID = packageID;
        mSelfRequestCode = selfRequestCode;
        mTarget = target;
    }

    public AbsPlatform(Context context, String baseUrl, String appId, String appKey, String adID,
                       String packageID, int target) {
        mBaseUrl = baseUrl;
        mAppId = appId;
        mAppKey = appKey;
        mAdID = adID;
        mPackageID = packageID;
        mTarget = target;
    }


    @Override
    public Class getUIKitClazz() {
        return null;
    }

    @Override
    public void handleIntent(Activity intent) {

    }

    @Override
    public void onResponse(Object response) {

    }

    @Override
    public boolean isInstall(Context context) {
        return false;
    }

    @Override
    public void login(Activity activity, int target, LoginObject object, OnLoginStateListener listener) {
        throw new UnsupportedOperationException("该平台不支持登录操作～");
    }

    @Override
    public void onActivityResult(BaseActionActivity activity, int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void recycle() {

    }

    public boolean checkPlatformConfig() {
        return !TextUtils.isEmpty(mAppId) && !TextUtils.isEmpty(mAppKey);
    }
}
