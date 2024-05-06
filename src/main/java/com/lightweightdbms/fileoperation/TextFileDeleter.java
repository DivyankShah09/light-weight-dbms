package com.lightweightdbms.fileoperation;

import java.io.File;

public class TextFileDeleter {
    public void deleteFile(String filePath) {
        File file = new File(filePath);

        if (file.delete()) {
            System.out.println("File deleted successfully.");
        } else {
            System.err.println("Failed to delete the file.");
        }
    }
}
