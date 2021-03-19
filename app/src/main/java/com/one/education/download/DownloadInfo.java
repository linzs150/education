package com.one.education.download;

import android.text.TextUtils;

public class DownloadInfo {
    private static final String TAG = "DownloadInfo";
    private final String mUrl;
    private final String mFilePath;
    private final boolean mNeedCache;
    private long mPosition = 0;
    private long mFileSize = 0;

    /**
     * 下载信息
     *
     * @param url      ：uri路径
     * @param filePath ： 文件路径
     */
    public DownloadInfo(String url, String filePath) {
        mUrl = url;
        mFilePath = filePath;
        mNeedCache = true;
    }

    /**
     * 下载信息
     *
     * @param url       : uri路径
     * @param filePath  ：文件路径
     * @param needCache ： 缓存路径
     */
    public DownloadInfo(String url, String filePath, boolean needCache) {
        mUrl = url;
        mFilePath = filePath;
        mNeedCache = needCache;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public long getFileSize() {
        return mFileSize;
    }

    public void setFileSize(long fileSize) {
        mFileSize = fileSize;
    }

    public void setPosition(long position) {
        mPosition = position > 0 ? position : 0;
    }

    public long getPosition() {
        return mPosition;
    }

    public boolean needCache() {
        return mNeedCache;
    }

    @Override
    public boolean equals(Object o) {
        if (null == o || !(o instanceof DownloadInfo)) {
            return false;
        }

        DownloadInfo info = (DownloadInfo) o;
        return TextUtils.equals(this.mUrl, info.mUrl)
                && TextUtils.equals(this.mFilePath, info.mFilePath);
    }

    @Override
    public String toString() {
        return "URL:" + mUrl + ", FilePath:" + mFilePath + ", Position:"
                + mPosition + ", NeedCache:" + mNeedCache;
    }
}
