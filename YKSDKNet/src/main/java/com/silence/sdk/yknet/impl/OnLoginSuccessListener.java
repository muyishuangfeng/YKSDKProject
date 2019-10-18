package com.silence.sdk.yknet.impl;


import com.silence.sdk.ykcore.base.BaseEntry;

public interface OnLoginSuccessListener<T> {

    void onSuccess(BaseEntry<T> result);

    void onFailed(BaseEntry<T> failed);
}
