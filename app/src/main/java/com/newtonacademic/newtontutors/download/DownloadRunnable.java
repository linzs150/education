package com.newtonacademic.newtontutors.download;

import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * DownloadRunnable
 */
public abstract class DownloadRunnable implements Runnable {
    private static final String TAG = "DownloadRunnable";
    private final long mTaskId;
    protected final DownloadInfo mInfo;
    private final List<IDownloadListener> mListenerList = new CopyOnWriteArrayList<>();
    private boolean mIsDownloadStop = false;
    private int mProgress = 0;

    public DownloadRunnable(long taskId, DownloadInfo info) {
        mTaskId = taskId;
        mInfo = info;
    }

    public long getTaskId() {
        return mTaskId;
    }

    public DownloadInfo getDownloadInfo() {
        return mInfo;
    }

    void stopTask() {
        mIsDownloadStop = true;
    }

    protected boolean isDownloadStop() {
        return mIsDownloadStop;
    }

    public int getProgress() {
        return mProgress;
    }

    public void addListener(IDownloadListener listener) {
        if (listener == null) {
            return;
        }

        synchronized (mListenerList) {
            if (!existListener(listener)) {
                mListenerList.add(listener);
            }
        }
    }

    public void removeListener(IDownloadListener listener) {
        if (listener == null) {
            return;
        }

        synchronized (mListenerList) {
            mListenerList.remove(listener);
        }
    }

    private boolean existListener(IDownloadListener dl) {
        if (dl == null) {
            return false;
        }

        mListenerList.remove(dl);
        return false;
    }

    protected void notifyStart() {
        try {
            for (IDownloadListener listener : mListenerList) {
                if (null != listener) {
                    listener.onStart(mInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void notifyProgress(int progress) {
        if (progress < 0 || progress > 100) {
            Log.e(TAG, "notifyProgress : progress is invalid[progress = " + progress);
            progress = 0;
        }
        mProgress = progress;

        try {
            for (IDownloadListener listener : mListenerList) {
                if (null != listener) {
                    listener.onProgress(mInfo, progress);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void notifyFinish(final Download.Result state) {
        mProgress = 100;
        try {
            for (IDownloadListener listener : mListenerList) {
                if (listener != null) {
                    listener.onFinish(mInfo, state);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
