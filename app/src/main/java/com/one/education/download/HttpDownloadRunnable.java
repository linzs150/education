package com.one.education.download;

import android.util.Log;

import com.one.education.EducationAppliction;
import com.one.education.utils.FileCacheManager;
import com.one.education.utils.NetworkUtils;
import com.one.education.utils.Utilts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class HttpDownloadRunnable extends DownloadRunnable {
    private static final String TAG = "HttpDownloadRunnable";
    private static final int MAX_FAIL_TIMES = 5;

    HttpDownloadRunnable(long taskId, DownloadInfo info) {
        super(taskId, info);
    }

    @Override
    public void run() {
        notifyStart();
        if (mInfo.toString().contains("apk")) {
            Log.d(TAG, "run(): start:" + mInfo.toString() + ", threadid=" + Thread.currentThread().getId());
        }

        if (!NetworkUtils.isNetworkValidity(EducationAppliction.getInstance())) {
            notifyFinish(Download.Result.NETWORK_UNAVAILABLE);
            return;
        }

        File file;
        InputStream inStream = null;
        HttpURLConnection conn = null;
        RandomAccessFile randomAccessFile = null;
        int failTimes = 0;
        long fileSize = 0;
        while (true) {
            try {
                boolean hasCache = mInfo.needCache();
                if (mInfo.needCache()) {
                    file = new File(FileCacheManager.getInstance().applyFilePath());
                } else {
                    file = new File(mInfo.getFilePath());
                }

                if (!Utilts.createDirectory(file.getParent())) {
                    Log.d(TAG, "createDirectory[path = " + file.getParent() + "] failed :Download stop");
                    notifyFinish(Download.Result.FAILED);
                    return;
                }

                Log.i(TAG, "position = " + mInfo.getPosition());
                if (0 == mInfo.getPosition()) {
                    if (file.exists()) {
                        if (!file.delete()) {
                            Log.e(TAG, "run : delete exist file failed[file = " + file.getPath());
                        }
                    }

                    if (!file.createNewFile()) {
                        Log.e(TAG, "run : createNewFile failed [file = " + file.getPath());
                    }
                } else if (mInfo.getPosition() > 0) {
                    if (!file.exists()) {
                        mInfo.setPosition(0);
                        Log.i(TAG, "file is no exists");
                        if (!file.createNewFile()) {
                            Log.e(TAG, "run : createNewFile failed [file = " + file.getPath());
                        }
                    }
                }

                if (isDownloadStop()) {
                    notifyFinish(Download.Result.DOWNLOAD_CANCEL);
                    Log.d(TAG, "Download stop");
                    return;
                }

                URL url = new URL(mInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("accept-Encoding", "identity");
                conn.setRequestProperty("Range", "bytes=" + mInfo.getPosition() + "-");
                conn.setConnectTimeout(10000);        //10秒
                conn.setReadTimeout(10000);            //10秒
                conn.connect();
                if (0 == fileSize) {
                    fileSize = conn.getContentLength() + mInfo.getPosition();
                }

                inStream = conn.getInputStream();
                byte[] tempBytes = new byte[1024 * 10];

                randomAccessFile = new RandomAccessFile(file, "rwd");
                randomAccessFile.seek(mInfo.getPosition());

                int readNum;
                while (fileSize > mInfo.getPosition() && (readNum = inStream.read(tempBytes)) != -1) {
                    randomAccessFile.write(tempBytes, 0, readNum);
                    mInfo.setPosition(mInfo.getPosition() + readNum);
                    long progress = mInfo.getPosition() * 100 / fileSize;
                    notifyProgress((int) progress);
                    if (isDownloadStop()) {
                        notifyFinish(Download.Result.DOWNLOAD_CANCEL);
                        Log.d(TAG, "Download stop");
                        return;
                    }
                }

                if (!isDownloadStop()) {
                    if (fileSize != mInfo.getPosition()) {
                        notifyFinish(Download.Result.LENGTH_ERROR);
                    } else {
                        randomAccessFile.close();
                        randomAccessFile = null;

                        if (hasCache) {
                            File desFile = new File(mInfo.getFilePath());
                            File parentFolder = desFile.getParentFile();
                            if (!parentFolder.exists() && !parentFolder.mkdirs()) {
                                notifyFinish(Download.Result.FAILED);
                                return;
                            }

                            if (desFile.exists()) {
                                desFile.delete();
                            }

                            if (!file.renameTo(desFile)) {
                                notifyFinish(Download.Result.FAILED);
                                return;
                            }
                        }

                        notifyFinish(Download.Result.SUCCESS);
                    }
                }

                break;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                notifyFinish(Download.Result.FAILED);
                break;
            } catch (MalformedURLException e) {
                notifyFinish(Download.Result.DOWNLOAD_CANCEL);
                break;
            } catch (Exception e) {
                e.printStackTrace();
                if (failTimes++ > MAX_FAIL_TIMES) {
                    notifyFinish(Download.Result.NETWORK_TIMEOUT);
                    break;
                } else {
                    notifyStart();

                    //sleep
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e2) {
                        Log.e(TAG, "run.Exception", e2);
                    }
                }
            } finally {
                try {
                    if (inStream != null) {
                        inStream.close();
                    }
                    if (conn != null) {
                        conn.disconnect();
                    }
                    if (randomAccessFile != null) {
                        randomAccessFile.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public DownloadInfo getDownloadInfo() {
        return mInfo;
    }
}
