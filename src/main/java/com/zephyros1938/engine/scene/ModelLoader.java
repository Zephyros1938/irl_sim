package com.zephyros1938.engine.scene;

import com.zephyros1938.engine.graph.Model;
import com.zephyros1938.engine.graph.TextureCache;
import com.zephyros1938.engine.graph.Material;
import com.zephyros1938.engine.graph.Mesh;

import static org.lwjgl.assimp.Assimp.*;

import java.io.File;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector4f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIColor4D;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIString;
import org.lwjgl.assimp.AIVector2D;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.system.MemoryStack;

public class ModelLoader {

    private ModelLoader() {

    }

    public static Model loadModel(String model_id, String model_path, TextureCache texture_cache) {
        return loadModel(model_id, model_path, texture_cache,
                aiProcess_GenSmoothNormals | aiProcess_JoinIdenticalVertices | aiProcess_Triangulate
                        | aiProcess_FixInfacingNormals | aiProcess_CalcTangentSpace | aiProcess_LimitBoneWeights |
                        aiProcess_PreTransformVertices);
    }

    public static Model loadModel(String model_id, String model_path, TextureCache texture_cache, int flags) {
        File file = new File(model_path);
        if(!file.exists()) {
            org.tinylog.Logger.error(String.format("Model path does not exist: [%s]", model_path));
            throw new RuntimeException(String.format("Model path does not exist: [%s]", model_path));
        }
        String model_dir = file.getParent();

        AIScene ai_scene = aiImportFile(model_path, flags);
        if(ai_scene == null) {
            org.tinylog.Logger.error(String.format("Error loading model [model_path: %s]", model_path));
            throw new RuntimeException(String.format("Error loading model [model_path: %s]", model_path));
        }

        int num_materials = ai_scene.mNumMaterials();
        List<Material> material_list = new ArrayList<>();
        for( int i = 0; i < num_materials; i++) {
            AIMaterial ai_material = AIMaterial.create(ai_scene.mMaterials().get(i));
            material_list.add(processMaterial(ai_material, model_dir, texture_cache));
        }

        int num_meshes = ai_scene.mNumMeshes();
        PointerBuffer ai_meshes = ai_scene.mMeshes();
        Material default_material = new Material();
        for(int i = 0; i < num_meshes; i++) {
            AIMesh ai_mesh = AIMesh.create(ai_meshes.get(i));
            Mesh mesh = processMesh(ai_mesh);
            int material_id_x = ai_mesh.mMaterialIndex();
            Material material;
            if(material_id_x >= 0 && material_id_x < material_list.size()) {
                material = material_list.get(material_id_x);
            } else {
                material = default_material;
            }
            material.getMeshList().add(mesh);
        }
        
        if(!default_material.getMeshList().isEmpty()){
            material_list.add(default_material);
        }

        return new Model(model_id, material_list);
    }

    private static Material processMaterial(AIMaterial ai_material, String model_dir, TextureCache texture_cache) {
        Material material = new Material();
        try(MemoryStack stack = MemoryStack.stackPush()) {
            AIColor4D color = AIColor4D.create();
            int result = aiGetMaterialColor(ai_material, AI_MATKEY_COLOR_DIFFUSE, aiTextureType_NONE, 0, color);
            if (result == aiReturn_SUCCESS) {
                material.setDiffuseColor(new Vector4f(color.r(), color.g(), color.b(), color.a()));
            }

            AIString ai_texture_path = AIString.calloc(stack);
            aiGetMaterialTexture(ai_material, aiTextureType_DIFFUSE, 0, ai_texture_path, (IntBuffer) null,
            null,null,null,null,null);
            String texture_path = ai_texture_path.dataString();
            if(texture_path != null && texture_path.length() > 0) {
                material.setTexturePath(model_dir + File.separator + new File(texture_path).getName());
                texture_cache.createTexture(material.getTexturePath());
                material.setDiffuseColor(Material.DEFAULT_COLOR);
            }

            return material;
        }
    }

    private static Mesh processMesh(AIMesh ai_mesh) {
        float[] vertices = processVertices(ai_mesh);
        float[] tex_coords = processTexCoords(ai_mesh);
        int[] indices = processIndices(ai_mesh);

        if(tex_coords.length == 0) {
            int num_elements = (vertices.length / 3) * 2;
            tex_coords = new float[num_elements];
        }

        return new Mesh(vertices, tex_coords, indices);
    }

    private static int[] processIndices(AIMesh ai_mesh) {
        List<Integer> indices = new ArrayList<>();
        int num_faces = ai_mesh.mNumFaces();
        AIFace.Buffer ai_faces = ai_mesh.mFaces();
        for(int i = 0; i < num_faces; i++){
            AIFace ai_face = ai_faces.get(i);
            IntBuffer buffer = ai_face.mIndices();
            while(buffer.remaining() > 0) {
                indices.add(buffer.get(i));
            }
        }

        return indices.stream().mapToInt(Integer::intValue).toArray();
    }

    private static float[] processTexCoords(AIMesh ai_mesh) {
        AIVector3D.Buffer buffer = ai_mesh.mTextureCoords(0);
        if(buffer == null){
            return new float[]{};
        }
        float[] data = new float[buffer.remaining() * 2];
        int pos = 0;
        while(buffer.remaining() > 0) {
            AIVector3D tex_coord = buffer.get();
            data[pos++] = tex_coord.x();
            data[pos++] = 1 -tex_coord.y();
        }

        return data;
    }

    private static float[] processVertices(AIMesh ai_mesh) {
        AIVector3D.Buffer buffer = ai_mesh.mVertices();
        float[] data = new float[buffer.remaining() * 3];
        int pos = 0;
        while (buffer.remaining() > 0) {
            AIVector3D tex_coord = buffer.get();
            data[pos++] = tex_coord.x();
            data[pos++] = tex_coord.y();
            data[pos++] = tex_coord.z();
        }

        return data;
    }
}
