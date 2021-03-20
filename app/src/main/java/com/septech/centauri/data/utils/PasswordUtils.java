package com.septech.centauri.data.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtils {
    private char[] password;
    private byte[] salt;
    private final Random random = new SecureRandom();
    private final int ITERATIONS = 65536;
    private final int KEY_LENGTH = 256;

    public PasswordUtils(String password) {
        this.password = password.toCharArray();
        salt = generatePasswordSalt();
    }

    public PasswordUtils(String password, String salt) {
        this.password = password.toCharArray();
        this.salt = salt.getBytes();
    }

    public byte[] generatePasswordSalt() {
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public byte[] hash() {
        PBEKeySpec spec = new PBEKeySpec(this.password, this.salt, ITERATIONS, KEY_LENGTH);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    public boolean isExpectedPassword(byte[] expectedHash) {
        byte[] pwdHash = hash();
        if (pwdHash.length != expectedHash.length) return false;
        for (int i = 0; i < pwdHash.length; i++) {
            if (pwdHash[i] != expectedHash[i]) return false;
        }
        return true;
    }
}
