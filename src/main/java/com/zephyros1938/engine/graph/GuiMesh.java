package com.zephyros1938.engine.graph;

import static org.lwjgl.opengl.GL30.*;

import imgui.ImDrawData;

public class GuiMesh {
    
    private int indices_vbo;
    private int vao_id;
    private int vertices_vbo;

    public GuiMesh() {
        vao_id = glGenVertexArrays();
        glBindVertexArray(vao_id);

        // Single VBO
        vertices_vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertices_vbo);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_FLOAT , false, ImDrawData.sizeOfImDrawVert(), 0);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 2, GL_FLOAT , false, ImDrawData.sizeOfImDrawVert(), 8);
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 4, GL_UNSIGNED_BYTE , true, ImDrawData.sizeOfImDrawVert(), 16);

        indices_vbo = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void cleanup() {
        glDeleteBuffers(indices_vbo);
        glDeleteBuffers(vertices_vbo);
        glDeleteVertexArrays(vao_id);
    }

    public int getIndicesVBO() {
        return indices_vbo;
    }

    public int getVaoId() {
        return vao_id;
    }

    public int getVerticesVBO() {
        return vertices_vbo;
    }

    

}
