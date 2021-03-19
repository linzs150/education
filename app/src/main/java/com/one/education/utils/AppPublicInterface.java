package com.one.education.utils;

import android.content.Context;

import com.one.education.language.ConstantGlobal;
import com.one.education.language.SpUtil;
import com.one.education.retrofit.model.GetArticleListRsp;
import com.one.mylibrary.MyPublicInterface;

import java.util.ArrayList;

import io.github.prototypez.appjoint.core.ServiceProvider;

@ServiceProvider
public class AppPublicInterface implements MyPublicInterface {

    @Override
    public boolean isSimpleChinese() {

        String language = SpUtil.getString(ConstantGlobal.LOCALE_LANGUAGE);
        String country = SpUtil.getString(ConstantGlobal.LOCALE_COUNTRY);
        if (language.equals("en")) {
            return false;
        } else if (language.equals("zh")) {
            if (country.equals("HK")) {
                return false;
            }
        }

        return true;
    }
}
