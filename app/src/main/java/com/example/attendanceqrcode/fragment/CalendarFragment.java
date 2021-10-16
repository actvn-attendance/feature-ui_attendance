package com.example.attendanceqrcode.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import com.example.attendanceqrcode.R;
import com.example.attendanceqrcode.adapter.AdapterRecyclerEventCalendar;
import com.example.attendanceqrcode.api.ApiService;
import com.example.attendanceqrcode.modelapi.Schedule;
import com.example.attendanceqrcode.modelapi.ScheduleStudent;

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

    List<ScheduleStudent> scheduleStudents = new ArrayList<>();
    com.applandeo.materialcalendarview.CalendarView calendar;
    RecyclerView recyclerView_event;
    List<EventDay> events = new ArrayList<>();
    AdapterRecyclerEventCalendar adapterRecyclerEventCalendar;
    RelativeLayout rlNodata;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        getActivity().setTitle("Lịch học");

        calendar = view.findViewById(R.id.calendarViewEvent);
        recyclerView_event = view.findViewById(R.id.recycler_event);
        rlNodata = view.findViewById(R.id.ll_nodata);


        getSchedule();
        getScheduleOnCLick();

        return view;
    }

    private void getSchedule()
    {

        SharedPreferences sharedPref = getActivity().getSharedPreferences("Account", Context.MODE_PRIVATE);
        String access_token = sharedPref.getString("token", "");

        //add icon event calendar
        ApiService.apiService.getSchedule(access_token, "2021-10-20 ", "2021-08-15").enqueue(new Callback<List<ScheduleStudent>>() {
            @Override
            public void onResponse(Call<List<ScheduleStudent>> call, Response<List<ScheduleStudent>> response) {
                if(response.code() == 200) {
                    scheduleStudents = response.body();

                    for (int k = 0; k < scheduleStudents.size(); k++) {

                        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = null;
                        try {
                            date = inputFormat.parse(scheduleStudents.get(k).getDatetime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar cal = Calendar.getInstance();

                        cal.setTime(date);
                        events.add(new EventDay(cal, R.drawable.ic_baseline_brightness_1_24));

                        //set event default
                        Date currentTime = Calendar.getInstance().getTime();
                        if (currentTime.getTime() == convertStringToDate(scheduleStudents.get(k).getDatetime()).getTime()) {
                            rlNodata.setVisibility(View.GONE);
                            recyclerView_event.setVisibility(View.VISIBLE);
                            LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_anim_up_to_down);
                            recyclerView_event.setLayoutAnimation(animationController);
                            adapterRecyclerEventCalendar = new AdapterRecyclerEventCalendar(scheduleStudents.get(k).getSchedule(), getActivity());
                            recyclerView_event.setAdapter(adapterRecyclerEventCalendar);

                        } else {
                            rlNodata.setVisibility(View.VISIBLE);
                            recyclerView_event.setVisibility(View.GONE);
                        }

                    }
                    calendar.setEvents(events);
                }
            }
            @Override
            public void onFailure(Call<List<ScheduleStudent>> call, Throwable t) {

            }
        });

    }

    private void getScheduleOnCLick()
    {

        SharedPreferences sharedPref = getActivity().getSharedPreferences("Account", Context.MODE_PRIVATE);
        String access_token = sharedPref.getString("token", "");

        //add icon event calendar
        ApiService.apiService.getSchedule(access_token, "2021-10-20 ", "2021-08-15").enqueue(new Callback<List<ScheduleStudent>>() {
            @Override
            public void onResponse(Call<List<ScheduleStudent>> call, Response<List<ScheduleStudent>> response) {

                scheduleStudents = response.body();
                // setclick
                calendar.setOnDayClickListener(new OnDayClickListener() {
                    @Override
                    public void onDayClick(EventDay eventDay) {
                        Calendar clickedDayCalendar = eventDay.getCalendar();
                        rlNodata.setVisibility(View.VISIBLE);
                        recyclerView_event.setVisibility(View.GONE);

                        for (int k = 0; k < scheduleStudents.size(); k++) {
                            Date date = convertStringToDate(scheduleStudents.get(k).getDatetime());

                            if (clickedDayCalendar.getTime().toString().equals(date.toString())) {
                                rlNodata.setVisibility(View.GONE);
                                recyclerView_event.setVisibility(View.VISIBLE);
                                Log.d("currentTime____", clickedDayCalendar.getTime().toString());
                                Log.d("currentTime-111 ", date.toString());
                                LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_anim_up_to_down);
                                recyclerView_event.setLayoutAnimation(animationController);

                                adapterRecyclerEventCalendar = new AdapterRecyclerEventCalendar(scheduleStudents.get(k).getSchedule(), getActivity());
                                recyclerView_event.setAdapter(adapterRecyclerEventCalendar);

                            }


                        }

                    }

                });

            }
            @Override
            public void onFailure(Call<List<ScheduleStudent>> call, Throwable t) {

            }
        });

    }

    private Date convertStringToDate(String time)
    {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = inputFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}