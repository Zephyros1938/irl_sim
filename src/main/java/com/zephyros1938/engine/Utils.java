package com.zephyros1938.engine;

import java.io.FileInputStream;
import java.io.IOException;

public class Utils {

    // As of 12/12/2024 (Thursday, December 2024)
    private static final String FALLBACK_SHADER_VERT = "#version 330\n\nlayout (location=0) in vec3 position;\n\nlayout (location=1) in vec3 color;\n\nout vec3 outColor;\n\nuniform mat4 projectionMatrix;\nuniform mat4 modelMatrix;\n\nvoid main()\n{\n\tgl_Position = projectionMatrix * modelMatrix * vec4(position, 1.0);\n\toutColor = color;\n}";
    private static final String FALLBACK_SHADER_FRAG = "#version 330\n\nin vec3 outColor;\nout vec4 fragColor;\n\nvoid main()\n{\n\tfragColor = vec4(outColor,1.0);\n}";

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Utils.class.getName());

    private Utils() {
    }

    public static String readFile(String file_path) {
        String str;
        LOGGER.info(String.format("Loading shader [%s]", file_path));
        try {
            FileInputStream fs = new FileInputStream("gltest/" + file_path);
            str = new String(fs.readAllBytes());
            fs.close();
        } catch (IOException excp) {
            if (file_path.endsWith(".vert")) {
                LOGGER.warning(String.format("Could not load shader [%s], loading fallback.", file_path));
                return FALLBACK_SHADER_VERT;
            } else if (file_path.endsWith(".frag")) {
                LOGGER.warning(String.format("Could not load shader [%s], loading fallback.", file_path));
                return FALLBACK_SHADER_FRAG;
            }
            throw new RuntimeException("Error reading file [" + file_path + "]", excp);
        }
        return str;
    }
}
