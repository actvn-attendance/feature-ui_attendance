package com.example.attendanceqrcode.modelapi;

import java.io.Serializable;

public class Roles implements Serializable {

    private int role_id;
    private String role_cd;
    private String role_name;
    private String description;
    private String created_date;
    private String updated_date;
    private String updated_user;

    public Roles(int role_id, String role_cd, String role_name, String description, String created_date, String updated_date, String updated_user) {
        this.role_id = role_id;
        this.role_cd = role_cd;
        this.role_name = role_name;
        this.description = description;
        this.created_date = created_date;
        this.updated_date = updated_date;
        this.updated_user = updated_user;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRole_cd() {
        return role_cd;
    }

    public void setRole_cd(String role_cd) {
        this.role_cd = role_cd;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getUpdated_user() {
        return updated_user;
    }

    public void setUpdated_user(String updated_user) {
        this.updated_user = updated_user;
    }
}
