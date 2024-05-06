package com.lightweightdbms.queryoperation;

import com.lightweightdbms.fileoperation.TextFileReader;
import com.lightweightdbms.fileoperation.TextFileWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteDataQueryHandler {
    QueryOperationUtil queryOperationUtil = new QueryOperationUtil();

    public void deleteDataQuery(String query, String userId, String databaseName) {
        ArrayList<String> extractedData;
        String finalQuery = query.substring(0, query.length() - 2);
        String selectQueryPattern = "^delete from (.*?) where ";
        Pattern pattern = Pattern.compile(selectQueryPattern);
        Matcher matcher = pattern.matcher(finalQuery);

        if (matcher.find()) {
            String tableName = matcher.group(1);
            String whereClause = finalQuery.substring(matcher.end());
            String filePath = "./DataFiles/" + userId + "/" + databaseName + "/" + tableName + ".txt";

            if (!queryOperationUtil.tableExists(filePath)) {
                System.out.println("Table does not exists");
            } else {
                TextFileReader textFileReader = new TextFileReader();
                extractedData = textFileReader.readFile(filePath);

                ArrayList<String> tableColumnList = new ArrayList<>();

                for (String s : extractedData.get(0).split(" \\| ")) {
                    tableColumnList.add(s.split(" ")[0].trim());
                }
//                ArrayList<String> tableColumnList = new ArrayList<>(Arrays.asList(extractedData.get(0).split(" \\| ")));

                String whereColumnName = whereClause.split("=")[0].trim();
                String whereColumnValue = whereClause.split("=")[1].trim();

                ArrayList<String> writeData = new ArrayList<>();

                writeData.add(extractedData.get(0));

                for (int i = 1; i < extractedData.size(); i++) {
                    ArrayList<String> dataList = new ArrayList<>(Arrays.asList(extractedData.get(i).split(" \\| ")));
                    if (!dataList.get(tableColumnList.indexOf(whereColumnName)).equals(whereColumnValue)) {
                        writeData.add(extractedData.get(i));
                    }
                }

                TextFileWriter textFileWriter = new TextFileWriter();
                textFileWriter.writeFile(filePath, writeData.get(0) + "\n");
                for (int i = 1; i < writeData.size(); i++) {
                    textFileWriter.appendFile(filePath, writeData.get(i) + "\n");
                }
                System.out.println("Data deleted");
            }
        } else {
            System.out.println("Query Syntax Error.");
        }
    }
}
