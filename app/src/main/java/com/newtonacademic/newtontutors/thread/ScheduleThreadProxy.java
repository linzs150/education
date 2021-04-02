package com.newtonacademic.newtontutors.thread;

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
public class ScheduleThreadProxy extends BaseThreadPoolProxy {

    /**
     *线程池执行器
     */
    private ScheduledThreadPoolExecutor mThreadPoolExecutor;

    /**
     * @param threadPoolName 线程名称
     * @param corePoolSize 核心池的大小
     */
    ScheduleThreadProxy(String threadPoolName, int corePoolSize) {
        super(threadPoolName, corePoolSize, corePoolSize, 0L, TimeUnit.MILLISECONDS);
    }

    @Override
    void initThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        if (mThreadPoolExecutor == null || mThreadPoolExecutor.isShutdown()) {
            synchronized (ScheduleThreadProxy.class) {
                if (mThreadPoolExecutor == null || mThreadPoolExecutor.isShutdown()) {
                    mThreadPoolExecutor = new ScheduledThreadPoolExecutor(corePoolSize, threadFactory, handler);
                }
            }
        }
    }

    /**
     * 执行
     * @param task 执行体
     * @param initDelay 此周期任务的开始执行时的延时时间（即只在第一次开始执行时延时，此后周期性地执行这个任务）。
     * @param period 周期
     */
    public ScheduledFuture scheduleWithFixedDelay(Runnable task, long initDelay, long period) {
        if (task == null) {
            throw new RuntimeException("task is null.");
        }

        initThreadPool();
        //下一次执行时间是上一次任务执行完的系统时间加上period，因而具体执行时间不是固定的，但周期是固定的，是采用相对固定的延迟来执行任务
        return mThreadPoolExecutor.scheduleAtFixedRate(task, initDelay, period, TimeUnit.MILLISECONDS);
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

    /**
     * 执行
     * @param task 执行体
     */
    public void schedule(Runnable task) {
        if (task == null) {
            throw new RuntimeException("task is null.");
        }

        initThreadPool();
        //下一次执行时间是上一次任务执行完的系统时间加上period，因而具体执行时间不是固定的，但周期是固定的，是采用相对固定的延迟来执行任务
        mThreadPoolExecutor.schedule(task, 0, TimeUnit.MILLISECONDS);
    }

    /**
     * 删除任务（只能移除未开始的任务）
     * 如果任务已经开始运行了，执行该方法会无效
     */
    public void remove(Runnable task) {
        if (task == null) {
            throw new RuntimeException("task is null.");
        }

        if (mThreadPoolExecutor != null) {
            mThreadPoolExecutor.remove(task);
        }
    }

    /**
     * 清除已取消的任务
     */
    public void purge() {
        if (mThreadPoolExecutor != null) {
            mThreadPoolExecutor.purge();
        }
    }

    public void shutdownNow() {
        if (mThreadPoolExecutor != null) {
            mThreadPoolExecutor.shutdownNow();
        }
    }
}
