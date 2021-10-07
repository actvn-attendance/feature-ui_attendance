package com.example.attendanceqrcode.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Message {
    private String message;
    private long uid;
    private String fullName;
    private String avatar;
    private String type;
    private String created;

    public Message(HashMap mapMessage){
        this.message = (String) mapMessage.get("message");
        this.uid = (long) mapMessage.get("uid");
        this.fullName = (String) mapMessage.get("fullName");
        this.avatar = (String) mapMessage.get("avatar");
        this.type = (String) mapMessage.get("type");
        this.created = (String) mapMessage.get("created");
    }

    public Message(long uid, String fullName, String message, String type) {
        this.uid = uid;
        this.fullName = fullName;
        this.message = message;
        this.type = type;
    }

    public Message(String message){
        this.message = message;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("message", message);
        result.put("fullName", fullName);
        result.put("avatar", avatar);
        result.put("type", type);
        result.put("created", created);

        return result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
