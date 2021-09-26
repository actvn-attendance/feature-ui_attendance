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


import com.example.attendanceqrcode.HistoryAttendanceActivity;
import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.adapter.AdapterRecyclerClass;
import com.example.attendanceqrcode.model.ClassRooms;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarInputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ClassFragment extends Fragment implements AdapterRecyclerClass.ClickHistoryClass,AdapterRecyclerClass.ClickDetailClass, SwipeRefreshLayout.OnRefreshListener {

    AdapterRecyclerClass adapterRecyclerClass;
    List<ClassRooms> lopHocModels;
    RecyclerView recyclerViewLopHoc;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        getActivity().setTitle("Lớp học");
        View view = inflater.inflate(R.layout.fragment_class, container, false);
        recyclerViewLopHoc = view.findViewById(R.id.recycler_class);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        initData();
        adapterRecyclerClass = new AdapterRecyclerClass(lopHocModels,getActivity(),this,this);
        recyclerViewLopHoc.setAdapter(adapterRecyclerClass);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.buttoncolor));


//        getInfo();


        return view;
    }

    private void initData()
    {
        lopHocModels = new ArrayList<>();
        lopHocModels.add(new ClassRooms(0,"Android nang cao",0,"Thay Cuong"));
        lopHocModels.add(new ClassRooms(0,"Toi uu phan mem",0,"Thay Cuong"));
        lopHocModels.add(new ClassRooms(0,"Toi uu phan mem nhung",0,"Co Van"));
        lopHocModels.add(new ClassRooms(0,"Nhung nang cao",0,"Thay Truong"));
    }


//    private void getInfo()
//    {
//        SharedPreferences sharedPref = getActivity().getSharedPreferences("Account", Context.MODE_PRIVATE);
//        String username = sharedPref.getString("username", "");
//        String password = sharedPref.getString("password","");
//        User user = new User(username,password);
//        ApiService.apiService.sendAccount(user).enqueue(new Callback<InfoUser>() {
//            @Override
//            public void onResponse(Call<InfoUser> call, Response<InfoUser> response) {
//                InfoUser infoUser = response.body();
//
//                lopHocModels = infoUser.getAccount().getClassRooms();
//                adapterRecyclerClass = new AdapterRecyclerClass(lopHocModels,getActivity(),ClassFragment.this::clickItem);
//                recyclerViewLopHoc.setAdapter(adapterRecyclerClass);
//                adapterRecyclerClass.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onFailure(Call<InfoUser> call, Throwable t) {
//
//            }
//        });
//    }


    @Override
    public void onRefresh() {
//        getInfo();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);//tat di

            }
        },1500);
    }

    @Override
    public void clickHistory() {
        Intent iHistoryAttendance = new Intent(getActivity(), HistoryAttendanceActivity.class);
        startActivity(iHistoryAttendance);

    }

    @Override
    public void clickDetail() {

    }
}