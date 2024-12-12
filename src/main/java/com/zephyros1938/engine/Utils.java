package com.zephyros1938.engine;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Utils {
    private Utils() {
    }

    public static String readFile(String file_path) {
        String str;
        try {
            FileInputStream fs = new FileInputStream("gltest/" + file_path);
            str = new String(fs.readAllBytes());
            fs.close();
        } catch (IOException excp) {
            throw new RuntimeException("Error reading file [" + file_path + "]", excp);
        }
        return str;
    }
}
