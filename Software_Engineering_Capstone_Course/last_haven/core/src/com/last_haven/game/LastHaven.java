package com.last_haven.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.last_haven.game.screens.LastHavenGame;
import com.last_haven.game.screens.MainScreen;
import com.last_haven.game.screens.PauseScreen;

public class LastHaven extends Game {
    /**
     *
     */
    MainScreen mainScreen;
    LastHavenGame lastHavenGame;
    PauseScreen pauseScreen;
    boolean paused;

    @Override
    public void create() {
        mainScreen = new MainScreen(this);
        lastHavenGame = new LastHavenGame(this);
        pauseScreen = new PauseScreen();
        paused = false;

        this.setScreen(mainScreen);
    }
}
