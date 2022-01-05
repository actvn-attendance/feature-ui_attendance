package com.example.attendanceqrcode.utils.wrapper;

import android.util.Base64;
import android.util.Log;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import kotlin.text.Charsets;

public class CipherWrapper {
    private final Cipher cipher;

    public static String TRANSFORMATION_ASYMMETRIC = "RSA/ECB/PKCS1Padding";
    public static String TRANSFORMATION_SYMMETRIC = "AES/CBC/PKCS7Padding";
    private String IV_SEPARATOR = "]";


    public CipherWrapper(String transformation) throws NoSuchPaddingException, NoSuchAlgorithmException {
        super();
        this.cipher = Cipher.getInstance(transformation);
    }

    public final String encrypt(String data, SecretKey key)
            throws InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException {
        Log.d("CipherWrapper", "====== encrypt with secret key start=======");
        boolean useInitializationVector = true;
        String result = "";

        this.cipher.init(Cipher.ENCRYPT_MODE, key);

        if (useInitializationVector) {
            byte[] iv = cipher.getIV();
            String ivString = Base64.encodeToString(iv, Base64.DEFAULT);
            result = ivString + IV_SEPARATOR;
        }

        byte[] bytes = cipher.doFinal(data.getBytes());
        result += Base64.encodeToString(bytes, Base64.DEFAULT);

        Log.d("CipherWrapper", "====== DONE =======");
        Log.d("CipherWrapper", result);

        return result;
    }

    public final String decrypt(String data, SecretKey key)
            throws InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException, InvalidAlgorithmParameterException {
        Log.d("CipherWrapper", "====== decrypt with secret key start=======");

        boolean useInitializationVector = true;
        String encodedString;

        if (useInitializationVector) {
            String[] split = data.split(IV_SEPARATOR);
            if (split.length != 2)
                throw new IllegalArgumentException("Passed data is incorrect. There was no IV specified with it.");

            String ivString = split[0];
            encodedString = split[1];
            IvParameterSpec ivSpec = new IvParameterSpec(Base64.decode(ivString, Base64.DEFAULT));
            this.cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        } else {
            encodedString = data;
            this.cipher.init(Cipher.DECRYPT_MODE, key);
        }

        byte[] encryptedData = Base64.decode(encodedString, Base64.DEFAULT);
        byte[] decodedData = this.cipher.doFinal(encryptedData);

        String result =  new String(decodedData, Charsets.UTF_8);
        Log.d("CipherWrapper", "====== DONE =======");
        Log.d("CipherWrapper", result);
        return result;
    }

    public final String wrapKey(Key keyToBeWrapped, Key keyToWrapWith)
            throws InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        Log.d("CipherWrapper", "====== wrapKey by public key =======");
        this.cipher.init(Cipher.WRAP_MODE, keyToWrapWith);
        byte[] decodedData = this.cipher.wrap(keyToBeWrapped);
        return Base64.encodeToString(decodedData, Base64.DEFAULT);
    }

    public final Key unWrapKey(String wrappedKeyData, String algorithm, int wrappedKeyType, Key keyToUnWrapWith)
            throws InvalidKeyException, NoSuchAlgorithmException {
        Log.d("CipherWrapper", "====== unWrapKey by private key =======");
        byte[] encryptedKeyData = Base64.decode(wrappedKeyData, Base64.DEFAULT);
        this.cipher.init(Cipher.UNWRAP_MODE, keyToUnWrapWith);
        return cipher.unwrap(encryptedKeyData, algorithm, wrappedKeyType);
    }

}