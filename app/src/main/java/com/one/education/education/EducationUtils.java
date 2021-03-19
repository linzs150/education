package com.one.education.education;

import android.content.Context;

import com.one.education.EducationAppliction;

/**
 * @author laiyongyang
 * @date 2020-05-07
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class EducationUtils {

    /**
     * 获取学历
     *
     * @param education 学历
     * @return str
     */
    public static String getEducationStr(int education) {
        switch (education) {
            case 1:
                return EducationAppliction.getInstance().getString(R.string.high_school);
            case 2:
                return EducationAppliction.getInstance().getString(R.string.bachelor);
            case 3:
                return EducationAppliction.getInstance().getString(R.string.master_degree);
            case 4:
                return EducationAppliction.getInstance().getString(R.string.phd);
            case 5:
                return EducationAppliction.getInstance().getString(R.string.postdoctor);
            case 6:
                return EducationAppliction.getInstance().getString(R.string.phd_teacher);
                default:
                    return "";
        }
    }
}
