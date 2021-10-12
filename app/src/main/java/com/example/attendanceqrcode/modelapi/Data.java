package com.example.attendanceqrcode.modelapi;

public class Data {

    private int notification_id;
    private int topic_id;
    private int device_user_id;
    private String target;
    private String title;
    private String body;
    private int type;
    private int send_by;
    private String created_date;
    private String updated_date;
    private String updated_user;
    private int active_flg;
    private int read_status;


    public Data(int notification_id, int topic_id, int device_user_id, String target, String title, String body, int type, int send_by, String created_date, String updated_date, String updated_user, int active_flg, int read_status) {
        this.notification_id = notification_id;
        this.topic_id = topic_id;
        this.device_user_id = device_user_id;
        this.target = target;
        this.title = title;
        this.body = body;
        this.type = type;
        this.send_by = send_by;
        this.created_date = created_date;
        this.updated_date = updated_date;
        this.updated_user = updated_user;
        this.active_flg = active_flg;
        this.read_status = read_status;
    }

    public Data() {
    }

    public int getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(int notification_id) {
        this.notification_id = notification_id;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public int getDevice_user_id() {
        return device_user_id;
    }

    public void setDevice_user_id(int device_user_id) {
        this.device_user_id = device_user_id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSend_by() {
        return send_by;
    }

    public void setSend_by(int send_by) {
        this.send_by = send_by;
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

    public int getActive_flg() {
        return active_flg;
    }

    public void setActive_flg(int active_flg) {
        this.active_flg = active_flg;
    }

    public int getRead_status() {
        return read_status;
    }

    public void setRead_status(int read_status) {
        this.read_status = read_status;
    }
}
