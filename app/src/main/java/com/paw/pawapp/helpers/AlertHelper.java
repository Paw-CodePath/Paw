package com.paw.pawapp.helpers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class AlertHelper {
    private String title;
    private String message;

    public static void present(@NonNull Context context, String title, String message) {
        AlertHelper alert = new AlertHelper(title, message);
        alert.show(context);
    }

    public static void present(@NonNull Context context, String title, Exception e) {
        present(context, title, e.getLocalizedMessage());
    }

    private AlertHelper(String title, String message) {
        this.title = title;
        this.message = message;
    }

    private void show(@NonNull Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialog, id) -> {
            // Nothing to do here; alert closes automatically when button is tapped
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
