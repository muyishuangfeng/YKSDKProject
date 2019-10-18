package com.silence.sdk.ykonestore;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.onestore.iap.api.IapResult;
import com.onestore.iap.api.PurchaseClient;
import com.onestore.iap.api.PurchaseData;
import com.silence.sdk.ykcore.base.BaseEntry;
import com.silence.sdk.ykcore.common.Target;
import com.silence.sdk.ykcore.impl.OnRechargeListener;
import com.silence.sdk.ykcore.model.OneStoreResult;
import com.silence.sdk.ykcore.model.RechargeResult;
import com.silence.sdk.ykcore.model.YKGameValues;

import java.lang.ref.WeakReference;
import java.util.List;


public class OneStoreHelper {

    @SuppressLint("StaticFieldLeak")
    private static PurchaseClient mPurchaseClient;
    private static final int IAP_API_VERSION = 5;
    //是否初始化
    private static boolean mIsInit = false;
    private WeakReference<Activity> mActivityRef;
    //公钥
    private String mPublicKey;
    //订单号
    private String mOrderID;
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
    //商品类型
    private String mProductType;
    //用户Token
    private String mUserToken;

    OneStoreHelper(Activity activity, String mPublicKey, String userToken, int payTest,
                   String sku, String productID, String productType, int requestCode,
                   String orderID, OnRechargeListener mListener) {
        this.mActivityRef = new WeakReference<>(activity);
        this.mPublicKey = mPublicKey;
        this.mUserToken = userToken;
        this.mPayTest = payTest;
        this.mSku = sku;
        this.mProductID = productID;
        this.mRequestCode = requestCode;
        this.mOrderID = orderID;
        this.mProductType = productType;
        this.mRechargeTarget = Target.RECHARGE_ONE_STORE;
        this.mListener = mListener;

    }


    /**
     * 初始化
     *
     * @param mListener 回调
     */
    public void initOneStore(final OnRechargeListener mListener) {
        if (!mIsInit) {
            mPurchaseClient = new PurchaseClient(mActivityRef.get(), mPublicKey);
            mIsInit = true;
        }
        if (mPurchaseClient != null) {
            mPurchaseClient.connect(new PurchaseClient.ServiceConnectionListener() {
                @Override
                public void onConnected() {
                    if (mListener != null) {
                        mListener.onState(mActivityRef.get(),
                                RechargeResult.stateOf(OneStoreResult.RESULT_CLIENT_CONNECTED));
                    }
                    checkBillingSupportedAndLoadPurchases(mListener);
                }

                @Override
                public void onDisconnected() {
                    if (mListener != null) {
                        mListener.onState(mActivityRef.get(),
                                RechargeResult.stateOf(OneStoreResult.RESULT_CLIENT_UN_CONNECTED));
                    }
                }

                @Override
                public void onErrorNeedUpdateException() {
                    if (mListener != null) {
                        mListener.onState(mActivityRef.get(),
                                RechargeResult.stateOf(OneStoreResult.RESULT_CONNECTED_NEED_UPDATE));
                        PurchaseClient.launchUpdateOrInstallFlow(mActivityRef.get());
                    }
                }
            });
        }
    }

