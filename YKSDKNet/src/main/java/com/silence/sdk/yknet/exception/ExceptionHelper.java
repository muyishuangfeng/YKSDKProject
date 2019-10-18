package com.silence.sdk.yknet.exception;

import android.net.ParseException;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.silence.sdk.ykcore.exception.YKGameError;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/*
 * 文件名:    ExceptionHelper
 */
public class ExceptionHelper {

    public static YKGameError handleException(Throwable e) {
        e.printStackTrace();
        YKGameError error;
        if (e instanceof SocketTimeoutException) {//网络超时
            error = YKGameError.make(YKGameError.CODE_REQUEST_ERROR,"Network timeout");
        } else if (e instanceof ConnectException) { //均视为网络错误
            error = YKGameError.make(YKGameError.CODE_REQUEST_ERROR,"Network connect timeout");
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //均视为解析错误
            error = YKGameError.make(YKGameError.CODE_PARSE_ERROR,"Data parse error");
        } else if (e instanceof ApiException) {//服务器返回的错误信息
            error = YKGameError.make(YKGameError.CODE_REQUEST_ERROR,e.getCause().getMessage());
        } else if (e instanceof UnknownHostException) {
            error = YKGameError.make(YKGameError.CODE_REQUEST_ERROR,"Unknown Host Exception");
        } else if (e instanceof IllegalArgumentException) {
            error = YKGameError.make(YKGameError.CODE_NOT_SUPPORT,"IllegalArgumentException");
        } else {//未知错误
            try {
                Log.e("TAG", "错误: " + e.getMessage());
            } catch (Exception e1) {
                Log.e("TAG", "未知错误Debug调试 ");
            }
            error = YKGameError.make(YKGameError.CODE_NOT_SUPPORT,"unknown error");
        }
        return error;
    }

}