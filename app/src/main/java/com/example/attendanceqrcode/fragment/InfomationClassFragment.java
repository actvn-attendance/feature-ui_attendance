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
import com.example.attendanceqrcode.adapter.AdapterRecyclerListSinhVien;
import com.example.attendanceqrcode.modelapi.Account;
import com.example.attendanceqrcode.modelapi.Subject;

import java.util.ArrayList;
import java.util.List;

public class InfomationClassFragment extends Fragment implements AdapterRecyclerListSinhVien.OnCLickAccount {
    TextView tvTenLop,tvSoTinChi;
    Subject subject;
    RecyclerView recyclerView;
    AdapterRecyclerListSinhVien adapterRecyclerListSinhVien;
    List<Account> accountList;
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


        tvTenLop.setText(subject.getSubject_name());
        tvSoTinChi.setText(subject.getNumber_of_credits()+"");
        initData();

        adapterRecyclerListSinhVien = new AdapterRecyclerListSinhVien(accountList,getActivity(),this);
        recyclerView.setAdapter(adapterRecyclerListSinhVien);



        return view;
    }
    private void initData()
    {
        accountList = new ArrayList<>();
        accountList.add(new Account("CT020139","Tiến Xuân Hoàng"));
        accountList.add(new Account("CT020139","Trương Việt Hoàng"));
        accountList.add(new Account("CT020139","Trương Việt Hoàng"));
        accountList.add(new Account("CT020139","Tiến Xuân Hoàng"));

    }

    @Override
    public void onClickItem(Account account) {
        Intent intent = new Intent(getActivity(), AccountDetailActivity.class);
        intent.putExtra("student",account);
        startActivity(intent);
    }
}
