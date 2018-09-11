package com.speed.faster.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.speed.faster.R;
import com.speed.faster.utils.RotaterView;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by mjm on 2017/9/12.
 * Describe:
 */
public class ShowDialogActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sweep_code);
        ButterKnife.bind(this);
        //对话框宽度全屏
        Window w = getWindow();
        WindowManager.LayoutParams a = w.getAttributes();
        a.gravity = Gravity.CENTER;
        a.width = WindowManager.LayoutParams.FILL_PARENT;
        w.setAttributes(a);
        doRotate(true);
    }

    private void doRotate(boolean success) {
        RotaterView rotaterView = (RotaterView) findViewById(R.id.result_rotater);
        rotaterView.setColour(success ? 0xff4ae8ab : 0xfffe8c92);
        final ImageView statusView = (ImageView) findViewById(R.id.result_status);
        statusView.setVisibility(View.INVISIBLE);
        statusView.setImageResource(success ? R.drawable.result_success
                : R.drawable.result_failded);

        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(rotaterView,
                "progress", 0, 100);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.setDuration(600);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Animation scaleanimation = AnimationUtils.loadAnimation(
                        ShowDialogActivity.this, R.anim.scaleoutin);
                statusView.startAnimation(scaleanimation);
                statusView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
