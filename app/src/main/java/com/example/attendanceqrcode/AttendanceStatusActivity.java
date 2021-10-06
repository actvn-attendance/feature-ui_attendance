package com.example.attendanceqrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.attendanceqrcode.middleware.BaseActivity;

public class AttendanceStatusActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_status);
    }
}