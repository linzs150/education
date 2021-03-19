package com.one.education.utils;

import android.text.TextUtils;

import com.one.education.api.ICallback;
import com.one.education.download.Download;
import com.one.education.download.DownloadInfo;
import com.one.education.download.IDownloadListener;

/**
 * SingleFileUri
 */
class SingleFileUri extends FileUri {
    private final String mResource;
    private final String mLocalPath;

    SingleFileUri(String resource, String localPath) {
        mResource = resource;
        mLocalPath = localPath;
    }

    @Override
    public String getLocalPath() {
        return mLocalPath;
    }

    public String getResource() {
        return mResource;
    }

    /**
     * 加载资源<br/>
     * ICallback<Boolean> boolean 表示加载成功或者失败<br/>
     * 子类需要重载这个方法实现加载
     */
    @Override
    public void loadResource(final ICallback<Boolean> callback) {
        IDownloadListener listener = new IDownloadListener() {
            @Override
            public void onStart(DownloadInfo info) {
            }

            @Override
            public void onProgress(DownloadInfo info, int progress) {
            }

            @Override
            public void onFinish(DownloadInfo info, Download.Result result) {
                if (null != callback) {
                    callback.onCallback(Download.Result.SUCCESS == result);
                }
            }
        };
        Download.getInstance().download(new DownloadInfo(mResource, mLocalPath), listener);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (null == o || !(o instanceof SingleFileUri)) {
            return false;
        }

        SingleFileUri cmp = (SingleFileUri) o;
        return TextUtils.equals(cmp.mResource, this.mResource) && TextUtils.equals(cmp.mLocalPath, this.mLocalPath);
    }
}
