package com.example.attendanceqrcode.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.adapter.AdapterRecyclerHistoryAttendance;
import com.example.attendanceqrcode.api.ApiService;
import com.example.attendanceqrcode.modelapi.HistoryAttendanceUser;
import com.example.attendanceqrcode.modelapi.Subject;
import com.example.attendanceqrcode.utils.SharedPreferenceHelper;
import com.example.attendanceqrcode.utils.Utils;

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
        String token = Utils.getToken(getContext());

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
