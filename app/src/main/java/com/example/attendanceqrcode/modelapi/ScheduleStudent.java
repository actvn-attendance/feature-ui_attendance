package com.example.attendanceqrcode.modelapi;

import java.util.List;

public class ScheduleStudent {
    private String datetime;
    private List<Schedule> schedule;

    public ScheduleStudent(String datetime, List<Schedule> schedule) {
        this.datetime = datetime;
        this.schedule = schedule;
    }

    public ScheduleStudent() {
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }
}
