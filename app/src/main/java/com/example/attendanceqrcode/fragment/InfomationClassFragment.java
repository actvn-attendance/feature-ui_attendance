package com.example.attendanceqrcode.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendanceqrcode.AccountDetailActivity;
import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.adapter.AdapterRecyclerHistoryAttendance;
import com.example.attendanceqrcode.adapter.AdapterRecyclerListSinhVien;
import com.example.attendanceqrcode.api.ApiService;
import com.example.attendanceqrcode.modelapi.Account;
import com.example.attendanceqrcode.modelapi.HistoryAttendanceUser;
import com.example.attendanceqrcode.modelapi.Subject;
import com.example.attendanceqrcode.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfomationClassFragment extends Fragment implements AdapterRecyclerListSinhVien.OnCLickAccount {
    TextView tvTenLop, tvSoTinChi;
    Subject subject;
    RecyclerView recyclerView, recyclerGiaoVien;
    AdapterRecyclerListSinhVien adapterRecyclerListSinhVien, adapterGiaoVien;

    List<Account> studentList = new ArrayList<>();
    List<Account> teacherList = new ArrayList<>();

    public InfomationClassFragment(Subject subject) {
        this.subject = subject;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_information_class, container, false);
        tvTenLop = view.findViewById(R.id.tv_ten_lop);
        tvSoTinChi = view.findViewById(R.id.tv_so_tin);
        recyclerView = view.findViewById(R.id.list_sinhvien);
        recyclerGiaoVien= view.findViewById(R.id.list_giaovien);


        tvTenLop.setText(subject.getSubject_name());
        tvSoTinChi.setText(subject.getNumber_of_credits() + "");

        initData();

        adapterGiaoVien = new AdapterRecyclerListSinhVien(teacherList, getActivity(), this);
        recyclerGiaoVien.setAdapter(adapterRecyclerListSinhVien);

        adapterRecyclerListSinhVien = new AdapterRecyclerListSinhVien(studentList, getActivity(), this);
        recyclerView.setAdapter(adapterRecyclerListSinhVien);


        return view;
    }

    private void initData() {
        String token = Utils.getToken(getActivity());
        ApiService.apiService.getUserInSubject(token, subject.getId()).enqueue(new Callback<List<Account>>() {
            @Override
            public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                if (response.code() == 200) {
                    List<Account> accountList = response.body();
                    accountList.forEach(account -> {
                        if (account.getRole_cd().contains("TEACHER")) {
                            teacherList.add(account);
                        } else {
                            studentList.add(account);
                        }
                    });

                    adapterGiaoVien = new AdapterRecyclerListSinhVien(teacherList, getActivity(), InfomationClassFragment.this::onClickItem);
                    recyclerGiaoVien.setAdapter(adapterGiaoVien);

                    adapterRecyclerListSinhVien = new AdapterRecyclerListSinhVien(studentList, getActivity(), InfomationClassFragment.this::onClickItem);
                    recyclerView.setAdapter(adapterRecyclerListSinhVien);
                }
            }

            @Override
            public void onFailure(Call<List<Account>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClickItem(Account account) {
        Intent intent = new Intent(getActivity(), AccountDetailActivity.class);
        intent.putExtra("student", account);
        startActivity(intent);
    }
}
