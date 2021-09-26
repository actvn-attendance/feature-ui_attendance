package com.example.attendanceqrcode.model;

public class Notification {
     int typeNotifi;
     String contentNotifi;
     String dateNotifi;

    public Notification(int typeNotifi, String contentNotifi, String dateNotifi) {
        this.typeNotifi = typeNotifi;
        this.contentNotifi = contentNotifi;
        this.dateNotifi = dateNotifi;
    }

    public int getTypeNotifi() {
        return typeNotifi;
    }

    public void setTypeNotifi(int typeNotifi) {
        this.typeNotifi = typeNotifi;
    }

    public String getContentNotifi() {
        return contentNotifi;
    }

    public void setContentNotifi(String contentNotifi) {
        this.contentNotifi = contentNotifi;
    }

    public String getDateNotifi() {
        return dateNotifi;
    }

    public void setDateNotifi(String dateNotifi) {
        this.dateNotifi = dateNotifi;
    }

}
