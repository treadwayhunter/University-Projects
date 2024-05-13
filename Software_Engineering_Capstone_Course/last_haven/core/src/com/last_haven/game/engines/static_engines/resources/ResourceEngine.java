package com.last_haven.game.engines.static_engines.resources;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.last_haven.game.engines.static_engines.StaticEntityEngine;
import com.last_haven.game.entities.Entity;
import com.last_haven.game.entities.Resource;
import com.last_haven.game.pathfinding.NodeGraph;
import com.last_haven.game.utils.GameVars;
import com.last_haven.game.utils.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class ResourceEngine extends StaticEntityEngine {

    protected NodeGraph graph; // Engine needs to know information about the graph to manipulate it

    /**
     * Create a new ResourceEngine
     * @param camera the camera that defines the visible portion of the game.
     * @param viewport defines the game area.
     * @param batch maintains textures to draw.
     * @param graph the Graph that will be manipulated when resources are created and destroyed.
     */
    public ResourceEngine(OrthographicCamera camera, Viewport viewport, SpriteBatch batch, NodeGraph graph) {
        super(camera, viewport, batch);
        this.graph = graph;
    }

    @Override
    public void draw() {
        // if resource is dying, then change the color
        viewport.apply();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Entity e : entityMap.values()) {
            Resource r = (Resource) e;
            if (r.getAmount() >= 3 && r.getAmount() <= 6) {
                batch.setColor(1, 0, 0, 1);
            }
            if(r.getAmount() < 3) {
                batch.setColor(0.5f, 0, 0, 1);
            }
            batch.draw(e.getTexture(), e.getX(), e.getY(), e.getWidth(), e.getHeight());
            batch.setColor(1, 1, 1, 1);
        }
        batch.end();
    }

    @Override
    public Entity getEntity(Position position) {
        return entityMap.get(position);
    }

    public Resource getResource(Position position) {
        return (Resource)getEntity(position);
    }

    public void update(float deltaTime, int tick) {
        List<Position> deadResourcePositions = new ArrayList<>();
        for (Entity e : entityMap.values()) {
            Resource r = (Resource)e;
            if (r.getDead()) {
                deadResourcePositions.add(r.getPosition());
            }
        }

        for(Position p : deadResourcePositions) {
            //entityMap.remove(p);
            removeEntity(p);
            int SIZE = GameVars.SIZE;
            graph.getNode(p).enable(graph.getNodeMap(), -SIZE, SIZE, -SIZE, SIZE);
        }
    }

    @Override
    public void removeEntity(Position position) {
        entityMap.remove(position);
    }
}
