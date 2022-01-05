package com.example.attendanceqrcode.utils.wrapper;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Calendar;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.security.auth.x500.X500Principal;

/**
 * This class wraps [KeyStore] class apis with some additional possibilities.
 */
public class KeyStoreWrapper {
    private final Context context;
    private final KeyStore keyStore;

    public KeyStoreWrapper(Context context)
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        this.context = context;
        keyStore = createAndroidKeyStore();
    }

    public KeyStore createAndroidKeyStore()
            throws KeyStoreException, CertificateException,
            NoSuchAlgorithmException, IOException {
        Log.d("KeyStoreWrapper", "====== createAndroidKeyStore() start =======");

        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        Log.d("KeyStoreWrapper", "====== createAndroidKeyStore() done =======");
        return keyStore;
    }

    // implement for API level > 23
    // alias (bí danh)
    @TargetApi(23)
    public SecretKey createAndroidKeyStoreSymmetricKey(String alias)
            throws NoSuchProviderException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException {

        Log.d("KeyStoreWrapper", "====== API level > 23 =======");
        Log.d("KeyStoreWrapper", "====== createAndroidKeyStoreSymmetricKey() start =======");
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(
                alias,
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
        keyGenerator.init(builder.build());

        Log.d("KeyStoreWrapper", "====== createAndroidKeyStoreSymmetricKey() done =======");
        return keyGenerator.generateKey();
    }


    /**
     * @return symmetric key from Android Key Store or null if any key with given alias exists
     */
    public SecretKey getAndroidKeyStoreSymmetricKey(String alias) {
        Log.d("KeyStoreWrapper", "====== API level > 23 =======");
        Log.d("KeyStoreWrapper", "====== getAndroidKeyStoreSymmetricKey() start =======");

        SecretKey secretKey = null;
        try {
            secretKey = (SecretKey) ((KeyStore.SecretKeyEntry) keyStore
                    .getEntry(alias, null)).getSecretKey();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Log.d("KeyStoreWrapper", "====== getAndroidKeyStoreSymmetricKey() done =======");
        return secretKey;
    }

    public SecretKey generateDefaultSymmetricKey() throws NoSuchAlgorithmException {
        Log.d("KeyStoreWrapper", "====== generateDefaultSymmetricKey() start =======");
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey secretKey = keyGenerator.generateKey();
        Log.d("KeyStoreWrapper", "====== generateDefaultSymmetricKey() done =======");
        return secretKey;
    }

    /**
     * @return asymmetric keypair from Android Key Store or null if any key with given alias exists
     */
    public KeyPair getAndroidKeyStoreAsymmetricKeyPair(String alias)
            throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        Log.d("KeyStoreWrapper", "====== getAndroidKeyStoreAsymmetricKeyPair() start =======");
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, null);
        PublicKey publicKey = keyStore.getCertificate(alias).getPublicKey();

        if (privateKey != null && publicKey != null) {
            Log.d("KeyStoreWrapper", "====== getAndroidKeyStoreAsymmetricKeyPair() done =======");
            return new KeyPair(publicKey, privateKey);
        }
        return null;
    }


    public void removeAndroidKeyStoreKey(String alias) throws KeyStoreException {
        Log.d("KeyStoreWrapper", "====== removeAndroidKeyStoreKey() =======");
        keyStore.deleteEntry(alias);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public KeyPair createAndroidKeyStoreAsymmetricKey(String alias)
            throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        Log.d("KeyStoreWrapper", "====== createAndroidKeyStoreAsymmetricKey() start =======");
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("KeyStoreWrapper", "====== API >= 23 =======");
            Log.d("KeyStoreWrapper", "====== initGeneratorWithKeyGenParameterSpec() =======");
            initGeneratorWithKeyGenParameterSpec(generator, alias);
        } else {
            Log.d("KeyStoreWrapper", "====== 18 =< API < 23 =======");
            Log.d("KeyStoreWrapper", "====== initGeneratorWithKeyPairGeneratorSpec() =======");
            initGeneratorWithKeyPairGeneratorSpec(generator, alias);
        }

        KeyPair keyPair = generator.generateKeyPair();
        Log.d("KeyStoreWrapper", "====== createAndroidKeyStoreAsymmetricKey() done =======");

        return keyPair;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initGeneratorWithKeyPairGeneratorSpec(KeyPairGenerator generator, String alias)
            throws InvalidAlgorithmParameterException {
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.YEAR, 20);

        KeyPairGeneratorSpec.Builder builder = new KeyPairGeneratorSpec.Builder(context)
                .setAlias(alias)
                .setSerialNumber(BigInteger.ONE)
                .setSubject(new X500Principal("CN=${alias} CA Certificate"))
                .setStartDate(startDate.getTime())
                .setEndDate(endDate.getTime());

        generator.initialize(builder.build());
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initGeneratorWithKeyGenParameterSpec(KeyPairGenerator generator, String alias)
            throws InvalidAlgorithmParameterException {
        KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(
                alias,
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1);
        generator.initialize(builder.build());
    }

}
