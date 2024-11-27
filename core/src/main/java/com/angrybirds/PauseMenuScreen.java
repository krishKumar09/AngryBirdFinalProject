package com.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PauseMenuScreen implements Screen {
    private static final float WORLD_WIDTH = 640;
    private static final float WORLD_HEIGHT = 480;

    private final Main game;
    private final Screen previousScreen;
    private SpriteBatch batch;
    private Texture resumeButtonTexture;
    private Texture newGameButtonTexture;
    private Texture homeButtonTexture;
    private Rectangle resumeButtonBounds;
    private Rectangle newGameButtonBounds;
    private Rectangle homeButtonBounds;
    private Viewport viewport;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;
    private Vector3 touchPoint;

    public PauseMenuScreen(final Main game, Screen previousScreen) {
        this.game = game;
        this.previousScreen = previousScreen;
        batch = new SpriteBatch();
        // Use FitViewport instead of StretchViewport for better scaling
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        shapeRenderer = new ShapeRenderer();
        touchPoint = new Vector3();

        try {
            resumeButtonTexture = new Texture(Gdx.files.internal("resume.png"));
            newGameButtonTexture = new Texture(Gdx.files.internal("newgame.png"));
            homeButtonTexture = new Texture(Gdx.files.internal("home.png"));
        } catch (Exception e) {
            Gdx.app.error("PauseMenuScreen", "Image not found: " + e.getMessage());
        }

        // Initialize font
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        // Initialize button positions - will be updated in resize()
        updateButtonPositions();
    }

    private void updateButtonPositions() {



        resumeButtonBounds = new Rectangle(125, 300, 100, 100);
        newGameButtonBounds = new Rectangle(130, 170  , 105, 100);
        homeButtonBounds = new Rectangle(110, 60, 123, 97);


        float fontScale = WORLD_HEIGHT * 0.003f;
        font.getData().setScale(fontScale);
    }

    @Override
    public void render(float delta) {

        if (previousScreen != null) {
            previousScreen.render(delta);
        }

        viewport.apply();


        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.7f);
        shapeRenderer.rect(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);


        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();


        batch.draw(resumeButtonTexture, resumeButtonBounds.x, resumeButtonBounds.y,
            resumeButtonBounds.width, resumeButtonBounds.height);
        batch.draw(newGameButtonTexture, newGameButtonBounds.x, newGameButtonBounds.y,
            newGameButtonBounds.width, newGameButtonBounds.height);
        batch.draw(homeButtonTexture, homeButtonBounds.x, homeButtonBounds.y,
            homeButtonBounds.width, homeButtonBounds.height);



        font.draw(batch, "Resume", 360  ,
            363);
        font.draw(batch, "New Game", 360,
            238);
        font.draw(batch, "Home", 360,
            130);

        batch.end();

        // Handle input
        if (Gdx.input.justTouched()) {
            touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touchPoint);

            if (resumeButtonBounds.contains(touchPoint.x, touchPoint.y)) {
                if (previousScreen instanceof Level1Screen) {
//                    ((Level1Screen) previousScreen).setPaused(false);
                }
                game.setScreen(previousScreen);
            } else if (newGameButtonBounds.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new Level1Screen(game));
            } else if (homeButtonBounds.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new MainScreen(game));
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        updateButtonPositions(); // Recalculate button positions when resized
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        batch.dispose();
        if (resumeButtonTexture != null) resumeButtonTexture.dispose();
        if (newGameButtonTexture != null) newGameButtonTexture.dispose();
        if (homeButtonTexture != null) homeButtonTexture.dispose();
        font.dispose();
        shapeRenderer.dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}
