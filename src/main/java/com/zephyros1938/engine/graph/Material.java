package com.zephyros1938.engine.graph;

import java.util.ArrayList;
import java.util.List;

public class Material {
    
    private List<Mesh> mesh_list;
    private String texture_path;

    public Material() {
        mesh_list = new ArrayList<>();
    }

    public void cleanup() {
        mesh_list.forEach(Mesh::cleanup);
    }

    public List<Mesh> getMeshList() {
        return mesh_list;
    }

    public String getTexturePath() {
        return texture_path;
    }

    public void setTexturePath(String texture_path) {
        this.texture_path = texture_path;
    }

}
