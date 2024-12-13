package com.zephyros1938.engine.scene;

import org.joml.*;

public class Camera {

    private Vector3f direction;
    private Vector3f position;
    private Vector3f right;
    private Vector2f rotation;
    private Vector3f up;
    private Matrix4f view_matrix;

    public Camera() {
        direction = new Vector3f();
        position = new Vector3f();
        right = new Vector3f();
        rotation = new Vector2f();
        up = new Vector3f();
        view_matrix = new Matrix4f();
    }

    public void addRotation(float x, float y) {
        rotation.add(x, y);
        recalculate();
    }

    public Vector3f getPosition() {
        return position;
    }

    public Matrix4f getViewMatrix() {
        return view_matrix;
    }

    public void moveBackwards(float inc) {
        view_matrix.positiveZ(direction).negate().mul(inc);
        position.sub(direction);
        recalculate();
    }

    public void moveForward(float inc) {
        view_matrix.positiveZ(direction).negate().mul(inc);
        position.add(direction);
        recalculate();
    }

    public void moveDown(float inc) {
        view_matrix.positiveY(up).mul(inc);
        position.sub(up);
        recalculate();
    }

    public void moveUp(float inc) {
        view_matrix.positiveY(up).mul(inc);
        position.add(up);
        recalculate();
    }

    public void moveLeft(float inc) {
        view_matrix.positiveX(right).mul(inc);
        position.sub(right);
        recalculate();
    }

    public void moveRight(float inc) {
        view_matrix.positiveX(right).mul(inc);
        position.add(right);
        recalculate();
    }

    private void recalculate() {
        view_matrix.identity()
                .rotateX(rotation.x)
                .rotateY(rotation.y)
                .translate(-position.x, -position.y, -position.z);
    }

    public void setPosition(float x, float y, float z) {
        position.set(x, y, z);
        recalculate();
    }

    public void setRotation(float x, float y) {
        rotation.set(x, y);
        recalculate();
    }
}
