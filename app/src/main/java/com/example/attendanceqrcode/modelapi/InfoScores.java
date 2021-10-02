package com.example.attendanceqrcode.modelapi;

import java.util.List;

public class InfoScores {
    private int user_id;
    private int classroom_id;
    private String name_user;
    private String name_classroom;
    private String code_user;
    private List<Scores> scores;

    public InfoScores(int user_id, int classroom_id, String name_user, String name_classroom, String code_user, List<Scores> scores) {
        this.user_id = user_id;
        this.classroom_id = classroom_id;
        this.name_user = name_user;
        this.name_classroom = name_classroom;
        this.code_user = code_user;
        this.scores = scores;
    }

    public InfoScores() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getClassroom_id() {
        return classroom_id;
    }

    public void setClassroom_id(int classroom_id) {
        this.classroom_id = classroom_id;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public String getName_classroom() {
        return name_classroom;
    }

    public void setName_classroom(String name_classroom) {
        this.name_classroom = name_classroom;
    }

    public String getCode_user() {
        return code_user;
    }

    public void setCode_user(String code_user) {
        this.code_user = code_user;
    }

    public List<Scores> getScores() {
        return scores;
    }

    public void setScores(List<Scores> scores) {
        this.scores = scores;
    }
}
