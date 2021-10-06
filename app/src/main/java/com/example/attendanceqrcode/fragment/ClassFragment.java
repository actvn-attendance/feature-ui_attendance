package com.example.attendanceqrcode.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.attendanceqrcode.ClassDetailActivity;
import com.example.attendanceqrcode.HistoryAttendanceActivity;
import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.adapter.AdapterRecyclerSubject;
import com.example.attendanceqrcode.api.ApiService;
import com.example.attendanceqrcode.model.ClassRooms;
import com.example.attendanceqrcode.modelapi.Subject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ClassFragment extends Fragment implements AdapterRecyclerSubject.ClickDetailClass, SwipeRefreshLayout.OnRefreshListener {

    AdapterRecyclerSubject adapterRecyclerSubject;
    List<Subject> subjectList;
    RecyclerView recyclerViewLopHoc;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        getActivity().setTitle("Lớp học");
        View view = inflater.inflate(R.layout.fragment_class, container, false);
        getActivity().setTitle("Danh sách lớp");
        recyclerViewLopHoc = view.findViewById(R.id.recycler_class);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.buttoncolor));

        getInfo();

        return view;
    }



    private void getInfo()
    {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("Account", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", "");

        ApiService.apiService.getSubject(token).enqueue(new Callback<List<Subject>>() {
            @Override
            public void onResponse(Call<List<Subject>> call, Response<List<Subject>> response) {
                if (response.code() == 200)
                {
                    subjectList = response.body();
                    adapterRecyclerSubject = new AdapterRecyclerSubject(subjectList,getActivity(),ClassFragment.this::clickDetail);
                    recyclerViewLopHoc.setAdapter(adapterRecyclerSubject);
                }
            }

            @Override
            public void onFailure(Call<List<Subject>> call, Throwable t) {

            }
        });
    }


    @Override
    public void onRefresh() {
        getInfo();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);//tat di

            }
        },1500);
    }


    @Override
    public void clickDetail(Subject subject) {
        Intent intent = new Intent(getActivity(), ClassDetailActivity.class);
        intent.putExtra("subject", subject);

        startActivity(intent);
    }
}