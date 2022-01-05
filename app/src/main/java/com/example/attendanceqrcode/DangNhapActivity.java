package com.example.attendanceqrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.attendanceqrcode.api.ApiService;
import com.example.attendanceqrcode.middleware.BaseActivity;
import com.example.attendanceqrcode.modelapi.Account;
import com.example.attendanceqrcode.modelapi.InfoUser;
import com.example.attendanceqrcode.modelapi.User;
import com.example.attendanceqrcode.utils.SharedPreferenceHelper;
import com.example.attendanceqrcode.utils.diffie_hellman.DiffieHellman;
import com.example.attendanceqrcode.utils.secure.SecureServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    private DatabaseReference userPublicKeysDB;

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
                                    initialKeysAndSaveData(postResult);
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

    void initialKeysAndSaveData(InfoUser postResult) {
        /// Sign in is successfully
        /// -> if(api >= 18) Create Android KeyStore
        /// -> create master key: secret key and RSA key of Android Keystore
        /// note: if(api < 18) -> use generate aes key by user password
        /// -> if(api >= 23) use Android Keystore to generate/save/get secret key
        /// -> (18<= api < 23) -> wrap secret key by public key and unwrap by private key
        /// -> api < 18 -> save secret key to local storage
        try {
            new SecureServices(getApplicationContext()).createMasterKey(postResult.getAccount().getPassword());

            // save data
            SharedPreferenceHelper.encryptAndSetData(SharedPreferenceHelper.tokenKey, postResult.getAccess_token());
            SharedPreferenceHelper.encryptAndSetData(SharedPreferenceHelper.fullNameKey, postResult.getAccount().getFull_name());
            SharedPreferenceHelper.encryptAndSetData(SharedPreferenceHelper.accountKey, postResult.getAccount().toJsonString());
            SharedPreferenceHelper.set(SharedPreferenceHelper.uidKey, String.valueOf(postResult.getAccount().getAccount_id()));

            // generate private key and sync public key to firebase
            generatePublicAndPrivateKey(postResult.getAccount());
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException
                | NoSuchProviderException
                | CertificateException | KeyStoreException
                | IOException e) {
            e.printStackTrace();
        }
    }

    void generatePublicAndPrivateKey(Account account) {
        DiffieHellman diffieHellman = new DiffieHellman(getApplicationContext());
        diffieHellman.generatePrivateKey(account.getPassword());

        userPublicKeysDB = FirebaseDatabase.getInstance().getReference().child("userPublicKeys");
        userPublicKeysDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (!snapshot.hasChild(String.valueOf(account.getAccount_id()))) {
                    Map<String, Object> postValues = new HashMap<>();

                    postValues.put("id", account.getAccount_id());

                    int publicKey = diffieHellman.generatePublicKey();
                    if (publicKey > -1) {
                        postValues.put("publicKey",
                                publicKey);
                        postValues.put("createAt", new Date().getTime());

                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("/" + account.getAccount_id(), postValues);
                        userPublicKeysDB.updateChildren(childUpdates);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}