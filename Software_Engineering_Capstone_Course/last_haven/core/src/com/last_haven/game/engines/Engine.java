package com.last_haven.game.engines;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.last_haven.game.entities.Entity;
import com.last_haven.game.utils.Position;

/**
 * Engines are classes that act as containers for Entity classes.
 * Each Engine class will maintain a Collection or Map. They are not
 * implemented in this superclass, as different Entities have different
 * collection needs. For the implementation of Collections or Maps, look at
 * the ActiveEntityEngine and the StaticEntityEngine respectfully.
 *
 * Engines also implements operations for adding, getting, and removing Entities.
 */
public abstract class Engine {

    /**
     * The camera used to capture the visuals of the game.
     */
    protected OrthographicCamera camera;
    /**
     * The frame where the camera's output is displayed to th player's screen.
     */
    protected Viewport viewport;
    /**
     * The batch of sprites used by the game.
     */
    protected SpriteBatch batch;

    /**
     * Constructor for creating an Engine.
     * @param camera class provided by LibGDX. Used by viewport and batch for drawing objects at the correct size.
     * @param viewport class provided by LibGDX. Displays the screen in "units" instead of pixels.
     * @param batch class provided by LibGDX for drawing objects across the display.
     */
    public Engine(OrthographicCamera camera, Viewport viewport, SpriteBatch batch) {
        this.camera = camera;
        this.viewport = viewport;
        this.batch = batch;
    }

    /**
     * Adds an Entity to a Collection. Collections will be defined in subclasses, but are usually Lists and Maps.
     * @param entity a class representing a drawable object on the screen.
     */
    public abstract void addEntity(Entity entity);

    /**
     * Draws all Entity objects in the Collection.
     */
    public abstract void draw();

    /**
     * From the Collection or Map, find and return the Entity at the given Position.
     * @param position the x and y coordinates of an Entity
     * @return the Entity from the Collection at the given Position.
     */
    public abstract Entity getEntity(Position position);

    /**
     * Updates the Entities in the collection. Implementation will differ greatly based on the subclass.
     * For example, Villagers will become more tired, hungry, and thirsty as time goes along, so update accordingly.
     *
     * @param deltaTime the time between 2 subsequent frames.
     * @param tick
     */
    public abstract void update(float deltaTime, int tick);

}
