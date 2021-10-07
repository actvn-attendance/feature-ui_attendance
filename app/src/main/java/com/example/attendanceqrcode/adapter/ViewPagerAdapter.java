package com.example.attendanceqrcode.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.attendanceqrcode.fragment.ChatClassFragment;
import com.example.attendanceqrcode.fragment.InfomationClassFragment;
import com.example.attendanceqrcode.fragment.HistoryAttendanceFragment;
import com.example.attendanceqrcode.modelapi.Subject;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    Subject subject;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior, Subject subject) {
        super(fm, behavior);
        this.subject = subject;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new InfomationClassFragment(subject);
            case 1:
                return new ChatClassFragment(subject);
            case 2:
                return new HistoryAttendanceFragment(subject);
                default:
                    return new InfomationClassFragment(subject);
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title = "";
        switch (position)
        {
            case 0:
                title = "Thông tin lớp";
                break;
            case 1:
                title = "Trò chuyện";
                break;
            case 2:
                title = "Lịch sử điểm danh";
                break;
        }
        return title;
    }

}
