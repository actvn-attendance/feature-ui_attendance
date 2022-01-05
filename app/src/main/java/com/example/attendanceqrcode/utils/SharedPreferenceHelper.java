package com.example.attendanceqrcode.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.attendanceqrcode.utils.secure.SecureServices;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class SharedPreferenceHelper {
    private static String key = "ACTVN";
    public static String tokenKey = "token";
    public static String usernameKey = "username";
    public static String passwordKey = "password";
    public static String uidKey = "uid";
    public static String fullNameKey = "fullName";
    public static String accountKey = "account";
    public static String notificationsKey = "notifications";

    public static String masterKey = "masterKey";
    public static String privateKey = "privateKey";

    private static SharedPreferences sharedPreferences;
    private static Context context;

    private static volatile SharedPreferenceHelper instance;

    // Private constructor to avoid client applications to use constructor
    public SharedPreferenceHelper(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
    }

    public static SharedPreferenceHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceHelper(context);
        }
        return instance;
    }

    public static <T> void setObject(String key, T object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        try {
            encryptAndSetData(key, json);
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException
                | IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T getObject(String key, Class className) {
        Gson gson = new Gson();
        String json = null;
        try {
            json = getDataEncrypted(key);
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException
                | IOException | NoSuchPaddingException | UnrecoverableKeyException
                | IllegalBlockSizeException | BadPaddingException
                | InvalidAlgorithmParameterException | InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        }
        Log.e("Get object: ", json);
        T obj = gson.fromJson(json, (Type) className);
        return obj;
    }

    public static <T> void setList(String key, List<T> list)
            throws IOException, CertificateException, NoSuchAlgorithmException,KeyStoreException {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        encryptAndSetData(key, json);
    }

    public static <T> List<T> getList(String key) {
        List<T> objects = null;
        String serializedObject = get(key);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<T>>() {
            }.getType();
            objects = gson.fromJson(serializedObject, type);
        }
        return objects;
    }

    public static void set(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public static void encryptAndSetData(String key, String value)
            throws CertificateException, NoSuchAlgorithmException,
            KeyStoreException, IOException {
        String dataEncrypted = new SecureServices(context).encrypt(value);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, dataEncrypted);
        editor.apply();
    }

    public static String getDataEncrypted(String key)
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException,
            IOException, NoSuchPaddingException, UnrecoverableEntryException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException,
            InvalidKeyException {
        String value = sharedPreferences.getString(key, null);

        if (value != null) {
            return new SecureServices(context).decrypt(value);
        }
        return "";
    }


    public static String get(String key) {
        return sharedPreferences.getString(key, null);
    }

    public static int getInt(String key) {
        String result = sharedPreferences.getString(key, "-1");
        return Integer.parseInt(result);
    }

    public static void clearData() {
        sharedPreferences.edit().clear().apply();
    }

    public static void remove(String key) {
        sharedPreferences.edit().remove(key).apply();
    }
}
