package com.zephyros1938.engine.graph;

import java.util.ArrayList;
import java.util.List;

import com.zephyros1938.engine.Scene;

import static com.zephyros1938.engine.graph.UniformsMap.*;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class SceneRender {

    private ShaderProgram shader_program;
    private UniformsMap uniforms_map;

    public SceneRender() {
        List<ShaderProgram.ShaderModuleData> shader_module_data_list = new ArrayList<>();
        shader_module_data_list
                .add(new ShaderProgram.ShaderModuleData(
                        "src/main/resources/shaders/default.vert",
                        GL_VERTEX_SHADER));
        shader_module_data_list
                .add(new ShaderProgram.ShaderModuleData(
                        "src/main/resources/shaders/default.frag",
                        GL_FRAGMENT_SHADER));
        shader_program = new ShaderProgram(shader_module_data_list);
        createUniforms();
    }

    public void cleanup() {
        shader_program.cleanup();
    }

    private void createUniforms() {
        uniforms_map = new UniformsMap(shader_program.getProgramId());
        uniforms_map.createUniform("projectionMatrix");
    }

    public void render(Scene scene) {
        shader_program.bind();

        uniforms_map.setUniform("projectionMatrix", scene.getProjection().getProjectionMatrix());

        scene.getMeshMap().values().forEach(mesh -> {
            glBindVertexArray(mesh.getVaoId());
            glDrawElements(GL_TRIANGLES, mesh.getNumVertices(), GL_UNSIGNED_INT, 0);
        });

        glBindVertexArray(0);

        shader_program.unbind();
    }

}
