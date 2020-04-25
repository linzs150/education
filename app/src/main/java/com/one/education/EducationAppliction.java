package com.one.education;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;


/**
 * Created by dengyancheng on 16/2/23.
 */
public class EducationAppliction extends Application {

    private static EducationAppliction app;

    public EducationAppliction() {
        app = this;
    }

    public static synchronized EducationAppliction getInstance() {
        if (app == null) {
            app = new EducationAppliction();
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    //multidex 分包 必须添加
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
