package com.last_haven.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.last_haven.game.engines.active_engines.VillagerEngine;
import com.last_haven.game.engines.static_engines.StructureEngine;
import com.last_haven.game.engines.static_engines.resources.FoliageEngine;
import com.last_haven.game.engines.static_engines.resources.FoodEngine;
import com.last_haven.game.engines.static_engines.resources.RockEngine;
import com.last_haven.game.engines.static_engines.TerrainEngine;
import com.last_haven.game.engines.static_engines.UIEngine;
import com.last_haven.game.engines.static_engines.resources.WaterEngine;
import com.last_haven.game.entities.ui.Background;
import com.last_haven.game.entities.ui.UIButton;
import com.last_haven.game.factories.*;
import com.last_haven.game.input.CameraHandler;
import com.last_haven.game.input.Hotkeys;
import com.last_haven.game.input.MouseHandler;
import com.last_haven.game.pathfinding.Node;
import com.last_haven.game.pathfinding.NodeGraph;
import com.last_haven.game.pathfinding.Pathfinder;
import com.last_haven.game.utils.*;
import com.last_haven.game.utils.tasks.TaskManager;

/**
 * This is the object that maintains the game screen.
 */
public class LastHavenGame implements Screen {
    Game game;
    AssetManager assetManager;
    SpriteBatch batch;

    BitmapFont font;
    Viewport gameViewPort, uiViewPort;
    OrthographicCamera gameCamera, uiCamera;
    final float screenWidth = 32;
    final float screenHeight = 18;
    final int startAreaSize = 5;
    final int SIZE = GameVars.SIZE;

    final int UI_SCALE = 32;
    int tick = 0;
    int tickCounter = 0;
    boolean currentTick = true;

    TerrainEngine terrainEngine;
    CameraHandler cameraHandler;
    FoliageEngine foliageEngine;
    VillagerEngine villagerEngine;
    RockEngine rockEngine;
    FoodEngine foodEngine;
    WaterEngine waterEngine;

    StructureEngine structureEngine;
    UIEngine uiEngine;

    Pathfinder pathfinder;
    Noise foliageNoise, terrainNoise, foodNoise;

    NodeGraph graph;

    TaskManager taskManager;

    /**
     * A constructor to create a new LastHavenGame screen.
     * @param game represents the class that maintains the entire executable process.
     */
    public LastHavenGame(Game game) {
        this.game = game;
    }

