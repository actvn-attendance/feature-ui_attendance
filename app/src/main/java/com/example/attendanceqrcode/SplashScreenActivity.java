package com.example.attendanceqrcode;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.attendanceqrcode.modelapi.Account;
import com.example.attendanceqrcode.utils.SharedPreferenceHelper;
import com.example.attendanceqrcode.utils.Utils;
import com.example.attendanceqrcode.utils.diffie_hellman.DiffieHellman;
import com.example.attendanceqrcode.utils.secure.SecureServices;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

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
        // will update the "progress" propriety of seekbar until it reaches progress
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 100);
        animation.setDuration(3000); // 0.5 second
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }


}