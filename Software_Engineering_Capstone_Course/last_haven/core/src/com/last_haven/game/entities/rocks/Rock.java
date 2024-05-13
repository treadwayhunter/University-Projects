package com.last_haven.game.entities.rocks;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.last_haven.game.entities.Entity;
import com.last_haven.game.entities.Resource;
import com.last_haven.game.utils.Asset;
import com.last_haven.game.utils.Position;

/**
 * Rocks are one of the harvestable building resources in the game.
 * The other building resource in the game is wood.
 */
public class Rock extends Entity implements Resource {
    /**
     * The amount of resources a villager can harvest.
     */
    private int amount;
    /**
     * The dead status of the rock.
     */
    private boolean isDead = false;

    /**
     * Constructor for making Rocks, but uses the position class for its location.
     * @param assetManager The asset manager for the game.
     * @param position The location of where to create a rock.
     */
    public Rock(AssetManager assetManager, Position position) {
        super(assetManager, position, 1, 1);
        updateTexture(assetManager);
        this.amount = 10;
    }

    /**
     * Constructor for making Rocks, but uses the X and Y positions directly for its location.
     * @param assetManager The asset manager for the game.
     * @param posX The x position of where to create the rock.
     * @param posY The y position of where to create the rock.
     */
    public Rock(AssetManager assetManager, float posX, float posY) {
        super(assetManager, posX, posY, 1, 1);
        updateTexture(assetManager);
        this.amount = 10;
    }

    /**
     * @param assetManager
     */
    @Override
    protected void updateTexture(AssetManager assetManager) {
        if (assetManager != null)
        this.texture = assetManager.get(Asset.ROCK, Texture.class);
    }

    /**
     * Decreases the amount of harvestable resources in the rock.
     * @return False if the decrease was successful, True otherwise.
     */
    @Override
    public boolean decrementAmount() {
        if (amount > 0) {
            //System.out.println("DECREMENTING ROCK LIFE");
            amount--;
            return false;
        }
        if (amount == 0) {
            //System.out.println("ROCK IS DEAD");
            setDead();
        }
        return true; // this should only be reached if amount <= 0
    }

    /**
     * Sets the dead status of the rock to true.
     */
    public void setDead() {
        this.isDead = true;
    }

    /**
     * Gets the current dead status of the rock.
     * @return True if the rock is dead, False otherwise.
     */
    @Override
    public boolean getDead() {
        return isDead;
    }

    /**
     * Gets the current amount of harvestable resources in the rock.
     * @return The amount of resources that can be harvested from the rock.
     */
    @Override
    public int getAmount() {
        return amount;
    }
}
