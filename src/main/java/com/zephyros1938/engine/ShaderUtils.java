package com.zephyros1938.engine;

import java.util.HashMap;
import java.util.Map;

public class ShaderUtils {
    // As of 12/12/2024 (Thursday, December 2024)
    private static final String FALLBACK_SHADER_VERT = "#version 330\nlayout (location=0) in vec3 position;layout (location=1) in vec3 color;out vec3 outColor;uniform mat4 projectionMatrix;uniform mat4 modelMatrix;void main(){gl_Position = projectionMatrix * modelMatrix * vec4(position, 1.0);outColor = color;}";
    private static final String FALLBACK_SHADER_FRAG = "#version 330\nin vec3 outColor;out vec4 fragColor;void main(){fragColor = vec4(outColor,1.0);}";

    private static final Map<String, Integer> SHADER_FILE_MAP = new HashMap<>();
    private static final Map<Integer, String> SHADER_FILE_MAP_FALLBACKS = new HashMap<>();

    static {
        // Static block to initialize the map with the extensions and corresponding
        // shader types
        SHADER_FILE_MAP.put(".vert", 0);
        SHADER_FILE_MAP.put(".vr", 0);
        SHADER_FILE_MAP.put(".vsh", 0);
        SHADER_FILE_MAP.put(".vs", 0);
        SHADER_FILE_MAP.put(".frag", 1);
        SHADER_FILE_MAP.put(".fs", 1);
        SHADER_FILE_MAP.put(".fsh", 1);
        SHADER_FILE_MAP.put(".geom", 2);
        SHADER_FILE_MAP.put(".gs", 2);
        SHADER_FILE_MAP.put(".gsh", 2);
        SHADER_FILE_MAP.put(".tcs", 3);
        SHADER_FILE_MAP.put(".tessc", 3);
        SHADER_FILE_MAP.put(".tes", 4);
        SHADER_FILE_MAP.put(".tesh", 4);
        SHADER_FILE_MAP.put(".comp", 5);
        SHADER_FILE_MAP.put(".cs", 5);
    }

    static {
        SHADER_FILE_MAP_FALLBACKS.put(0, FALLBACK_SHADER_VERT);
        SHADER_FILE_MAP_FALLBACKS.put(1, FALLBACK_SHADER_FRAG);
        SHADER_FILE_MAP_FALLBACKS.put(2, "Shader Fallback Not Found");
        SHADER_FILE_MAP_FALLBACKS.put(3, "Shader Fallback Not Found");
        SHADER_FILE_MAP_FALLBACKS.put(4, "Shader Fallback Not Found");
    }

    public static String getShaderFallback(String file_path) {
        for (String extension : SHADER_FILE_MAP.keySet()) {
            if (file_path.endsWith(extension)) {
                return SHADER_FILE_MAP_FALLBACKS.get(SHADER_FILE_MAP.get(extension));
            }
        }
        return null;
    }
}
