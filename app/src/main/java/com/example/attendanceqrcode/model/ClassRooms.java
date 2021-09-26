package com.example.attendanceqrcode.model;

public class ClassRooms {

    private int id;
    private String name;
    private int active;
    private String nameKD;
//    private Subject subject;

    public ClassRooms(int id, String name, int active, String nameKD) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.nameKD = nameKD;
//        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getNameKD() {
        return nameKD;
    }

    public void setNameKD(String nameKD) {
        this.nameKD = nameKD;
    }

//    public Subject getSubject() {
//        return subject;
//    }
//
//    public void setSubject(Subject subject) {
//        this.subject = subject;
//    }
}
