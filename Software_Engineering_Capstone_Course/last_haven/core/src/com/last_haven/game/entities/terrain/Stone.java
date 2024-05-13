package com.last_haven.game.entities.terrain;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.last_haven.game.utils.Asset;
import com.last_haven.game.utils.Position;

/**
 * Stone extends Terrain which extends Entity.
 * Stone is a Terrain tile that displays "stone".
 * Refer to the TerrainCost class for information on each Terrain's cost.
 */
public class Stone extends Terrain {

    /**
     * A constructor for Stone. Stone is a Terrain which is an Entity.
     * @param assetManager allows Stone to determine its own texture. There can be many Stone textures.
     * @param position the Position of a Stone tile. Position is x and y coordinates.
     */
    public Stone(AssetManager assetManager, Position position) {
        super(assetManager, position);
        this.type = "stone";
        this.cost = TerrainCost.STONE;
        updateTexture(assetManager);
    }


    /**
     * A constructor for Stone. Stone is a Terrain which is an Entity.
     * @param assetManager allows Stone to determine its own texture. There can be many Stone textures.
     * @param posX the x-coordinate of a Stone tile.
     * @param posY the y-coordinate of a Stone tile.
     */
    public Stone(AssetManager assetManager, float posX, float posY) {
        super(assetManager, new Position(posX, posY));
        this.type = "stone";
        this.cost = TerrainCost.STONE;
        updateTexture(assetManager);
    }

    /**
     * Used to update the texture of Stone. There can be many Stone tiles, and situationes that may cause the tile
     * to change texture to another stone texture.
     * @param assetManager allows Stone to determine its texture.
     */
    @Override
    protected void updateTexture(AssetManager assetManager) {
        if(assetManager != null)
        this.texture = assetManager.get(Asset.STONE_000, Texture.class);
    }
}
