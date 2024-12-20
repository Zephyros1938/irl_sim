package com.zephyros1938.engine.graph;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL40;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
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

    public Mesh(float[] positions, float[] tex_coords, int[] indices) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            num_vertices = indices.length;
            vbo_id_list = new ArrayList<>();

            vao_id = glGenVertexArrays();
            glBindVertexArray(vao_id);

            // Positions VBO
            int vbo_id = glGenBuffers();
            vbo_id_list.add(vbo_id);
            //FloatBuffer positions_buffer = stack.callocFloat(positions.length);
            //positions_buffer.put(0, positions);
            glBindBuffer(GL_ARRAY_BUFFER, vbo_id);
            glBufferData(GL_ARRAY_BUFFER, positions, GL_STATIC_DRAW);
            //positions_buffer.clear();
            glEnableVertexAttribArray(0);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            // TexCords VBO
            vbo_id = glGenBuffers();
            vbo_id_list.add(vbo_id);
            //FloatBuffer tex_coords_buffer = stack.callocFloat(tex_coords.length);
            //tex_coords_buffer.put(0, tex_coords);
            glBindBuffer(GL_ARRAY_BUFFER, vbo_id);
            glBufferData(GL_ARRAY_BUFFER, tex_coords, GL_STATIC_DRAW);
            //tex_coords_buffer.clear();
            glEnableVertexAttribArray(1);
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0); // MAKE SURE THE SIZE IS 2, I SPENT 30 MINUTES TRYING TO
                                                                // MAP TEXTURE COORDS JUST TO FIND THAT SIZE WAS 3
                                                                // INSTEAD OF 2.

            
            // Indices VBO
            vbo_id = glGenBuffers();
            vbo_id_list.add(vbo_id);
            //IntBuffer indices_buffer = stack.callocInt(indices.length);
            //indices_buffer.put(0, indices);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo_id);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
            //indices_buffer.clear();

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
            org.tinylog.Logger
                    .info(String.format("Created model with id:[%s] positions:[%s] texcoords:[%s] indices:[%s]", vao_id,
                            positions.length, tex_coords.length, indices.length));
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
