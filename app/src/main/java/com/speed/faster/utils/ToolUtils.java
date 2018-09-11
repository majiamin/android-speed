package com.speed.faster.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by mjm on 2018/7/19.
 * 简易工具类
 */

public class ToolUtils {

    /**
     * 根据name从assets中读取文件
     * fileName--文件名（us）
     */
    public static Bitmap getImageFromAssetsFile(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            //自动补全后面的。.png
            InputStream is = am.open(fileName.trim().toLowerCase() + ".png");
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

}
