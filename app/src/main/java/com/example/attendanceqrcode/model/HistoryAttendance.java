package com.example.attendanceqrcode.model;

public class HistoryAttendance {
    private int typeStatus;
    private String dateAttendance;

    public HistoryAttendance(int typeStatus, String dateAttendance) {
        this.typeStatus = typeStatus;
        this.dateAttendance = dateAttendance;
    }

    public int getTypeStatus() {
        return typeStatus;
    }

    public void setTypeStatus(int typeStatus) {
        this.typeStatus = typeStatus;
    }

    public String getDateAttendance() {
        return dateAttendance;
    }

    public void setDateAttendance(String dateAttendance) {
        this.dateAttendance = dateAttendance;
    }
}
