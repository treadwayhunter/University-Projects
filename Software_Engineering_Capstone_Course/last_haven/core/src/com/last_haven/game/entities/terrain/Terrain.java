package com.last_haven.game.entities.terrain;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.last_haven.game.entities.Entity;
import com.last_haven.game.utils.Position;

/**
 * Terrain inherits the Entity class. This abstraction is suited for Terrain tiles, where each tile has a type for
 * easy identification of the tile. Each tile also has a "cost" which is used to inform pathfinding. For example,
 * a grass tile has a lower cost than a mud tile, so the pathfinder will prioritize traversing grass tiles.
 */
public abstract class Terrain extends Entity {
    protected String type; // Used to quickly identify the class
    protected float cost; // the cost of passing through this tile, given to all nodes in the tile.

    /**
     * The constructor for creating Terrain tiles. Differs slightly from its superclass in that the width and height
     * of a terrain tile are already known, so this constructor does not need to request it as parameters.
     * @param assetManager
     * @param position
     */
    public Terrain(AssetManager assetManager, Position position) {
        super(assetManager, position, 1, 1);
    }

    /**
     * A getter that retrieves the cost of the tile.
     * @return cost, a float representing the difficulty of traversing a tile.
     */
    public float getCost() {
        return cost;
    }

    /**
     * A getter for returning the type, an easy identifier to determine the class. For example,
     * class Grass returns "grass".
     * @return
     */
    public String getType() {
        return type;
    }

}