    /**
     * 检查是否支持
     */
    private void checkBillingSupportedAndLoadPurchases(final OnRechargeListener mListener) {
        if (mPurchaseClient == null) {
            if (mListener != null) {
                mListener.onState(mActivityRef.get(),
                        RechargeResult.stateOf(OneStoreResult.RESULT_CLIENT_NOT_INIT));
            }
        } else {
            mPurchaseClient.isBillingSupportedAsync(IAP_API_VERSION, new PurchaseClient.BillingSupportedListener() {
                @Override
                public void onSuccess() {
                    mListener.onState(mActivityRef.get(),
                            RechargeResult.stateOf(OneStoreResult.RESULT_BILLING_OK));
                    // 然后通过对托管商品和每月采购历史记录的呼叫接收采购历史记录信息。
                    mPurchaseClient.queryPurchasesAsync(IAP_API_VERSION, mProductType,
                            new PurchaseClient.QueryPurchaseListener() {
                                @Override
                                public void onSuccess(List<PurchaseData> purchaseDataList, String productType) {
                                    if (purchaseDataList != null) {
                                        if (purchaseDataList.size() > 0) {
                                            for (PurchaseData purchase : purchaseDataList) {
                                                consumeItem(purchase, mListener);
                                            }
                                        } else {
                                            getProduct();
                                        }

                                    }
                                }

                                @Override
                                public void onError(IapResult iapResult) {
                                    mListener.onState(mActivityRef.get(),
                                            RechargeResult.stateOf(OneStoreResult.getResult(iapResult.getCode())));
                                }

                                @Override
                                public void onErrorRemoteException() {
                                    mListener.onState(mActivityRef.get(),
                                            RechargeResult.stateOf(OneStoreResult.RESULT_PURCHASES_REMOTE_ERROR));
                                }

                                @Override
                                public void onErrorSecurityException() {
                                    mListener.onState(mActivityRef.get(),
                                            RechargeResult.stateOf(OneStoreResult.RESULT_PURCHASES_SECURITY_ERROR));
                                }

                                @Override
                                public void onErrorNeedUpdateException() {
                                    mListener.onState(mActivityRef.get(),
                                            RechargeResult.stateOf(OneStoreResult.RESULT_PURCHASES_NEED_UPDATE));
                                    PurchaseClient.launchUpdateOrInstallFlow(mActivityRef.get());
                                }
                            });
                }

                @Override
                public void onError(IapResult iapResult) {
                    mListener.onState(mActivityRef.get(),
                            RechargeResult.stateOf(OneStoreResult.getResult(iapResult.getCode())));
                }

                @Override
                public void onErrorRemoteException() {
                    mListener.onState(mActivityRef.get(),
                            RechargeResult.stateOf(OneStoreResult.RESULT_BILLING_REMOTE_ERROR));
                }

                @Override
                public void onErrorSecurityException() {
                    mListener.onState(mActivityRef.get(),
                            RechargeResult.stateOf(OneStoreResult.RESULT_BILLING_SECURITY_ERROR));
                }

                @Override
                public void onErrorNeedUpdateException() {
                    mListener.onState(mActivityRef.get(),
                            RechargeResult.stateOf(OneStoreResult.RESULT_BILLING_NEED_UPDATE));
                    PurchaseClient.launchUpdateOrInstallFlow(mActivityRef.get());
                }
            });
        }
    }


    /**
     * 在管理商品 (inapp) 后或历史记录视图完成后, 消耗托管商品的消费.
     *
     * @param purchaseData 产品数据
     */
    private void consumeItem(PurchaseData purchaseData, final OnRechargeListener mListener) {
        if (mPurchaseClient == null) {
            mListener.onState(mActivityRef.get(),
                    RechargeResult.stateOf(OneStoreResult.RESULT_PURCHASES_NEED_UPDATE));
            return;
        }
        mPurchaseClient.consumeAsync(IAP_API_VERSION, purchaseData,
                new PurchaseClient.ConsumeListener() {
                    @Override
                    public void onSuccess(PurchaseData purchaseData) {
                        if (purchaseData != null) {
                            uploadServer(purchaseData);
                        }
                    }

                    @Override
                    public void onError(IapResult iapResult) {
                        mListener.onState(mActivityRef.get(),
                                RechargeResult.stateOf(OneStoreResult.getResult(iapResult.getCode())));
                    }

                    @Override
                    public void onErrorRemoteException() {
                        mListener.onState(mActivityRef.get(),
                                RechargeResult.stateOf(OneStoreResult.RESULT_CONSUME_REMOTE_ERROR));
                    }

                    @Override
                    public void onErrorSecurityException() {
                        mListener.onState(mActivityRef.get(),
                                RechargeResult.stateOf(OneStoreResult.RESULT_CONSUME_SECURITY_ERROR));
                    }

                    @Override
                    public void onErrorNeedUpdateException() {
                        mListener.onState(mActivityRef.get(),
                                RechargeResult.stateOf(OneStoreResult.RESULT_CONSUME_NEED_UPDATE));
                    }
                });
    }


