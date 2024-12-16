package com.zephyros1938.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class Utils {

    private Utils() {
    }

    public static String readFile(String file_path) {
        String str;
        file_path = "src" + File.separator + "main" + File.separator + file_path;
        org.tinylog.Logger.info(String.format("Loading shader [%s]", file_path));
        try {
            FileInputStream fs = new FileInputStream(Paths.get("", file_path.split("/")).toFile());
            str = new String(fs.readAllBytes());
            fs.close();
        } catch (IOException exception) {
            org.tinylog.Logger.warn(String.format("Could not load shader [%s], attempting fallback load", file_path));
            String shader_fallback = UtilsShaders.getShaderFallback(file_path);

            if (shader_fallback != null) {
                org.tinylog.Logger.info(String.format("Fallback for shader [%s] loaded", file_path));
                return shader_fallback;
            }
            org.tinylog.Logger.error(String.format("Could not load fallback for shader [%s]", file_path));
            throw new RuntimeException("Error reading file [" + file_path + "]", exception);
        }
        return str;
    }
}
