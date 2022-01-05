package com.example.attendanceqrcode.model;

import android.util.Log;

import com.example.attendanceqrcode.utils.AES.AESNormal;

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

    public Message(HashMap mapMessage, String smsKey) {
        System.out.println("symmetric key: "+smsKey);

        this.uid = (long) mapMessage.get("uid");
        this.fullName = (String) mapMessage.get("fullName");
        this.avatar = (String) mapMessage.get("avatar");
        this.type = (String) mapMessage.get("type");
        this.created = (String) mapMessage.get("created");

        String content = (String) mapMessage.get("message");

        if (smsKey != null && !smsKey.isEmpty()) {
            String decrypted = AESNormal.decrypt(content, smsKey);
            if (decrypted != null) {
                this.message = decrypted;
            } else {
                this.message = content;
            }
        } else {
            this.message = content;
        }
    }

    public Message(String smsPrivateKey, long uid, String fullName, String message, String type) {
        this.uid = uid;
        this.fullName = fullName;
        this.type = type;

        if (smsPrivateKey != null && !smsPrivateKey.isEmpty()) {
            this.message = AESNormal.encrypt(message, smsPrivateKey);
        } else {
            this.message = message;
        }
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

    public String getMessage(String smsPrivateKey) {
        if (smsPrivateKey != null && !smsPrivateKey.isEmpty()) {
            try {
                return AESNormal.decrypt(message, smsPrivateKey);
            } catch (Exception e) {
                return message;
            }
        } else {
            return message;
        }
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
