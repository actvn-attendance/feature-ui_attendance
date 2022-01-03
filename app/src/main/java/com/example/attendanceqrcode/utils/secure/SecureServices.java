package com.example.attendanceqrcode.utils.secure;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.attendanceqrcode.utils.AES.AESWithSalt;
import com.example.attendanceqrcode.utils.wrapper.CipherWrapper;
import com.example.attendanceqrcode.utils.SharedPreferenceHelper;
import com.example.attendanceqrcode.utils.wrapper.KeyStoreWrapper;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class SecureServices {
    static public String MASTER_KEY = "MASTER_KEY";
    static public String ALGORITHM_AES = "AES";

    private Context context;
    private KeyStoreWrapper keyStoreWrapper;

    public SecureServices(Context context)
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        this.context = context;
        SharedPreferenceHelper.getInstance(context);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            keyStoreWrapper = new KeyStoreWrapper(context);
        }
    }

    /// create master key depends API level
    /// API > = 23 -> generate SecretKey by Android Keystore
    /// 18 <= API < 23 -> generate secret default key by KeyGenerator
    //                  -> generate Keypair of AK
    //                  -> wrap secret key by public key -> save to shared prefs
    /// API < 18 -> generate secret key by keyPassword + salt + iv vector
    public void createMasterKey(String keyPassword)
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            NoSuchProviderException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, InvalidKeySpecException {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            createAndroidSymmetricKey();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            createDefaultSymmetricKey();
        } else {
            AESWithSalt aesWithSalt = new AESWithSalt(context);
            aesWithSalt.generateAESKey(keyPassword);
        }
    }

    public void removeMasterKey() throws KeyStoreException {
        keyStoreWrapper.removeAndroidKeyStoreKey(MASTER_KEY);
    }

    public String encrypt(String data)
            throws NoSuchPaddingException, UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException, BadPaddingException,
            IllegalBlockSizeException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return encryptWithAndroidSymmetricKey(data);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return encryptWithDefaultSymmetricKey(data);
        } else {
            AESWithSalt aesWithSalt = new AESWithSalt(context);
            return aesWithSalt.encrypt(data);
        }
    }

    public String decrypt(String data)
            throws NoSuchPaddingException, UnrecoverableKeyException, NoSuchAlgorithmException,
            KeyStoreException, BadPaddingException, IllegalBlockSizeException,
            InvalidKeyException, InvalidAlgorithmParameterException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String result = decryptWithAndroidSymmetricKey(data);
            return result;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return decryptWithDefaultSymmetricKey(data);
        } else {
            AESWithSalt aesWithSalt = new AESWithSalt(context);
            return aesWithSalt.decrypt(data);
        }
    }

    private SecretKey createAndroidSymmetricKey()
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        return keyStoreWrapper.createAndroidKeyStoreSymmetricKey(MASTER_KEY);
    }

    private String encryptWithAndroidSymmetricKey(String data)
            throws UnrecoverableKeyException, NoSuchAlgorithmException,
            KeyStoreException, NoSuchPaddingException, BadPaddingException,
            InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        SecretKey masterKey = keyStoreWrapper.getAndroidKeyStoreSymmetricKey(MASTER_KEY);
        return new CipherWrapper(CipherWrapper.TRANSFORMATION_SYMMETRIC).encrypt(data, masterKey);
    }

    private String decryptWithAndroidSymmetricKey(String data)
            throws UnrecoverableKeyException, NoSuchAlgorithmException,
            KeyStoreException, NoSuchPaddingException, BadPaddingException,
            InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        SecretKey masterKey = keyStoreWrapper.getAndroidKeyStoreSymmetricKey(MASTER_KEY);
        return new CipherWrapper(CipherWrapper.TRANSFORMATION_SYMMETRIC).decrypt(data, masterKey);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void createDefaultSymmetricKey()
            throws NoSuchAlgorithmException, NoSuchProviderException,
            InvalidAlgorithmParameterException, NoSuchPaddingException,
            IllegalBlockSizeException, InvalidKeyException {
        SecretKey symmetricKey = keyStoreWrapper.generateDefaultSymmetricKey();

        KeyPair masterKey = keyStoreWrapper.createAndroidKeyStoreAsymmetricKey(MASTER_KEY);
        String encryptedSymmetricKey = new CipherWrapper(CipherWrapper.TRANSFORMATION_ASYMMETRIC)
                .wrapKey(symmetricKey, masterKey.getPublic());

        SharedPreferenceHelper.set(SharedPreferenceHelper.secretKey, encryptedSymmetricKey);
    }

    private String encryptWithDefaultSymmetricKey(String data)
            throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException,
            NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        KeyPair masterKey = keyStoreWrapper.getAndroidKeyStoreAsymmetricKeyPair(MASTER_KEY);

        String encryptionKey = SharedPreferenceHelper.get(SharedPreferenceHelper.secretKey);

        SecretKey symmetricKey = (SecretKey) new CipherWrapper(CipherWrapper.TRANSFORMATION_ASYMMETRIC)
                .unWrapKey(encryptionKey, ALGORITHM_AES, Cipher.SECRET_KEY, masterKey.getPrivate());
        return new CipherWrapper(CipherWrapper.TRANSFORMATION_SYMMETRIC).encrypt(data, symmetricKey);
    }

    private String decryptWithDefaultSymmetricKey(String data)
            throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException,
            NoSuchPaddingException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        KeyPair masterKey = keyStoreWrapper.getAndroidKeyStoreAsymmetricKeyPair(MASTER_KEY);

        String encryptionKey = SharedPreferenceHelper.get(SharedPreferenceHelper.secretKey);

        SecretKey symmetricKey = (SecretKey) new CipherWrapper(CipherWrapper.TRANSFORMATION_ASYMMETRIC)
                .unWrapKey(encryptionKey, ALGORITHM_AES, Cipher.SECRET_KEY, masterKey.getPrivate());
        return new CipherWrapper(CipherWrapper.TRANSFORMATION_SYMMETRIC).decrypt(data, symmetricKey);
    }

}
