package com.one.education.network;


import com.one.education.beans.BaseBean;
import com.one.education.commons.LogUtils;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 *
 *
 */
public abstract class RestNewCallBack<T extends BaseBean> implements Callback<T> {

    public abstract void success(T t);

    public abstract void failure(RestError error);

    @Override
    public void onResponse(Response<T> response, Retrofit retrofit) {
        if (response.isSuccess()) {
            if (response.body().getStatus().equals("1")) {
                success(response.body());
            } else {
                failure(new RestError(response.body().getStatus(), response.body().getDescript()));
            }
        } else

        {
            try {
                //                String errorMsg = response.errorBody().string();
                String errorMsg = "客官,咱家服务器开了个小差,您稍候!";
                failure(new RestError(response.code() + "", errorMsg));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
        LogUtils.e("RestNewCallBack", "retrofit onFailure");
        failure(new RestError("-1", "网络异常"));
    }


}
