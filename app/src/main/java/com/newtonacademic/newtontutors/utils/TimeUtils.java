package com.newtonacademic.newtontutors.utils;

import android.content.Context;
import android.text.TextUtils;

import com.newtonacademic.newtontutors.EducationAppliction;
import com.newtonacademic.newtontutors.language.SpUtil;
import com.newtonacademic.mylibrary.ConstantGlobal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Copyright (c) 2015, Intretech All rights reserved.
 *
 * @author laiyongyang
 * @version v1.0
 * @Description
 * @email fzlaiyy@intretech.com
 * @date 2015年6月8日
 */
public class TimeUtils {
    private static final String TAG = "TimeUtils";

    public static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_TIME_FORMA2 = "yyyy-MM-dd HH:mm";
    public static final String DEFAULT_TIME_FORMA3 = "yyyy-MM-dd";
    public static final String DEFAULT_TIME_FORMAT_CN = "yyyy年MM月dd日 HH:mm:ss";

    public static String GetCurrentTime() {
        return GetCurrentTime(DEFAULT_TIME_FORMAT);
    }


    public static String GetCurrentTime(String format) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(new Date(System.currentTimeMillis()));
    }

    public static String GetTimeFormat(long time, String format) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(time);
    }

    public static String GetTime(long time, String format) {
        String sdf = "";

        String spLanguage = SpUtil.getString(EducationAppliction.getContext(), ConstantGlobal.LOCALE_LANGUAGE);
        String spCountry = SpUtil.getString(EducationAppliction.getContext(), ConstantGlobal.LOCALE_COUNTRY);
        if (TextUtils.isEmpty(spLanguage) && TextUtils.isEmpty(spCountry)) {
        } else {
            if (spLanguage.equals("zh")) {

                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(time));

            } else if (spLanguage.equals("en")) {
                sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date(time));
            }
        }

//        if (EducationAppliction.getInstance().getResources().getConfiguration().locale.getCountry().equals("UK") ||
//                EducationAppliction.getInstance().getResources().getConfiguration().locale.getCountry().equals("US")) {
//            sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm",Locale.getDefault()).format(new Date(time));
//        } else {
//            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date(time));
//        }

//        return new SimpleDateFormat(format, Locale.getDefault()).format(new Date(time));
        return sdf;
    }


    public static String GetSpeciaTime(long time, String format) {
        String sdf = "";

        String spLanguage = SpUtil.getString(EducationAppliction.getContext(), ConstantGlobal.LOCALE_LANGUAGE);
        String spCountry = SpUtil.getString(EducationAppliction.getContext(), ConstantGlobal.LOCALE_COUNTRY);
        if (TextUtils.isEmpty(spLanguage) && TextUtils.isEmpty(spCountry)) {
        } else {
            if (spLanguage.equals("zh")) {

                sdf = new SimpleDateFormat("yyyy-MM-dd").format(new Date(time));

            } else if (spLanguage.equals("en")) {
                sdf = new SimpleDateFormat("dd/MM/yyyy").format(new Date(time));
            }
        }

//        if (EducationAppliction.getInstance().getResources().getConfiguration().locale.getCountry().equals("UK") ||
//                EducationAppliction.getInstance().getResources().getConfiguration().locale.getCountry().equals("US")) {
//            sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm",Locale.getDefault()).format(new Date(time));
//        } else {
//            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date(time));
//        }

//        return new SimpleDateFormat(format, Locale.getDefault()).format(new Date(time));
        return sdf;
    }

    public static String GetDefaultTime(long time) {
        return new SimpleDateFormat(DEFAULT_TIME_FORMAT, Locale.getDefault()).format(new Date(time));
    }

    public static String GetDefaultTimeCN(long time) {
        return new SimpleDateFormat(DEFAULT_TIME_FORMAT_CN, Locale.getDefault()).format(new Date(time));
    }

    /*
     * 通过时间格式获取对应的毫秒数 eg:"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"
     */
    public static long GetTimestamp(String time, String format) {
        try {
            return new SimpleDateFormat(format, Locale.getDefault()).parse(time).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /*
     * 通过时间格式获取对应的毫秒数 eg:"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "HH:mm:ss"
     */
    public static long GetTimestamp(String time) {
        return GetTimestamp(time, DEFAULT_TIME_FORMAT);
    }

    public static String formatDate(Date date, String format) {
        if (date == null) {
            return null;
        }

        return new SimpleDateFormat(format, Locale.getDefault()).format(date);
    }

    public static int getMonthDayNum(int year, int month) {
        int number = 0;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12: {
                number = 31;
            }
            break;
            case 2: {
                if (isLeapYear(year)) {
                    number = 29;
                } else {
                    number = 28;
                }
                ;
            }
            break;
            case 4:
            case 6:
            case 9:
            case 11: {
                number = 30;
            }
            break;
            default:
                break;
        }
        return number;
    }

    private static boolean isLeapYear(int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    public static Date customFormatTime(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        calendar.set(year, month - 1, day, hour, minute, second);
        return calendar.getTime();
    }


    // 增加或减少天数
    public static Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }

    // 增加或减少年数
    public static Date addYear(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.YEAR, num);
        return startDT.getTime();
    }

    // 增加或减少月数
    public static Date addMonth(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }

    public static String getDateToWeek(Context context, Date date) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        return context.getString(Week.getTextResource(Week.OsWeekToWeek(startDT.get(Calendar.DAY_OF_WEEK))));
    }

    /**
     * 检验时间格式是否正确
     *
     * @param context
     * @param time
     * @return
     */
    public static String getWeek(Context context, long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        return context.getString(Week.getTextResource(c.get(Calendar.DAY_OF_WEEK)));
    }

    /**
     * 检验时间格式是否正确
     *
     * @param time
     * @param timeFormat
     */
    public static boolean validateTimeFormat(String time, String timeFormat) {
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat(timeFormat, Locale.getDefault());
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(time);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }

        return true;
    }

    public static Date getDate(String time, String format) {
        SimpleDateFormat timeFormat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            return timeFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取今天周几
     *
     * @return 1是星期日、2是星期一、3是星期二、4是星期三、5是星期四、6是星期五、7是星期六
     */
    public static int getCurrentDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis()));
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static boolean isSameDate(Calendar firstDate, Calendar secondDate) {
        return firstDate.get(Calendar.DAY_OF_YEAR) == secondDate.get(Calendar.DAY_OF_YEAR)
                && firstDate.get(Calendar.DAY_OF_MONTH) == secondDate.get(Calendar.DAY_OF_MONTH)
                && firstDate.get(Calendar.YEAR) == secondDate.get(Calendar.YEAR);
    }

    /**
     * 本地时间转换为UTC时间
     *
     * @param localTime 本地时间
     */
    public static long convertLocalTimeToUTC(long localTime) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(DEFAULT_TIME_FORMAT, Locale.getDefault());
            Date date = df.parse(TimeUtils.GetTime(localTime, DEFAULT_TIME_FORMAT));
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0L;
    }

    /**
     * 本地时间转换为UTC时间
     *
     * @param utcTime UTC时间戳，到毫秒
     */
    public static String formatUtcToLocalTime(long utcTime, String localFormat) {
        Date date = new Date(utcTime);
        return formatDate(date, localFormat);
    }

    /**
     * 格式化时间
     *
     * @param duration 时长
     * @return 00:00:00
     */
    public static String formatDuration(long duration) {
        int hour = (int) (duration / 3600000);
        long minute = (duration % 3600000) / 60000;//几分
        long second = (duration % 60000) / 1000;//几秒
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, minute, second);
    }
}
