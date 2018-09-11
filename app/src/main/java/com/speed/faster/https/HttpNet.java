package com.speed.faster.https;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.speed.faster.MyApplication;
import com.speed.faster.utils.AuthorizationUtils;
import com.speed.faster.utils.JwtUtils;
import com.speed.faster.utils.L;
import com.speed.faster.R;
import com.speed.faster.utils.SharedUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by mjm on 2018/7/9.
 * 网络请求类
 */

public class HttpNet {
    private static OkHttpClient mOkHttpClient;
    private static Handler mHandler;
    private static String url;

    /**
     * 初始化mOkHttpClient
     */
    public static void initOkHttpClient() {
        File sdcache = MyApplication.getInstance().getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        mOkHttpClient = builder.build();
//        mHandler=new Handler(MyApplication.getInstance().getMainLooper());
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 图片加载带缓存
     *
     * @param url
     * @param context
     * @param image
     */
    public static void httpImage(String url, Context context, ImageView image) {
        //网络加载图片有缓存
        Glide.with(context)
                .load(url)
//                .asGif()//只加载动态图
                .placeholder(R.drawable.aa)//加载之前默认图
                .error(R.drawable.aa)//加载出现错误显示错误图
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存设置-设置不缓存
//                .override(200,150)//加载图片大小
                .into(image);
        // 加载本地图片
//        File file = new File(getExternalCacheDir() + "/image.jpg");
//        Glide.with(this).load(file).into(img);
// 加载应用资源
//        int resource = R.drawable.image;
//        Glide.with(this).load(resource).into(img);
// 加载二进制流
//        byte[] image = getImageBytes();
//        Glide.with(this).load(image).into(img);
// 加载Uri对象
//        Uri imageUri = getImageUri();
//        Glide.with(this).load(imageUri).into(img);
    }

    /**
     * 图片加载不带缓存
     *
     * @param url
     * @param context
     * @param image
     */
    public static void httpImageNoCache(String url, Context context, ImageView image) {
        //网络加载图片无缓存
        Glide.with(context)
                .load(url)
//                .asGif()//只加载动态图
                .placeholder(R.drawable.aa)//加载之前默认图
                .error(R.drawable.aa)//加载出现错误显示错误图
                .diskCacheStrategy(DiskCacheStrategy.NONE)//缓存设置-设置不缓存
//                .override(200,150)//加载图片大小
                .into(image);
    }

    /**
     * get请求,且带其他参数
     * 注: 回调返回的是子线程
     *
     * @param url
     * @param context
     * @param params
     * @param requestListener 专门给请求域名接口使用
     */
    public static void httpGetApp(String url, final Context context, Map<String, String> params, final RequestLinstener requestListener) {
        try {
            // 打印日志
            getHttpUrl(GetRequestUrl() + url, params);
            Request request = new Request.Builder()
                    .url(GetRequestUrl() + url)
                    .build();
            Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final int code = response.code();
                    L.e("response.code()---" + code);

                    final String json = response.body().string();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //只要不是200全部是错误码
                                if (code != 200) {
                                    requestListener.OnError("code：" + code);
                                    return;
                                }
                                L.e("jsonString--" + json);
                                requestListener.OnSuccess(json);
                            } catch (Exception e2) {
                                L.e("-----" + e2.getClass());
                                e2.printStackTrace();
                            }
                        }
                    });
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    try {
                        //换域名
                        Intent intent22 = new Intent("com.speed.action.broadcast");
                        intent22.putExtra("freshNet", true);
                        context.sendBroadcast(intent22);
                        if (TextUtils.isEmpty(e.getMessage()) == false) {
                            requestListener.OnError(HttpCode.FILD);
                            L.e("网络请求出错---" + e.getMessage());
                        } else {
                            requestListener.OnError(HttpCode.FILD_NULL);
                            L.e("未知网络错误---" + e.getMessage());
                        }
                    } catch (Exception e1) {
                        L.e("onFailure---" + e1.getMessage());
                        e1.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get请求,且带其他参数
     * 注: 回调返回的是子线程
     *
     * @param url
     * @param context
     * @param params
     * @param requestListener 专门给请求域名接口使用
     */
    public static void httpGetNoHeader(String url, final Context context, Map<String, String> params, final RequestLinstener requestListener) {
        try {
            // 打印日志
            getHttpUrl(url, params);
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final int code = response.code();
                    L.e("response.code()---" + code);

                    final String json = response.body().string();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //只要不是200全部是错误码
                                if (code != 200) {
                                    requestListener.OnError("code：" + code);
                                    return;
                                }
                                L.e("jsonString--" + json);
                                requestListener.OnSuccess(json);
                            } catch (Exception e2) {
                                L.e("-----" + e2.getClass());
                                e2.printStackTrace();
                            }
                        }
                    });
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    try {
                        //换域名
                        Intent intent22 = new Intent("com.speed.action.broadcast");
                        intent22.putExtra("freshNet", true);
                        context.sendBroadcast(intent22);
                        if (TextUtils.isEmpty(e.getMessage()) == false) {
                            requestListener.OnError(HttpCode.FILD);
                            L.e("网络请求出错---" + e.getMessage());
                        } else {
                            requestListener.OnError(HttpCode.FILD_NULL);
                            L.e("未知网络错误---" + e.getMessage());
                        }
                    } catch (Exception e1) {
                        L.e("onFailure---" + e1.getMessage());
                        e1.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get请求,且带其他参数
     * 注: 回调返回的是子线程
     *
     * @param url
     * @param context
     * @param params
     * @param requestListener
     */
    public static void httpGet(final String url, final Context context, Map<String, String> params, final RequestLinstener requestListener) {
        try {
            // 打印日志
            getHttpUrl(GetRequestUrl() + url, params);
            Request request = new Request.Builder()
                    .url(GetRequestUrl() + url)
                    .addHeader("Authorization", "Bearer " + AuthorizationUtils.AuthorizationJwt(context))
                    .build();
            Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final int code = response.code();
                    L.e("response.code()---" + code);

                    final String json = response.body().string();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //只要不是200全部是错误码
                                if (code != 200) {
                                    requestListener.OnError("code：" + code);
                                    return;
                                }
                                SharedUtils.putString("currentDomain", HttpNet.GetRequestUrl());
                                L.e("jsonString--" + json);
                                requestListener.OnSuccess(json);
                            } catch (Exception e2) {
                                L.e("-----" + e2.getClass());
                                e2.printStackTrace();
                            }
                        }
                    });
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    try {
                        //换域名
                        Intent intent22 = new Intent("com.speed.action.broadcast");
                        intent22.putExtra("freshNet", true);
//                        intent22.putExtra("url",url);
                        context.sendBroadcast(intent22);

                        if (TextUtils.isEmpty(e.getMessage()) == false) {
                            requestListener.OnError(HttpCode.FILD);

                            L.e("网络请求出错---" + e.getMessage());
                        } else {
                            requestListener.OnError(HttpCode.FILD_NULL);
                            L.e("未知网络错误---" + e.getMessage());
                        }
                    } catch (Exception e1) {
                        L.e("onFailure---" + e1.getMessage());
                        e1.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * post请求
     *
     * @param url
     * @param context
     * @param BodyParams
     * @param requestListener
     */
    public static void httpPost(String url, Context context, Map<String, String> BodyParams, RequestLinstener requestListener) {
        try {
            // 打印日志
            getHttpUrl(GetRequestUrl() + url, BodyParams);
            //初始化当前网络
//            initOkHttpClient();
            Request request = new Request.Builder()
                    .url(GetRequestUrl() + url)
                    .post(SetRequestBody(BodyParams))
                    .addHeader("Authorization", "Bearer " + AuthorizationUtils.AuthorizationJwt(context))
                    .build();
            Call call = mOkHttpClient.newCall(request);
            call.enqueue(new MyStringCallBack(requestListener, context, mHandler));
//            call.enqueue(new MyStringCallBack(requestListener,context));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * put请求
     *
     * @param url
     * @param context
     * @param BodyParams
     * @param requestListener
     */
    public static void httpPut(String url, Context context, Map<String, String> BodyParams, RequestLinstener requestListener) {
        try {
            // 打印日志
            getHttpUrl(GetRequestUrl() + url, BodyParams);
            //初始化当前网络
//            initOkHttpClient();
            Request request = new Request.Builder()
                    .url(GetRequestUrl() + url)
                    .put(SetRequestBody(BodyParams))
                    .addHeader("Authorization", "Bearer " + AuthorizationUtils.AuthorizationJwt(context))
                    .build();
            Call call = mOkHttpClient.newCall(request);
            call.enqueue(new MyStringCallBack(requestListener, context, mHandler));
//            call.enqueue(new MyStringCallBack(requestListener,context));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传单个文件单个参数,多个文件多个参数,且带其他参数
     *
     * @param url
     * @param context
     * @param BodyParams      参数集合
     * @param paramsFile      文件集合
     * @param requestListener
     */
    public static void httpUploadFile(String url, Context context, Map<String, String> BodyParams, Map<String, File> paramsFile, RequestLinstener requestListener) {
        // 打印日志
        getHttpUrl(url, BodyParams);
        //初始化当前网络
//        initOkHttpClient();
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //添加图片--将map集合整合到builder里面
        if (paramsFile != null) {
            Iterator<String> iterator = paramsFile.keySet().iterator();
            String keyFile = "";
            while (iterator.hasNext()) {
                keyFile = iterator.next().toString();
                //添加其他参数
                builder.addFormDataPart(keyFile, paramsFile.get(keyFile).getName(), RequestBody.create(MEDIA_TYPE_PNG, paramsFile.get(keyFile)));
                L.e("添加图片===" + keyFile + "====" + paramsFile.get(keyFile).getName() + "===" + paramsFile.get(keyFile));
            }
        }
        //添加其他非图片参数--将map集合整合到builder里面
        if (BodyParams != null) {
            for (String key : BodyParams.keySet()) {
                builder.addFormDataPart(key, BodyParams.get(key));
                L.e("添加其他参数===" + key + "====" + BodyParams.get(key));
            }
        }
        //此方法左右和上面一样-暂不删除,留待以后使用
        //添加其他非图片参数--将map集合整合到builder里面
//        if(BodyParams != null){
//            Iterator<String> iterator = BodyParams.keySet().iterator();
//            String key = "";
//            while (iterator.hasNext()) {
//                key = iterator.next().toString();
//                //添加其他参数
//                builder.addFormDataPart(key,BodyParams.get(key));
//                L.e( "添加其他参数==="+key+"===="+BodyParams.get(key));
//            }
//        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)//地址
                .post(requestBody)//添加请求体
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new MyFileCallBack(requestListener, context, mHandler));
    }

    /**
     * 上传多个文件,单个参数,且带其他参数
     *
     * @param url
     * @param context
     * @param BodyParams      参数集合
     * @param pic_key         文件参数
     * @param files           多个文件list
     * @param requestListener
     */
    public static void httpUploadMoreFile(String url, Context context, Map<String, String> BodyParams, String pic_key, List<File> files, RequestLinstener requestListener) {
        // 打印日志
        getHttpUrl(url, BodyParams);
        //初始化当前网络
//        initOkHttpClient();
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //添加图片--将map集合整合到builder里面
        if (files != null) {
            for (File file : files) {
                builder.addFormDataPart(pic_key, file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
            }
        }
        //添加其他非图片参数--将map集合整合到builder里面
        if (BodyParams != null) {
            for (String key : BodyParams.keySet()) {
                builder.addFormDataPart(key, BodyParams.get(key));
                L.e("添加其他参数===" + key + "====" + BodyParams.get(key));
            }
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)//地址
                .post(requestBody)//添加请求体
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new MyStringCallBack(requestListener, context, mHandler));
    }
    /**
     * 下载apk
     */
    public static void httpDownLoadApk(String url, Context context, Map<String, String> BodyParams, okhttp3.Callback callback) {
        // 打印日志
        getHttpUrl(url, BodyParams);

        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);

    }
    /**
     * 下载文件
     */
    public static void httpDownLoadFile(String url, Context context, Map<String, String> BodyParams, RequestLinstener requestListener) {
        // 打印日志
        getHttpUrl(url, BodyParams);

        Request request = new Request.Builder()
                .url(url)
                .post(SetRequestBody(BodyParams))
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new MyFileCallBack(requestListener, context, mHandler));

    }

    /**
     * 拼接网络地址和参数
     */
    private static String getHttpUrl(String url, Map<String, String> params) {
        StringBuffer sb = new StringBuffer();
        String httpUrl;
        sb.append(url + "?");
        // 排除参数为空的情况
        if (params != null) {
            for (String key : params.keySet()) {
                Object value = params.get(key);
                if (value != null) {
                    sb.append(key + "=" + value.toString() + "&");
                } else {
                    sb.append(key + "=null&");
                }
            }

            httpUrl = sb.toString();
            if (httpUrl.endsWith("&")) {
                httpUrl = httpUrl.substring(0, sb.length() - 1);
            }
        } else {
            httpUrl = url;
        }
        // 日志里输出get形式的 url和params
        L.e("url---" + httpUrl);
        return httpUrl;
    }

    public static void SetRequestUrl(String str) {
        //创建用户/获取用户信息
        url = str;
    }

    public static String GetRequestUrl() {

        return url;
    }

    /**
     * get方法连接拼加参数
     *
     * @param mapParams
     * @return
     */
    private static String setGetUrlParams(Map<String, String> mapParams) {
        String strParams = "";
        if (mapParams != null) {
            Iterator<String> iterator = mapParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                strParams += "&" + key + "=" + mapParams.get(key);
            }
        }
        return strParams;
    }

    /**
     * post请求参数
     *
     * @param BodyParams
     * @return
     */
    private static RequestBody SetRequestBody(Map<String, String> BodyParams) {
        RequestBody body = null;
        FormBody.Builder formEncodingBuilder = new FormBody.Builder();
        if (BodyParams != null) {
            Iterator<String> iterator = BodyParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                formEncodingBuilder.add(key, BodyParams.get(key));
                L.e("post_Params===" + key + "====" + BodyParams.get(key));
            }
        }
        body = formEncodingBuilder.build();
        return body;
    }

    /**
     * 设置请求头
     *
     * @param headersParams
     * @return
     */
    private static Headers SetHeaders(Map<String, String> headersParams) {
        Headers headers = null;
        Headers.Builder headersbuilder = new Headers.Builder();

        if (headersParams != null) {
            Iterator<String> iterator = headersParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                headersbuilder.add(key, headersParams.get(key));
                Log.e("get http", "get_headers===" + key + "====" + headersParams.get(key));
            }
        }
        headers = headersbuilder.build();
        return headers;
    }
}
