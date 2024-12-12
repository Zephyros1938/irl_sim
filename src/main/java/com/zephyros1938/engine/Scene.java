package com.zephyros1938.engine;

import java.util.HashMap;
import java.util.Map;

import com.zephyros1938.engine.graph.Mesh;

public class Scene {

    private Map<String, Mesh> mesh_map;

    public Scene() {
        mesh_map = new HashMap<>();
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
}
