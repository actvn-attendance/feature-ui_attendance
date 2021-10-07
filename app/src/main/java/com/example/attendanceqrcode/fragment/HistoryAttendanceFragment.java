package com.example.attendanceqrcode.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.adapter.AdapterRecyclerHistoryAttendance;
import com.example.attendanceqrcode.adapter.AdapterRecyclerSubject;
import com.example.attendanceqrcode.api.ApiService;
import com.example.attendanceqrcode.model.HistoryAttendance;
import com.example.attendanceqrcode.modelapi.HistoryAttendanceUser;
import com.example.attendanceqrcode.modelapi.HistoryAttendances;
import com.example.attendanceqrcode.modelapi.Subject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryAttendanceFragment extends Fragment {
    RecyclerView recyclerView;
    AdapterRecyclerHistoryAttendance adapterRecyclerHistoryAttendance;
    Subject subject;
    List<HistoryAttendanceUser> attendanceUserList = new ArrayList<>();

    public HistoryAttendanceFragment(Subject subject) {
        this.subject = subject;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_attendance, container, false);

        recyclerView = view.findViewById(R.id.recycler_history_attendance);


        getHistoryAttendance(true,subject.getId());



        return view;
    }

    private void getHistoryAttendance(boolean progress,int id)
    {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("Account", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", "");

        ApiService.apiService.getHistoryAttendance(token,id).enqueue(new Callback<List<HistoryAttendanceUser>>() {
            @Override
            public void onResponse(Call<List<HistoryAttendanceUser>> call, Response<List<HistoryAttendanceUser>> response) {
                if (response.code() == 200)
                {
                    attendanceUserList = response.body();
                    for (HistoryAttendanceUser historyAttendanceUser : attendanceUserList)
                    {
                        adapterRecyclerHistoryAttendance = new AdapterRecyclerHistoryAttendance(historyAttendanceUser.getHistory_attendances(),getContext());
                        recyclerView.setAdapter(adapterRecyclerHistoryAttendance);
                    }


                }
            }

            @Override
            public void onFailure(Call<List<HistoryAttendanceUser>> call, Throwable t) {

            }
        });
    }



}
