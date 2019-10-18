package com.silence.sdk.ykonestore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.silence.sdk.ykcore.common.Target;
import com.silence.sdk.ykcore.common.YKGameOptions;
import com.silence.sdk.ykcore.common.YKGameSdk;
import com.silence.sdk.ykcore.impl.OnRechargeListener;
import com.silence.sdk.ykcore.model.RechargeObject;
import com.silence.sdk.ykcore.platform.AbsPlatform;
import com.silence.sdk.ykcore.platform.IPlatform;
import com.silence.sdk.ykcore.platform.PlatformFactory;
import com.silence.sdk.ykcore.uikit.BaseActionActivity;
import com.silence.sdk.ykcore.util.YKGameUtil;
import com.silence.sdk.ykonestore.uikit.OneStoreActivity;

import java.util.Map;

public class OneStorePlatform extends AbsPlatform {

    private OneStoreHelper mHelper;

    private OneStorePlatform(Context context, int payTest, String publicKey,
                             int selfRequestCode, String sku, String productID, String payType, int target) {
        super(context, payTest, publicKey, selfRequestCode, sku, productID, payType, target);
    }

    @Override
    public void recharge(Activity activity, int target, RechargeObject object, OnRechargeListener listener) {
        mHelper = new OneStoreHelper(activity, object.getPublicKey(), object.getUserToken(), object.getPayTest(), object.getSku(),
                object.getGoodsID(), object.getmGoodsType(), object.getSelfRequestCode(), object.getOrderID(), listener);
        mHelper.initOneStore(listener);
    }

    @Override
    public void onActivityResult(BaseActionActivity activity, int requestCode, int resultCode, Intent data) {
        mHelper.onActivityResult(requestCode, resultCode, data, mHelper.mRequestCode);
    }

    @Override
    public Class getUIKitClazz() {
        return OneStoreActivity.class;
    }

    @Override
    public void recycle() {
        if (mHelper != null) {
            mHelper.release();
        }
    }

    /**
     * 工厂
     */
    public static class Factory implements PlatformFactory {

        @Override
        public IPlatform create(Context context, int target) {
            IPlatform platform = null;
            YKGameOptions options = YKGameSdk.options();
            if (!YKGameUtil.isAnyEmpty(
                    options.getSku(), options.getGoodsID(), options.getmPublicKey(),
                    options.getGoodsType()) &&
                    options.getmPayTest() != -1 && options.getSelfRequestCode() != -1) {
                platform = new OneStorePlatform(context, options.getmPayTest(), options.getmPublicKey(), options.getSelfRequestCode(),
                        options.getSku(), options.getGoodsID(), options.getGoodsType(), target);
            }
            return platform;
        }

        @Override
        public int getPlatformTarget() {
            return Target.PLATFORM_ONE_STORE;
        }

        @Override
        public boolean checkLoginPlatformTarget(int target) {
            return false;
        }

        @Override
        public boolean checkRechargePlatformTarget(int target) {
            return target == Target.RECHARGE_ONE_STORE;
        }
    }
}
