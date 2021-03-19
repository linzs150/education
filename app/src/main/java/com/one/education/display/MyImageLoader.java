package com.one.education.display;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.one.education.EducationAppliction;
import com.one.education.api.ICallback;
import com.one.education.utils.FileUri;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * MyImageLoader
 */
public class MyImageLoader {
    private static final String TAG = "MyImageLoader";
    private ImageLoader mImageLoader;
    private final HashMap<Integer, String> mCacheKeyForImageView = new HashMap<>();
    private static MyImageLoader sInstance;

    private MyImageLoader() {
        mImageLoader = EducationAppliction.getImageLoader();
    }

    public static MyImageLoader getInstance() {
        if (null == sInstance) {
            sInstance = new MyImageLoader();
        }

        return sInstance;
    }

    public void clearMemoryCache() {
        mImageLoader.clearMemoryCache();
    }

    public void displayImageByUrl(String uri, ImageView imageView, DisplayImageOptions options) {
        displayImageByUrl(uri, imageView, options, null);
    }

    public void displayImageByUrl(String uri, ImageView imageView, DisplayImageOptions options, SimpleImageLoadingListener listener) {
        mImageLoader.displayImage(uri, imageView, options, listener);
    }

    /**
     * 显示本地图片
     */
    public void displayLocalImage(String localPath, ImageView imageView, DisplayImageOptions options, SimpleImageLoadingListener listener) {
        displayLocalImage(localPath, new ImageViewAware(imageView), options, listener);
    }

    /**
     * 显示本地图片
     */
    public void displayLocalImage(String localPath, ImageViewAware imageViewAware, DisplayImageOptions options, SimpleImageLoadingListener listener) {
        mImageLoader.displayImage(toLocalUri(localPath), imageViewAware, options, listener);
    }

    /**
     * 提供一个下载后显示方法
     */
    public void displayImage(final FileUri uri, ImageView imageView, DisplayImageOptions options) {
        displayImage(uri, new ImageViewAware(imageView), options);
    }


    /**
     * 提供一个下载后显示方法
     */
    public void displayImage(final FileUri uri, ImageViewAware imageViewAware, DisplayImageOptions options) {
        if (null == imageViewAware) {
            return;
        }

        int key = imageViewAware.hashCode();
        removeImageUri(key);

        if (null == uri) {
            mImageLoader.displayImage(null, imageViewAware, options);
            return;
        }

        String localPath = uri.getLocalPath();
        if (TextUtils.isEmpty(localPath)) {
            mImageLoader.displayImage(null, imageViewAware, options);
            return;
        }

        File file = new File(localPath);
        if (file.exists()) {
            mImageLoader.displayImage(toLocalUri(localPath), imageViewAware, options);
        } else {
            cacheImageUri(key, uri);
            mImageLoader.displayImage(null, imageViewAware, options);
            uri.loadResource(new MyCallBack(key, imageViewAware.getWrappedView(), uri, options));
        }
    }

    /**
     * 提供一个下载后显示方法
     */
    public void displayImage2(final FileUri uri, ImageView imageView, DisplayImageOptions options) {
        displayImage2(uri, new ImageViewAware(imageView), options);
    }

    /**
     * 提供一个下载后显示方法
     */
    public void displayImage2(final FileUri uri, ImageViewAware imageViewAware, DisplayImageOptions options) {
        if (null == imageViewAware) {
            Log.w(TAG, "displayImage : imageViewAware is null.");
            return;
        }

        int key = imageViewAware.hashCode();
        removeImageUri(key);
        if (null == uri) {
            mImageLoader.displayImage(null, imageViewAware, options);
            return;
        }

        cacheImageUri(key, uri);
        uri.loadResource(new MyCallBack(key, imageViewAware.getWrappedView(), uri, options));
    }

    private static String generateKey(FileUri imageUri) {
        return imageUri.getLocalPath();
    }

    private void cacheImageUri(int key, FileUri imageUri) {
        synchronized (mCacheKeyForImageView) {
            mCacheKeyForImageView.put(key, generateKey(imageUri));
        }
    }

    private String removeImageUri(int key) {
        synchronized (mCacheKeyForImageView) {
            return mCacheKeyForImageView.remove(key);
        }
    }

    private class MyCallBack implements ICallback<Boolean> {
        private final int mKey;
        private WeakReference<ImageView> mWeekView;
        private final FileUri mImageUri;
        private final DisplayImageOptions mOptions;

        MyCallBack(int key, ImageView imageView, FileUri uri, DisplayImageOptions options) {
            mKey = key;
            mWeekView = new WeakReference<>(imageView);
            mImageUri = uri;
            mOptions = options;
        }

        @Override
        public void onCallback(final Boolean objects) {
            Handler handler = EducationAppliction.getHandler();
            if (null != handler) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String uriKey = removeImageUri(mKey);
                        if (objects) {
                            ImageView ivView = mWeekView.get();
                            if (null != ivView) {
                                //判断当前显示的还是这个ImageUri?
                                String thisKey = generateKey(mImageUri);
                                if (TextUtils.equals(uriKey, thisKey)) {
                                    mImageLoader.displayImage(toLocalUri(mImageUri.getLocalPath()), ivView, mOptions);
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    private static String toLocalUri(String localFilePath) {
        if (TextUtils.isEmpty(localFilePath)) {
            return null;
        }

        return "file://" + localFilePath;
    }
}
