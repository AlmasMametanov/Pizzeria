package com.epam.pizzeria.util.encodePassword;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encoder {
    public static String encodePassword(String passwordToEncode) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md5.digest(passwordToEncode.getBytes());
            StringBuilder hashPass = new StringBuilder();
            for (byte b : messageDigest) {
                hashPass.append(String.format("%02X", b));
            }
            return hashPass.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
