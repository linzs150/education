/**
 * Copyright (c) 2015, Intretech All rights reserved.
 *
 * @Description
 * @author zhuangjianwei
 * @email fzhzjw@intretech.com
 * @date 2016年06月01日
 * @version v1.0
 */
package com.one.education.display;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * 灰色圆形显示
 */
public class GrayRoundedBitmapDisplayer implements BitmapDisplayer
{
    protected final int cornerRadius;
    protected final int margin;

    public GrayRoundedBitmapDisplayer(int cornerRadiusPixels) {
        this(cornerRadiusPixels, 0);
    }

    public GrayRoundedBitmapDisplayer(int cornerRadiusPixels, int marginPixels) {
        this.cornerRadius = cornerRadiusPixels;
        this.margin = marginPixels;
    }

    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
        if(!(imageAware instanceof ImageViewAware)) {
            throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
        } else {
            imageAware.setImageDrawable(new RoundedDrawable(bitmap, this.cornerRadius, this.margin));
        }
    }

    public static class RoundedDrawable extends Drawable
    {
        protected final float cornerRadius;
        protected final int margin;
        protected final RectF mRect = new RectF();
        protected final RectF mBitmapRect;
        protected final BitmapShader bitmapShader;
        protected final Paint paint;

        public RoundedDrawable(Bitmap bitmap, int cornerRadius, int margin) {
            this.cornerRadius = (float)cornerRadius;
            this.margin = margin;
            this.bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            this.mBitmapRect = new RectF((float)margin, (float)margin, (float)(bitmap.getWidth() - margin), (float)(bitmap.getHeight() - margin));
            this.paint = new Paint();
            this.paint.setAntiAlias(true);
            this.paint.setShader(this.bitmapShader);
            this.paint.setFilterBitmap(true);
            this.paint.setDither(true);
        }

        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            this.mRect.set((float)this.margin, (float)this.margin, (float)(bounds.width() - this.margin), (float)(bounds.height() - this.margin));
            Matrix shaderMatrix = new Matrix();
            shaderMatrix.setRectToRect(this.mBitmapRect, this.mRect, Matrix.ScaleToFit.FILL);
            this.bitmapShader.setLocalMatrix(shaderMatrix);

            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0);
            ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
            this.paint.setColorFilter(colorMatrixFilter);
        }

        public void draw(Canvas canvas) {
            canvas.drawRoundRect(this.mRect, this.cornerRadius, this.cornerRadius, this.paint);
        }

        public int getOpacity() {
            return -3;
        }

        public void setAlpha(int alpha) {
            this.paint.setAlpha(alpha);
        }

        public void setColorFilter(ColorFilter cf) {
            this.paint.setColorFilter(cf);
        }
    }
}

