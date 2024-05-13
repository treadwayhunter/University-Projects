package com.last_haven.game.engines.static_engines;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.last_haven.game.engines.active_engines.VillagerEngine;
import com.last_haven.game.entities.Entity;
import com.last_haven.game.entities.structures.StoreHouse;
import com.last_haven.game.entities.structures.Structure;
import com.last_haven.game.factories.VillagerFactory;
import com.last_haven.game.pathfinding.NodeGraph;
import com.last_haven.game.utils.Position;
import com.last_haven.game.utils.tasks.Task;
import com.last_haven.game.utils.tasks.TaskManager;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class StructureEngine extends StaticEntityEngine {
    NodeGraph graph; // StructureEngine needs to be able to poison/enable tiles as buildings are added/removed
    VillagerEngine villagerEngine;
    AssetManager assetManager;
    TaskManager taskManager;
    public StructureEngine(OrthographicCamera camera, Viewport viewport, SpriteBatch batch, NodeGraph graph,
                           VillagerEngine villagerEngine, AssetManager assetManager, TaskManager taskManager) {
        super(camera, viewport, batch);
        this.graph = graph;
        this.villagerEngine = villagerEngine;
        this.assetManager = assetManager;
        this.taskManager = taskManager;
    }


    /**
     * Adds an Entity to a Collection. Collections will be defined in subclasses, but are usually Lists and Maps.
     *
     * @param entity a class representing a drawable object on the screen.
     */
    @Override
    public void addEntity(Entity entity) {
        entityMap.put(entity.getPosition(), entity);
        Structure s = (Structure) entity;
        if (s instanceof StoreHouse && taskManager != null) {
            villagerEngine.addEntity(VillagerFactory.FemaleVillagerFactory(assetManager, s.getX() + 1, s.getY(), taskManager));
        }
        s.poisonArea(graph);
    }

    /**
     * Draws all Entity objects in the Collection.
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
     * From the Collection or Map, find and return the Entity at the given Position.
     *
     * @param position the x and y coordinates of an Entity
     * @return the Entity from the Collection at the given Position.
     */
    @Override
    public Entity getEntity(Position position) {
        //return null; // this is a little different too.
        // If the mouse clicks within the area of the building, the building is selected.
        return entityMap.get(position);
    }

    /**
     * Updates the Entities in the collection. Implementation will differ greatly based on the subclass.
     * For example, Villagers will become more tired, hungry, and thirsty as time goes along, so update accordingly.
     *
     * @param deltaTime the time between 2 subsequent frames.
     * @param tick
     */
    @Override
    public void update(float deltaTime, int tick) {

    }

    /**
     * @param position
     */
    @Override
    public void removeEntity(Position position) {

    }

    public void updateTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public ArrayList<Structure> getStoreHouseList() {
        ArrayList<Structure> storeHouses = entityMap.values()
                .stream()
                .filter(s -> s instanceof StoreHouse && ((StoreHouse) s).isBuilt())
                .map(s -> (StoreHouse)s)
                .collect(Collectors.toCollection(ArrayList::new));

        return storeHouses;
    }
}
