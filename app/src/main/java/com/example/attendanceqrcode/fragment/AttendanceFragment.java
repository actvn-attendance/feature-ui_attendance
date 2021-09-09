package com.example.attendanceqrcode.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.attendanceqrcode.CaptureAct;
import com.example.attendanceqrcode.R;
import com.google.zxing.integration.android.IntentIntegrator;



public class AttendanceFragment extends Fragment implements  SwipeRefreshLayout.OnRefreshListener {

    Button btnScan;
    public static int REQUEST_ENABLE_BT = 105;

//    AdapterRecyclerAttendanceStatistics adapterRecyclerAttendanceStatistics;
    RecyclerView recyclerStatistics;
//    List<AttendanceStatistics> statisticsList = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_attendance, container, false);
        btnScan = view.findViewById(R.id.btn_scan);
        recyclerStatistics = view.findViewById(R.id.recycler_history_attendance);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.buttoncolor));


//        getAttendanceStatistics();


//        BluetoothAdapter m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        if (!m_BluetoothAdapter.isEnabled()) {
//            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            String bluetooth = m_BluetoothAdapter.getAddress();
//
//            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("bluetooth", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("bluetooth",bluetooth);
//            editor.commit();
//            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
//        }

        btnScan = view.findViewById(R.id.btn_scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ScanQrCode(getActivity());

//                BluetoothAdapter m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//                if (!m_BluetoothAdapter.isEnabled()) {
//                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                    String bluetooth = m_BluetoothAdapter.getAddress();
//
//
//                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("bluetooth", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("bluetooth",bluetooth);
//                    editor.commit();
//                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
//                }



            }
        });


        return view;
    }

    public void ScanQrCode(Activity activity)
    {
        IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
        intentIntegrator.setCaptureActivity(CaptureAct.class);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Scanning Code");
        intentIntegrator.initiateScan();

    }

//    private void getAttendanceStatistics()
//    {
//        SharedPreferences sharedPref = getActivity().getSharedPreferences("Account", Context.MODE_PRIVATE);
//        String accessToken = sharedPref.getString("token","");
//
//        ApiService.apiService.getAttendanceStatistics(accessToken).enqueue(new Callback<List<AttendanceStatistics>>() {
//
//            @Override
//            public void onResponse(Call<List<AttendanceStatistics>> call, Response<List<AttendanceStatistics>> response) {
//
//                statisticsList = response.body();
//                adapterRecyclerAttendanceStatistics = new AdapterRecyclerAttendanceStatistics(statisticsList,getActivity(),AttendanceFragment.this::clickedItemDetailAttendance);
//                recyclerStatistics.setAdapter(adapterRecyclerAttendanceStatistics);
//
//            }
//
//            @Override
//            public void onFailure(Call<List<AttendanceStatistics>> call, Throwable t) {
//
//            }
//        });
//
//    }


    @Override
    public void onRefresh() {
//        getAttendanceStatistics();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);//tat di

            }
        },1500);

    }
}