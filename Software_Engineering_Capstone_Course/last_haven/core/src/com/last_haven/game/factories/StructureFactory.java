package com.last_haven.game.factories;

import com.badlogic.gdx.assets.AssetManager;
import com.last_haven.game.entities.structures.StoreHouse;
import com.last_haven.game.entities.structures.Structure;
import com.last_haven.game.utils.Position;

public class StructureFactory extends Factory {
    /**
     * Create a StoreHouse at a given Position.
     * @param assetManager manages the asset of the StoreHouse.
     * @param position the Position the StoreHouse will be created.
     * @param buildPercentage the percentage at which a building is constructed. Typically starts at 0.
     * @return a new StoreHouse at the given Position.
     */
    public static Structure createStoreHouse(AssetManager assetManager, Position position, int buildPercentage) {
        return new StoreHouse(assetManager, position, buildPercentage);
    }
    /**
     * Create a StoreHouse at a given Position.
     * @param assetManager manages the asset of the StoreHouse.
     * @param posX the X position of the StoreHouse.
     * @param posY the Y position of the StoreHouse.
     * @param buildPercentage the percentage at which a building is constructed. Typically starts at 0.
     * @return a new StoreHouse at the given Position.
     */
    public static Structure createStoreHouse(AssetManager assetManager, float posX, float posY, int buildPercentage) {
        return new StoreHouse(assetManager, posX, posY, buildPercentage);
    }
}
