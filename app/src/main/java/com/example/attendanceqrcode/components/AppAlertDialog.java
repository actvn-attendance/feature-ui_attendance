package com.example.attendanceqrcode.components;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;


import com.example.attendanceqrcode.DangNhapActivity;
import com.example.attendanceqrcode.utils.Utils;

import java.util.concurrent.Callable;

public class AppAlertDialog {
    public static void showYesNoDialog(Context context, String title,
                                       String message, int icon, Callable<Void> callback) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            callback.call();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(icon)
                .show();
    }

    public static void showOnlyAlertDialog(Context context, String title,
                                       String message, int icon) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(android.R.string.ok, null)
                .setIcon(icon)
                .show();
    }

    public static void showTokenTimeOutDialog(Activity context) {
        new AlertDialog.Builder(context)
                .setTitle("HẾT THỜI GIAN ĐĂNG NHẬP")
                .setMessage("Vui lòng đăng nhập lại")
                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout(context);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    private static  void logout(Activity activity) {
        Utils.logOut(activity);
        activity.startActivity(new Intent(activity, DangNhapActivity.class));
        activity.finish();
    }

}
