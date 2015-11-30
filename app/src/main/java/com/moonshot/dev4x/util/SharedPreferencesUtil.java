package com.moonshot.dev4x.util;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.moonshot.dev4x.Dev4xApp;

/**
 * Created by hirendave on 8/14/15.
 */
public class SharedPreferencesUtil {

    public void savePreferences(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(Dev4xApp.getAppContext());
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getPreferenceValue(String key){
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(Dev4xApp.getAppContext());
        return sharedPreferences.getString(key, null);
    }

    public void removePreferenceValue(String key){
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(Dev4xApp.getAppContext());
        Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
