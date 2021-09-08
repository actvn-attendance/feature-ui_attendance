package com.example.attendanceqrcode;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.style.FoldingCube;

//import com.example.appattendance.api.ApiService;
//import com.example.appattendance.modelapi.InfoUser;
//import com.example.appattendance.modelapi.User;
//import com.github.ybq.android.spinkit.style.FoldingCube;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;

public class DangNhapActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtUsername;
    EditText edtPassword;
    TextView txtForgetPass;
    Button btnLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        edtUsername  = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        txtForgetPass = findViewById(R.id.txt_forgetpass);
        btnLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.processbar);

        txtForgetPass.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        progressBar.setIndeterminateDrawable(new FoldingCube());
    }




    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.txt_forgetpass:
                Intent iForget = new Intent(DangNhapActivity.this,FogetPasswordActivity.class);
                startActivity(iForget);
                break;

            case R.id.btn_login:
//                sendPosts();
                Intent iHome = new Intent(DangNhapActivity.this,HomeActivity.class);
                startActivity(iHome);
                finish();
                break;
        }
    }

//    private void sendPosts()
//    {
//        progressBar.setVisibility(View.VISIBLE);
//
//        String username = edtUsername.getText().toString().trim();
//        String password = edtPassword.getText().toString().trim();
//
//        if (username.isEmpty()||password.isEmpty())
//        {
//            progressBar.setVisibility(View.GONE);
//            Toast.makeText(DangNhapActivity.this,"Please enter Username or Password",Toast.LENGTH_SHORT).show();
//        }
//        else {
//            User user = new User(username,password);
//            ApiService.apiService.sendAccount(user).enqueue(new Callback<InfoUser>() {
//                @Override
//                public void onResponse(Call<InfoUser> call, Response<InfoUser> response) {
//                    InfoUser postResult = response.body();
//
//                    if (postResult!= null)
//                    {
//                        Log.d("kiemtra1",postResult.toString()+"");
//                        SharedPreferences sharedPreferences = getSharedPreferences("Account", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("token", postResult.getAccessToken());
//                        editor.putString("username", username);
//                        editor.putString("password", password);
//                        editor.commit();
//
//                        Intent iHome = new Intent(DangNhapActivity.this,HomeActivity.class);
//                        startActivity(iHome);
//                        finish();
//                        progressBar.setVisibility(View.GONE);
//                    }else
//                    {
//                        progressBar.setVisibility(View.GONE);
//                        Toast.makeText(DangNhapActivity.this,"Account or password incorrect",Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<InfoUser> call, Throwable t) {
//                    progressBar.setVisibility(View.GONE);
//                    Toast.makeText(DangNhapActivity.this,"Account or password incorrect",Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//
//    }

}