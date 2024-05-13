package com.last_haven.game.factories;

import com.badlogic.gdx.assets.AssetManager;
import com.last_haven.game.entities.characters.Villager;
import com.last_haven.game.utils.tasks.TaskManager;

public class VillagerFactory extends Factory {
    /**
     * Create a Female Villager at a given Position.
     * @param assetManager manages the asset of the Villager.
     * @param posX the X position of the Villager.
     * @param posY the Y position of the Villager.
     * @param taskManager manages how the Villager will behave.
     * @return a new Villager at the given coordinates.
     */
    public static Villager FemaleVillagerFactory(AssetManager assetManager, float posX, float posY, TaskManager taskManager) {
        return new Villager(assetManager, posX, posY, taskManager);
    }

}
