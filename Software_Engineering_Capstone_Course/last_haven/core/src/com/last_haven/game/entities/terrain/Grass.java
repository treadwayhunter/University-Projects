package com.last_haven.game.entities.terrain;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.last_haven.game.utils.Asset;
import com.last_haven.game.utils.Position;

/**
 * Grass extends Terrain which extends Entity.
 * Grass is a Terrain tile that displays "grass".
 * Refer to the TerrainCost class for information on each Terrain's cost.
 */
public class Grass extends Terrain {

    /**
     * A constructor for Grass. Grass is a Terrain which is an Entity.
     * @param assetManager allows Grass to determine its own texture. There can be many Grass textures.
     * @param position the Position of a Grass tile. Position is x and y coordinates.
     */
    public Grass(AssetManager assetManager, Position position) {
        super(assetManager, position);
        this.type = "grass";
        this.cost = TerrainCost.GRASS;
        updateTexture(assetManager);
    }

    /**
     * A constructor for Grass. Grass is a Terrain which is an Entity.
     * @param assetManager allows Grass to determine its own texture. There can be many Grass textures.
     * @param posX the x-coordinate of a Grass tile.
     * @param posY the y-coordinate of a Grass tile.
     */
    public Grass(AssetManager assetManager, float posX, float posY) {
        super(assetManager, new Position(posX, posY));
        this.type = "grass";
        this.cost = TerrainCost.GRASS;
        updateTexture(assetManager);
    }


    /**
     * Used to update the texture of Grass. There can be many Grass tiles, and situations that may cause the tile
     * to change texture to another grass texture.
     * @param assetManager allows Grass to determine its texture.
     */
    @Override
    protected void updateTexture(AssetManager assetManager) {
        if (assetManager != null) this.texture = assetManager.get(Asset.GRASS, Texture.class);
    }
}
