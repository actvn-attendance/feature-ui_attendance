package com.example.attendanceqrcode.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.adapter.AdapterRecyclerNotifi;
import com.example.attendanceqrcode.adapter.AdapterRecyclerSubject;
import com.example.attendanceqrcode.api.ApiService;
import com.example.attendanceqrcode.model.Notification;
import com.example.attendanceqrcode.modelapi.Notifications;
import com.example.attendanceqrcode.modelapi.Subject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment implements AdapterRecyclerNotifi.ClickItemNotifi, SwipeRefreshLayout.OnRefreshListener {

    AdapterRecyclerNotifi adapterRecyclerNotifi;
    Notifications notifications;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    LinearLayoutManager linearLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notification, container, false);
        getActivity().setTitle("Thông báo");
        recyclerView = view.findViewById(R.id.recyclerNotifi);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        progressBar = view.findViewById(R.id.processbar);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.buttoncolor));

        getNotigication(true);

        return view;
    }

    private void getNotigication(Boolean progress)
    {
        progressBar.setVisibility(View.GONE);
        if (progress == true)
        {
            progressBar.setVisibility(View.VISIBLE);
        }
        SharedPreferences sharedPref = getActivity().getSharedPreferences("Account", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", "");

        ApiService.apiService.getNotification(token,1,20).enqueue(new Callback<Notifications>() {
            @Override
            public void onResponse(Call<Notifications> call, Response<Notifications> response) {
                if (response.code() == 200)
                {
                    notifications = response.body();

                    adapterRecyclerNotifi = new AdapterRecyclerNotifi(notifications.getData(),getActivity(),NotificationFragment.this::clickItemNoti);
                    linearLayoutManager = new LinearLayoutManager(getActivity());
                    linearLayoutManager.setReverseLayout(true);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapterRecyclerNotifi);
                    recyclerView.scrollToPosition(notifications.getData().size() - 1);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Notifications> call, Throwable t) {

            }
        });
    }




    @Override
    public void clickItemNoti() {

    }


    @Override
    public void onRefresh() {
        getNotigication(false);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);//tat di

            }
        },1500);
    }
}