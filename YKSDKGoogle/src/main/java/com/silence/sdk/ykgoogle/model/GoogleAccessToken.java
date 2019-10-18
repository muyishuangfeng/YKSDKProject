package com.silence.sdk.ykgoogle.model;


import com.silence.sdk.ykcore.common.Target;
import com.silence.sdk.ykcore.model.token.AccessToken;

public class GoogleAccessToken extends AccessToken {

    private String access_token;

    @Override
    public String getAccess_token() {
        return access_token;
    }

    @Override
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @Override
    public int getLoginTarget() {
        return Target.LOGIN_GOOGLE;
    }
}
