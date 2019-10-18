package com.silence.sdk.yknet.impl;


public interface OnWeChatAccessTokenListener<T> {

    void onWeChatSuccess(T t);

    void onWeChatFailed(String failed);
}
