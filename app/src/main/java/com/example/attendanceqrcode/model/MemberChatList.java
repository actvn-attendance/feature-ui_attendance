package com.example.attendanceqrcode.model;


import java.util.HashMap;

public class MemberChatList{
    public String fullName = "";
    public long uid;

    public MemberChatList(HashMap mapMessage){
        this.fullName = (String) mapMessage.get("fullName");
        this.uid = (long) mapMessage.get("uid");
    }

    public MemberChatList(){}
}
