package com.silence.sdk.ykcore.model;


import com.silence.sdk.ykcore.base.BaseEntry;
import com.silence.sdk.ykcore.exception.YKGameError;
import com.silence.sdk.ykcore.model.token.AccessToken;
import com.silence.sdk.ykcore.model.user.YKGameUser;

public class LoginResult extends Result {

    // 本次登陆的 token 信息，openId, unionId,token,expires_in
    private AccessToken mToken;
    // 返回的基本用户信息
    // 针对登录类型可强转为 WxUser,QQUser 来获取更加丰富的信息
    private YKGameUser mUser;
    // 授权码，如果 onlyAuthCode 为 true, 将会返回它
    public String wxAuthCode;

    private BaseEntry<ResultModel> resultModel;
    private BaseEntry baseEntry;
    private YKGameError error;
    // 扫码登录二维码文件路径
    public String wxCodePath;


    public LoginResult(int state, int target) {
        super(state, target);
    }

    public LoginResult(int state) {
        super(state, -1);
    }

    public LoginResult(int state, BaseEntry<ResultModel> resultModel) {
        super(state, resultModel);
    }


    public static LoginResult stateOf(BaseEntry baseEntry) {
        LoginResult result = new LoginResult(STATE_RESULT);
        result.baseEntry = baseEntry;
        return result;
    }


    public static LoginResult successOf(int target, YKGameUser baseUser, AccessToken baseToken) {
        LoginResult result = new LoginResult(STATE_SUCCESS, target);
        result.mUser = baseUser;
        result.mToken = baseToken;
        return result;
    }

    public static LoginResult successOf(int target, String wxAuthCode) {
        LoginResult result = new LoginResult(STATE_SUCCESS, target);
        result.wxAuthCode = wxAuthCode;
        return result;
    }

    public static LoginResult successOf(int target, BaseEntry<ResultModel> resultModel) {
        LoginResult result = new LoginResult(STATE_SUCCESS, target);
        result.resultModel = resultModel;
        return result;
    }

    public static LoginResult successOf(BaseEntry<ResultModel> resultModel) {
        LoginResult result = new LoginResult(STATE_SUCCESS);
        result.resultModel = resultModel;
        return result;
    }

    public static LoginResult rechargeSuccessOf(BaseEntry<ResultModel> resultModel) {
        LoginResult result = new LoginResult(STATE_RECHARGE_SUCCESS);
        result.resultModel = resultModel;
        return result;
    }

    public static LoginResult successOf(int target) {
        return new LoginResult(STATE_SUCCESS, target);
    }

    public static LoginResult failOf(int target, YKGameError error) {
        LoginResult result = new LoginResult(STATE_FAIL, target);
        result.error = error;
        return result;
    }

    public static LoginResult failOf(int target, BaseEntry<ResultModel> error) {
        LoginResult result = new LoginResult(STATE_FAIL, target);
        result.resultModel = error;
        return result;
    }

    public static LoginResult failOf(YKGameError error) {
        LoginResult result = new LoginResult(STATE_FAIL);
        result.error = error;
        return result;
    }

    public static LoginResult loginOut(YKGameError error) {
        LoginResult result = new LoginResult(STATE_LOGIN_OUT);
        result.error = error;
        return result;
    }

    public static LoginResult cancelOf(int target) {
        return new LoginResult(STATE_CANCEL, target);
    }

    public static LoginResult cancelOf() {
        return new LoginResult(STATE_CANCEL);
    }

    public static LoginResult completeOf(int target) {
        return new LoginResult(STATE_COMPLETE, target);
    }

    public static LoginResult stateOf(int state, int target) {
        return new LoginResult(state, target);
    }


    public static LoginResult stateOf(int state) {
        return new LoginResult(state);
    }


    public BaseEntry<ResultModel> getResultModel() {
        return resultModel;
    }

    public BaseEntry getBaseEntry() {
        return baseEntry;
    }

    public AccessToken getmToken() {
        return mToken;
    }

    public YKGameUser getmUser() {
        return mUser;
    }

    public String getWxAuthCode() {
        return wxAuthCode;
    }

    public YKGameError getError() {
        return error;
    }


    @Override
    public String toString() {
        return "LoginResult{" +
                "mToken=" + mToken +
                ", mUser=" + mUser +
                ", wxAuthCode='" + wxAuthCode + '\'' +
                ", resultModel=" + resultModel +
                ", baseEntry=" + baseEntry +
                ", error=" + error +
                ", state=" + state +
                ", target=" + target +
                ", error=" + error +
                ", result=" + result +
                '}';
    }
}
