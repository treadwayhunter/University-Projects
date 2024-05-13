package com.last_haven.game.factories;

import com.badlogic.gdx.assets.AssetManager;
import com.last_haven.game.entities.food_and_water.Mushroom;
import com.last_haven.game.entities.food_and_water.WaterResource;
import com.last_haven.game.utils.Position;

public class FoodFactory extends Factory {
    /**
     * Create a Mushroom at a given Position.
     * @param assetManager manages the asset of the Mushroom.
     * @param position the Position the Mushroom will be created.
     * @return a new Mushroom at the given Position.
     */
    public static Mushroom createMushroom(AssetManager assetManager, Position position) {
        return new Mushroom(assetManager, position);
    }

    /**
     * Create a WaterResource at a given Position.
     * @param assetManager manages the asset of the WaterResource.
     * @param position the Position the WaterResource will be created.
     * @return a new WaterResource at the given Position.
     */
    public static WaterResource createWater(AssetManager assetManager, Position position) {
        return new WaterResource(assetManager, position);
    }
    /**
     * Create a WaterResource at a given Position.
     * @param assetManager manages the asset of the WaterResource.
     * @param x the X position of the WaterResource.
     * @param y the Y position of the WaterResource.
     * @return a new WaterResource at the given coordinates.
     */
    public static WaterResource createWater(AssetManager assetManager, float x, float y) {
        return new WaterResource(assetManager, x, y);
    }
}
