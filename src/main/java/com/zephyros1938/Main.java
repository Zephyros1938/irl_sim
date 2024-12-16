package com.zephyros1938;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

import com.zephyros1938.engine.Engine;
import com.zephyros1938.engine.IAppLogic;
import com.zephyros1938.engine.MouseInput;
import com.zephyros1938.engine.Render;
import com.zephyros1938.engine.Scene;
import com.zephyros1938.engine.Window;
import com.zephyros1938.engine.Window.WindowOptions;
import com.zephyros1938.engine.graph.Material;
import com.zephyros1938.engine.graph.Mesh;
import com.zephyros1938.engine.graph.Model;
import com.zephyros1938.engine.graph.Texture;
import com.zephyros1938.engine.scene.Camera;
import com.zephyros1938.engine.scene.Entity;
import com.zephyros1938.engine.scene.ModelLoader;

// Guide: https://ahbejarano.gitbook.io/lwjglgamedev/chapter-04

public class Main implements IAppLogic {

    private static final float MOUSE_SENSITIVITY = 0.05f;
    private static final float MOVEMENT_SPEED = 0.001f;

    private Entity cube_entity;
    private float rotation;

    public static void main(String[] args) {
        Main main = new Main();
        String name = "IRL SIM";

        WindowOptions window_options = new Window.WindowOptions();

        if (args.length == 3) {
            name = args[0];
            window_options.width = Integer.parseInt(args[1]);
            window_options.height = Integer.parseInt(args[2]);
            org.tinylog.Logger.debug(String.format("Window options set with name:[%s] width:[%s] height:[%s]", args[0],
                    args[1], args[2]));
        }

        Engine game_engine = new Engine(name, window_options, main);
        game_engine.start();
    }

    @Override
    public void cleanup() {
        // Nothing to be done yet
    }

    @Override
    public void init(Window window, Scene scene, Render render) {
        Model cube_model = ModelLoader.loadModel("cube_model", "src/main/resources/models/cube/cube.obj", scene.getTextureCache());
        scene.addModel(cube_model);

        cube_entity = new Entity("cube_entity", cube_model.getId());
        cube_entity.setPosition(0, 0, -2);
        scene.addEntity(cube_entity);
    }

    @Override
    public void input(Window window, Scene scene, long diffTimeMillis) {
        float move = diffTimeMillis * MOVEMENT_SPEED;
        Camera camera = scene.getCamera();
        if (window.isKeyPressed(GLFW_KEY_W)) {
            camera.moveForward(move);
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            camera.moveBackwards(move);
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            camera.moveLeft(move);
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            camera.moveRight(move);
        }
        if (window.isKeyPressed(GLFW_KEY_SPACE)) {
            camera.moveUp(move);
        } else if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
            camera.moveDown(move);
        }

        MouseInput mouse_input = window.getMouseInput();
        if (mouse_input.isRightButtonPressed()) {
            Vector2f displ_vec = mouse_input.getDisplVec();
            camera.addRotation((float) Math.toRadians(-displ_vec.x * MOUSE_SENSITIVITY),
                    (float) Math.toRadians(-displ_vec.y * MOUSE_SENSITIVITY));
        }
    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {
        rotation += 0.25;
        if (rotation > 360) {
            rotation = 0;
        }
        cube_entity.setRotation(1, 1, 1, (float) Math.toRadians(rotation));
        cube_entity.updateModelMatrix();
    }
}
