package com.one.education.utils.toast;

import android.content.Context;

import com.one.education.EducationAppliction;
import com.one.education.education.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author laiyongyang
 * @date 2020-05-27
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class DateUtilts {

    /**
     * 获取一段时间的列表
     */
    public static List<Long> getPeroidTime(int dateMaxCount) {
        List<Long> time = new ArrayList<>(); // 返回的集合
        int dayTag; // 天数标志
        long toDay; // 时间戳
        Calendar c = Calendar.getInstance(); // 当时的日期和时间
        int year = c.get(Calendar.YEAR); // 当前年份
        int month = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int day = c.get(Calendar.DAY_OF_MONTH);//当前的天数
        int maximum = c.getActualMaximum(Calendar.DAY_OF_MONTH);// 当月最大天数
        // 获取20的范围 要判断今天和当月最大天数
        for (int i = 0; i < dateMaxCount; i++) {
            dayTag = day + i;
            if (maximum - dayTag >= 0) { //当月还有lenght天
                c.set(Calendar.DAY_OF_MONTH, dayTag);
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                toDay = c.getTime().getTime();
            } else { //当月没有lenght天
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month - 1);// 设置月份 防止时间错乱
                c.set(Calendar.DAY_OF_MONTH, day + i);
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                toDay = c.getTime().getTime();
            }

            time.add(toDay);
        }

        return time;
    }

    /**
     * 根据当前日期获得是星期几
     * time=yyyy-MM-dd
     *
     */
    public static String getWeek(Context mCtx,long time) {
        String Week = "";
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(time));
        int wek = c.get(Calendar.DAY_OF_WEEK);
        if (wek == 1) {
            Week += mCtx.getString(R.string.sun);
        }
        if (wek == 2) {
            Week += mCtx.getString(R.string.mon);;
        }
        if (wek == 3) {
            Week += mCtx.getString(R.string.tue);;
        }
        if (wek == 4) {
            Week += mCtx.getString(R.string.wed);;
        }
        if (wek == 5) {
            Week += mCtx.getString(R.string.thu);;
        }
        if (wek == 6) {
            Week += mCtx.getString(R.string.fri);;
        }
        if (wek == 7) {
            Week += mCtx.getString(R.string.sat);;
        }
        return Week;
    }
}
