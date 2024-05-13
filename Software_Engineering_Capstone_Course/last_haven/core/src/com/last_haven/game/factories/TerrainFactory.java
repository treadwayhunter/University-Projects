package com.last_haven.game.factories;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.last_haven.game.entities.Entity;
import com.last_haven.game.entities.terrain.*;
import com.last_haven.game.utils.Position;


public class TerrainFactory extends Factory {

    /**
     * Create a Grass tile at a given Position.
     * @param assetManager manages the asset of the Grass tile.
     * @param position the Position the Grass tile will be created.
     * @return a new Grass tile at the given Position.
     */
    public static Grass createGrass(AssetManager assetManager, Position position) {
        return new Grass(assetManager, position);
    }

    /**
     * Create a Grass tile at a given Position.
     * @param assetManager manages the asset of the Grass tile.
     * @param posX the X position of the Grass tile.
     * @param posY the Y position of the Grass tile.
     * @return a new Grass tile at the given Position.
     */
    public static Grass createGrass(AssetManager assetManager, float posX, float posY) {
        return new Grass(assetManager, posX, posY);
    }

    /**
     * Create a Water tile at a given Position.
     * @param assetManager manages the asset of the Water tile.
     * @param position the Position the Water tile will be created.
     * @return a new Water tile at the given Position.
     */
    public static Water createWater(AssetManager assetManager, Position position) {
        return new Water(assetManager, position);
    }
    /**
     * Create a Water tile at a given Position.
     * @param assetManager manages the asset of the Water tile.
     * @param posX the X position of the Water tile.
     * @param posY the Y position of the Water tile.
     * @return a new Water tile at the given Position.
     */
    public static Water createWater(AssetManager assetManager, float posX, float posY) {
        return new Water(assetManager, posX, posY);
    }

    /**
     * Create a Mud tile at a given Position.
     * @param assetManager manages the asset of the Mud tile.
     * @param position the Position the Mud tile will be created.
     * @return a new Mud tile at the given Position.
     */
    public static Mud createMud(AssetManager assetManager, Position position) {
        return new Mud(assetManager, position);
    }
    /**
     * Create a Mud tile at a given Position.
     * @param assetManager manages the asset of the Mud tile.
     * @param posX the X position of the Mud tile.
     * @param posY the Y position of the Mud tile.
     * @return a new Mud tile at the given Position.
     */
    public static Mud createMud(AssetManager assetManager, float posX, float posY) {
        return new Mud(assetManager, posX, posY);
    }
    /**
     * Create a Stone tile at a given Position.
     * @param assetManager manages the asset of the Stone tile.
     * @param position the Position the Stone tile will be created.
     * @return a new Stone tile at the given Position.
     */
    public static Stone createStone(AssetManager assetManager, Position position) {
        return new Stone(assetManager, position);
    }
    /**
     * Create a Stone tile at a given Position.
     * @param assetManager manages the asset of the Stone tile.
     * @param posX the X position of the Stone tile.
     * @param posY the Y position of the Stone tile.
     * @return a new Stone tile at the given Position.
     */
    public static Stone createStone(AssetManager assetManager, float posX, float posY) {
        return new Stone(assetManager, posX, posY);
    }

}
