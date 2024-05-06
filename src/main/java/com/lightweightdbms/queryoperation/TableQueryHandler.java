package com.lightweightdbms.queryoperation;

import com.lightweightdbms.fileoperation.TextFileCreater;
import com.lightweightdbms.fileoperation.TextFileDeleter;
import com.lightweightdbms.fileoperation.TextFileWriter;

import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableQueryHandler {
    public void createTableQuery(String query, String userId, String databaseName) {
        try {
            System.out.println("in table create");
            String[] parts = query.split("\\s+");
            String tableName = parts[2].trim();

            String tableFilePath = "./DataFiles/" + userId + "/" + databaseName + "/" + tableName + ".txt";

            ArrayList<String> columns = new ArrayList<>();
            String[] lines = query.split("\n");

            TextFileCreater textFileCreater = new TextFileCreater();
            if (textFileCreater.createFile(tableFilePath)) {

                for (int i = 1; i < lines.length - 1; i++) {
                    String line = lines[i].trim();
                    if (line.isEmpty() || line.equals(");")) {
                        break;
                    }
                    String[] data = line.split("\\s+");
                    String columnName = data[0];
                    String dataType = data[1].replace(",", "");
                    columns.add(columnName + " " + dataType);
                }

                String delimiter = " | ";
                StringJoiner joiner = new StringJoiner(delimiter);
                for (String cols : columns) {
                    joiner.add(cols);
                }

                TextFileWriter textFileWriter = new TextFileWriter();
                System.out.println(joiner.toString());
                textFileWriter.appendFile(tableFilePath, joiner.toString());
//                textFileWriter.writeFile("./DataFiles/" + userId + "/user_logs.txt", "Created the table : " + tableName);
//                System.out.println("Table created successfully.");
            } else {
                System.out.println("in else");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTableQuery(String query, String userId, String databaseName) {
        String queryPattern = "^drop table ";
        Pattern pattern = Pattern.compile(queryPattern);
        Matcher matcher = pattern.matcher(query);

        QueryOperationUtil queryOperationUtil = new QueryOperationUtil();

        if (matcher.find()) {
            String tableName = query.substring(matcher.end());
            if (!queryOperationUtil.tableExists("./DataFiles/" + userId + "/" + databaseName + "/" + tableName)) {
                System.out.println("Table doesnot exists");
            } else {
                System.out.println("Table dropped");
                TextFileDeleter textFileDeleter = new TextFileDeleter();
                textFileDeleter.deleteFile("./DataFiles/" + userId + "/" + databaseName + "/" + tableName);
                TextFileWriter textFileWriter = new TextFileWriter();
                textFileWriter.writeFile("./DataFiles/" + userId + "/user_logs.txt", "Deleted table : " + tableName);
                System.out.println("Table deleted successfully.");
            }
        } else {
            System.out.println("Query Syntax Error - Table not dropped.");
        }
    }
}
