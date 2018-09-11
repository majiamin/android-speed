package com.speed.faster;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.speed.faster.https.HttpNet;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;


/**
 * Created by mjm on 2018/7/6.
 */

public class MyApplication extends Application {
    public static MyApplication mInstance;
    // 所有系统配置有关的信息 都存放在这个xml文件里面（sharedPreferencesFile）
    private static final String SystemConfigFile = "systemConfig.xml";
    // 存放系统配置信息的sharedPreferences文件实例
    private static SharedPreferences mSharedPreferences;

    public static MyApplication getInstance() {
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        createSharedPreferencesFile();
        //初始化OKhttp3
        HttpNet.initOkHttpClient();
        initYouMeng(mInstance);
    }

    public void initYouMeng(Context context) {
        /*
注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调用初始化接口
（如需要使用AndroidManifest.xml中配置好的appkey和channel值，UMConfigure.init调用中appkey和channel参数请置为null）。
*/
        UMConfigure.init(context, Content.YOUMENG_KEY, Content.CHANNEL, UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    /**
     * @描述: 创建存放系统所有配置文件的 sharedpreferences文件
     */
    public void createSharedPreferencesFile() {
        mSharedPreferences = getSharedPreferences(SystemConfigFile,
                MODE_PRIVATE);
    }

    /**
     * @return sharedPreferences文件实例
     * @描述: 返回系统配置信息的sharedPreferences文件实例
     */
    public static SharedPreferences getSystemConfigSharedFile() {
        return mSharedPreferences;
    }

}
