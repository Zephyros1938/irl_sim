package com.zephyros1938.engine;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.opengl.GL;

import com.zephyros1938.engine.graph.SceneRender;

public class Render {

    private SceneRender scene_render;

    public Render() {
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        scene_render = new SceneRender();
    }

    public void cleanup() {
        // Nothing here yet
    }

    public void render(Window window, Scene scene) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glViewport(0, 0, window.getWidth(), window.getHeight());
        scene_render.render(scene);
    }
}
