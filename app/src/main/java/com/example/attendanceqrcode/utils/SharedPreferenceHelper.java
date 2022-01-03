package com.example.attendanceqrcode.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.attendanceqrcode.utils.secure.SecureServices;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class SharedPreferenceHelper {
    private static String key = "Account";
    public static String tokenKey = "token";
    public static String usernameKey = "username";
    public static String passwordKey = "password";
    public static String uidKey = "uid";
    public static String fullNameKey = "fullName";
    public static String accountKey = "account";
    public static String notificationsKey = "notifications";

    public static String secretKey = "secretKey";

    private static SharedPreferences sharedPreferences;
    private static Context context;

    private static volatile SharedPreferenceHelper instance;

    // Private constructor to avoid client applications to use constructor
    private SharedPreferenceHelper(Context context) {
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
                | IOException | NoSuchPaddingException | UnrecoverableKeyException
                | IllegalBlockSizeException | BadPaddingException
                | InvalidAlgorithmParameterException | InvalidKeyException e) {
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
        }
        System.out.println(json);
        T obj = gson.fromJson(json, (Type) className);
        return obj;
    }

    public static <T> void setList(String key, List<T> list)
            throws IOException, CertificateException, NoSuchAlgorithmException,
            UnrecoverableKeyException, InvalidKeyException, InvalidAlgorithmParameterException,
            NoSuchPaddingException, BadPaddingException, KeyStoreException, IllegalBlockSizeException {
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

    public static void encryptAndSetData(String key, String value)
            throws CertificateException, NoSuchAlgorithmException,
            KeyStoreException, IOException, NoSuchPaddingException, UnrecoverableKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException,
            InvalidKeyException {
        String dataEncrypted = new SecureServices(context).encrypt(value);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, dataEncrypted);
        editor.apply();
    }

    public static String getDataEncrypted(String key)
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException,
            IOException, NoSuchPaddingException, UnrecoverableKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException,
            InvalidKeyException {
        String value = sharedPreferences.getString(key, null);

        if (value != null) {
            String result = new SecureServices(context).decrypt(value);
            return result;
        }
        return "";
    }


    public static String get(String key) {
        return sharedPreferences.getString(key, null);
    }

    public static int getInt(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public static void clearData() {
        sharedPreferences.edit().clear().apply();
    }

    public static void remove(String key) {
        sharedPreferences.edit().remove(key).apply();
    }
}
