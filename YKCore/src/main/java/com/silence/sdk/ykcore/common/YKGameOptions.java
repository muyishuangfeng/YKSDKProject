package com.silence.sdk.ykcore.common;

import android.content.Context;
import android.text.TextUtils;


import com.silence.sdk.ykcore.R;
import com.silence.sdk.ykcore.util.FileUtil;

import java.io.File;
import java.util.Map;

public class YKGameOptions {
    private static final String SHARE_CACHE_DIR_NAME = "toLogin";
    //是否debug
    private boolean debug;
    //app名称
    private String appName;
    //appID
    private String wxAppId;
    //secretKey
    private String wxSecretKey;
    //Google客户端ID
    private String googleClientID;
    //微信鉴权code
    private boolean wxOnlyAuthCode;
    //qqAppID
    private String qqAppId;
    //错误图片
    private int failImgRes;
    //token保存时间
    private long tokenExpiresHours;
    //微信是否可以
    private boolean wxEnable;
    //qq是否可以
    private boolean qqEnable;
    //google是否可以
    private boolean googleEnable;
    //google是否可以
    private boolean googlePlayEnable;
    //facebook是否可以
    private boolean facebookEnable;
    //facebookAppID
    private String facebookAppID;
    //baseUrl
    private String baseUrl;
    //乐推AppID
    private String ltAppId;
    //乐推AppKey
    private String ltAppKey;
    //广告ID（唯一）
    private String adID;
    //包名
    private String packageID;
    //请求码
    private int selfRequestCode;
    //商品
    private String sku;
    //是否沙盒测试
    private int mPayTest;
    //自定义参数
    private Map<String, Object> mParams;
    //公钥
    private String mPublicKey;
    //商品ID
    private String goodsID;
    //商品类型
    private String goodsType;
    //onestore
    private boolean oneStoreEnable;
    //手机号
    private String mPhone;
    //密码
    private String mPassword;
    //登录状态
    private String mLoginCode;
    //是否支持手机登录
    private boolean isPhoneEnable;
    //是否支持Twitter登录
    private boolean isTwitterEnable;
    //是否支持微博登录
    private boolean isWBEnable;
    //缓存
    private String cacheDir;

    public String getCacheDir() {
        return cacheDir;
    }

    public String getAppName() {
        return appName;
    }

    public String getWxAppId() {
        return wxAppId;
    }

    public String getWxSecretKey() {
        return wxSecretKey;
    }

    public String getGoogleClientID() {
        return googleClientID;
    }

    public boolean isWxOnlyAuthCode() {
        return wxOnlyAuthCode;
    }

    public String getQqAppId() {
        return qqAppId;
    }


    public int getFailImgRes() {
        return failImgRes;
    }

    public long getTokenExpiresHours() {
        return tokenExpiresHours;
    }

    public boolean isWxEnable() {
        return wxEnable;
    }

    public boolean isQqEnable() {
        return qqEnable;
    }

    public boolean isGoogleEnable() {
        return googleEnable;
    }

    public boolean isFacebookEnable() {
        return facebookEnable;
    }

    public boolean isGooglePlayEnable() {
        return googlePlayEnable;
    }

