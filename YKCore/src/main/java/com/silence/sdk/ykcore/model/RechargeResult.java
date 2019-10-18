package com.silence.sdk.ykcore.model;


import com.silence.sdk.ykcore.base.BaseEntry;
import com.silence.sdk.ykcore.exception.YKGameError;

public class RechargeResult extends Result {

    private RechargeObject rechargeObject;
    private BaseEntry<ResultModel> resultModel;
    private OneStoreResult result;
    private String errorMsg;
    private BaseEntry baseEntry;


    private RechargeResult(int state, RechargeObject shareObj, int target) {
        super(state, target);
        this.rechargeObject = shareObj;
    }

    public RechargeResult(int state) {
        super(state);
    }


    public RechargeResult(int state, BaseEntry<ResultModel> resultModel) {
        super(state, resultModel);
    }

    public RechargeResult(int state, OneStoreResult resultModel) {
        super(state, resultModel);
    }

    public RechargeResult(int state, String resultModel) {
        super(state, resultModel);
    }

    public static RechargeResult startOf(int target, RechargeObject obj) {
        RechargeResult result = new RechargeResult(STATE_RECHARGE_START, obj, target);
        result.rechargeObject = obj;
        return result;
    }

    public static RechargeResult stateOf(BaseEntry baseEntry) {
        RechargeResult result = new RechargeResult(STATE_RECHARGE_RESULT);
        result.baseEntry = baseEntry;
        return result;
    }
    public static RechargeResult stateOf(OneStoreResult oneStoreResult) {
        RechargeResult result = new RechargeResult(STATE_RECHARGE_RESULT, oneStoreResult);
        result.result = oneStoreResult;
        return result;
    }


    public static RechargeResult successOf(int target, RechargeObject obj) {
        RechargeResult result = new RechargeResult(STATE_RECHARGE_SUCCESS, obj, target);
        result.rechargeObject = obj;
        return result;
    }

    public static RechargeResult successOf(BaseEntry<ResultModel> resultModel) {
        RechargeResult result = new RechargeResult(STATE_RECHARGE_SUCCESS, resultModel);
        result.resultModel = resultModel;
        return result;

    }


    public static RechargeResult failOf(BaseEntry<ResultModel> resultModel) {
        RechargeResult result = new RechargeResult(STATE_RECHARGE_FAILED, resultModel);
        result.resultModel = resultModel;
        return result;
    }

    public static RechargeResult failOf(String errorMsg) {
        RechargeResult result = new RechargeResult(STATE_RECHARGE_FAILED, errorMsg);
        result.errorMsg = errorMsg;
        return result;
    }

    public static RechargeResult failOf(int target, RechargeObject obj, YKGameError error) {
        RechargeResult result = new RechargeResult(STATE_RECHARGE_FAILED, obj, target);
        result.error = error;
        return result;
    }

    public static RechargeResult failOf(int target, YKGameError error) {
        RechargeResult result = new RechargeResult(STATE_RECHARGE_FAILED);
        result.error = error;
        return result;
    }
    public static RechargeResult failOf(YKGameError error) {
        RechargeResult result = new RechargeResult(STATE_RECHARGE_FAILED);
        result.error = error;
        return result;
    }

    public static RechargeResult cancelOf(int target, RechargeObject obj) {
        return new RechargeResult(STATE_RECHARGE_CANCEL, obj, target);
    }


    public static RechargeResult completeOf(int target, RechargeObject obj) {
        return new RechargeResult(STATE_RECHARGE_COMPLETE, obj, target);
    }

    public static RechargeResult completeOf(int target) {
        return new RechargeResult(STATE_RECHARGE_COMPLETE);
    }

    public static RechargeResult stateOf(int state, int target, RechargeObject obj) {
        return new RechargeResult(state, obj, target);
    }

    public static RechargeResult stateOf(int state) {
        return new RechargeResult(state);
    }

    public static RechargeResult stateOf(int stateActive, int state) {
        return new RechargeResult(state);
    }

    public RechargeObject getRechargeObject() {
        return rechargeObject;
    }

    public void setRechargeObject(RechargeObject rechargeObject) {
        this.rechargeObject = rechargeObject;
    }

    public BaseEntry<ResultModel> getResultModel() {
        return resultModel;
    }

    public void setResultModel(BaseEntry<ResultModel> resultModel) {
        this.resultModel = resultModel;
    }

    public OneStoreResult getResult() {
        return result;
    }

    public void setResult(OneStoreResult result) {
        this.result = result;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public BaseEntry getBaseEntry() {
        return baseEntry;
    }

    public void setBaseEntry(BaseEntry baseEntry) {
        this.baseEntry = baseEntry;
    }
}
