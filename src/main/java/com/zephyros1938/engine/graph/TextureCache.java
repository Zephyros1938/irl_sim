package com.zephyros1938.engine.graph;

import java.util.HashMap;
import java.util.Map;

public class TextureCache {

    public static final String DEFAULT_TEXTURE = "src/main/resources/models/default/default_texture.png";

    private Map<String, Texture> texture_map;

    public TextureCache() {
        texture_map = new HashMap<>();
        texture_map.put(DEFAULT_TEXTURE, new Texture(DEFAULT_TEXTURE));
    }

    public void cleanup() {
        texture_map.values().forEach(Texture::cleanup);
    }

    public Texture createTexture(String texture_path) {
        return texture_map.computeIfAbsent(texture_path, Texture::new);
    }

    public Texture getTexture(String texture_path) {
        Texture texture = null;
        if (texture_path != null) {
            texture = texture_map.get(texture_path);
        }
        if (texture == null) {
            texture = texture_map.get(DEFAULT_TEXTURE);
        }
        return texture;
    }

}
