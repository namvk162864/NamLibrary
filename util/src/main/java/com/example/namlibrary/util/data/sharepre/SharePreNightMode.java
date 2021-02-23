package com.example.namlibrary.util.data.sharepre;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.namlibrary.util.Constant;

public class SharePreNightMode {
    private final SharedPreferences sharePreNightMode;
    private final String KEY_NIGHT_MODE = "KEY_NIGHT_MODE";

    public SharePreNightMode(Context context) {
        sharePreNightMode = context.getSharedPreferences(Constant.defaultNameSharePreNightMode, Context.MODE_PRIVATE);
    }

    public void setNightMode(int isNightMode) {
        SharedPreferences.Editor editor = sharePreNightMode.edit();
        editor.putInt(KEY_NIGHT_MODE, isNightMode);
        editor.apply();
    }

    public int getNightMode() {
        return sharePreNightMode.getInt(KEY_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_NO);
    }
}
