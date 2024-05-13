package com.last_haven.game.engines.static_engines.resources;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.last_haven.game.engines.static_engines.resources.ResourceEngine;
import com.last_haven.game.entities.Entity;
import com.last_haven.game.entities.foliage.Tree;
import com.last_haven.game.pathfinding.NodeGraph;
import com.last_haven.game.utils.Position;


/**
 * An Engine specific to handling Foliage Entities.
 */
public class FoliageEngine extends ResourceEngine {
    public FoliageEngine(OrthographicCamera camera, Viewport viewport, SpriteBatch batch, NodeGraph graph) {
        super(camera, viewport, batch, graph);
    }

    /**
     * @param position
     */
    @Override
    public void removeEntity(Position position) {
        entityMap.remove(position);
    }

    /**
     * @param entity
     */
    @Override
    public void addEntity(Entity entity) {
        // TODO verify that entity is Foliage
        //System.out.println(entity.getClass().getSimpleName());
        entityMap.put(entity.getPosition(), entity);
    }

    /**
     *
     */

    /**
     * @param position
     * @return
     */
    @Override
    public Entity getEntity(Position position) {
        return entityMap.get(position);
    }

    /**
     * TODO change Tree to Foliage
     */
    public Tree getTree(Position position) {
        return (Tree)getEntity(position);
    }

    /**
     * @param deltaTime
     * @param tick
     */
}
