package com.zephyros1938.engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {
    private Utils() {
    }

    public static String readFile(String file_path) {
        String str;
        try {
            str = new String(Files.readAllBytes(Paths.get(file_path)));
        } catch (IOException excp) {
            throw new RuntimeException("Error reading file [" + file_path + "]", excp);
        }
        return str;
    }
}
