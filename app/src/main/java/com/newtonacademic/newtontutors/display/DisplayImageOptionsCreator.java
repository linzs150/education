package com.newtonacademic.newtontutors.display;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.newtonacademic.newtontutors.R;
import com.newtonacademic.newtontutors.fragments.FlexibleRoundedBitmapDisplayer;

/**
 * DisplayImageOptions
 */
public class DisplayImageOptionsCreator {
    /**
     * 用户显示选项
     */
    private static DisplayImageOptions sUserDisplayImageOptions = null;


    /**
     * 获取默认的用户显示选项
     */
    public static DisplayImageOptions getDefaultUserDisplayImageOptions() {
        if (null == sUserDisplayImageOptions) {
            sUserDisplayImageOptions = createUserDisplayImageOptionsBuilder().build();
        }

        return sUserDisplayImageOptions;
    }

    /**
     * 创建用户DisplayImageOptions.Builder
     */
    public static DisplayImageOptions.Builder createUserDisplayImageOptionsBuilder() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.teacher_icon_74x74)
                .showImageForEmptyUri(R.drawable.teacher_icon_74x74)
                .showImageOnFail(R.drawable.teacher_icon_74x74)
                .cacheInMemory(true)
                .displayer(new RoundedBitmapDisplayer(360))
                .bitmapConfig(Bitmap.Config.RGB_565);
    }

    /**
     * 创建用户DisplayImageOptions.Builder
     */
    public static DisplayImageOptions.Builder createUserDisplayImageOptionsBuilder2() {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .displayer(new RoundedBitmapDisplayer(30))
                .bitmapConfig(Bitmap.Config.RGB_565);
    }

    /**
     * 左上，右上圆角
     * @return
     */
    public static DisplayImageOptions.Builder createLeftRightTopImageOptionsBuilder() {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .displayer(new FlexibleRoundedBitmapDisplayer(30,
                        FlexibleRoundedBitmapDisplayer.CORNER_TOP_LEFT | FlexibleRoundedBitmapDisplayer.CORNER_TOP_RIGHT))
                .bitmapConfig(Bitmap.Config.RGB_565);
    }
}
