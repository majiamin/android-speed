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
 * Created by mjm  on 2018/7/14 14:29
 * 个人设置页面
 */

public class BindEmailActivity extends BaseActivity {
    @Bind(R.id.et_email)
    EditText et_email;
    @Bind(R.id.et_pass)
    EditText et_pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_email);
        ButterKnife.bind(this);
        //切换动画
        Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.fade);
        getWindow().setEnterTransition(explode);
        //退出时使用
        getWindow().setExitTransition(explode);
        LoginBean loginBean = SharedUtils.getObject(SharedUtils.LOGIN_BEAN);
        String email = loginBean.getEmail();
        L.e("绑定邮箱--"+email);
        if (email != null || email!="") {
            et_email.setText(email);
        }

    }

    @OnClick({R.id.back, R.id.btn_email})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back:
                finishAfterTransition();
                break;
            case R.id.btn_email:
                submit();
                break;
        }
    }

    private void submit() {
        String account = et_email.getText().toString();
        String pass = et_pass.getText().toString();
        if (TextUtils.isEmpty(account)) {
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
        LoadDialog.show(BindEmailActivity.this, HttpCode.LOADING);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("email", account);
        map.put("password", pass);
        HttpNet.httpPut(HttpUrl.BIND, BindEmailActivity.this, map, new RequestLinstener() {
            @Override
            public void OnSuccess(String responseString) {
                LoadDialog.dismiss(BindEmailActivity.this);
                ToastUtils.showMessage(responseString);
                finish();
            }

            @Override
            public void OnError(String errorString) {
                LoadDialog.dismiss(BindEmailActivity.this);
                ToastUtils.showMessage(errorString);
            }
        });
    }
}
