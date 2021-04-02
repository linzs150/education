package com.newtonacademic.newtontutors.thread;

import android.text.TextUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lyy
 * @version v1.0, 2019/6/5
 * @Description
 */
abstract class BaseThreadPoolProxy {

    /**
     * 线程池名称
     */
    private String mThreadName;

    /**
     * 核心池的大小
     */
    private int mCoreThreadSize;
    /**
     * 最大线程数
     */
    private int mMaximumPoolSize;

    /**
     * 非核心线程闲置时的超时时长，超过这个时长，非核心线程就会被回收
     */
    private long mKeepAliveTime;

    /**
     * keepAliveTime 参数的时间单位
     */
    private TimeUnit mTimeUnit;

    BaseThreadPoolProxy(String threadName, int coreThreadSize, int maximumPoolSize, long keepAliveTime, TimeUnit timeUnit) {
        this.mThreadName = threadName;
        this.mCoreThreadSize = coreThreadSize;
        this.mMaximumPoolSize = maximumPoolSize;
        this.mKeepAliveTime = keepAliveTime;
        this.mTimeUnit = timeUnit;
    }

    /**
     * 初始化线程池
     * @param corePoolSize 核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime 非核心线程闲置时的超时时长，超过这个时长，非核心线程就会被回收
     * @param unit keepAliveTime单位
     * @param workQueue 任务队列
     * @param threadFactory 线程工厂
     * @param handler 线程被拒绝时的处理
     */
    abstract void initThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                 BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler);

    /**
     * 初始化线程池
     */
    void initThreadPool() {
        //线程池中的任务队列，通过线程池的 execute 方法提交的 Runnable 对象会存储在这个参数中
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
        //创建线程的线程工厂。ThreadFactory 是一个接口，只有一个方法：Thread newThread (Runnable r)
        ThreadFactory threadFactory = new DefaultThreadFactory(mThreadName);
        //是线程池持有的一个对象，用于不能由 ThreadPoolExecutor 执行的任务的处理
        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
        initThreadPool(mCoreThreadSize, mMaximumPoolSize, mKeepAliveTime, mTimeUnit, workQueue, threadFactory, handler);
    }

    /**
     * 自定义线程工厂
     */
    private class DefaultThreadFactory implements ThreadFactory {
        private final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        private DefaultThreadFactory(String namePrefix) {
            SecurityManager s = System.getSecurityManager();
            this.group = s != null ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            if (TextUtils.isEmpty(namePrefix)) {
                this.namePrefix = "-pool-" + poolNumber.getAndIncrement() + "-thread-";
            } else {
                this.namePrefix = namePrefix + "-pool-" + poolNumber.getAndIncrement() + "-thread-";
            }
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(this.group, r, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }

            if (t.getPriority() != 5) {
                t.setPriority(5);
            }

            return t;
        }
    }

}
