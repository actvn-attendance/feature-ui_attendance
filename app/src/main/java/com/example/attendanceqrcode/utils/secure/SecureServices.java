package com.example.attendanceqrcode.utils.secure;

import android.content.Context;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.attendanceqrcode.utils.AES.AESWithSalt;
import com.example.attendanceqrcode.utils.wrapper.CipherWrapper;
import com.example.attendanceqrcode.utils.SharedPreferenceHelper;
import com.example.attendanceqrcode.utils.wrapper.KeyStoreWrapper;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class SecureServices {
    static public String MASTER_KEY = "MASTER_KEY";
    static public String ALGORITHM_AES = "AES";

    private KeyStoreWrapper keyStoreWrapper;
    private AESWithSalt aesWithSalt;

    public SecureServices(Context context)
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        SharedPreferenceHelper.getInstance(context);
        aesWithSalt = new AESWithSalt(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            keyStoreWrapper = new KeyStoreWrapper(context);
        }
    }

    //     create master key depends API level
    //     API > = 23 -> generate SecretKey by Android Keystore
    //     18 <= API < 23 -> generate secret default key by KeyGenerator
    //                    -> generate Keypair of AK
    //                    -> wrap secret key by public key -> save to shared prefs
    //     API < 18 -> generate secret key by keyPassword + salt + iv vector
    public void createMasterKey(String userPassword)
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            createAndroidSymmetricKey();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            createDefaultSymmetricKey();
        } else {
            SecretKey secretKey = aesWithSalt.generateAESKey(userPassword);
            SharedPreferenceHelper.set(
                    SharedPreferenceHelper.masterKey,
                    Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT));
        }
    }

    /**
     * remove master key in android key store
     */
    public boolean removeMasterKey() throws KeyStoreException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            try {
                keyStoreWrapper.removeAndroidKeyStoreKey(MASTER_KEY);
                return true;
            } catch (KeyStoreException e) {
                return false;
            }
        } else {
            SharedPreferenceHelper.remove(
                    SharedPreferenceHelper.masterKey);
            return true;
        }
    }

    /**
     * This method is encrypt data from clear data
     *
     * @param data
     * @return NOTE: API < 18 use custom AES key it is generated and saved in shared prefs
     */
    public String encrypt(String data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return encryptWithAndroidSymmetricKey(data);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return encryptWithDefaultSymmetricKey(data);
        } else {
            String rawKey = SharedPreferenceHelper.get(SharedPreferenceHelper.masterKey);
            return aesWithSalt.encrypt(rawKey, data);
        }
    }

    /**
     * This method is decrypt data from encrypted data
     *
     * @param data
     * @return NOTE: API < 18 use custom AES key it is generated and saved in shared prefs
     */
    public String decrypt(String data) {
        Log.d("SecureSevices", data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return decryptWithAndroidSymmetricKey(data);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return decryptWithDefaultSymmetricKey(data);
        } else {
            String rawKey = SharedPreferenceHelper.get(SharedPreferenceHelper.masterKey);
            return aesWithSalt.decrypt(rawKey, data);
        }
    }

    /**
     * BELOW INCLUDE METHODS FOR ENCRYPT LOCAL DATA HAS API >= 23 =========
     * IT SUPPORT Symmetric key (AES) IN ANDROID KEYSTORE
     */

    /**
     * This create Android Symmetric Key by Android Keystore
     */
    private void createAndroidSymmetricKey()
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        keyStoreWrapper.createAndroidKeyStoreSymmetricKey(MASTER_KEY);
    }

    /**
     * @param data is clear string
     * @return encrypted string
     * This encrypt clear data to encrypted data by android symmetric key
     * Step 1: get symmetric key (AES) from Android key store
     * Step 2: use Symmetric Key to encrypt data
     */
    private String encryptWithAndroidSymmetricKey(String data) {
        try {
            SecretKey symmetricKey = keyStoreWrapper.getAndroidKeyStoreSymmetricKey(MASTER_KEY);
            return new CipherWrapper(CipherWrapper.TRANSFORMATION_SYMMETRIC).encrypt(data, symmetricKey);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException | BadPaddingException |
                InvalidKeyException | IllegalBlockSizeException e) {
            return null;
        }
    }

    /**
     * @param data is encrypted string
     * @return clear text
     * This decrypt encrypted data to clear data by android symmetric key
     * Step 1: get symmetric key (AES) from Android key store
     * Step 2: use Symmetric Key to decrypt data
     */
    private String decryptWithAndroidSymmetricKey(String data) {
        try {
            SecretKey symmetricKey = keyStoreWrapper.getAndroidKeyStoreSymmetricKey(MASTER_KEY);
            return new CipherWrapper(CipherWrapper.TRANSFORMATION_SYMMETRIC).decrypt(data, symmetricKey);
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | BadPaddingException
                | InvalidKeyException
                | IllegalBlockSizeException
                | InvalidAlgorithmParameterException e) {
        }
        return null;
    }


    /**
     *  ============== BELOW INCLUDE METHODS FOR ENCRYPT LOCAL DATA FOR 18 <= API < 23 =========
     */

    /**
     * This method for 18 <= API < 23
     * This create default symmetric key from android keystore and save it to shared prefs
     * And then we can use default symmetric to encrypt our data
     * Step 1: generate Default Symmetric Key by KeyGenerator
     * Step 2: create Android KeyStore Asymmetric Key (private/public key)
     * Step 3: wrap symmetricKey by public key -> encrypted Symmetric Key
     * Step 4: save encrypted Symmetric Key to shared prefs
     */
    private void createDefaultSymmetricKey() {
        try {
            SecretKey symmetricKey = keyStoreWrapper.generateDefaultSymmetricKey();
            KeyPair masterKey = null;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                masterKey = keyStoreWrapper.createAndroidKeyStoreAsymmetricKey(MASTER_KEY);
            }

            String encryptedSymmetricKey = new CipherWrapper(CipherWrapper.TRANSFORMATION_ASYMMETRIC)
                    .wrapKey(symmetricKey, masterKey.getPublic());

            SharedPreferenceHelper.set(SharedPreferenceHelper.masterKey, encryptedSymmetricKey);
        } catch (NoSuchAlgorithmException | NoSuchProviderException |
                InvalidAlgorithmParameterException | NoSuchPaddingException |
                IllegalBlockSizeException | InvalidKeyException e) {
        }
    }


    /**
     * @param data is clear string
     * @return encrypted string
     * * This encrypt clear data to encrypted data by default symmetric key
     * Step 1: get master key (pub/private) from Android key store
     * Step 2: get `wrapped string` of symmetric key from shared prefs
     * Step 3: pass private key to cipher to unwrap `encrypted string` to SymmetricKey
     * Step 4: use SymmetricKey to encrypt data
     */
    private String encryptWithDefaultSymmetricKey(String data) {
        try {
            KeyPair masterKey = keyStoreWrapper.getAndroidKeyStoreAsymmetricKeyPair(MASTER_KEY);
            String encryptionKey = SharedPreferenceHelper.get(SharedPreferenceHelper.masterKey);

            SecretKey symmetricKey = (SecretKey) new CipherWrapper(CipherWrapper.TRANSFORMATION_ASYMMETRIC)
                    .unWrapKey(encryptionKey, ALGORITHM_AES, Cipher.SECRET_KEY, masterKey.getPrivate());
            return new CipherWrapper(CipherWrapper.TRANSFORMATION_SYMMETRIC).encrypt(data, symmetricKey);
        } catch (UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException |
                NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            return null;
        }
    }

    /**
     * @param data is encrypted string
     * @return clear text
     * This decrypt encrypted data to clear data by default symmetric key
     * Step 1: get master key (pub/private) from Android key store
     * Step 2: get `wrapped string` of symmetric key from shared prefs
     * Step 3: pass private key to cipher to unwrap `encrypted string` to SymmetricKey
     * Step 4: use SymmetricKey to decrypt data
     */
    private String decryptWithDefaultSymmetricKey(String data) {
        try {
            KeyPair masterKey = keyStoreWrapper.getAndroidKeyStoreAsymmetricKeyPair(MASTER_KEY);

            String encryptionKey = SharedPreferenceHelper.get(SharedPreferenceHelper.masterKey);

            SecretKey symmetricKey = (SecretKey) new CipherWrapper(CipherWrapper.TRANSFORMATION_ASYMMETRIC)
                    .unWrapKey(encryptionKey, ALGORITHM_AES, Cipher.SECRET_KEY, masterKey.getPrivate());
            return new CipherWrapper(CipherWrapper.TRANSFORMATION_SYMMETRIC).decrypt(data, symmetricKey);
        } catch (UnrecoverableKeyException
                | NoSuchAlgorithmException
                | KeyStoreException
                | NoSuchPaddingException
                | BadPaddingException
                | InvalidKeyException
                | IllegalBlockSizeException
                | InvalidAlgorithmParameterException e) {
            return null;
        }
    }

}
