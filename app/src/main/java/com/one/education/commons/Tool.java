package com.one.education.commons;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Tool {
    private static long lastClickTime;


    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm;
    }
    /**
     * 传入时间差
     *
     * @param millision
     * @return
     */
    public synchronized static boolean isFastClick(long millision) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < millision) {
            return true;
        }
        lastClickTime = time;
        return false;
    }



    @SuppressLint("NewApi")
    public static boolean checkDeviceHasNavigationBar(Context context) {

        // 通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        return !hasMenuKey || !hasBackKey;
    }


    public static boolean checkEmail(String email) {
        return stringFilter("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",
                email);
    }

    public static Boolean stringFilter(String regEx, String str) throws PatternSyntaxException {
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    public static String stringFilter(String str) {
        str = str.replaceAll("【", "[").replaceAll("】", "]").replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static void hideInputMethod(Context context, View view) {
        try {
            InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static boolean isSingleScreen(Context context) {
        boolean result = false;
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        int desiredHeight = wallpaperManager.getDesiredMinimumHeight();
        int desiredWidth = wallpaperManager.getDesiredMinimumWidth();
        if ((desiredHeight > 0 && desiredWidth > 0) && (desiredHeight > desiredWidth)) {
            result = true;
        }
        return result;
    }


    public static void showKeyboard(Context context, View view) {
        view.requestFocus();
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(0,
                InputMethodManager.SHOW_FORCED);
    }


    public static boolean isTopActivity(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // android 如何判断程序是否在前台运行
        List<RunningTaskInfo> tasksInfo = am.getRunningTasks(1);
        LogUtils.d("getTopActivityPackageName", tasksInfo.get(0).topActivity.getPackageName());
        if (tasksInfo.size() > 0) {
            // 应用程序位于堆栈的顶层
            if ("com.bodong.androidwallpaper".equals(tasksInfo.get(0).topActivity.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static void openBrowser(Context context, String url) {
        try {
            if (!TextUtils.isEmpty(url)) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri uri = Uri.parse(url);
                intent.setData(uri);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean equals(Object a, Object b) {
        return (a == null) ? (b == null) : a.equals(b);
    }


    public static String trim(String value) {
        return TextUtils.isEmpty(value) ? value : value.trim();
    }


    /**
     * 格式化价格 2位小数
     *
     * @param price
     * @return
     */
    public static String formatPrice(double price) {
        BigDecimal b = new BigDecimal(price);
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1 + "";
    }

    /**
     * 格式化价格 2位小数
     *
     * @param price
     * @return
     */
    public static String formatPrice(String price) {
        String res;
        try {
            BigDecimal b = new BigDecimal(price);
            res = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "";
        } catch (Exception e) {
            e.printStackTrace();
            res = price;
        }
        return res;
    }

    /**
     * 格式化为整数,往上进位, 整数
     *
     * @param num
     * @return
     */
    public static String formatNumNoScale(String num) {
        String res;
        try {
            double number = Double.parseDouble(num);
            res = (int) number + "";
        } catch (Exception e) {
            e.printStackTrace();
            res = num;
        }
        return res;
    }



    /**
     * 安装应用
     *
     * @param context
     * @param paramString
     */
    public static void installApk(Context context, String paramString) {
        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setDataAndType(Uri.fromFile(new File(paramString)), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * activity是否在栈顶
     *
     * @param context
     * @param className
     * @return
     */
    public static boolean isTopActivity(Context context, String className) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

//        String name = manager.getRunningTasks(1).get(0).topActivity.getClassName();

        List<RunningTaskInfo> tasksInfo = manager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            String topActivityName = tasksInfo.get(0).topActivity.getClassName();
            if (topActivityName.equals(className)) {
                return true;
            }
        }
        return false;
    }

    public static int px2dip(Context context, int px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
    }

    public static int dp2px(Context context, int value) {
        float v = context.getResources().getDisplayMetrics().density;
        return (int) (v * value + 0.5f);
    }

    public static int sp2px(Context context, int value) {
        float v = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (v * value + 0.5f);
    }

}