package com.example.attendanceqrcode;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar = findViewById(R.id.processbar_screen);
        AnimProcess();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (!checkSigined()) {
//                    Intent iHome = new Intent(SplashScreenActivity.this, HomeActivity.class);
//                    startActivity(iHome);
//                } else {
                    Intent iDangNhap = new Intent(SplashScreenActivity.this, DangNhapActivity.class);
                    startActivity(iDangNhap);
                    finish();
//                }
//                finish();
            }
        }, 3000);
    }

    private boolean checkSigined() {
        SharedPreferences sharedPreferences = getSharedPreferences("Account", Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString("token", "");
        if (token.isEmpty()) {
            return false;
        }
        return true;
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