package com.zephyros1938.engine.scene;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Entity {

    private final String id;
    private final String model_id;
    private Matrix4f model_matrix;
    private Vector3f position;
    private Quaternionf rotation;
    private float scale;

    public Entity(String id, String model_id) {
        this.id = id;
        this.model_id = model_id;
        model_matrix = new Matrix4f();
        position = new Vector3f();
        rotation = new Quaternionf();
        scale = 1f;
    }

    public String getId() {
        return id;
    }

    public String getModelId() {
        return model_id;
    }

    public Matrix4f getModelMatrix() {
        return model_matrix;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public final void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public void setRotation(float x, float y, float z, float angle) {
        this.rotation.fromAxisAngleRad(x, y, z, angle);
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void updateModelMatrix() {
        model_matrix.translationRotateScale(position, rotation, scale);
    }

}
