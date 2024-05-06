package com.lightweightdbms.queryoperation;

import com.lightweightdbms.fileoperation.TextFileReader;
import com.lightweightdbms.fileoperation.TextFileWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateDataQueryHandler {
    QueryOperationUtil queryOperationUtil = new QueryOperationUtil();

    public void updateDataQuery(String query, String userId, String databaseName) {
        ArrayList<String> extractedData;
        String finalQuery = query.substring(0, query.length() - 2);
        String selectQueryPattern = "^update (.*?) set (.*?) where ";
        Pattern pattern = Pattern.compile(selectQueryPattern);
        Matcher matcher = pattern.matcher(finalQuery);

        if (matcher.find()) {
            String tableName = matcher.group(1);
            String columnNewValue = matcher.group(2);
            String whereCondition = finalQuery.substring(matcher.end());

            String[] columnNewValueList = columnNewValue.split(",");
            ArrayList<String> queryNewColumnNameList = new ArrayList<>();
            ArrayList<String> queryNewColumnValueList = new ArrayList<>();

            String whereColumnName = whereCondition.split("=")[0].trim();
            String whereColumnValue = whereCondition.split("=")[1].trim();

            String filePath = "./DataFiles/" + userId + "/" + databaseName + "/" + tableName + ".txt";

            if (!queryOperationUtil.tableExists(filePath)) {
                System.out.println("Table does not exists");
            } else {
                TextFileReader textFileReader = new TextFileReader();
                extractedData = textFileReader.readFile(filePath);
                String tableData = "";

                ArrayList<String> tableColumnList = new ArrayList<>();

                for (String s : extractedData.get(0).split(" \\| ")) {
                    tableColumnList.add(s.split(" ")[0].trim());
                }
                ArrayList<Integer> columnIndexList = new ArrayList<>();

                for (String s : columnNewValueList) {
                    queryNewColumnNameList.add(s.trim().split("=")[0].trim());

                    int index = tableColumnList.indexOf(s.trim().split("=")[0].trim());
                    columnIndexList.add(index);

                    queryNewColumnValueList.add(s.trim().split("=")[1].trim());
                }

                ArrayList<String> writeData = new ArrayList<>();
                writeData.add(extractedData.get(0));

                for (int i = 1; i < extractedData.size(); i++) {
                    ArrayList<String> dataList = new ArrayList<>(Arrays.asList(extractedData.get(i).split(" \\| ")));
                    if (dataList.get(tableColumnList.indexOf(whereColumnName)).equals(whereColumnValue)) {
                        for (int j = 0; j < dataList.size(); j++) {
                            if (columnIndexList.contains(j)) {
                                int valueIndex = queryNewColumnNameList.indexOf(tableColumnList.get(j));
                                tableData += queryNewColumnValueList.get(valueIndex) + " | ";
                            } else {
                                tableData += dataList.get(j) + " | ";
                            }
                        }

                        writeData.add(tableData.substring(0, tableData.length() - 2));
                    } else {
                        writeData.add(extractedData.get(i));
                    }
                }

                TextFileWriter textFileWriter = new TextFileWriter();
                textFileWriter.writeFile(filePath, writeData.get(0) + "\n");
                for (int i = 1; i < writeData.size(); i++) {
                    textFileWriter.appendFile(filePath, writeData.get(i) + "\n");
                }
                System.out.println("Data successfully updated");
            }
        } else {
            System.out.println("Query Syntax Error.");
        }
    }
}