    /**
     * When this method is called, it creates all the objects necessary for the game to run.
     * It also does some preliminary setting up of classes with static methods, such as the ResourceStore.
     */
    @Override
    public void show() {
        assetManager = new AssetManager();
        assetManagerInit();
        batch = new SpriteBatch();
        font = new BitmapFont(); // default font
        gameCamera = new OrthographicCamera();
        gameViewPort = new ExtendViewport(screenWidth, screenHeight, gameCamera);
        uiCamera = new OrthographicCamera();
        uiViewPort = new FitViewport(screenWidth * UI_SCALE, screenHeight * UI_SCALE, uiCamera);
        villagerEngine = new VillagerEngine(gameCamera, gameViewPort, batch);

        uiEngine = new UIEngine(uiCamera, uiViewPort, batch, font, villagerEngine);
        Background villagerInfo = new Background(assetManager, -15 * UI_SCALE, -8 * UI_SCALE, 5 * UI_SCALE, 8 * UI_SCALE, "villager_info");
        Background resourceBar = new Background(assetManager, -16 * UI_SCALE, 8 * UI_SCALE, screenWidth * UI_SCALE, 1 * UI_SCALE, "resource_info");
        UIButton buildButton = new UIButton(assetManager, -9 * UI_SCALE, -8 * UI_SCALE, assetManager.get(Asset.HAMMER, Texture.class));
        uiEngine.addEntity(villagerInfo);
        uiEngine.addEntity(resourceBar);
        uiEngine.addEntity(buildButton);

        if(ResourceStore.getWood() != 450 && ResourceStore.getStone() != 75) {
            ResourceStore.addWood(-ResourceStore.getWood());
            ResourceStore.addStone(-ResourceStore.getStone());

            ResourceStore.addWood(450);
            ResourceStore.addStone(75);
        }


        graph = new NodeGraph(-SIZE, SIZE, -SIZE, SIZE);
        cameraHandler = new CameraHandler(gameCamera, gameViewPort, batch);
        terrainEngine = new TerrainEngine(gameCamera, gameViewPort, batch);
        foliageEngine = new FoliageEngine(gameCamera, gameViewPort, batch, graph);
        rockEngine = new RockEngine(gameCamera, gameViewPort, batch, graph);
        foodEngine = new FoodEngine(gameCamera, gameViewPort, batch, graph);
        waterEngine = new WaterEngine(gameCamera, gameViewPort, batch, graph);
        structureEngine = new StructureEngine(gameCamera, gameViewPort, batch, graph, villagerEngine, assetManager, taskManager);

        foliageNoise = new Noise(SIZE * 2, SIZE * 2);
        terrainNoise = new Noise(SIZE * 2, SIZE * 2);
        foodNoise = new Noise(SIZE * 2, SIZE * 2);

        terrainGeneration(); // method for generating terrain
        foliageGeneration(); // method for generating foliage
        rockGeneration();
        foodGeneration();
        waterResourceGeneration(); // generates water resources. Different to Terrain Water.
        startingAreaGeneration();
        nodeCheck();
        graph.initConnections();
        pathfinder = new Pathfinder(graph);
        taskManager = new TaskManager(assetManager, villagerEngine, foliageEngine, rockEngine, foodEngine, waterEngine,
                structureEngine, graph, pathfinder);
        structureEngine.updateTaskManager(taskManager);
        villagerEngine.addEntity(VillagerFactory.FemaleVillagerFactory(assetManager, -1, -1, taskManager));
        villagerEngine.addEntity(VillagerFactory.FemaleVillagerFactory(assetManager, -2, -2, taskManager));
        villagerEngine.addEntity(VillagerFactory.FemaleVillagerFactory(assetManager, -2, -3, taskManager));
        villagerEngine.addEntity(VillagerFactory.FemaleVillagerFactory(assetManager, -2, -4, taskManager));
        villagerEngine.addEntity(VillagerFactory.FemaleVillagerFactory(assetManager, -3, -2, taskManager));
        villagerEngine.addEntity(VillagerFactory.FemaleVillagerFactory(assetManager, -3, -3, taskManager));
        villagerEngine.addEntity(VillagerFactory.FemaleVillagerFactory(assetManager, -3, -4, taskManager));
        villagerEngine.addEntity(VillagerFactory.FemaleVillagerFactory(assetManager, -4, -4, taskManager));
        structureEngine.addEntity(StructureFactory.createStoreHouse(assetManager, 1, 1, 100));
    }

    /**
     * Render is called at Game's framerate, redrawing the screen, and updating appropriate models.
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        float deltaTime = Gdx.graphics.getDeltaTime();
        if (currentTick) {
            villagerEngine.update(deltaTime, tick); // this should only be called per tick
            foliageEngine.update(deltaTime, tick);
            rockEngine.update(deltaTime, tick);
            foodEngine.update(deltaTime, tick);
        }
        currentTick = false;
        terrainEngine.draw();   // draws the terrain
        foliageEngine.draw();   // draws all foliage
        rockEngine.draw();      // draws all rocks
        foodEngine.draw();      // draws all mushrooms/food
        structureEngine.draw(); // draws all structures
        villagerEngine.draw();  // draws villagers
        if(villagerEngine.VillagersAlive()) {
            game.setScreen(new GameOverScreen(game));
        }
        cameraHandler.update(deltaTime);
        Hotkeys.check();
        uiEngine.draw();

        incrementTickCounter();
        incrementTick();

        MouseHandler.clickHandler(uiViewPort, gameViewPort, villagerEngine, foliageEngine, rockEngine,
                foodEngine, waterEngine, uiEngine, graph, pathfinder);
    }

    /**
     * The Game calls this when the screen resizes. This is also called once when the Screen is created.
     * @param width the width of the game screen.
     * @param height the width of the game screen.
     */
    @Override
    public void resize(int width, int height) {
        gameViewPort.update(width, height);
        uiViewPort.update(width, height);
    }

