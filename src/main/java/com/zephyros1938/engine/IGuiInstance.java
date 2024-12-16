package com.zephyros1938.engine;

import com.zephyros1938.engine.scene.Scene;

public interface IGuiInstance {
    void drawGui();

    boolean handleGuiInput(Scene scene, Window window);
}
