package com.lightweightdbms.authentication;

import java.security.SecureRandom;

public class CaptchaGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

    public static String generateCaptcha(int length) {
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            captcha.append(CHARACTERS.charAt(randomIndex));
        }
        return captcha.toString();
    }
}
