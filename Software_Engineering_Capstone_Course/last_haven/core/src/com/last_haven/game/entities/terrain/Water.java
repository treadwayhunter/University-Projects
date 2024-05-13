package com.last_haven.game.entities.terrain;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.last_haven.game.utils.Asset;
import com.last_haven.game.utils.Position;

/**
 * Water extends Terrain which extends Entity.
 * Water is a Terrain tile that displays "water".
 * Refer to the TerrainCost class for information on each Terrain's cost.
 */
public class Water extends Terrain {
    /**
     * A constructor for Water. Water is a Terrain which is an Entity.
     * @param assetManager allows Water to determine its own texture. There can be many Water textures.
     * @param position the Position of a Water tile. Position is x and y coordinates.
     */
    public Water(AssetManager assetManager, Position position) {
        super(assetManager, position);
        this.type = "water";
        this.cost = TerrainCost.WATER;
        updateTexture(assetManager);
    }

    /**
     * A constructor for Water. Water is a Terrain which is an Entity.
     * @param assetManager allows Water to determine its own texture. There can be many Water textures.
     * @param posX the x-coordinate of a Water tile.
     * @param posY the y-coordinate of a Water tile.
     */
    public Water(AssetManager assetManager, float posX, float posY) {
        super(assetManager, new Position(posX, posY));
        this.type = "water";
        this.cost = TerrainCost.WATER;
        updateTexture(assetManager);
    }

    /**
     * Used to update the texture of Water. There can be many Water tiles, and situations that may cause the tile
     * to change texture to another water texture.
     * @param assetManager allows Water to determine its texture.
     */
    @Override
    protected void updateTexture(AssetManager assetManager) {
        if (assetManager != null)
        this.texture = assetManager.get(Asset.WATER_000, Texture.class);
    }
}
