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
    private boolean isPaused;  // Add this flag to track pause state

    // Game element positions
    private float birdX = 100f, birdY = 50f;
    private float pig1X = 400f, pig1Y = 50f;
    private float pig2X = 450f, pig2Y = 50f;
    private float box1X = 350f, box1Y = 50f;
    private float box2X = 500f, box2Y = 50f;
    private float slingshotX = 90f, slingshotY = 50f;

    public Level1Screen(Main game) {
        this.game = game;
        this.isPaused = false;
        initializeScreen();
    }

    private void initializeScreen() {
        batch = new SpriteBatch();

        // Load textures
        levelBackground = new Texture(Gdx.files.internal("level.png"));
        angryBirdTexture = new Texture(Gdx.files.internal("angry_bird.png"));
        pigTexture = new Texture(Gdx.files.internal("pig.png"));
        boxTexture = new Texture(Gdx.files.internal("boxx.png"));
        slingshotTexture = new Texture(Gdx.files.internal("slingshot.png"));

        // Set up viewport and stage
        viewport = new StretchViewport(640, 480);
        stage = new Stage(viewport);

        setupPauseButton();
    }

    private void setupPauseButton() {
        // Create pause button
        Texture pauseTexture = new Texture(Gdx.files.internal("pause.png"));
        Drawable pauseDrawable = new TextureRegionDrawable(pauseTexture);
        ImageButton pauseButton = new ImageButton(pauseDrawable);

        // Add click listener
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseGame();
            }
        });

        // Set up table for layout
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Position pause button
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
        // Clear screen
        ScreenUtils.clear(0.2f, 0.3f, 0.4f, 1f);

        // Update only if not paused
        if (!isPaused) {
            update(delta);
        }

        // Apply viewport and set up batch
        viewport.apply();
        batch.setProjectionMatrix(stage.getCamera().combined);

        // Render game elements
        batch.begin();

        // Draw background
        batch.draw(levelBackground, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // Draw game elements
        batch.draw(slingshotTexture, 70, 102, 100, 110);
        batch.draw(angryBirdTexture, 40, 102, 55, 55);
        batch.draw(pigTexture, 577, 162, 30, 30);
        batch.draw(pigTexture, 515, 162, 30, 30);
        batch.draw(boxTexture, 562, 102, 60, 60);
        batch.draw(boxTexture, 500, 102, 60, 60);

        batch.end();

        // Update and draw stage
        stage.act(delta);
        stage.draw();
    }

    private void update(float delta) {
        // Add game logic updates here
        // This will only run when the game is not paused
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
