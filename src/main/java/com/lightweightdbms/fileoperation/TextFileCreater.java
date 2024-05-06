package com.lightweightdbms.fileoperation;

import java.io.File;

public class TextFileCreater {

    public boolean createFile(String filePath) throws Exception {

        // Create a File object representing the directory
        File file = new File(filePath);

        // Check if the directory already exists
        if (!file.exists()) {
            // Create the directory
            if (file.createNewFile()) {
                System.out.println("File created successfully.");
                return true;
            } else {
                System.err.println("Failed to create the file.");
                return false;
            }
        } else {
            System.out.println("File already exists.");
            return false;
        }
    }
}
