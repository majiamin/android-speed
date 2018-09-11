package com.speed.faster.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.speed.faster.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogAccountActivity extends Activity {
    @Bind(R.id.sweep_code_cancel)
    Button btn_expire;
    @Bind(R.id.sweep_code_go)
    Button btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.dialog_account_expire);
        ButterKnife.bind(this);
        //切换动画
        Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.explode);
        getWindow().setEnterTransition(explode);
//退出时使用
        Transition fade = TransitionInflater.from(this).inflateTransition(R.transition.fade);
        getWindow().setExitTransition(fade);
        //对话框宽度全屏
        Window w = getWindow();
        WindowManager.LayoutParams a = w.getAttributes();
        a.gravity = Gravity.CENTER;
        a.width = WindowManager.LayoutParams.FILL_PARENT;
        w.setAttributes(a);
    }

    @OnClick({R.id.sweep_code_cancel, R.id.sweep_code_go})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.sweep_code_cancel:

                finish();
                break;
            case R.id.sweep_code_go:
                startActivity(new Intent(DialogAccountActivity.this, VipActivity.class));
                finish();
                break;
        }
    }
}
