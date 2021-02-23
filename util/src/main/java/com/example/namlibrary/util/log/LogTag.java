package com.example.namlibrary.util.log;

import android.util.Log;

import java.util.ArrayList;

public class LogTag {
    public static String TAG_DEBUG = "zzz";
    public static String TAG_404 = "404 Not Found";

    public static void loglog(String message) {
        Log.d(TAG_DEBUG, message);
    }

    public static void loglog(int n) {
        loglog(String.valueOf(n));
    }

    public static void loglog(ArrayList<?> list) {
        for (Object o : list) loglog(o.toString());
    }
}
