package com.example.attendanceqrcode.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.attendanceqrcode.AccountDetailActivity;
import com.example.attendanceqrcode.ChatDetailActivity;
import com.example.attendanceqrcode.DangNhapActivity;
import com.example.attendanceqrcode.MainActivity;
import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.modelapi.Account;
import com.example.attendanceqrcode.utils.Utils;

public class ProfileFragment extends Fragment {
    Account account;
    TextView tvHoten, tvNgaySinh, tvMaSV, tvSoSMND, tvDiaChi, tvGioiTinh, tvEmail, tvSdt;
    Button btnChat,btnDangXuat;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().setTitle("Thông tin sinh viên");

        tvHoten = view.findViewById(R.id.tv_ten_sinhvien);
        tvNgaySinh = view.findViewById(R.id.tv_ngaysinh);
        tvMaSV = view.findViewById(R.id.tv_masv);
        tvSoSMND = view.findViewById(R.id.tv_so_cmnd);
        tvDiaChi = view.findViewById(R.id.tv_dia_chi);
        tvEmail = view.findViewById(R.id.tv_email);
        tvSdt = view.findViewById(R.id.tv_sdt);
        btnChat = view.findViewById(R.id.btn_chat);
        btnDangXuat = view.findViewById(R.id.btn_logout);

        int uid = Utils.getUserID(getActivity());

        account = Utils.getLocalAccount(getActivity());

        if (account.getAccount_id() == uid) {
            btnChat.setVisibility(View.GONE);
            btnDangXuat.setVisibility(View.VISIBLE);
        }
        tvHoten.setText(account.getFull_name() + "");
        tvNgaySinh.setText(account.getDate_of_birth());
        tvMaSV.setText(account.getNumber_code() + "");
        tvSoSMND.setText(account.getId_no() + "");
        tvDiaChi.setText(account.getNative_place() + "");
        tvEmail.setText(account.getEmail() + "");
        tvSdt.setText(account.getPhone_no() + "");


        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChatDetailActivity.class);
                intent.putExtra("chat", account);
                startActivity(intent);
            }
        });

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.logOut(getActivity());
                Intent intent = new Intent(getActivity(), DangNhapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        return view;
    }
}