package com.speed.faster.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.speed.faster.Content;
import com.speed.faster.MyApplication;

import java.util.UUID;

import static android.text.TextUtils.isEmpty;

public class AuthorizationUtils {


    public static String AuthorizationJwt(Context context) {
        String header = "";
        String customerId = "";
        if (!isEmpty(SharedUtils.getString("customerId"))) {
            customerId = SharedUtils.getString("customerId");
        }
        header = JwtUtils.generateJwt(getDeviceId(context), Content.APPID, customerId);
        return header;
    }


    /**
     * deviceID的组成为：渠道标志+识别符来源标志+hash后的终端识别符
     * <p>
     * 渠道标志为：
     * 1，andriod（a）
     * <p>
     * 识别符来源标志：
     * 1， wifi mac地址（wifi）；
     * 2， IMEI（imei）；
     * 3， 序列号（sn）；
     * 4， id：随机码。若前面的都取不到时，则随机生成一个随机码，需要缓存。
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        StringBuilder deviceId = new StringBuilder();
        // 渠道标志
        deviceId.append("a");
        try {
            //IMEI（imei）
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            @SuppressLint("MissingPermission") String imei = tm.getDeviceId();
            if (!isEmpty(imei)) {
                deviceId.append("imei");
                deviceId.append(imei);
                L.e("imei-getDeviceId : " + deviceId.toString());
                return deviceId.toString();
            }
            //wifi mac地址
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            String wifiMac = info.getMacAddress();
            if (!isEmpty(wifiMac)) {
                deviceId.append("wifi");
                deviceId.append(wifiMac);
                L.e("wifi--getDeviceId : " + deviceId.toString());
                return deviceId.toString();
            }
            //序列号（sn）
            @SuppressLint("MissingPermission") String sn = tm.getSimSerialNumber();
            if (!isEmpty(sn)) {
                deviceId.append("sn");
                deviceId.append(sn);
                L.e("sn--getDeviceId : " + deviceId.toString());
                return deviceId.toString();
            }
            //如果上面都没有， 则生成一个id：随机码
            String uuid = getUUID(context);
            if (!isEmpty(uuid)) {
                deviceId.append("id");
                deviceId.append(uuid);
                L.e("uuid--getDeviceId : " + deviceId.toString());
                return deviceId.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            deviceId.append("id").append(getUUID(context));
        }
        L.e("getDeviceId最终-- " + deviceId.toString());
        return deviceId.toString();
    }

    /**
     * 得到全局唯一UUID
     */
    public static String getUUID(Context context) {
        String uuid = SharedUtils.getString("devicedID");
        if (isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            SharedUtils.putString("devicedID", uuid);
        }
        return uuid;
    }
}
