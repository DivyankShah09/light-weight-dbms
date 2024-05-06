package com.lightweightdbms.authentication;

import java.math.BigInteger;
import java.security.MessageDigest;

public class PasswordHasher {


    public String hashPassword(String password, String SALT) {
        try {
            String saltedPassword = password + SALT;
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(saltedPassword.getBytes());

            // Convert the byte array to a hexadecimal string
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashText = new StringBuilder(no.toString(16));

            // Add leading zeros to ensure the hash has 32 characters
            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }

            return hashText.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
