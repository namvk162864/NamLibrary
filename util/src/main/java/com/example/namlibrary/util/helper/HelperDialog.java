package com.example.namlibrary.util.helper;

import android.app.AlertDialog;
import android.content.Context;

public class HelperDialog {
    public interface ITFActionDialogButton {
        void actionPositive();

        void actionNegative();
    }

    public static void showDialogConfirm(Context context, String message, boolean isCancelable,
                                         String txtPositive, String txtNegative,
                                         ITFActionDialogButton itfActionDialogButton) {
        new AlertDialog.Builder(context).setMessage(message).setCancelable(isCancelable)
                .setPositiveButton(txtPositive, (dialog, which) -> itfActionDialogButton.actionPositive())
                .setNegativeButton(txtNegative, (dialog, which) -> itfActionDialogButton.actionNegative())
                .create().show();
    }
}
