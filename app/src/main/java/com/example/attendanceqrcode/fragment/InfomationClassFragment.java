package com.example.attendanceqrcode.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.modelapi.Subject;

public class InfomationClassFragment extends Fragment {
    TextView tvTenLop,tvSoTinChi;
    Subject subject;
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

        tvTenLop.setText(subject.getSubject_name());
        tvSoTinChi.setText(subject.getNumber_of_credits()+"");


        return view;
    }

}
