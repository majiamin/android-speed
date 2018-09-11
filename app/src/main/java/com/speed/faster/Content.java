package com.speed.faster;

import com.speed.faster.https.HttpCode;
import com.speed.faster.utils.L;
import com.speed.faster.utils.ToastUtils;

import java.util.Random;

/**
 * Created by mjm on 2018/7/9.
 * Describe: 常量
 */
public class Content {
    //代理地址 端口：200-1000
//    ss://chacha20:lovekuando@45.79.78.129:887
    public static String PROXYURL = "";
    //代表当前App的ID号1372003313
    public static final String APPID = "1372003313";
    //jwt密钥
    public static String JWT_SECRET = "YzIwYjgyNGUtNmI0YS00ZTBmLTliZmEtMTkzZDA4OWZhODkzCg==";
    //AES256 CBC PKCS5Padding AES256密钥
    public static String AES_SECRET = "iSxFwpf$$q!ndlXqrQaQ60RZ#Wrlr@Uo";
    //vpn请求code
    public static final int START_VPN_SERVICE_REQUEST_CODE = 1111;
    //7.0文件存储标识
    public static final String FILEPROVIDER = "com.speed.faster.fileprovider";
    //QQ 群号码
    public static final String QQQun = "648697128";
    //google 商店标识
    public static final String GOOGLE_STORE = "com.android.vending";
    //友盟统计渠道
    public static final String CHANNEL = "SPEED_FASTER";
    //友盟统计key
    public static final String YOUMENG_KEY = "5b629de88f4a9d375e00004c";

    //得到随机端口号
    public static String getHost(String host) {
        String port = "";//端口200-1000
        int min = 200;
        int max = 1000;
        Random random = new Random();
        int num = random.nextInt(max) % (max - min + 1) + min;
        L.e("num---" + num);
        if ("".equals(host)) {
            ToastUtils.showMessage(MyApplication.getInstance().getString(R.string.HOST_NULL));
            return null;
        }
        L.e("---host地址--" + "ss://chacha20:lovekuando@" + host + ":" + num);
        return "ss://chacha20:lovekuando@" + host + ":" + num;//代理地址 端口：200-1000
    }

    //分离host字符串
    public static String spltString(String str, int number) {
//        45.79.67.34,45.79.69.42,45.79.104.142
        //http://146.196.55.156,http://api.marcosida.top,http://180.188.197.158
        String[] all = null;
        String oneArray = "";
        String secondArray = "";
        String threeArray = "";
        String string = "";//
        if (str != null) {
            all = str.split(",");
        }
        if (all.length == 3) {
            oneArray = all[0];
            secondArray = all[1];
            threeArray = all[2];
        }
        if (number == 1) {
            string = oneArray;
        } else if (number == 2) {
            string = secondArray;
        } else if (number == 3) {
            string = threeArray;
        }
        return string;
    }
}
