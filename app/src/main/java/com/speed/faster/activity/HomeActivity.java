package com.speed.faster.activity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.speed.faster.Content;
import com.speed.faster.MyApplication;
import com.speed.faster.R;
import com.speed.faster.bean.AppManagerBean;
import com.speed.faster.bean.LoginBean;
import com.speed.faster.core.AppInfo;
import com.speed.faster.core.AppProxyManager;
import com.speed.faster.core.LocalVpnService;
import com.speed.faster.https.HttpCode;
import com.speed.faster.https.HttpNet;
import com.speed.faster.https.HttpUrl;
import com.speed.faster.https.RequestLinstener;
import com.speed.faster.utils.AesUtils;
import com.speed.faster.utils.AppUtils;
import com.speed.faster.utils.JumpStoreUtils;
import com.speed.faster.utils.L;
import com.speed.faster.utils.LoadDialog;
import com.speed.faster.utils.PingNet;
import com.speed.faster.utils.PingNetEntity;
import com.speed.faster.utils.SharedUtils;
import com.speed.faster.utils.ToastUtils;
import com.speed.faster.utils.ToolUtils;
import com.speed.faster.utils.UpdateApk;
import com.speed.faster.utils.WaveView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.speed.faster.Content.START_VPN_SERVICE_REQUEST_CODE;
import static com.speed.faster.Content.getHost;

/**
 * Created by mjm  on 2018/7/11 14:09
 * 主页
 */

public class HomeActivity extends BaseActivity implements LocalVpnService.onStatusChangedListener {
    @Bind(R.id.btn_setup)
    Button btn_setup;
    @Bind(R.id.img_connect)
    ImageView img_connect;
    @Bind(R.id.tv_connect)
    TextView tv_connect;
    @Bind(R.id.waveview)
    WaveView waveview;
    @Bind(R.id.img_country)
    ImageView img_country;
    @Bind(R.id.tv_country_code)
    TextView tv_country_code;
    @Bind(R.id.ll_gift)
    LinearLayout ll_gift;
    @Bind(R.id.ivProgress)
    ImageView ivProgress;
    @Bind(R.id.rl_connect)
    RelativeLayout rl_connect;

    Handler handler = new Handler();

    protected static final int MSG_ONE = 1;
    protected static final int MSG_Two = 2;
    protected static final int MSG_Three = 3;
    protected static final int MSG_Four = 4;

    private String oneTime = "10000.0";//设置默认时间，防止超时链接
    private String twoTime = "10000.0";
    private String threeTime = "10000.0";
    private String onehost = "";
    private String twohost = "";
    private String threehost = "";
    private Timer timer;
    private TimerTask task;//时间定时器
    private String host;
    Animation animation, animation_rl_out, animation_rl_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        animation = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.loading);
        animation_rl_out = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.scale_out);
        animation_rl_in = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.scale_in);
        init();
    }

    private void init() {
        //根据第一次安装 评论过 增加时间 isShowRate决定是否第一次显示 HasComment是否评论过
        AppManagerBean appManagerBean = SharedUtils.getObject(SharedUtils.APP_BEAN);
        LoginBean loginBean = SharedUtils.getObject(SharedUtils.LOGIN_BEAN);
        boolean flag1 = appManagerBean.getApp().getMeta().isIsShowRate();//true--显示出来
        boolean flag2 = loginBean.isHasComment();
        if (flag1) {
            if (flag2) {
                ll_gift.setVisibility(View.INVISIBLE);
            } else {
                ll_gift.setVisibility(View.VISIBLE);
            }
        } else {
            ll_gift.setVisibility(View.INVISIBLE);
        }
        //vip连接--进入判断是否到期 到期自动关闭
        if (!loginBean.isIsVip()) {
            disConnect();
        }
        LocalVpnService.addOnStatusChangedListener(HomeActivity.this);
        if (AppProxyManager.isLollipopOrAbove) {
            new AppProxyManager(this);
        } else {
            ((ViewGroup) findViewById(R.id.AppSelectLayout).getParent()).removeView(findViewById(R.id.AppSelectLayout));
            ((ViewGroup) findViewById(R.id.textViewAppSelectLine).getParent()).removeView(findViewById(R.id.textViewAppSelectLine));
        }
        L.e("进入页面vpn---" + LocalVpnService.IsRunning);
        if (LocalVpnService.IsRunning) {
            img_connect.setImageResource(R.drawable.switch2);
            tv_connect.setText("CONNECTED");
            ivProgress.setVisibility(View.GONE);
            tv_connect.setTextColor(Color.parseColor("#5DBDF9"));
        } else {
            ivProgress.setVisibility(View.GONE);
            img_connect.setImageResource(R.drawable.switch1);
            tv_connect.setText("DISCONNECT");
            tv_connect.setTextColor(Color.parseColor("#ABABAB"));
        }
//        waveview.setDuration(6000);
//        waveview.setStyle(Paint.Style.FILL);
//        waveview.setColor(Color.parseColor("#5DBDF9"));

//        waveview.setInterpolator(new LinearOutSlowInInterpolator());
        loadSnodes();
        //国家选择
        if (!TextUtils.isEmpty(SharedUtils.getString("country_code"))) {
            tv_country_code.setText(SharedUtils.getString("country_code"));
            img_country.setImageBitmap(ToolUtils.getImageFromAssetsFile(HomeActivity.this, SharedUtils.getString("country_code")));
        } else {
            L.e("默认没有。。。");
        }
        //版本更新
        UpdateApk.UpdateVersion(HomeActivity.this, appManagerBean.getApp().getMeta().getUpdate());
    }


    @OnClick({R.id.btn_setup, R.id.btn_vip, R.id.rl_connect, R.id.ll_country, R.id.ll_gift})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_setup:
