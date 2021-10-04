package com.example.attendanceqrcode.modelapi;

public class Schedule {
    private int schedule_id;
    private String start_lesson;
    private String lesson;
    private String end_lesson;
    private String start_time;
    private String end_time;
    private String datetime;

    private int classroom_id;
    private String classroom_name;
    private String subject_name;
    private String address_name;
    private String teacher_name;

    public Schedule() {
    }

    public Schedule(int schedule_id, String start_lesson, String lesson, String end_lesson, String start_time, String end_time, String datetime, int classroom_id, String classroom_name, String subject_name, String address_name, String teacher_name) {
        this.schedule_id = schedule_id;
        this.start_lesson = start_lesson;
        this.lesson = lesson;
        this.end_lesson = end_lesson;
        this.start_time = start_time;
        this.end_time = end_time;
        this.datetime = datetime;
        this.classroom_id = classroom_id;
        this.classroom_name = classroom_name;
        this.subject_name = subject_name;
        this.address_name = address_name;
        this.teacher_name = teacher_name;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }

    public String getStart_lesson() {
        return start_lesson;
    }

    public void setStart_lesson(String start_lesson) {
        this.start_lesson = start_lesson;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getEnd_lesson() {
        return end_lesson;
    }

    public void setEnd_lesson(String end_lesson) {
        this.end_lesson = end_lesson;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getClassroom_id() {
        return classroom_id;
    }

    public void setClassroom_id(int classroom_id) {
        this.classroom_id = classroom_id;
    }

    public String getClassroom_name() {
        return classroom_name;
    }

    public void setClassroom_name(String classroom_name) {
        this.classroom_name = classroom_name;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getAddress_name() {
        return address_name;
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }
}