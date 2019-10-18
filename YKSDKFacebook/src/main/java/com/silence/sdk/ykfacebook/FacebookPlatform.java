package com.silence.sdk.ykfacebook;

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
import com.silence.sdk.ykfacebook.uikit.FacebookActionActivity;


public class FacebookPlatform extends AbsPlatform {

    private static final String TAG = FacebookPlatform.class.getSimpleName();
    private FacebookLoginHelper mLoginHelper;


    private FacebookPlatform(Context context, int target) {
        super(context, target);
    }


    public static class Factory implements PlatformFactory {

        @Override
        public IPlatform create(Context context, int target) {
            IPlatform platform = null;
            YKGameOptions options = YKGameSdk.options();
            if (!YKGameUtil.isAnyEmpty(options.getFacebookAppID())) {
                platform = new FacebookPlatform(context, target);
            }
            return platform;
        }

        @Override
        public int getPlatformTarget() {
            return Target.PLATFORM_FACEBOOK;
        }

        @Override
        public boolean checkLoginPlatformTarget(int target) {
            return target == Target.LOGIN_FACEBOOK;
        }

        @Override
        public boolean checkRechargePlatformTarget(int target) {
            return false;
        }
    }

    @Override
    public Class getUIKitClazz() {
        return FacebookActionActivity.class;
    }

    @Override
    public void onActivityResult(BaseActionActivity activity, int requestCode, int resultCode, Intent data) {
        super.onActivityResult(activity, requestCode, resultCode, data);
        mLoginHelper.setOnActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void login(Activity activity, int target, LoginObject object, OnLoginStateListener listener) {
        mLoginHelper = new FacebookLoginHelper(activity, listener, target);
        mLoginHelper.login(object.getFacebookAppID(), activity, object.isLoginOut());

    }

    @Override
    public void recharge(Activity activity, int target, RechargeObject object, OnRechargeListener listener) {

    }
}
