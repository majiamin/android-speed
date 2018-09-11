package com.speed.faster.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.speed.faster.R;
import com.speed.faster.bean.AppManagerBean;
import com.speed.faster.https.HttpCode;


/**
 * Created by mjm on 2017/5/17.
 * 更新对话框
 */

public class UpdateDialog extends Dialog implements View.OnClickListener {
    private UpdateDialogOperate aDialogOperate; // 操作接口
    private Context context;
    private TextView ver;
    private TextView content;
    private Button update_ok;
    private Button upadte_cancle;
    private AppManagerBean.AppBean.MetaBean.UpdateBean upadtebean;

    private TextView tip;

    public UpdateDialog(Context context) {
        super(context, R.style.common_dialog);
        this.context = context;
        this.setContentView(R.layout.dialog_update);
        ver = (TextView) findViewById(R.id.update_ver);
        content = (TextView) findViewById(R.id.update_content);
        update_ok = (Button) findViewById(R.id.update_btn_ok);
        upadte_cancle = (Button) findViewById(R.id.update_btn_cancle);
        tip = (TextView) findViewById(R.id.update_tip);
        update_ok.setOnClickListener(this);
        upadte_cancle.setOnClickListener(this);

    }

    public void setData(AppManagerBean.AppBean.MetaBean.UpdateBean upadtebean, boolean flag, UpdateDialogOperate aDialogOperate) {
        this.aDialogOperate = aDialogOperate;
        this.upadtebean = upadtebean;


        upadte_cancle.setVisibility(View.VISIBLE);
        tip.setVisibility(View.GONE);
        //关闭---以后再说。。。
        upadte_cancle.setVisibility(View.GONE);
        update_ok.setVisibility(View.VISIBLE);
        ver.setText(upadtebean.getVersionName());
        content.setText(HttpCode.DIALOG_VERSION_CONTENT);
        this.setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_btn_cancle:
                aDialogOperate.executeCancel("");
                break;
            case R.id.update_btn_ok:
                aDialogOperate.executeCommit("");
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
