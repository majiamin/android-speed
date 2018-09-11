package com.speed.faster.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.text.TextUtils;

import java.util.List;


//获取当前应用的流量 上传 下载
public class GetFlowUtil {

    public static void getAppFlowInfo(String pakageName, Context context) {
        //获取到配置权限信息的应用程序
        PackageManager pms = context.getPackageManager();
        ;
        List<PackageInfo> packinfos = pms
                .getInstalledPackages(PackageManager.GET_PERMISSIONS);
        //存放具有Internet权限信息的应用
//        FlowInfo flowInfo = new FlowInfo();
        for (PackageInfo packinfo : packinfos) {
            String appName = packinfo.packageName;
            if (!TextUtils.isEmpty(appName)) {
                if (appName.equals(pakageName)) {
                    //用于封装具有Internet权限的应用程序信息
                    //封装应用信息
//                    flowInfo.setPackname(packinfo.packageName);
//                    flowInfo.setIcon(packinfo.applicationInfo.loadIcon(pms));
//                    flowInfo.setAppname(packinfo.applicationInfo.loadLabel(pms).toString());
                    //获取到应用的uid（user id）
//                    int uid = packinfo.applicationInfo.uid;
                    //TrafficStats对象通过应用的uid来获取应用的下载、上传流量信息
                    //发送的 上传的流量byte
//                    flowInfo.setUpKb(TrafficStats.getUidRxBytes(uid));
                    //下载的流量 byte
//                    flowInfo.setDownKb(TrafficStats.getUidTxBytes(uid));
                    break;
                }
            }
        }
//        return flowInfo;
    }
}
