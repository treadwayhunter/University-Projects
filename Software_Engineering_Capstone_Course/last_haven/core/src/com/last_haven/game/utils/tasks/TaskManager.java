package com.last_haven.game.utils.tasks;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.assets.AssetManager;
import com.last_haven.game.engines.active_engines.VillagerEngine;
import com.last_haven.game.engines.static_engines.StructureEngine;
import com.last_haven.game.engines.static_engines.resources.*;
import com.last_haven.game.entities.Resource;
import com.last_haven.game.entities.characters.Villager;
import com.last_haven.game.entities.structures.StoreHouse;
import com.last_haven.game.entities.structures.Structure;
import com.last_haven.game.factories.StructureFactory;
import com.last_haven.game.pathfinding.Node;
import com.last_haven.game.pathfinding.NodeGraph;
import com.last_haven.game.pathfinding.Pathfinder;
import com.last_haven.game.utils.Distance;
import com.last_haven.game.utils.ItemStack;
import com.last_haven.game.utils.Position;
import com.last_haven.game.utils.ResourceStore;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that manages all the tasks in the game.
 * This class takes a task and fulfills the logic needed to complete a task.
 */
public class TaskManager {
    // Create a task manager, fill it with all the engines and graph
    AssetManager assetManager;
    FoliageEngine foliageEngine;
    RockEngine rockEngine;
    FoodEngine foodEngine;
    WaterEngine waterEngine;
    VillagerEngine villagerEngine;
    StructureEngine structureEngine;
    NodeGraph graph;
    Pathfinder pathfinder;

    public TaskManager(AssetManager assetManager, VillagerEngine villagerEngine, FoliageEngine foliageEngine,
                       RockEngine rockEngine, FoodEngine foodEngine, WaterEngine waterEngine,
                       StructureEngine structureEngine, NodeGraph graph, Pathfinder pathfinder) {
        this.assetManager = assetManager;
        this.villagerEngine = villagerEngine;
        this.foliageEngine = foliageEngine;
        this.rockEngine = rockEngine;
        this.foodEngine = foodEngine;
        this.waterEngine = waterEngine;
        this.graph = graph;
        this.pathfinder = pathfinder;
        this.structureEngine = structureEngine;
    }

    //TODO: Get this working, but figure out the resource parameter. Do we need it?

    /**
     * This function allows the provided villager to locate a resource.
     * It calculates the distance to the target, and if the target is close start harvesting.
     * Otherwise, move to the target resource.
     * @param villager The village that is finding the resource.
     */
    public void findResource(Villager villager) {
        Resource target = villager.getTargetResource();
        float distanceToResource = Distance.distance(villager.getPosition(), target.getPosition());
        if (distanceToResource == 1) {
            boolean dead = target.decrementAmount();
            //System.out.println(target.getClass().getSimpleName());
            String resourceClass = target.getClass().getSimpleName();
            if (!dead) incrementResourceStore(resourceClass, villager);
            else {
                Resource newTarget = nearestResource(villager.getPosition(), villager.getTargetResourceType());
                if (newTarget != null) {
                    travelToResource(villager, newTarget);
                }
                else {
                    villager.clearTargetResource();

                }
            }
        }
        else if (distanceToResource > 1) {
            travelToResource(villager, villager.getTargetResource());
        }
    }

    /**
     * Increases the resource amount in the given villager's inventory.
     * If there is room in the villager's inventory, otherwise travel to the storehouse.
     * @param resourceClass The type of resource.
     * @param v The villager that have the target inventory.
     */
    public void incrementResourceStore(String resourceClass, Villager v) {
        switch(resourceClass) {
            case "Tree": {
                //If the adding of resources was unsuccessful
                if (!v.inventory.changeAmount(1, ItemStack.StackType.Wood)) {
                    //Stop collecting resources
                    //v.clearTargetResource();
                    v.setDeliveringResources();
                    travelToStoreHouse(v);
                }
                break;
            }
            case "Rock": {
                if (!v.inventory.changeAmount(1, ItemStack.StackType.Stone)) {
                    //Stop collecting resources
                    //v.clearTargetResource();
                    v.setDeliveringResources();
                    travelToStoreHouse(v);
                }
                break;
            }
            case "Mushroom": {
                if (!v.inventory.changeAmount(1, ItemStack.StackType.Food)) {
                    v.setDeliveringResources();
                    travelToStoreHouse(v);
                }
                break;
            }
            case "WaterResource": {
                if (!v.inventory.changeAmount(1, ItemStack.StackType.Water)) {
                    v.setDeliveringResources();
                    travelToStoreHouse(v);
                }
                break;
            }
            default: System.out.println("Something went wrong with resource: " + resourceClass);
        }
    }

