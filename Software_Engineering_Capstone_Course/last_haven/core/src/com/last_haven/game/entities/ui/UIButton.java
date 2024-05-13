package com.last_haven.game.entities.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.last_haven.game.entities.Entity;
import com.last_haven.game.utils.Asset;
import com.last_haven.game.utils.Position;

public class UIButton extends Entity {

    Texture icon;
    Boolean visible = false;
    /**
     * A constructor to create an Entity.
     *
     * @param assetManager allows the Entity to determine its own texture.
     * @param position     the x-coordinate and y-coordinate of the Entity, starting in the bottom left corner.
     */
    public UIButton(AssetManager assetManager, Position position, Texture icon) {
        super(assetManager, position, 32, 32);
        this.icon = icon;
        updateTexture(assetManager);
    }

    /**
     * A constructor to create an Entity. Creates a Position from posX and posY.
     *
     * @param assetManager allows the Entity to determine its own texture.
     * @param posX         the x-coordinate of the Entity. Used to create a Position object.
     * @param posY         the y-coordinate of the Entity. Used to create a Position object.
     */
    public UIButton(AssetManager assetManager, float posX, float posY, Texture icon) {
        super(assetManager, posX, posY, 32, 32);
        this.icon = icon;
        updateTexture(assetManager);
    }

    /**
     * Updates the texture when called.
     * When called at first, effectively sets the texture.
     *
     * @param assetManager
     */
    @Override
    protected void updateTexture(AssetManager assetManager) {
        this.texture = assetManager.get(Asset.UI_BACKGROUND, Texture.class);
    }

    public Texture getIcon() {
        return icon;
    }

    public void setVisible() {
        visible = true;
    }

    public void setInvisible() {
        visible = false;
    }

    public boolean getVisible() {
        return visible;
    }
}
