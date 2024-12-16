package com.zephyros1938.engine;

import com.zephyros1938.engine.scene.Scene;

public interface IAppLogic {

    void cleanup();

    void init(Window window, Scene scene, Render render);

    void input(Window window, Scene scene, long diff_time_milli, boolean input_consumed);

    void update(Window window, Scene scene, long diff_time_millis);

}
