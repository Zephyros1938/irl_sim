package com.zephyros1938.engine.graph;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL40;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Mesh {
    private int num_vertices;
    private int vao_id;
    private List<Integer> vbo_id_list;

    public Mesh(float[] positions, int num_vertices) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            this.num_vertices = num_vertices;
            vbo_id_list = new ArrayList<>();

            vao_id = glGenVertexArrays();
            glBindVertexArray(vao_id);

            // Positions VBO
            int vbo_id = glGenBuffers();
            vbo_id_list.add(vbo_id);
            FloatBuffer positions_buffer = stack.callocFloat(positions.length);
            positions_buffer.put(0, positions);
            glBindBuffer(GL_ARRAY_BUFFER, vbo_id);
            glBufferData(GL_ARRAY_BUFFER, positions_buffer, GL_STATIC_DRAW);
            glEnableVertexAttribArray(0);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        }
    }

    public void cleanup() {
        vbo_id_list.forEach(GL40::glDeleteBuffers);
        glDeleteVertexArrays(vao_id);
    }

    public int getNumVertices() {
        return num_vertices;
    }

    public final int getVaoId() {
        return vao_id;
    }
}
