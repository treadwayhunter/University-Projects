package com.last_haven.game.entities;

import com.last_haven.game.utils.Position;

/**
 * Interface that defines the methods of the various types of resources in the game.
 * Each resource will have an amount that wil need to be changed, a position in the world, and the ability to "be dead".
 */
public interface Resource {

    /**
     * Decreases the amount of resources that can be harvested.
     * When a villager harvests from a resources this method is called to decrease the amount.
     * @return True if the amount was decreased successfully, False otherwise.
     */
    boolean decrementAmount();

    /**
     * Gets the current position of the resource in the world.
     * @return The current location of the resource.
     */
    Position getPosition();

    /**
     * Gets if the resource "is dead".
     * A dead resource cannot have resources harvested from it.
     * @return True if the resource is dead, False otherwise.
     */
    boolean getDead();

    /**
     * Gets the current amount of resources that can be harvested from the resource.
     * @return The amount of resources that a villager can harvest.
     */
    int getAmount();
}