    /**
     * Not in use.
     * Called when the game is paused.
     */
    @Override
    public void pause() {

    }

    /**
     * Not in use.
     * Called when the game is resumed from a paused state.
     */
    @Override
    public void resume() {

    }

    /**
     * Not in use.
     * Called when the screen is hidden.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when the screen is destroyed. The SpriteBatch needs to be destroyed, and the assetManager with all of its
     * assets must also be disposed.
     */
    @Override
    public void dispose() {
        batch.dispose();
        assetManager.dispose();
    }

    /**
     * This method manages the terrain generation and initial Graph.
     */
    private void terrainGeneration() {
        for (int i = 0; i < SIZE * 2; i++) {
            for (int j = 0; j < SIZE * 2; j++) {
                //terrainEngine.addEntity(TerrainFactory.TerrainFactory("grass", img, i, j));
                //terrainEngine.addEntity(TerrainFactory.createGrass(img, i, j));
                double n = terrainNoise.noise(i, j);
                if (n >= 0.9) {
                    terrainEngine.addEntity(TerrainFactory.createStone(assetManager, i - SIZE, j - SIZE));
                    //System.out.println("Stone created");
                }
                else if (n >= 0.1 && n < 0.9) {
                    terrainEngine.addEntity(TerrainFactory.createGrass(assetManager, i - SIZE, j - SIZE));
                }
                else if (n > 0.01 && n < 0.1) {
                    terrainEngine.addEntity(TerrainFactory.createMud(assetManager, i - SIZE, j - SIZE));
                }
                else {
                    terrainEngine.addEntity(TerrainFactory.createWater(assetManager, i - SIZE, j - SIZE));
                    //waterEngine.addEntity(FoodFactory.createWater(assetManager, i - SIZE, j - SIZE));
                }

                graph.createNode(i - SIZE, j - SIZE);
            }
        }
        RiverGenerator river = new RiverGenerator(new Position(-10, 10), terrainEngine, 90, -90, assetManager);
    }

    /**
     * This method generates the foliage.
     */
    private void foliageGeneration() {
        for (int i = 0; i < SIZE * 2; i++) {
            for (int j = 0; j < SIZE * 2; j++) {
                double n = foliageNoise.noise(i, j);
                if (n < 0.4) {

                    String terrainType = terrainEngine.getTerrain(new Position(i - SIZE, j - SIZE)).getType();
                    if (terrainType != "water" && terrainType != "stone" ) {
                        foliageEngine.addEntity(TreeFactory.TreeFactory(assetManager, i - SIZE, j - SIZE));
                    }
                }
            }
        }
    }

    /**
     * This method generates the rocks.
     */
    private void rockGeneration() {
        for (int i = -SIZE; i < SIZE; i++) {
            for (int j = -SIZE; j < SIZE; j++) {
                String terrainType = terrainEngine.getTerrain(new Position(i, j)).getType();
                if (terrainType == "stone" && Math.random() > 0.3) {
                    rockEngine.addEntity(RockFactory.createRock(assetManager, new Position(i, j)));
                    //System.out.println("Rock made");
                }
                if (terrainType == "grass" && Math.random() > 0.998) {
                    rockEngine.addEntity(RockFactory.createRock(assetManager, new Position(i, j)));
                }
            }
        }
    }

    /**
     * This method generates the food.
     */
    private void foodGeneration() {
        for (int i = 0; i < SIZE * 2; i++) {
            for (int j = 0; j < SIZE * 2; j++) {
                double n = foodNoise.noise(i, j);
                if (n < 0.3) {
                    Position position = new Position(i - SIZE, j - SIZE);
                    String terrainType = terrainEngine.getTerrain(position).getType();
                    if (terrainType.equals("grass") && !terrainOccupied(position)) {
                        foodEngine.addEntity(FoodFactory.createMushroom(assetManager, position));
                    }
                }
            }
        }
    }

