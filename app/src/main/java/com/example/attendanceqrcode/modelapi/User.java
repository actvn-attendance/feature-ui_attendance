package com.example.attendanceqrcode.modelapi;

public class User {
    private String device_token;
    private String login_id;
    private String password;

    public User(String login_id, String password, String device_token) {
        this.login_id = login_id;
        this.password = password;
        this.device_token = device_token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
