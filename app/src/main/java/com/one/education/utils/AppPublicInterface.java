package com.one.education.utils;

import android.text.TextUtils;

import com.one.education.EducationAppliction;
import com.one.education.language.MultiLanguageUtil;
import com.one.education.language.SpUtil;
import com.one.mylibrary.ConstantGlobal;
import com.one.mylibrary.MyPublicInterface;

import java.util.Locale;

import io.github.prototypez.appjoint.core.ServiceProvider;

@ServiceProvider
public class AppPublicInterface implements MyPublicInterface {

    @Override
    public ConstantGlobal.LanguageType getLanguageType() {
        String language = SpUtil.getString(ConstantGlobal.LOCALE_LANGUAGE);
        String country = SpUtil.getString(ConstantGlobal.LOCALE_COUNTRY);
        if (language.equals("en")) {
            return ConstantGlobal.LanguageType.EN;
        } else if (language.equals("zh")) {
            if (country.equals("HK")) {
                return ConstantGlobal.LanguageType.HK;
            }
        }

        return ConstantGlobal.LanguageType.CHINESE;
    }

    @Override
    public void changeLanguage() {
        String spLanguage = SpUtil.getString(EducationAppliction.getInstance(), ConstantGlobal.LOCALE_LANGUAGE);
        String spCountry = SpUtil.getString(EducationAppliction.getInstance(), ConstantGlobal.LOCALE_COUNTRY);

        if (!TextUtils.isEmpty(spLanguage) && !TextUtils.isEmpty(spCountry)) {
            // 如果有一个不同
            if (!MultiLanguageUtil.isSameWithSetting(EducationAppliction.getInstance())) {
                Locale locale = new Locale(spLanguage, spCountry);
                MultiLanguageUtil.changeAppLanguage(EducationAppliction.getInstance(), locale, false);
            }
        } else {
            Locale locale = Locale.getDefault();
            MultiLanguageUtil.saveLanguageSetting(EducationAppliction.getInstance(), locale);
            MultiLanguageUtil.changeAppLanguage(EducationAppliction.getInstance(), locale, true);
        }
    }}
