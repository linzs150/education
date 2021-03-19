package com.one.education.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.one.education.EducationAppliction;
import com.one.education.education.R;

import java.util.concurrent.ExecutionException;



/**
 * Created by chenliang_91 on 2015/7/8.
 */
public class ImageLoader {

    private final static int DEFAULT_ICON_RESID = R.drawable.teacher_icon_74x74;


    public static void loadImage(Uri uri, ImageView imageView) {


        //        GlideApp.with(EducationAppliction.getInstance()).loadFromMediaStore(uri).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        Glide.with(EducationAppliction.getInstance()).load(uri).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }


    public static void loadImageImmediately(String url, ImageView imageView, int placeHolderImgRes) {
        if (TextUtils.isEmpty(url) || imageView == null) {
            return;
        }

        Glide.with(EducationAppliction.getInstance()).load(trim(url)).fitCenter().placeholder(placeHolderImgRes).diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate().into(imageView);
    }

    public static void loadImage(String url, ImageView imageView) {
        if (TextUtils.isEmpty(url) || imageView == null) {
            return;
        }

        Glide.with(EducationAppliction.getInstance()).load(trim(url)).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public static void loadImageImmediately(String url, ImageView imageView) {
        if (TextUtils.isEmpty(url) || imageView == null) {
            return;
        }

        Glide.with(EducationAppliction.getInstance()).load(trim(url)).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate().into(imageView);
    }

    public static void loadImageCircle(final Context ctx, String url, final ImageView imageView) {
        if (TextUtils.isEmpty(url) || imageView == null) {
            return;
        }

        Glide.with(EducationAppliction.getInstance())
                .load(trim(url))
                .fitCenter()
                .error(R.drawable.teacher_icon_74x74) //异常时候显示的图片
                .placeholder(R.drawable.teacher_icon_74x74) //加载成功前显示的图片
                .fallback(R.drawable.teacher_icon_74x74)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition transition) {
                Bitmap bitmap = ((BitmapDrawable) (resource)).getBitmap();
                imageView.setImageDrawable(new CircleDrawable(ctx.getResources(), bitmap));
            }
        });
    }

    public static void loadImage(String url, SimpleTarget target) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(EducationAppliction.getInstance()).load(trim(url)).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(target);
    }

    public static void loadHomeImage(String url, ImageView imageView) {
        if (TextUtils.isEmpty(url) || imageView == null) {
            return;
        }
        Glide.with(EducationAppliction.getInstance()).load(url).format(DecodeFormat.PREFER_ARGB_8888).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }


    public static void loadAdImage(String url,  final ImageView imageView) {
        if (TextUtils.isEmpty(url) || imageView == null) {
            return;
        }

        Glide.with(EducationAppliction.getInstance()).load(trim(url)).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);

        //        GlideApp.with(EducationAppliction.getInstance()).load(trim(url)).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Drawable>() {
        //            @Override
        //            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
        //                imageView.setImageDrawable(resource);
        //            }
        //        });
//
//        Glide.with(EducationAppliction.getInstance()).load(trim(url)).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).fitCenter()).into(new BaseTarget<Drawable>() {
//            @Override
//            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
//                imageView.setImageDrawable(resource);
//            }
//
//            @Override
//            public void getSize(SizeReadyCallback cb) {
//            }
//
//            @Override
//            public void removeCallback(SizeReadyCallback cb) {
//
//            }
//        });

