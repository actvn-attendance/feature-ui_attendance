package com.example.attendanceqrcode.model;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;


public class QrcodeUser {
    private Long scheduleId;
    private Long qrCodeId;
    private double latitude; // kinh độ
    private double longitude; // vĩ độ
    private String imei;
    private Date timeBeganQrcode;
    private Date qrcodeEndTime;

    public QrcodeUser(JSONObject obj) throws JSONException {
        this.scheduleId = obj.getLong("scheduleId");
        this.qrCodeId = obj.getLong("qrCodeId");
        this.latitude = obj.getDouble("latitude");
        this.longitude = obj.getDouble("longitude");
        this.timeBeganQrcode = new Date(obj.getLong("timeBeganQrcode"));
        this.qrcodeEndTime = new Date(obj.getLong("qrcodeEndTime"));
    }

    public QrcodeUser(Long scheduleId, Long qrCodeId, float latitude, float longitude, String imei, Date timeBeganQrcode, Date qrcodeEndTime) {
        this.scheduleId = scheduleId;
        this.qrCodeId = qrCodeId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imei = imei;
        this.timeBeganQrcode = timeBeganQrcode;
        this.qrcodeEndTime = qrcodeEndTime;
    }

    public String toRequestJSON(String imei, double latitude, double longitude) {
        setImei(imei);
        setLatitude(latitude);
        setLongitude(longitude);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("scheduleId", getScheduleId());
            jsonObject.put("qrCodeId", getQrCodeId());
            jsonObject.put("latitude", getLatitude());
            jsonObject.put("longitude", getLongitude());
            jsonObject.put("androidId", getImei());
            System.out.println(jsonObject.toString());
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }

    }

    public Date getTimeBeganQrcode() {
        return timeBeganQrcode;
    }

    public void setTimeBeganQrcode(Date timeBeganQrcode) {
        this.timeBeganQrcode = timeBeganQrcode;
    }

    public Date getQrcodeEndTime() {
        return qrcodeEndTime;
    }

    public void setQrcodeEndTime(Date qrcodeEndTime) {
        this.qrcodeEndTime = qrcodeEndTime;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getQrCodeId() {
        return qrCodeId;
    }

    public void setQrCodeId(Long qrCodeId) {
        this.qrCodeId = qrCodeId;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }


    @Override
    public String toString() {
        return "QrcodeUser{" +
                "scheduleId=" + scheduleId +
                ", qrCodeId=" + qrCodeId +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", imei='" + imei + '\'' +
                ", timeBeganQrcode=" + timeBeganQrcode +
                ", qrcodeEndTime=" + qrcodeEndTime +
                '}';
    }
}
