package com.lightweightdbms.fileoperation;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class TextFileWriter {
    public void appendFile(String filePath, String data) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            // Write data to the file
            writer.write(data);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeFile(String filePath, String data) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            // Write data to the file
            writer.write(data);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
