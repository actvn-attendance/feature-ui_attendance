package com.example.attendanceqrcode.utils.AES;

import android.content.Context;
import android.util.Base64;

import com.example.attendanceqrcode.utils.SharedPreferenceHelper;
import com.example.attendanceqrcode.utils.wrapper.KeyStoreWrapper;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESWithSalt {
    private String PBE_ALGORITHM = "PBEWithSHA256And256BitAES-CBC-BC";
    private String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private int NUM_OF_ITERATIONS = 1000;
    private int KEY_SIZE = 256;
    private byte[] salt = "ababababababababababab".getBytes();
    private byte[] iv = "1234567890abcdef".getBytes();

    public AESWithSalt(Context context) {
        SharedPreferenceHelper.getInstance(context);
    }

    /// generate aes key with input text + salt
    public SecretKey generateAESKey(String input) throws InvalidKeySpecException, NoSuchAlgorithmException {
        PBEKeySpec pbeKeySpec = new PBEKeySpec(input.toCharArray(),
                salt, NUM_OF_ITERATIONS, KEY_SIZE);
        SecretKeyFactory keyFactory;
        try {
            keyFactory = SecretKeyFactory.getInstance(PBE_ALGORITHM);
            SecretKey tempKey = keyFactory.generateSecret(pbeKeySpec);
            SecretKey secretKey = new SecretKeySpec(tempKey.getEncoded(), "AES");
            SharedPreferenceHelper.set(
                    SharedPreferenceHelper.secretKey,
                    Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT));
            return secretKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw e;
        }
    }

    public String encrypt(String clearText)
            throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher encCipher;
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        try {
            String rawData = SharedPreferenceHelper.get(SharedPreferenceHelper.secretKey);
            byte[] keyEncoded = Base64.decode(rawData, Base64.DEFAULT);
            SecretKey secretKey = new SecretKeySpec(keyEncoded, "AES");

            encCipher = Cipher.getInstance(CIPHER_ALGORITHM);
            encCipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            return new String(encCipher.doFinal(clearText.getBytes()));
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | BadPaddingException
                | IllegalBlockSizeException
                | InvalidAlgorithmParameterException
                | InvalidKeyException e) {
            throw e;
        }
    }

    public String decrypt(String encryptedData)
            throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        try {
            String rawData = SharedPreferenceHelper.get(SharedPreferenceHelper.secretKey);
            byte[] keyEncoded = Base64.decode(rawData, Base64.DEFAULT);
            SecretKey secretKey = new SecretKeySpec(keyEncoded, "AES");

            Cipher decCipher = Cipher.getInstance(CIPHER_ALGORITHM);
            decCipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

            byte[] data = Base64.decode(encryptedData, Base64.DEFAULT);

            return new String(decCipher.doFinal(data));
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | BadPaddingException
                | IllegalBlockSizeException
                | InvalidAlgorithmParameterException
                | InvalidKeyException e) {
            throw e;
        }
    }
}
