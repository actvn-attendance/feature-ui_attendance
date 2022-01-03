package com.example.attendanceqrcode.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.attendanceqrcode.model.QrcodeUser;
import com.example.attendanceqrcode.modelapi.Account;
import com.example.attendanceqrcode.utils.secure.SecureServices;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.sql.Timestamp;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

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

    public static String getToken(Context context) {
        SharedPreferenceHelper.getInstance(context);
        try {
            return SharedPreferenceHelper.getDataEncrypted(SharedPreferenceHelper.tokenKey);
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getUserID(Context context) {
        SharedPreferenceHelper.getInstance(context);
        return SharedPreferenceHelper.getInt(SharedPreferenceHelper.uidKey);
    }

    public static String getUserFullName(Context context) {
        SharedPreferenceHelper.getInstance(context);
        try {
            return SharedPreferenceHelper.getDataEncrypted(SharedPreferenceHelper.fullNameKey);
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getPersonalID(Activity activity, int otherUserID) {
        int uid = getUserID(activity);
        if (uid < otherUserID) {
            return uid + "_" + otherUserID;
        }

        return otherUserID + "_" + uid;
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
        return null;
    }

    public static Account getLocalAccount(Context context) {
        SharedPreferenceHelper.getInstance(context);
        Gson gson = new Gson();
        String json = null;
        try {
            json = SharedPreferenceHelper.getDataEncrypted(SharedPreferenceHelper.accountKey);
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        if (json != null && !json.isEmpty()) {
            return gson.fromJson(json, Account.class);
        }
        return null;
    }

    public static void logOut(Activity activity) {
        int uid = getUserID(activity);
        FirebaseHelper.unsubscribeTopic(String.valueOf(uid));
        clearUserData(activity);
    }

    public static void clearUserData(Context context) {
        SharedPreferenceHelper.getInstance(context);
        SharedPreferenceHelper.clearData();
        try {
            new SecureServices(context).removeMasterKey();
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // For 29 api or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (capabilities == null) return false;

            return
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);

        }
        // For below 29 api

        return connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
