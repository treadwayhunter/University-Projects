package com.last_haven.game.utils;

/**
 * Global resource storage for the game.
 * Each resource is stored as a separate integer.
 */
public class ResourceStore {
    // wood, stone, water, food
    private static int wood, stone, water, food = 0;

    /**
     * Gets the current amount of wood in the resource storage.
     * @return The current amount of wood.
     */
    public static int getWood() {
        return wood;
    }

    /**
     * Gets the current amount of stone in the resource storage.
     * @return The current amount of stone.
     */
    public static int getStone() {
        return stone;
    }

    /**
     * Gets the current amount of water in the resource storage.
     * @return The current amount of water.
     */
    public static int getWater() {
        return water;
    }

    /**
     * Gets the current amount of food in the resource storage.
     * @return The current amount of food.
     */
    public static int getFood() {
        return food;
    }

    /**
     * Increases the amount of wood in the storage.
     * @param amount The amount of wood to add to storage.
     */
    public static void addWood(int amount) {
        wood += amount;
    }

    /**
     * Increases the amount of stone in the storage.
     * @param amount The amount of stone to add to storage.
     */
    public static void addStone(int amount) {
        stone += amount;
    }

    /**
     * Increases the amount of water in the storage.
     * @param amount The amount of water to add to the storage.
     */
    public static void addWater(int amount) {
        water += amount;
    }

    /**
     * Increases the amount of food in the storage.
     * @param amount The amount of food to add to the storage.
     */
    public static void addFood(int amount) {
        food += amount;
    }
}
