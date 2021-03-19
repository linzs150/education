package com.one.education.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * 用于解决 Provider 冲突的 util
 */

public class ProviderUtil {

    public static String getFileProviderName(Context context) {
        return context.getPackageName() + ".provider";
    }

    public static String bitmaptoString(Bitmap bitmap) {

        // 将Bitmap转换成字符串

        String string = null;

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);

        byte[] bytes = bStream.toByteArray();

        string = Base64.encodeToString(bytes, Base64.DEFAULT);

        return string;

    }
}
