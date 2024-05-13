package com.last_haven.game.engines.static_engines.resources;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.last_haven.game.entities.Entity;
import com.last_haven.game.entities.food_and_water.WaterResource;
import com.last_haven.game.pathfinding.NodeGraph;
import com.last_haven.game.utils.Position;

public class WaterEngine extends ResourceEngine {
    public WaterEngine(OrthographicCamera camera, Viewport viewport, SpriteBatch batch, NodeGraph graph) {
        super(camera, viewport, batch, graph);
    }

    /**
     * Adds an Entity to a Collection. Collections will be defined in subclasses, but are usually Lists and Maps.
     *
     * @param entity a class representing a drawable object on the screen.
     */
    @Override
    public void addEntity(Entity entity) {
        entityMap.put(entity.getPosition(), entity);
    }

    public Entity getEntity(Position position) {
        return entityMap.get(position);
    }
    public WaterResource getWater(Position position) {
        return (WaterResource) getEntity(position);
    }
}
