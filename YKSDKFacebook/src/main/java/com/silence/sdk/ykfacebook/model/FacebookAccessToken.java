package com.silence.sdk.ykfacebook.model;


import com.silence.sdk.ykcore.common.Target;
import com.silence.sdk.ykcore.model.token.AccessToken;

public class FacebookAccessToken extends AccessToken {


    private String access_token;

    @Override
    public int getLoginTarget() {
        return Target.LOGIN_FACEBOOK;
    }

    @Override
    public String getAccess_token() {
        return access_token;
    }

    @Override
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
