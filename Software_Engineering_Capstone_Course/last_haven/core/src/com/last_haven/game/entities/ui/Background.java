package com.last_haven.game.entities.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.last_haven.game.entities.Entity;
import com.last_haven.game.utils.Asset;
import com.last_haven.game.utils.Position;


public class Background extends Entity {

    private String ID;
    /**
     * A constructor to create an Entity.
     *
     * @param assetManager allows the Entity to determine its own texture.
     * @param position     the x-coordinate and y-coordinate of the Entity, starting in the bottom left corner.
     * @param unitWidth    the width of the Entity in game units, not pixels.
     * @param unitHeight   the height of the Entity in game units, not pixels.
     */
    public Background(AssetManager assetManager, Position position, float unitWidth, float unitHeight, String ID) {
        super(assetManager, position, unitWidth, unitHeight);
        updateTexture(assetManager);
        this.ID = ID;
    }

    /**
     * A constructor to create an Entity. Creates a Position from posX and posY.
     *
     * @param assetManager allows the Entity to determine its own texture.
     * @param posX         the x-coordinate of the Entity. Used to create a Position object.
     * @param posY         the y-coordinate of the Entity. Used to create a Position object.
     * @param unitWidth    the width of the Entity in game units, not pixels.
     * @param unitHeight   the height of the Entity in game units, not pixels.
     */
    public Background(AssetManager assetManager, float posX, float posY, float unitWidth, float unitHeight, String ID) {
        super(assetManager, posX, posY, unitWidth, unitHeight);
        updateTexture(assetManager);
        this.ID = ID;

    }

    /**
     * @param assetManager
     */
    @Override
    protected void updateTexture(AssetManager assetManager) {
        this.texture = assetManager.get(Asset.UI_BACKGROUND, Texture.class);
    }

    public String getID() {
        return ID;
    }

}
