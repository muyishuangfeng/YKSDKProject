package com.silence.sdk.ykcore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.silence.sdk.ykcore.base.BaseEntry;
import com.silence.sdk.ykcore.common.Target;
import com.silence.sdk.ykcore.common.YKGameOptions;
import com.silence.sdk.ykcore.common.YKGameSdk;
import com.silence.sdk.ykcore.exception.YKGameError;
import com.silence.sdk.ykcore.impl.OnLoginStateListener;
import com.silence.sdk.ykcore.manager.LoginManager;
import com.silence.sdk.ykcore.model.LoginObject;
import com.silence.sdk.ykcore.model.LoginResult;
import com.silence.sdk.ykcore.model.YKGameValues;
import com.silence.sdk.ykcore.util.DeviceUtils;

import java.util.concurrent.Executors;

public class FacebookActivity extends AppCompatActivity {


    Button mBtnStart, mBtnLoginOut;
    TextView mTxtResult;
    String TAG = "FacebookActivity";
    String mAdID;
    String mFacebookId = "347759105952557";
    private OnLoginStateListener mOnLoginListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        initView();
        initData();
    }

    private void initView() {
        mTxtResult = findViewById(R.id.txt_result);
        mBtnLoginOut = findViewById(R.id.btn_loginOut);
        mBtnStart = findViewById(R.id.btn_login);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginObject object = new LoginObject();
                object.setFacebookAppID(mFacebookId);
                object.setLoginOut(false);
                LoginManager.login(FacebookActivity.this, Target.LOGIN_FACEBOOK, object, mOnLoginListener);

            }
        });
        mBtnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginObject object = new LoginObject();
                object.setFacebookAppID(mFacebookId);
                object.setLoginOut(true);
                LoginManager.login(FacebookActivity.this, Target.LOGIN_FACEBOOK, object, mOnLoginListener);

            }
        });
    }


    /**
     * 初始化数据
     */
    private void initData() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mAdID = DeviceUtils.getGoogleAdId(getApplicationContext());
                    if (!TextUtils.isEmpty(mAdID)) {
                        init();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mOnLoginListener = new OnLoginStateListener() {
            @Override
            public void onState(Activity activity, LoginResult result) {
                if (result.state == LoginResult.STATE_RESULT) {
                    BaseEntry entry = result.getBaseEntry();
                    switch (entry.getCode()) {
                        case YKGameValues.FACEBOOK_LOGIN_SUCCESS: {
                            Log.e(TAG, "success===" + entry.getResult());
                            break;
                        }
                        case YKGameValues.FACEBOOK_LOGIN_FAILED: {
                            Log.e(TAG, "failed===" + entry.getResult());
                            break;
                        }
                        case YKGameValues.FACEBOOK_LOGIN_CANCEL: {
                            Log.e(TAG, "cancel===" + entry.getResult());
                            break;
                        }
                        case YKGameValues.FACEBOOK_LOGIN_OUT: {
                            Log.e(TAG, "loginOut===" + entry.getResult());
                            break;
                        }
                    }
                }

            }

        };
    }

    private void init() {
        YKGameOptions options = new YKGameOptions.Builder(this)
                .debug(true)
                .facebookEnable()
                .facebook(mFacebookId)
                .build();
        YKGameSdk.init(options);
    }


}
