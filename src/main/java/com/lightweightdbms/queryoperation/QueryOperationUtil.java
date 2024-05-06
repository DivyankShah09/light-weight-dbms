package com.lightweightdbms.queryoperation;

import java.io.File;
import java.util.regex.Pattern;

public class QueryOperationUtil {
    String createDatabasePattern = "^create database ";
    String useDatabasePattern = "^use ";
    String dropDatabasePattern = "^drop database ";
    String createTablePattern = "^create table ";
    String dropTablePattern = "^drop table ";
    String insertQueryPattern = "^insert ";
    String selectQueryPattern = "^select ";
    String deleteQueryPattern = "^delete from ";
    String updateQueryPattern = "^update ";

    public boolean tableExists(String tablePath) {
        File tableFile = new File(tablePath);

        if (tableFile.exists() && tableFile.isFile()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean databaseExists(String databasePath) {
        File databaseDirectory = new File(databasePath);

        if (databaseDirectory.exists() && databaseDirectory.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }

    public String queryMapper(String query, String userId, String databaseName) {
        DatabaseQueryHandler databaseQueryHandler = new DatabaseQueryHandler();
        TableQueryHandler tableQueryHandler = new TableQueryHandler();
        InsertDataQueryHandler insertDataQueryHandler = new InsertDataQueryHandler();
        SelectDataQueryHandler selectDataQueryHandler = new SelectDataQueryHandler();
        UpdateDataQueryHandler updateDataQueryHandler = new UpdateDataQueryHandler();
        DeleteDataQueryHandler deleteDataQueryHandler = new DeleteDataQueryHandler();


        if (Pattern.compile(createDatabasePattern).matcher(query).find()) {
            String database = databaseQueryHandler.createDatabaseQuery(query, userId);
            return database;
        } else if (Pattern.compile(useDatabasePattern).matcher(query).find()) {
            String database = databaseQueryHandler.useDatabase(query, userId);
            return database;
        } else if (Pattern.compile(dropDatabasePattern).matcher(query).find()) {
            databaseQueryHandler.deleteDatabaseQuery(query, userId);
            return null;
        } else if (Pattern.compile(createTablePattern).matcher(query).find()) {
            tableQueryHandler.createTableQuery(query, userId, databaseName);
            return null;
        } else if (Pattern.compile(dropTablePattern).matcher(query).find()) {
            tableQueryHandler.deleteTableQuery(query, userId, databaseName);
            return null;
        } else if (Pattern.compile(insertQueryPattern).matcher(query).find()) {
            insertDataQueryHandler.insertDataQuery(query, userId, databaseName);
            return null;
        } else if (Pattern.compile(selectQueryPattern).matcher(query).find()) {
            selectDataQueryHandler.getColumnDataQuery(query, userId, databaseName);
            return null;
        } else if (Pattern.compile(updateQueryPattern).matcher(query).find()) {
            updateDataQueryHandler.updateDataQuery(query, userId, databaseName);
            return null;
        } else if (Pattern.compile(deleteQueryPattern).matcher(query).find()) {
            deleteDataQueryHandler.deleteDataQuery(query, userId, databaseName);
            return null;
        } else {
            System.out.println("in else");
            return null;
        }
    }
}
