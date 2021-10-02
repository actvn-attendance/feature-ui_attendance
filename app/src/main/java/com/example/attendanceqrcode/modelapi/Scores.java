package com.example.attendanceqrcode.modelapi;

public class Scores {

    private int id;
    private int userId;
    private int classroomId;
    private int type;
    private double point;

    public Scores(int id, int userId, int classroomId, int type, double point) {
        this.id = id;
        this.userId = userId;
        this.classroomId = classroomId;
        this.type = type;
        this.point = point;
    }

    public Scores() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }
}
