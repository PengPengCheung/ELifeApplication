package com.pengpeng.elifecore;

/**
 * Created by pengpeng on 16-1-13.
 */
public interface ResponseCallbackListener<T> {
    public void onSuccess(T data);
    public void onFailure(String error_code, String reason);

}
