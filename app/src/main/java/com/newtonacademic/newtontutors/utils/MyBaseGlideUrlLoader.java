package com.newtonacademic.newtontutors.utils;


import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lzs on 2019/3/6.
 */

public class MyBaseGlideUrlLoader extends BaseGlideUrlLoader<String> {


    private static final ModelCache<String, GlideUrl> mModelCache = new ModelCache<>(150);

    private MyBaseGlideUrlLoader(ModelLoader<GlideUrl, InputStream> modelLoader, ModelCache<String, GlideUrl> modelCache) {
        super(modelLoader, modelCache);
    }

    @Override // __w-200-400-800__
    protected String getUrl(String s, int width, int height, Options options) {
        Matcher matcher = Pattern.compile("__w-((?:-?\\d+)+)__").matcher(s);
        int bestBucket = 0;
        if (matcher.find()) {
            String[] found = matcher.group(1).split("-");
            for (String bucket : found) {
                bestBucket = Integer.parseInt(bucket);
                if (bestBucket >= width)
                    break;
            }
            if (bestBucket > 0)
                s = matcher.replaceFirst("w" + bestBucket);
        }
        return s;
    }

    @Override
    public boolean handles(String s) {
        return true;
    }

    /**
     * 工厂
     */
    public static class MyFactory implements ModelLoaderFactory<String, InputStream> {

        @Override
        public ModelLoader<String, InputStream> build(MultiModelLoaderFactory multiFactory) {
            return new MyBaseGlideUrlLoader(multiFactory.build(GlideUrl.class, InputStream.class), mModelCache);
        }

        @Override
        public void teardown() {}
    }


}
