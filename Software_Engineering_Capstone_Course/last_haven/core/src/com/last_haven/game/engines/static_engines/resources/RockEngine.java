package com.last_haven.game.engines.static_engines.resources;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.last_haven.game.engines.static_engines.resources.ResourceEngine;
import com.last_haven.game.entities.Entity;
import com.last_haven.game.entities.rocks.Rock;
import com.last_haven.game.pathfinding.NodeGraph;
import com.last_haven.game.utils.Position;
// Todo, maybe combine RockEngine and FoliageEngine into a single Resource Engine?
/**
 * An Engine specific for managing Rock Entities.
 */
public class RockEngine extends ResourceEngine {
    public RockEngine(OrthographicCamera camera, Viewport viewport, SpriteBatch batch, NodeGraph graph) {
        super(camera, viewport, batch, graph);
    }

    /**
     * @param entity a class representing a drawable object on the screen.
     */
    @Override
    public void addEntity(Entity entity) {
        entityMap.put(entity.getPosition(), entity);
    }


    /**
     * @param position the x and y coordinates of an Entity
     * @return
     */
    @Override
    public Entity getEntity(Position position) {
        return entityMap.get(position);
    }

    public Rock getRock(Position position) { return (Rock)getEntity(position); }
    /**
     * @param deltaTime the time between 2 subsequent frames.
     * @param tick
     */

    /**
     * @param position
     */
    @Override
    public void removeEntity(Position position) {
        entityMap.remove(position);
    }
}
