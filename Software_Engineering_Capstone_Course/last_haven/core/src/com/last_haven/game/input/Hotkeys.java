package com.last_haven.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * A class that manages key presses for the game.
 */
public class Hotkeys {
    /**
     * Checks if any key has been pressed. Currently only checks for the escape key.
     */
    public static void check() {
        escapeKey();
    }

    /**
     * Checks if the escape key has been pressed.
     */
    private static void escapeKey() {

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            SelectedVillagers.deSelect();
        }
    }

}
