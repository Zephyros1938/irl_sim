package com.zephyros1938.engine;

public interface IAppLogic {

    void cleanup();

    void init(Window window, Scene scene, Render render);

    void input(Window window, Scene scene, long diff_time_millis);

    void update(Window window, Scene scene, long diff_time_millis);

}
