package com.speed.faster.activity;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.speed.faster.Content;
import com.speed.faster.core.AppInfo;
import com.speed.faster.core.AppProxyManager;
import com.speed.faster.core.LocalVpnService;
import com.speed.faster.utils.L;
import com.speed.faster.utils.ToastUtils;
import com.speed.faster.R;
import com.speed.faster.utils.WaveView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.speed.faster.Content.START_VPN_SERVICE_REQUEST_CODE;

/**
 * Created by mjm  on 2018/7/6 16:29
 */
public class StartActivity extends BaseActivity implements LocalVpnService.onStatusChangedListener {
    @Bind(R.id.btn_img)
    Button btn_img;
    @Bind(R.id.btn_start)
    Button btn_start;
    @Bind(R.id.btn_end)
    Button btn_end;
    @Bind(R.id.img_load)
    ImageView img_load;
    @Bind(R.id.waveview)
    WaveView waveview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
//        LocalVpnService.addOnStatusChangedListener(this); //注释掉  避免监听打印2次数据
        if (AppProxyManager.isLollipopOrAbove) {
            new AppProxyManager(this);
        } else {
            ((ViewGroup) findViewById(R.id.AppSelectLayout).getParent()).removeView(findViewById(R.id.AppSelectLayout));
            ((ViewGroup) findViewById(R.id.textViewAppSelectLine).getParent()).removeView(findViewById(R.id.textViewAppSelectLine));
        }
        waveview.setDuration(5000);
        waveview.setStyle(Paint.Style.FILL);
        waveview.setColor(Color.parseColor("#5DBDF9"));
        waveview.setInterpolator(new LinearOutSlowInInterpolator());
        waveview.start();
    }

    String aa = "";

    @OnClick({R.id.btn_img, R.id.btn_start, R.id.btn_end})
    public void click(View view) {

        switch (view.getId()) {
            case R.id.btn_start:
                try {
                    jumpActivity(HomeActivity.class);
//                    startVPN();
//                aa=  JwtUtils.generateJwt("E3FBCA22-6C3F-41F9-B67A-2CB2B641DS8B","1375965573",null);
//                JwtUtils.parseJWT("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InpUV0I2S0JxIiwicGFzc3dvcmQiOiJ6bFNxMVNUbiIsImlhdCI6MTUzMTI3MzY4N30.Vrd4cKFi6Xwp-tKkx0ysWd6RU87IjYE3PZaqREqM7-g");
                    L.e("开始");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_end:
                disConnect();
                break;
            case R.id.btn_img:
//                String url = "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1306/25/c0/22546728_1372141722791.jpg";
//                HttpNet.httpImage(url, StartActivity.this, img_load);
                try {
                    String filecontent = "YzC9ZCbbdcR7qKzXLMgWRn0vA9B7fH55RKTJmC0epVooiUWc885juTMVP3TIovlE";
//                String jiami=CXAESUtils.encrypt(Content.AES_SECRET,"majiamin");
//                   String jiami=AES256Encryption.encrypt("majiamin");
//                   L.e("加密---"+jiami);
//                   String decryptString  = AesUtils.decrypt(Content.AES_SECRET,filecontent);//加密后的
//                    String decryptString = AES256Encryption.decrypt(filecontent);//加密后的
//                    L.e("解密---" + decryptString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == START_VPN_SERVICE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                startVPNService();
            } else {
                L.e("canceled");
                ToastUtils.showMessage("canceled");
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    private void disConnect() {
        //断开链接
        LocalVpnService.IsRunning = false;
//        LocalVpnService.Instance.disconnectVPN();
//        stopService(new Intent(StartActivity.this, LocalVpnService.class));
//        System.runFinalization();
//        System.exit(0);
//        ToastUtils.showMessage("已关闭");
        L.e("disConnect----");
    }

    private void startVPN() {
        if (!LocalVpnService.IsRunning) {
            Intent intent = LocalVpnService.prepare(this);
            if (intent == null) {
                startVPNService();
            } else {
                startActivityForResult(intent, START_VPN_SERVICE_REQUEST_CODE);
            }
        }
        L.e("startVPN----");
    }

    private void startVPNService() {
        String ProxyUrl = Content.PROXYURL;
        if (!isValidUrl(ProxyUrl)) {
            Toast.makeText(this, R.string.err_invalid_url, Toast.LENGTH_SHORT).show();
            return;
        }
        //传递url
        LocalVpnService.ProxyUrl = ProxyUrl;
        startService(new Intent(this, LocalVpnService.class));
    }


    //判断url格式是否正确
    public boolean isValidUrl(String url) {
        try {
            if (url == null || url.isEmpty())
                return false;

            if (url.startsWith("ss://")) {//file path
                return true;
            } else { //url
                Uri uri = Uri.parse(url);
                if (!"http".equals(uri.getScheme()) && !"https".equals(uri.getScheme()))
                    return false;
                if (uri.getHost() == null)
                    return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AppProxyManager.isLollipopOrAbove) {
            if (AppProxyManager.Instance.proxyAppInfo.size() != 0) {
                String tmpString = "";
                for (AppInfo app : AppProxyManager.Instance.proxyAppInfo) {
                    tmpString += app.getAppLabel() + ", ";
                }
//                textViewProxyApp.setText(tmpString);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStatusChanged(String status, Boolean isRunning) {
        L.e("onStatusChanged--status--" + status);
        ToastUtils.showMessage("status--" + status);
        onLogReceived(status);
    }

    @Override
    public void onLogReceived(String logString) {
        L.e("logString----" + logString);
    }
}