//                jumpActivity(SetActivity.class);
                Intent intent4 = new Intent(HomeActivity.this, SetActivity.class);
                startActivity(intent4, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.btn_vip:
                Intent intent1 = new Intent(this, VipActivity.class);
                startActivityForResult(intent1, 200);
                break;
            case R.id.rl_connect:
                if (SharedUtils.getObject(SharedUtils.LOGIN_BEAN) != null) {
                    LoginBean loginBean = SharedUtils.getObject(SharedUtils.LOGIN_BEAN);
                    //vip连接
                    if (!loginBean.isIsVip()) {
                        if (LocalVpnService.IsRunning) {
                            disConnect();
                        }
                        Intent intent5 = new Intent(HomeActivity.this, DialogAccountActivity.class);
                        startActivity(intent5, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                        break;
                    }
                }
                if (LocalVpnService.IsRunning) {
//                    waveview.stop();
                } else {
//                    waveview.start();
                }
                L.e("LocalVpnService.IsRunning----" + LocalVpnService.IsRunning);
                if (LocalVpnService.IsRunning) {
                    disConnect();
                } else {
                    rl_connect.startAnimation(animation_rl_out);
                    ivProgress.startAnimation(animation);
                    ivProgress.setVisibility(View.VISIBLE);
                    tv_connect.setText("CONNECTING...");
                    tv_connect.setTextColor(Color.parseColor("#ABABAB"));
                    isPingConnect();
                }
                break;
            case R.id.ll_country:
                if (TextUtils.isEmpty(SharedUtils.getString("country_code"))) {
                    L.e("默认没有。。。");
                    //没有的话，默认，前提是存在
                    SharedUtils.putString("country_code", "US");
                }
                Intent intent = new Intent(this, CountryActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.ll_gift:
                loadGiftVip();
                JumpStoreUtils.launchAppDetail("com.facebook.katana", Content.GOOGLE_STORE, HomeActivity.this);
                break;

        }

    }


    private void disConnect() {
        //断开链接
        LocalVpnService.IsRunning = false;
//        LocalVpnService.Instance.disconnectVPN();
//        stopService(new Intent(StartActivity.this, LocalVpnService.class));
//        System.runFinalization();
//        System.exit(0);
//        ToastUtils.showMessage("已关闭");
        L.e("disConnect----");
    }

    private void startVPN() {
        if (!LocalVpnService.IsRunning) {
            Intent intent = LocalVpnService.prepare(this);
            if (intent == null) {
                startVPNService();
            } else {
                startActivityForResult(intent, START_VPN_SERVICE_REQUEST_CODE);
            }
        }
        L.e("startVPN----");
    }

    private void startVPNService() {
        if ("".equals(host) || host == null) {
            ToastUtils.showMessage(HttpCode.HOST_NULL);
            ivProgress.clearAnimation();
            return;
        }
        String ProxyUrl = getHost(host);
        if (!isValidUrl(ProxyUrl)) {
            Toast.makeText(this, R.string.err_invalid_url, Toast.LENGTH_SHORT).show();
            ivProgress.clearAnimation();
            return;
        }
        //传递url
        LocalVpnService.ProxyUrl = ProxyUrl;
        startService(new Intent(this, LocalVpnService.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == START_VPN_SERVICE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                startVPNService();
            } else {
                L.e("canceled");
                ToastUtils.showMessage("canceled");
            }
            return;
        }
        if (requestCode == 100 && resultCode == RESULT_OK) {
            //国家选择完毕，重新请求节点
            loadSnodes();
            if (!TextUtils.isEmpty(SharedUtils.getString("country_code"))) {
                tv_country_code.setText(SharedUtils.getString("country_code"));
                img_country.setImageBitmap(ToolUtils.getImageFromAssetsFile(HomeActivity.this, SharedUtils.getString("country_code")));
            } else {
                L.e("默认没有。。。");
                //没有的话，默认，前提是存在
                SharedUtils.putString("country_code", "US");
            }
        }
        if (requestCode == 200 && resultCode == RESULT_OK) {
            loadGetorcreate();
        }
    }


    @Override
    public void onStatusChanged(String status, Boolean isRunning) {
        L.e("onStatusChanged--status--" + status);
        ToastUtils.showMessage(status);
        ivProgress.clearAnimation();
        ivProgress.setVisibility(View.INVISIBLE);
        if (isRunning) {
            rl_connect.startAnimation(animation_rl_out);
            img_connect.setImageResource(R.drawable.switch2);
            tv_connect.setText("CONNECTED");
            tv_connect.setTextColor(Color.parseColor("#5DBDF9"));
        } else {
            rl_connect.startAnimation(animation_rl_out);
            img_connect.setImageResource(R.drawable.switch1);
            tv_connect.setText("DISCONNECT");
            tv_connect.setTextColor(Color.parseColor("#ABABAB"));
        }
        onLogReceived(status);
//        LoadDialog.dismiss(HomeActivity.this);
    }

    @Override
    public void onLogReceived(String logString) {
        L.e("onLogReceived----" + logString);
    }

    //判断url格式是否正确
    public boolean isValidUrl(String url) {
        try {
            if (url == null || url.isEmpty())
                return false;

            if (url.startsWith("ss://")) {//file path
                return true;
            } else { //url
                Uri uri = Uri.parse(url);
                if (!"http".equals(uri.getScheme()) && !"https".equals(uri.getScheme()))
                    return false;
                if (uri.getHost() == null)
                    return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void isPingConnect() {
        String host = SharedUtils.getString("host");
        if (TextUtils.isEmpty(host)) {
            ToastUtils.showMessage(HttpCode.HOST_NULL);
            ivProgress.clearAnimation();
            return;
        }
        String aaArray = Content.spltString(host, 1);
        String bbArray = Content.spltString(host, 2);
        String ccArray = Content.spltString(host, 3);
//        String aaArray = "42.51.43.114";
//        String bbArray = "42.51.42.209";
//        String ccArray = "42.51.28.142";
        L.e("aa----" + host + "---getHost----" + Content.getHost(aaArray));
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 4;
                handlerd.sendMessage(message);
            }
        };
        timer.schedule(task, 2500);
        threadPing(aaArray, 1);
        threadPing(bbArray, 2);
        threadPing(ccArray, 3);

    }

    //开启子线程进行ping  Ip
    public void threadPing(final String hostArray, final int type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                double time = 10000.0;//给出默认时间
                final PingNetEntity pingNetEntity = new PingNetEntity(hostArray, 3, 3, new StringBuffer());
                PingNet.ping(pingNetEntity);
                L.i(hostArray, pingNetEntity.getIp());
                L.i(hostArray, "IP----" + pingNetEntity.getIp() + "单次时间----" + pingNetEntity.getPingTime());
                L.i(hostArray, pingNetEntity.isResult() + "");
                L.i(hostArray, "总时间---" + pingNetEntity.getPingTimeTotal() + "");
                L.i(hostArray, "成功次数---" + pingNetEntity.getPingSuccessCount() + "");
                //代表超时  100%丢包  默认给出较大的时间10s--超出了3秒的限制时间就可以
                if (pingNetEntity.getPingTimeTotal() == null || pingNetEntity.getPingSuccessCount() == null) {

                } else {
                    time = (Double.parseDouble(pingNetEntity.getPingTimeTotal()) / Double.parseDouble(pingNetEntity.getPingSuccessCount()));
                    L.i(hostArray, "平均时间---" + time + "");
                }
                Message message = new Message();
                if (type == 1) {
                    message.what = MSG_ONE;
                } else if (type == 2) {
                    message.what = MSG_Two;
                } else if (type == 3) {
                    message.what = MSG_Three;
                }
                Bundle bundle = new Bundle();
                bundle.putString("time", time + "");
                bundle.putString("hostArray", hostArray + "");
                message.setData(bundle);
                handlerd.sendMessage(message);
            }
        }).start();
    }

    public void loadGetorcreate() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("platform", "Android");
        HttpNet.httpPost(HttpUrl.GETORCREATE, MyApplication.getInstance(), map, new RequestLinstener() {
            @Override
            public void OnSuccess(String responseString) {
                try {
                    Gson gson = new Gson();
                    LoginBean loginBean = gson.fromJson(responseString, LoginBean.class);
                    L.e("username---" + loginBean.getUsername());
                    L.e("Appid---" + loginBean.getAppId());
                    L.e("Id---" + loginBean.getId());
                    //保存登录对象
                    SharedUtils.saveObject(SharedUtils.LOGIN_BEAN, loginBean);
                    //customerId--下次登录使用
                    SharedUtils.putString("customerId", loginBean.getId() + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void OnError(String errorString) {
                ToastUtils.showMessage(errorString);
            }
        });
    }

    public void loadGiftVip() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "day");
        map.put("num", "1");
        HttpNet.httpPut(HttpUrl.BOUGJHT_VIP, HomeActivity.this, map, new RequestLinstener() {
            @Override
            public void OnSuccess(String responseString) {
                Gson gson = new Gson();
                LoginBean loginBean = gson.fromJson(responseString, LoginBean.class);
                L.e("username---" + loginBean.getUsername());
                L.e("Appid---" + loginBean.getAppId());
                L.e("Id---" + loginBean.getId());
                //保存登录对象
                SharedUtils.saveObject(SharedUtils.LOGIN_BEAN, loginBean);
                //customerId--下次登录使用
                SharedUtils.putString("customerId", loginBean.getId() + "");
            }

            @Override
            public void OnError(String errorString) {

            }
        });
    }

    public void loadSnodes() {
        HttpNet.httpGet(HttpUrl.SNODES, HomeActivity.this, null, new RequestLinstener() {
            @Override
            public void OnSuccess(String responseString) {
                try {
                    JSONObject object = new JSONObject(responseString);
                    String secret = object.get("secret").toString();
                    String aa = AesUtils.decrypt(secret);
                    L.e("aa--" + aa);
                    SharedUtils.putString("host", aa);//获取host 保存在本地
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void OnError(String errorString) {
                ToastUtils.showMessage(HttpCode.HOST_NULL);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        L.e("onResume");
        //不在当前页面-回到主页面时时刷新个人信息
        loadGetorcreate();
        if (AppProxyManager.isLollipopOrAbove) {
            if (AppProxyManager.Instance.proxyAppInfo.size() != 0) {
                String tmpString = "";
                for (AppInfo app : AppProxyManager.Instance.proxyAppInfo) {
                    tmpString += app.getAppLabel() + ", ";
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //退出时的时间
    private long mExitTime = 0;

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(HomeActivity.this, HttpCode.Press_again, Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            //关闭服务。。。
            if (LocalVpnService.Instance != null) {
                LocalVpnService.Instance.disconnectVPN();
                stopService(new Intent(HomeActivity.this, LocalVpnService.class));
                System.runFinalization();
            }
            System.exit(0);
        }
    }

    private Handler handlerd = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_ONE:
                    oneTime = (String) msg.getData().get("time");
                    onehost = (String) msg.getData().get("hostArray");
                    L.e("oneTime--" + oneTime + "---handhost---" + onehost);
                    break;
                case MSG_Two:
                    twoTime = (String) msg.getData().get("time");
                    twohost = (String) msg.getData().get("hostArray");
                    L.e("twoTime--" + twoTime + "---handhost---" + twohost);
                    break;
                case MSG_Three:
                    threeTime = (String) msg.getData().get("time");
                    threehost = (String) msg.getData().get("hostArray");
                    L.e("threeTime--" + threeTime + "---handhost---" + threehost);
                    break;
                case MSG_Four:
                    double time = 0.0;
                    double doubleOne = Double.parseDouble(oneTime);
                    double doubleTwo = Double.parseDouble(twoTime);
                    double doubleThree = Double.parseDouble(threeTime);
                    L.e("doubleOne--" + doubleOne);
                    L.e("doubleTwo--" + doubleTwo);
                    L.e("doubleThree--" + doubleThree);
                    if (doubleOne < doubleTwo) {
                        time = doubleOne;
                        if (time < doubleThree) {
                            time = time;
                            host = onehost;
                        } else {
                            time = doubleThree;
                            host = threehost;
                        }
                    } else {
                        time = doubleTwo;
                        if (time < doubleThree) {
                            time = time;
                            host = twohost;
                        } else {
                            time = doubleThree;
                            host = threehost;
                        }
                    }
                    L.e("最小时间--" + time + "---域名--" + host);
                    timer.cancel();
                    startVPN();
                    break;
            }
        }
    };
}
