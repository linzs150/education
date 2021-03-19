package com.one.education.language;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SPUtils {

    private static String KEY_APP_LANGUAGE = "key_app_language";

    public static void setLanguageLocal(Context context, String language){
        SharedPreferences preferences;
        SharedPreferences.Editor editor;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putString(KEY_APP_LANGUAGE, language);
        editor.commit();
    }

    public static String getLanguageLocal(Context context){
        SharedPreferences preferences;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String language = preferences.getString(KEY_APP_LANGUAGE, "");
        return language;
    }
}