    /**
     * This function actually moves the villager to the target resource.
     * @param villager The villager that s moving.
     * @param target The resource that the villager needs to move to.
     */
    public void travelToResource(Villager villager, Resource target) {
        //System.out.println("Travel To Resource called");
        Node villagerNode = graph.getNode(villager.getPosition());
        GraphPath<Node> path = pathToResource(villagerNode, target.getPosition());
        if (path != null) {
            if (villager.getTargetResource() != target) villager.setTargetResource(target);
        }
        else {
            villager.clearTargetResource();
        }
        villager.setCurrentPath(path);
    }


    /**
     * This function locates and returns the nearest resource of a specific type based on the villager's position.
     * @param villagerPosition The current location of the reference villager.
     * @param resourceType The type of resource that the villager is looking for.
     * @return The nearest resource that the villager is looking for.
     */
    private Resource nearestResource(Position villagerPosition, String resourceType) {
        List<Position> resourcePositions = new ArrayList<>();
        int round = 1;

        ResourceEngine resourceEngine = null;
        Resource closestResource = null;
        switch(resourceType) {
            case "Tree": resourceEngine = foliageEngine; break;
            case "Rock": resourceEngine = rockEngine; break;
            case "Mushroom": resourceEngine = foodEngine; break;
            case "WaterResource": resourceEngine = waterEngine; break;
            default: System.out.println("Problem picking an engine to find resource");
        }

        if (resourceEngine != null) {
            while (resourcePositions.size() == 0 || round < 5) {
                for (int i = -1 * round; i <= round; i++) {
                    for (int j = -1 * round; j <= round; j++) {
                        Position position = new Position(villagerPosition.getX() + i, villagerPosition.getY() + j);
                        //if (foliageEngine.getTree(position) != null) resourcePositions.add(position);
                        if (resourceEngine.getResource(position) != null) resourcePositions.add(position);
                    }
                }
                round++;
            }


            for (Position p : resourcePositions) {
                if (closestResource == null) {
                    //closestResource = foliageEngine.getTree(p);
                    closestResource = resourceEngine.getResource(p);
                } else {
                    if (Distance.distance(villagerPosition, p) < Distance.distance(villagerPosition, closestResource.getPosition())) {
                        //closestResource = foliageEngine.getTree(p);
                        closestResource = resourceEngine.getResource(p);
                    }
                }
            }
        }
        return closestResource;
    }

