package com.last_haven.game.factories;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.last_haven.game.entities.rocks.Rock;
import com.last_haven.game.utils.Position;

public class RockFactory extends Factory {
    /**
     * Creates a Rock at the given Position.
     * @param assetManager manages the asset of the Rock.
     * @param position the Position the Rock will be created.
     * @return a new Rock at the given Position
     */
    public static Rock createRock(AssetManager assetManager, Position position) {
        return new Rock(assetManager, position);
    }
}
