package com.speed.faster.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
 * Created by mjm  on 2018/7/11 16:59
 * 修改密码页面
 */
public class ChangPasswordActivity extends BaseActivity {
    @Bind(R.id.tv_oldpass)
    TextView tv_oldpass;
    @Bind(R.id.et_new_pass)
    EditText et_new_pass;
    @Bind(R.id.et_config_pass)
    EditText et_config_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changpass);
        ButterKnife.bind(this);
        //切换动画
        Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.fade);
        getWindow().setEnterTransition(explode);
        //退出时使用
        getWindow().setExitTransition(explode);
    }

    @OnClick({R.id.btn_reset, R.id.btn_show, R.id.back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back:
                finishAfterTransition();
                break;
            case R.id.btn_reset:
                submit();
                break;
            case R.id.btn_show:
                LoginBean loginBean = SharedUtils.getObject(SharedUtils.LOGIN_BEAN);
                if ("******".equals(tv_oldpass.getText().toString().trim())) {
                    tv_oldpass.setText(loginBean.getPassword());
                } else {
                    tv_oldpass.setText("******");
                }
                break;
        }
    }

    private void submit() {
        String newPass = et_new_pass.getText().toString();
        String configPass = et_config_pass.getText().toString();
        if (TextUtils.isEmpty(newPass)) {
            ToastUtils.showMessage(HttpCode.ENTER_NEW_PASS);
            return;
        }
        if (!StringUtils.checkPassWord(newPass)) {
            ToastUtils.showMessage(HttpCode.NEW_PASS_FORMATEL);
            return;
        }
        if (TextUtils.isEmpty(configPass)) {
            ToastUtils.showMessage(HttpCode.ENTER__CONFIRM_PASS);
            return;
        }
        if (!configPass.equals(newPass)) {
            ToastUtils.showMessage(HttpCode.TWO_NO_IDENTICAL);
            return;
        }

        LoadDialog.show(ChangPasswordActivity.this, HttpCode.LOADING);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("newPassword", newPass);
        map.put("password", tv_oldpass.getText().toString().trim());
        HttpNet.httpPut(HttpUrl.CHANGEPSAAWORD, ChangPasswordActivity.this, map, new RequestLinstener() {
            @Override
            public void OnSuccess(String responseString) {
                LoadDialog.dismiss(ChangPasswordActivity.this);
                ToastUtils.showMessage(HttpCode.Successfully_modified);
                Gson gson = new Gson();
                LoginBean loginBean = gson.fromJson(responseString, LoginBean.class);
                L.e("username---" + loginBean.getUsername());
                L.e("Appid---" + loginBean.getAppId());
                L.e("Id---" + loginBean.getId());
                //保存登录对象
                SharedUtils.saveObject(SharedUtils.LOGIN_BEAN, loginBean);
                //customerId--下次登录使用
                SharedUtils.putString("customerId", loginBean.getId() + "");
                finish();

            }

            @Override
            public void OnError(String errorString) {
                LoadDialog.dismiss(ChangPasswordActivity.this);
                ToastUtils.showMessage(errorString);
            }
        });
    }
}













