package com.last_haven.game.utils;

import com.last_haven.game.entities.Entity;

/**
 * A utility class that provides static methods for determining
 * the area of an entity.
 */
public class Area {

    /**
     * checkArea() checks if a clicked occurred inside the area of an entity.
     * @param entityPos the position of the entity
     * @param clickedPos the position that was clicked
     * @param unitHeight the height of the entity
     * @param unitWidth the width of the entity
     * @return true if the entity was clicked, false if the entity was not clicked.
     */
    public static boolean checkArea(Position entityPos, Position clickedPos, float unitHeight, float unitWidth) {
        // Position represents the bottom left-hand corner of an entity
        // the unitHeight represents the height in units
        // the unitWidth represents the width in units
        if (clickedPos.getX() >= entityPos.getX() && clickedPos.getX() <= entityPos.getX() + unitWidth) {
            if (clickedPos.getY() >= entityPos.getY() && clickedPos.getY() <= entityPos.getY() + unitHeight) {
                return true;
            }
        }
        return false;
    }

    /**
     * checkArea() checks if a click occurred inside the area of an entity.
     * @param entity The entity that may have been clicked.
     * @param clickedPos The position that was clicked.
     * @return true if the entity was clicked, false if the entity was not clicked.
     */
    public static boolean checkArea(Entity entity, Position clickedPos) {
        if (clickedPos.getX() >= entity.getX() && clickedPos.getX() <= entity.getX() + entity.getWidth()) {
            if (clickedPos.getY() >= entity.getY() && clickedPos.getY() <= entity.getY() + entity.getHeight()) {
                return true;
            }

        }

        return false;
    }
}
