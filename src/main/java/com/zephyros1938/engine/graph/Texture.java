package com.zephyros1938.engine.graph;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {

    private int texture_id;
    private String texture_path;

    public Texture(int width, int height, ByteBuffer buf) {
        this.texture_path = "";
        generateTexture(width, height, buf);
    }

    public Texture(String texture_path) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            this.texture_path = texture_path;
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            ByteBuffer buf = stbi_load(texture_path, w, h, channels, 4);

            if (buf == null) {
                String error_out = String.format("Image file [%s] failed to load with reason : %s", texture_path,
                        stbi_failure_reason() != null ? stbi_failure_reason() : "No reason provided");
                org.tinylog.Logger.error(error_out);
                throw new RuntimeException(error_out);
            }

            int width = w.get();
            int height = h.get();

            generateTexture(width, height, buf);

            stbi_image_free(buf);
        }
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texture_id);
    }

    public void cleanup() {
        glDeleteTextures(texture_id);
    }

    private void generateTexture(int width, int height, ByteBuffer buf) {
        texture_id = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, texture_id);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    public String getTexturePath() {
        return texture_path;
    }
}
