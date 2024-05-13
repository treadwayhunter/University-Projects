package com.last_haven.game.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.last_haven.game.engines.static_engines.TerrainEngine;
import com.last_haven.game.factories.TerrainFactory;

/**
 * A class for generating a river across the map.
 */
public class RiverGenerator {
    private Position current;
    private TerrainEngine engine;
    private AssetManager assetManager;
    public RiverGenerator(Position start, TerrainEngine engine, int edge1, int edge2, AssetManager assetManager) {
        this.current = start;
        this.engine = engine;
        this.assetManager = assetManager;
        generateRiver(start, engine, edge1, edge2);
    }

    /**
     * Method for generating a river across the map.
     * @param current the current position the river is being generated.
     * @param engine the engine that needs to be modified.
     * @param edge1 ensures the river stays inside this edge of the map
     * @param edge2 ensures the river stays inside this edge of the map
     */
    private void generateRiver(Position current, TerrainEngine engine, int edge1, int edge2) {
        // if the position contains either edge1 or edge2, then stop
        if (current.getX() == edge1 || current.getX() == edge2) {
            return;
        }
        if (current.getY() == edge1 || current.getY() == edge2) {
            return;
        }

        engine.addEntity(TerrainFactory.createWater(assetManager, current)); // make current position water
        engine.addEntity(TerrainFactory.createWater(assetManager, current.getX(), current.getY() + 1));
        engine.addEntity(TerrainFactory.createWater(assetManager, current.getX(), current.getY() - 1));
        engine.addEntity(TerrainFactory.createWater(assetManager, current.getX() + 1, current.getY()));
        engine.addEntity(TerrainFactory.createWater(assetManager, current.getX() - 1, current.getY()));

        // TODO creating a River needs a lot more work
        float newX = current.getX();
        if (Math.random() < 0.3) {
            newX = newX - 1;
        }
        else if (Math.random() > 0.7) {
            newX = newX + 1;
        }
        generateRiver(new Position(newX, current.getY() - 1), engine, edge1, edge2);
    }

}
