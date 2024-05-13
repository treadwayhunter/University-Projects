package com.last_haven.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * This class manages inputs related to Camera movement.
 */
public class CameraHandler {

    OrthographicCamera camera;
    Viewport viewport;
    SpriteBatch batch;
    public CameraHandler(OrthographicCamera camera, Viewport viewport, SpriteBatch batch) {
        this.camera = camera;
        this.viewport = viewport;
        this.batch = batch;
    }

    /**
     * This updates the Camera position when called.
     * @param deltaTime
     */
    public void update(float deltaTime) {
        handleCameraInput(deltaTime);
    }

    /**
     * Accepts Arrow Key Input, and adjusts the Camera position accordingly.
     * @param deltaTime
     */
    private void handleCameraInput(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0, 5 * deltaTime, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0, -5 * deltaTime, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-5 * deltaTime, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(5 * deltaTime, 0, 0);
        }

    }
}

