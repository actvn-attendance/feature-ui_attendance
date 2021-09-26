package com.example.attendanceqrcode.model;

public class GroupChat {;

    private String nameGroup;
    private String description;
    private int imageGroup;

    public GroupChat() {
    }

    public GroupChat(String nameGroup, String description, int imageGroup) {
        this.nameGroup = nameGroup;
        this.description = description;
        this.imageGroup = imageGroup;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageGroup() {
        return imageGroup;
    }

    public void setImageGroup(int imageGroup) {
        this.imageGroup = imageGroup;
    }
}
