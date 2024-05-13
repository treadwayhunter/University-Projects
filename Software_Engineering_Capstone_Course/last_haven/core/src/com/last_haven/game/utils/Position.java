package com.last_haven.game.utils;

import java.util.Objects;

/**
 * An object that acts like a datatype and stores the position of an object.
 */
public class Position {
    /**
     * The X and Y coordinates of the object.
     */
    private float x, y;

    /**
     * Constructor for the position object.
     * @param x The x position.
     * @param y The y position.
     */
    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the X value of the object.
     * @return The X value.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the Y value of the object.
     * @return The Y value.
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the position of the object.
     * @return The position.
     */
    public Position getPosition() {
        return this;
    }

    public void changeX() {
        // TODO some entities can move
    }

    public void changeY() {
        // TODO some entities can move
    }

    /**
     * Override of the equal comparison to check if two positions are the same location.
     * @param o The other position to check the location of.
     * @return True if two positions are the same spot.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position)o;
        return x == position.x && y == position.y;
    }

    /**
     * Overrides the hashCode() method.
     * Simplifies the hash used in a HashMap. Many entities will belong to a hashmap where the key is the Position.
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
