package com.one.education.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

/**
 * Created by lzs on 2019/3/6.
 */

@GlideModule
public class MyAppGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //        super.applyOptions(context, builder);
        // 重新设置内存限制
        builder.setMemoryCache(new LruResourceCache(10 * 1024 * 1024));
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        //        super.registerComponents(context, glide, registry);
        registry.append(String.class, InputStream.class, new MyBaseGlideUrlLoader.MyFactory());
    }

    @Override
    public boolean isManifestParsingEnabled() {
        //        return super.isManifestParsingEnabled();
        return false;
    }


}
