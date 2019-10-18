package com.silence.sdk.ykgoogle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.silence.sdk.ykcore.common.Target;
import com.silence.sdk.ykcore.common.YKGameOptions;
import com.silence.sdk.ykcore.common.YKGameSdk;
import com.silence.sdk.ykcore.impl.OnLoginStateListener;
import com.silence.sdk.ykcore.impl.OnRechargeListener;
import com.silence.sdk.ykcore.model.LoginObject;
import com.silence.sdk.ykcore.model.RechargeObject;
import com.silence.sdk.ykcore.platform.AbsPlatform;
import com.silence.sdk.ykcore.platform.IPlatform;
import com.silence.sdk.ykcore.platform.PlatformFactory;
import com.silence.sdk.ykcore.uikit.BaseActionActivity;
import com.silence.sdk.ykcore.util.YKGameUtil;
import com.silence.sdk.ykgoogle.uikit.GoogleLoginActivity;


public class GooglePlatform extends AbsPlatform {

    private GoogleLoginHelper mGoogleHelper;

    private GooglePlatform(Context context,  String clientID, int selfRequestCode, int target) {
        super(context, clientID, selfRequestCode, target);
    }

    /**
     * 工厂
     */
    public static class Factory implements PlatformFactory {


        @Override
        public IPlatform create(Context context, int target) {
            IPlatform platform = null;
            YKGameOptions options = YKGameSdk.options();
            if (!YKGameUtil.isAnyEmpty(options.getGoogleClientID()) && options.getSelfRequestCode() != -1) {
                platform = new GooglePlatform(context, options.getGoogleClientID(), options.getSelfRequestCode(), target);
            }
            return platform;
        }

        @Override
        public int getPlatformTarget() {
            return Target.PLATFORM_GOOGLE;
        }

        @Override
        public boolean checkLoginPlatformTarget(int target) {
            return target == Target.LOGIN_GOOGLE;
        }

        @Override
        public boolean checkRechargePlatformTarget(int target) {
            return false;
        }
    }

    @Override
    public Class getUIKitClazz() {
        return GoogleLoginActivity.class;
    }

    @Override
    public void onActivityResult(BaseActionActivity activity, int requestCode, int resultCode, Intent data) {
        super.onActivityResult(activity, requestCode, resultCode, data);
        mGoogleHelper.onActivityResult(requestCode, data, mGoogleHelper.selfRequestCode);
    }


    @Override
    public void login(Activity activity, int target, LoginObject object, OnLoginStateListener listener) {
        mGoogleHelper = new GoogleLoginHelper(activity, object.getmGoogleClient(),
                object.getSelfRequestCode(), listener);
        if (object.isLoginOut()) {
            mGoogleHelper.loginOut(activity, object.getmGoogleClient());
        } else {
            mGoogleHelper.login();
        }


    }

    @Override
    public void recharge(Activity activity, int target, RechargeObject object, OnRechargeListener listener) {

    }
}
