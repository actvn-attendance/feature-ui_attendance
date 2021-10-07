package com.example.attendanceqrcode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendanceqrcode.api.ApiService;
import com.example.attendanceqrcode.components.AppAlertDialog;
import com.example.attendanceqrcode.fragment.CalendarFragment;
import com.example.attendanceqrcode.fragment.ChatFragment;
import com.example.attendanceqrcode.fragment.ClassFragment;
import com.example.attendanceqrcode.fragment.NotificationFragment;
import com.example.attendanceqrcode.middleware.BaseActivity;
import com.example.attendanceqrcode.model.QrcodeUser;
import com.example.attendanceqrcode.modelapi.Account;
import com.example.attendanceqrcode.modelapi.InfoUser;
import com.example.attendanceqrcode.modelapi.ResponseAttendance;
import com.example.attendanceqrcode.utils.AES;
import com.example.attendanceqrcode.utils.AppConfigs;
import com.example.attendanceqrcode.utils.GpsTracker;
import com.example.attendanceqrcode.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;
    GpsTracker location;
    private long backPressdTime;
    Toast toast;
    TextView tvTenSinhVien,tvMaSinhVien,imgProfile;

    Account account;

    private static final int FRAGMENT_CALENDAR = 0;
    private static final int FRAGMENT_CLASS = 1;
    private static final int FRAGMENT_CHAT = 3;
    private static final int FRAGMENT_NOTIFICATION = 4;
    private int currentFragment = FRAGMENT_CALENDAR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolBar);
        navigationView = findViewById(R.id.navigation_view);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        floatingActionButton = findViewById(R.id.fab);
        View headerLayout = navigationView.getHeaderView(0);
        tvTenSinhVien = headerLayout.findViewById(R.id.tv_ten_sinhvien);
        tvMaSinhVien = headerLayout.findViewById(R.id.tv_ma_sv);
        setSupportActionBar(toolbar);

        bottomNavigationView.setBackground(null);

        floatingActionButton.setBackgroundColor(getResources().getColor(R.color.background));
        floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_qr_code_scanner_24));


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new CalendarFragment());
        bottomNavigationView.getMenu().findItem(R.id.bottom_calendar).setChecked(true);

        account = (Account) getIntent().getSerializableExtra("student");
        tvTenSinhVien.setText(account.getFull_name());
        tvMaSinhVien.setText(account.getEmail());


        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.bottom_calendar) {
                openFragment(FRAGMENT_CALENDAR, new CalendarFragment());
            } else if (id == R.id.bottom_class) {
                openFragment(FRAGMENT_CLASS, new ClassFragment());
            } else if (id == R.id.bottom_chat) {
                openFragment(FRAGMENT_CHAT, new ChatFragment());
            } else if (id == R.id.bottom_notification) {
                openFragment(FRAGMENT_NOTIFICATION, new NotificationFragment());
            }
            return true;
        });

        floatingActionButton.setOnClickListener(view -> {
            if (Utils.checkGPSPermission(MainActivity.this, getApplicationContext())) {
                location = Utils.getLocation(MainActivity.this);
                if (location != null)
                    ScanQrCode();
            } else {
                Log.e("QR Code", "gps permission");
                Toast.makeText(MainActivity.this, "Vui lòng cấp quyền vị trí để sử dụng tính năng này!",
                        Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_account) {
            Intent intent = new Intent(MainActivity.this,AccountDetailActivity.class);
            intent.putExtra("student",account);
            startActivity(intent);

        } else if (id == R.id.nav_change_pass) {

        }else if (id == R.id.nav_log_out)
        {
            Intent intent = new Intent(MainActivity.this,DangNhapActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openFragment(int fragmentSelect, Fragment fragment) {
        if (currentFragment != fragmentSelect) {
            replaceFragment(fragment);
            currentFragment = fragmentSelect;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
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

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "", "Đang điểm danh...", true);
            handlingAttendance(result.getContents());
            dialog.hide();
        }
    }

    public void ScanQrCode() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setCaptureActivity(CaptureAct.class);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Scanning Code");
        intentIntegrator.initiateScan();
    }

    private void handlingAttendance(String contents) {
        if (contents != null) {
            String json = AES.decrypt(contents, AppConfigs.aesKey);

            try {
                assert json != null;
                JSONObject obj = new JSONObject(json);
                QrcodeUser qrcodeUser = new QrcodeUser(obj);

                if (Utils.checkActiveQRCode(qrcodeUser)) {
                    //success time
                    if (location.getLongitude() == 0.0 || location.getLongitude() == 0.0) {
                        location = Utils.getLocation(MainActivity.this);
                    }
                    assert location != null;
                    if (location != null && Utils.checkDistanceAttendance(location.getLatitude(), location.getLongitude(), qrcodeUser)) {
                        // success gps
                        // as well as attendance successfully
//                        System.out.println(">>> uid: " + Utils.getAndroidID(getContentResolver()));
                        String encryptCode = AES.encrypt(
                                qrcodeUser.toRequestJSON(Utils.getAndroidID(getContentResolver()), location.getLatitude(), location.getLongitude()), AppConfigs.aesKey);

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("data", encryptCode);
                        JsonObject gsonObject = (JsonObject) JsonParser.parseString(jsonObject.toString());

                        ApiService.apiService.attendance(
                                Utils.getToken(this), gsonObject)
                                .enqueue(new Callback<ResponseAttendance>() {
                                    @Override
                                    public void onResponse( Call<ResponseAttendance> call, Response<ResponseAttendance> response) {
                                        int code = response.code();
                                        System.out.println(code);
                                        if (code == 200) {
                                            // thanh cong
                                            Toast.makeText(MainActivity.this, "Điểm danh thành công !",
                                                    Toast.LENGTH_LONG).show();
                                        } else if (code == 401) {
                                            // het han token
                                            AppAlertDialog.showTokenTimeOutDialog(MainActivity.this);
                                        } else {
                                            showFailureDialog("Đã xảy ra lỗi");
                                        }

                                    }

                                    @Override
                                    public void onFailure( Call<ResponseAttendance> call, Throwable t) {
                                        showFailureDialog("Đã xảy ra lỗi");
                                    }
                                });


                    } else {
                        // fail gps
                        showFailureDialog("Bạn đang không ở trong lớp học");
                    }
                } else {
                    // fail time
                    showFailureDialog("Mã QR đã hết hạn");
                }


            } catch (Throwable t) {
                Log.e("kt", "Could not parse malformed JSON: \"" + json + "\"");
                showFailureDialog("Đã xảy ra lỗi");
            }

        } else {
            showFailureDialog("Đã xảy ra lỗi");
        }
    }

    void showFailureDialog(String message) {
        AppAlertDialog.showOnlyAlertDialog(
                MainActivity.this,
                "ĐIỂM DANH THẤT BẠI", message,
                R.drawable.ic_fail
        );
    }


}