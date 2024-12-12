package com.zephyros1938.engine.scene;

import org.joml.Matrix4f;

public class Projection {

    private static final float FOV = (float) Math.toRadians(60f);
    private static final float Z_FAR = 1000.f;
    private static final float Z_NEAR = 0.01f;

    private Matrix4f projection_matrix;

    public Projection(int width, int height) {
        projection_matrix = new Matrix4f();
        updateProjectionMatrix(width, height);
    }

    public Matrix4f getProjectionMatrix() {
        return projection_matrix;
    }

    public void updateProjectionMatrix(int width, int height) {
        projection_matrix.setPerspective(FOV, (float) width / height, Z_NEAR, Z_FAR);
    }

}
