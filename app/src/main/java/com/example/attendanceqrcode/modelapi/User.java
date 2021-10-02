package com.example.attendanceqrcode.modelapi;

public class User {

    private String login_id;
    private String password;

    public User(String login_id, String password) {
        this.login_id = login_id;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
