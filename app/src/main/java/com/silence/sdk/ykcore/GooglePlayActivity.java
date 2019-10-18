package com.silence.sdk.ykcore;

import androidx.appcompat.app.AppCompatActivity;

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
import com.silence.sdk.ykgoogleplay.util.Purchase;

import java.util.Map;
import java.util.WeakHashMap;

public class GooglePlayActivity extends AppCompatActivity {

    Button mBtnPay;
    TextView mTxtResult;
    private static final String TAG = GooglePlayActivity.class.getSimpleName();
    String base64EncodedPublicKey;
    String LTAppKey = "q2h75rE8MW3fOVed82muf5w8dkBfXiSG";
    String LTAppID = "20003";
    String packageName = "com.gnetop.sdk.demo";
    private static final int selfRequestCode = 0x01;
    private String mGoodsID = "33";
    String productID = "com.gnetop.one";
    String baseUrl = "http://sdk.aktgo.com";
    Map<String, Object> params = new WeakHashMap<>();
    private String mUserToken="123456";
    private String mOrderID="1111111";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_play);
        initView();
        init();
    }

    private void initView() {
        base64EncodedPublicKey = getResources().getString(R.string.ltgame_google_iab_key);
        params.put("key", "123");
        mTxtResult = findViewById(R.id.txt_pay_result);
        mBtnPay = findViewById(R.id.btn_pay);
        mBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeObject result = new RechargeObject();
                result.setUserToken(mUserToken);
                result.setOrderID(mOrderID);
                result.setSku(productID);
                result.setGoodsID(mGoodsID);
                result.setPublicKey(base64EncodedPublicKey);
                result.setPayTest(0);
                RechargeManager.recharge(GooglePlayActivity.this, Target.RECHARGE_GOOGLE,
                        result, mOnRechargeListener);
            }
        });
    }

    private void init() {
        YKGameOptions options = new YKGameOptions.Builder(this)
                .debug(true)
                .publicKey(base64EncodedPublicKey)
                .payTest(0)
                .goodsID(productID, mGoodsID)
                .googlePlay(true)
                .requestCode(selfRequestCode)
                .build();
        YKGameSdk.init(options);
    }

    OnRechargeListener mOnRechargeListener = new OnRechargeListener() {
        @Override
        public void onState(Activity activity, RechargeResult result) {
            switch (result.state) {
                case RechargeResult.STATE_RECHARGE_RESULT:
                    BaseEntry entry = result.getBaseEntry();
                    switch (entry.getCode()) {
                        case YKGameValues.GOOGLE_PLAY_SUCCESS: {
                            Log.e(TAG, entry.getResult());
                            break;
                        }
                        case YKGameValues.GOOGLE_PLAY_SUPPLEMENTS: {
                            Purchase purchase = (Purchase) entry.getData();
                            Log.e(TAG, "补单"+purchase.toString());
                            break;
                        }
                    }
                    break;
                case RechargeResult.STATE_RECHARGE_START:
                    mTxtResult.setText("开始支付");
                    Log.e(TAG, "开始支付");
                    break;

            }
        }
    };
}
