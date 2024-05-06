package com.lightweightdbms.queryoperation;

import com.lightweightdbms.fileoperation.TextFileWriter;

import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InsertDataQueryHandler {
    public void insertDataQuery(String query, String userId, String databaseName) {
        String tableName = "";
        Pattern tableNamePattern = Pattern.compile("insert into (\\w+) \\(");
        Matcher tableNameMatcher = tableNamePattern.matcher(query);
        if (tableNameMatcher.find()) {
            tableName = tableNameMatcher.group(1);
        }
        String tableFilePath = "./DataFiles/" + userId + "/" + databaseName + "/" + tableName + ".txt";

        Pattern dataPattern = Pattern.compile("values");
        Matcher dataMatcher = dataPattern.matcher(query);

        if (dataMatcher.find()) {
            String[] extractedData = query.substring(dataMatcher.end() + 2, query.length() - 3).split(",");

            String delimiter = " | ";
            StringJoiner joiner = new StringJoiner(delimiter);
            for (String data : extractedData) {
                System.out.println(data);
                data = data.trim();
                data = data.replace("'", "");
                joiner.add(data);
            }
            TextFileWriter textFileWriter = new TextFileWriter();
            textFileWriter.appendFile(tableFilePath, "\n" + joiner);
            textFileWriter.writeFile("./DataFiles/" + userId + "/user_logs.txt", "Insert operation successful");
            System.out.println("Data inserted successfully.");
        }
    }
}
