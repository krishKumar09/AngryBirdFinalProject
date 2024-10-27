package com.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenu implements Screen {
    private final Main game; // Reference to the Main game class
    private Stage stage;
    private SpriteBatch batch;
    private Texture background;
    private Viewport viewport;

    public MainMenu(Main game) {
        this.game = game;

        // Use a StretchViewport for responsive design
        viewport = new StretchViewport(640, 480);
        stage = new Stage(viewport);
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("angry.png")); // Your background image

        // Create the angrybirdtext image above the start button
        Texture angrybirdTextTexture = new Texture(Gdx.files.internal("angrybirdtext.png"));
        Image angrybirdTextImage = new Image(angrybirdTextTexture);

        // Create a start button using an image
        Texture startTexture = new Texture(Gdx.files.internal("start.png")); // Ensure the image is named "start.png"
        TextureRegionDrawable startDrawable = new TextureRegionDrawable(startTexture);

        ImageButton startButton = new ImageButton(startDrawable);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game)); // Switch to MainScreen
            }
        });

        // Create a menu button at the bottom-left corner
        Texture menuTexture = new Texture(Gdx.files.internal("menu.png")); // Ensure the image is named "menu.png"
        TextureRegionDrawable menuDrawable = new TextureRegionDrawable(menuTexture);

        ImageButton menuButton = new ImageButton(menuDrawable);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Menu button clicked");
            }
        });

        // Create an exit button at the bottom-right corner
        Texture exitTexture = new Texture(Gdx.files.internal("exit.png")); // Ensure the image is named "exit.png"
        TextureRegionDrawable exitDrawable = new TextureRegionDrawable(exitTexture);

        ImageButton exitButton = new ImageButton(exitDrawable);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); // Exit the application
            }
        });

        // Table for layout
        Table table = new Table();
        table.setFillParent(true);

        // Add the angrybirdTextImage above the start button
        table.add(angrybirdTextImage).padBottom(20).center().height(70).width(500); // Add image and pad below it
        table.row(); // Move to the next row
        table.add(startButton).padTop(10).padBottom(10).width(200).height(100).center(); // Adjust button size

        // Positioning the menu button at the bottom-left corner
        Table bottomLeftTable = new Table();
        bottomLeftTable.setFillParent(true);
        bottomLeftTable.bottom().left();
        bottomLeftTable.add(menuButton).width(50).height(50).pad(10);

        // Positioning the exit button at the bottom-right corner
        Table bottomRightTable = new Table();
        bottomRightTable.setFillParent(true);
        bottomRightTable.bottom().right();
        bottomRightTable.add(exitButton).width(50).height(50).pad(10);

        // Add tables to the stage
        stage.addActor(table);           // Main layout table
        stage.addActor(bottomLeftTable); // Bottom-left corner layout table
        stage.addActor(bottomRightTable); // Bottom-right corner layout table for the exit button
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage); // Set input processor
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // Apply viewport to handle resizing
        viewport.apply();
        batch.setProjectionMatrix(stage.getCamera().combined);

        batch.begin();
        // Draw background proportionally, using the viewport's width and height
        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();

        stage.act(); // Update stage
        stage.draw(); // Draw stage
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
        background.dispose();
    }
}
