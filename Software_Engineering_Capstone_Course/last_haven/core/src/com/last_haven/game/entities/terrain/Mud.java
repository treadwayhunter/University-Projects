package com.last_haven.game.entities.terrain;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.last_haven.game.utils.Asset;
import com.last_haven.game.utils.Position;

/**
 * Mud extends Terrain which extends Entity.
 * Mud is a Terrain tile that displays "mud".
 * Refer to the TerrainCost class for information on each Terrain's cost.
 */
public class Mud extends Terrain {

    /**
     * A constructor for Mud. Mud is a Terrain which is an Entity.
     * @param assetManager allows Mud to determine its own texture. There can be many Mud textures.
     * @param position the Position of a Mud tile. Position is x and y coordinates.
     */
    public Mud(AssetManager assetManager, Position position) {
        super(assetManager, position);
        this.type = "mud";
        this.cost = TerrainCost.MUD;
        updateTexture(assetManager);
    }

    /**
     * A constructor for Mud. Mud is a Terrain which is an Entity.
     * @param assetManager allows Mud to determine its own texture. There can be many Mud textures.
     * @param posX the x-coordinate of a Mud tile.
     * @param posY the y-coordinate of a Mud tile.
     */
    public Mud(AssetManager assetManager, float posX, float posY) {
        super(assetManager, new Position(posX, posY));
        this.type = "mud";
        this.cost = TerrainCost.MUD;
        updateTexture(assetManager);
    }

    /**
     * Used to update the texture of Mud. There can be many Mud tiles, and situations may cause the tile to change
     * texture to another mud texture.
     * @param assetManager allows Mud to determine its texture.
     */
    @Override
    protected void updateTexture(AssetManager assetManager) {
        if(assetManager != null)
        this.texture = assetManager.get(Asset.MUD, Texture.class);
    }
}
