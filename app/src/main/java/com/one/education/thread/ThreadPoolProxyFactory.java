package com.one.education.thread;

/**
 * @author lyy
 * @version v1.0, 2019/6/4
 * @Description
 */
public class ThreadPoolProxyFactory {

    /**
     * 上传的线程池
     */
    private static volatile ThreadPoolProxy sUploadThreadProxy = null;

    /**
     * 下载的多线程线程池
     */
    private static volatile ThreadPoolProxy sDownloadThreadProxy = null;

    /**
     * 下载的单线程线程池
     */
    private static volatile ThreadPoolProxy sSingleDownloadThreadProxy = null;

    /**
     * 普通线程池
     */
    private static volatile ThreadPoolProxy sNormalThreadProxy = null;

    /**
     * 单线程线程池
     */
    private static volatile ThreadPoolProxy sSingleThreadProxy = null;

    /**
     * 数据库线程池
     */
    private static volatile ThreadPoolProxy sDbThreadProxy = null;
    /**
     * 普通定时器线程代理
     */
    private static volatile ScheduleThreadProxy sScheduleThreadProxy = null;
    /**
     * Sas定时器线程代理
     */
    private static volatile ScheduleSasThreadProxy sScheduleSasThreadProxy = null;

    /**
     * 登录定时器线程代理
     */
    private static volatile LoginScheduleThreadProxy sLoginScheduleThreadProxy = null;


    /**
     * 上传线程代理
     * @return ThreadPoolProxy
     */
    public static synchronized ThreadPoolProxy getUploadThreadProxy() {
        if (sUploadThreadProxy == null) {
            synchronized (ThreadPoolProxy.class) {
                if (sUploadThreadProxy == null) {
                    int poolSize = Math.max(5,  Runtime.getRuntime().availableProcessors() * 2 + 1);
                    sUploadThreadProxy = new ThreadPoolProxy("UploadThread", poolSize, poolSize);
                }
            }
        }

        return sUploadThreadProxy;
    }

    /**
     * 多线程下载线程池
     * @return ThreadPoolProxy
     */
    public static ThreadPoolProxy getDownloadPool() {
        if(sDownloadThreadProxy == null) {
            synchronized (ThreadPoolProxy.class) {
                if (sDownloadThreadProxy == null) {
                    int poolSize = Math.max(5,  Runtime.getRuntime().availableProcessors() * 2 + 1);
                    sDownloadThreadProxy = new ThreadPoolProxy("Download", poolSize, poolSize);
                }
            }
        }

        return sDownloadThreadProxy;
    }

    /**
     * 单线程下载线程池
     * @return ThreadPoolProxy
     */
    public static ThreadPoolProxy getSingleDownloadPool() {
        if (sSingleDownloadThreadProxy == null) {
            synchronized (ThreadPoolProxy.class) {
                if (sSingleDownloadThreadProxy == null) {
                    sSingleDownloadThreadProxy = new ThreadPoolProxy("SingleDownload", 1, 1);
                }
            }
        }

        return sSingleDownloadThreadProxy;
    }

    /**
     * 普通线程代理
     * @return ThreadPoolProxy
     */
    public static synchronized ThreadPoolProxy getNormalThreadProxy() {
        if (sNormalThreadProxy == null) {
            synchronized (ThreadPoolProxy.class) {
                if (sNormalThreadProxy == null) {
                    sNormalThreadProxy = new ThreadPoolProxy("NormalThreadPool", 3, 3);
                }
            }
        }

        return sNormalThreadProxy;
    }

    /**
     * 单线程池代理
     * @return ThreadPoolProxy
     */
    public static synchronized ThreadPoolProxy getSingleThreadProxy() {
        if (sSingleThreadProxy == null) {
            synchronized (ThreadPoolProxy.class) {
                if (sSingleThreadProxy == null) {
                    sSingleThreadProxy = new ThreadPoolProxy("SingleThreadPool", 1, 1);
                }
            }
        }

        return sSingleThreadProxy;
    }

    /**
     * 数据库线程池代理
     * @return ThreadPoolProxy
     */
    public static synchronized ThreadPoolProxy getDbThreadProxy() {
        if (sDbThreadProxy == null) {
            synchronized (ThreadPoolProxy.class) {
                if (sDbThreadProxy == null) {
                    sDbThreadProxy = new ThreadPoolProxy("DbThreadPool", 1, 1);
                }
            }
        }

        return sDbThreadProxy;
    }

    /**
     * 定时器线程池代理
     * @return ThreadPoolProxy
     */
    public static synchronized ScheduleThreadProxy getScheduleThreadProxy() {
        if (sScheduleThreadProxy == null) {
            synchronized (ThreadPoolProxy.class) {
                if (sScheduleThreadProxy == null) {
                    sScheduleThreadProxy = new ScheduleThreadProxy("ScheduledThreadPool", 3);
                }
            }
        }

        return sScheduleThreadProxy;
    }

    /**
     * 登录定时器线程池代理
     * @return ThreadPoolProxy
     */
    public static synchronized LoginScheduleThreadProxy getLoginScheduleThreadProxy() {
        if (sLoginScheduleThreadProxy == null) {
            synchronized (ThreadPoolProxy.class) {
                if (sLoginScheduleThreadProxy == null) {
                    sLoginScheduleThreadProxy = new LoginScheduleThreadProxy();
                }
            }
        }

        return sLoginScheduleThreadProxy;
    }

    /**
     * 定时器线程池代理
     * @return ThreadPoolProxy
     */
    public static synchronized ScheduleSasThreadProxy getScheduleSasThreadProxy() {
        if (sScheduleSasThreadProxy == null) {
            synchronized (ThreadPoolProxy.class) {
                if (sScheduleSasThreadProxy == null) {
                    sScheduleSasThreadProxy = new ScheduleSasThreadProxy("ScheduledThreadPool", 3);
                }
            }
        }

        return sScheduleSasThreadProxy;
    }
}
