package com.example.attendanceqrcode;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendanceqrcode.api.ApiService;
import com.example.attendanceqrcode.middleware.BaseActivity;
import com.example.attendanceqrcode.modelapi.InfoUser;
import com.example.attendanceqrcode.modelapi.User;
import com.example.attendanceqrcode.services.AppFirebaseService;
import com.example.attendanceqrcode.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangNhapActivity extends BaseActivity implements View.OnClickListener {
    EditText edtUsername;
    EditText edtPassword;
    TextView txtForgetPass;
    Button btnLogin;
    ProgressBar progressBar;
    private long backPressdTime;
    Toast toast;

    @Override
    protected void handleUnauthorizedEvent() {
        //Don't handle unauthorized event
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        txtForgetPass = findViewById(R.id.txt_forgetpass);
        btnLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.processbar);

        txtForgetPass.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_forgetpass:
                Intent iForget = new Intent(DangNhapActivity.this, FogetPasswordActivity.class);
                startActivity(iForget);
                break;

            case R.id.btn_login:
                sendPosts();
                break;
        }
    }

    private void sendPosts() {
        progressBar.setVisibility(View.VISIBLE);

        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(DangNhapActivity.this, "Please enter Username or Password", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();

                        User user = new User(username, password, token);
                        ApiService.apiService.sendAccount(user).enqueue(new Callback<InfoUser>() {
                            @Override
                            public void onResponse(Call<InfoUser> call, Response<InfoUser> response) {
                                InfoUser postResult = response.body();

                                if (postResult != null) {
                                    Log.d("kiemtra1", postResult.toString() + "");
                                    SharedPreferences sharedPreferences = getSharedPreferences("Account", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("token", postResult.getAccess_token());
                                    editor.putString("username", username);
                                    editor.putString("password", password);
                                    editor.putInt("uid", postResult.getAccount().getAccount_id());
                                    editor.putString("fullName", postResult.getAccount().getFull_name());
                                    editor.putString("account", postResult.getAccount().toJsonString());

                                    editor.commit();

                                    Intent iHome = new Intent(DangNhapActivity.this, MainActivity.class);
                                    iHome.putExtra("student",postResult.getAccount());
                                    startActivity(iHome);
                                    finish();
                                    progressBar.setVisibility(View.GONE);
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(DangNhapActivity.this, "Account or password incorrect", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<InfoUser> call, Throwable t) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(DangNhapActivity.this, "Account or password incorrect", Toast.LENGTH_SHORT).show();
                            }
                        });
                    });
        }

    }

    @Override
    public void onBackPressed() {
        if (backPressdTime+2000> System.currentTimeMillis())
        {
            toast.cancel();//thoat thi huy toast
            super.onBackPressed();
            return;


        }else {
            toast = Toast.makeText(this,"Press back again to exit the app",Toast.LENGTH_SHORT);
            toast.show();
        }

        backPressdTime = System.currentTimeMillis();
    }
}