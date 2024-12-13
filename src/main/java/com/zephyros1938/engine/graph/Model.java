package com.zephyros1938.engine.graph;

import java.util.ArrayList;
import java.util.List;

import com.zephyros1938.engine.scene.Entity;

public class Model {
    
    private final String id;
    private List<Entity> entities_list;
    private List<Material> material_list;

    public Model(String id, List<Material> material_list) {
        this.id = id;
        this.material_list = material_list;
        entities_list = new ArrayList<>();
    }

    public void cleanup() {
        material_list.forEach(Material::cleanup);
    }

    public List<Entity> getEntitiesList() {
        return entities_list;
    }
    
    public String getId() {
        return id;
    }

    public List<Material> getMaterialList() {
        return material_list;
    }

}
