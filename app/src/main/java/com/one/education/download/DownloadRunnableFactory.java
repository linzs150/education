package com.one.education.download;

import android.text.TextUtils;
import android.util.Log;

/**
 * DownloadRunnable工厂
 */
public class DownloadRunnableFactory {
    private static final String TAG = "DownloadFactory";

    public DownloadRunnable createDownloadRunnable(long taskId, DownloadInfo info) {
        String url = info.getUrl();
        if (TextUtils.isEmpty(url)) {
            Log.e(TAG, "createDownloadRunnable : url is null.");
            return null;
        }

        if (url.startsWith("http")) {
            return new HttpDownloadRunnable(taskId, info);
        }

        DownloadRunnable downloadRunnable = createOtherDownloadRunnable(taskId, info);
        if (null == downloadRunnable) {
            Log.e(TAG, "createDownloadRunnable : unknown url[url = " + url);
        }

        return downloadRunnable;
    }

    protected DownloadRunnable createOtherDownloadRunnable(long taskId, DownloadInfo info) {
        return null;
    }

}
