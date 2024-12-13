package com.zephyros1938.engine;

import java.util.HashMap;
import java.util.Map;

import com.zephyros1938.engine.graph.Model;
import com.zephyros1938.engine.graph.TextureCache;
import com.zephyros1938.engine.scene.Camera;
import com.zephyros1938.engine.scene.Entity;
import com.zephyros1938.engine.scene.Projection;

public class Scene {

    private Map<String, Model> model_map;
    private Projection projection;
    private TextureCache texture_cache;
    private Camera camera;

    public Scene(int width, int height) {
        model_map = new HashMap<>();
        projection = new Projection(width, height);
        texture_cache = new TextureCache();
        camera = new Camera();
    }

    public void addEntity(Entity entity) {
        String model_id = entity.getModelId();
        Model model = model_map.get(model_id);
        if(model==null){
            throw new RuntimeException("Could not find model ["+model_id+"]");
        }
        model.getEntitiesList().add(entity);
    }

    public void addModel(Model model) {
        model_map.put(model.getId(), model);
    }

    public void cleanup() {
        model_map.values().forEach(Model::cleanup);
    }

    public TextureCache getTextureCache() {
        return texture_cache;
    }

    public Map<String, Model> getModelMap() {
        return model_map;
    }

    public Projection getProjection() {
        return projection;
    }

    public Camera getCamera() {
        return camera;
    }

    public void resize(int width, int height) {
        projection.updateProjectionMatrix(width, height);
    }
}
