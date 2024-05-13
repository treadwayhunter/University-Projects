package com.last_haven.game.engines.static_engines;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.last_haven.game.engines.active_engines.VillagerEngine;
import com.last_haven.game.entities.Entity;
import com.last_haven.game.entities.characters.Villager;
import com.last_haven.game.entities.ui.Background;
import com.last_haven.game.entities.ui.UIButton;
import com.last_haven.game.input.SelectedVillagers;
import com.last_haven.game.utils.Area;
import com.last_haven.game.utils.Position;
import com.last_haven.game.utils.ResourceStore;

public class UIEngine extends StaticEntityEngine {

    private BitmapFont font;
    private VillagerEngine villagerEngine;
    public UIEngine(OrthographicCamera camera, Viewport viewport, SpriteBatch batch, BitmapFont font, VillagerEngine villagerEngine) {
        super(camera, viewport, batch);
        this.font = font;
        this.villagerEngine = villagerEngine;
    }

    /**
     * @param entity a class representing a drawable object on the screen.
     */
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
        camera.update(); // is this necessary if the camera doesn't move?
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.setColor(1, 1, 1, 1f);
        for (Entity e : entityMap.values()) {
            String classType = e.getClass().getSimpleName();
            if(classType.equals("Background")) {
                Background b = (Background)e;
                if (SelectedVillagers.getSelected() != null && (b.getID().equals("villager_info"))) {
                    batch.draw(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
                    Villager v = SelectedVillagers.getSelected();
                    String health = "Health: " + v.getHealth() + "/" + v.getMaxHealth();
                    String energy = "Energy: " + v.getEnergy() + "/" + v.getMaxEnergy();
                    String hunger = "Hunger: " + v.getHunger() + "/" + v.getMaxHunger();
                    String thirst = "Thirst: " + v.getThirst() + "/" + v.getMaxThirst();
                    String strength = "Strength: " + v.getStrength();
                    String intellect = "Intellect: " + v.getIntellect();
                    String status = "Status: " + v.getStatus();

                    font.getData().setScale(2);
                    font.draw(batch, v.getName(), b.getX(), b.getY() + b.getHeight());
                    font.getData().setScale(1);
                    font.draw(batch, health, b.getX(), b.getY() + b.getHeight() - 24);
                    font.draw(batch, energy, b.getX(), b.getY() + b.getHeight() - 48);
                    font.draw(batch, hunger, b.getX(), b.getY() + b.getHeight() - 72);
                    font.draw(batch, thirst, b.getX(), b.getY() + b.getHeight() - 96);
                    font.draw(batch, strength, b.getX(), b.getY() + b.getHeight() - 120);
                    font.draw(batch, intellect, b.getX(), b.getY() + b.getHeight() - 144);
                    font.draw(batch, status, b.getX(), b.getY() + b.getHeight() - 168);

                }

                if (b.getID().equals("resource_info")) {
                    batch.draw(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
                    String wood = "Wood: " + ResourceStore.getWood();
                    String stone = "Stone: " + ResourceStore.getStone();
                    String water = "Water: " + ResourceStore.getWater();
                    String food = "Food: " + ResourceStore.getFood();
                    String numAlive = "Villagers: " + villagerEngine.numberVillagersAlive();

                    font.draw(batch, wood, b.getX() + 128, b.getY() + b.getHeight()/2);
                    font.draw(batch, stone, b.getX() + 256, b.getY() + b.getHeight()/2);
                    font.draw(batch, water, b.getX() + 384, b.getY()+ b.getHeight()/2);
                    font.draw(batch, food, b.getX() + 512, b.getY()+ b.getHeight()/2);
                    font.draw(batch, numAlive, b.getX() + 640, b.getY() + b.getHeight()/2);
                }
            }
            if(classType.equals("UIButton")) {
                UIButton u = (UIButton)e;
                if(SelectedVillagers.getSelected() != null) {
                    u.setVisible();
                    batch.draw(u.getTexture(), u.getX(), u.getY(), u.getWidth(), u.getHeight());
                    batch.draw(u.getIcon(), u.getX(), u.getY(), u.getWidth(), u.getHeight());
                }
                else {
                    u.setInvisible();
                }
            }

        }

        batch.setColor(1, 1, 1,1);
        batch.end();
    }

    /**
     * @param position the x and y coordinates of an Entity
     * @return
     */
    @Override
    public Entity getEntity(Position position) {
        //return entityMap.get(position);
        for (Entity entity : entityMap.values()) {
            if (Area.checkArea(entity, position)) return entity;
        }
        return null;
    }

    public UIButton getButton(Position position) {
        System.out.println("getButton() called");
        Entity e = getEntity(position);
        String classType = "";
        if(e != null) {
            classType = e.getClass().getSimpleName();
        }
        if (classType.equals("UIButton")) {
            UIButton b = (UIButton) e;
            if(b.getVisible()) return (UIButton) e;
        }
        return null;
    }

    /**
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
}
