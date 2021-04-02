package com.newtonacademic.newtontutors.network;


import com.google.gson.Gson;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;

/**
 *
 *
 */
public class NetCustomConverterFactory extends Converter.Factory {
    
    private final Gson gson;
    
    private NetCustomConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }
    
    public static NetCustomConverterFactory create() {
        return create(new Gson());
    }
    
    public static NetCustomConverterFactory create(Gson gson) {
        return new NetCustomConverterFactory(gson);
    }
    
    @Override
    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        return new NetJsonResponseBodyConverter<>(gson, type);
    }
    
    @Override
    public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
        return new NetJsonRequestBodyConverter<>(gson, type);
    }
}
