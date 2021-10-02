package com.example.attendanceqrcode.modelapi;

public class AttendanceStatistics {

    private String classroom_name;
    private int sum_attendance;
    private int user_attendance;
    private int classroom_id;

    public AttendanceStatistics(String classroom_name, int sum_attendance, int user_attendance, int classroom_id) {
        this.classroom_name = classroom_name;
        this.sum_attendance = sum_attendance;
        this.user_attendance = user_attendance;
        this.classroom_id = classroom_id;
    }

    public String getClassroom_name() {
        return classroom_name;
    }

    public void setClassroom_name(String classroom_name) {
        this.classroom_name = classroom_name;
    }

    public int getSum_attendance() {
        return sum_attendance;
    }

    public void setSum_attendance(int sum_attendance) {
        this.sum_attendance = sum_attendance;
    }

    public int getUser_attendance() {
        return user_attendance;
    }

    public void setUser_attendance(int user_attendance) {
        this.user_attendance = user_attendance;
    }

    public int getClassroom_id() {
        return classroom_id;
    }

    public void setClassroom_id(int classroom_id) {
        this.classroom_id = classroom_id;
    }

    @Override
    public String toString() {
        return "AttendanceStatistics{" +
                "classroom_name='" + classroom_name + '\'' +
                ", sum_attendance=" + sum_attendance +
                ", user_attendance=" + user_attendance +
                ", classroom_id=" + classroom_id +
                '}';
    }
}
