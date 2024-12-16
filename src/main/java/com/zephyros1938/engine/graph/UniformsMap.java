package com.zephyros1938.engine.graph;

import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform4f;
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
            org.tinylog.Logger.error(
                    String.format("Could not find uniform [%s] in shader program [%s]", uniform_name, program_id));
            throw new RuntimeException(
                    "Could not find uniform [" + uniform_name + "] in shader program [" + program_id + "]");
        }
        uniforms.put(uniform_name, uniform_location);
    }

    private int getUniformLocation(String uniform_name) {
        Integer location = uniforms.get(uniform_name);
        if (location == null) {
            org.tinylog.Logger.error(String.format("Could not find uniform [%s]", uniform_name));
            throw new RuntimeException(String.format("Could not find uniform [%s]", uniform_name));
        }

        return location.intValue();
    }

    public void setUniform(String uniform_name, int value) {
        glUniform1i(getUniformLocation(uniform_name), value);
    }

    public void setUniform(String uniform_name, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            glUniformMatrix4fv(getUniformLocation(uniform_name), false, value.get(stack.mallocFloat(16)));
        }
    }

    public void setUniform(String uniform_name, Vector4f value) {
        glUniform4f(getUniformLocation(uniform_name), value.x, value.y, value.z, value.w);
    }
}
