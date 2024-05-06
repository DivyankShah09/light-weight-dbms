package com.lightweightdbms.queryoperation;

import com.lightweightdbms.fileoperation.TextFileWriter;
import com.lightweightdbms.folderoperation.FolderCreater;
import com.lightweightdbms.folderoperation.FolderDeleter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseQueryHandler {
    QueryOperationUtil queryOperationUtil = new QueryOperationUtil();

    public String createDatabaseQuery(String query, String userId) {
        String queryPattern = "^create database ";
        Pattern pattern = Pattern.compile(queryPattern);
        Matcher matcher = pattern.matcher(query);

        if (matcher.find()) {
            String databaseName = query.substring(matcher.end());
            if (!queryOperationUtil.databaseExists("./DataFiles/" + userId + "/" + databaseName)) {
                FolderCreater folderCreater = new FolderCreater();
                folderCreater.createFolder("./DataFiles/" + userId + "/" + databaseName, "database");
                TextFileWriter textFileWriter = new TextFileWriter();
                textFileWriter.writeFile("./DataFiles/" + userId + "/user_logs.txt", "Database " + databaseName + " create");
                return databaseName;
            } else {
                System.out.println("Database Already Exists");
                return null;
            }
        } else {
            System.out.println("Query Syntax Error - Database not created.");
            return null;
        }
    }

    public String useDatabase(String query, String userId) {
        System.out.println("Query : " + query);
        String queryPattern = "use ";
        Pattern pattern = Pattern.compile(queryPattern);
        Matcher matcher = pattern.matcher(query);

        if (matcher.find()) {
            String databaseName = query.substring(matcher.end());
            System.out.println("./DataFiles/" + userId + "/" + databaseName);
            if (!queryOperationUtil.databaseExists("./DataFiles/" + userId + "/" + databaseName)) {
                System.out.println("Database doesnot exists");
                return null;
            } else {
                System.out.println("Database changed to : " + databaseName);
                TextFileWriter textFileWriter = new TextFileWriter();
                textFileWriter.writeFile("./DataFiles/" + userId + "/user_logs.txt", "Database switched to " + databaseName);
                return databaseName;
            }
        } else {
            System.out.println("Query Syntax Error - Database not created.");
            return null;
        }
    }

    public boolean deleteDatabaseQuery(String query, String userId) {
        String queryPattern = "^drop database ";
        Pattern pattern = Pattern.compile(queryPattern);
        Matcher matcher = pattern.matcher(query);

        if (matcher.find()) {
            String databaseName = query.substring(matcher.end());
            if (!queryOperationUtil.databaseExists("./DataFiles/" + userId + "/" + databaseName)) {
                System.out.println("Database doesnot exists");
                return false;
            } else {
                System.out.println("Database dropped");
                FolderDeleter folderDeleter = new FolderDeleter();
                folderDeleter.deleteFolder("./DataFiles/" + userId + "/" + databaseName);
                TextFileWriter textFileWriter = new TextFileWriter();
                textFileWriter.writeFile("./DataFiles/" + userId + "/user_logs.txt", "Deleted Database : " + databaseName);
                System.out.println("Database deleted.");
                return true;
            }
        } else {
            System.out.println("Query Syntax Error - Database not dropped.");
            return false;
        }
    }
}
