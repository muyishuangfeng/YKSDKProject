package com.silence.sdk.ykgoogle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.silence.sdk.ykcore.base.BaseEntry;
import com.silence.sdk.ykcore.common.Target;
import com.silence.sdk.ykcore.impl.OnLoginStateListener;
import com.silence.sdk.ykcore.model.LoginResult;
import com.silence.sdk.ykcore.model.YKGameValues;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;


class GoogleLoginHelper {
    private int mLoginTarget;
    private WeakReference<Activity> mActivityRef;
    private OnLoginStateListener mListener;
    private String clientID;
    public int selfRequestCode;

    GoogleLoginHelper(Activity activity, String clientID,
                      int selfRequestCode, OnLoginStateListener listener) {
        this.mActivityRef = new WeakReference<>(activity);
        this.clientID = clientID;
        this.selfRequestCode = selfRequestCode;
        this.mListener = listener;
        this.mLoginTarget = Target.LOGIN_GOOGLE;
    }


    /**
     * 登录
     */
    void login() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientID)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(mActivityRef.get(), gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        mActivityRef.get().startActivityForResult(signInIntent, selfRequestCode);
    }

    /**
     * 登录回调
     */
    void onActivityResult(int requestCode, Intent data, int selfRequestCode) {
        if (requestCode == selfRequestCode) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                String idToken = account.getIdToken();
                if (!TextUtils.isEmpty(idToken)) {
                    BaseEntry entry = new BaseEntry();
                    entry.setCode(YKGameValues.GOOGLE_LOGIN_SUCCESS);
                    entry.setResult(idToken);
                    mListener.onState(mActivityRef.get(), LoginResult.stateOf(entry));
                } else {
                    BaseEntry entry = new BaseEntry();
                    entry.setCode(YKGameValues.GOOGLE_LOGIN_FAILED);
                    entry.setResult("Google user token is empty");
                    mListener.onState(mActivityRef.get(), LoginResult.stateOf(entry));
                }
            } else {
                BaseEntry entry = new BaseEntry();
                entry.setCode(YKGameValues.GOOGLE_LOGIN_FAILED);
                entry.setResult("Google account is empty");
                mListener.onState(mActivityRef.get(), LoginResult.stateOf(entry));
            }

        } catch (ApiException e) {
            BaseEntry entry = new BaseEntry();
            entry.setCode(YKGameValues.GOOGLE_LOGIN_FAILED);
            entry.setResult(e.toString());
            mListener.onState(mActivityRef.get(), LoginResult.stateOf(entry));
            e.printStackTrace();
        }
    }

    /**
     * 退出登录
     */
    void loginOut(Context context, String clientID) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientID)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        mGoogleSignInClient.signOut().addOnCompleteListener((Activity) context, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                BaseEntry entry = new BaseEntry();
                entry.setCode(YKGameValues.GOOGLE_LOGIN_OUT);
                entry.setResult("Google loginOut");
                mListener.onState(mActivityRef.get(), LoginResult.stateOf(entry));
                mActivityRef.get().finish();
            }
        });
    }

}
