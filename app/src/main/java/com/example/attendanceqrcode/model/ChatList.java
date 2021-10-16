package com.example.attendanceqrcode.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatList {
    private String id;
    private long time;
    private List<MemberChatList> members = new ArrayList<>();

    public ChatList(HashMap mapMessage){
        System.out.println(mapMessage);
        this.id = (String) mapMessage.get("id");
        this.time = (long) mapMessage.get("time");

        HashMap membersValue = (HashMap) mapMessage.get("members");
        membersValue.forEach((o, o2) -> {
            members.add(new MemberChatList((HashMap)o2));
        });
    }

    public String getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public List<MemberChatList> getMembers() {
        return members;
    }
}
