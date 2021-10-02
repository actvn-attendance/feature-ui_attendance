package com.example.attendanceqrcode.modelapi;

public class ResponseAttendance {
    private int id;
    private int userId;
    private int scheduleId;
    private int present;
    private int isActive;
    private String imei;

    public ResponseAttendance(int id, int userId, int scheduleId, int present, int isActive, String imei) {
        this.id = id;
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.present = present;
        this.isActive = isActive;
        this.imei = imei;
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

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getPresent() {
        return present;
    }

    public void setPresent(int present) {
        this.present = present;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    @Override
    public String toString() {
        return "ResponseAttendance{" +
                "id=" + id +
                ", userId=" + userId +
                ", scheduleId=" + scheduleId +
                ", present=" + present +
                ", isActive=" + isActive +
                ", imei='" + imei + '\'' +
                '}';
    }
}
