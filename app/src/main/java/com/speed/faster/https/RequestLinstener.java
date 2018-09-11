package com.speed.faster.https;

/**
 * Created by mjm on 2018/7/10.
 */

public abstract class RequestLinstener {
    // 失败
    public abstract void OnSuccess(String responseString);
    // 成功
    public abstract void OnError(String errorString);

}