//        RequestBuilder<Drawable> requestBuilder =
//                Glide.with(EducationAppliction.getInstance())
//                        .asBitmap()
//                        .apply(requestOptions);



    }

    private static String trim(String url) {
        return url != null ? url.trim() : url;
    }

    public static void loadImage(String url, ImageView imageView, int placeHolderImgRes) {
        Glide.with(EducationAppliction.getInstance()).load(trim(url)).fitCenter().placeholder(placeHolderImgRes).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    //    public static void loadCircleImage(String url, ImageView imageView) {
    //        Context context = EducationAppliction.getInstance();
    //        BitmapPool pool = Glide.get(context).getBitmapPool();
    //        Glide.with(context).load(trim(url)).fitCenter().bitmapTransform(new CropCircleTransformation(pool)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    //    }


    public static Bitmap getImage(Context context, String url) {
        try {

            FutureTarget<Bitmap> bitmap = Glide.with(context).asBitmap().load(url).submit();
            return bitmap.get();

            //            return GlideApp.with(context).load(url).asBitmap().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void loadImageToDisk(Context context, final String url, final String dicPath, final String fileName) {
        loadImageToDisk(context, url, dicPath, null);
    }

    //    public static void loadImageToDisk(Context context, final String url, final String dicPath, final String fileName, final DownloadCompleteCallback callback) {
    //        final Context ctx = context.getApplicationContext();
    //        Glide.with(ctx).load(url).downloadOnly(new SimpleTarget<File>() {
    //            @Override
    //            public void onResourceReady(final File bitmapFile, GlideAnimation anim) {
    //                new AsyncTask<File, Void, File>() {
    //
    //                    @Override
    //                    protected File doInBackground(File... params) {
    //                        LogUtils.i("download", "loadImageToDisk");
    //                        String title = fileName;
    //                        //                                String dicPath = LocalWallpaegrManager.WALLPAGER_DISK;
    //                        File dic = new File(dicPath);
    //                        if (!dic.exists()) {
    //                            dic.mkdirs();
    //                        }
    //                        File dest = new File(dic, title);
    //                        try {
    //                            FileUtil.copyFile(params[0], dest);
    //                        } catch (IOException e) {
    //                            e.printStackTrace();
    //                        }
    //                        return dest;
    //                    }
    //
    //                    @Override
    //                    protected void onPostExecute(File destFile) {
    //                        try {
    //                            if (callback != null) {
    //                                callback.onComplete(destFile.getPath());
    //                            }
    //                            //                                    Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
    //                            //                                    scanIntent.setData(Uri.fromFile(destFile));
    //                            //                                    ctx.sendBroadcast(scanIntent);
    //                            //                                    SecurePreferences.getInstance().edit()
    //                        } catch (Exception e) {
    //
    //                        }
    //                        //                                ToastHelp.getInstance().showToast(R.string.save_success);
    //                    }
    //                }.execute(bitmapFile);
    //            }
    //        });
    //    }


    public interface DownloadCompleteCallback {

        void onComplete(String path);
    }

    //    public static class CircleTransform extends BitmapTransformation {
    //
    //        public CircleTransform(Context context) {
    //            super(context);
    //        }
    //
    //        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
    //            if (source == null)
    //                return null;
    //
    //            int size = Math.min(source.getWidth(), source.getHeight());
    //            int x = (source.getWidth() - size) / 2;
    //            int y = (source.getHeight() - size) / 2;
    //
    //            // TODO this could be acquired from the pool too
    //            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
    //
    //            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
    //            if (result == null) {
    //                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
    //            }
    //            Canvas canvas = new Canvas(result);
    //            Paint paint = new Paint();
    //            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
    //            paint.setAntiAlias(true);
    //            float r = size / 2f;
    //            canvas.drawCircle(r, r, r, paint);
    //            return result;
    //        }
    //
    //        @Override
    //        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
    //            return circleCrop(pool, toTransform);
    //        }
    //
    //        @Override
    //        public String getId() {
    //            return getClass().getName();
    //        }
    //    }

    //    public static class CropCircleTransformation implements Transformation<Bitmap> {
    //
    //        private BitmapPool mBitmapPool;
    //
    //        public CropCircleTransformation(BitmapPool pool) {
    //            this.mBitmapPool = pool;
    //        }
    //
    //        @Override
    //        public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
    //            Bitmap source = resource.get();
    //            int size = Math.min(source.getWidth(), source.getHeight());
    //
    //            int width = (source.getWidth() - size) / 2;
    //            int height = (source.getHeight() - size) / 2;
    //
    //            Bitmap bitmap = mBitmapPool.get(size, size, Bitmap.Config.ARGB_8888);
    //            if (bitmap == null) {
    //                bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
    //            }
    //
    //            Canvas canvas = new Canvas(bitmap);
    //            Paint paint = new Paint();
    //            BitmapShader shader = new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
    //            if (width != 0 || height != 0) {
    //                Matrix matrix = new Matrix();
    //                matrix.setTranslate(-width, -height);
    //                shader.setLocalMatrix(matrix);
    //            }
    //            paint.setShader(shader);
    //            paint.setAntiAlias(true);
    //
    //            float r = size / 2f;
    //            canvas.drawCircle(r, r, r, paint);
    //
    //            return BitmapResource.obtain(bitmap, mBitmapPool);
    //        }
    //
    //        @Override
    //        public String getId() {
    //            return "CropCircleTransformation()";
    //        }
    //    }
}