    /**
     * This method generates water resources.
     * Note that a WaterResource is different to a Water Terrain tile.
     */
    private void waterResourceGeneration() {
        for(int i = -SIZE; i < SIZE; i++) {
            for(int j = -SIZE; j < SIZE; j++) {
                if (terrainEngine.getTerrain(new Position(i, j)).getType().equals("water")) {
                    //System.out.println("Water spot");
                    waterEngine.addEntity(FoodFactory.createWater(assetManager, new Position(i, j)));
                }
            }
        }
    }

    /**
     * This method carves out a starting area, and ensures no entities are drawn where the villagers will be drawn.
     */
    private void startingAreaGeneration() {
        // More like removal. Remove all water tiles and foliage in an area
        for (int i = -startAreaSize; i < startAreaSize; i++) {
            for (int j = -startAreaSize; j < startAreaSize; j++) {
                //terrainEngine.getTerrain(new Position(i, j)).updateType("grass", img);
                terrainEngine.addEntity(TerrainFactory.createGrass(assetManager, i, j));
                foliageEngine.removeEntity(new Position(i, j));
                rockEngine.removeEntity(new Position(i, j));
                foodEngine.removeEntity(new Position(i, j));
            }
        }
    }

    /**
     * This method forces the graph to check all of its nodes, and disable them if they are water or otherwise occupied.
     */
    private void nodeCheck() {
        for (Node n : graph.getAllNodes()) {
            Position nPos = n.getPosition();
            String terrainType = terrainEngine.getTerrain(nPos).getType();
            float cost = terrainEngine.getTerrain(nPos).getCost();

            if (terrainType.equals("water") || terrainOccupied(nPos)) {
                n.disable();
            }
            n.updateCost(cost);
        }
    }

    /**
     * Determines if a terrain tile is occupied by another object, a tree for example
     * @param position the position of an entity on the map.
     * @return
     */
    private boolean terrainOccupied(Position position) {
        if (foliageEngine.getTree(position) != null || rockEngine.getRock(position) != null || foodEngine.getEntity(position) != null) {
            return true; // terrain is not occupied
        }
        return false; // terrain is occupied
    }


    /**
     * This method simplifies initializing all the textures for the game.
     * Note that each call to load() is asynchronous. finishLoading() puts the game in a blocking state until all
     * load() methods complete.
     */
    private void assetManagerInit() {
        assetManager.load(Asset.GRASS, Texture.class);
        assetManager.load(Asset.WATER_000, Texture.class);
        assetManager.load(Asset.MUD, Texture.class);
        assetManager.load(Asset.STONE_000, Texture.class);
        assetManager.load(Asset.VILLAGER_FEMALE, Texture.class);
        assetManager.load(Asset.TREE, Texture.class);
        assetManager.load(Asset.ROCK, Texture.class);
        assetManager.load(Asset.UI_BACKGROUND, Texture.class);
        assetManager.load(Asset.STORE_HOUSE, Texture.class);
        assetManager.load(Asset.HAMMER, Texture.class);
        assetManager.load(Asset.MUSHROOM_001, Texture.class);
        assetManager.load(Asset.STORE_HOUSE_UNFINISHED, Texture.class);
        assetManager.finishLoading(); // Forces all asynchronous operations to complete before moving on.
    }

    /**
     * Increments the current tickCounter. Every 6 ticks, an event should occur.
     */
    private void incrementTickCounter() {
        tickCounter = (tickCounter + 1) % 6;
    }

    /**
     * Increment the current tick.
     */
    private void incrementTick() {
        if (tickCounter == 4) {
            //System.out.println("Tick incremented");
            tick = (tick + 1) % Integer.MAX_VALUE;
            //System.out.println(tick);
            currentTick = true;
        }
    }
}
