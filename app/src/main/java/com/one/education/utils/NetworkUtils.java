package com.one.education.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author laiyongyang
 * @date 2020-05-01
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class NetworkUtils {
    /**
     * 判断网络是否存在
     *
     * @param ctx 上下文
     * @return bool
     */
    public static boolean isNetworkValidity(Context ctx) {
        ConnectivityManager manager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }
        return true;
    }
}
