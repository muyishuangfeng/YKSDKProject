package com.silence.sdk.yknet.impl;


import com.silence.sdk.ykcore.exception.YKGameError;

public interface OnAutoCheckLoginListener {

    void onCheckedSuccess(String result);

    void onCheckedFailed(String failed);

    void onCheckedException(YKGameError ex);
}
