package com.one.education.network;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.one.education.commons.Constants;
import com.one.mylibrary.ConstantGlobal;

import retrofit.Retrofit;

/**
 *
 *
 */

public class ApiClient {

    public static String BASE_URL = ConstantGlobal.Net.URL + "/";

    private ApiService apiService;

    private ApiClient() {
        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(ApiOkHttpClient.getClient())
                .addConverterFactory(NetCustomConverterFactory.create(gson)).build();
        //                .addConverterFactory(GsonConverterFactory.create()).build();

        apiService = retrofit.create(ApiService.class);

    }

    public static ApiService getApiService() {

        return ApiClientHolder.INSTANCE.apiService;

    }

    private static class ApiClientHolder {
        public static ApiClient INSTANCE = new ApiClient();
    }

}
