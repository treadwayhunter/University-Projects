package com.last_haven.game.entities.foliage;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.last_haven.game.entities.Entity;
import com.last_haven.game.utils.Position;

/**
 * Foliage represents all the plant life that a villager can harvest resources from.
 * Currently, there is only one type of foliage in the game, trees.
 */
public abstract class Foliage extends Entity {
    /**
     * Todo Need to determine attributes that are specific to foliage
     */
    //protected int amount; // the amount of "health" a foliage object has
    /**
     * The dead status of the foliage.
     */
    protected boolean isDead = false;
    /**
     * Create new Foliage.
     * @param assetManager manages the asset of the Foliage.
     * @param position the Position of the Foliage.
     * @param unitWidth the width of the Foliage in game units.
     * @param unitHeight the height of the Foliage in game units.
     */
    public Foliage(AssetManager assetManager, Position position, float unitWidth, float unitHeight) {
        super(assetManager, position, unitWidth, unitHeight);
    }

    /**
     * Create new Foliage.
     * @param assetManager manages the asset of the Foliage.
     * @param posX the X position of the Foliage.
     * @param posY the Y position of the Foliage.
     * @param unitWidth the width of the Foliage in game units.
     * @param unitHeight the height of the Foliage in game units.
     */
    public Foliage(AssetManager assetManager, float posX, float posY, float unitWidth, float unitHeight) {
        super(assetManager, posX, posY, unitWidth, unitHeight);
    }

    /**
     * Sets the Foliage to dead, and will be removed from the Engine.
     */
    public void setDead() {
        isDead = true;
    }

}
