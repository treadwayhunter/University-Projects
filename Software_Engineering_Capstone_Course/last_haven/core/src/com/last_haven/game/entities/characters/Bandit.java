package com.last_haven.game.entities.characters;

import com.badlogic.gdx.assets.AssetManager;
import com.last_haven.game.utils.Position;
import com.last_haven.game.utils.tasks.TaskManager;

/**
 * Currently unused in the demo of the game, Bandits are intended to be enemies that will attack the village.
 * Using the same task system as villagers, they would automatically queue "Attack" tasks to attack structures and villagers.
 * Bandits would provide resistance to your ever-growing village.
 */
public class Bandit extends Character {
    /**
     * Create a new Bandit.
     * @param assetManager manages the asset of the Bandit.
     * @param position the Position the Bandit will be created.
     * @param unitWidth the width a Bandit should be drawn with in game units.
     * @param unitHeight the height a Bandit should be drawn with in game units.
     * @param taskManager manages how the Bandit will behave.
     */
    public Bandit(AssetManager assetManager, Position position, float unitWidth, float unitHeight, TaskManager taskManager) {
        super(assetManager, position, unitWidth, unitHeight, taskManager);
        updateTexture(assetManager);
    }
    /**
     * Create a new Bandit.
     * @param assetManager manages the asset of the Bandit.
     * @param posX the X position of the Bandit.
     * @param posY the Y position of the Bandit.
     * @param unitWidth the width a Bandit should be drawn with in game units.
     * @param unitHeight the height a Bandit should be drawn with in game units.
     * @param taskManager manages how the Bandit will behave.
     */
    public Bandit(AssetManager assetManager, float posX, float posY, float unitWidth, float unitHeight, TaskManager taskManager) {
        super(assetManager, posX, posY, unitWidth, unitHeight, taskManager);
        updateTexture(assetManager);
    }

    /**
     * @deprecated
     */
    @Override
    protected void updateTexture(AssetManager assetManager) {

    }

    /**
     * @deprecated
     */
    @Override
    public void initStats() {

    }

    /**
     * @deprecated
     */
    @Override
    public void setDead() {

    }

    /**
     * @deprecated
     */
    @Override
    public void update(int tick) {

    }
}
