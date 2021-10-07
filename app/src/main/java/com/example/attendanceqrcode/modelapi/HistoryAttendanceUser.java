package com.example.attendanceqrcode.modelapi;

import java.util.List;

public class HistoryAttendanceUser {

    private int user_id;
    private int classroom_id;
    private int subject_id;
    private String subject_name;
    private String user_name;
    private List<HistoryAttendances> history_attendances;

    public HistoryAttendanceUser(int user_id, int classroom_id, int subject_id, String subject_name, String user_name, List<HistoryAttendances> history_attendances) {
        this.user_id = user_id;
        this.classroom_id = classroom_id;
        this.subject_id = subject_id;
        this.subject_name = subject_name;
        this.user_name = user_name;
        this.history_attendances = history_attendances;
    }

    public HistoryAttendanceUser() {
    }


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getClassroom_id() {
        return classroom_id;
    }

    public void setClassroom_id(int classroom_id) {
        this.classroom_id = classroom_id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
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
