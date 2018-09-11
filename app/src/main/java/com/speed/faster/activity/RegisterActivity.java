package com.speed.faster.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.speed.faster.R;
import com.speed.faster.bean.LoginBean;
import com.speed.faster.https.HttpCode;
import com.speed.faster.https.HttpNet;
import com.speed.faster.https.HttpUrl;
import com.speed.faster.https.RequestLinstener;
import com.speed.faster.utils.L;
import com.speed.faster.utils.LoadDialog;
import com.speed.faster.utils.SharedUtils;
import com.speed.faster.utils.StringUtils;
import com.speed.faster.utils.ToastUtils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mjm  on 2018/7/13 16:09
 * 注册页面
 */
public class RegisterActivity extends BaseActivity {
    @Bind(R.id.et_register_email)
    EditText et_register_email;
    @Bind(R.id.et_register_pass)
    EditText et_register_pass;
    @Bind(R.id.et_register_pass2)
    EditText et_register_pass2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        //切换动画
        Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.fade);
        getWindow().setEnterTransition(explode);
        //退出时使用
        getWindow().setExitTransition(explode);
    }

    @OnClick({R.id.back, R.id.btn_register})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back:
//                finish();
                finishAfterTransition();
                break;
            case R.id.btn_register:
                loadConfig();
                break;
        }
    }

    public void loadConfig() {
        final String email = et_register_email.getText().toString();
        final String pass = et_register_pass.getText().toString();
        final String pass2 = et_register_pass2.getText().toString();
        if (TextUtils.isEmpty(email)) {
            ToastUtils.showMessage(HttpCode.EAMIL_NULL);
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            ToastUtils.showMessage(HttpCode.PASS_NULL);
            return;
        }
        if (!StringUtils.checkPassWord(pass)) {
            ToastUtils.showMessage(HttpCode.PASS_FORMATEL);
            return;
        }
        if (!pass2.equals(pass)) {
            ToastUtils.showMessage(HttpCode.TWO_NO_IDENTICAL);
            return;
        }
        LoadDialog.show(RegisterActivity.this, HttpCode.LOADING);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("email", email);
        map.put("password", pass);
        HttpNet.httpPut(HttpUrl.BIND, RegisterActivity.this, map, new RequestLinstener() {
            @Override
            public void OnSuccess(String responseString) {
                loadLogin(email, pass2);
            }

            @Override
            public void OnError(String errorString) {
                LoadDialog.dismiss(RegisterActivity.this);
                ToastUtils.showMessage(errorString);
            }
        });


    }

    private void loadLogin(String username, String pass) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        map.put("password", pass);
        HttpNet.httpPost(HttpUrl.LOGIN, RegisterActivity.this, map, new RequestLinstener() {
            @Override
            public void OnSuccess(String responseString) {
                LoadDialog.dismiss(RegisterActivity.this);
                Gson gson = new Gson();
                LoginBean loginBean = gson.fromJson(responseString, LoginBean.class);
                L.e("username---" + loginBean.getUsername());
                L.e("Appid---" + loginBean.getAppId());
                L.e("Id---" + loginBean.getId());
                //保存登录对象
                SharedUtils.saveObject(SharedUtils.LOGIN_BEAN, loginBean);
                //customerId--下次登录使用
                SharedUtils.putString("customerId", loginBean.getId() + "");
                jumpActivity(HomeActivity.class);
                //关闭之前的页面
                if (SplashActivity.instance != null) {
                    SplashActivity.instance.finish();
                }
                if (LoginActivity.instance != null) {
                    LoginActivity.instance.finish();
                }
                finish();
            }

            @Override
            public void OnError(String errorString) {
                LoadDialog.dismiss(RegisterActivity.this);
                ToastUtils.showMessage(errorString);
            }
        });
    }
}
