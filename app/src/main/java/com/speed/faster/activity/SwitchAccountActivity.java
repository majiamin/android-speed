package com.speed.faster.activity;

import android.content.Intent;
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
import com.speed.faster.utils.ToastUtils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mjm  on 2018/7/11 15:29
 * 切换账号页面
 */
public class SwitchAccountActivity extends BaseActivity {
    @Bind(R.id.et_account)
    EditText et_account;
    @Bind(R.id.et_pass)
    EditText et_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_account);
        ButterKnife.bind(this);
        //切换动画
        Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.fade);
        getWindow().setEnterTransition(explode);
        //退出时使用
        getWindow().setExitTransition(explode);

        LoginBean loginBean = SharedUtils.getObject(SharedUtils.LOGIN_BEAN);
        String username = loginBean.getUsername();
        L.e("切换账号--" + username);
        if (username != null || username != "") {
            et_account.setText(username);
        }

    }

    @OnClick({R.id.btn_switch_account, R.id.back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_switch_account:
                loadData();
                break;
            case R.id.back:
                finishAfterTransition();
                break;

        }
    }

    private void loadData() {
        String account = et_account.getText().toString();
        String pass = et_pass.getText().toString();
        if (TextUtils.isEmpty(account)) {
            ToastUtils.showMessage(HttpCode.ACCOUNT_NULL);
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            ToastUtils.showMessage(HttpCode.PASS_NULL);
            return;
        }
        LoadDialog.show(SwitchAccountActivity.this, HttpCode.LOADING);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", account);
        map.put("password", pass);
        HttpNet.httpPost(HttpUrl.LOGIN, SwitchAccountActivity.this, map, new RequestLinstener() {
            @Override
            public void OnSuccess(String responseString) {
                LoadDialog.dismiss(SwitchAccountActivity.this);
                Gson gson = new Gson();
                LoginBean loginBean = gson.fromJson(responseString, LoginBean.class);
                L.e("username---" + loginBean.getUsername());
                L.e("Appid---" + loginBean.getAppId());
                L.e("Id---" + loginBean.getId());
                //保存登录对象
                SharedUtils.saveObject(SharedUtils.LOGIN_BEAN, loginBean);
                //customerId--下次登录使用
                SharedUtils.putString("customerId", loginBean.getId() + "");
                ToastUtils.showMessage(HttpCode.Successfully_login);
                Intent it = new Intent();
                setResult(RESULT_OK, it);
                finish();

            }

            @Override
            public void OnError(String errorString) {
                LoadDialog.dismiss(SwitchAccountActivity.this);
                ToastUtils.showMessage(errorString);
            }
        });
    }
}
