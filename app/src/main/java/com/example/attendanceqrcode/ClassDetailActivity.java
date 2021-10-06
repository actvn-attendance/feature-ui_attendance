package com.example.attendanceqrcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendanceqrcode.adapter.ViewPagerAdapter;
import com.example.attendanceqrcode.modelapi.Subject;
import com.google.android.material.tabs.TabLayout;

public class ClassDetailActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    ImageView imgBack;
    TextView tvNameSubject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);

        Subject subject = (Subject) getIntent().getSerializableExtra("subject");
        imgBack = findViewById(R.id.img_back_detail);
        tvNameSubject = findViewById(R.id.tv_name_subject);
        tabLayout = findViewById(R.id.tabLayyout);
        viewPager = findViewById(R.id.viewPager);

        tvNameSubject.setText(subject.getSubject_name());

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,subject);
        viewPager.setAdapter(viewPagerAdapter);


        tabLayout.setupWithViewPager(viewPager);
        imgBack.setOnClickListener(view -> {
            finish();
        });


    }

}
