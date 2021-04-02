package com.newtonacademic.newtontutors.utils;

import android.content.Context;

import com.newtonacademic.newtontutors.R;
import com.newtonacademic.newtontutors.utils.toast.ToastUtils;

/**
 * @author laiyongyang
 * @date 2020-05-01
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class AppTipsUtils {

    /**
     * 检查网络<br/>
     * 网络未连接会弹出Toast提示：网络异常
     *
     * @return true : 网络已连接，false : 网络未连接
     */
    public static boolean checkNetworkState(Context context) {
        if (!NetworkUtils.isNetworkValidity(context)) {
            ToastUtils.ShowToastShort(context, context.getString(R.string.current_network_unavailable));
            return false;
        }

        return true;
    }
}
