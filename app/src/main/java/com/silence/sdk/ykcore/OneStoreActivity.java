package com.silence.sdk.ykcore;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.silence.sdk.ykcore.base.BaseEntry;
import com.silence.sdk.ykcore.common.Target;
import com.silence.sdk.ykcore.common.YKGameOptions;
import com.silence.sdk.ykcore.common.YKGameSdk;
import com.silence.sdk.ykcore.impl.OnRechargeListener;
import com.silence.sdk.ykcore.manager.RechargeManager;
import com.silence.sdk.ykcore.model.RechargeObject;
import com.silence.sdk.ykcore.model.RechargeResult;
import com.silence.sdk.ykcore.model.YKGameValues;
import com.silence.sdk.ykonestore.OneStorePlatform;

import java.util.Map;
import java.util.WeakHashMap;

import androidx.appcompat.app.AppCompatActivity;

public class OneStoreActivity extends AppCompatActivity {

    Button mBtnPay;
    TextView mTxtResult;
    String PUBLIC_KEY = "123456";
    private static final int selfRequestCode = 0x01;
    String productID = "com.ltgamesyyjw.lslb1";
    private static final String TAG = OneStoreActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_store);
        initView();
        init();
    }


    private void initView() {
        mTxtResult = findViewById(R.id.txt_result);
        mBtnPay = findViewById(R.id.btn_pay);
        mBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeObject result = new RechargeObject();
                result.setSku(productID);
                result.setOrderID("1111111");
                result.setUserToken("78945522222");
                result.setGoodsID("11");
                result.setmGoodsType("in-app");
                result.setPublicKey(PUBLIC_KEY);
                result.setPayTest(1);
                RechargeManager.recharge(OneStoreActivity.this, Target.RECHARGE_ONE_STORE,
                        result, mOnRechargeListener);
            }
        });
    }

    private void init() {
        YKGameOptions options = new YKGameOptions.Builder(this)
                .debug(true)
                .publicKey(PUBLIC_KEY)
                .goodsType("in-app")
                .payTest(1)
                .oneStore()
                .goodsID(productID, "11")
                .googlePlay(true)
                .requestCode(selfRequestCode)
                .build();
        YKGameSdk.init(options);
    }

    OnRechargeListener mOnRechargeListener = new OnRechargeListener() {
        @Override
        public void onState(Activity activity, RechargeResult result) {
            BaseEntry entry=result.getBaseEntry();
            if (entry!=null){
                if (entry.getCode()== YKGameValues.ONE_STORE_FAILED){
                    Log.e("successOne",entry.getResult());
                }
            }
            switch (result.state) {
                case RechargeResult.STATE_RECHARGE_SUCCESS:
                    mTxtResult.setText(result.getResultModel().getCode() + "======");
                    break;
                case RechargeResult.STATE_RECHARGE_START:
                    Log.e(TAG, "开始支付");
                    break;
                case RechargeResult.STATE_RECHARGE_RESULT:
                    if (result.getResult() != null) {
                        switch (result.getResult()) {
                            case RESULT_BILLING_NEED_UPDATE: {
                                Log.e(TAG, "RESULT_BILLING_NEED_UPDATE");
                                break;
                            }
                            case RESULT_CLIENT_UN_CONNECTED: {
                                Log.e(TAG, "RESULT_CLIENT_UN_CONNECTED");
                                break;
                            }
                            case RESULT_CLIENT_CONNECTED: {
                                Log.e(TAG, "RESULT_CLIENT_CONNECTED");
                                break;
                            }
                            case RESULT_PURCHASES_REMOTE_ERROR: {
                                Log.e(TAG, "RESULT_PURCHASES_REMOTE_ERROR");
                                break;
                            }
                            case RESULT_PURCHASES_SECURITY_ERROR: {
                                Log.e(TAG, "RESULT_PURCHASES_SECURITY_ERROR");
                                break;
                            }
                            case RESULT_CLIENT_NOT_INIT: {
                                Log.e(TAG, "RESULT_CLIENT_NOT_INIT");
                                break;
                            }
                            case RESULT_BILLING_OK: {
                                Log.e(TAG, "RESULT_BILLING_OK");
                                break;
                            }
                            case RESULT_CONNECTED_NEED_UPDATE: {
                                Log.e(TAG, "RESULT_BILLING_OK");
                                break;
                            }
                            case RESULT_BILLING_REMOTE_ERROR: {
                                Log.e(TAG, "RESULT_BILLING_REMOTE_ERROR");
                                break;
                            }
                            case RESULT_BILLING_SECURITY_ERROR: {
                                Log.e(TAG, "RESULT_BILLING_SECURITY_ERROR");
                                break;
                            }
                            case IAP_ERROR_UNDEFINED_CODE: {
                                Log.e(TAG, "IAP_ERROR_UNDEFINED_CODE");
                                break;
                            }
                        }
                    }
                    break;
                case RechargeResult.STATE_RECHARGE_FAILED:
                    Log.e(TAG, "支付错误" + result.getErrorMsg());
                    break;

            }
        }

    };
}
