package com.example.attendanceqrcode.model;

public class NotificationRequest {
    public String body, target, title;
    public int type;

    public NotificationRequest(String title, String body, String target,  int type) {
        this.body = body;
        this.target = target;
        this.title = title;
        this.type = type;
    }
}
