/**
 * @author 马佳敏
 * @time 2016-3-16 下午5:47:32
 */
package com.speed.faster.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.speed.faster.MyApplication;
import com.speed.faster.R;


/**
 * @author 马佳敏
 * @time 2016-3-16 下午5:47:32
 * @描述 Toast工具类
 */
public class ToastUtils {
    private static final CharSequence MSG_NO_ACCESS = "对不起，您无权限操作";

    /**
     * @param target
     * @param toastMsg
     * @return 不空 false, 空 check
     * @描述 检查表单是否为空
     * @author 马佳敏
     */
    public static boolean checkForEmpty(String target, String toastMsg) {
        if (TextUtils.isEmpty(target)) {
            showMessage(toastMsg);
            return true;
        }
        return false;
    }

    /**
     * @param msg --提示信息
     * @描述 Toast发送消息，默认Toast.LENGTH_SHORT
     * @author 马佳敏
     */
    public static void showMessage(final String msg) {
        Context context = MyApplication.getInstance();
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param msg --提示信息
     * @描述 Toast发送消息，默认Toast.LENGTH_LONG
     * @author 马佳敏
     */
    public static void showMessageL(final String msg) {
        Context context = MyApplication.getInstance();
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * @param --提示信息
     * @描述 Toast发送“没有权限”消息，默认Toast.LENGTH_SHORT
     * @author 马佳敏
     */
    public static void showNoAccess() {
        Context context = MyApplication.getInstance();
        Toast.makeText(context, MSG_NO_ACCESS, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param --提示信息
     * @描述 Toast发送 没有获取到数据 消息
     * @author 马佳敏
     */
    public static void showNoDatas() {
        showMessage("No Data");

    }

    /**
     * @param --提示信息
     * @描述 Toast发送 位置在中间 时间长
     * @author 马佳敏
     */
    public static void showMessageCenterL(String message) {
        Context context = MyApplication.getInstance();
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * @param --提示信息
     * @描述 Toast发送 带图片的 位置在中间 时间长
     * @author 马佳敏
     */
    public static void showMessageImageL(String message) {
        Context context = MyApplication.getInstance();
        Toast toast = Toast.makeText(context,
                message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(context);
        imageCodeProject.setImageResource(R.drawable.aa);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }
}
