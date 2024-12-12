package com.zephyros1938.engine.graph;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.zephyros1938.engine.Scene;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class SceneRender {

    private ShaderProgram shader_program;

    public SceneRender() {
        List<ShaderProgram.ShaderModuleData> shader_module_data_list = new ArrayList<>();
        shader_module_data_list
                .add(new ShaderProgram.ShaderModuleData(
                        "/src/main/resources/shaders/default.vert".replace("/", File.separator),
                        GL_VERTEX_SHADER));
        shader_module_data_list
                .add(new ShaderProgram.ShaderModuleData(
                        "/src/main/resources/shaders/default.frag".replace("/", File.separator),
                        GL_FRAGMENT_SHADER));
        shader_program = new ShaderProgram(shader_module_data_list);
    }

    public void cleanup() {
        shader_program.cleanup();
    }

    public void render(Scene scene) {
        shader_program.bind();

        scene.getMeshMap().values().forEach(mesh -> {
            glBindVertexArray(mesh.getVaoId());
            glDrawArrays(GL_TRIANGLES, 0, mesh.getNumVertices());
        });

        glBindVertexArray(0);

        shader_program.unbind();
    }

}
