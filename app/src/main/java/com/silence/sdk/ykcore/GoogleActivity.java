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

public class GoogleActivity extends AppCompatActivity {

    Button mBtnLogin, mBtnLoginOut;
    TextView mTxtResult;
    private static final int REQUEST_CODE = 0x01;
    String TAG = "GoogleActivity";
    String clientID = "443503959733-0vhjo7df08ahd9i7d5lj9mdtt7bahsbq.apps.googleusercontent.com";
    String mAdID;
    private OnLoginStateListener mOnLoginListener;
    String mLtToken, mLtId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);
        initView();
        initData();
    }

    private void initView() {
        mTxtResult = findViewById(R.id.txt_result);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginObject object = new LoginObject();
                object.setmGoogleClient(clientID);
                object.setSelfRequestCode(REQUEST_CODE);
                object.setLoginOut(false);
                LoginManager.login(GoogleActivity.this, Target.LOGIN_GOOGLE, object, mOnLoginListener);
            }
        });

        mBtnLoginOut = findViewById(R.id.btn_loginOut);
        mBtnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginObject object = new LoginObject();
                object.setmGoogleClient(clientID);
                object.setSelfRequestCode(REQUEST_CODE);
                object.setLoginOut(true);
                LoginManager.login(GoogleActivity.this, Target.LOGIN_GOOGLE, object, mOnLoginListener);
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
                switch (result.state) {
                    case LoginResult.STATE_SUCCESS:
                        if (result.getResultModel() != null) {
                            mLtToken = result.getResultModel().getData().getLt_uid_token();
                            mLtId = result.getResultModel().getData().getLt_uid();
                            mTxtResult.setText(mLtToken + "====" + mLtId);
                        }
                        break;
                    case LoginResult.STATE_RESULT:
                        BaseEntry entry = result.getBaseEntry();
                        switch (entry.getCode()) {
                            case YKGameValues.GOOGLE_LOGIN_SUCCESS: {
                                Log.e(TAG, "success=====" + entry.getResult());
                                break;
                            }
                            case YKGameValues.GOOGLE_LOGIN_FAILED: {
                                Log.e(TAG, "failed=====" + entry.getResult());
                                break;
                            }
                        }
                        break;
                    case LoginResult.STATE_LOGIN_OUT:
                        if (result.getError().getMsg() != null) {
                            Toast.makeText(GoogleActivity.this, result.getError().getMsg(), Toast.LENGTH_SHORT).show();
                            mTxtResult.setText(result.getError().getMsg());
                        }
                        break;
                    case LoginResult.STATE_FAIL:
                        if (result.getError() != null) {
                            switch (result.getError().getCode()) {
                                case YKGameError.CODE_PARAM_ERROR: {
                                    Log.e("RESULT123", result.getError().getMsg());
                                    break;
                                }
                                case YKGameError.CODE_REQUEST_ERROR: {
                                    mTxtResult.setText("CODE_REQUEST_ERROR" + result.getError().getMsg());
                                    Log.e("RESULT123", result.getError().getMsg());
                                    break;
                                }
                                case YKGameError.CODE_NOT_SUPPORT: {
                                    mTxtResult.setText("CODE_NOT_SUPPORT" + result.getError().getMsg());
                                    Log.e("RESULT123", result.getError().getMsg());
                                    break;
                                }
                            }
                        }
                        break;

                }
            }

        };
    }

    private void init() {
        YKGameOptions options = new YKGameOptions.Builder(this)
                .debug(true)
                .google(clientID)
                .requestCode(REQUEST_CODE)
                .build();
        YKGameSdk.init(options);
    }
}
