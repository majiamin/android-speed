package com.speed.faster.https;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;


import com.speed.faster.utils.L;
import com.speed.faster.utils.SharedUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Created by mjm on 2018/7/10.
 */

public class MyStringCallBack implements Callback {
    final RequestLinstener mListener;
    Context context;
    Handler mHandler;

    public MyStringCallBack(RequestLinstener requestListener, Context context, Handler handler) {
        this.mListener = requestListener;
        this.context = context;
        this.mHandler = handler;
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
     final    int code = response.code();
        L.e("response.code()---" + code);
        //这里回到了主线程
        final String json = response.body().string();
        L.e("jsonString--" + json);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //只要不是200全部是错误码
                    if (code != 200) {
                        mListener.OnError("code：" + code+json);
                        return;
                    }
                    SharedUtils.putString("currentDomain",HttpNet.GetRequestUrl());
                    mListener.OnSuccess(json);
                    call.cancel();
                } catch (Exception e2) {
                    L.e("-----" + e2.getClass());
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //换域名
                    Intent intent22 = new Intent("com.speed.action.broadcast");
                    intent22.putExtra("freshNet", true);
                    context.sendBroadcast(intent22);
                    if (TextUtils.isEmpty(e.getMessage()) == false) {
                        mListener.OnError(HttpCode.FILD);
                        L.e("网络请求出错---" + e.getMessage());
                    } else {
                        mListener.OnError(HttpCode.FILD_NULL);
                        L.e("未知网络错误---" + e.getMessage());
                    }
                } catch (Exception e1) {
                    L.e("onFailure---" + e1.getMessage());
                    e1.printStackTrace();
                }
            }
        });
    }


}
