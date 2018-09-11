package com.speed.faster.activity;

import android.animation.LayoutTransition;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.speed.faster.Content;
import com.speed.faster.R;
import com.speed.faster.bean.AppManagerBean;
import com.speed.faster.bean.LoginBean;
import com.speed.faster.https.HttpCode;
import com.speed.faster.utils.JumpStoreUtils;
import com.speed.faster.utils.L;
import com.speed.faster.utils.SharedUtils;
import com.speed.faster.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mjm  on 2018/7/11 14:29
 * 个人设置页面
 */
public class SetActivity extends BaseActivity {
    @Bind(R.id.username)
    TextView tv_username;
    @Bind(R.id.tv_superVipExp)
    TextView superVipExp;
    @Bind(R.id.ll_aa)
    LinearLayout ll_aa;
    @Bind(R.id.ll_bb)
    LinearLayout ll_bb;
    @Bind(R.id.ll_cc)
    LinearLayout ll_cc;
    @Bind(R.id.ll_dd)
    LinearLayout ll_dd;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
        //切换动画
        Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.fade);
        getWindow().setEnterTransition(explode);
        //退出时使用
        getWindow().setExitTransition(explode);

        animation = AnimationUtils.loadAnimation(SetActivity.this, R.anim.layoutanim);
//        ll_aa.startAnimation(animation);
        ll_bb.startAnimation(animation);
        ll_cc.startAnimation(animation);
        ll_dd.startAnimation(animation);

        initView();
    }

    private void initView() {
        LoginBean loginBean = SharedUtils.getObject(SharedUtils.LOGIN_BEAN);
        if ("CN".equals(HttpCode.ABLE)) {
            tv_username.setText("用户ID  :" + loginBean.getUsername());
        } else {
            tv_username.setText("ID  :" + loginBean.getUsername());
        }

        if (TextUtils.isEmpty(loginBean.getVipExp())) {
            superVipExp.setVisibility(View.GONE);
        } else {
            if ("CN".equals(HttpCode.ABLE)) {
                superVipExp.setText("到期时间 :" + UTCDate(loginBean.getVipExp()));
            } else {
                superVipExp.setText("Expire date :" + UTCDate(loginBean.getVipExp()));
            }

        }

    }

    @OnClick({R.id.back, R.id.rl_pass, R.id.rl_qiehuan, R.id.rl_email, R.id.rl_help, R.id.rl_contact, R.id.rl_score, R.id.rl_secrey, R.id.rl_service})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back:
                finishAfterTransition();
                break;
            case R.id.rl_pass:
//                jumpActivity(ChangPasswordActivity.class);
                //动画效果
                Intent intent = new Intent(SetActivity.this, ChangPasswordActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
//                startActivity(intent, ActivityOptions.ma(this).toBundle());
                break;
            case R.id.rl_qiehuan:
                Intent intent3 = new Intent(this, SwitchAccountActivity.class);
                startActivityForResult(intent3, 300, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.rl_email:
                Intent intent4 = new Intent(SetActivity.this, BindEmailActivity.class);
                startActivity(intent4, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.rl_help:
                Intent intent5 = new Intent(SetActivity.this, HelpActivity.class);
                startActivity(intent5, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.rl_contact:
                AppManagerBean appManagerBean = SharedUtils.getObject(SharedUtils.APP_BEAN);
                String key = appManagerBean.getApp().getMeta().getQQ();
                L.e("key------"+key);
                boolean flag = JumpStoreUtils.joinQQGroup(key, SetActivity.this);
                L.e("联系我们---" + flag);
                break;
            case R.id.rl_score:
                JumpStoreUtils.launchAppDetail("com.speed.faster", Content.GOOGLE_STORE, SetActivity.this);
                break;
            case R.id.rl_secrey:
                Intent intent1 = new Intent(SetActivity.this, WebActivity.class);
                intent1.putExtra("type", "secrey");
                startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.rl_service:
                Intent intent2 = new Intent(SetActivity.this, WebActivity.class);
                intent2.putExtra("type", "service");
                startActivity(intent2, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300 && resultCode == RESULT_OK) {
            initView();
        }
    }

    public String UTCDate(String str) {
        String dateString = "";
        try {
            str = str.replace("Z", " UTC");
            SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = utcFormat.parse(str);
            dateString = defaultFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }
}
