package com.speed.faster.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.speed.faster.https.HttpCode;

/**
 * @author mjm
 * @time 2015-12-28 下午5:02:13
 * @描述:检测是否有网络的代码 工具类
 */
public class CheckNetWorkAvailableUtil {

    private static final String TAG = "MA";

    /**
     * @param context
     * @author mjm
     * @time 2015-12-28 下午5:23:03
     * @描述: 检测是否有网络 并且没有网络 弹出打开wifi的界面
     */
    public static void checkNetWorkInfo(final Context context) {
        if (!CheckNetWorkAvailableUtil.isNetworkAvailable(context)) {
            new AlertDialog.Builder(context)
                    .setTitle("提示!")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setMessage("检测到你还没开启网络，请开启")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("开启",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    Intent wifiSettingsIntent = new Intent(
                                            "android.settings.WIFI_SETTINGS");
                                    context.startActivity(wifiSettingsIntent);

                                    // context.startActivity(new Intent(
                                     // Settings.ACTION_WIRELESS_SETTINGS));//
                                    // 进入无线网络配置界面
                                    // startActivity(new Intent(
                                    // Settings.ACTION_WIFI_SETTINGS)); //
                                    // 进入手机中的wifi网络设置界面
                                }
                            }).show();
        }
    }

    public static void dialog(final Context context) {
        new AlertDialog.Builder(context)
                .setTitle(HttpCode.DIALOG_TIPS)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setMessage(HttpCode.DIALOG_NO_NET)
                .setCancelable(false)
                .setNegativeButton(HttpCode.DIALOG_CANCEL, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ToastUtils.showMessage(HttpCode.DIALOG_PLEASE_OPEN_NET);
                    }
                })
                .setPositiveButton(HttpCode.DIALOG_OPEN_NET,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                Intent wifiSettingsIntent = new Intent(
                                        "android.settings.WIFI_SETTINGS");
                                context.startActivity(wifiSettingsIntent);

                                // context.startActivity(new Intent(
                                // Settings.ACTION_WIRELESS_SETTINGS));//
                                // 进入无线网络配置界面
                                // startActivity(new Intent(
                                // Settings.ACTION_WIFI_SETTINGS)); //
                                // 进入手机中的wifi网络设置界面
                            }
                        }).show();
    }

    public static void WifiDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage("您目前不是wifi状态,是否继续下载");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
//        Intent intent = new Intent(IDDetatilActivity.this, PersonFaceActivity.class);
//        startActivity(intent);
            }
        });

        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    /**
     * @param context
     * @return
     * @author mjm
     * @time 2015-12-28 下午5:03:43
     * @描述:判断是否有网络
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        } else {
            // 打印所有的网络状态
            NetworkInfo[] infos = cm.getAllNetworkInfo();
            if (infos != null) {
                for (int i = 0; i < infos.length; i++) {
                    // Log.d(TAG, "isNetworkAvailable - info: " +
                    // infos[i].toString());
                    if (infos[i].getState() == NetworkInfo.State.CONNECTED) {
                        Log.d(TAG, "isNetworkAvailable -  I " + i);
                    }
                }
            }

            // 如果仅仅是用来判断网络连接　　　　　　
            // 则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null) {
                Log.d(TAG,
                        "isNetworkAvailable - 是否有网络： "
                                + networkInfo.isAvailable());
            } else {
                Log.d(TAG, "isNetworkAvailable - 完成没有网络！");
                return false;
            }

            // 1、判断是否有3G网络
            if (networkInfo != null
                    && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                Log.d(TAG, "isNetworkAvailable - 有3G网络");
                return true;
            } else {
                Log.d(TAG, "isNetworkAvailable - 没有3G网络");
            }

            // 2、判断是否有wifi连接
            if (networkInfo != null
                    && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                Log.d(TAG, "isNetworkAvailable - 有wifi连接");
                return true;
            } else {
                Log.d(TAG, "isNetworkAvailable - 没有wifi连接");
            }
        }
        return false;
    }

    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        } else {
            // 打印所有的网络状态
            NetworkInfo[] infos = cm.getAllNetworkInfo();
            if (infos != null) {
                for (int i = 0; i < infos.length; i++) {
                    // Log.d(TAG, "isNetworkAvailable - info: " +
                    // infos[i].toString());
                    if (infos[i].getState() == NetworkInfo.State.CONNECTED) {
                        Log.d(TAG, "isNetworkAvailable -  I " + i);
                    }
                }
            }

            // 如果仅仅是用来判断网络连接　　　　　　
            // 则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            // 2、判断是否有wifi连接
            if (networkInfo != null
                    && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                Log.d(TAG, "isNetworkAvailable - 有wifi连接");
                return true;
            } else {
                Log.d(TAG, "isNetworkAvailable - 没有wifi连接");
            }
        }
        return false;
    }
}
