package com.last_haven.game.entities.food_and_water;

import com.badlogic.gdx.assets.AssetManager;
import com.last_haven.game.entities.Entity;
import com.last_haven.game.utils.Position;

/**
 * An abstract class representing Food objects.
 */
public abstract class Food extends Entity {
    /**
     * Create new Food Entity.
     * @param assetManager manages the asset of the Food.
     * @param position the Position of the Food.
     */
    public Food(AssetManager assetManager, Position position) {
        super(assetManager, position, 1, 1);
    }
    /**
     * Create new Food Entity.
     * @param assetManager manages the asset of the Food.
     * @param posX the X position of the Food.
     * @param posY the Y position of the Food.
     */
    public Food(AssetManager assetManager, float posX, float posY) {
        super(assetManager, posX, posY, 1, 1);
    }
}
