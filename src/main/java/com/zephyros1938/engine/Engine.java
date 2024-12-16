package com.zephyros1938.engine;

import com.zephyros1938.engine.scene.Scene;

public class Engine {
    public static final int TARGET_UPS = 60;
    private final IAppLogic app_logic;
    private final Window window;
    private Render render;
    private boolean running;
    private Scene scene;
    private int target_fps;
    private int target_ups;

    public Engine(String window_title, Window.WindowOptions opts, IAppLogic app_logic) {
        window = new Window(window_title, opts, () -> {
            resize();
            return null;
        });
        target_fps = opts.fps;
        target_ups = opts.ups;
        this.app_logic = app_logic;
        render = new Render(window);
        scene = new Scene(window.getWidth(), window.getHeight());
        app_logic.init(window, scene, render);
        running = true;
    }

    private void cleanup() {
        org.tinylog.Logger.info("Game engine cleanup started");
        app_logic.cleanup();
        render.cleanup();
        scene.cleanup();
        window.cleanup();
    }

    private void resize() {
        int width = window.getWidth();
        int height = window.getHeight();
        scene.resize(width, height);
        render.resize(width, height);
    }

    private void run() {
        long initial_time = System.currentTimeMillis();
        float time_u = 1000.f / target_ups;
        float time_r = target_fps > 0 ? 1000.f / target_fps : 0;
        float delta_update = 0;
        float delta_fps = 0;

        long update_time = initial_time;
        org.tinylog.Logger.info("Game engine started");
        IGuiInstance i_gui_instance = scene.getGuiInstance();
        while (running && !window.windowShouldClose()) {
            window.pollEvents();

            long now = System.currentTimeMillis();
            delta_update += (now - initial_time) / time_u;
            delta_fps = (now - initial_time) / time_r;

            if (target_fps <= 0 || delta_fps >= 1) {
                window.getMouseInput().input();
                boolean input_consumed = i_gui_instance != null ? i_gui_instance.handleGuiInput(scene, window) : false;
                app_logic.input(window, scene, now - initial_time, input_consumed);
            }

            if (delta_update >= 1) {
                long diff_time_millis = now - update_time;
                app_logic.update(window, scene, diff_time_millis);
                update_time = now;
                delta_update--;
            }

            if (target_fps <= 0 || delta_fps >= 1) {
                render.render(window, scene);
                delta_fps--;
                window.update();
            }
            initial_time = now;
        }

        cleanup();
        org.tinylog.Logger.info("Game engine stopped");
    }

    public void start() {
        running = true;
        run();
    }

    public void stop() {
        running = false;
    }
}
