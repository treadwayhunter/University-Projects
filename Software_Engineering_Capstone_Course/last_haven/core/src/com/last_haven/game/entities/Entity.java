package com.last_haven.game.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.last_haven.game.utils.Inventory;
import com.last_haven.game.utils.Position;

/**
 * An abstraction representing the base to all Entity objects.
 * Any object that can be drawn on the display is an Entity. This includes but is not limited to, Characters,
 * Foliage (trees, plants), and even Terrain tiles (grass, water, etc.)
 */

public abstract class Entity {

    /**
     * The texture of the entity.
     */
    protected Texture texture;
    /**
     * The location of the entity.
     */
    protected Position position;
    /**
     * The asset manager of the game.
     */
    protected AssetManager assetManager;
    /**
     * The inventory of the entity.
     */
    public Inventory inventory;

    /**
     * The width and height that define the size of the entity.
     */
    private float unitWidth, unitHeight;

    /**
     * A constructor to create an Entity.
     * @param assetManager allows the Entity to determine its own texture.
     * @param position the x-coordinate and y-coordinate of the Entity, starting in the bottom left corner.
     * @param unitWidth the width of the Entity in game units, not pixels.
     * @param unitHeight the height of the Entity in game units, not pixels.
     */
    public Entity(AssetManager assetManager, Position position, float unitWidth, float unitHeight) {
        this.position = position;
        this.unitWidth = unitWidth;
        this.unitHeight = unitHeight;
        this.assetManager = assetManager;
        inventory = new Inventory(0, 20);
    }

    /**
     * A constructor to create an Entity. Creates a Position from posX and posY.
     * @param assetManager allows the Entity to determine its own texture.
     * @param posX the x-coordinate of the Entity. Used to create a Position object.
     * @param posY the y-coordinate of the Entity. Used to create a Position object.
     * @param unitWidth the width of the Entity in game units, not pixels.
     * @param unitHeight the height of the Entity in game units, not pixels.
     */
    public Entity(AssetManager assetManager, float posX, float posY, float unitWidth, float unitHeight) {
        this.position = new Position(posX, posY);
        //this.texture = texture;
        this.unitWidth = unitWidth;
        this.unitHeight = unitHeight;
        this.assetManager = assetManager;
        inventory = new Inventory(0, 20);
    }

    /**
     * A getter that retrieves the Texture of an Entity
     * @return the Texture of an Entity
     */
    public Texture getTexture() { return this.texture; }

    /**
     * A getter that retrieves the Position of an Entity
     * @return the Position of an Entity
     */
    public Position getPosition() { return this.position; }

    /**
     * A getter that retrieves the x-coordinate from the Position of an Entity
     * @return the x-coordinate of the Position of an Entity
     */
    public float getX() { return position.getX(); }

    /**
     * A getter that retrieves the y-coordinate from the Position of an Entity
     * @return the y-coordinate of the Position of an Entity
     */
    public float getY() { return position.getY(); }

    /**
     * A getter that retrieves the width of an Entity. Width is in Viewport units, not pixels.
     * @return the width of the Entity in game units, not pixels.
     */
    public float getWidth() { return this.unitWidth; }

    /**
     * A getter that retrieves the height of an Entity. Height is in Viewport units, not pixels.
     * @return the height of the Entity in game units, not pixels.
     */
    public float getHeight() { return this.unitHeight; }

    /**
     * Updates the texture when called.
     * When called at first, effectively sets the texture.
     */
    protected abstract void updateTexture(AssetManager assetManager);
}
