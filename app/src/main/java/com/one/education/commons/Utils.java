package com.one.education.commons;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.one.education.EducationAppliction;
import com.one.education.language.ConstantGlobal;
import com.one.education.language.SpUtil;
import com.one.education.retrofit.model.GetArticleListRsp;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具类
 */
public class Utils {

    /**
     * android2.1 7
     */
    public static final int SDK = 7;

    /**
     * 无连接
     */
    public static final int STATE_CONNECT_NONE = 2;

    /**
     * WIFI连接
     */
    public static final int STATE_CONNECT_WIFI = 1;

    /**
     * 移动网络 2G/3G
     */
    public static final int STATE_CONNECT_MOBILE = 0;

    public static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * 获取bitmap的size大小.
     *
     * @param bitmap
     * @return size in bytes
     */
    @SuppressLint("NewApi")
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        }
        // Pre HC-MR1
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    /**
     * yyyy-MM月dd日
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getSystemTimeYMD() {
        // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter.format(curDate);
    }

    /**
     * yyyy-MM月dd日
     *
     * @param
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getSystemTimeYMDhms() {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy年MM月dd日 HH时mm分ss秒");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter.format(curDate);
    }

    /**
     * yyyyMMddhhmmss
     *
     * @param
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getSystemTimeYMDhms1() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter.format(curDate);
    }

    /**
     * yyyy-MM月
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getSystemTimeYM() {
        // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter.format(curDate);
    }

    /**
     * 年月日，时分秒
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getSystemTimeYMD_HMS(Context context) {

        try {
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());// 获取当前时间

            return formatter.format(curDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "1977-01-01 00:00:00";
    }

    public static String getSystemTime(SimpleDateFormat formatter) {
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    /**
     * yyyy
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getSystemTimeY() {
        // SimpleDateFormat formatter = new SimpleDateFormat("yyyy年");
        // Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        // return formatter.format(curDate);
        return "2";
    }

    /**
     * 获取内存大小
     *
     * @param context
     * @return
     */
    public static int getMemoryClass(Context context) {
        return ((ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
    }

    /**
     * 获取系统振动类型。
     *
     * @param context
     */
    public static int getVibrateSetting(Context context) {
        int result = -1;
        try {
            AudioManager mAudioManager = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
            result = mAudioManager
                    .getVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 是否支持actionbar
     *
     * @return
     */
    public static boolean hasActionBar() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * 是否Root
     *
     * @return
     */
    public static boolean isRoot() {

        String sys = System.getenv("PATH");
        ArrayList<String> commands = new ArrayList<String>();
        String[] path = sys.split(":");
        for (int i = 0; i < path.length; i++) {
            String commod = "ls -1" + path[i] + "/su";
            commands.add(commod);
        }
        ArrayList<String> res = run("/system/bin/sh", commands);
        String response = "";
        for (int i = 0; i < res.size(); i++) {
            response += res.get(i);
        }

        String root = "-rwsr-sr-x root   root";

        return false;
    }

    public static ArrayList<String> run(String shell, ArrayList<String> commands) {

        ArrayList<String> output = new ArrayList<String>();
        Process process = null;

        try {

            process = Runtime.getRuntime().exec(shell);
            BufferedOutputStream shellInput = new BufferedOutputStream(
                    process.getOutputStream());
            BufferedReader shellOutput = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            for (String command : commands) {
                shellInput.write((command + " 2>&1\n").getBytes());
            }

            shellInput.write("exit\n".getBytes());
            shellInput.flush();

            String line;
            while ((line = shellOutput.readLine()) != null) {
                output.add(line);
            }

            process.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            process.destroy();
        }

        return output;
    }


    /**
     * 返回网络的连接状态
     *
     * @param context
     * @return
     */
    public static int getNetConnectState(Context context) {
        try {
            final ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
                return STATE_CONNECT_NONE;
            } else if (ConnectivityManager.TYPE_WIFI == networkInfo.getType()) {
                return ConnectivityManager.TYPE_WIFI;
            } else {
                return ConnectivityManager.TYPE_MOBILE;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return STATE_CONNECT_NONE;

    }

    public static void removeList(List list) {
        if (list != null) {
            list.clear();
            list = null;
        }
    }

    public static void removeList(ArrayList list) {
        if (list != null) {
            list.clear();
            list = null;
        }
    }

    public static void removeMap(Map map) {
        if (map != null) {
            map.clear();
            map = null;
        }
    }

    public static void startActivity0(Context context, Class c) {
        Intent i = new Intent();
        i.setClass(context, c);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    /**
     * 从InputStream中读取数据并转为字符串类型
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static String convertToString(InputStream is) throws IOException {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
            String buff;
            while ((buff = reader.readLine()) != null) {
                builder.append(buff);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return builder.toString();
    }

    /**
     * Function: 保存图片文件流到指定的文件名 返回boolean
     *
     * @author Xiang DateTime 2011-12-26 上午11:14:21
     */
    public static boolean savePicInStream2sdCard(String dirName,
                                                 String fileName, Bitmap bitmap) {
        boolean result = false;
        if (!"".equals(dirName) && !"".equals(fileName) && bitmap != null) {
            OutputStream bufferedOutputStream = null;
            try {
                // 文件夹
                File dirFile = new File(dirName);
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }
                // 文件
                File file = new File(dirFile, fileName);
                file.createNewFile();
                bufferedOutputStream = new BufferedOutputStream(
                        new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.PNG, 100,
                        bufferedOutputStream);
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeOutStream(bufferedOutputStream);
            }
        }
        return result;
    }

    /**
     * 文件是否存在
     */
    public static boolean isExistOfFile(String dirName, String fileName) {
        if (dirName != null && fileName != null && !"".equals(dirName.trim())
                && !"".equals(fileName.trim())) {
            File file = new File(dirName, fileName);
            return file.exists();
        }
        return false;
    }

    /**
     * Function: 字节输出流的安全关闭
     *
     * @author Xiang DateTime 2011-12-26 上午11:24:45
     */
    public static void closeOutStream(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
                outputStream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取应用的安装时间 AIP大于等于9的时候才有这个属性
     *
     * @param context
     * @return
     */
    @SuppressLint({"NewApi", "SimpleDateFormat"})
    public static String getAppInstallDate(Context context) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");

        PackageManager pManager = context.getPackageManager();
        long time = 0;
        // 获取手机内所有应用
        List<PackageInfo> paklist = pManager.getInstalledPackages(0);
        for (int i = 0; i < paklist.size(); i++) {

            PackageInfo pak = (PackageInfo) paklist.get(i);
            if (pak.applicationInfo.packageName.equals(context
                    .getApplicationContext().getPackageName())) {
                time = pak.firstInstallTime;
            }
        }
        return formatter.format(time);
    }

    /**
     * 获取应用的安装时间 AIP小于9的时候才有这个属性
     *
     * @param context
     * @return
     */
    @SuppressLint({"NewApi", "SimpleDateFormat"})
    public static String getAppInstallDateAIPLess9(Context context) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        Date time = new Date(new File(applicationInfo.sourceDir).lastModified());
        return formatter.format(time);
    }

    /**
     * 是否是特殊字符
     *
     * @param str
     * @return
     */
    private boolean compileExChar(String str) {

        String limitEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

        Pattern pattern = Pattern.compile(limitEx);
        Matcher m = pattern.matcher(str);

        if (m.find()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 获取单位为 元 的话费
     */
    public static String getMoneyUnit(String fare) {
        if (fare == null || fare.equals("")) {
            return "";
        }
        //        if (!TextUtils.isEmpty(fare)) {
        //            return fare;
        //        }
        if (!isNumber(fare)) {
            return fare;
        }
        int mbValue = Integer.valueOf(fare);
        BigDecimal temp = new BigDecimal(mbValue);
        BigDecimal divide = new BigDecimal(1000);
        BigDecimal trafficBG = temp.divide(divide, 2, BigDecimal.ROUND_HALF_UP);
        return trafficBG.toString();
    }

    /**
     * 获取单位为 元 的话费
     */
    public static String getMoneyWithUnit(String fare) {
        return getMoneyUnit(fare) + " 元";
    }

    /**
     * 判断是否为纯数字
     */
    public static boolean isNumber(String str) {
        int i, j;
        String strTemp = "-0123456789"; // 数字的匹配模式
        if (str == null || "".equals(str))
            return false;
        for (i = 0; i < str.length(); i++) {
            j = strTemp.indexOf(str.charAt(i));
            if (j == -1) {// 说明含有不是数字的字符
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否为纯字母
     */
    public static boolean isLetter(String str) {
        if (str == null || "".equals(str))
            return false;
        Pattern pattern = Pattern.compile("[a-zA-Z]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 获取当前任务栈顶的activity
     *
     * @return packageName
     */
    public static String getActivity(Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String packageName = cn.getPackageName();
        // LogUtils.i("任务栈顶的应用包名", packageName);

        return packageName;
    }

    /**
     * Change value from pix to dip
     *
     * @param context
     * @param pxValue
     * @return
     */
    public final static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = context.getResources().getDimensionPixelSize(
                        x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }

    /***************************************************************************
     * 函数功能：判断指定字符串是否为福建移动号码 参数说明：13[456789]|147|15[012789]|18[2378]
     *
     * @str:待判断的字符串 返回值：是返回true;否则返回false;
     **************************************************************************/
    public static boolean isChinaMobleNumber(String str) {
        boolean flag = false;
        if (TextUtils.isEmpty(str)) {
            return flag;
        }
        String mobile_number_regular = "(13[0123]|145|15[356]|18[01569])\\d{8}";
        // String mobile_number_regular =
        // "(13[456789]|147|15[012789]|18[23478])\\d{8}";
        Pattern pattern = Pattern.compile(mobile_number_regular);
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches())
            flag = true;
        else
            flag = false;
        return flag;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    public static String getAlpha(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        // 正则表达式，判断首字母是否是英文字母
        if (str.matches("[A-Z]")) {
            return str;
        } else {
            return "#";
        }
    }

    /**
     * 检查手机号码正确性
     *
     * @param context
     * @return
     */
    public static boolean isRightPhone(Context context, String phone) {
        if ("".equals(phone)) {
            ToastUtils.showToastShort(context, "请输入手机号码");
            return false;
        }
        if (phone.length() != 11) {
            ToastUtils.showToastShort(context, "号码输入有误");
            return false;
        }
        return true;
    }

    /**
     * 检查手机号码正确性
     *
     * @param context
     * @return
     */
    public static boolean isRightCheck(Context context, String check) {
        if ("".equals(check)) {
            ToastUtils.showToastShort(context, "请输入验证码");
            return false;
        }
        if (check.length() != 6) {
            ToastUtils.showToastShort(context, "验证码输入有误");
            return false;
        }
        return true;
    }

    /**
     * 动态获取list高度
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    public static int getListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return 0;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        return totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
    }


    /**
     * 判断是否在模拟器中运行
     *
     * @param context
     * @return
     */
    public final static int TYPE_NORMAL = 0;
    public final static int TYPE_EMULATOR = 1;
    public final static int TYPE_FORBID = 2;

    public static int isEmulator(Context context) {
        int type = TYPE_NORMAL;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();
            LogUtils.d("Utils", "imei=" + imei);
            if (imei != null && imei.equals("000000000000000")) {
                String cpuInfo = readCpuInfo();
                if ((cpuInfo.contains("intel") || cpuInfo.contains("amd"))) {
                    type = TYPE_EMULATOR;
                } else {
                    type = TYPE_FORBID;
                }
            } else if (Build.MODEL.equals("sdk") || Build.MODEL.equals("google_sdk")) {
                type = TYPE_EMULATOR;
            }
        } catch (Exception e) {
            e.printStackTrace();
            type = TYPE_FORBID;
        }
        return type;
    }

    public static String readCpuInfo() {
        String result = "";
        try {
            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
            ProcessBuilder cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            StringBuffer sb = new StringBuffer();
            String readLine = "";
            BufferedReader responseReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), "utf-8"));
            while ((readLine = responseReader.readLine()) != null) {
                sb.append(readLine);
            }
            responseReader.close();
            result = sb.toString().toLowerCase();
        } catch (IOException ex) {
        }
        return result;
    }

    public static float FormateNumber(float value) {
        float temp = 0;

        try {
            BigDecimal b = new BigDecimal(value);
            temp = b.setScale(3, BigDecimal.ROUND_HALF_UP).floatValue();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return temp;
    }

    public static float FormateNumberPie(float value) {
        float temp = 0;

        try {
            BigDecimal b = new BigDecimal(value);
            temp = b.setScale(4, BigDecimal.ROUND_HALF_UP).floatValue();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return temp;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm;
    }

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
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

    public static int dp2pxFloor(Context context, int value) {
        int v = (int) context.getResources().getDisplayMetrics().density;
        return v * value;
    }

    public static float getTextWidth(Context context, String text, int textSize) {
        TextPaint paint = new TextPaint();
        Rect rect = new Rect();
        paint.setTextSize(sp2px(context, textSize));
        paint.getTextBounds(text, 0, text.length(), rect);
//	    TextPaint paint = new TextPaint();
//	    paint.setTextSize(sp2px(context,textSize));
        return rect.width();
    }

    public static int getRealHeight(View child) {
        int realHeight = 0;
        LinearLayout llayout = (LinearLayout) child;
        ViewGroup.LayoutParams params = llayout.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
        }
        int widthSpec = ViewGroup.getChildMeasureSpec(MeasureSpec.UNSPECIFIED, 0, params.width);
        int heightSpec;
        if (params.height <= 0) {
            heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        } else {
            heightSpec = MeasureSpec.makeMeasureSpec(params.height, MeasureSpec.EXACTLY);
        }
        llayout.measure(widthSpec, heightSpec);
        realHeight = llayout.getMeasuredHeight();
        return realHeight;
    }


    public static int getRealHeight1(View child) {
        int realHeight = 0;
        RelativeLayout llayout = (RelativeLayout) child;
        ViewGroup.LayoutParams params = llayout.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
        }
        int widthSpec = ViewGroup.getChildMeasureSpec(MeasureSpec.UNSPECIFIED, 0, params.width);
        int heightSpec;
        if (params.height <= 0) {
            heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        } else {
            heightSpec = MeasureSpec.makeMeasureSpec(params.height, MeasureSpec.EXACTLY);
        }
        llayout.measure(widthSpec, heightSpec);
        realHeight = llayout.getMeasuredHeight();
        return realHeight;
    }


    public static boolean isNumeric(String var0) {
        Pattern var1 = Pattern.compile("[0-9]*");
        Matcher var2 = var1.matcher(var0);
        return var2.matches();
    }

    public static int GetDimension(Context context, int resource_id) {
        return (int) context.getResources().getDimension(resource_id);
    }

    public static String spliteData(String data, String regex) {
        StringBuffer temp = new StringBuffer();
        String buffer = "";
        if (!TextUtils.isEmpty(data)) {
            String[] strings = data.split(regex);

            for (String s : strings) {
                temp.append(s).append("\n");
            }

            buffer = temp.toString().substring(0, temp.toString().length() - 1);


        }

        return buffer;
    }

    /**
     * 获取系统语言
     *
     * @return <zh, CN></>
     */
    public static Pair<String, String> getLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = EducationAppliction.getContext().getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = EducationAppliction.getContext().getResources().getConfiguration().locale;
        }
        return new Pair<>(locale.getLanguage(), locale.getCountry());
    }



}
