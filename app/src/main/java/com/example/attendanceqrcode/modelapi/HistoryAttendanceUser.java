package com.example.attendanceqrcode.modelapi;

import java.util.List;

public class HistoryAttendanceUser {

    private int user_id;
    private int schedule_id;
    private int classroom_id;
    private String classroom_name;
    private String user_name;
    private List<HistoryAttendances> history_attendances;

    public HistoryAttendanceUser(int user_id, int schedule_id, int classroom_id, String classroom_name, String user_name, List<HistoryAttendances> history_attendances) {
        this.user_id = user_id;
        this.schedule_id = schedule_id;
        this.classroom_id = classroom_id;
        this.classroom_name = classroom_name;
        this.user_name = user_name;
        this.history_attendances = history_attendances;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }

    public int getClassroom_id() {
        return classroom_id;
    }

    public void setClassroom_id(int classroom_id) {
        this.classroom_id = classroom_id;
    }

    public String getClassroom_name() {
        return classroom_name;
    }

    public void setClassroom_name(String classroom_name) {
        this.classroom_name = classroom_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    public List<HistoryAttendances> getHistory_attendances() {
        return history_attendances;
    }

    public void setHistory_attendances(List<HistoryAttendances> history_attendances) {
        this.history_attendances = history_attendances;
    }
}
