package com.lightweightdbms.folderoperation;

import java.io.File;

public class FolderDeleter {
    public void deleteFolder(String folderPath) {
        File directory = new File(folderPath);

        // Check if the directory exists
        if (directory.exists() && directory.isDirectory()) {
            // Delete the directory
            if (directory.delete()) {
                System.out.println("Directory deleted successfully.");
            } else {
                System.err.println("Failed to delete the directory.");
            }
        } else {
            System.out.println("Directory does not exist.");
        }
    }
}
