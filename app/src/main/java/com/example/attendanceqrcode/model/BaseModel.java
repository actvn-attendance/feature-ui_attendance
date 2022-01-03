package com.example.attendanceqrcode.model;

import com.google.gson.Gson;

import java.io.Serializable;

public class BaseModel implements Serializable {
    public String toJsonString(){
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
}
