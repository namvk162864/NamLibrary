package com.example.namlibrary.util.data.sharepre;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreNightMode {
    private final SharedPreferences sharePreNightMode;
    private final String KEY_NIGHT_MODE = "KEY_NIGHT_MODE";

    public SharePreNightMode(Context context, String nameSharePreNightMode) {
        sharePreNightMode = context.getSharedPreferences(nameSharePreNightMode, Context.MODE_PRIVATE);
    }

    public void setNightMode(int isNightMode) {
        SharedPreferences.Editor editor = sharePreNightMode.edit();
        editor.putInt(KEY_NIGHT_MODE, isNightMode);
        editor.apply();
    }

    public int getNightMode() {
        return sharePreNightMode.getInt(KEY_NIGHT_MODE, 1);
    }
}