    /**
     * Finds the path to a resource. A villager cannot "stand" on a resource, but can be to the top, bottom, left,
     * or right of a resource. Each position will be checked, and the shortest path will be chosen.
     * @param fromNode the originating node.
     * @param x the x position of a target resource.
     * @param y the y position of a target resource.
     * @return the Path a villager should traverse.
     */
    private GraphPath<Node> pathToResource(Node fromNode, float x, float y) {
        Node toAboveTree = graph.getNode(new Position(x, y + 1));
        Node toBelowTree = graph.getNode(new Position(x, y - 1));
        Node toRightTree = graph.getNode(new Position(x + 1, y));
        Node toLeftTree = graph.getNode(new Position(x - 1, y));

        GraphPath<Node> pathAboveTree = pathfinder.getPath(fromNode, toAboveTree);
        GraphPath<Node> pathBelowTree = pathfinder.getPath(fromNode, toBelowTree);
        GraphPath<Node> pathRightTree = pathfinder.getPath(fromNode, toRightTree);
        GraphPath<Node> pathLeftTree = pathfinder.getPath(fromNode, toLeftTree);
        GraphPath<Node> shortestPath = null;
        if (pathAboveTree != null) {
            //System.out.println("Above: " + pathAboveTree.getCount());
            shortestPath = pathAboveTree;
        }
        if (pathBelowTree != null) {
            //System.out.println("Below: " + pathBelowTree.getCount());
            if(shortestPath == null)
                shortestPath = pathBelowTree;
            else
            if (pathBelowTree.getCount() < shortestPath.getCount())
                shortestPath = pathBelowTree;
        }
        if (pathRightTree != null) {
            //System.out.println("Right: " + pathRightTree.getCount());
            if(shortestPath == null)
                shortestPath = pathRightTree;
            else
            if (pathRightTree.getCount() < shortestPath.getCount())
                shortestPath = pathRightTree;
        }
        if (pathLeftTree != null) {
            //System.out.println("Left: " + pathLeftTree.getCount());
            if(shortestPath == null)
                shortestPath = pathLeftTree;
            else
            if (pathLeftTree.getCount() < shortestPath.getCount())
                shortestPath = pathLeftTree;
        }

        return shortestPath;
    }

    /**
     * This function calculates the path, as a list of nodes, that the villager will travel.
     * @param fromNode The origin of the path calculation.
     * @param position The target resource, as a position.
     * @return The path the villager needs to travel.
     */
    private GraphPath<Node> pathToResource(Node fromNode, Position position) {
        float worldX = position.getX();
        float worldY = position.getY();

        return pathToResource(fromNode, worldX, worldY);
    }

    /**
     * This task causes the villager to move to the storehouse.
     * This is normally called when the villager attempts to get resources while the villager's inventory is full.
     * @param villager The villager that will travel to the storehouse.

     */
    public void travelToStoreHouse(Villager villager) {

        Structure nearest = nearestStructure(villager); // finds the nearest structure to the villager
        Node villagerNode = graph.getNode(villager.getPosition());
        GraphPath<Node> path = pathToResource(villagerNode, nearest.getPosition()); //"resource" is the structure

        villager.setCurrentPath(path);

    }

    /**
     * This function locates the closest building to the villager.
     * @param villager The villager that needs to locate a building.
     * @return The building that is the closest to the villager.
     */
    private Structure nearestStructure(Villager villager) {
        List<Structure> storeHouseList = structureEngine.getStoreHouseList();

        Structure closestStructure = null;


        for (Structure s : storeHouseList) {
            if (closestStructure == null) {
                closestStructure = s;
            }
            else {
                if(Distance.distance(villager.getPosition(), s.getPosition())
                        < Distance.distance(villager.getPosition(), closestStructure.getPosition())) {
                    closestStructure = s;
                }
            }
        }

        return closestStructure;
    }

    /**
     * This function builds a storehouse using a villager, at a position, using up the appropriate amount of resources in the storage.
     * @param villager The villager that will do the building.
     * @param position The location to build the structure.
     */
    public void BuildStoreHouse(Villager villager, Position position) {
        // let's say 500 wood and 100 stone
        int woodAmount = ResourceStore.getWood();
        int stoneAmount = ResourceStore.getStone();


        if(structureEngine.getEntity(position) == null) { // place a new structure
            structureEngine.addEntity(StructureFactory.createStoreHouse(assetManager, position.getX(), position.getY(), 0));
            if(woodAmount >= 500 && stoneAmount >= 100) {
                ResourceStore.addWood(-500);
                ResourceStore.addStone(-100);
            }
            else {
                //System.out.println("The structure can't be built");
                villager.setConstructionPos(null); // the building can't be built
            }

        }
        else { // a structure was there, increment its build
            //System.out.println("Building...");
            StoreHouse sh = (StoreHouse) structureEngine.getEntity(position);
            if(sh.incrementBuild()) {
                sh.update();
                villager.setConstructionPos(null); // no longer building anything
            }
        }
    }
}
