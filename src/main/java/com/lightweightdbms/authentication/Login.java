package com.lightweightdbms.authentication;

import com.lightweightdbms.fileoperation.TextFileReader;

import java.util.ArrayList;
import java.util.Scanner;

public class Login {
    private static final String SALT = "417b61eea1f7408f79a8fc52ee8bfafd";
    private String userId;
    private String password;
    private String captcha;
    private ArrayList<String> loginCredentialsList = new ArrayList<>();
    private String[] loginCredentials;
    private String loginCredentialsFilePath = "./DataFiles/LoginCredentials.txt";

    public static boolean verifyPassword(String userPassword, String salt, String storedHashedPassword) {
        PasswordHasher passwordHasher = new PasswordHasher();
        String hashedPassword = passwordHasher.hashPassword(userPassword, salt);
        return storedHashedPassword.equals(hashedPassword);
    }

    public String userLogin(String userId, String password) {
        String loginStatus = "";

        TextFileReader textFileReader = new TextFileReader();
        loginCredentialsList = textFileReader.readFile(loginCredentialsFilePath);

        for (String s : loginCredentialsList) {
            loginCredentials = s.split(":");
            if (userId.equals(loginCredentials[0])) {
                if (verifyPassword(password, SALT, loginCredentials[1])) {
                    CaptchaGenerator captchaGenerator = new CaptchaGenerator();
                    captcha = captchaGenerator.generateCaptcha(6);

                    System.out.println("Please enter the following CAPTCHA: " + captcha);
                    Scanner scanner = new Scanner(System.in);
                    String userResponse = scanner.nextLine();

                    if (userResponse.equals(captcha)) {
                        loginStatus = "Login Successful";
                        System.out.println("Login Successful !!");
                        break;
                    }
                    break;
                }
            } else {
                loginStatus = "login failed";
            }
        }
        return loginStatus;
    }
}