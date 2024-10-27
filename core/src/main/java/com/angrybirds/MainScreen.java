package com.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainScreen implements Screen {
    private final Main game;
    private SpriteBatch batch;
    private Texture background;
    private Stage stage;
    private Viewport viewport;
    private Texture backButtonTexture;
    private Texture level1Texture;
    private Texture level2Texture;
    private Texture level3Texture;

    public MainScreen(Main game) {
        this.game = game;
        batch = new SpriteBatch();

        viewport = new StretchViewport(640, 480);
        stage = new Stage(viewport);

        try {
            background = new Texture(Gdx.files.internal("angry.png"));
        } catch (Exception e) {
            Gdx.app.error("MainScreen", "Background image not found or failed to load: " + e.getMessage());
        }

        Skin skin;
        try {
            skin = new Skin(Gdx.files.internal("uiskin.json"));
        } catch (Exception e) {
            Gdx.app.error("MainScreen", "UI skin file not found or failed to load: " + e.getMessage());
            return;
        }

        try {
            backButtonTexture = new Texture(Gdx.files.internal("back.png"));
            level1Texture = new Texture(Gdx.files.internal("level1.png"));
            level2Texture = new Texture(Gdx.files.internal("level2.png")); // Load level 2 texture
            level3Texture = new Texture(Gdx.files.internal("level3.png")); // Load level 3 texture
        } catch (Exception e) {
            Gdx.app.error("MainScreen", "Button image not found or failed to load: " + e.getMessage());
            return;
        }

        ImageButton backButton = new ImageButton(new TextureRegionDrawable(backButtonTexture));
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenu(game));
            }
        });

        ImageButton level1Button = new ImageButton(new TextureRegionDrawable(level1Texture));
        level1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Level1Screen(game));
            }
        });

        ImageButton level2Button = new ImageButton(new TextureRegionDrawable(level2Texture));
        level2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Level1Screen(game));
            }
        });

        ImageButton level3Button = new ImageButton(new TextureRegionDrawable(level3Texture));
        level3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Level1Screen(game));
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.top().left();
        table.add(backButton).padTop(10).padLeft(10).width(60).height(40).left();
        table.row();
        table.add(level1Button).expand().left().width(200).height(110).padBottom(10);
        table.add(level2Button).expand().center().width(200).height(110).padBottom(10);
        table.add(level3Button).expand().right().width(200).height(110).padBottom(10);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0.3f, 0.4f, 1f);
        viewport.apply();

        batch.setProjectionMatrix(stage.getCamera().combined);
        batch.begin();
        if (background != null) {
            batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        }
        batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        if (background != null) background.dispose();
        if (backButtonTexture != null) backButtonTexture.dispose();
        if (level1Texture != null) level1Texture.dispose();
        if (level2Texture != null) level2Texture.dispose();
        if (level3Texture != null) level3Texture.dispose();
    }
}
