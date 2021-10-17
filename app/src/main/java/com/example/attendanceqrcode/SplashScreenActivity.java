package com.example.attendanceqrcode;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.attendanceqrcode.modelapi.Account;
import com.example.attendanceqrcode.utils.Utils;

public class SplashScreenActivity extends AppCompatActivity {
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar = findViewById(R.id.processbar_screen);
        AnimProcess();

        new Handler().postDelayed(() -> {
            Account account = Utils.getLocalAccount(SplashScreenActivity.this);

            if (account != null) {
                Intent iHome = new Intent(SplashScreenActivity.this, MainActivity.class);
                iHome.putExtra("student", account);
                startActivity(iHome);
            } else {
                Intent iDangNhap = new Intent(SplashScreenActivity.this, DangNhapActivity.class);
                startActivity(iDangNhap);
                finish();
            }
            finish();
        }, 3000);
    }

    private void AnimProcess() {
        //Do any action here. Now we are moving to next page
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            // will update the "progress" propriety of seekbar until it reaches progress
            ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 100);
            animation.setDuration(3000); // 0.5 second
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
        }
    }


}