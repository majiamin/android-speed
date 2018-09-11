package com.speed.faster.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.speed.faster.R;
import com.speed.faster.bean.AppManagerBean;
import com.speed.faster.https.HttpCode;
import com.speed.faster.utils.AuthorizationUtils;
import com.speed.faster.utils.L;
import com.speed.faster.utils.SharedUtils;
import com.speed.faster.utils.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.List;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by mjm  on 2018/7/11 16:59
 * Vip页面
 */
public class VipActivity extends BaseActivity {
    @Bind(R.id.webView)
    WebView webView;
    boolean flag = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            //存在
            L.e("webView.getUrl()--" + webView.getUrl());
            webView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        } else {
            Intent it = new Intent();
            setResult(RESULT_OK, it);
            finish();
            L.e("返回键");
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip);
        ButterKnife.bind(this);
        init();
    }

    public void init(){
        WebSettings webSettings = webView.getSettings();
        JSInterface JSInterface = new JSInterface();
        webView.addJavascriptInterface(JSInterface, "JsInteration");
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);//关键点
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDisplayZoomControls(false);//隐藏webview缩放按钮
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom(true); // 支持缩放
//        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        AppManagerBean appManagerBean = SharedUtils.getObject(SharedUtils.APP_BEAN);
        if (!TextUtils.isEmpty(appManagerBean.getApp().getMeta().getBuyUrl())) {
            webView.loadUrl(appManagerBean.getApp().getMeta().getBuyUrl());
        } else {
            ToastUtils.showMessage("error");
        }
        webView.setWebChromeClient(new WebChromeClient());//
        webView.setWebViewClient(new WebViewClient() {
                                     public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                                         googleSSl(handler);
                                     }

                                     @Override
                                     public void onPageFinished(WebView view, String url) {
                                         super.onPageFinished(view, url);
                                         if (!flag) {
                                             String aa = AuthorizationUtils.AuthorizationJwt(VipActivity.this);
                                             webView.loadUrl("javascript:javaCalljs('" + aa + "')");
                                             flag = true;
                                         }
                                         L.e("onPageFinished-url---" + url);
                                     }

                                     @Override
                                     public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                         super.onPageStarted(view, url, favicon);
                                         //alipays://platformapi/startApp?appId=20000125&orderSuffix=h5_route_token%3D%22RZ24vPzqPfYso9GjATS4INcUpRJfOamobilecashierRZ24%22%26is_h5_route%3D%22true%22#Intent;scheme=alipays;package=com.eg.android.AlipayGphone;end
                                         String AlipayUrl = url.replace("alipays://", "intent://");
                                         L.e("onPageStarted---AlipayUrl---" + AlipayUrl);
                                         skipScheme(VipActivity.this, AlipayUrl);

                                     }

                                     @Override
                                     public boolean shouldOverrideUrlLoading(WebView view, String newurl) {
                                         try {
                                             //处理intent协议
                                             if (newurl.startsWith("intent://")) {
                                                 Intent intent;
                                                 try {
                                                     intent = Intent.parseUri(newurl, Intent.URI_INTENT_SCHEME);
                                                     intent.addCategory("android.intent.category.BROWSABLE");
                                                     intent.setComponent(null);
                                                     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                                                         intent.setSelector(null);
                                                     }
                                                     List<ResolveInfo> resolves = getPackageManager().queryIntentActivities(intent, 0);
                                                     if (resolves.size() > 0) {
                                                         startActivityIfNeeded(intent, -1);
                                                     }
                                                     return true;
                                                 } catch (URISyntaxException e) {
                                                     e.printStackTrace();
                                                 }
                                             }
                                             // 处理自定义scheme协议
                                             if (!newurl.startsWith("http")) {
                                                 L.e("处理自定义scheme-->" + newurl);
                                                 try {
                                                     // 以下固定写法
                                                     final Intent intent = new Intent(Intent.ACTION_VIEW,
                                                             Uri.parse(newurl));
                                                     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                                             | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                     startActivity(intent);
                                                 } catch (Exception e) {
                                                     // 防止没有安装的情况
                                                     e.printStackTrace();
                                                     ToastUtils.showMessage(HttpCode.ZHIFUBAO_FAIL);
                                                 }
                                                 return true;
                                             }
                                         } catch (Exception e) {
                                             e.printStackTrace();
                                         }

                                         return super.shouldOverrideUrlLoading(view, newurl);
                                     }

                                 }
        );
    }
    public void googleSSl(SslErrorHandler handler){
        final SslErrorHandler mHandler;
        mHandler = handler;
        AlertDialog.Builder builder = new AlertDialog.Builder(VipActivity.this);
        builder.setMessage(HttpCode.SSL_VER);
        builder.setPositiveButton(HttpCode.GO_ON, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mHandler.proceed();
            }
        });
        builder.setNegativeButton(HttpCode.CANCEL, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mHandler.cancel();
            }
        });
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    mHandler.cancel();
                    dialog.dismiss();
                    return true;
                }
                return false;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public static boolean skipScheme(Context context, String newurl) {
        if (TextUtils.isEmpty(newurl) || !newurl.contains("scheme")) {
            L.e("------空-------");
            return false;
        }
        L.e("skipScheme处理自定义scheme-->" + newurl);
        DealedUrl dealedUrl = dealUrl(newurl);
        final String finalParams = dealedUrl.params;
        if (TextUtils.isEmpty(finalParams)) return false;
        TreeMap<String, String> treeMap = getMapFromString(finalParams);
        if (!treeMap.containsKey("scheme")) return false;
        String schemeUrl = treeMap.get("scheme");
        try {
            schemeUrl = URLDecoder.decode(schemeUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            // 以下固定写法
            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(schemeUrl));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            List<ResolveInfo> resolves = context.getPackageManager().queryIntentActivities(intent, 0);
            if (resolves.size() > 0) {
                ((Activity) context).startActivityIfNeeded(intent, -1);
            }
        } catch (Exception e) {
            // 防止没有安装的情况
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static class DealedUrl {
        public String url;
        public String params;
    }

    public static DealedUrl dealUrl(String url) {
        DealedUrl dealedUrl = new DealedUrl();
        if (!url.contains("?")) {
            dealedUrl.url = url;
            dealedUrl.params = "";
            return dealedUrl;
        }
        String params = url.substring(url.indexOf("?") + 1);
        dealedUrl.url = url.substring(0, url.indexOf("?"));
        String[] results = params.split("&");
        StringBuilder specialParams = new StringBuilder();//该url特有参数
        for (String str : results) {
            if (str.split("=").length != 2) {
                continue;
            }
            String key = str.split("=")[0];
            specialParams.append(str).append("&");
        }
        if (specialParams.length() > 0) {
            specialParams.deleteCharAt(specialParams.length() - 1);
        }
        dealedUrl.params = specialParams.toString();
        return dealedUrl;
    }

    public static TreeMap<String, String> getMapFromString(String data) {
        TreeMap<String, String> reqMap = new TreeMap<>();
        if (TextUtils.isEmpty(data)) {
            return reqMap;
        }
        String[] array = data.split("&");
        for (String entry : array) {
            String[] parts = entry.split("=");
            if (parts.length < 2) {
                continue;
            }
            reqMap.put(parts[0], parts[1]);
        }
        return reqMap;
    }

    @OnClick({R.id.back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back:
                Intent it = new Intent();
                setResult(RESULT_OK, it);
                finish();
                break;
        }
    }

    class JSInterface {
        @JavascriptInterface
        public void CloseSesame() {

        }
    }
}
