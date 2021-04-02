package com.newtonacademic.newtontutors;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import uikit.api.NimUIKit;
import uikit.api.UIKitOptions;
import uikit.business.contact.core.query.PinYin;
import uikit.business.session.SessionHelper;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.newtonacademic.newtontutors.com.netease.nim.DemoCache;
import com.newtonacademic.newtontutors.com.netease.nim.DemoOnlineStateContentProvider;
import com.newtonacademic.newtontutors.com.netease.nim.NimSDKOptionConfig;
import com.newtonacademic.newtontutors.com.netease.nim.Preferences;
import com.newtonacademic.newtontutors.commons.Constants;
import com.newtonacademic.newtontutors.db.DBManager;
import com.newtonacademic.newtontutors.download.Download;
import com.newtonacademic.newtontutors.download.DownloadRunnableFactory;
import com.newtonacademic.newtontutors.language.MultiLanguageUtil;
import com.newtonacademic.newtontutors.language.SpUtil;
import com.newtonacademic.newtontutors.location.NimDemoLocationProvider;
import com.newtonacademic.newtontutors.utils.FileCacheManager;
import com.newtonacademic.newtontutors.utils.toast.ToastAliPayStyle;
import com.newtonacademic.newtontutors.utils.toast.ToastUtils;
import com.newtonacademic.newtontutors.widget.smartrefresh.layout.SmartRefreshLayout;
import com.newtonacademic.newtontutors.widget.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.newtonacademic.newtontutors.widget.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.newtonacademic.newtontutors.widget.smartrefresh.layout.api.RefreshFooter;
import com.newtonacademic.newtontutors.widget.smartrefresh.layout.api.RefreshHeader;
import com.newtonacademic.newtontutors.widget.smartrefresh.layout.api.RefreshLayout;
import com.newtonacademic.newtontutors.widget.smartrefresh.layout.footer.ClassicsFooter;
import com.newtonacademic.newtontutors.widget.smartrefresh.layout.header.ClassicsHeader;
import com.newtonacademic.mylibrary.ConstantGlobal;
import com.tencent.smtt.sdk.QbSdk;

import java.io.File;
import java.io.IOException;
import java.util.Locale;


/**
 * Created by dengyancheng on 16/2/23.
 */
public class EducationAppliction extends Application {

    private static EducationAppliction app;
    private static ImageLoader sImageLoader;
    private static Handler sHandler;
    private static Context sContext;

    public EducationAppliction() {
        app = this;
    }

    public static synchronized EducationAppliction getInstance() {
        if (app == null) {
            app = new EducationAppliction();
        }
        return app;
    }


