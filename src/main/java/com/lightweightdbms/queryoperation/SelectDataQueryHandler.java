package com.lightweightdbms.queryoperation;

import com.lightweightdbms.fileoperation.TextFileReader;
import com.lightweightdbms.fileoperation.TextFileWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectDataQueryHandler {
    QueryOperationUtil queryOperationUtil = new QueryOperationUtil();

    public String getColumnDataQuery(String query, String userId, String databaseName) {
        String finalQuery = query.substring(0, query.length() - 2);
        ArrayList<String> extractedData;
        String selectQueryPattern = "^select (.*?) from ";
        Pattern pattern = Pattern.compile(selectQueryPattern);
        Matcher matcher = pattern.matcher(finalQuery);
        String data = "";
        String tableName;
        String tableData;

        if (matcher.find()) {
            String queryColumns = matcher.group(1);
            tableName = finalQuery.substring(matcher.end()).split(" ")[0];

            if (!queryOperationUtil.tableExists("./DataFiles/" + userId + "/" + databaseName + "/" + tableName + ".txt")) {
                System.out.println("Table does not exists");
                return null;
            } else {
                TextFileReader textFileReader = new TextFileReader();
                extractedData = textFileReader.readFile("./DataFiles/" + userId + "/" + databaseName + "/" + tableName + ".txt");
                tableData = "";

//                ArrayList<String> tableColumnList = new ArrayList<>(Arrays.asList(extractedData.get(0).split(" \\| ")));
                ArrayList<String> tableColumnList = new ArrayList<>();

                for (String s : extractedData.get(0).split(" \\| ")) {
                    tableColumnList.add(s.split(" ")[0].trim());
                }
                ArrayList<Integer> columnIndexList = new ArrayList<>();

                //gets list of column
                ArrayList<String> queryColumnList;
                if (queryColumns.equals("*")) {
                    queryColumnList = tableColumnList;
                } else {
                    queryColumnList = new ArrayList<>(Arrays.asList(matcher.group(1).split(",")));
                }


                //gets index of columns
                for (String element : queryColumnList) {
                    int index = tableColumnList.indexOf(element.trim());
                    columnIndexList.add(index);
                }

                String wherePatternString = "where ";
                Pattern wherePattern = Pattern.compile(wherePatternString);
                Matcher whereMatcher = wherePattern.matcher(query);

                if (whereMatcher.find()) {
                    String whereCondition = finalQuery.substring(whereMatcher.end());

                    String columnName = whereCondition.split("=")[0].trim();
                    String value = whereCondition.split("=")[1].trim();

                    int whereColumnIndex = tableColumnList.indexOf(columnName);

                    for (int i = 1; i < extractedData.size(); i++) {
                        ArrayList<String> dataList = new ArrayList<>(Arrays.asList(extractedData.get(i).split(" \\| ")));

                        if (dataList.get(whereColumnIndex).equals(value)) {
                            for (Integer j : columnIndexList) {
                                tableData += dataList.get(j) + " | ";
                            }
                            tableData = tableData.substring(0, tableData.length() - 2) + "\n";
                        }
                    }
                    System.out.println(tableData);
                    TextFileWriter textFileWriter = new TextFileWriter();
                    textFileWriter.writeFile("./DataFiles/" + userId + "/user_logs.txt", "Select operation successfull");
                    return tableData;
                } else {
                    //reads the data of every row
                    for (int i = 1; i < extractedData.size(); i++) {
                        ArrayList<String> dataList = new ArrayList<>(Arrays.asList(extractedData.get(i).split(" \\| ")));
                        for (Integer j : columnIndexList) {
                            tableData += dataList.get(j) + " | ";
                        }
                        tableData = tableData.substring(0, tableData.length() - 2) + "\n";
                    }
                    System.out.println(tableData);
                    TextFileWriter textFileWriter = new TextFileWriter();
                    textFileWriter.writeFile("./DataFiles/" + userId + "/user_logs.txt", "Select operation successfull");
                    return tableData;
                }
            }
        } else {
            System.out.println("Query Syntax Error.");
            return null;
        }
    }
}
