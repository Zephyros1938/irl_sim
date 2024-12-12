package com.zephyros1938.engine.graph;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL40;

import com.zephyros1938.engine.Utils;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

public class ShaderProgram {
    private final int program_id;

    public ShaderProgram(List<ShaderModuleData> shader_module_data_list) {
        program_id = glCreateProgram();
        if (program_id == 0) {
            throw new RuntimeException("Could not create shader");
        }

        List<Integer> shader_modules = new ArrayList<>();
        shader_module_data_list
                .forEach(s -> shader_modules.add(createShader(Utils.readFile(s.shader_file), s.shader_type)));

        link(shader_modules);
    }

    public void cleanup() {
        unbind();
        if (program_id != 0) {
            glDeleteProgram(program_id);
        }
    }

    public void bind() {
        glUseProgram(program_id);
    }

    public void unbind() {
        glUseProgram(0);
    }

    protected int createShader(String shader_code, int shader_type) {
        int shader_id = glCreateShader(shader_type);
        if (shader_id == 0) {
            throw new RuntimeException("Error creating shader. Type: " + shader_type);
        }

        glShaderSource(shader_id, shader_code);
        glCompileShader(shader_id);

        if (glGetShaderi(shader_id, GL_COMPILE_STATUS) == 0) {
            throw new RuntimeException("Error compiling shader code: " + glGetShaderInfoLog(shader_id, 1024));
        }

        glAttachShader(program_id, shader_id);

        return shader_id;
    }

    public int getProgramId() {
        return program_id;
    }

    private void link(List<Integer> shader_modules) {
        glLinkProgram(program_id);
        if (glGetProgrami(program_id, GL_LINK_STATUS) == 0) {
            throw new RuntimeException("Error linking shader code: " + glGetProgramInfoLog(program_id, 1024));
        }

        shader_modules.forEach(s -> glDetachShader(program_id, s));
        shader_modules.forEach(GL40::glDeleteShader);
    }

    public void validate() {
        glValidateProgram(program_id);
        if (glGetProgrami(program_id, GL_VALIDATE_STATUS) == 0) {
            throw new RuntimeException("Error validating shader code: " + glGetProgramInfoLog(program_id, 1024));
        }
    }

    public record ShaderModuleData(String shader_file, int shader_type) {
    }
}
