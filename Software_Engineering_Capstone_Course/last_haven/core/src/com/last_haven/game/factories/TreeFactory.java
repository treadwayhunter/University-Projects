package com.last_haven.game.factories;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.last_haven.game.entities.foliage.Tree;

public class TreeFactory extends Factory {
    /**
     * Create a Tree at a given Position.
     * @param assetManager manages the asset of the Tree.
     * @param posX the X position of the Tree.
     * @param posY the Y position of the Tree.
     * @return a new Tree at the given Position.
     */
    public static Tree TreeFactory(AssetManager assetManager, float posX, float posY) {
        return new Tree(assetManager, posX, posY);
    }
}
