package com.one.mylibrary;

import android.content.Context;
import android.text.TextUtils;

import java.util.Locale;

public interface MyPublicInterface {
    ConstantGlobal.LanguageType getLanguageType();

    void changeLanguage();

}
