package com.last_haven.game.entities.food_and_water;

import com.badlogic.gdx.assets.AssetManager;
import com.last_haven.game.entities.Entity;
import com.last_haven.game.entities.Resource;
import com.last_haven.game.utils.Position;

/**
 * The WaterResource class provides an interact-able Resource over Water tiles.
 * Water Entities are considered Terrain Entities, and are managed by Terrain Engines. This causes an issue,
 * as other Resources such as Wood, Stone, and Food are managed by Resource Engines. This class defines an object
 * that can be managed by a Resource Engine. WaterResource will be a near limitless resource.
 */
public class WaterResource extends Entity implements Resource {
    /**
     * A constructor to create an Entity.
     *
     * @param assetManager allows the Entity to determine its own texture.
     * @param position     the x-coordinate and y-coordinate of the Entity, starting in the bottom left corner.
     */
    public WaterResource(AssetManager assetManager, Position position) {
        super(assetManager, position, 1, 1);
    }

    /**
     * A constructor to create an Entity. Creates a Position from posX and posY.
     *
     * @param assetManager allows the Entity to determine its own texture.
     * @param posX         the x-coordinate of the Entity. Used to create a Position object.
     * @param posY         the y-coordinate of the Entity. Used to create a Position object.
     */
    public WaterResource(AssetManager assetManager, float posX, float posY) {
        super(assetManager, posX, posY, 1, 1);
    }

    /**
     * Updates the texture when called.
     * When called at first, effectively sets the texture.
     *
     * @param assetManager The asset manager for the game.
     */
    @Override
    protected void updateTexture(AssetManager assetManager) {

    }

    /**
     * Decreases the amount of water.
     * @return True is successful in decreasing water, False otherwise.
     */
    @Override
    public boolean decrementAmount() {
        return false;
    }

    /**
     * Gets the current dead status of the water.
     * @return True if the water is dead, False otherwise.
     */
    @Override
    public boolean getDead() {
        return false;
    }

    /**
     * Gets the current amount of water in the resource.
     * Currently, there is no limit for harvesting water, so the maximum value an Integer can be is used.
     * @return The maximum value an Integer can be.
     */
    @Override
    public int getAmount() {
        return Integer.MAX_VALUE;
    }
}
