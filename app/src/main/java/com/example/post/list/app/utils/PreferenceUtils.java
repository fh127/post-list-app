package com.example.post.list.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class PreferenceUtils {
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    @Inject
    public PreferenceUtils(Context context) {
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedpreferences.edit();
    }

    public String getStringValue(String key) {
        return sharedpreferences.getString(key, null);
    }

    public int getIntValue(String key) {
        return sharedpreferences.getInt(key, -1);
    }

    public void removeKey(String key) {
        editor.remove(key).apply();
    }

    public void setStringValue(String key, String value) {
        editor.putString(key, value).apply();
    }

    public void setIntValue(String key, int value) {
        editor.putInt(key, value).apply();
    }
}
