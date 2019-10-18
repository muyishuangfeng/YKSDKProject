package com.silence.sdk.ykcore.common;

import android.util.Log;
import android.util.SparseArray;

import com.silence.sdk.ykcore.exception.YKGameError;
import com.silence.sdk.ykcore.platform.PlatformFactory;
import com.silence.sdk.ykcore.util.YKGameUtil;


public class YKGameSdk {
    public static final String TAG = YKGameSdk.class.getSimpleName();

    // 配置项
    private static YKGameOptions mOptions;
    // platform factory
    private static SparseArray<PlatformFactory> mPlatformFactories;

    /**
     * 初始化
     */
    public static void init(YKGameOptions options) {
        mOptions = options;
        mPlatformFactories = new SparseArray<>();
        // Google平台
        if (mOptions.isGoogleEnable()) {
            addPlatform(Target.PLATFORM_GOOGLE, "com.silence.sdk.ykgoogle.GooglePlatform$Factory");
        }
        // Facebook平台
        if (mOptions.isFacebookEnable()) {
            addPlatform(Target.PLATFORM_FACEBOOK, "com.silence.sdk.ykfacebook.FacebookPlatform$Factory");
        }
        // GooglePlay平台
        if (mOptions.isGooglePlayEnable()) {
            addPlatform(Target.PLATFORM_GOOGLE_PLAY, "com.silence.sdk.ykgoogleplay.GooglePlayPlatform$Factory");
        }
        // oneStore平台
        if (mOptions.isOneStoreEnable()) {
            addPlatform(Target.PLATFORM_ONE_STORE, "com.silence.sdk.ykonestore.OneStorePlatform$Factory");
        }
        // 手机平台
        if (mOptions.isPhoneEnable()) {
            addPlatform(Target.PLATFORM_PHONE, "xx.PhonePlatform$Factory");
        }
        // QQ平台
        if (mOptions.isQqEnable()) {
            addPlatform(Target.PLATFORM_QQ, "xx.QQPlatform$Factory");
        }
        // 微信平台
        if (mOptions.isWxEnable()) {
            addPlatform(Target.PLATFORM_WX, "xx.WxPlatform$Factory");
        }
    }

    /**
     * 获取配置项
     */
    public static YKGameOptions options() {
        if (mOptions == null) {
            throw YKGameError.make(YKGameError.CODE_SDK_INIT_ERROR);
        }
        return mOptions;
    }

    /**
     * 获取构建工厂
     */
    public static SparseArray<PlatformFactory> getPlatformFactories() {
        Log.e(TAG, mPlatformFactories.toString() + "===" + mPlatformFactories.size());
        return mPlatformFactories;
    }


    /**
     * 添加 platform
     *
     * @param factory 平台工厂
     */
    public static void addPlatform(PlatformFactory factory) {
        mPlatformFactories.append(factory.getPlatformTarget(), factory);
    }

    /**
     * 添加平台
     *
     * @param target       目标平台
     * @param factoryClazz
     */
    private static void addPlatform(int target, String factoryClazz) {
        try {
            Object instance = Class.forName(factoryClazz).newInstance();
            if (instance instanceof PlatformFactory) {
                addPlatform((PlatformFactory) instance);
                YKGameUtil.e(TAG, "注册平台 " + target + " ," + instance.getClass().getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