    /**
     * oneStore回调
     *
     * @param requestCode     请求码
     * @param resultCode      结果码
     * @param selfRequestCode 自定义请求码
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data, int selfRequestCode) {
        if (requestCode == selfRequestCode)
            if (resultCode == Activity.RESULT_OK) {
                if (mPurchaseClient.handlePurchaseData(data)) {
                    String signature = data.getStringExtra("purchaseSignature");
                    String purchaseData = data.getStringExtra("purchaseData");
                    Gson gson = new Gson();
                    PurchaseData mPurchaseData = gson.fromJson(purchaseData, PurchaseData.class);
                    if (mPurchaseData != null) {
                        consumeItem(mPurchaseData, mListener);
                    }
                }
            }
    }

    /**
     * 购买商品
     */
    private void getProduct() {
        if (!mIsInit) {
            mPurchaseClient = new PurchaseClient(mActivityRef.get(), mPublicKey);
        } else {
            getLTOrderID();

        }
    }

    /**
     * 购买
     */
    private void launchPurchase() {
        if (mPurchaseClient != null) {
            mPurchaseClient.launchPurchaseFlowAsync(IAP_API_VERSION,
                    mActivityRef.get(), mRequestCode, mSku, mSku,
                    mProductType, mOrderID, "",
                    false, new PurchaseClient.PurchaseFlowListener() {

                        @Override
                        public void onSuccess(PurchaseData purchaseData) {
                            mListener.onState(mActivityRef.get(),
                                    RechargeResult.stateOf(OneStoreResult.RESULT_PURCHASES_FLOW_OK));
                        }

                        @Override
                        public void onError(IapResult result) {
                            mListener.onState(mActivityRef.get(),
                                    RechargeResult.stateOf(OneStoreResult.getResult(result.getCode())));
                        }

                        @Override
                        public void onErrorRemoteException() {
                            mListener.onState(mActivityRef.get(),
                                    RechargeResult.stateOf(OneStoreResult.RESULT_PURCHASES_FLOW_REMOTE_ERROR));
                        }

                        @Override
                        public void onErrorSecurityException() {
                            mListener.onState(mActivityRef.get(),
                                    RechargeResult.stateOf(OneStoreResult.RESULT_PURCHASES_FLOW_SECURITY_ERROR));
                        }

                        @Override
                        public void onErrorNeedUpdateException() {
                            mListener.onState(mActivityRef.get(),
                                    RechargeResult.stateOf(OneStoreResult.RESULT_PURCHASES_FLOW_NEED_UPDATE));
                            PurchaseClient.launchUpdateOrInstallFlow(mActivityRef.get());
                        }
                    });
        }
    }


    /**
     * 获取订单ID
     */
    private void getLTOrderID() {
        if (!TextUtils.isEmpty(mUserToken) && !TextUtils.isEmpty(mOrderID)) {
            launchPurchase();
        } else {
            BaseEntry entry = new BaseEntry();
            entry.setCode(YKGameValues.ONE_STORE_FAILED);
            entry.setResult("order create failed:user token or orderID is empty");
            mListener.onState(mActivityRef.get(), RechargeResult.stateOf(entry));
        }
    }

    /**
     * 上传到服务器验证
     */
    private void uploadServer(PurchaseData purchaseData) {
        BaseEntry<PurchaseData> entry = new BaseEntry<>();
        entry.setCode(YKGameValues.ONE_STORE_SUCCESS);
        entry.setData(purchaseData);
        Log.e("successOne",purchaseData.toString());
        mListener.onState(mActivityRef.get(), RechargeResult.stateOf(entry));
    }

    /**
     * 释放
     */
    public void release() {
        mIsInit = false;
        if (mPurchaseClient != null) {
            mPurchaseClient.terminate();
            mPurchaseClient = null;
        }

    }


}