package com.lightweightdbms;

import com.lightweightdbms.authentication.Login;
import com.lightweightdbms.authentication.Signup;
import com.lightweightdbms.queryoperation.QueryOperationUtil;
import com.lightweightdbms.transactionmanagement.TransactionQueryHandler;

import java.util.Scanner;

public class LightWeightDbmsApplication {
    static String databaseName = null;
    static String userId;
    static String password;
    static String query;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Do you want to login/signup: \n" +
                    "1. Signup\n" +
                    "2. Login\n" +
                    "Choose an option (1/2)");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("Enter username : ");
                String newUsername = scanner.nextLine();

                System.out.println("Enter password : ");
                String newPassword = scanner.nextLine();

                Signup signup = new Signup();
                System.out.println(signup.userSignup(newUsername, newPassword));

                System.out.println("Please Login now");
                System.out.println("Enter username : ");
                userId = scanner.nextLine();

                System.out.println("Enter password : ");
                password = scanner.nextLine();

                Login login = new Login();
                String loginResult = login.userLogin(userId, password);
                System.out.println(loginResult);
                if (!loginResult.equals("login failed")) {
                    break;
                }
            } else {
                System.out.println("Please Login now ");
                System.out.println("Enter username : ");
                userId = scanner.nextLine();

                System.out.println("Enter password : ");
                password = scanner.nextLine();

                Login login = new Login();
                String loginResult = login.userLogin(userId, password);
                System.out.println(loginResult);
                if (!loginResult.equals("login failed")) {
                    break;
                }
            }
        }

        while (true) {
            System.out.println("Database Name : " + databaseName);
            System.out.println("Enter SQL Query or enter quit to exit :");

            StringBuilder inputLines = new StringBuilder();
            String line;

            while (true) {
                line = scanner.nextLine();
                if (line.equalsIgnoreCase("")) {
                    break;
                }
                inputLines.append(line).append("\n");
            }
            query = inputLines.toString().toLowerCase();
            System.out.println("query : " + query);

            if (query.contains("quit")) {
                System.out.println("Exiting...");
            } else if (query.contains("begin") || query.contains("end")) {
                databaseName = "mydb";
                if (databaseName != null) {
                    TransactionQueryHandler transactionQueryHandler = new TransactionQueryHandler();
                    transactionQueryHandler.transactionHandler(query, userId, databaseName);
                } else {
                    System.out.println("please select the database !!");
                }
            } else if (query.contains("database") || query.contains("use")) {
                QueryOperationUtil queryOperationUtil = new QueryOperationUtil();
                databaseName = queryOperationUtil.queryMapper(query, userId, databaseName);
            } else {
                if (databaseName != null) {
                    QueryOperationUtil queryOperationUtil = new QueryOperationUtil();
                    queryOperationUtil.queryMapper(query, userId, databaseName);
                } else {
                    System.out.println("please select the database !!");
                }
            }
        }
    }
}