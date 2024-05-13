package com.last_haven.game.engines.active_engines;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.last_haven.game.engines.Engine;
import com.last_haven.game.entities.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ActiveEntityEngine is a class for handling Entities that are Active (i.e. they move across the screen).
 * Engines of this type contain a List that manages the lifecycle of Entities that move.
 */
public abstract class ActiveEntityEngine extends Engine {
    /**
     * List of all 'active' entities in the game.
     */
    protected List<Entity> entityList;

    /**
     * Constructor for the entity engine. Initializes the
     * @param camera The camera used in the game.
     * @param viewport The viewport of the game.
     * @param batch Spritebatch to be rendered with the current engine.
     */
    public ActiveEntityEngine(OrthographicCamera camera, Viewport viewport, SpriteBatch batch) {
        super(camera, viewport, batch);
        //entityList = new ArrayList<>();
        entityList = new CopyOnWriteArrayList<>();
    }
}
