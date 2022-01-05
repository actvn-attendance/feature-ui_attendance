package com.example.attendanceqrcode.utils.diffie_hellman;

import android.content.Context;
import android.util.Base64;

import com.example.attendanceqrcode.utils.AES.AESWithSalt;
import com.example.attendanceqrcode.utils.MD5;
import com.example.attendanceqrcode.utils.SharedPreferenceHelper;

import java.math.BigInteger;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DiffieHellman {
    private static final BigInteger MOD = new BigInteger("23");
    private static final BigInteger G = new BigInteger("5");

    private AESWithSalt aesWithSalt;

    public DiffieHellman(Context context) {
        aesWithSalt = new AESWithSalt(context);
        SharedPreferenceHelper.getInstance(context);
    }

    public boolean generatePrivateKey(String userPassword) {
        SecretKey privateKey = aesWithSalt.generateAESKey(userPassword);
        System.out.println(">>> generatePrivateKey: "+privateKey.getEncoded());
        SharedPreferenceHelper.set(
                SharedPreferenceHelper.privateKey,
                Base64.encodeToString(privateKey.getEncoded(), Base64.DEFAULT));
        return true;
    }

    public void removePrivateKey() {
        SharedPreferenceHelper.remove(SharedPreferenceHelper.privateKey);
    }

    private SecretKey getPrivateKey() {
        String rawKey = SharedPreferenceHelper.get(SharedPreferenceHelper.privateKey);
        if (rawKey != null)
            return aesWithSalt.getSecretKeyFromRawKey(rawKey);
        return null;
    }

    public String generateSymmetricKey(long publicB) {
        SecretKey secretKey = getPrivateKey();
        if (secretKey != null) {
            BigInteger privateKey = new BigInteger(secretKey.getEncoded());
            try {
                BigInteger b = new BigInteger(String.valueOf(publicB));
                BigInteger result = new BigInteger(String.valueOf(publicB)).modPow(privateKey, MOD);
                return MD5.md5(String.valueOf(result.intValue()));
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        return "";

    }

    public int generatePublicKey() {
        SecretKey secretKey = getPrivateKey();
        if (secretKey != null) {
            BigInteger privateKey = new BigInteger(secretKey.getEncoded());
            BigInteger publicKeyB = G.modPow(privateKey, MOD);
            return publicKeyB.intValue();
        }
        return -1;
    }
}
