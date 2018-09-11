package com.speed.faster.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.speed.faster.MyApplication;
import com.speed.faster.R;
import com.speed.faster.bean.LoginBean;
import com.speed.faster.https.HttpCode;
import com.speed.faster.https.HttpNet;
import com.speed.faster.https.HttpUrl;
import com.speed.faster.https.RequestLinstener;
import com.speed.faster.utils.AesUtils;
import com.speed.faster.utils.L;
import com.speed.faster.utils.SharedUtils;
import com.speed.faster.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mjm  on 2018/7/13 16:09
 * App启动管理
 */
public class ControllerActivity extends BaseActivity {
    @Bind(R.id.img_splash)
    ImageView img_splash;
    public static Activity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
        ButterKnife.bind(this);
        judg();
    }
    public void loadGetorcreate() {
//        LoadDialog.show(SplashActivity.this, "正在加载...");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("platform", "Android");
        HttpNet.httpPost(HttpUrl.GETORCREATE, MyApplication.getInstance(), map, new RequestLinstener() {
            @Override
            public void OnSuccess(String responseString) {
                try {
                    Gson gson = new Gson();
                    LoginBean loginBean = gson.fromJson(responseString, LoginBean.class);
                    L.e("username---" + loginBean.getUsername());
                    L.e("Appid---" + loginBean.getAppId());
                    L.e("Id---" + loginBean.getId());
                    //保存登录对象
                    SharedUtils.saveObject(SharedUtils.LOGIN_BEAN, loginBean);
                    //customerId--下次登录使用
                    SharedUtils.putString("customerId", loginBean.getId() + "");
                    //获取节点
                    loadSnodes();
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

    public void loadSnodes() {
//        LoadDialog.show(SplashActivity.this, "正在加载...");
        HttpNet.httpGet(HttpUrl.SNODES, MyApplication.getInstance(), null, new RequestLinstener() {
            @Override
            public void OnSuccess(String responseString) {
                try {
//                    LoadDialog.dismiss(SplashActivity.this);
                    JSONObject object = new JSONObject(responseString);
                    String secret = object.get("secret").toString();
                    String aa = AesUtils.decrypt(secret);
                    L.e("节点---" + aa);
                    SharedUtils.putString("host", aa);//获取host 保存在本地
//                    jumpActivity(HomeActivity.class);
//                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void OnError(String errorString) {
                ToastUtils.showMessage(HttpCode.HOST_NULL);
//                LoadDialog.dismiss(SplashActivity.this);
            }
        });
    }
    private void judg() {
        jumpActivity(SplashActivity.class);
        finish();
    }
}
