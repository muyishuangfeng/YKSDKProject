package com.silence.sdk.ykcore.exception;


import com.silence.sdk.ykcore.util.YKGameUtil;

public class YKGameError extends RuntimeException {

    private static final String TAG = YKGameError.class.getSimpleName();

    public static final int CODE_OK = 1; // 成功
    public static final int CODE_COMMON_ERROR = 101; // 通用错误，未归类
    public static final int CODE_NOT_INSTALL = 102; // 没有安装应用
    public static final int CODE_SDK_ERROR = 109; // sdk 返回错误
    public static final int CODE_REQUEST_ERROR = 110; // 网络请求发生错误
    public static final int CODE_PARAM_ERROR = 114; // 参数错误
    public static final int CODE_SDK_INIT_ERROR = 115; // SocialSdk 初始化错误
    public static final int CODE_PREPARE_BG_ERROR = 116; // 执行 prepareOnBackground 时错误
    public static final int CODE_NOT_SUPPORT = 117; // 不支持
    public static final int CODE_COMPLETE = 118; // 完成
    public static final int CODE_RECHARGE_ERROR = 119; // 支付失败
    public static final int CODE_PARSE_ERROR = 120; // 数据解析失败


    private int code = CODE_OK;
    private String msg = "";
    private Exception error;


    public static YKGameError make(String msg) {
        YKGameError error = new YKGameError();
        error.code = CODE_COMMON_ERROR;
        error.msg = msg;
        return error;
    }

    public static YKGameError make(int code) {
        YKGameError error = new YKGameError();
        error.code = code;
        return error;
    }

    public static YKGameError make(int code, String msg) {
        YKGameError error = new YKGameError();
        error.code = code;
        error.msg = msg;
        return error;
    }

    public static YKGameError make(int code, String msg, Exception exception) {
        YKGameError error = new YKGameError();
        error.code = code;
        error.msg = msg;
        error.error = exception;
        return error;
    }

    private YKGameError() {
    }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void printStackTrace() {
        YKGameUtil.e(TAG, toString());
    }

    public Exception getError() {
        return error;
    }

    public void setError(Exception error) {
        this.error = error;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append("errCode = ").append(code)
                .append(", errMsg = ").append(getMsgByCode()).append("\n");
        if (error != null) {
            sb.append("其他错误 : ").append(error.getMessage());
            error.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 错误信息
     */
    private String getMsgByCode() {
        StringBuilder sb = new StringBuilder(this.msg).append(", ");
        switch (code) {
            case CODE_COMMON_ERROR:
                sb.append("通用错误，未归类");
                break; //
            case CODE_NOT_INSTALL:
                sb.append("没有安装应用");
                break;
            case CODE_SDK_ERROR:
                sb.append("第三方 sdk 返回错误");
                break;
            case CODE_REQUEST_ERROR:
                sb.append("网络请求发生错误");
                break;
            case CODE_PARAM_ERROR:
                sb.append(" 参数错误");
                break;
            case CODE_SDK_INIT_ERROR:
                sb.append("SocialSdk 初始化错误");
                break;
            case CODE_PREPARE_BG_ERROR:
                sb.append("执行 prepareOnBackground 时错误");
                break;
            case CODE_NOT_SUPPORT:
                sb.append("不支持的操作");
                break;
        }
        return sb.toString();
    }

    /**
     * 追加
     */
    public YKGameError append(String msg) {
        this.msg = String.valueOf(this.msg) + " ， " + msg;
        return this;
    }
}
