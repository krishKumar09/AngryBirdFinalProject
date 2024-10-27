package com.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Level1Screen implements Screen {
    private final Main game;
    private SpriteBatch batch;
    private Texture levelBackground;
    private Texture angryBirdTexture;
    private Texture pigTexture;
    private Texture boxTexture;
    private Texture slingshotTexture;
    private Stage stage;
    private Viewport viewport;
    private boolean isPaused;


    public Level1Screen(Main game) {
        this.game = game;
        this.isPaused = false;
        initializeScreen();
    }

    private void initializeScreen() {
        batch = new SpriteBatch();


        levelBackground = new Texture(Gdx.files.internal("level.png"));
        angryBirdTexture = new Texture(Gdx.files.internal("angry_bird.png"));
        pigTexture = new Texture(Gdx.files.internal("pig.png"));
        boxTexture = new Texture(Gdx.files.internal("boxx.png"));
        slingshotTexture = new Texture(Gdx.files.internal("slingshot.png"));


        viewport = new StretchViewport(640, 480);
        stage = new Stage(viewport);

        setupPauseButton();
    }

    private void setupPauseButton() {

        Texture pauseTexture = new Texture(Gdx.files.internal("pause.png"));
        Drawable pauseDrawable = new TextureRegionDrawable(pauseTexture);
        ImageButton pauseButton = new ImageButton(pauseDrawable);


        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseGame();
            }
        });


        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);


        table.add(pauseButton).padBottom(10).expand().top().width(40).height(40).left();
    }

    private void pauseGame() {
        isPaused = true;
        game.setScreen(new PauseMenuScreen(game, this));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0.2f, 0.3f, 0.4f, 1f);


        if (!isPaused) {
            update(delta);
        }


        viewport.apply();
        batch.setProjectionMatrix(stage.getCamera().combined);


        batch.begin();


        batch.draw(levelBackground, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());


        batch.draw(slingshotTexture, 70, 102, 100, 110);
        batch.draw(angryBirdTexture, 40, 102, 55, 55);
        batch.draw(pigTexture, 577, 162, 30, 30);
        batch.draw(pigTexture, 515, 162, 30, 30);
        batch.draw(boxTexture, 562, 102, 60, 60);
        batch.draw(boxTexture, 500, 102, 60, 60);

        batch.end();


        stage.act(delta);
        stage.draw();
    }

    private void update(float delta) {

    }

    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        isPaused = true;
    }

    @Override
    public void resume() {
        isPaused = false;
    }

    @Override
    public void hide() {
        isPaused = true;
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        levelBackground.dispose();
        angryBirdTexture.dispose();
        pigTexture.dispose();
        boxTexture.dispose();
        slingshotTexture.dispose();
    }
}
