package com.speed.faster.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.speed.faster.Content;
import com.speed.faster.R;
import com.speed.faster.bean.LoginBean;
import com.speed.faster.https.HttpCode;
import com.speed.faster.https.HttpNet;
import com.speed.faster.https.HttpUrl;
import com.speed.faster.https.RequestLinstener;
import com.speed.faster.utils.AesUtils;
import com.speed.faster.utils.L;
import com.speed.faster.utils.LoadDialog;
import com.speed.faster.utils.RotaterView;
import com.speed.faster.utils.SharedUtils;
import com.speed.faster.utils.StringUtils;
import com.speed.faster.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mjm  on 2018/7/13 16:09
 * 登录页面
 */
public class LoginActivity extends BaseActivity {
    @Bind(R.id.tv_register)
    TextView tv_register;
    @Bind(R.id.et_login_email)
    EditText et_login_email;
    @Bind(R.id.et_login_pass)
    EditText et_login_pass;
    public static Activity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        instance = this;
        //切换动画
        Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.fade);
        getWindow().setEnterTransition(explode);
        //退出时使用
        getWindow().setExitTransition(explode);

    }

    @OnClick({R.id.tv_register, R.id.btn_login, R.id.back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back:
//                finish();
                finishAfterTransition();
                break;
            case R.id.tv_register:
                Intent intent4 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent4, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.btn_login:
                loadData();
                break;
        }

    }


    private void loadData() {
        String email = et_login_email.getText().toString();
        String pass = et_login_pass.getText().toString();
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
        LoadDialog.show(LoginActivity.this, HttpCode.LOADING);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", email);
        map.put("password", pass);
        HttpNet.httpPost(HttpUrl.LOGIN, LoginActivity.this, map, new RequestLinstener() {
            @Override
            public void OnSuccess(String responseString) {
                LoadDialog.dismiss(LoginActivity.this);
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
                if (SplashActivity.instance != null) {
                    SplashActivity.instance.finish();
                }
                ToastUtils.showMessage(HttpCode.Successfully_login);
                finish();
            }

            @Override
            public void OnError(String errorString) {
                LoadDialog.dismiss(LoginActivity.this);
                ToastUtils.showMessage(errorString);
            }
        });
    }
}












