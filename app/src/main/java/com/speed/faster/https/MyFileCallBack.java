package com.speed.faster.https;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.speed.faster.utils.L;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by mjm on 2018/7/10.
 */

public class MyFileCallBack implements Callback {
    final RequestLinstener mListener;
    Context context;
    Handler mHandler;

    public MyFileCallBack(RequestLinstener requestListener, Context context, Handler handler) {
        this.mListener = requestListener;
        this.context = context;
        this.mHandler = handler;
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
       //这里还是在子线程中运行的
        L.e("response.code()---" + response.code() );
        final String json = response.body().string();
        //这里回到了主线程
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    L.e("jsonString--" + json);
                    mListener.OnSuccess(json);
                    call.cancel();
                } catch (Exception e2) {
                    L.e("-----"+e2.getClass());
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
                    if (TextUtils.isEmpty(e.getMessage()) == false) {
                        mListener.OnError("网络请求出错！");
                        L.e("网络请求出错---" + e.getMessage());
                    } else {
                        mListener.OnError("未知网络错误！");
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
