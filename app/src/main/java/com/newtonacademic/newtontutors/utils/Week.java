package com.newtonacademic.newtontutors.utils;


import com.newtonacademic.newtontutors.R;

public enum Week {
    /**
     * 星期一
     */
    MONDAY,

    /**
     * 星期二
     */
    TUESDAY,

    /**
     * 星期三
     */
    WEDNESDAY,

    /**
     * 星期四
     */
    THURSDAY,

    /**
     * 星期五
     */
    FRIDAY,

    /**
     * 星期六
     */
    SATURDAY,

    /**
     * 星期天
     */
    SUNDAY,
    ;

    public static int getTextResource(Week week) {
        switch (week) {
            case MONDAY:
                return R.string.monday;
            case TUESDAY:
                return R.string.tuesday;
            case WEDNESDAY:
                return R.string.wednesday;
            case THURSDAY:
                return R.string.thursday;
            case FRIDAY:
                return R.string.friday;
            case SATURDAY:
                return R.string.saturday;
            case SUNDAY:
                return R.string.sunday;
        }

        return 0;
    }

    public static int getTextResource2(Week week) {
        switch (week) {
            case MONDAY:
                return R.string.mon;
            case TUESDAY:
                return R.string.tue;
            case WEDNESDAY:
                return R.string.wed;
            case THURSDAY:
                return R.string.thu;
            case FRIDAY:
                return R.string.fri;
            case SATURDAY:
                return R.string.sat;
            case SUNDAY:
                return R.string.sun;
        }

        return 0;
    }

    /**
     * 获取周末字符串
     */
    public static int getWeekendString() {
        return R.string.weekend;
    }

    public static int getTextResource(int osWeek) {
        switch (osWeek) {
            case 2:
                return R.string.mon;
            case 3:
                return R.string.tue;
            case 4:
                return R.string.wed;
            case 5:
                return R.string.thu;
            case 6:
                return R.string.fri;
            case 7:
                return R.string.sat;
            case 1:
                return R.string.sun;
        }

        return 0;
    }

    /**
     * 操作系统的周模式转换成当前周模式
     *
     * @param os_week
     * @return
     */
    public static Week OsWeekToWeek(int os_week) {
        switch (os_week) {
            case 1:
                return Week.SUNDAY;
            case 2:
                return Week.MONDAY;
            case 3:
                return Week.TUESDAY;
            case 4:
                return Week.WEDNESDAY;
            case 5:
                return Week.THURSDAY;
            case 6:
                return Week.FRIDAY;
            case 7:
                return Week.SATURDAY;
            default:
                break;
        }

        return null;
    }

    /**
     * 周数组转int数组
     */
    public static int[] weeksToIntArray(Week[] weeks) {
        if (null == weeks) {
            return null;
        }

        int[] intWeeks = new int[weeks.length];
        int index = 0;
        for (Week select : weeks) {
            intWeeks[index++] = select.ordinal();
        }
        return intWeeks;
    }

    /**
     * int数组转周数组
     */
    public static Week[] intArrayToWeeks(int[] weeks) {
        if (null == weeks) {
            return null;
        }

        Week[] result = new Week[weeks.length];
        Week[] allValues = Week.values();
        for (int i = 0; i < weeks.length; i++) {
            result[i] = allValues[weeks[i]];
        }

        return result;
    }

}
