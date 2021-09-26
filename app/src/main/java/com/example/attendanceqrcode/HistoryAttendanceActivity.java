package com.example.attendanceqrcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.attendanceqrcode.adapter.AdapterRecyclerHistoryAttendance;
import com.example.attendanceqrcode.model.HistoryAttendance;

import java.util.ArrayList;
import java.util.List;

public class HistoryAttendanceActivity extends AppCompatActivity {
    ImageView btnBack;
    TextView tvHistoryAttndance;
    RecyclerView recyclerView;
    List<HistoryAttendance> historyAttendanceList;
    AdapterRecyclerHistoryAttendance adapterRecyclerHistoryAttendance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_attendance);

        btnBack = findViewById(R.id.btnBackHistory);
        tvHistoryAttndance = findViewById(R.id.tvHistoryAttndance);
        recyclerView = findViewById(R.id.recycler_history_attendance);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initData();

        adapterRecyclerHistoryAttendance = new AdapterRecyclerHistoryAttendance(historyAttendanceList,getApplicationContext());
        recyclerView.setAdapter(adapterRecyclerHistoryAttendance);


    }

    private void initData() {
        historyAttendanceList = new ArrayList<>();
        historyAttendanceList.add(new HistoryAttendance(0,"15-11-2021 - 15:00"));
        historyAttendanceList.add(new HistoryAttendance(1,"15-11-2021 - 15:00"));
        historyAttendanceList.add(new HistoryAttendance(2,"15-11-2021 - 15:00"));
        historyAttendanceList.add(new HistoryAttendance(0,"15-11-2021 - 15:00"));
        historyAttendanceList.add(new HistoryAttendance(1,"15-11-2021 - 15:00"));
        historyAttendanceList.add(new HistoryAttendance(2,"15-11-2021 - 15:00"));
    }
}