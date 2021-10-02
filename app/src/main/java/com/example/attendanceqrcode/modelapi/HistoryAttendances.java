package com.example.attendanceqrcode.modelapi;

public class HistoryAttendances {
    private String create_date;
    private int present;
    private String note;
    private int id;
    private int user_id;

    public HistoryAttendances(String create_date, int present, String note, int id, int user_id) {
        this.create_date = create_date;
        this.present = present;
        this.note = note;
        this.id = id;
        this.user_id = user_id;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public int getPresent() {
        return present;
    }

    public void setPresent(int present) {
        this.present = present;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
