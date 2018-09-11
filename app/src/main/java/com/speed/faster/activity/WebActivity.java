package com.speed.faster.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.speed.faster.R;
import com.speed.faster.https.HttpCode;
import com.speed.faster.utils.L;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mjm  on 2018/7/13 16:09
 * 网页页面
 */

public class WebActivity extends BaseActivity {
    @Bind(R.id.webView)
    WebView webView;

    @Bind(R.id.title)
    TextView title;
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        //切换动画
        Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.fade);
        getWindow().setEnterTransition(explode);
        //退出时使用
        getWindow().setExitTransition(explode);
        type = getIntent().getStringExtra("type");
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
        webView.loadUrl("https://stablevpn.quora.com");
        if ("secrey".equals(type)) {
            //隐私服务
            title.setText(HttpCode.PRIVACY_SERVICE);

//            webView.loadUrl("http://static.trillojoseph.top/PrivacyPolicy.html");
        } else if ("service".equals(type)) {
            //服务条款
            title.setText(HttpCode.TERMS_SERVICE);
//            webView.loadUrl("http://static.trillojoseph.top/TermsofService.html");
        }
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                final SslErrorHandler mHandler;
                mHandler = handler;
                AlertDialog.Builder builder = new AlertDialog.Builder(WebActivity.this);
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

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                L.e("---------onPageFinished");
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                L.e("---------onPageStarted");

            }
        } );
    }

    @OnClick({R.id.back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back:
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
