package com.zephyros1938;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

import com.zephyros1938.engine.Engine;
import com.zephyros1938.engine.IAppLogic;
import com.zephyros1938.engine.Render;
import com.zephyros1938.engine.Scene;
import com.zephyros1938.engine.Window;
import com.zephyros1938.engine.Window.WindowOptions;
import com.zephyros1938.engine.graph.Mesh;
import com.zephyros1938.engine.graph.Model;
import com.zephyros1938.engine.scene.Entity;

// Guide: https://ahbejarano.gitbook.io/lwjglgamedev/chapter-04

public class Main implements IAppLogic {

    private Entity cube_entity;
    private Vector4f displ_inc = new Vector4f();
    private float rotation;

    public static void main(String[] args) {
        System.err.println(Arrays.toString(args));
        Main main = new Main();
        String name = "IRL SIM";

        WindowOptions window_options = new Window.WindowOptions();

        if (args.length == 3) {
            name = args[0];
            window_options.width = Integer.parseInt(args[1]);
            window_options.height = Integer.parseInt(args[2]);
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
        float[] positions = new float[] {
                // VO
                -0.5f, 0.5f, 0.5f,
                // V1
                -0.5f, -0.5f, 0.5f,
                // V2
                0.5f, -0.5f, 0.5f,
                // V3
                0.5f, 0.5f, 0.5f,
                // V4
                -0.5f, 0.5f, -0.5f,
                // V5
                0.5f, 0.5f, -0.5f,
                // V6
                -0.5f, -0.5f, -0.5f,
                // V7
                0.5f, -0.5f, -0.5f,
        };
        float[] colors = new float[] {
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
        };
        int[] indices = new int[] {
                // Front face
                0, 1, 3, 3, 1, 2,
                // Top Face
                4, 0, 3, 5, 4, 3,
                // Right face
                3, 2, 7, 5, 3, 7,
                // Left face
                6, 1, 0, 6, 0, 4,
                // Bottom face
                2, 1, 6, 2, 6, 7,
                // Back face
                7, 6, 4, 7, 4, 5,
        };
        List<Mesh> mesh_list = new ArrayList<>();
        Mesh mesh = new Mesh(positions, colors, indices);
        mesh_list.add(mesh);
        String cube_model_id = "cube_model";
        Model model = new Model(cube_model_id, mesh_list);
        scene.addModel(model);

        cube_entity = new Entity("cube_entity", cube_model_id);
        cube_entity.setPosition(0, 0, -2);
        scene.addEntity(cube_entity);
    }

    @Override
    public void input(Window window, Scene scene, long diffTimeMillis) {
        displ_inc.zero();
        if (window.isKeyPressed(GLFW_KEY_UP)) {
            displ_inc.y = -1;
        } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            displ_inc.y = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            displ_inc.x = 1;
        } else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            displ_inc.x = -1;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            displ_inc.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_Q)) {
            displ_inc.z = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_Z)) {
            displ_inc.w = -1;
        } else if (window.isKeyPressed(GLFW_KEY_X)) {
            displ_inc.w = 1;
        }

        displ_inc.mul(diffTimeMillis / 1000.f);

        Vector3f entity_pos = cube_entity.getPosition();
        cube_entity.setPosition(displ_inc.x + entity_pos.x, displ_inc.y + entity_pos.y, displ_inc.z + entity_pos.z);
        cube_entity.setScale(cube_entity.getScale() + displ_inc.w);
        cube_entity.updateModelMatrix();

    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {
        rotation += 1.5;
        if (rotation > 360){
            rotation = 0;
        }
        cube_entity.setRotation(1, 1, 1, (float) Math.toRadians(rotation));
        cube_entity.updateModelMatrix();
    }
}
