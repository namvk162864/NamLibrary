package com.example.namlibrary.util.helper;

public class HelperInteger {
    public static String multiStringInt(String s, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append(s);
        return sb.toString();
    }

    public static String cvtInt2String(int length, int n) {
        String s = String.valueOf(n);
        return multiStringInt("0", length - s.length()) + s;
    }
}
