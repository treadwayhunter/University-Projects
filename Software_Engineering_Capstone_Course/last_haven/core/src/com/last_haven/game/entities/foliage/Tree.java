package com.last_haven.game.entities.foliage;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.last_haven.game.entities.Resource;
import com.last_haven.game.utils.Asset;
import com.last_haven.game.utils.Position;

/**
 * Trees represent a resource that be harvested to produce wood.
 * Wood is one of the building materials in the game, the other being stone.
 */
public class Tree extends Foliage implements Resource {

    /**
     * The amount of wood that can be harvested from the tree.
     */
    private int amount;

    /**
     * Create a new Tree.
     * @param assetManager manages the asset of the Tree.
     * @param position the Position of the Tree.
     */
    public Tree(AssetManager assetManager, Position position) {
        super(assetManager, position, 1, 1);
        updateTexture(assetManager);
        this.amount = 10;
    }
    /**
     * Create a new Tree.
     * @param assetManager manages the asset of the Tree.
     * @param posX the X position of the Tree.
     * @param posY the Y position of the Tree.
     */
    public Tree(AssetManager assetManager, float posX, float posY) {
        super(assetManager, posX, posY, 1, 1);
        updateTexture(assetManager);
        this.amount = 10;
    }


    /**
     * Updates the Texture of the Tree.
     * @param assetManager manages the asset of the Tree.
     */
    @Override
    protected void updateTexture(AssetManager assetManager) {
        if (assetManager != null)
        this.texture = assetManager.get(Asset.TREE, Texture.class);
    }


    /**
     * Decreases the amount of wood in the tree.
     * @return False if the wood was decreased successfully, True otherwise.
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
     * Gets the current amount of wood a villager can harvest from the tree.
     * @return The amount of wood a villager can harvest from the tree.
     */
    @Override
    public int getAmount() {
        return amount;
    }

    /**
     * Gets the current position of the tree.
     * @return The position of the tree.
     */
    @Override
    public Position getPosition() {
        return this.position;
    }

    /**
     * Gets the current dead status of the tree.
     * @return True if the tree is dead, False otherwise.
     */
    @Override
    public boolean getDead() {
        return isDead;
    }
}