    public boolean isOneStoreEnable() {
        return oneStoreEnable;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getLtAppId() {
        return ltAppId;
    }

    public String getLtAppKey() {
        return ltAppKey;
    }

    public String getAdID() {
        return adID;
    }

    public String getPackageID() {
        return packageID;
    }

    public int getSelfRequestCode() {
        return selfRequestCode;
    }

    public String getFacebookAppID() {
        return facebookAppID;
    }

    public String getSku() {
        return sku;
    }

    public int getmPayTest() {
        return mPayTest;
    }

    public Map<String, Object> getmParams() {
        return mParams;
    }

    public String getmPublicKey() {
        return mPublicKey;
    }

    public String getGoodsID() {
        return goodsID;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public String getmPhone() {
        return mPhone;
    }

    public String getmPassword() {
        return mPassword;
    }

    public String getmLoginCode() {
        return mLoginCode;
    }

    public boolean isPhoneEnable() {
        return isPhoneEnable;
    }

    public boolean isTwitterEnable() {
        return isTwitterEnable;
    }

    public boolean isWBEnable() {
        return isWBEnable;
    }

    private YKGameOptions(Builder builder) {
        if (builder.wxOnlyAuthCode == null) {
            builder.wxOnlyAuthCode = false;
        }
        if (builder.tokenExpiresHours < 0) {
            builder.tokenExpiresHours = 0;
        }
        this.debug = builder.debug;
        this.appName = builder.appName;
        this.wxAppId = builder.wxAppId;
        this.wxSecretKey = builder.wxSecretKey;
        this.wxOnlyAuthCode = builder.wxOnlyAuthCode;
        // qq 配置
        this.qqAppId = builder.qqAppId;
        // 图片默认资源
        this.failImgRes = builder.failImgRes;
        // token 有效时间，默认1天
        this.tokenExpiresHours = builder.tokenExpiresHours;
        this.googleClientID = builder.googleClientID;
        //乐推
        this.ltAppId = builder.ltAppId;
        this.ltAppKey = builder.ltAppKey;
        this.adID = builder.adID;
        this.packageID = builder.packageID;
        this.baseUrl = builder.baseUrl;
        this.selfRequestCode = builder.selfRequestCode;
        this.facebookAppID = builder.facebookAppID;
        this.sku = builder.sku;
        this.mPayTest = builder.mPayTest;
        this.mParams = builder.mParams;
        this.mPublicKey = builder.mPublicKey;
        this.goodsID = builder.goodsID;
        this.goodsType = builder.mGoodsType;
        this.mPhone = builder.mPhone;
        this.mPassword = builder.mPassword;
        this.mLoginCode = builder.mLoginCode;
        this.cacheDir=builder.cacheDir;

        // enable
        this.wxEnable = builder.wxEnable;
        this.qqEnable = builder.qqEnable;
        this.facebookEnable = builder.facebookEnable;
        this.googleEnable = builder.googleEnable;
        this.googlePlayEnable = builder.googlePlayEnable;
        this.oneStoreEnable = builder.oneStoreEnable;
        this.isPhoneEnable = builder.isPhoneEnable;
        this.isWBEnable = builder.isWBEnable;
        this.isTwitterEnable = builder.isTwitterEnable;
    }


    @Override
    public String toString() {
        return "YKGameOptions{" +
                "debug=" + debug +
                ", appName='" + appName + '\'' +
                ", wxAppId='" + wxAppId + '\'' +
                ", wxSecretKey='" + wxSecretKey + '\'' +
                ", googleClientID='" + googleClientID + '\'' +
                ", wxOnlyAuthCode=" + wxOnlyAuthCode +
                ", qqAppId='" + qqAppId + '\'' +
                ", failImgRes=" + failImgRes +
                ", tokenExpiresHours=" + tokenExpiresHours +
                ", wxEnable=" + wxEnable +
                ", qqEnable=" + qqEnable +
                ", googleEnable=" + googleEnable +
                ", googlePlayEnable=" + googlePlayEnable +
                ", facebookEnable=" + facebookEnable +
                ", facebookAppID='" + facebookAppID + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                ", ltAppId='" + ltAppId + '\'' +
                ", ltAppKey='" + ltAppKey + '\'' +
                ", adID='" + adID + '\'' +
                ", packageID='" + packageID + '\'' +
                ", selfRequestCode=" + selfRequestCode +
                ", sku='" + sku + '\'' +
                ", mPayTest=" + mPayTest +
                ", mParams=" + mParams +
                ", mPublicKey='" + mPublicKey + '\'' +
                ", goodsID='" + goodsID + '\'' +
                ", goodsType='" + goodsType + '\'' +
                ", oneStoreEnable=" + oneStoreEnable +
                ", mPhone='" + mPhone + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mLoginCode='" + mLoginCode + '\'' +
                ", cacheDir='" + cacheDir + '\'' +
                '}';
    }

    /**
     * Builder
     */
    public static class Builder {
        //缓存
        private String cacheDir;
        // 调试配置
        private boolean debug;
        private String appName;
        // 微信配置
        private String wxAppId;
        private String wxSecretKey;
        private Boolean wxOnlyAuthCode;
        // qq 配置
        private String qqAppId;
        // google配置
        private String googleClientID;
        // 图片默认资源
        private int failImgRes;
        // token 失效时间，默认立刻失效
        private int tokenExpiresHours = -1;

        private boolean wxEnable;
        private boolean qqEnable;
        private boolean googleEnable;
        private boolean googlePlayEnable;
        private boolean facebookEnable;
        private Context context;
        //baseUrl
        private String baseUrl;
        //乐推AppID
        private String ltAppId;
        //乐推AppKey
        private String ltAppKey;
        //广告ID（唯一）
        private String adID;
        //包名
        private String packageID;
        //请求码
        private int selfRequestCode;
        //facebook  AppID
        private String facebookAppID;
        //商品
        private String sku;
        //是否沙盒测试
        private int mPayTest;
        //自定义参数
        private Map<String, Object> mParams;
        //公钥
        private String mPublicKey;
        //商品ID
        private String goodsID;
        //商品类型
        private String mGoodsType;
        //oneStore
        private boolean oneStoreEnable;
        //手机号
        private String mPhone;
        //密码
        private String mPassword;
        //登录状态
        private String mLoginCode;
        //是否支持手机登录
        private boolean isPhoneEnable;
        //是否支持Twitter登录
        private boolean isTwitterEnable;
        //是否支持微博登录
        private boolean isWBEnable;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setQQEnable(boolean qqEnable) {
            this.qqEnable = qqEnable;
            return this;
        }

        public Builder facebook(String facebookAppID) {
            this.facebookAppID = facebookAppID;
            this.facebookEnable = true;
            return this;
        }

        public Builder facebookEnable() {
            this.facebookEnable = true;
            return this;
        }

        public Builder qq(String qqAppId) {
            this.qqAppId = qqAppId;
            this.qqEnable = true;
            return this;
        }

        public Builder wx(String wxAppId, String wxSecretKey) {
            this.wxSecretKey = wxSecretKey;
            this.wxAppId = wxAppId;
            this.wxEnable = true;
            return this;
        }

        public Builder wbEnable() {
            this.isWBEnable = true;
            return this;
        }

        public Builder phoneEnable() {
            this.isPhoneEnable = true;
            return this;
        }

        public Builder twitterEnable() {
            this.isTwitterEnable = true;
            return this;
        }

        public Builder wx(String wxAppId, String wxSecretKey, boolean wxOnlyAuthCode) {
            this.wxOnlyAuthCode = wxOnlyAuthCode;
            this.wxSecretKey = wxSecretKey;
            this.wxAppId = wxAppId;
            this.wxEnable = true;
            return this;
        }

        public Builder google(String googleClientID) {
            this.googleClientID = googleClientID;
            this.googleEnable = true;
            return this;
        }

        public Builder phoneAndPass(String phone, String password) {
            this.mPhone = phone;
            this.mPassword = password;
            return this;
        }

        public Builder loginCode(String loginCode) {
            this.mLoginCode = loginCode;
            return this;
        }

        public Builder oneStore() {
            this.oneStoreEnable = true;
            return this;
        }


        public Builder appName(String appName) {
            this.appName = appName;
            return this;
        }

        public Builder appID(String ltAppId) {
            this.ltAppId = ltAppId;
            return this;
        }

        public Builder goodsID(String sku, String goodsID) {
            this.sku = sku;
            this.goodsID = goodsID;
            return this;
        }

        public Builder appKey(String ltAppKey) {
            this.ltAppKey = ltAppKey;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder requestCode(int selfRequestCode) {
            this.selfRequestCode = selfRequestCode;
            return this;
        }

        public Builder setAdID(String adID) {
            this.adID = adID;
            return this;
        }

        public Builder packageID(String packageID) {
            this.packageID = packageID;
            return this;
        }

        public Builder publicKey(String publicKey) {
            this.mPublicKey = publicKey;
            return this;
        }

        public Builder setParams(Map<String, Object> params) {
            this.mParams = params;
            return this;
        }

        public Builder payTest(int payTest) {
            this.mPayTest = payTest;
            return this;
        }

        public Builder goodsType(String goodsType) {
            this.mGoodsType = goodsType;
            return this;
        }

        public Builder tokenExpiresHours(int time) {
            this.tokenExpiresHours = time;
            return this;
        }


        public Builder failImgRes(int failImgRes) {
            this.failImgRes = failImgRes;
            return this;
        }

        public Builder debug(boolean debug) {
            this.debug = debug;
            return this;
        }

        public Builder googlePlay(boolean googlePlayEnable) {
            this.googlePlayEnable = googlePlayEnable;
            return this;
        }


        public YKGameOptions build() {
            if (TextUtils.isEmpty(appName)) {
                appName = context.getString(R.string.app_name);
            }
            File storageDir = new File(context.getExternalCacheDir(), SHARE_CACHE_DIR_NAME);
            if (!FileUtil.isExist(storageDir)){
                storageDir.mkdirs();
            }
            this.cacheDir = storageDir.getAbsolutePath();
            return new YKGameOptions(this);
        }
    }
}
