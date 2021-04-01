package com.one.education.commons;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.one.education.EducationAppliction;
import com.one.education.education.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 * @创建者 Administrator
 * @创建时间 2020/4/24 22:20
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class AppUtils {

    /**
     * 用户判断多次点击的时间
     */
    private static long sLastClickTime;

    public static boolean isFastDoubleClick(long fastTime) {
        long time = System.currentTimeMillis();
        if (Math.abs(time - sLastClickTime) < fastTime) {
            return true;
        }
        sLastClickTime = time;
        return false;
    }

    public static String getJson(Context context,String json) {

        StringBuilder builder = new StringBuilder();
        InputStream is = null;
        try {
            is = context.getAssets().open(json);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String next = "";
            while (null != (next = bufferedReader.readLine())) {
                builder.append(next);
            }

        } catch (Exception e) {
            e.printStackTrace();
            builder.delete(0, builder.length());
        }

        return builder.toString().trim();
    }

    /**
     * 获取版本名
     *
     * @throws PackageManager.NameNotFoundException
     */
    public static String getVersionName(Context context) {
        PackageInfo pkg = null;
        try {
            pkg = context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pkg.versionName;
    }

    /**
     * 获取版本号
     *
     * @throws PackageManager.NameNotFoundException
     */
    public static int getVersionCode(Context context) {
        PackageInfo pkg = null;
        try {
            pkg = context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pkg.versionCode;
    }

    /**
     * 获取应用UUID
     *
     * @param context
     * @return
     */
    public synchronized static String getUUID(Context context) {
        File installation = new File(context.getFilesDir(), "INSTALLATION");
        try {
            if (!installation.exists()) {
                FileOutputStream out = new FileOutputStream(installation);
                String id = UUID.randomUUID().toString();
                out.write(id.getBytes());
                out.close();
            }
            RandomAccessFile f = new RandomAccessFile(installation, "r");
            byte[] bytes = new byte[(int) f.length()];
            f.readFully(bytes);
            f.close();

            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取android_id
     *
     * @param context
     * @return
     */
    public static String getAndroidId(Context context) {
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (android_id != null)
            return android_id;
        return "";
    }

    /**
     * 获取系统API版本
     *
     * @return
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
        //		return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取IMEI
     *
     * @param context
     * @return
     */
    public static String getTelImei(Context context) {
        String deviceId = "";
        if (!XXPermissions.isHasPermission(context, Permission.READ_PHONE_STATE)) {
            deviceId = Settings.System.getString(
                    context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = telephonyManager.getDeviceId();
            //android 10以上已经获取不了imei了 用 android id代替
            if(TextUtils.isEmpty(deviceId)){
                deviceId = Settings.System.getString(
                        context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }

        return deviceId;
    }

    /**
     * 获取IMSI
     *
     * @param context
     * @return
     */
    public static String getTelImSi(Context context) {
        if (!XXPermissions.isHasPermission(context, Permission.READ_PHONE_STATE)) {
            return "";
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSubscriberId();
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getTelModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 设备的user agent
     *
     * @return
     */
    public static String getUA() {
        try {
            return getTelModel();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取网络类型名称
     *
     * @return
     */
    public static String getNetTypeName(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {//网络打开
            //			State gprs = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
            //		     if(gprs == State.CONNECTED || gprs == State.CONNECTING){
            //		     }
            //			 State wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            //		     if(wifi == State.CONNECTED || wifi == State.CONNECTING){
            //		     }

            if (networkInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS
                    || networkInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_CDMA
                    || networkInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE) {//2g
                return "2g";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {//wifi
                return "wifi";
            } else if (networkInfo.getSubtypeName().equals("LTE") && networkInfo.getSubtype() == 13) {
                return "4g";
            } else {//3G
                return "3g";
            }
        }
        return "";
    }

    /**
     */
    public static String getAPN(Context context) {
        try {
            return getNetTypeName(context);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getMacAddress(Context context) {
        if (context == null) {
            return "";
        }
        String localMac = null;
        if (isWifiAvailable(context)) {
            localMac = getWifiMacAddress(context);
        }
        if (localMac != null && localMac.length() > 0) {
            localMac = localMac.replace(":", "-").toLowerCase();
            return localMac;
        }
        localMac = getMacFromCallCmd();
        if (localMac != null) {
            localMac = localMac.replace(":", "-").toLowerCase();
        }
        return localMac;
    }


    public static String getWifiMacAddress(Context context) {
        String localMac = "";
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo connectionInfo = wifi.getConnectionInfo();
        if (wifi.isWifiEnabled()) {
            localMac = connectionInfo.getMacAddress();
            if (localMac != null) {
                localMac = localMac.replace(":", "-").toLowerCase();
                return localMac;
            }
        }
        return "";
    }

    /**
     * 通过callCmd("busybox ifconfig","HWaddr")获取mac地址
     *
     * @return Mac Address
     * @attention 需要设备装有busybox工具
     */
    private static String getMacFromCallCmd() {
        String result = "";
        result = callCmd("busybox ifconfig", "HWaddr");

        if (result == null || result.length() <= 0) {
            return null;
        }


        // 对该行数据进行解析
        // 例如：eth0 Link encap:Ethernet HWaddr 00:16:E8:3E:DF:67
        if (result.length() > 0 && result.contains("HWaddr") == true) {
            String Mac = result.substring(result.indexOf("HWaddr") + 6,
                    result.length() - 1);
            if (Mac.length() > 1) {
                result = Mac.replaceAll(" ", "");
            }
        }

        return result;
    }

    public static String callCmd(String cmd, String filter) {
        String result = "";
        String line = "";
        try {

            if (XXPermissions.isHasPermission(EducationAppliction.getInstance(), Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)) {
                Process proc = Runtime.getRuntime().exec(cmd);
                InputStreamReader is = new InputStreamReader(proc.getInputStream());
                BufferedReader br = new BufferedReader(is);

                // 执行命令cmd，只取结果中含有filter的这一行
                while ((line = br.readLine()) != null
                        && line.contains(filter) == false) {
                }

                result = line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 检查是否链接wifi
     *
     * @param context
     * @return 是否在wifi状态下
     */

    public static boolean isWifiAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }


    static public String getCpuString() {
        if (Build.CPU_ABI.equalsIgnoreCase("x86")) {
            return "Intel";
        }

        String strInfo = "";
        try {
            byte[] bs = new byte[1024];
            RandomAccessFile reader = new RandomAccessFile("/proc/cpuinfo", "r");
            reader.read(bs);
            String ret = new String(bs);
            int index = ret.indexOf(0);
            if (index != -1) {
                strInfo = ret.substring(0, index);
            } else {
                strInfo = ret;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return strInfo;
    }


    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取教师等级
     * @param context
     * @param level
     * @return
     */
    public static String getTeacherLevelText(Context context, int level) {
        switch (level) {
            default:
            case 0:
            case 1:
                return context.getString(R.string.standard);
            case 2:
                return context.getString(R.string.higher);
            case 3:
                return context.getString(R.string.super_);
        }
    }

}
