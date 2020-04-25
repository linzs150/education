package com.one.education.network;


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.one.education.commons.LogUtils;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.reflect.Type;

import retrofit.Converter;

/**
 *
 *
 */
public class NetJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;//gson对象
    private final Type type;
    //    private final TypeAdapter<T> adapter;

    /**
     * 构造器
     */
    public NetJsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody responseBody) throws IOException {
        String response = responseBody.string();
        LogUtils.e("education", "ResponseBody：after des:" + response);
        return JSON.parseObject(response, type);
        //        定义好的泛型对象
        //        return JSON.parseObject(response, type);
    }
}
