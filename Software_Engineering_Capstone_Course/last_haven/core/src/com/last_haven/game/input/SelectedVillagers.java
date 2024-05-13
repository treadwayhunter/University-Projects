package com.last_haven.game.input;

import com.last_haven.game.entities.characters.Villager;

/**
 * Class providing global static methods for managing
 * selected villagers.
 */
public class SelectedVillagers {

    /**
     * TODO this should likely be a List
     * Methods should also manipulate the list
     */
    private static Villager selected;
    private static boolean buildMode = false;

    /**
     * Sets the selected villager. Does not contain the implementation of how to select a villager.
     * @param v the villager that was selected.
     */
    public static void setSelected(Villager v) {
        selected = v;
        selected.setSelected();
    }

    /**
     * Retrieves the selected villager
     * @return Villager that is selected.
     */
    public static Villager getSelected() {
        return selected;
    }

    /**
     * If a Villager is selected, and the build button is clicked, set build mode.
     */
    public static void setBuildMode() {
        System.out.println("Build Mode Set");
        buildMode = true;
    }

    /**
     * Stop the build mode.
     */
    public static void stopBuildMode() {
        buildMode = false;
    }

    /**
     * Fetch the status of the current build mode.
     * @return true if enabled, false otherwise.
     */
    public static boolean getBuildMode() {
        return buildMode;
    }

    /**
     * Deselects the Villager(s), and cancels the build mode.
     */
    public static void deSelect() {
        if (selected != null) {
            selected.deSelect();
            selected = null;
            buildMode = false;
        }
    }

}
