package com.example.date_app.activity.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.example.date_app.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Config {
    static AlertDialog dialog = null;

    public static void showdialog(Context context) {
        dialog = new MaterialAlertDialogBuilder(context)
                .setView(LayoutInflater.from(context).inflate(R.layout.loading_layout, null))
                .setCancelable(false)
                .create();

        dialog.show();
    }

    public static void hideDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
