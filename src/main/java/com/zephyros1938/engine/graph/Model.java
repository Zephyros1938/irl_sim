package com.zephyros1938.engine.graph;

import java.util.ArrayList;
import java.util.List;

import com.zephyros1938.engine.scene.Entity;

public class Model {
    
    private final String id;
    private List<Entity> entities_list;
    private List<Mesh> mesh_list;

    public Model(String id, List<Mesh> mesh_list) {
        this.id = id;
        this.mesh_list = mesh_list;
        entities_list = new ArrayList<>();
    }

    public void cleanup() {
        mesh_list.forEach(Mesh::cleanup);
    }

    public List<Entity> getEntitiesList() {
        return entities_list;
    }
    
    public String getId() {
        return id;
    }

    public List<Mesh> getMeshList() {
        return mesh_list;
    }

}
