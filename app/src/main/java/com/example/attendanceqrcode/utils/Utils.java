package com.example.attendanceqrcode.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.attendanceqrcode.model.QrcodeUser;

import java.sql.Timestamp;

public class Utils {
    public static boolean checkActiveQRCode(QrcodeUser qrcodeUser) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.after(qrcodeUser.getTimeBeganQrcode()) && timestamp.before(qrcodeUser.getQrcodeEndTime());
    }

    public static boolean checkDistanceAttendance(double lat, double longitude, QrcodeUser qrcodeUser) {
        double distance = distanceBetween2Points(
                qrcodeUser.getLatitude(), qrcodeUser.getLongitude(),
                lat, longitude);
        return distance >= 0 && distance < 11;
    }

    public static double distanceBetween2Points(double la1, double lo1, double la2, double lo2) {
        double R = 6371;
        double dLat = (la2 - la1) * (Math.PI / 180);
        double dLon = (lo2 - lo1) * (Math.PI / 180);
        double la1ToRad = la1 * (Math.PI / 180);
        double la2ToRad = la2 * (Math.PI / 180);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(la1ToRad)
                * Math.cos(la2ToRad) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public static String getToken(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("Account", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }

    public static int getUserID(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("Account", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("uid", -1);
    }

    public static String getUserFullName(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("Account", Context.MODE_PRIVATE);
        return sharedPreferences.getString("fullName", "");
    }

    public static String getPersonalID(Activity activity, int otherUserID){
        int uid = getUserID(activity);
        if(uid < otherUserID){
            return uid+"_"+otherUserID;
        }

        return otherUserID+"_"+uid;
    }

    public static boolean checkGPSPermission(Activity activity, Context context) {
        try {
            if (ContextCompat.checkSelfPermission(context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        activity,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        101);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @SuppressLint("HardwareIds")
    public static String getAndroidID(ContentResolver resolver) {
        return Settings.Secure.getString(resolver,
                Settings.Secure.ANDROID_ID);
    }

    public static GpsTracker getLocation(Activity activity) {
        GpsTracker gpsTracker = new GpsTracker(activity);
        if (gpsTracker.canGetLocation()) {
            return gpsTracker;
        } else {
            gpsTracker.showSettingsAlert();
        }
        System.out.println(">>> lat: " + gpsTracker.getLatitude());
        System.out.println(">>> long: " + gpsTracker.getLongitude());
        return null;
    }
}
