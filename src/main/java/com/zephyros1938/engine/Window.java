package com.zephyros1938.engine;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.concurrent.Callable;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryUtil;

public class Window {
    private final long window_handle;
    private int height;
    private Callable<Void> resize_function;
    private int width;
    private MouseInput mouse_input;

    public Window(String title, WindowOptions opts, Callable<Void> resize_function) {
        this.resize_function = resize_function;
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        if (opts.compatible_profile) {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_COMPAT_PROFILE);
        } else {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        }

        if (opts.width > 0 && opts.height > 0) {
            this.width = opts.width;
            this.height = opts.height;
        } else {
            glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
            GLFWVidMode vid_mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            width = vid_mode.width();
            height = vid_mode.height();
        }

        window_handle = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window_handle == NULL) {
            throw new RuntimeException("Failed to create new GLFW window");
        }

        glfwSetFramebufferSizeCallback(window_handle, (window, w, h) -> resized(w, h));

        glfwSetErrorCallback((int error_code, long msg_ptr) -> {
            System.err.println("Error code " + error_code + ", msg " + MemoryUtil.memUTF8(msg_ptr));
        });

        glfwSetKeyCallback(window_handle, (window, key, scancode, action, mods) -> {
            keyCallBack(key, action);
        });

        glfwMakeContextCurrent(window_handle);

        if (opts.fps > 0) {
            glfwSwapInterval(0);
        } else {
            glfwSwapInterval(1);
        }

        glfwShowWindow(window_handle);

        int[] arr_width = new int[1];
        int[] arr_height = new int[1];
        glfwGetFramebufferSize(window_handle, arr_width, arr_height);
        width = arr_width[0];
        height = arr_height[0];
        mouse_input = new MouseInput(window_handle);
    }

    public void keyCallBack(int key, int action) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            glfwSetWindowShouldClose(window_handle, true);
        }
    }

    public void cleanup() {
        glfwFreeCallbacks(window_handle);
        glfwDestroyWindow(window_handle);
        glfwTerminate();
        GLFWErrorCallback callback = glfwSetErrorCallback(null);
        if (callback != null) {
            callback.free();
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public long getWindowHandle() {
        return window_handle;
    }

    public MouseInput getMouseInput() {
        return mouse_input;
    }

    public boolean isKeyPressed(int key_code) {
        return glfwGetKey(window_handle, key_code) == GLFW_PRESS;
    }

    public void pollEvents() {
        glfwPollEvents();
    }

    protected void resized(int width, int height) {
        this.width = width;
        this.height = height;
        try {
            resize_function.call();
        } catch (Exception excp) {
            org.tinylog.Logger.error(String.format("Error calling resize callback. Exception : %s", excp.getMessage()));
            System.err.println("Error calling resize callback " + excp.getMessage());
        }
    }

    public void update() {
        glfwSwapBuffers(window_handle);
    }

    public boolean windowShouldClose() {
        return glfwWindowShouldClose(window_handle);
    }

    public static class WindowOptions {
        public boolean compatible_profile;
        public int fps;
        public int width;
        public int height;
        public int ups = Engine.TARGET_UPS;
    }
}
