package com.example.namlibrary.util.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.fragment.app.Fragment;

import com.example.namlibrary.util.MultiThread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HelperUrl {
    public static void openUrl(Context context, String url) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    public static void openUrl(Fragment fragment, String url) {
        fragment.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    public static void loadContentFromUrl(Activity activity, String url, MultiThread.ITFResult<ArrayList<String>> itfResult) {
        MultiThread.execute(activity, () -> {
            ArrayList<String> list = new ArrayList<>();
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setConnectTimeout(60000);
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String str;
                while ((str = in.readLine()) != null) {
                    list.add(str);
                }
                in.close();
            } catch (Exception ignored) {
            }
            return list;
        }, new MultiThread.ITFResult<ArrayList<String>>() {
            @Override
            public void onSuccess(ArrayList<String> result) {
                itfResult.onSuccess(result);
            }

            @Override
            public void onFailed(Exception e) {
                itfResult.onFailed(e);
            }
        });
    }
}
