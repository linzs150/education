package com.one.education.commons;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.one.education.EducationAppliction;
import com.one.education.education.R;

import java.util.Timer;
import java.util.TimerTask;


/**
 * @ClassName: ToastTool
 * @Description: TODO Toast工具
 */
public class ToastUtils {
    private static int oldintMsg;
    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;
    private static long lastShowTime = 0;
    private static final long NEXT_SHOW = 10000;
    protected static Toast customToast = null;
    private static LayoutInflater inflater;
    private static TextView overlay;
    private static View mView;

    public static void showToast(Context context, String text, int duration) {

        /***
         * 旧版toast
         * **/
      /*  if (toast == null) {
            toast = Toast.makeText(context, text, duration);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (twoTime - oneTime > duration) {
                oldMsg = text;
                toast.setDuration(duration);
                toast.setText(oldMsg);
                toast.show();
            }
        }
        oneTime = twoTime;*/
        //新版toast样式
        customNewToast(context, text, duration);
    }

    public static void showMyToast(Context context, String str, final int cnt) {
        final Toast toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt);

    }


    public static void showToastError(String text) {
        //        if (toast == null) {
        //            toast = Toast.makeText(App.context, text, Toast.LENGTH_SHORT);
        //        } else {
        //            toast.setDuration(Toast.LENGTH_SHORT);
        //            toast.setText(text);
        //        }
        //        long now = System.currentTimeMillis();
        //        if (lastShowTime + NEXT_SHOW <= now) {
        //            toast.show();
        //            lastShowTime = now;
        //        }
        customNewToast(EducationAppliction.getInstance(), text, Toast.LENGTH_SHORT);

    }

    public static void showToast(Context context, int text, int duration) {
        //        if (toast == null) {
        //            toast = Toast.makeText(context, text, duration);
        //            toast.show();
        //            oneTime = System.currentTimeMillis();
        //        } else {
        //            twoTime = System.currentTimeMillis();
        //            if (twoTime - oneTime > duration) {
        //                oldintMsg = text;
        //                toast.setDuration(duration);
        //                toast.setText(oldintMsg);
        //                toast.show();
        //            }
        //        }
        //        oneTime = twoTime;

        customNewToast(context, context.getString(text), duration);
    }

    public static void showToastShort(Context context, String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    public static void showToastShort(Context context, int text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    public static void showToastLong(Context context, String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        showToast(context, text, Toast.LENGTH_LONG);
    }

    public static void showToastLong(Context context, int text) {
        showToast(context, text, Toast.LENGTH_LONG);
    }

    public static void showToastShort(String message) {
        //        if (TextUtils.isEmpty(message)) {
        //            return;
        //        }
        //        Toast.makeText(App.getInstance().getBaseContext(), message, Toast.LENGTH_SHORT).show();
        customNewToast(EducationAppliction.getInstance().getBaseContext(), message, Toast.LENGTH_SHORT);
    }

    public static void showToastShort(int message) {
        //        Toast.makeText(App.getInstance().getBaseContext(), message, Toast.LENGTH_SHORT).show();

        customNewToast(EducationAppliction.getInstance().getBaseContext(), EducationAppliction.getInstance().getBaseContext().getString(message), Toast.LENGTH_SHORT);
    }

    public static void showToastLong(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }

        //        Toast.makeText(App.getInstance().getBaseContext(), message, Toast.LENGTH_LONG).show();
        customNewToast(EducationAppliction.getInstance().getBaseContext(), message, Toast.LENGTH_LONG);
    }

    public static void showToastLong(int message) {
        //        Toast.makeText(App.getInstance().getBaseContext(), message, Toast.LENGTH_LONG).show();
        customNewToast(EducationAppliction.getInstance().getBaseContext(), EducationAppliction.getInstance().getBaseContext().getString(message), Toast.LENGTH_LONG);
    }

//    public static void customToast(Context context, String text, int duration) {
//        if (inflater == null) {
//            inflater = LayoutInflater.from(context);
//        }
//        if (overlay == null) {
//            overlay = (TextView) inflater.inflate(R.layout.overlay, null);
//        }
//        // LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//        // TextView layout = inflater.inflate(R.layout.overlay,null);
//        overlay.setText(text);
//        if (customToast == null) {
//            customToast = new Toast(context);
//            customToast.setGravity(Gravity.CENTER, 0, 0);
//            customToast.setDuration(duration);
//            customToast.setView(overlay);
//            customToast.show();
//            oneTime = System.currentTimeMillis();
//        } else {
//            twoTime = System.currentTimeMillis();
//
//            if (twoTime - oneTime > duration) {
//                customToast.setGravity(Gravity.CENTER, 0, 0);
//                customToast.setDuration(duration);
//                customToast.setView(overlay);
//                customToast.show();
//            }
//        }
//        oneTime = twoTime;
//    }


    private View view;

    public static void customNewToast(Context context, String text, int duration) {
        //        if (inflater == null) {
        //            inflater = LayoutInflater.from(context);
        //        }
        //        if (mView == null) {
        //            mView = inflater.inflate(R.layout.new_ipv6, null);
        //        }

        show(text);

        //        LayoutInflater inflater = LayoutInflater.from(context);
        //        View view = inflater.inflate(R.layout.new_ipv6, null);
        //        TextView tv = (TextView) view.findViewById(R.id.tt);
        //        tv.setText(text);
        //
        //        // LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        //        // TextView layout = inflater.inflate(R.layout.overlay,null);
        //        if (customToast == null) {
        //            customToast = new Toast(context);
        //            //            customToast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
        //            customToast.setDuration(duration);
        //            customToast.setView(view);
        //            //            customToast.show();
        //            //            oneTime = System.currentTimeMillis();
        //        } else {
        //            customToast.setView(view);
        //            //            twoTime = System.currentTimeMillis();
        //            //
        //            //
        //            //            {
        //            //                //                customToast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
        //            //                customToast.setDuration(duration);
        //            //                customToast.setView(overlay);
        //            //                customToast.show();
        //            //            }
        //        }
        //        //        oneTime = twoTime;
        //
        //        customToast.show();//展示toast

    }


    private static Toast mToast;

    public static void show(String text) {
        TextView msgTv = null;
        if (mToast == null) {
            mToast = new Toast(EducationAppliction.getInstance());

            View view = View.inflate(EducationAppliction.getInstance(), R.layout.my_toast_layout, null);
            msgTv = (TextView) view.findViewById(R.id.tt);
            msgTv.setText(text);

            mToast.setView(view);
            mToast.setDuration(Toast.LENGTH_SHORT);
        } else {
            msgTv = (TextView) mToast.getView().findViewById(R.id.tt);
            msgTv.setText(text);
        }
        mToast.show();
    }




}
