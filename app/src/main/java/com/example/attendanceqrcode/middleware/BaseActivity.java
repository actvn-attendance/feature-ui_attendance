package com.example.attendanceqrcode.middleware;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.attendanceqrcode.DangNhapActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.PropertyPermission;

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public final void onUnauthorizedEvent(UnauthorizedEvent e) {
        handleUnauthorizedEvent();
    }

    protected void handleUnauthorizedEvent() {
        new Handler(Looper.getMainLooper()).post(() -> {
//            System.out.println(">>> TIMEOUT");

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    BaseActivity.this);
            alertDialog.setCancelable(false);
            alertDialog.setTitle("Nhắc nhở");
            alertDialog
                    .setMessage("Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại.");
            alertDialog.setNegativeButton("OK",
                    (dialog, which) -> {
                        Intent intent = new Intent(BaseActivity.this,
                                DangNhapActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        dialog.cancel();
                    });
            try {
                alertDialog.show();
            } catch (Exception e) {
                Log.e("BASE ACTIVIY", e.toString());
            }
        });

    }
}