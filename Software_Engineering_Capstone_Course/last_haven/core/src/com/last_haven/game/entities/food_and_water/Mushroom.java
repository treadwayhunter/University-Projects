package com.last_haven.game.entities.food_and_water;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.last_haven.game.entities.Resource;
import com.last_haven.game.utils.Position;
import com.last_haven.game.utils.Asset;

/**
 * Mushrooms are the resources that produce 'food' for the villagers in the demo of the game.
 */
public class Mushroom extends Food implements Resource {

    /**
     * The amount of harvestable resources in the mushroom.
     */
    private int amount;
    /**
     * The dead status of the mushroom.
     */
    private boolean isDead = false;

    /**
     * Constructor for creating a mushroom, uses a position for the location of the mushroom.
     * @param assetManager The asset manager for the game.
     * @param position The location of where to make the mushroom.
     */
    public Mushroom(AssetManager assetManager, Position position) {
        super(assetManager, position);
        updateTexture(assetManager);
        this.amount = 10;
    }

    /**
     * Constructor for creting a mushroom, uses X and Y coordinates for the location of the mushroom.
     * @param assetManager The asset manager for the game.
     * @param posX The X position of where to make the mushroom.
     * @param posY The Y position of where to make the mushroom.
     */
    public Mushroom(AssetManager assetManager, float posX, float posY) {
        super(assetManager, posX, posY);
        updateTexture(assetManager);
        this.amount = 10;
    }

    /**
     * Updates the mushroom's texture.
     * @param assetManager The asset manager for the game.
     */
    @Override
    protected void updateTexture(AssetManager assetManager) {
        if (assetManager != null)
        this.texture = assetManager.get(Asset.MUSHROOM_001, Texture.class);
    }

    /**
     * Decrease the amount of harvestable resources the mushroom has, if the mushroom hits zero, it becomes dead.
     * @return False if resources where decreased, True otherwise.
     */
    @Override
    public boolean decrementAmount() {
        if (amount > 0) {
            amount--;
            return false;
        }
        if (amount == 0) {
            setDead();
        }
        return true;
    }

    /**
     * Gets the current dead status of the mushroom.
     * @return True if the mushroom is dead, False otherwise.
     */
    @Override
    public boolean getDead() {
        return this.isDead;
    }

    /**
     * Updates the dead status of the mushroom.
     */
    public void setDead() {
        this.isDead = true;
    }

    /**
     * Gets the current amount of harvestable resources in the mushroom.
     * @return The current amount of harvestable resources in the mushroom.
     */
    @Override
    public int getAmount() {
        return amount;
    }
}
