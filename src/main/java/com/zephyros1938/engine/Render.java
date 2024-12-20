package com.zephyros1938.engine;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.opengl.GL;

import com.zephyros1938.engine.graph.GuiRender;
import com.zephyros1938.engine.graph.SceneRender;
import com.zephyros1938.engine.scene.Scene;

public class Render {

    private SceneRender scene_render;
    private GuiRender gui_render;

    public Render(Window window) {
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glEnable(GL_BACK);
        scene_render = new SceneRender();
        gui_render = new GuiRender(window);
    }

    public void cleanup() {
        scene_render.cleanup();
        gui_render.cleanup();
    }

    public void render(Window window, Scene scene) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glViewport(0, 0, window.getWidth(), window.getHeight());
        scene_render.render(scene);
        gui_render.render(scene);
    }

    public void resize(int width, int height){
        gui_render.resize(width, height);
    }
}
