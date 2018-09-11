package com.speed.faster.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.speed.faster.Content;
import com.speed.faster.MyApplication;
import com.speed.faster.https.HttpCode;
import com.speed.faster.https.HttpNet;
import com.speed.faster.https.HttpUrl;
import com.speed.faster.https.RequestLinstener;
import com.speed.faster.utils.AesUtils;
import com.speed.faster.utils.L;
import com.speed.faster.utils.SharedUtils;
import com.speed.faster.utils.ToastUtils;

public class MyReceiver extends BroadcastReceiver {
    public static final String TAG = "MyBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        L.e("com.speed.action.broadcast-------------");
        L.e(TAG, "intent:" + intent);
        boolean freshNet = intent.getBooleanExtra("freshNet", false);//true--代表刷新认证页面
        if (freshNet) {
            L.e("更换域名");

            //更换域名之前，需要先取当前使用的域名对其 查询本地 置空
            //存在2个域名不能使用，则重新请求网络，获取新的域名
            String domain1 = SharedUtils.getString("domain1");
            String domain2 = SharedUtils.getString("domain2");
            String domain3 = SharedUtils.getString("domain3");
            boolean Isdomain1 = false;//当前域名是否被删除 true--被删除
            boolean Isdomain2 = false;
            boolean Isdomain3 = false;
            String url = HttpNet.GetRequestUrl();
            L.e("当前域名----" + url);
            if (HttpUrl.DOMAIN1.equals(url)) {
                //第一个域名不能使用
                //使用第二个
                //对第一个域名只为 true  告诉当前域名，不可以使用
                SharedUtils.putBoolean("OneDomain", true);
                HttpNet.SetRequestUrl(HttpUrl.DOMAIN2);
                return;
            }
            if (HttpUrl.DOMAIN2.equals(url)) {
                //第一个域名不能使用
                //第二个也不能使用
                SharedUtils.putBoolean("TwoDomain", true);
            }
            if (url.equals(domain1)) {
                SharedUtils.putString("domain1", "");
            }
            if (url.equals(domain2)) {
                SharedUtils.putString("domain2", "");
            }
            if (url.equals(domain3)) {
                SharedUtils.putString("domain3", "");
            }
            if (domain1 == null || "".equals(domain1)) {
                Isdomain1 = true;
            }
            if (domain2 == null || "".equals(domain2)) {
                Isdomain2 = true;
            }
            if (domain3 == null || "".equals(domain3)) {
                Isdomain3 = true;
            }
            //第一个域名置空了，就判断第二个域名，第二个也置空了，就直接网络请求。
            //第一个么有置空，就对第一个赋值
            if (Isdomain1) {
                if (Isdomain2) {
                    loadDomains();
                } else {
                    HttpNet.SetRequestUrl(domain2);
                }
            } else {
                HttpNet.SetRequestUrl(domain1);
            }
        }
        L.e("设置完域名---" + HttpNet.GetRequestUrl());
    }

    public void loadDomains() {
        HttpNet.httpGetNoHeader(HttpUrl.DOMAIN_ADDRESS, MyApplication.getInstance(), null, new RequestLinstener() {
            @Override
            public void OnSuccess(String responseString) {
                try {
                    String domain = AesUtils.decrypt(responseString);
                    L.e("域名----" + domain);
                    SharedUtils.putString("domain", domain);
                    if (TextUtils.isEmpty(domain)) {
                        ToastUtils.showMessage(HttpCode.HOST_NULL);
                        return;
                    }
                    String aaArray = Content.spltString(domain, 1);
                    String bbArray = Content.spltString(domain, 2);
                    String ccArray = Content.spltString(domain, 3);
                    L.e("aaArray----" + aaArray);
                    L.e("bbArray----" + bbArray);
                    L.e("ccArray----" + ccArray);
                    SharedUtils.putString("domain1", aaArray);
                    SharedUtils.putString("domain2", bbArray);
                    SharedUtils.putString("domain3", ccArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void OnError(String errorString) {
                HttpNet.SetRequestUrl("https://api.marcosfdnd.com/");
                L.e(HttpNet.GetRequestUrl() + "----");
                //次域名被封，则启用新的域名地址
                loadDomainsUrgent();
                ToastUtils.showMessage(errorString);
            }
        });
    }

    public void loadDomainsUrgent() {
        HttpNet.httpGetNoHeader(HttpUrl.DOMAIN_URGENT_ADDRESS, MyApplication.getInstance(), null, new RequestLinstener() {
            @Override
            public void OnSuccess(String responseString) {
                try {
                    String domain = AesUtils.decrypt(responseString);
                    L.e("域名----" + domain);
                    SharedUtils.putString("domain", domain);
                    if (TextUtils.isEmpty(domain)) {
                        ToastUtils.showMessage(HttpCode.HOST_NULL);
                        return;
                    }
                    String aaArray = Content.spltString(domain, 1);
                    String bbArray = Content.spltString(domain, 2);
                    String ccArray = Content.spltString(domain, 3);
                    L.e("aaArray----" + aaArray);
                    L.e("bbArray----" + bbArray);
                    L.e("ccArray----" + ccArray);
                    SharedUtils.putString("domain1", aaArray);
                    SharedUtils.putString("domain2", bbArray);
                    SharedUtils.putString("domain3", ccArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void OnError(String errorString) {
                ToastUtils.showMessage(errorString);
            }
        });
    }
}
