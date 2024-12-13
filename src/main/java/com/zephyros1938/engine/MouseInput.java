package com.zephyros1938.engine;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_2;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW.*;

public class MouseInput {

    private Vector2f current_pos;
    private Vector2f previous_pos;
    private Vector2f displ_vec;
    private boolean in_window;
    private boolean left_button_pressed;
    private boolean right_button_pressed;

    public MouseInput(long window_handle) {
        previous_pos = new Vector2f(-1,-1);
        current_pos = new Vector2f();
        displ_vec = new Vector2f();
        left_button_pressed = false;
        right_button_pressed = false;
        in_window = false;

        glfwSetCursorPosCallback(window_handle, (handle, xpos, ypos) -> {
            current_pos.x = (float) xpos;
            current_pos.y = (float) ypos;
        });
        glfwSetCursorEnterCallback(window_handle, (handle, entered) -> {in_window = entered;});
        glfwSetMouseButtonCallback(window_handle, (handle, button, action, mode) -> {
            left_button_pressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
            right_button_pressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
        });
    }

    public Vector2f getCurrentPos() {
        return current_pos;    
    }

    public Vector2f getDisplVec() {
        return displ_vec;
    }

    public void input() {
        displ_vec.x = 0;
        displ_vec.y = 0;
        if(previous_pos.x > 0 && previous_pos.y > 0 && in_window) {
            double delta_x = current_pos.x - previous_pos.x;
            double delta_y = current_pos.y - previous_pos.y;
            boolean rotate_x = delta_x != 0;
            boolean rotate_y = delta_y != 0;
            if(rotate_x) {
                displ_vec.y = (float) delta_x;
            }
            if(rotate_y) {
                displ_vec.x = (float) delta_y;
            }
        }
        previous_pos.x = current_pos.x;
        previous_pos.y = current_pos.y;
    }

    public boolean isLeftButtonPressed() {
        return left_button_pressed;
    }

    public boolean isRightButtonPressed() {
        return right_button_pressed;
    }
}