    public static Handler getHandler() {
        return sHandler;
    }

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        //系统语言
        changeLanguage();
        registerActivityLifecycleCallbacks(callbacks);
        // 初始化吐司工具类
        ToastUtils.init(this, new ToastAliPayStyle(this));
        FileCacheManager.getInstance().initialize();
        Download.getInstance().initialize(sHandler, new DownloadRunnableFactory());
        //初始化数据库
        DBManager.getInstance().initialize(this);
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);

        smart();

        DemoCache.setContext(this);
        // 4.6.0 开始，第三方推送配置入口改为 SDKOption#mixPushConfig，旧版配置方式依旧支持。
        SDKOptions sdkOptions = NimSDKOptionConfig.getSDKOptions(this);
        NIMClient.init(this, getLoginInfo(), sdkOptions);
        // 以下逻辑只在主进程初始化时执行
        if (NIMUtil.isMainProcess(this)) {
            // init pinyin
            PinYin.init(this);
            PinYin.validate();
            // 初始化UIKit模块
            initUIKit();
        }
    }

    static {

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new ClassicsHeader(context).setDrawableSize(20);
            }
        });

        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });

    }

    private void smart() {
    }


    public static LoginInfo getLoginInfo() {
        String account = Preferences.getUserAccount();
        String token = Preferences.getUserToken();
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            DemoCache.setAccount(account.toLowerCase());
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }

    private void initUIKit() {
        // 初始化
        NimUIKit.init(this, buildUIKitOptions());
        // 设置地理位置提供者。如果需要发送地理位置消息，该参数必须提供。如果不需要，可以忽略。
        NimUIKit.setLocationProvider(new NimDemoLocationProvider());

        // IM 会话窗口的定制初始化。
        SessionHelper.init();

        NimUIKit.setOnlineStateContentProvider(new DemoOnlineStateContentProvider());
    }

    private UIKitOptions buildUIKitOptions() {
        UIKitOptions options = new UIKitOptions();
        // 设置app图片/音频/日志等缓存目录
        options.appCacheDir = NimSDKOptionConfig.getAppCacheDir(this) + "/app";
        return options;
    }

    private void changeLanguage() {
        String spLanguage = SpUtil.getString(getApplicationContext(), ConstantGlobal.LOCALE_LANGUAGE);
        String spCountry = SpUtil.getString(getApplicationContext(), ConstantGlobal.LOCALE_COUNTRY);

        if (!TextUtils.isEmpty(spLanguage) && !TextUtils.isEmpty(spCountry)) {
            // 如果有一个不同
            if (!MultiLanguageUtil.isSameWithSetting(this)) {
                Locale locale = new Locale(spLanguage, spCountry);
                MultiLanguageUtil.changeAppLanguage(getApplicationContext(), locale, false);
            }
        } else {
            Locale locale = Locale.getDefault();
            MultiLanguageUtil.saveLanguageSetting(this, locale);
            MultiLanguageUtil.changeAppLanguage(this, locale, true);
        }
    }

    //multidex 分包 必须添加
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static ImageLoader getImageLoader() {
        if (null == sImageLoader) {
            sImageLoader = ImageLoader.getInstance();

            LruDiskCache lruDiskCache;
            try {
                lruDiskCache = new LruDiskCache(new File(Constants.EXT_IMAGE_LOADER_CACHE), new Md5FileNameGenerator(), 1024 * 1024 * 10);
            } catch (IOException e) {
                return null;
            }

            // 创建配置过得DisplayImageOption对象
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(EducationAppliction.getInstance()).threadPriority(Thread.NORM_PRIORITY - 2)
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator()).diskCache(lruDiskCache).tasksProcessingOrder(QueueProcessingType.LIFO)
                    .imageDecoder(new BaseImageDecoder(false))
                    .diskCacheSize(50 * 1024 * 1024)
                    .diskCacheFileCount(100)
                    .memoryCacheExtraOptions(480, 800)
                    .diskCacheExtraOptions(480, 800, null).threadPoolSize(3)
                    .build();

            sImageLoader.init(config);
        }

        return sImageLoader;
    }


//    @Override
//    protected void attachBaseContext(Context base) {
//        //系统语言等设置发生改变时会调用此方法，需要要重置app语言
//        super.attachBaseContext(MultiLanguageUtil.attachBaseContext(base));
//    }

    ActivityLifecycleCallbacks callbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            String language = SpUtil.getString(getApplicationContext(), ConstantGlobal.LOCALE_LANGUAGE);
            String country = SpUtil.getString(getApplicationContext(), ConstantGlobal.LOCALE_COUNTRY);
            if (!TextUtils.isEmpty(language) && !TextUtils.isEmpty(country)) {
                //强制修改应用语言
                if (!MultiLanguageUtil.isSameWithSetting(activity)) {
                    Locale locale = new Locale(language, country);
                    MultiLanguageUtil.changeAppLanguage(activity, locale, false);
//                    activity.recreate();
                }
            }
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
//            String language = SpUtil.getString(getApplicationContext(), ConstantGlobal.LOCALE_LANGUAGE);
//            String country = SpUtil.getString(getApplicationContext(), ConstantGlobal.LOCALE_COUNTRY);
//            if (!TextUtils.isEmpty(language) && !TextUtils.isEmpty(country)) {
//                //强制修改应用语言
//                if (!MultiLanguageUtil.isSameWithSetting(activity)) {
//                    Locale locale = new Locale(language, country);
//                    MultiLanguageUtil.changeAppLanguage(activity, locale, false);
////                    activity.recreate();
//                }
//            }
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }

    };

}
