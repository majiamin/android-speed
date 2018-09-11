package com.speed.faster.https;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.speed.faster.utils.L;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by mjm on 2018/7/10.
 */

public class MyDownFileCallBack implements Callback {
    final RequestLinstener mListener;
    Context context;
    Handler mHandler;

    public MyDownFileCallBack(RequestLinstener requestListener, Context context, Handler handler) {
        this.mListener = requestListener;
        this.context = context;
        this.mHandler = handler;
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
       //这里还是在子线程中运行的
        L.e("response.code()---" + response.code() );
        InputStream is = null;//输入流
        FileOutputStream fos = null;//输出流
        final String json = response.body().string();
        try {
            is = response.body().byteStream();//获取输入流
            long total = response.body().contentLength();//获取文件大小
//            view.setMax(total);//为progressDialog设置大小
            if(is != null){
                Log.d("SettingPresenter", "onResponse: 不为空");
                File file = new File(Environment.getExternalStorageDirectory(),"Earn.apk");// 设置路径
                fos = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int ch = -1;
                int process = 0;
                while ((ch = is.read(buf)) != -1) {
                    fos.write(buf, 0, ch);
                    process += ch;
//                    view.downLoading(process);       //这里就是关键的实时更新进度了！
                }

            }
            fos.flush();
            // 下载完成
            if(fos != null){
                fos.close();
            }
//            view.downSuccess();
        } catch (Exception e) {
//            view.downFial();
            Log.d("SettingPresenter",e.toString());
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
            }
        }

        //这里回到了主线程
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    L.e("jsonString--" + json);
//                    mListener.OnSuccess(json);
//                    call.cancel();
//                } catch (Exception e2) {
//                    L.e("-----"+e2.getClass());
//                    e2.printStackTrace();
//                }
//            }
//        });
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
