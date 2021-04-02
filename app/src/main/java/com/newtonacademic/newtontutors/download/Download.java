package com.newtonacademic.newtontutors.download;

import android.os.Handler;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Download {
    private static final String TAG = "Download";

    public enum Result {
        /**
         * 下载成功
         */
        SUCCESS("Success"),

        /**
         * URL 错误
         */
        URL_ERROR("UrlError"),

        /**
         * 网络不可用
         */
        NETWORK_UNAVAILABLE("NetworkUnavailable"),

        /**
         * 网络超时
         */
        NETWORK_TIMEOUT("NetworkTimeout"),

        /**
         * 取消下载
         */
        DOWNLOAD_CANCEL("DownloadCancel"),

        /**
         * 长度不对
         */
        LENGTH_ERROR("LengthError"),

        /**
         * 下载失败
         */
        FAILED("Failed"),
        ;

        private final String name;

        Result(String message) {
            this.name = message;
        }

        public String getName() {
            super.name();
            return this.name;
        }
    }

    public static final long INVALID_TASK = 0L;
    /**
     * 下载状态
     */
    public static final int STATE_NO_FOUND = -1;
    public static final int STATE_WAIT = -2;

    public static final int DEFAULT_THREAD_NUMBER = 5;

    /**
     * 任务id
     */
    private static long sDownloadTaskId = 0;

    // 保存请求列表
    private final List<DownloadRunnable> mRequestList = new ArrayList<>();
    // 保存执行列表
    private final List<DownloadRunnable> mExecuteList = new ArrayList<>();

    private final Object mTaskListLock = new Object();
    private ThreadPoolExecutor mExecutor;
    private Handler mHandler;
    private static Download sDownload = null;
    private DownloadRunnableFactory mDownloadRunnableFactory = null;

    private Download() {
    }

    public static Download getInstance() {
        if (null == sDownload) {
            sDownload = new Download();
        }
        return sDownload;
    }

    public void initialize(Handler handler, DownloadRunnableFactory downloadRunnableFactory) {
        mHandler = handler;
        if (null == downloadRunnableFactory) {
            downloadRunnableFactory = new DownloadRunnableFactory();
        }
        mDownloadRunnableFactory = downloadRunnableFactory;
        mExecutor = new ThreadPoolExecutor(DEFAULT_THREAD_NUMBER, DEFAULT_THREAD_NUMBER, Long.MAX_VALUE, TimeUnit.NANOSECONDS, (new LinkedBlockingQueue<Runnable>()));
    }

    /**
     * 下载状态
     *
     * @param info 下载任务
     * @return 返回的结果包含以下几种情况：STATE_NO_FOUND 未找到该下载任务，STATE_WAIT 该任务在等待，除了以上情况，返回值表示下载任务的下载进度
     */
    public int getState(DownloadInfo info) {
        synchronized (mTaskListLock) {
            synchronized (mRequestList) {
                DownloadInfo tempInfo;
                for (DownloadRunnable item : mRequestList) {
                    tempInfo = item.getDownloadInfo();
                    if (null != tempInfo && tempInfo.equals(info)) {
                        return STATE_WAIT;
                    }
                }
            }

            synchronized (mExecuteList) {
                DownloadInfo tempInfo;
                for (DownloadRunnable item : mExecuteList) {
                    tempInfo = item.getDownloadInfo();
                    if (tempInfo.equals(info)) {
                        return item.getProgress();
                    }
                }
            }
            return STATE_NO_FOUND;
        }
    }

    public int getTaskSize() {
        synchronized (mTaskListLock) {
            int count = 0;
            synchronized (mRequestList) {
                count += mRequestList.size();
            }

            synchronized (mExecuteList) {
                count += mExecuteList.size();
            }
            return count;
        }
    }

    /**
     * 添加任务
     *
     * @param info 下载信息
     * @return long 返回新的workId
     */
    public long download(DownloadInfo info) {
        return download(info, null);
    }

    /**
     * 添加任务
     *
     * @param info     下载信息
     * @param listener 侦听器
     * @return long 返回新的workId
     */
    public long download(DownloadInfo info, IDownloadListener listener) {
        if (info == null) {
            return INVALID_TASK;
        }

        synchronized (mTaskListLock) {
            synchronized (mRequestList) {
                DownloadInfo tempInfo;
                for (DownloadRunnable item : mRequestList) {
                    tempInfo = item.getDownloadInfo();
                    if (null != tempInfo && tempInfo.equals(info)) {
                        item.addListener(listener);
                        return item.getTaskId();
                    }
                }
            }

            synchronized (mExecuteList) {
                DownloadInfo tempInfo;
                for (DownloadRunnable item : mExecuteList) {
                    tempInfo = item.getDownloadInfo();
                    if (null != tempInfo && tempInfo.equals(info)) {
                        item.addListener(listener);
                        return item.getTaskId();
                    }
                }
            }
        }

        long taskId = newTaskId();
        TaskRunnable task = new TaskRunnable(taskId);
        DownloadRunnable downloadRunnable = mDownloadRunnableFactory.createDownloadRunnable(taskId, info);
        if (null == downloadRunnable) {
            Log.e(TAG, "download : createDownloadRunnable result is null[DownloadInfo = " + String.valueOf(info));
            return INVALID_TASK;
        }

        downloadRunnable.addListener(listener);
        addRequestList(downloadRunnable);
        mExecutor.execute(task);
        Log.d(TAG, "download = " + info.toString());
        return taskId;
    }

    /**
     * 停止任务
     *
     * @param taskId 任务Id
     */
    public void stopTask(long taskId) {
        synchronized (mTaskListLock) {
            synchronized (mRequestList) {
                for (DownloadRunnable item : mRequestList) {
                    if (null != item && item.getTaskId() == taskId) {
                        item.notifyFinish(Result.DOWNLOAD_CANCEL);
                        mRequestList.remove(item);
                        return;
                    }
                }
            }

            synchronized (mExecuteList) {
                for (DownloadRunnable item : mExecuteList) {
                    if (null != item && item.getTaskId() == taskId) {
                        item.stopTask();
                        return;
                    }
                }
            }
        }
    }

    /**
     * 停止所有任务
     */
    public void stopAll() {
        synchronized (mTaskListLock) {
            synchronized (mRequestList) {
                if (mRequestList.size() > 0) {
                    List<DownloadRunnable> tempList = new ArrayList<>(mRequestList);
                    for (DownloadRunnable item : tempList) {
                        if (null != item) {
                            item.notifyFinish(Result.DOWNLOAD_CANCEL);
                            mRequestList.remove(item);
                        }
                    }
                }
            }

            synchronized (mExecuteList) {
                for (DownloadRunnable item : mExecuteList) {
                    if (null != item) {
                        item.stopTask();
                    }
                }
            }
        }
    }

    /**
     * 移除任务的侦听
     *
     * @param taskId   任务id
     * @param listener 侦听器
     */
    public void addListener(long taskId, IDownloadListener listener) {
        synchronized (mTaskListLock) {
            synchronized (mRequestList) {
                for (DownloadRunnable item : mRequestList) {
                    if (item.getTaskId() == taskId) {
                        item.addListener(listener);
                    }
                }
            }

            synchronized (mExecuteList) {
                for (DownloadRunnable item : mExecuteList) {
                    if (item.getTaskId() == taskId) {
                        item.addListener(listener);
                    }
                }
            }
        }
    }

    /**
     * 移除任务的侦听
     *
     * @param taskId   任务id
     * @param listener 侦听器
     */
    public void removeListener(int taskId, IDownloadListener listener) {
        synchronized (mTaskListLock) {
            synchronized (mRequestList) {
                for (DownloadRunnable item : mRequestList) {
                    if (item.getTaskId() == taskId) {
                        item.removeListener(listener);
                    }
                }
            }

            synchronized (mExecuteList) {
                for (DownloadRunnable item : mExecuteList) {
                    if (item.getTaskId() == taskId) {
                        item.removeListener(listener);
                    }
                }
            }
        }
    }

    private static boolean isValidTask(long taskId) {
        return taskId > INVALID_TASK;
    }

    private long newTaskId() {
        if (!isValidTask(++sDownloadTaskId)) {
            sDownloadTaskId = 1;
        }

        return sDownloadTaskId;
    }

    private void addRequestList(DownloadRunnable item) {
        if (item == null) {
            Log.e(TAG, "addRequestList: item is null.");
            return;
        }

        synchronized (mRequestList) {
            mRequestList.add(item);
        }
    }

    private void addExecuteList(DownloadRunnable item) {
        if (item == null) {
            Log.e(TAG, "addExecuteList: item is null.");
            return;
        }

        synchronized (mExecuteList) {
            mExecuteList.add(item);
        }
    }

    private void removeExecuteList(DownloadRunnable item) {
        if (null == item) {
            Log.e(TAG, "removeExecuteList: item is null.");
            return;
        }

        synchronized (mExecuteList) {
            mExecuteList.remove(item);
        }
    }

    private DownloadRunnable popTask(long taskId) {
        synchronized (mRequestList) {
            for (int i = 0; i < mRequestList.size(); i++) {
                DownloadRunnable downloadItem = mRequestList.get(i);
                if (null != downloadItem && downloadItem.getTaskId() == taskId) {
                    return mRequestList.remove(i);
                }
            }
            return null;
        }
    }

    private class TaskRunnable implements Runnable {
        private long mTaskId;

        TaskRunnable(long taskId) {
            mTaskId = taskId;
        }

        long getTaskId() {
            return mTaskId;
        }

        @Override
        public void run() {
            Log.d(TAG, "run(): threadid=" + Thread.currentThread().getId());

            DownloadRunnable task = null;
            try {
                synchronized (mTaskListLock) {
                    task = popTask(mTaskId);
                    if (task == null) {
                        return;
                    }

                    addExecuteList(task);
                }

                task.run();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                removeExecuteList(task);
            }
        }
    }
}
