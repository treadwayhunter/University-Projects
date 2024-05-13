package com.last_haven.game.engines.static_engines;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.last_haven.game.entities.Entity;
import com.last_haven.game.entities.terrain.Terrain;
import com.last_haven.game.utils.Position;

/**
 * An Engine specific for managing Terrain Entities.
 */
public class TerrainEngine extends StaticEntityEngine {

    public TerrainEngine(OrthographicCamera camera, Viewport viewport, SpriteBatch batch) {
        super(camera, viewport, batch);
    }

    /**
     * @param position
     */
    @Override
    public void removeEntity(Position position) {
        entityMap.remove(position);
    }


    @Override
    public void addEntity(Entity entity) {
        entityMap.put(entity.getPosition(), entity);
    }

    /**
     *
     */
    @Override
    public void draw() {
        viewport.apply();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Entity e : entityMap.values()) {
            batch.draw(e.getTexture(), e.getX(), e.getY(), e.getWidth(), e.getHeight());
        }
        batch.end();
    }

    /**
     * @param position
     * @return
     */
    @Override
    public Entity getEntity(Position position) {
        return entityMap.get(position);
    }

    public Terrain getTerrain(Position position) {
        return (Terrain)getEntity(position);
    }

    /**
     * @param deltaTime
     * @param tick
     */
    @Override
    public void update(float deltaTime, int tick) {

    }
}
