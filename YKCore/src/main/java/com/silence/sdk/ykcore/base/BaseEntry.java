package com.silence.sdk.ykcore.base;


public class BaseEntry<T> {


    private int code;
    private String result;
    private String msg;

    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 是否成功
     */
    public boolean isSuccess() {
        return code == 0;
    }

    @Override
    public String toString() {
        return "BaseEntry{" +
                "code=" + code +
                ", result='" + result + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
