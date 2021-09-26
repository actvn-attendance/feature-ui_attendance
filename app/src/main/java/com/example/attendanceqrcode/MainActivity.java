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

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.attendanceqrcode.fragment.AccountFragment;
import com.example.attendanceqrcode.fragment.AttendanceFragment;
import com.example.attendanceqrcode.fragment.CalendarFragment;
import com.example.attendanceqrcode.fragment.ChatFragment;
import com.example.attendanceqrcode.fragment.ClassFragment;
import com.example.attendanceqrcode.fragment.NotificationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;

    private static final int FRAGMENT_CALENDAR = 0;
    private static final int FRAGMENT_CLASS = 1;
    private static final int FRAGMENT_CHAT = 3;
    private static final int FRAGMENT_NOTIFICATION = 4;
    private static final int FRAGMENT_ACCOUNT = 5;
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
        bottomNavigationView.setBackground(null);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new CalendarFragment());
        navigationView.getMenu().findItem(R.id.nav_calendar).setChecked(true);
        bottomNavigationView.getMenu().findItem(R.id.bottom_calendar).setChecked(true);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull  MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.bottom_calendar)
                {
                    openFragment(FRAGMENT_CALENDAR,new CalendarFragment());
                    navigationView.getMenu().findItem(R.id.nav_calendar).setChecked(true);
                }
                else if (id == R.id.bottom_class)
                {
                    openFragment(FRAGMENT_CLASS,new ClassFragment());
                    navigationView.getMenu().findItem(R.id.nav_class).setChecked(true);
                } else if (id == R.id.bottom_chat)
                {
                    openFragment(FRAGMENT_CHAT,new ChatFragment());
                    navigationView.getMenu().findItem(R.id.nav_chat).setChecked(true);
                }else if (id == R.id.bottom_notification)
                {
                    openFragment(FRAGMENT_NOTIFICATION,new NotificationFragment());
                    navigationView.getMenu().findItem(R.id.nav_notification).setChecked(true);
                }
                return true;
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanQrCode();
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull  MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_calendar)
        {
            openFragment(FRAGMENT_CALENDAR,new CalendarFragment());
            bottomNavigationView.getMenu().findItem(R.id.bottom_calendar).setChecked(true);
        }else if (id == R.id.nav_class){
            openFragment(FRAGMENT_CLASS,new ClassFragment());
            bottomNavigationView.getMenu().findItem(R.id.bottom_class).setChecked(true);

        }else if (id == R.id.nav_chat)
        {
           openFragment(FRAGMENT_CHAT,new ChatFragment());
            bottomNavigationView.getMenu().findItem(R.id.bottom_chat).setChecked(true);
        }else if (id == R.id.nav_notification)
        {
             openFragment(FRAGMENT_NOTIFICATION,new NotificationFragment());
            bottomNavigationView.getMenu().findItem(R.id.bottom_notification).setChecked(true);
        }else if (id == R.id.nav_account)
        {
            openFragment(FRAGMENT_ACCOUNT,new AccountFragment());
        }else if (id == R.id.nav_change_pass)
        {
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openFragment(int fragmentSelect,Fragment fragment)
    {
        if (currentFragment != fragmentSelect)
        {
            replaceFragment(fragment);
            currentFragment = fragmentSelect;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,fragment);
        fragmentTransaction.commit();

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

    }

    public void ScanQrCode()
    {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setCaptureActivity(CaptureAct.class);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Scanning Code");
        intentIntegrator.initiateScan();

    }

}