package com.silence.sdk.ykcore.impl;

import android.app.Activity;

import com.silence.sdk.ykcore.model.RechargeResult;


public interface OnRechargeListener {

    void onState(Activity activity, RechargeResult result);
}
