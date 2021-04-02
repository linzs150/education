package com.newtonacademic.newtontutors.utils;

import android.text.TextUtils;
import android.util.Log;

import com.newtonacademic.newtontutors.commons.Constants;
import com.newtonacademic.newtontutors.thread.ThreadPoolProxyFactory;

import java.io.File;
import java.util.ArrayList;

public class FileCacheManager {
    private FileCacheManager() {
    }

    private static FileCacheManager sInstance = null;

    public static FileCacheManager getInstance() {
        if (null == sInstance) {
            synchronized (FileCacheManager.class) {
                if (null == sInstance) {
                    sInstance = new FileCacheManager();
                }
            }
        }

        return sInstance;
    }

    private static final String TAG = "FileCacheManager";
    /**
     * 默认6个小时的有效期
     */
    private static final int DEFAULT_VALID_TIME = 6 * 60 * 60 * 1000;
    private String mRootDirectory = null;

    /** 缓存列表
     * String : 文件路径
     * Long : 有效缓存时间
     */
    private final ArrayList<String> mCacheFilePaths = new ArrayList<>();
    private boolean mHasInitFinish = false;

    public void initialize() {
        mRootDirectory = Constants.EXT_FOLDER_CACHE_PATH;
        //清除旧的缓存文件
        ThreadPoolProxyFactory.getNormalThreadProxy().execute(new Runnable() {
            @Override
            public void run() {
                String[] curFiles = new File(mRootDirectory).list();

                synchronized (mCacheFilePaths) {
                    boolean isNewAdd;
                    if (null != curFiles) {
                        for (String oldFile : curFiles) {
                            oldFile = Utilts.createFilePath(mRootDirectory, oldFile);
                            isNewAdd = false;
                            for (String newFilePath : mCacheFilePaths) {
                                if (TextUtils.equals(newFilePath, oldFile)) {
                                    isNewAdd = true;
                                    break;
                                }
                            }

                            if (isNewAdd) {
                                continue;
                            }

                            if (!hasExpireFile(oldFile)) {
                                continue;
                            }

                            File file = new File(oldFile);
                            if (!Utilts.deleteFile(file)) {
                                Log.e(TAG, "delete old failed : ");
                            }
                        }
                    }

                    mHasInitFinish = true;
                }
            }
        });
    }

    /**
     * 申请文件
     * @return 申请到的文件路径
     */
    public String applyFilePath() {
        return applyFilePath(false);
    }

    /**
     * 申请文件
     * @param needRetrieve 是否需要归还
     * @return 申请到的文件路径
     */
    public String applyFilePath(boolean needRetrieve) {
        return applyFilePath(null, DEFAULT_VALID_TIME, needRetrieve);
    }

    /**
     * 申请文件
     * validTime 文件的有效期
     * @return 申请到的文件路径
     */
    public String applyFilePath(long validTime) {
        return applyFilePath(validTime, false);
    }

    /**
     * 申请文件
     * @param validTime 文件的有效期
     * @param needRetrieve 是否需要归还
     * @return 申请到的文件路径
     */
    public String applyFilePath(long validTime, boolean needRetrieve) {
        return applyFilePath(null, validTime, needRetrieve);
    }

    /**
     * @param extensionName 文件扩展名
     * @return 文件路径
     */
    public String applyFilePath(String extensionName) {
        return applyFilePath(extensionName, false);
    }

    /**
     * 申请文件
     * @param extensionName 文件扩展名
     * @param needRetrieve 是否需要归还
     * @return 文件路径
     */
    public String applyFilePath(String extensionName, boolean needRetrieve) {
        return applyFilePath(extensionName, DEFAULT_VALID_TIME, needRetrieve);
    }

    /**
     * 申请文件
     * @param extensionName 文件扩展名
     * @param validTime 有效时间
     * @param needRetrieve 是否需要归还
     * @return 文件路径
     */
    public String applyFilePath(String extensionName, long validTime, boolean needRetrieve) {
        String newPath = Utilts.createFilePath(mRootDirectory, Utilts.randomUUIDRemoveMinusSign());
        if (validTime > 0) {
            //文件名后面添加有效期标识
            newPath = newPath + "_" + (System.currentTimeMillis() + validTime);
        }

        if (null != extensionName) {
            newPath = newPath + "." + extensionName;
        }

        if (needRetrieve && !mHasInitFinish) {
            synchronized (mCacheFilePaths) {
                if (!mHasInitFinish) {
                    mCacheFilePaths.add(newPath);
                }
            }
        }

        return newPath;
    }

    /**
     * 是否为缓存文件
     */
    private boolean isCacheFile(String filePath) {
        return null != filePath && filePath.contains(mRootDirectory);
    }

    /**
     * 文件是否已过期
     */
    private boolean hasExpireFile(String filePath) {
        return System.currentTimeMillis() > getFileValidTime(filePath);
    }

    private long getFileValidTime(String filePath) {
        if (null == filePath) {
            return 0L;
        }

        int lastSeparator = filePath.lastIndexOf(File.separator);
        String fileName = filePath;
        if (lastSeparator >= 0) {
            fileName = filePath.substring(lastSeparator + 1);
        }

        int timeSeparator = fileName.lastIndexOf("_");
        if (timeSeparator < 0) {
            return 0L;
        }

        try {
            return Long.valueOf(fileName.substring(timeSeparator + 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }



}
