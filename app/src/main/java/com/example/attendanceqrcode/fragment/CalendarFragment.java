package com.example.attendanceqrcode.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.api.ApiService;
import com.example.attendanceqrcode.modelapi.InfoUser;
import com.example.attendanceqrcode.modelapi.Schedule;
import com.example.attendanceqrcode.modelapi.User;
import com.github.ybq.android.spinkit.style.FoldingCube;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CalendarFragment extends Fragment {

    List<Schedule> scheduleList = new ArrayList<>();
    com.applandeo.materialcalendarview.CalendarView calendar;
    TextView txt_batdau;
    TextView txt_ketthuc;
    TextView txt_tenlop;
    TextView txt_phonghoc;
    LinearLayout ll_detailclass;
    TextView txt_event;
    List<EventDay> events = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        getActivity().setTitle("Lịch học");

        calendar = view.findViewById(R.id.calendarViewEvent);
        txt_batdau = view.findViewById(R.id.txt_tBatdau);
        txt_ketthuc = view.findViewById(R.id.txt_tKetthuc);
        txt_tenlop = view.findViewById(R.id.txt_tenlop);
        txt_phonghoc = view.findViewById(R.id.txt_phonghoc);
        ll_detailclass = view.findViewById(R.id.ll_detailclass);
        txt_event = view.findViewById(R.id.txt_event);


        getSchedule();


        return view;
    }

    private void getSchedule()
    {

        SharedPreferences sharedPref = getActivity().getSharedPreferences("Account", Context.MODE_PRIVATE);
        String access_token = sharedPref.getString("token", "");

        //add icon event calendar
//        ApiService.apiService.getSchedule(access_token, "2021-07-01 ", "2021-11-20").enqueue(new Callback<List<Schedule>>() {
//            @Override
//            public void onResponse(Call<List<Schedule>> call, Response<List<Schedule>> response) {
//
//                scheduleList = response.body();
//
//                for (int k = 0; k < scheduleList.size(); k++) {
//
//                    //add event in schedule
//                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//                    Date date = null;
//                    try {
//                        date = sdf.parse(scheduleList.get(k).getDate()+"-"
//                                +scheduleList.get(k).getMonth()+"-"+scheduleList.get(k).getYear());
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    Calendar cal = Calendar.getInstance();
//
//                    cal.setTime(date);
//                    events.add(new EventDay(cal, R.drawable.ic_baseline_brightness_1_24));
//
//                    //set event default
//                    Date currentTime = Calendar.getInstance().getTime();
//                    if (currentTime.getDate() == scheduleList.get(k).getDate()
//                            && currentTime.getMonth()+1 == scheduleList.get(k).getMonth()
//                            && currentTime.getYear()+1900 == scheduleList.get(k).getYear())
//                    {
//                        txt_event.setText("School");
//                        ll_detailclass.setVisibility(View.VISIBLE);
//                        txt_batdau.setText(scheduleList.get(cal.getTime().getDate()).getStartTime());
//                        txt_tenlop.setText(scheduleList.get(cal.getTime().getDate()).getClassroomName());
//                        txt_ketthuc.setText(scheduleList.get(cal.getTime().getDate()).getEndTime());
//                        txt_phonghoc.setText(scheduleList.get(cal.getTime().getDate()).getAddressName());
//                    }
//                    if (currentTime.getDate() != scheduleList.get(k).getDate()
//                            && currentTime.getMonth()+1 != scheduleList.get(k).getMonth()
//                            && currentTime.getYear()+1900 != scheduleList.get(k).getYear()){
//                        txt_event.setText("No event");
//                        ll_detailclass.setVisibility(View.INVISIBLE);
//
//                    }
//
//
//
//                }
//                calendar.setEvents(events);
//
//                // setclick
//                calendar.setOnDayClickListener(new OnDayClickListener() {
//                    @Override
//                    public void onDayClick(EventDay eventDay) {
//                        Calendar clickedDayCalendar = eventDay.getCalendar();
//
//                        txt_event.setText("No event");
//                        ll_detailclass.setVisibility(View.INVISIBLE);
//
//
//                        for (int k = 0; k < scheduleList.size(); k++) {
//
////                            if (clickedDayCalendar.getTime().getDate() == scheduleList.get(k).getDate() &&
////                                    clickedDayCalendar.getTime().getMonth()+1 == scheduleList.get(k).getMonth() &&
////                                    clickedDayCalendar.getTime().getYear()+1900 == scheduleList.get(k).getYear() )
////                            {
////
////                                txt_event.setText("School");
////                                ll_detailclass.setVisibility(View.VISIBLE);
////
////                                Log.d("kiemtra123", scheduleList.get(k).getAddressName() + "");
////                                txt_batdau.setText(scheduleList.get(k).getStartTime());
////                                txt_tenlop.setText(scheduleList.get(k).getClassroomName());
////                                txt_ketthuc.setText(scheduleList.get(k).getEndTime());
////                                txt_phonghoc.setText(scheduleList.get(k).getAddressName());
////
////
////                            }
//
//
//                        }
//
//
//
//                            }
//                        });
//
//
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Schedule>> call, Throwable t) {
//
//                    }
//                });


            }


}