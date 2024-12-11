package com.zephyros1938;

import com.zephyros1938.engine.Engine;
import com.zephyros1938.engine.IAppLogic;
import com.zephyros1938.engine.Render;
import com.zephyros1938.engine.Scene;
import com.zephyros1938.engine.Window;

// Guide: https://ahbejarano.gitbook.io/lwjglgamedev/chapter-02

public class Main implements IAppLogic {
    public static void main(String[] args) {
        Main main = new Main();
        Engine game_engine = new Engine("Test Window", new Window.WindowOptions(), main);
        game_engine.start();
    }

    @Override
    public void cleanup() {
        // Nothing to be done yet
    }

    @Override
    public void init(Window window, Scene scene, Render render) {
        // Nothing to be done yet
    }

    @Override
    public void input(Window window, Scene scene, long diffTimeMillis) {
        // Nothing to be done yet
    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {
        // Nothing to be done yet
    }
}
