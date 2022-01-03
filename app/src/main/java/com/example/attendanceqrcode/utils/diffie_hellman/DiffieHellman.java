package com.example.attendanceqrcode.utils.diffie_hellman;

import com.example.attendanceqrcode.utils.MD5;
import com.example.attendanceqrcode.utils.SharedPreferenceHelper;

import java.math.BigInteger;

public class DiffieHellman {
    private static final BigInteger MOD = new BigInteger("11");
    private static final BigInteger G = new BigInteger("5");

    public String generateKey(int numberBInt) {
        String secretKey = SharedPreferenceHelper.get(SharedPreferenceHelper.secretKey);
        if (secretKey != null) {
            BigInteger expInt = new BigInteger(secretKey.getBytes());

            try {
                BigInteger result = new BigInteger(String.valueOf(numberBInt)).modPow(expInt, MOD);
                return MD5.md5(String.valueOf(result.intValue()));
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        return "";

    }

    public int generatePublicKey() {
        String secretKey = SharedPreferenceHelper.get(SharedPreferenceHelper.secretKey);
        BigInteger privateKey = new BigInteger(secretKey.getBytes());
        BigInteger publicKeyB = G.modPow(privateKey, MOD);
        return publicKeyB.intValue();
    }
}
