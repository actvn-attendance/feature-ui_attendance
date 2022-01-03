package com.example.attendanceqrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendanceqrcode.api.ApiService;
import com.example.attendanceqrcode.middleware.BaseActivity;
import com.example.attendanceqrcode.modelapi.InfoUser;
import com.example.attendanceqrcode.modelapi.User;
import com.example.attendanceqrcode.utils.SharedPreferenceHelper;
import com.example.attendanceqrcode.utils.secure.SecureServices;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
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
                                    /// Sign in is successfully
                                    /// -> if(api >= 18) Create Android KeyStore
                                    /// -> create master key: secret key and RSA key of Android Keystore
                                    /// note: if(api < 18) -> use generate aes key by user password
                                    /// -> if(api >= 23) use Android Keystore to generate/save/get secret key
                                    /// -> (18<= api < 23) -> wrap secret key by public key and unwrap by private key
                                    /// -> api < 18 -> save secret key to local storage
                                    try {
                                        new SecureServices(getApplicationContext()).createMasterKey(postResult.getAccount().getPassword());

                                        SharedPreferenceHelper.encryptAndSetData(SharedPreferenceHelper.tokenKey, postResult.getAccess_token());
                                        SharedPreferenceHelper.encryptAndSetData(SharedPreferenceHelper.fullNameKey, postResult.getAccount().getFull_name());
                                        SharedPreferenceHelper.encryptAndSetData(SharedPreferenceHelper.accountKey, postResult.getAccount().toJsonString());

                                        SharedPreferenceHelper.set(SharedPreferenceHelper.uidKey, String.valueOf(postResult.getAccount().getAccount_id()));
                                    } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException
                                            | NoSuchProviderException | InvalidKeyException | IllegalBlockSizeException
                                            | InvalidKeySpecException | CertificateException | KeyStoreException
                                            | IOException | NoSuchPaddingException e) {
                                        e.printStackTrace();
                                    }  catch (UnrecoverableKeyException e) {
                                        e.printStackTrace();
                                    } catch (BadPaddingException e) {
                                        e.printStackTrace();
                                    }


                                    Intent iHome = new Intent(DangNhapActivity.this, MainActivity.class);
                                    iHome.putExtra("student", postResult.getAccount());
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
        if (backPressdTime + 2000 > System.currentTimeMillis()) {
            toast.cancel();//thoat thi huy toast
            super.onBackPressed();
            return;


        } else {
            toast = Toast.makeText(this, "Press back again to exit the app", Toast.LENGTH_SHORT);
            toast.show();
        }

        backPressdTime = System.currentTimeMillis();
    }
}