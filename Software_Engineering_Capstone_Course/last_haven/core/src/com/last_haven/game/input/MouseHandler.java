package com.last_haven.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.last_haven.game.engines.active_engines.VillagerEngine;
import com.last_haven.game.engines.static_engines.UIEngine;
import com.last_haven.game.engines.static_engines.resources.FoliageEngine;
import com.last_haven.game.engines.static_engines.resources.FoodEngine;
import com.last_haven.game.engines.static_engines.resources.RockEngine;
import com.last_haven.game.engines.static_engines.resources.WaterEngine;
import com.last_haven.game.entities.characters.Villager;
import com.last_haven.game.entities.terrain.Water;
import com.last_haven.game.entities.ui.UIButton;
import com.last_haven.game.pathfinding.Node;
import com.last_haven.game.pathfinding.NodeGraph;
import com.last_haven.game.pathfinding.Pathfinder;
import com.last_haven.game.utils.ItemStack;
import com.last_haven.game.utils.Position;

/**
 * This class contains methods that check for mouse input.
 */
public class MouseHandler {

    /**
     * Checks for Left Clicks. A left click is for selecting Entities.
     * Checks for UI Entities first, then checks for Game Entities next.
     * @param uiViewPort the viewport containing UI Entities.
     * @param gameViewport the viewport containing Game Entities.
     * @param villagerEngine the Engine responsible for maintaining Villagers
     * @param uiEngine the Engine responsible for maintaining the UI.
     */
    public static void leftClick(Viewport uiViewPort, Viewport gameViewport, VillagerEngine villagerEngine, UIEngine uiEngine) {
        if (Gdx.input.isButtonJustPressed((Input.Buttons.LEFT))) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.input.getY();
            // Check for UI element first
            boolean checkClick = checkButton(uiViewPort, uiEngine, mouseX, mouseY);

            // Check for villager.
            if (!checkClick) checkClick = checkVillager(gameViewport, villagerEngine, mouseX, mouseY);

            // Check for structure
        }
    }

    /**
     * Checks for Right Clicks. Right Clicks are for issuing actions to Villagers.
     * @param viewport the viewport managing Game Entities.
     * @param graph the NodeGraph for Villagers to traverse.
     * @param pathfinder the Pathfinder used to determine the traversal for Villagers.
     * @param foliageEngine the Engine responsible for maintaining foliage.
     * @param rockEngine the Engine responsible for maintaining rocks.
     * @param foodEngine the Engine responsible for maintaining food.
     * @param waterEngine the Engine responsible for maintaining water.
     */
    public static void rightClick(Viewport viewport, NodeGraph graph, Pathfinder pathfinder,
                                  FoliageEngine foliageEngine, RockEngine rockEngine,
                                  FoodEngine foodEngine, WaterEngine waterEngine) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {


            //System.out.println("RIGHT CLICK");
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.input.getY();
            Vector2 worldCoordinates = viewport.unproject(new Vector2(mouseX, mouseY));
            float worldX = (float)Math.floor(worldCoordinates.x); // floor to get the nearest value
            float worldY = (float)Math.floor(worldCoordinates.y);

            Node toNode = graph.getNode(worldX, worldY);
            Villager selected = SelectedVillagers.getSelected();
            if (selected != null) {
                //selected.getDetails();
                Position p = selected.getPosition();
                Node fromNode = graph.getNode(p);

                GraphPath<Node> path = pathfinder.getPath(fromNode, toNode);

                // TODO handle this better
                if (path != null) {
                    selected.setCurrentPath(path); // Node was empty, can walk to the node
                    selected.clearTargetResource();
                    //selected.setConstructionPos(null);
                    if(SelectedVillagers.getBuildMode()) {
                        selected.setConstructionPos(toNode.getPosition());
                        SelectedVillagers.stopBuildMode();
                    }
                }
                else { // Node had an object in it. Check if it's a Resource
                    // check that a tree or rock is in that location?
                    Position resourcePos = new Position(worldX, worldY);
                    if (foliageEngine.getTree(resourcePos) != null) {
                        selected.setTargetResource(foliageEngine.getTree(resourcePos));
                    }
                    // else if rockEngine.getRock
                    else if (rockEngine.getRock(resourcePos) != null) {
                        selected.setTargetResource(rockEngine.getRock(resourcePos));
                    }
                    else if (foodEngine.getFood(resourcePos) != null) {
                        selected.setTargetResource(foodEngine.getMushroom(resourcePos));
                    }
                    else if (waterEngine.getWater(resourcePos) != null) {
                        System.out.println("Water was clicked.....");
                        selected.setTargetResource(waterEngine.getWater(resourcePos));
                    }
                }
            }
        }
    }

    /**
     * Checks for either mouse to be pressed. Checked every render.
     * @param uiViewport the viewport that contains UI Entities.
     * @param gameViewport the viewport that contains Game Entities.
     * @param villagerEngine the Engine responsible for managing Villagers.
     * @param foliageEngine the Engine responsible for managing Foliage.
     * @param rockEngine the Engine responsible for managing Rocks.
     * @param foodEngine the Engine responsible for managing Food.
     * @param waterEngine the Engine responsible for managing Water.
     * @param uiEngine the Engine responsible for managing UI Entities.
     * @param graph the NodeGraph for villagers to traverse.
     * @param pathfinder the Pathfinder that creates paths for Villager traversal.
     */
    public static void clickHandler(Viewport uiViewport, Viewport gameViewport, VillagerEngine villagerEngine,
                                    FoliageEngine foliageEngine, RockEngine rockEngine, FoodEngine foodEngine, WaterEngine waterEngine,
                                    UIEngine uiEngine, NodeGraph graph, Pathfinder pathfinder) {
        leftClick(uiViewport, gameViewport, villagerEngine, uiEngine);
        rightClick(gameViewport, graph, pathfinder, foliageEngine, rockEngine, foodEngine, waterEngine);
    }

    /**
     * A method for checking if a button was clicked.
     * @param viewport the viewport responsible for UI Entities.
     * @param uiEngine the Engine responsible for UI Entities.
     * @param mouseX the mouse X position that was clicked.
     * @param mouseY the mouse Y position that was clicked.
     * @return true if a button was clicked, false otherwise.
     */
    private static boolean checkButton(Viewport viewport, UIEngine uiEngine, float mouseX, float mouseY) {
        System.out.println("Checking for button");

        Vector2 worldCoordinates = viewport.unproject(new Vector2(mouseX, mouseY));

        float worldX = worldCoordinates.x;
        float worldY = worldCoordinates.y;
        System.out.println((int)worldX + " " + (int)worldY);

        UIButton b = uiEngine.getButton(new Position((int)worldX, (int)worldY));
        if (b != null) {
            System.out.println("Button Pressed");
            SelectedVillagers.setBuildMode();
            return true;
        }
        else {
            System.out.println("Button is null for some reason");
        }
        return false;
    }

    /**
     * Checks if a Villager has been clicked.
     * @param viewport the viewport responsible for Game Entities.
     * @param villagerEngine the Engine responsible for Villagers.
     * @param mouseX the mouse X position that was clicked.
     * @param mouseY the mouse Y position that was clicked.
     * @return
     */
    private static boolean checkVillager(Viewport viewport, VillagerEngine villagerEngine, float mouseX, float mouseY) {
        Vector2 worldCoordinates = viewport.unproject(new Vector2(mouseX, mouseY));

        float worldX = worldCoordinates.x;
        float worldY = worldCoordinates.y;
        
        
        Villager v = villagerEngine.getVillager(new Position(worldX, worldY));
        if (v != null) {

            if (SelectedVillagers.getSelected() != null) {
                SelectedVillagers.deSelect(); // deselect the current villager
            }
            SelectedVillagers.setSelected(v);
            return true;
        }
        else {
            return false;
        }
    }
}

