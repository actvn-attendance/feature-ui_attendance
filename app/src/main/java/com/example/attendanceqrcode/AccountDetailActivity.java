package com.example.attendanceqrcode;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.attendanceqrcode.modelapi.Account;
import com.example.attendanceqrcode.modelapi.InfoUser;

public class AccountDetailActivity extends AppCompatActivity {

    Account account;
    ImageView imgBack;
    TextView tvHoten,tvNgaySinh,tvMaSV,tvSoSMND,tvDiaChi,tvGioiTinh,tvEmail,tvSdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);

        tvHoten = findViewById(R.id.tv_ten_sinhvien);
        tvNgaySinh = findViewById(R.id.tv_ngaysinh);
        tvMaSV = findViewById(R.id.tv_masv);
        tvSoSMND = findViewById(R.id.tv_so_cmnd);
        tvDiaChi = findViewById(R.id.tv_dia_chi);
        tvEmail = findViewById(R.id.tv_email);
        tvSdt = findViewById(R.id.tv_sdt);
        imgBack = findViewById(R.id.img_back_detail);

        account = (Account) getIntent().getSerializableExtra("student");

        tvHoten.setText(account.getFull_name());
        tvNgaySinh.setText(account.getDate_of_birth().substring(0,10));
        tvMaSV.setText(account.getNumber_code());
        tvSoSMND.setText(account.getId_no());
        tvDiaChi.setText(account.getNative_place());
        tvEmail.setText(account.getEmail());
        tvSdt.setText(account.getPhone_no());

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




    }
}