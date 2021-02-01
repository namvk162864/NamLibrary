package com.example.namlibrary.util.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class HelperDialog {
    public interface ITFActionDialogButton {
        void actionPositive(DialogInterface dialog, int which);

        void actionNegative(DialogInterface dialog, int which);
    }

    public static void showDialogConfirm(Context context, String title, String message, boolean isCancelable,
                                         String txtPositive, String txtNegative,
                                         ITFActionDialogButton itfActionDialogButton) {
        new AlertDialog.Builder(context).setTitle(title).setMessage(message).setCancelable(isCancelable)
                .setPositiveButton(txtPositive, itfActionDialogButton::actionPositive)
                .setNegativeButton(txtNegative, itfActionDialogButton::actionNegative)
                .create().show();
    }
}
