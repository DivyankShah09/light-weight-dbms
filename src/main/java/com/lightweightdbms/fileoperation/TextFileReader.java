package com.lightweightdbms.fileoperation;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;

public class TextFileReader {

    public ArrayList<String> readFile(String filePath) {
        File inputFile = new File(filePath);
        ArrayList<String> dataList = new ArrayList<>();
        if (inputFile.exists()) {
            try {
                // Reading from the input file
                BufferedReader reader = new BufferedReader(new java.io.FileReader(inputFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    dataList.add(line);
                }
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Wrong path");
        }
        return dataList;
    }
}
