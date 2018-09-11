package com.speed.faster.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.speed.faster.R;
import com.speed.faster.receiver.MyReceiver;
import com.speed.faster.utils.CheckNetWorkAvailableUtil;
import com.speed.faster.utils.StatusBarUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by mjm on 2018/7/9.
 * Describe:基类
 */
public class BaseActivity extends Activity {

    MyReceiver broadcastReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        StatusBarUtils.loadMain(BaseActivity.this);
        setContentView(R.layout.activity_base);
        //禁止截屏
//    this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        //网络判断
        if (!CheckNetWorkAvailableUtil.isNetworkAvailable(BaseActivity.this)) {
            //没有网络打开wifi对话框
            CheckNetWorkAvailableUtil.dialog(BaseActivity.this);
        }

        broadcastReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter("com.speed.action.broadcast");//action.test.wedview.life
        registerReceiver(broadcastReceiver, filter);
    }

    public void jumpActivity(Class cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
    }

    public void jumpActivity(Intent intent, Class cls) {
        intent.setClass(this, cls);
        startActivity(intent);
    }

    public void onResume() {
        super.onResume();
        //重载友盟的方法--Session启动、App使用时长等基础数据统计接口API
//        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}

