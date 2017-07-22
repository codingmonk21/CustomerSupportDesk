package com.dev21.customersupportdesk.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Prajwal on 07/07/17.
 */

public class SharedPreferenceHelper {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferenceHelper(Context context, String preferenceName) {
        sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public SharedPreferences getSharedPreference() {
        return sharedPreferences;
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }
}
