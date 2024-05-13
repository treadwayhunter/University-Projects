package com.last_haven.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainScreen implements Screen {
    Game game;
    Stage stage;
    SpriteBatch batch;
    BitmapFont font;
    Skin skin;
    TextureAtlas atlas;

    public MainScreen(Game game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        font = new BitmapFont();
        atlas = new TextureAtlas(Gdx.files.internal("ui/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        skin.addRegions(atlas);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        TextButton startButton = new TextButton("Start", skin, "default");
        startButton.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        startButton.setSize(100, 40);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LastHavenGame(game));
            }
        });

        stage.addActor(startButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.2f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "Last Haven", (float) Gdx.graphics.getWidth() / 2, ((float) Gdx.graphics.getHeight() / 2) + 100);
        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        font.dispose();
        skin.dispose();
        atlas.dispose();
    }
}
