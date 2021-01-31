package com.example.namlibrary.util.helper;

import android.os.Build;

import com.example.namlibrary.util.log.LogTag;

import java.util.ArrayList;
import java.util.Arrays;

public class HelperString {
    public static String join(ArrayList<String> list, String sep) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return String.join(sep, list);
        }
        return LogTag.TAG_404;
    }

    public static ArrayList<String> split(String s, String sep) {
        if (s.length() == 0) return new ArrayList<>();
        return new ArrayList<>(Arrays.asList(s.split(sep)));
    }

    public static ArrayList<String> substring(String string, String sub_start, String sub_end) {
        ArrayList<String> list = new ArrayList<>();
        while (true) {
            try {
                string = string.substring(string.indexOf(sub_start) + sub_start.length());
                list.add(string.substring(0, string.indexOf(sub_end)));
                string = string.substring(string.indexOf(sub_end) + sub_end.length());
            } catch (Exception e) {
                break;
            }
        }
        return list;
    }
}
