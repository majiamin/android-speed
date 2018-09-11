package com.speed.faster.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.speed.faster.Content;
import com.speed.faster.MyApplication;
import com.speed.faster.R;
import com.speed.faster.bean.AppManagerBean;
import com.speed.faster.bean.LoginBean;
import com.speed.faster.core.LocalVpnService;
import com.speed.faster.https.HttpCode;
import com.speed.faster.https.HttpNet;
import com.speed.faster.https.HttpUrl;
import com.speed.faster.https.RequestLinstener;
import com.speed.faster.utils.AesUtils;
import com.speed.faster.utils.JumpStoreUtils;
import com.speed.faster.utils.JwtUtils;
import com.speed.faster.utils.L;
import com.speed.faster.utils.LoadDialog;
import com.speed.faster.utils.PermissionHelper;
import com.speed.faster.utils.PingNet;
import com.speed.faster.utils.PingNetEntity;
import com.speed.faster.utils.SharedUtils;
import com.speed.faster.utils.ToastUtils;
import com.speed.faster.view.SplashView;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mjm  on 2018/7/13 16:09
 * 启动页
 */
public class SplashActivity extends BaseActivity {
    @Bind(R.id.img_splash)
    ImageView img_splash;
    @Bind(R.id.ll_login)
    LinearLayout ll_login;
    @Bind(R.id.ll_login_click)
    LinearLayout ll_login_click;
    @Bind(R.id.tv_youke)
    TextView tv_youke;


