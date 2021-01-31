package com.example.namlibrary.util.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.namlibrary.util.R;
import com.example.namlibrary.util.log.LogTag;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class HelperImage {
    public static void showImage(String im, ImageView imageView) {
        Picasso.get().load(im).into(imageView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                LogTag.loglog("showImage\t" + e.toString());
            }
        });
    }

    /**
     * @param im: đường link trực tiếp tới ảnh
     * @param k:  hệ số phóng đại ảnh, default = 3
     */
    public static void zoomImage(Context context, ImageView imageView, String im, int k) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.layout_dialog_zoom_image, null);
        PhotoView imZoom = mView.findViewById(R.id.imZoom);
        Picasso.get().load(im).resize(imageView.getWidth() * k, imageView.getHeight() * k).into(imZoom);
        mBuilder.setView(mView);
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    public static void zoomImage(Context context, ImageView imageView, String im) {
        zoomImage(context, imageView, im, 3);
    }

    public static void setImage(ImageView imageView, boolean is, int drawableTrue, int drawableFalse) {
        imageView.setImageResource(is ? drawableTrue : drawableFalse);
    }
}
