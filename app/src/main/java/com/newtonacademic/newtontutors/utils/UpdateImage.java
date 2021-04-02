package com.newtonacademic.newtontutors.utils;

import com.newtonacademic.newtontutors.commons.LogUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

/**
 * @创建者 Administrator
 * @创建时间 2020/6/20 16:18
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class UpdateImage {

    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10 * 1000; // 超时时间
    private static final String CHARSET = "utf-8"; // 设置编码

    /**
     * Android上传文具到服务端
     *
     * @param file       需要上传的文件
     * @param RequestURL 请求的rul
     * @return 返回响应的内容
     */
    public static String uploadFile(File file, String RequestURL, Map<String, String> param) {
        String result = null;
        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型

        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
                    + BOUNDARY);
            conn.setRequestProperty("api-version", "1.0"); //请求头，根据要求要还是不要
            if (file != null) {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                LogUtils.e("======", "====DataOutputStream===");
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                StringBuffer sb = new StringBuffer();
                String params = "";


                /***
                 * 以下是用于上传参数
                 */
                //                if (param != null && param.size() > 0) {
                //                    Iterator<String> it = param.keySet().iterator();
                //                    while (it.hasNext()) {
                //                        sb = null;
                //                        sb = new StringBuffer();
                //                        String key = it.next();
                //                        String value = param.get(key);
                //                        sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
                //                        sb.append("Content-Disposition: form-data; name=\"")
                //                                .append(key).append("\"").append(LINE_END)
                //                                .append(LINE_END);
                //                        sb.append(value).append(LINE_END);
                //                        params = sb.toString();
                //                        LogUtils.i(TAG, key + "=" + params + "##");
                //                        dos.write(params.getBytes());
                //                        // dos.flush();
                //                    }
                //                }

                sb = null;
                params = null;
                sb = new StringBuffer();

                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意： name里面的值为服务端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比如:abc.png
                 */
                sb.append("Content-Disposition: form-data; name=\"avatar\"; filename=\""
                        + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset="
                        + CHARSET + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                        .getBytes();
                dos.write(end_data);
                dos.flush();
                LogUtils.e("======", "====flush===");

                /**
                 * 获取响应码 200=成功 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                LogUtils.e("========", "response code:" + res);
                if (res == 200) {
                    LogUtils.e(TAG, "request success");
                    InputStream input = conn.getInputStream();
                    StringBuffer sb1 = new StringBuffer();
                    int ss;
                    while ((ss = input.read()) != -1) {
                        sb1.append((char) ss);
                    }
                    result = sb1.toString();
                    LogUtils.e(TAG, "result : " + result);
                } else {
                    LogUtils.e(TAG, "request error");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

}
