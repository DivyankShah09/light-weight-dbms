package com.lightweightdbms.folderoperation;

import java.io.File;

public class FolderCreater {
    public void createFolder(String folderPath, String obj) {
        // Create a File object representing the directory
        File directory = new File(folderPath);

        // Check if the directory already exists
        if (!directory.exists()) {
            // Create the directory
            if (directory.mkdirs()) {
                System.out.println(obj + " created successfully.");
            } else {
                System.err.println("Failed to create the " + obj);
            }
        } else {
            System.out.println(obj + "already exists.");
        }
    }
}
