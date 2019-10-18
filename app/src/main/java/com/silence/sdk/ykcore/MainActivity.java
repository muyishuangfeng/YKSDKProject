package com.silence.sdk.ykcore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.silence.sdk.yknet.Constants;
import com.silence.sdk.yknet.util.PreferencesUtils;

public class MainActivity extends AppCompatActivity {

    Button mBtnGoogle, mBtnGooglePlay, mBtnFacebook, mBtnOneStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        //Log.e("MAIN", PreferencesUtils.getString(this, Constants.USER_API_TOKEN));
        //Log.e("MAIN", PreferencesUtils.getString(this, Constants.USER_LT_UID));
        mBtnFacebook = findViewById(R.id.btn_facebook);
        mBtnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,FacebookActivity.class));
            }
        });
        mBtnGooglePlay = findViewById(R.id.btn_google_play);
        mBtnGooglePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,GooglePlayActivity.class));
            }
        });
        mBtnGoogle = findViewById(R.id.btn_google);
        mBtnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,GoogleActivity.class));
            }
        });
        mBtnOneStore = findViewById(R.id.btn_onestore);
        mBtnOneStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,OneStoreActivity.class));
            }
        });
    }


}
