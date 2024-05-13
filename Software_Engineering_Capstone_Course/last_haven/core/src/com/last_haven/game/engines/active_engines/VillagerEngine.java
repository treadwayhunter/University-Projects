package com.last_haven.game.engines.active_engines;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.last_haven.game.entities.Entity;
import com.last_haven.game.entities.characters.Character;
import com.last_haven.game.entities.characters.Villager;
import com.last_haven.game.pathfinding.NodeGraph;
import com.last_haven.game.pathfinding.Pathfinder;
import com.last_haven.game.utils.Area;
import com.last_haven.game.utils.Position;

import java.nio.file.Path;

/**
 * An Engine specific for managing Villager Entities.
 */
public class VillagerEngine extends ActiveEntityEngine {
    public VillagerEngine(OrthographicCamera camera, Viewport viewport, SpriteBatch batch) {
        super(camera, viewport, batch);
    }

    /**
     * @param entity
     */
    @Override
    public void addEntity(Entity entity) {
        // TODO check that entity is a villager
        entityList.add(entity);
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
        for (Entity e : entityList) {
            Character c = (Character)e;
            if(c.isDead()) {
                batch.setColor(0, 0, 0, 1);
            }
            if (c.isSelected()) {
                batch.setColor(0, 0, 1, 1);
            }
            batch.draw(c.getTexture(), c.getX(), c.getY(), c.getWidth(), c.getHeight());
            batch.setColor(1, 1, 1, 1);
        }
        batch.end();
    }

    /**
     * @param position
     * @return
     */
    @Override
    public Entity getEntity(Position position) {

        for (Entity entity : entityList) {
            /*if (Area.checkArea(entity.getPosition(), position, entity.getHeight(), entity.getWidth())) {
                return entity;
            }*/
            if (Area.checkArea(entity, position)) return entity;
        }

        return null;
    }

    public Villager getVillager(Position position) {
        // TODO might need to check that getEntity() is not null
        // TODO check for villager area instead of position


        return (Villager)getEntity(position);
    }

    public boolean VillagersAlive() {
        boolean allDead = true;
        for (Entity entity : entityList) {
            Villager v = (Villager) entity;
            if(!v.isDead()) allDead = false;
        }
        return allDead;
    }

    public int numberVillagersAlive() {
        int alive = 0;
        for (Entity entity : entityList) {
            Villager v = (Villager) entity;
            if(!v.isDead()) {
                //System.out.println(v.getX() + " " + v.getY());
                alive++;
            }
        }
        return alive;
    }

    /**
     * @param deltaTime
     * @param tick
     */
    @Override
    public void update(float deltaTime, int tick) {
        // TODO this method would be good for updating all villagers
        for (Entity e : entityList) {
            Villager v = (Villager)e;
            v.update(tick);
        }
    }

}
