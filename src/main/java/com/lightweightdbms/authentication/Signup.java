package com.lightweightdbms.authentication;

import com.lightweightdbms.fileoperation.TextFileCreater;
import com.lightweightdbms.fileoperation.TextFileWriter;
import com.lightweightdbms.folderoperation.FolderCreater;

public class Signup {
    private static final String SALT = "417b61eea1f7408f79a8fc52ee8bfafd";
    private String userId;
    private String password;
    private String loginCredentialsFilePath = "./DataFiles/LoginCredentials.txt";

    public String userSignup(String userId, String password) {
        try {
            TextFileWriter textFileWriter = new TextFileWriter();
            PasswordHasher passwordHasher = new PasswordHasher();
            String userCredential = "\n" + userId + ":" + passwordHasher.hashPassword(password, SALT);
            textFileWriter.appendFile(loginCredentialsFilePath, userCredential);
            FolderCreater folderCreater = new FolderCreater();
            folderCreater.createFolder("./DataFiles/" + userId, "User");
            System.out.println("User Registration Successfull.");

            TextFileCreater textFileCreater = new TextFileCreater();
            textFileCreater.createFile("./DataFiles/" + userId + "/user_logs.txt");
            String logFileData = "SignUp successfull. and user created.\n" + "User Logs file created.";
            textFileWriter.writeFile("./DataFiles/" + userId + "user_logs.txt", logFileData);

            return "User registered successfully.";
        } catch (Exception e) {
            System.out.println("Registration failed.");
            return "Registration failed.";
        }
    }
}
