package com.speed.faster.utils;

import android.content.Context;
import android.os.Build;
import android.view.WindowManager;

//import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.speed.faster.R;
import com.speed.faster.activity.BaseActivity;

/**
 * Created by mjm on 2017/6/8.
 * 修改状态栏问题
 * 配合 compile  'com.readystatesoftware.systembartint:systembartint:1.0.3'使用
 */

public class StatusBarUtils {
    public static void loadMain(Context mContext){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            ((BaseActivity)mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            ((MainActivity)mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(((BaseActivity)mContext));
//            tintManager.setTranslucentStatus(((MainActivity)mContext), true);
            // 激活状态栏
            tintManager.setStatusBarTintEnabled(true);
            // enable navigation bar tint 激活导航栏
//            tintManager.setNavigationBarTintEnabled(true);
            //设置系统栏设置颜色
            //tintManager.setTintColor(R.color0.red);
            //给状态栏设置颜色
            tintManager.setStatusBarTintResource(R.color.background_status);
            //Apply the specified drawable or color resource to the system navigation bar.
            //给导航栏设置资源
//            tintManager.setNavigationBarTintResource(R.color.colorAccent);
        }
    }

}
