package com.newtonacademic.newtontutors.network;


import com.newtonacademic.newtontutors.BuildConfig;
import com.squareup.okhttp.OkHttpClient;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 *
 *
 */
public class ApiOkHttpClient {


    public static OkHttpClient getClient() {
        return OkHttpClientHolder.client;
    }

    /**
     * 绕过验证
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        // Create a trust manager that does not validate certificate chains
        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }};

        //        SSLContext sc = SSLContext.getInstance("SSLv3");
        SSLContext sc = SSLContext.getInstance("TLSv1.2");
        sc.init(null, trustAllCerts, null);
        return sc;
    }

    private static final class OkHttpClientHolder {

        public final static OkHttpClient client = getHttpClient();

        static {
            RequestHeaderInterceptor headerInterceptor = new RequestHeaderInterceptor();
            //日志
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            if (BuildConfig.DEBUG) {
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            }
            client.interceptors().add(headerInterceptor);
            client.interceptors().add(interceptor);
            //            client.networkInterceptors().add(headerInterceptor);
            //            client.networkInterceptors().add(interceptor);

            //            client.interceptors().add(interceptor);
        }

        private static OkHttpClient getHttpClient() {
            try {
                final SSLSocketFactory sslSocketFactory = createIgnoreVerifySSL().getSocketFactory();

                OkHttpClient okHttpClient = new OkHttpClient();
                //                                okHttpClient.setConnectTimeout(40000, TimeUnit.MILLISECONDS);
                okHttpClient.setConnectTimeout(20, TimeUnit.SECONDS);
                okHttpClient.setReadTimeout(20, TimeUnit.SECONDS);
                okHttpClient.setWriteTimeout(20, TimeUnit.SECONDS);
                okHttpClient.setSslSocketFactory(SSLSocketClient.getSSLSocketFactory());
                okHttpClient.setHostnameVerifier(SSLSocketClient.getHostnameVerifier());
                //                okHttpClient.newBuilder().connectTimeout(20, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS)
                //                        .sslSocketFactory(SSLSocketClient.getSSLSocketFactory()).hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                //                        .build();
                return okHttpClient;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
}
