package com.zephyros1938.engine.graph;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector4f;

public class Material {

    public static final Vector4f DEFAULT_COLOR = new Vector4f(0.f, 0.f, 0.f, 1.f);

    private Vector4f diffuse_color;
    
    private List<Mesh> mesh_list;
    private String texture_path;

    public Material() {
        diffuse_color = DEFAULT_COLOR;
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

    public Vector4f getDiffuseColor() {
        return diffuse_color;
    }

    public void setDiffuseColor(Vector4f diffuse_color) {
        this.diffuse_color = diffuse_color;
    }

}
