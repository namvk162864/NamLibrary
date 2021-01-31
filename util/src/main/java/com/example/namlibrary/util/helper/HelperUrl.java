package com.example.namlibrary.util.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.fragment.app.Fragment;

public class HelperUrl {
    public static void openUrl(Context context, String url) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    public static void openUrl(Fragment fragment, String url) {
        fragment.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }
}
