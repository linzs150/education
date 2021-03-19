package com.one.education.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lyy
 * @version v1.0, 2019/6/4
 * @Description 线程池的代理类，用于执行、提交、移除操作
 */
public class ThreadPoolProxy extends BaseThreadPoolProxy{

    /**
     *线程池执行器
     */
    private ThreadPoolExecutor mThreadPoolExecutor;

    /**
     * @param threadPoolName 线程名称
     * @param corePoolSize 核心池的大小
     * @param maximumPoolSize 最大线程数
     */
    ThreadPoolProxy(String threadPoolName, int corePoolSize, int maximumPoolSize) {
        super(threadPoolName, corePoolSize, maximumPoolSize, 10000L, TimeUnit.MILLISECONDS);
    }

    @Override
    void initThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                        BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        if (mThreadPoolExecutor == null || mThreadPoolExecutor.isShutdown()) {
            synchronized (ThreadPoolExecutor.class) {
                if (mThreadPoolExecutor == null || mThreadPoolExecutor.isShutdown()) {
                    mThreadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                            workQueue, threadFactory, handler);
                }
            }
        }
    }

    /**
     * 执行任务
     */
    public void execute(Runnable task) {
        if (task == null) {
            throw new RuntimeException("task is null.");
        }

        initThreadPool();
        mThreadPoolExecutor.execute(task);
    }

    /**
     * 提交任务
     */
    public Future submit(Runnable task) {
        if (task == null) {
            throw new RuntimeException("task is null.");
        }

        initThreadPool();
        return mThreadPoolExecutor.submit(task);
    }

    /**
     * 移除任务
     */
    public void remove(Runnable task) {
        if (task == null) {
            throw new RuntimeException("task is null.");
        }

        initThreadPool();
        mThreadPoolExecutor.remove(task);
    }
}
