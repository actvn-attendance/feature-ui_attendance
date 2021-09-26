package com.example.attendanceqrcode.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.adapter.AdapterRecyclerNotifi;
import com.example.attendanceqrcode.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment implements AdapterRecyclerNotifi.ClickItemNotifi {

    AdapterRecyclerNotifi adapterRecyclerNotifi;
    List<Notification> notificationList;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notification, container, false);
        recyclerView = view.findViewById(R.id.recyclerNotifi);
        initData();
        adapterRecyclerNotifi = new AdapterRecyclerNotifi(notificationList,getActivity(),this);
        recyclerView.setAdapter(adapterRecyclerNotifi);

        return view;
    }

    private void initData() {
        notificationList = new ArrayList<>();
        notificationList.add(new Notification(0,"Ma diem danh lop Android da duoc tao","15-10-2021 - 15:00"));
        notificationList.add(new Notification(1,"Nhom android da gui cho ban tin nhan moi","15-10-2021 - 15:00"));
        notificationList.add(new Notification(0,"Ma diem danh lop Android da duoc tao","15-10-2021 - 15:00"));
        notificationList.add(new Notification(1,"Nhom android da gui cho ban tin nhan moi","15-10-2021 - 15:00"));
        notificationList.add(new Notification(0,"Ma diem danh lop Android da duoc tao","15-10-2021 - 15:00"));
        notificationList.add(new Notification(1,"Nhom android da gui cho ban tin nhan moi","15-10-2021 - 15:00"));
    }


    @Override
    public void clickItemNoti() {

    }
}