package com.example.attendanceqrcode.modelapi;

public class Schedule {
    private int id;
    private String start_time;
    private String end_time;
    private String start_lesson;
    private String end_lesson;
    private String  lesson;
    private String datetime;

    private String create_date;
    private String update_date;
    private String update_user;
    private int active_flg;
    private int address_id;

    public Schedule() {
    }

    public Schedule(int id, String start_time, String end_time, String start_lesson, String end_lesson, String lesson, String datetime, String create_date, String update_date, String update_user, int active_flg, int address_id) {
        this.id = id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.start_lesson = start_lesson;
        this.end_lesson = end_lesson;
        this.lesson = lesson;
        this.datetime = datetime;
        this.create_date = create_date;
        this.update_date = update_date;
        this.update_user = update_user;
        this.active_flg = active_flg;
        this.address_id = address_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getStart_lesson() {
        return start_lesson;
    }

    public void setStart_lesson(String start_lesson) {
        this.start_lesson = start_lesson;
    }

    public String getEnd_lesson() {
        return end_lesson;
    }

    public void setEnd_lesson(String end_lesson) {
        this.end_lesson = end_lesson;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(String update_user) {
        this.update_user = update_user;
    }

    public int getActive_flg() {
        return active_flg;
    }

    public void setActive_flg(int active_flg) {
        this.active_flg = active_flg;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }
}