    private PermissionHelper permissionHelper;
    public static Activity instance = null;
    AlphaAnimation animation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        instance = this;
        initRoot();
        iniDomain();
        initSplashView();
        initAnimation();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                permissionJudg();
            }
        }, 2000);
        loadApp();
    }

    Handler handler = new Handler();

    @OnClick({R.id.ll_login_click, R.id.tv_youke})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.ll_login_click:
                Intent intent4 = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent4, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.tv_youke:
                //判断是否获取用户信息，未获取，先本地获取一下
                if (SharedUtils.getBoolean("isGetorcreate")) {
                    jumpActivity(HomeActivity.class);
                    finish();
                } else {
                    loadGetorcreate("youke");
                }
                break;
        }
    }

    public void initRoot() {
        //判断该Activity是不是任务空间的源Activity，“非”也就是说是被系统重新实例化出来--
        //这段代码作用:第一次安装,直接打开,点击退出,一次就退出,不会弹出"再点一次退出应用"
        if (!this.isTaskRoot()) {
            //如果你就放在launcher Activity中话，这里可以直接return了
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;//finish()之后该活动会继续执行后面的代码，你可以logCat验证，加return避免可能的exception
            }
        }
    }

    public void iniDomain() {
        //域名申请---初始化
        if (SharedUtils.getString("domain") == null) {
            L.e("域名---初始化");
            HttpNet.SetRequestUrl(HttpUrl.DOMAIN1);
        } else {
            if (isDomain1()) {
                HttpNet.SetRequestUrl(HttpUrl.DOMAIN1);
            } else if (HttpUrl.DOMAIN2.equals(SharedUtils.getString("currentDomain")) && !SharedUtils.getBoolean("TwoDomain")) {
                HttpNet.SetRequestUrl(HttpUrl.DOMAIN2);
            } else if (SharedUtils.getString("domain1") != null || "".equals(SharedUtils.getString("domain1"))) {
                HttpNet.SetRequestUrl(SharedUtils.getString("domain1"));
            } else if (SharedUtils.getString("domain2") != null || "".equals(SharedUtils.getString("domain2"))) {
                HttpNet.SetRequestUrl(SharedUtils.getString("domain2"));
            } else if (SharedUtils.getString("domain3") != null || "".equals(SharedUtils.getString("domain3"))) {
                HttpNet.SetRequestUrl(SharedUtils.getString("domain3"));
            } else {
                HttpNet.SetRequestUrl(HttpUrl.DOMAIN1);
            }
        }
        if (!SharedUtils.getBoolean("isGetorcreate")) {
            loadDomains();
        }
    }

    public boolean isDomain1() {
        boolean flag = false;
        if (SharedUtils.getString("currentDomain") == null) {
            flag = true;
        } else if (HttpUrl.DOMAIN1.equals(SharedUtils.getString("currentDomain")) && !SharedUtils.getBoolean("OneDomain")) {
            flag = true;
        }
        return flag;
    }

    public void initAnimation() {
        animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(3000);
        img_splash.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //第一次的话，就在动画结束之后，在请求。因为第一次有权限申请。权限申请那里第一次的话，走动画之后的网络
                //不是第一次的话，就走权限申请的网络请求（后台运行）。同时前台判断是否绑定过邮箱 绑定了直接跳转到home
                //未绑定就显示登录和游客登录页面
                if (!SharedUtils.getBoolean("isGetorcreate")) {
                    ll_login.setVisibility(View.VISIBLE);
                    img_splash.setVisibility(View.GONE);
//                    loadGetorcreate("zidong");
                } else {
                    LoginBean loginBean = SharedUtils.getObject(SharedUtils.LOGIN_BEAN);
                    if (loginBean != null) {
                        String email = loginBean.getEmail();
                        L.e("email--" + email);
                        if (email != null && email != "") {
                            //已经绑定  可以直接跳转到home
                            jumpActivity(HomeActivity.class);
                            finish();
                        } else {
                            L.e("email--" + email + "---没有绑定邮箱显示登录页面");
                            ll_login.setVisibility(View.VISIBLE);
                            img_splash.setVisibility(View.GONE);
                        }
                    } else {
                        ll_login.setVisibility(View.VISIBLE);
                        img_splash.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {


            }
        });
    }

    public void initSplashView() {
        if (SharedUtils.getObject(SharedUtils.APP_BEAN) != null && !("".equals(SharedUtils.getObject(SharedUtils.APP_BEAN)))) {
            //判断是否事第一次启动安装，第一次不显示闪屏，其余则根据isShow进行判断是否显示-true-显示
            AppManagerBean appManagerBean = SharedUtils.getObject(SharedUtils.APP_BEAN);
            boolean flag = appManagerBean.getApp().getMeta().getRecommond().isIsShow();
            final String type = appManagerBean.getApp().getMeta().getRecommond().getType();
            final String apppkg = appManagerBean.getApp().getMeta().getRecommond().getApppkg();
            final String imageUrl = appManagerBean.getApp().getMeta().getRecommond().getImageurl();
            L.e("--type-" + type + "--apppkg--" + apppkg);
            if (flag) {
                SplashView.showSplashView(this, 3, R.drawable.default_img, new SplashView.OnSplashViewActionListener() {
                    @Override
                    public void onSplashImageClick(String actionUrl) {
                        //点击
                        Log.d("SplashView", "img clicked. actionUrl: " + actionUrl);
                        Toast.makeText(SplashActivity.this, "img clicked.", Toast.LENGTH_SHORT).show();
                        if ("google".equals(type)) {
                            //type===google 代表推广vpn，否则就是正常广告，使用浏览器打开 apppkg-是包名
                            JumpStoreUtils.launchAppDetail(apppkg, Content.GOOGLE_STORE, SplashActivity.this);
                        } else {
                            //打开浏览器 apppkg-是广告连接
                            Uri uri = Uri.parse(actionUrl);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onSplashViewDismiss(boolean initiativeDismiss) {
                        Log.d("SplashView", "dismissed, initiativeDismiss: " + initiativeDismiss);
                        //消失
                    }
                });
                SplashView.updateSplashData(this, imageUrl, apppkg);
            }
        }
    }

    public void permissionJudg() {
        permissionHelper = new PermissionHelper(SplashActivity.this, new PermissionHelper.PermissionModel[]{
                new PermissionHelper.PermissionModel(HttpCode.Storage, Manifest.permission.WRITE_EXTERNAL_STORAGE, HttpCode.Storage_Detail, PermissionHelper.WRITE_EXTERNAL_STORAGE_CODE),
                new PermissionHelper.PermissionModel(HttpCode.Device_Information, Manifest.permission.READ_PHONE_STATE, HttpCode.Device_Information_Detail, PermissionHelper.ACCESS_COARSE_LOCATION_CODE)
        });
        permissionHelper.setOnApplyPermissionListener(new PermissionHelper.OnApplyPermissionListener() {
            @Override
            public void onAfterApplyAllPermission() {
                //通过权限
                //判断是否事第一次，不是第一次，就不用等动画结束，直接网络请求。
                //是第一次，就要等待动画结束
                if (!SharedUtils.getBoolean("isGetorcreate")) {
                    if (animation.hasEnded()) {
                        loadGetorcreate("zidong");
                    }
                }else{
                    loadGetorcreate("zidong");
                }
            }
        });
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //通过权限
            if (!SharedUtils.getBoolean("isGetorcreate")) {
                if (animation.hasEnded()) {
                    loadGetorcreate("zidong");
                }
            }else{
                loadGetorcreate("zidong");
            }

        } else {
            if (permissionHelper.isAllRequestedPermissionGranted()) {
                //通过所有权限
                if (!SharedUtils.getBoolean("isGetorcreate")) {
                    if (animation.hasEnded()) {
                        loadGetorcreate("zidong");
                    }
                }else{
                    loadGetorcreate("zidong");
                }
            } else {
                permissionHelper.applyPermissions();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        permissionHelper.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void loadApp() {
        HttpNet.httpGetApp(HttpUrl.APP_MANAGER, MyApplication.getInstance(), null, new RequestLinstener() {
            @Override
            public void OnSuccess(String responseString) {
                try {
                    Gson gson = new Gson();
                    AppManagerBean appManagerBean = gson.fromJson(responseString, AppManagerBean.class);
                    SharedUtils.saveObject(SharedUtils.APP_BEAN, appManagerBean);
                    AppManagerBean appManagerBean1 = SharedUtils.getObject(SharedUtils.APP_BEAN);
                    L.e("app---getBuyUrl-" + appManagerBean.getApp().getMeta().getBuyUrl());
                    L.e("app--ishow--" + appManagerBean.getApp().getMeta().isIsShowRate());
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

    public void loadDomains() {
        HttpNet.httpGetNoHeader(HttpUrl.DOMAIN_ADDRESS, MyApplication.getInstance(), null, new RequestLinstener() {
            @Override
            public void OnSuccess(String responseString) {
                try {
                    String domain = AesUtils.decrypt(responseString);
                    L.e("域名----" + domain);
                    SharedUtils.putString("domain", domain);
                    if (TextUtils.isEmpty(domain)) {
                        ToastUtils.showMessage(HttpCode.HOST_NULL);
                        return;
                    }
                    String aaArray = Content.spltString(domain, 1);
                    String bbArray = Content.spltString(domain, 2);
                    String ccArray = Content.spltString(domain, 3);
                    L.e("aaArray--------" + aaArray);
                    L.e("bbArray--------" + bbArray);
                    L.e("ccArray--------" + ccArray);
                    SharedUtils.putString("domain1", aaArray);
                    SharedUtils.putString("domain2", bbArray);
                    SharedUtils.putString("domain3", ccArray);
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

    public void loadGetorcreate(final String type) {
        L.e("type--" + type);
        if (!SharedUtils.getBoolean("isGetorcreate")) {
            LoadDialog.show(SplashActivity.this, HttpCode.LOADING);
        }
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
                    //获取节点
                    loadSnodes(type);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void OnError(String errorString) {
                ToastUtils.showMessage(errorString);
                if (!SharedUtils.getBoolean("isGetorcreate")) {
                    LoadDialog.dismiss(SplashActivity.this);
                }
            }
        });
    }

    public void loadSnodes(final String type) {
        HttpNet.httpGet(HttpUrl.SNODES, MyApplication.getInstance(), null, new RequestLinstener() {
            @Override
            public void OnSuccess(String responseString) {
                try {
                    if (!SharedUtils.getBoolean("isGetorcreate")) {
                        LoadDialog.dismiss(SplashActivity.this);
                    }
                    JSONObject object = new JSONObject(responseString);
                    String secret = object.get("secret").toString();
                    String aa = AesUtils.decrypt(secret);
                    L.e("节点---" + aa);
                    SharedUtils.putString("host", aa);//获取host 保存在本地
                    //添加标识--获取成功
                    SharedUtils.putBoolean("isGetorcreate", true);
                    if ("youke".equals(type)) {
                        jumpActivity(HomeActivity.class);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void OnError(String errorString) {
                ToastUtils.showMessage(HttpCode.HOST_NULL);
                if (!SharedUtils.getBoolean("isGetorcreate")) {
                    LoadDialog.dismiss(SplashActivity.this);
                }
            }
        });
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
            Toast.makeText(SplashActivity.this, HttpCode.Press_again, Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
