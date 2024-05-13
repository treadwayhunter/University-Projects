package com.last_haven.game.engines.static_engines;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.last_haven.game.engines.Engine;
import com.last_haven.game.entities.Entity;
import com.last_haven.game.utils.Position;

import java.util.HashMap;
import java.util.Map;

/**
 * StaticEntityEngine is a class for handling Entities that are Static (i.e. they do not move)
 * Engines of this type contain a Map that manages the lifecycle of static Entities.
 */
public abstract class StaticEntityEngine extends Engine {

    /**
     * Manages the life cycle of the static entities.
     */
    protected Map<Position, Entity> entityMap;

    /**
     * Constructor for the static entity engine.
     * @param camera The camera of the game.
     * @param viewport The viewport of the game.
     * @param batch The batch of sprites that are to be used with this engine.
     */
    public StaticEntityEngine(OrthographicCamera camera, Viewport viewport, SpriteBatch batch) {
        super(camera, viewport, batch);
        entityMap = new HashMap<>();
    }

    /**
     * Deletes the entity at the specified location.
     * @param position The location of the entity that is to be deleted.
     */
    public abstract void removeEntity(Position position);

}
