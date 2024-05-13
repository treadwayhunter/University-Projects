package com.last_haven.game.entities.structures;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.last_haven.game.entities.Entity;
import com.last_haven.game.pathfinding.NodeGraph;
import com.last_haven.game.utils.Position;

public abstract class Structure extends Entity {

    protected int buildPercentage;

    public Structure(AssetManager assetManager, Position position, float unitWidth, float unitHeight) {
        super(assetManager, position, unitWidth, unitHeight);
    }

    public Structure(AssetManager assetManager, float posX, float posY, float unitWidth, float unitHeight) {
        super(assetManager, posX, posY, unitWidth, unitHeight);
    }

    public abstract void poisonArea(NodeGraph graph);

    public boolean isBuilt() {
        return buildPercentage >= 100; // set to over 100 just in case there is some issue with the increment
    }

    public boolean incrementBuild() {
        if(buildPercentage < 100) {
            buildPercentage++;
        }
        return buildPercentage == 100;
    }
}
