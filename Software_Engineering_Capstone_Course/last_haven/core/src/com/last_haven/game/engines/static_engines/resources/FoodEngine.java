package com.last_haven.game.engines.static_engines.resources;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.last_haven.game.entities.Entity;
import com.last_haven.game.entities.food_and_water.Food;
import com.last_haven.game.entities.food_and_water.Mushroom;
import com.last_haven.game.pathfinding.NodeGraph;
import com.last_haven.game.utils.Position;

public class FoodEngine extends ResourceEngine {
    public FoodEngine(OrthographicCamera camera, Viewport viewport, SpriteBatch batch, NodeGraph graph) {
        super(camera, viewport, batch, graph);
    }

    @Override
    public void addEntity(Entity entity) {
        entityMap.put(entity.getPosition(), entity);
    }

    @Override
    public Entity getEntity(Position position) {
        return entityMap.get(position);
    }

    public Food getFood(Position position) {
        return (Food)getEntity(position);
    }

    public Mushroom getMushroom(Position position) {
        return (Mushroom)getEntity(position);
    }

    @Override
    public void removeEntity(Position position) {
        entityMap.remove(position);
    }

}
