package com.silence.sdk.ykcore.model;

import java.util.Map;

/**
 * 登录参数类
 */
public class RechargeObject {
    //乐推AppID
    private String LTAppID;
    //乐推AppKey
    private String LTAppKey;
    //包名
    private String mPackageID;
    //唯一标识
    private String mAdID;
    //baseUrl
    private String baseUrl;
    //请求码
    private int selfRequestCode;
    //商品
    private String sku;
    //自定义参数
    private Map<String, Object> params;
    //用户token
    private String userToken;
    //订单
    private String orderID;
    //公钥
    private String publicKey;
    //是否是沙盒登录
    private int payTest;
    //商品ID
    private String goodsID;
    //商品类型
    private String mGoodsType;

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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public int getPayTest() {
        return payTest;
    }

    public void setPayTest(int payTest) {
        this.payTest = payTest;
    }

    public String getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }

    public String getmGoodsType() {
        return mGoodsType;
    }

    public void setmGoodsType(String mGoodsType) {
        this.mGoodsType = mGoodsType;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
}
