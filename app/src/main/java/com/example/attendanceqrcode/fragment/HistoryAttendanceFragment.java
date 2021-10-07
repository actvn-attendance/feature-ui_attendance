package com.example.attendanceqrcode.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.adapter.AdapterRecyclerHistoryAttendance;
import com.example.attendanceqrcode.model.HistoryAttendance;
import com.example.attendanceqrcode.modelapi.Subject;

import java.util.ArrayList;
import java.util.List;

public class HistoryAttendanceFragment extends Fragment {
    RecyclerView recyclerView;
    List<HistoryAttendance> historyAttendanceList;
    AdapterRecyclerHistoryAttendance adapterRecyclerHistoryAttendance;

    public HistoryAttendanceFragment(Subject subject) {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_attendance, container, false);

        recyclerView = view.findViewById(R.id.recycler_history_attendance);


        initData();

        adapterRecyclerHistoryAttendance = new AdapterRecyclerHistoryAttendance(historyAttendanceList,getContext());
        recyclerView.setAdapter(adapterRecyclerHistoryAttendance);

        return view;
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
