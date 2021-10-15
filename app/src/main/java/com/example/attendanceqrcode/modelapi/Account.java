package com.example.attendanceqrcode.modelapi;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class Account implements Serializable {
    private int account_id;
    private String number_code;
    private String last_name;
    private String fist_name;
    private String full_name;
    private String role_cd;
    private String password;
    private String sex;
    private String id_no;
    private String date_of_birth;

    private String issued_on;
    private String native_place;
    private String place_of_permanent;
    private String phone_no;
    private String email;
    private String created_date;
    private int active_flg;
    private List<Roles> roles;

    public Account() {
    }

    public Account(String number_code, String full_name) {
        this.number_code = number_code;
        this.full_name = full_name;
    }

    public Account(int account_id, String number_code, String last_name, String fist_name, String full_name, String role_cd, String password, String sex, String id_no, String date_of_birth, String issued_on, String native_place, String place_of_permanent, String phone_no, String email, String created_date, int active_flg, List<Roles> roles) {
        this.account_id = account_id;
        this.number_code = number_code;
        this.last_name = last_name;
        this.fist_name = fist_name;
        this.full_name = full_name;
        this.role_cd = role_cd;
        this.password = password;
        this.sex = sex;
        this.id_no = id_no;
        this.date_of_birth = date_of_birth;
        this.issued_on = issued_on;
        this.native_place = native_place;
        this.place_of_permanent = place_of_permanent;
        this.phone_no = phone_no;
        this.email = email;
        this.created_date = created_date;
        this.active_flg = active_flg;
        this.roles = roles;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getNumber_code() {
        return number_code;
    }

    public void setNumber_code(String number_code) {
        this.number_code = number_code;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFist_name() {
        return fist_name;
    }

    public void setFist_name(String fist_name) {
        this.fist_name = fist_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getRole_cd() {
        return role_cd;
    }

    public void setRole_cd(String role_cd) {
        this.role_cd = role_cd;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getId_no() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getIssued_on() {
        return issued_on;
    }

    public void setIssued_on(String issued_on) {
        this.issued_on = issued_on;
    }

    public String getNative_place() {
        return native_place;
    }

    public void setNative_place(String native_place) {
        this.native_place = native_place;
    }

    public String getPlace_of_permanent() {
        return place_of_permanent;
    }

    public void setPlace_of_permanent(String place_of_permanent) {
        this.place_of_permanent = place_of_permanent;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public int getActive_flg() {
        return active_flg;
    }

    public void setActive_flg(int active_flg) {
        this.active_flg = active_flg;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

}