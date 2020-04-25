package com.one.education.network;


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.one.education.commons.LogUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okio.Buffer;
import retrofit.Converter;

/**
 *
 *
 */
public class NetJsonRequestBodyConverter<T> implements Converter<T, RequestBody> {


    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final Gson gson;
    private final Type type;

    public NetJsonRequestBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        try {
            writer.write(encryptPrmIn(value));
            writer.flush();
        } catch (IOException e) {
            throw new AssertionError(e); // Writing to Buffer does no I/O.
        }
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
    }


    /***
     * 请求加密  加密方式暂时修改到拦截器里面去(RequestHeaderInterceptor)  登陆接口需要对报文进行修改
     * @see  RequestHeaderInterceptor
     * @param value
     * @return
     */
    private String encryptPrmIn(T value) {
        try {
            String strParams = JSON.toJSONString(value);
            LogUtils.e("JIGUANG", "RequestBody:before encyt=====:" + strParams);
//            L.d(strParams);
//            strParams = TripleDES.getEncString(strParams, HttpGetConstast.DES_CODE);
//            LogUtils.e("JIGUANG", "RequestBody:after encyt=====:" + strParams);
//            strParams = TripleDES.getEncString(strParams, HttpGetConstast.DES_CODE);
//            LogUtils.e("JIGUANG", "RequestBody:after encyt=====:" + strParams);
            return strParams;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
