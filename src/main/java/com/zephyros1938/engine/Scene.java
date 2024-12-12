package com.zephyros1938.engine;

import java.util.HashMap;
import java.util.Map;

import com.zephyros1938.engine.graph.Mesh;
import com.zephyros1938.engine.scene.Projection;

public class Scene {

    private Map<String, Mesh> mesh_map;
    private Projection projection;

    public Scene(int width, int height) {
        mesh_map = new HashMap<>();
        projection = new Projection(width, height);
    }

    public void addMesh(String mesh_id, Mesh mesh) {
        mesh_map.put(mesh_id, mesh);
    }

    public void cleanup() {
        mesh_map.values().forEach(Mesh::cleanup);
    }

    public Map<String, Mesh> getMeshMap() {
        return mesh_map;
    }

    public Projection getProjection() {
        return projection;
    }

    public void resize(int width, int height) {
        projection.updateProjectionMatrix(width, height);
    }
}
