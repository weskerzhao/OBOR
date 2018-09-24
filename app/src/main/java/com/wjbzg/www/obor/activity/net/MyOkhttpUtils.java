package com.wjbzg.www.obor.activity.net;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wjbzg.www.obor.activity.LoginActivity;
import com.wjbzg.www.obor.entity.LoginDTO;
import com.wjbzg.www.obor.utils.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.log.LoggerInterceptor;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2018\8\29 0029.
 */

public class MyOkhttpUtils {

    public static RequestCall okHttpPost(Map<String,String> maps,String method){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
        PostFormBuilder url = OkHttpUtils
                .post()
                .url(Constant.APP_URL + method);

        for (String key:maps.keySet()) {
            url.addParams(key,maps.get(key));
        }

    return  url.build();
    }
}
