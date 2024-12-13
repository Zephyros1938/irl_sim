package com.zephyros1938.engine;

import java.util.HashMap;
import java.util.Map;

public class ShaderUtils {
    // As of 12/12/2024 (Thursday, December 2024)
    private static final String FALLBACK_SHADER_VERT = "#version 330\nlayout (location=0) in vec3 position;layout (location=1) in vec3 color;out vec3 outColor;uniform mat4 projectionMatrix;uniform mat4 modelMatrix;void main(){gl_Position = projectionMatrix * modelMatrix * vec4(position, 1.0);outColor = color;}";
    private static final String FALLBACK_SHADER_FRAG = "#version 330\nin vec3 outColor;out vec4 fragColor;void main(){fragColor = vec4(outColor,1.0);}";

    private static final Map<String, Integer> SHADER_MAP_KEYS = new HashMap<>();
    private static final Map<Integer, String> SHADER_MAP_FALLBACKS = new HashMap<>();

    public static final Integer VERTEX_ID = 0;
    public static final Integer FRAGMENT_ID = 1;
    public static final Integer GEOMETRY_ID = 2;
    public static final Integer TESSELLATION_CONTROL_ID = 3;
    public static final Integer TESSELLATION_EVALUATION_ID = 4;
    public static final Integer COMPUTE_ID = 5;

    static {
        // Initialize the map with the extensions and corresponding shader IDs
        SHADER_MAP_KEYS.put(".vert", VERTEX_ID);
        SHADER_MAP_KEYS.put(".vr", VERTEX_ID);
        SHADER_MAP_KEYS.put(".vsh", VERTEX_ID);
        SHADER_MAP_KEYS.put(".vs", VERTEX_ID);
        SHADER_MAP_KEYS.put(".frag", FRAGMENT_ID);
        SHADER_MAP_KEYS.put(".fs", FRAGMENT_ID);
        SHADER_MAP_KEYS.put(".fsh", FRAGMENT_ID);
        SHADER_MAP_KEYS.put(".geom", GEOMETRY_ID);
        SHADER_MAP_KEYS.put(".gs", GEOMETRY_ID);
        SHADER_MAP_KEYS.put(".gsh", GEOMETRY_ID);
        SHADER_MAP_KEYS.put(".tcs", TESSELLATION_CONTROL_ID);
        SHADER_MAP_KEYS.put(".tessc", TESSELLATION_CONTROL_ID);
        SHADER_MAP_KEYS.put(".tes", TESSELLATION_EVALUATION_ID);
        SHADER_MAP_KEYS.put(".tesh", TESSELLATION_EVALUATION_ID);
        SHADER_MAP_KEYS.put(".comp", COMPUTE_ID);
        SHADER_MAP_KEYS.put(".cs", COMPUTE_ID);
    }

    static {
        // Initialize the map with the shader IDs and corresponding fallbacks
        SHADER_MAP_FALLBACKS.put(VERTEX_ID, FALLBACK_SHADER_VERT);
        SHADER_MAP_FALLBACKS.put(FRAGMENT_ID, FALLBACK_SHADER_FRAG);
        SHADER_MAP_FALLBACKS.put(GEOMETRY_ID, "Shader Fallback Not Found");
        SHADER_MAP_FALLBACKS.put(TESSELLATION_CONTROL_ID, "Shader Fallback Not Found");
        SHADER_MAP_FALLBACKS.put(TESSELLATION_EVALUATION_ID, "Shader Fallback Not Found");
        SHADER_MAP_FALLBACKS.put(COMPUTE_ID, "Shader Fallback Not Found");
    }

    public static String getShaderFallback(String file_path) {
        for (String extension : SHADER_MAP_KEYS.keySet()) {
            if (file_path.endsWith(extension)) {
                return SHADER_MAP_FALLBACKS.get(SHADER_MAP_KEYS.get(extension));
            }
        }
        return null;
    }
}
