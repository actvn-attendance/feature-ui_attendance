package com.example.attendanceqrcode.modelapi;

import java.io.Serializable;

public class Subject implements Serializable {

    private int id;
    private String subject_code;
    private String subject_name;
    private int number_of_credits;
    private String created_date;
    private String updated_date;
    private int updated_user;
    private int active_flg;

    public Subject(int id, String subject_code, String subject_name, int number_of_credits, String created_date, String updated_date, int updated_user, int active_flg) {
        this.id = id;
        this.subject_code = subject_code;
        this.subject_name = subject_name;
        this.number_of_credits = number_of_credits;
        this.created_date = created_date;
        this.updated_date = updated_date;
        this.updated_user = updated_user;
        this.active_flg = active_flg;
    }

    public Subject() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject_code() {
        return subject_code;
    }

    public void setSubject_code(String subject_code) {
        this.subject_code = subject_code;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public int getNumber_of_credits() {
        return number_of_credits;
    }

    public void setNumber_of_credits(int number_of_credits) {
        this.number_of_credits = number_of_credits;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    public int getUpdated_user() {
        return updated_user;
    }

    public void setUpdated_user(int updated_user) {
        this.updated_user = updated_user;
    }

    public int getActive_flg() {
        return active_flg;
    }

    public void setActive_flg(int active_flg) {
        this.active_flg = active_flg;
    }
}
