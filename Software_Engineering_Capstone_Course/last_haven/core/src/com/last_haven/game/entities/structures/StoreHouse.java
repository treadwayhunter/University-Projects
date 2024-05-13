package com.last_haven.game.entities.structures;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.last_haven.game.pathfinding.NodeGraph;
import com.last_haven.game.utils.Asset;
import com.last_haven.game.utils.ItemStack;
import com.last_haven.game.utils.Position;

public class StoreHouse extends Structure {

    private final int maxWood = 2000;
    private final int maxStone = 2000;
    private final int maxWater = 1000;
    private final int maxFood = 1000;
    /*private int wood = 0;
    private int water = 0;
    private int food = 0;
    private int stone = 0;*/

    public StoreHouse(AssetManager assetManager, Position position, int buildPercentage) {
        super(assetManager, position, 2, 3);
        this.buildPercentage = buildPercentage;

        updateTexture(assetManager);
        inventory.setMaxSpecificStorage(maxWood, ItemStack.StackType.Wood);
        inventory.setMaxSpecificStorage(maxStone, ItemStack.StackType.Stone);
        inventory.setMaxSpecificStorage(maxWater, ItemStack.StackType.Water);
        inventory.setMaxSpecificStorage(maxFood, ItemStack.StackType.Food);
    }

    public StoreHouse(AssetManager assetManager, float posX, float posY, int buildPercentage) {
        super(assetManager, posX, posY, 2, 3);
        this.buildPercentage = buildPercentage;

        updateTexture(assetManager);
        inventory.setMaxSpecificStorage(maxWood, ItemStack.StackType.Wood);
        inventory.setMaxSpecificStorage(maxStone, ItemStack.StackType.Stone);
        inventory.setMaxSpecificStorage(maxWater, ItemStack.StackType.Water);
        inventory.setMaxSpecificStorage(maxFood, ItemStack.StackType.Food);
    }

    /**
     * Updates the texture when called.
     * When called at first, effectively sets the texture.
     *
     * @param assetManager
     */
    @Override
    protected void updateTexture(AssetManager assetManager) {
        if(assetManager != null) {
            if (buildPercentage >= 100) this.texture = assetManager.get(Asset.STORE_HOUSE, Texture.class);
            else this.texture = assetManager.get(Asset.STORE_HOUSE_UNFINISHED, Texture.class);
        }
    }

    public void update() {
        updateTexture(assetManager);
    }


    /**
     *
     */
    @Override
    public void poisonArea(NodeGraph graph) {
        graph.getNode(this.getX(), this.getY() + 1).poison();
        graph.getNode(this.getX() + 1, this.getY() + 1).poison();
        graph.getNode(this.getX(), this.getY() + 2).poison();
        graph.getNode(this.getX() + 1, this.getY() + 2).poison();
    }



    /*public boolean fullWood() {
        return wood == maxWood;
    }

    public boolean fullWater() {
        return water == maxWater;
    }

    public boolean fullStone() {
        return stone == maxStone;
    }

    public boolean fullFood() {
        return food == maxFood;
    }*/

    /*public void addWood(int amount) {
        if (wood < maxWood) {
            wood += amount;
        }
    }

    public void addWater(int amount) {
        if (water < maxWater) {
            water += amount;
        }
    }

    public void addStone(int amount) {
        if (stone < maxStone) {
            water += amount;
        }
    }

    public void addFood(int amount) {
        if (food < maxFood) {
            food += amount;
        }
    }

    public int getWood() {
        return wood;
    }

    public int getWater() {
        return water;
    }

    public int getStone() {
        return stone;
    }

    public int getFood() {
        return food;
    }

    public int getFoodSpace() {
        return maxFood - food;
    }

    public int getWaterSpace() {
        return maxWater - water;
    }

    public int getWoodSpace() {
        return maxWood - wood;
    }

    public int getStoneSpace() {
        return maxStone - stone;
    }*/
}
