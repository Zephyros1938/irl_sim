package com.zephyros1938.engine.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.zephyros1938.engine.Scene;
import com.zephyros1938.engine.scene.Entity;

import static org.lwjgl.opengl.GL30.*;

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

        uniforms_map.createUniform("material.diffuse");
    }

    public void cleanup() {
        shader_program.cleanup();
    }

    private void createUniforms() {
        uniforms_map = new UniformsMap(shader_program.getProgramId());
        uniforms_map.createUniform("projectionMatrix");
        uniforms_map.createUniform("modelMatrix");
        uniforms_map.createUniform("txtSampler");
        uniforms_map.createUniform("viewMatrix");
    }

    public void render(Scene scene) {
        shader_program.bind();

        uniforms_map.setUniform("projectionMatrix", scene.getProjection().getProjectionMatrix());
        uniforms_map.setUniform("viewMatrix", scene.getCamera().getViewMatrix());
        uniforms_map.setUniform("txtSampler", 0);

        Collection<Model> models = scene.getModelMap().values();
        TextureCache texture_cache = scene.getTextureCache();
        for (Model model : models) {
            List<Entity> entities = model.getEntitiesList();

            for (Material material : model.getMaterialList()) {
                uniforms_map.setUniform("material.diffuse", material.getDiffuseColor());
                Texture texture = texture_cache.getTexture(material.getTexturePath());
                glActiveTexture(GL_TEXTURE0);
                texture.bind();

                for (Mesh mesh : material.getMeshList()) {
                    glBindVertexArray(mesh.getVaoId());
                    for (Entity entity : entities) {
                        uniforms_map.setUniform("modelMatrix", entity.getModelMatrix());
                        glDrawElements(GL_TRIANGLES, mesh.getNumVertices(), GL_UNSIGNED_INT, 0);
                    }
                }
            }
        }

        glBindVertexArray(0);

        shader_program.unbind();
    }

}
