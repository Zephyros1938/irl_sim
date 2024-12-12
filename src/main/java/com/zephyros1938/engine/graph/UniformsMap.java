package com.zephyros1938.engine.graph;

import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

public class UniformsMap {
    private int program_id;
    private Map<String, Integer> uniforms;

    public UniformsMap(int program_id) {
        this.program_id = program_id;
        uniforms = new HashMap<>();
    }

    public void createUniform(String uniform_name) {
        int uniform_location = glGetUniformLocation(program_id, uniform_name);
        if (uniform_location < 0) {
            throw new RuntimeException(
                    "Could not find uniform [" + uniform_name + "] in shader program [" + program_id + "]");
        }
        uniforms.put(uniform_name, uniform_location);
    }

    public void setUniform(String uniform_name, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            Integer location = uniforms.get(uniform_name);
            if (location == null) {
                throw new RuntimeException("Could not find uniform [" + uniform_name + "]");
            }
            glUniformMatrix4fv(location.intValue(), false, value.get(stack.mallocFloat(16)));
        }
    }
}
