package com.example.attendanceqrcode.modelapi;

public class Subject {

    private int id;
    private String name;
    private int numberOfCredits;
    private int isActive;
    private String studyForm;
    private String nameKD;

    public Subject(int id, String name, int numberOfCredits, int isActive, String studyForm, String nameKD) {
        this.id = id;
        this.name = name;
        this.numberOfCredits = numberOfCredits;
        this.isActive = isActive;
        this.studyForm = studyForm;
        this.nameKD = nameKD;
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

    public int getNumberOfCredits() {
        return numberOfCredits;
    }

    public void setNumberOfCredits(int numberOfCredits) {
        this.numberOfCredits = numberOfCredits;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(String studyForm) {
        this.studyForm = studyForm;
    }

    public String getNameKD() {
        return nameKD;
    }

    public void setNameKD(String nameKD) {
        this.nameKD = nameKD;
    }
}
