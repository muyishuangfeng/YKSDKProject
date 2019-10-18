package com.silence.sdk.ykgoogleplay;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;


import com.silence.sdk.ykcore.base.BaseEntry;
import com.silence.sdk.ykcore.common.Target;
import com.silence.sdk.ykcore.impl.OnRechargeListener;
import com.silence.sdk.ykcore.model.RechargeResult;
import com.silence.sdk.ykcore.model.YKGameValues;
import com.silence.sdk.ykgoogleplay.util.IabHelper;
import com.silence.sdk.ykgoogleplay.util.IabResult;
import com.silence.sdk.ykgoogleplay.util.Inventory;
import com.silence.sdk.ykgoogleplay.util.Purchase;
import com.silence.sdk.yknet.manager.LoginRealizeManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class GooglePlayHelper {

    private static final String TAG = GooglePlayHelper.class.getSimpleName();
    private static IabHelper mHelper;
    private static boolean mSetupDone = false;
    //订单号
    private String mOrderID;
    //商品集合
    private List<String> mGoodsList = new ArrayList<>();
    private WeakReference<Activity> mActivityRef;
    //公钥
    private String mPublicKey;
    private int mRechargeTarget;
    private OnRechargeListener mListener;
    //是否是沙盒账号
    private int mPayTest;
    //商品ID
    private String mProductID;
    //请求码
    int mRequestCode;
    //商品
    private String mSku;
    //用户标记
    private String mUserToken;


    GooglePlayHelper(Activity activity, String mPublicKey, String userToken, int payTest,
                     String sku, String productID, int requestCode, String orderID,
                     OnRechargeListener mListener) {
        this.mActivityRef = new WeakReference<>(activity);
        this.mPublicKey = mPublicKey;
        this.mUserToken = userToken;
        this.mOrderID = orderID;
        this.mPayTest = payTest;
        this.mSku = sku;
        this.mProductID = productID;
        this.mRequestCode = requestCode;
        this.mRechargeTarget = Target.RECHARGE_GOOGLE;
        this.mListener = mListener;
    }



    /**
     * 初始化
     */
    void init() {
        //创建谷歌帮助类
        mHelper = new IabHelper(mActivityRef.get(), mPublicKey);
        mHelper.enableDebugLogging(true);
        if (mHelper != null) {
            mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                @Override
                public void onIabSetupFinished(IabResult result) {
                    if (!result.isSuccess()) {
                        mSetupDone = false;
                    } else {
                        mSetupDone = true;
                        try {
                            mHelper.queryInventoryAsync(true, null, null,
                                    new IabHelper.QueryInventoryFinishedListener() {
                                        @Override
                                        public void onQueryInventoryFinished(IabResult result, Inventory inv) {
                                            if (result.isSuccess()) {
                                                if (inv.getAllPurchases() != null) {
                                                    if (inv.getAllPurchases().size() > 0) {
                                                        mGoodsList = getGoodsList(inv.getAllPurchases());
                                                        for (int i = 0; i < inv.getAllPurchases().size(); i++) {
                                                            consumeProduct(inv.getAllPurchases().get(i));
                                                        }
                                                    } else {
                                                        recharge();
                                                    }

                                                }

                                            }
                                        }

                                    });
                        } catch (IabHelper.IabAsyncInProgressException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    /**
     * 消费掉商品
     */
    private void consumeProduct(Purchase purchase) {
        try {
            mHelper.consumeAsync(purchase, new IabHelper.OnConsumeFinishedListener() {
                @Override
                public void onConsumeFinished(Purchase purchase, IabResult result) {
                    if (purchase != null ) {
                        BaseEntry<Purchase> entry = new BaseEntry<>();
                        entry.setCode(YKGameValues.GOOGLE_PLAY_SUPPLEMENTS);
                        entry.setData(purchase);
                        mListener.onState(mActivityRef.get(), RechargeResult.stateOf(entry));
                        recharge();
                    }
                }
            });
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消费掉商品
     */
    private void consumeProduct2(Purchase purchase) {
        try {
            mHelper.consumeAsync(purchase, new IabHelper.OnConsumeFinishedListener() {
                @Override
                public void onConsumeFinished(Purchase purchase, IabResult result) {
                }
            });
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    /**
     * 购买
     */
    private void recharge() {
        if (mSetupDone) {
            if (!TextUtils.isEmpty(mUserToken)) {
                getLTOrderID();
            } else {
                mListener.onState(mActivityRef.get(), RechargeResult.failOf("order create failed:user token is empty"));
            }
        } else {
            if (!TextUtils.isEmpty(mPublicKey)) {
                //创建谷歌帮助类
                mHelper = new IabHelper(mActivityRef.get(), mPublicKey);
                mHelper.enableDebugLogging(true);
                mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                    @Override
                    public void onIabSetupFinished(IabResult result) {
                        if (result.isFailure()) {
                            mSetupDone = false;
                        }
                        if (result.isSuccess()) {
                            mSetupDone = true;
                        }
                    }
                });
            }

        }
    }


    /**
     * 获取订单ID
     *
     */
    private void getLTOrderID() {
        if (!TextUtils.isEmpty(mOrderID)) {
            try {
                if (mHelper == null) return;
                mHelper.queryInventoryAsync(true, mGoodsList, mGoodsList,
                        new IabHelper.QueryInventoryFinishedListener() {
                            @Override
                            public void onQueryInventoryFinished(IabResult result, Inventory inv) {
                                if (result != null) {
                                    if (result.isSuccess() && inv.hasPurchase(mSku)) {
                                        //消费, 并下一步, 这里Demo里面我没做提示,将购买了,但是没消费掉的商品直接消费掉, 正常应该
                                        //给用户一个提示,存在未完成的支付订单,是否完成支付
                                        consumeProduct(inv.getPurchase(mSku));
                                    } else {
                                        getProduct(mRequestCode, mSku);
                                    }
                                }
                            }

                        });
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 产品获取
     *
     * @param REQUEST_CODE 请求码
     * @param SKU          产品唯一id, 填写你自己添加的商品id
     */
    private void getProduct(int REQUEST_CODE, final String SKU) {
        if (!TextUtils.isEmpty(mOrderID)) {
            try {
                mHelper.launchPurchaseFlow(mActivityRef.get(), SKU, REQUEST_CODE, new IabHelper.OnIabPurchaseFinishedListener() {
                    @Override
                    public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                        if (result.isFailure()) {
                            return;
                        }
                        if (purchase.getSku().equals(SKU)) {
                            //购买成功，调用消耗
                            consumeProduct(purchase);
                        }
                    }
                }, mOrderID);
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 回调
     *
     * @param requestCode     请求码
     * @param resultCode      结果码
     * @param data            数据
     * @param selfRequestCode 自定义请求码
     */
    void onActivityResult(int requestCode, int resultCode, Intent data, int selfRequestCode) {
        //将回调交给帮助类来处理, 否则会出现支付正在进行的错误
        if (mHelper == null) return;
        mHelper.handleActivityResult(requestCode, resultCode, data);
        if (requestCode == selfRequestCode) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            //订单信息
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");
            if (!TextUtils.isEmpty(purchaseData)) {
                BaseEntry entry = new BaseEntry();
                entry.setCode(YKGameValues.GOOGLE_PLAY_SUCCESS);
                entry.setResult(purchaseData);
                mListener.onState(mActivityRef.get(), RechargeResult.stateOf(entry));
            }
        }

    }




    /**
     * 释放资源
     */
    void release() {
        if (mHelper != null) {
            mHelper.disposeWhenFinished();
        }
        mHelper = null;
    }

    /**
     * 获取商品集合
     */
    private List<String> getGoodsList(
            List<Purchase> mList) {
        mGoodsList = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            mGoodsList.add(mList.get(i).getSku());
        }
        return mGoodsList;
    }

}
