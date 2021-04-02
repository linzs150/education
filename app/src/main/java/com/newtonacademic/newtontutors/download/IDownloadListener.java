package com.newtonacademic.newtontutors.download;

public interface IDownloadListener
{
    void onStart(DownloadInfo info);

    void onProgress(DownloadInfo info, int progress);

    void onFinish(DownloadInfo info, Download.Result result);
}