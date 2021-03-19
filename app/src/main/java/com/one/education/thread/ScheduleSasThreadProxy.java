package com.one.education.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author lyy
 * @version v1.0, 2019/6/5
 * @Description
 */
public class ScheduleSasThreadProxy extends BaseThreadPoolProxy {

    /**
     *线程池执行器
     */
    private ScheduledThreadPoolExecutor mThreadPoolExecutor;

    /**
     * @param threadPoolName 线程名称
     * @param corePoolSize 核心池的大小
     */
    ScheduleSasThreadProxy(String threadPoolName, int corePoolSize) {
        super(threadPoolName, corePoolSize, corePoolSize, 0L, TimeUnit.MILLISECONDS);
    }

    @Override
    void initThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        if (mThreadPoolExecutor == null || mThreadPoolExecutor.isShutdown()) {
            synchronized (ScheduleSasThreadProxy.class) {
                if (mThreadPoolExecutor == null || mThreadPoolExecutor.isShutdown()) {
                    mThreadPoolExecutor = new ScheduledThreadPoolExecutor(corePoolSize, threadFactory, handler);
                }
            }
        }
    }

    /**
     * 执行
     * @param task 执行体
     * @param delay 延迟时长
     */
    public ScheduledFuture scheduleDelay(Runnable task, long delay) {
        if (task == null) {
            throw new RuntimeException("task is null.");
        }

        initThreadPool();
        //下一次执行时间是上一次任务执行完的系统时间加上period，因而具体执行时间不是固定的，但周期是固定的，是采用相对固定的延迟来执行任务
        return mThreadPoolExecutor.schedule(task, delay, TimeUnit.MILLISECONDS);
    }


    public void shutdownNow() {
        if (mThreadPoolExecutor != null) {
            mThreadPoolExecutor.shutdownNow();
        }
    }
}
