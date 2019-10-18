package com.silence.sdk.ykcore.model;


/**
 * 登录参数类
 */
public class LoginObject {

    private String appSecret; // 授权域
    private String scope = YKGameValues.WX_SCOPE; // 授权域
    private String nonceStr; // 随机字符串
    private String timestamp; // 时间戳
    private String signature; // 签名
    private String LTAppID;//乐推AppID
    private String LTAppKey;//乐推AppKey
    private String mPackageID;//包名
    private String mAdID;//唯一标识
    private String mGoogleClient;//GoogleClientID
    private String baseUrl;//baseUrl
    private int selfRequestCode;//请求码
    private String facebookAppID;//facebook AppID
    private boolean loginOut;//退出登录
    private String mPhone;//手机号
    private String mPassword;//密码
    private String mLoginCode;//登录状态码
    private String qqAppID;//qqAppID

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getLTAppID() {
        return LTAppID;
    }

    public void setLTAppID(String LTAppID) {
        this.LTAppID = LTAppID;
    }

    public String getLTAppKey() {
        return LTAppKey;
    }

    public void setLTAppKey(String LTAppKey) {
        this.LTAppKey = LTAppKey;
    }

    public String getmPackageID() {
        return mPackageID;
    }

    public void setmPackageID(String mPackageID) {
        this.mPackageID = mPackageID;
    }

    public String getmAdID() {
        return mAdID;
    }

    public void setmAdID(String mAdID) {
        this.mAdID = mAdID;
    }

    public String getmGoogleClient() {
        return mGoogleClient;
    }

    public void setmGoogleClient(String mGoogleClient) {
        this.mGoogleClient = mGoogleClient;
    }

    public int getSelfRequestCode() {
        return selfRequestCode;
    }

    public void setSelfRequestCode(int selfRequestCode) {
        this.selfRequestCode = selfRequestCode;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getFacebookAppID() {
        return facebookAppID;
    }

    public void setFacebookAppID(String facebookAppID) {
        this.facebookAppID = facebookAppID;
    }

    public boolean isLoginOut() {
        return loginOut;
    }

    public void setLoginOut(boolean loginOut) {
        this.loginOut = loginOut;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmLoginCode() {
        return mLoginCode;
    }

    public void setmLoginCode(String mLoginCode) {
        this.mLoginCode = mLoginCode;
    }

    public String getQqAppID() {
        return qqAppID;
    }

    public void setQqAppID(String qqAppID) {
        this.qqAppID = qqAppID;
    }
}
