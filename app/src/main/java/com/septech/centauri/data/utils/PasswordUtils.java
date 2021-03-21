package com.septech.centauri.data.utils;

import android.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

public class PasswordUtils {
    private final Random RANDOM = new SecureRandom();
    private final int ITERATIONS = 10000;
    private final int KEY_LENGTH = 256;

    private char[] password;
    private byte[] salt;

    /**
     * static utility class
     */
    public PasswordUtils(String password) {
        this.password = password.toCharArray();
        getNextSalt();
    }

    public PasswordUtils(String password, String passwordSalt) {
        this.password = password.toCharArray();
        this.salt = Base64.decode(passwordSalt, Base64.NO_WRAP);
    }

    /**
     * Returns a random salt to be used to hash a password.
     *
     * @return a 16 bytes random salt
     */
    public void getNextSalt() {
        this.salt = new byte[16];
        RANDOM.nextBytes(salt);
    }

    public String getSalt() {
        return Base64.encodeToString(salt, Base64.NO_WRAP);
    }

    public String hash() {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS,
                KEY_LENGTH);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return Base64.encodeToString(skf.generateSecret(spec).getEncoded(), Base64.NO_WRAP);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    public boolean isExpectedPassword(char[] expectedHash) {
        char[] pwdHash = hash().toCharArray();
        if (pwdHash.length != expectedHash.length) return false;
        for (int i = 0; i < pwdHash.length; i++) {
            if (pwdHash[i] != expectedHash[i]) return false;
        }
        return true;
    }
}