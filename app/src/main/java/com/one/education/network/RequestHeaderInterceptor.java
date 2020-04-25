package com.one.education.network;


import android.text.TextUtils;

import com.one.education.commons.Constants;
import com.one.education.commons.SharedPreferencesUtils;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 *
 *
 */
public class RequestHeaderInterceptor implements Interceptor {
    private String mac;


    @Override
    public Response intercept(Chain chain) throws IOException {
        String hrand = generateHrand();
        Request original = chain.request();
        Request.Builder builder = original.newBuilder();

        if (!TextUtils.isEmpty(SharedPreferencesUtils.getInstance().getString(Constants.TOKEN, ""))) {
            builder.header("Authorization", "TOKEN " + SharedPreferencesUtils.getInstance().getString(Constants.TOKEN, "0"));


        }

        //        builder.header("")
        //                .header("clienttype", "android")
        //                .header("clientsysver", "android" + Build.VERSION.SDK_INT)
        //                .header("ver", "v1.0")
        //                .header("device", getMac())
        //                .header("hrand", hrand)
        //                .header("Content-Type", "application/json;charset=UTF-8")
        //                .header("transfer-encoding", "chunked")
        //                .header("date", new Date(System.currentTimeMillis()) + "");
        //                .header("hsign", hsign);
        //Head设置  请求过滤
        builder.header("X-User-Agent",String.format("{%s/%s}{%s/%s}{%s}{%s}{%s}{%s}{%s}{%s}{%s}",));

        builder.method(original.method(), original.body());
        return chain.proceed(builder.build());
    }

    private String generateHrand() {
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        return time + randomStr();
    }

    private static String randomStr() {
        return String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
    }
}
