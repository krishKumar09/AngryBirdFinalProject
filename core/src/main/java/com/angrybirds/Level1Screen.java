package com.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Level1Screen implements Screen {
    private World world;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private Texture backButtonTexture;
    private AngryBird angryBird;
    private Array<Pig> pigs;
    private Array<Box> boxes; // Boxes in the game
    private Slingshot slingshot;

    private boolean dragging;
    private boolean snappedToSlingshot;
    private Vector2 slingshotAnchor;
    private Texture background;

    private Stage stage; // Stage for UI elements
    private ImageButton pausemenuButton; // Pause button for the menu

    public Level1Screen(Main game) {
        // Box2D world initialization
        world = new World(new Vector2(0, -9.8f), true);
        setupContactListener();

        // Camera setup
        camera = new OrthographicCamera();
        viewport = new StretchViewport(16, 9, camera); // Maintain 16:9 aspect ratio
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        // Sprite batch for rendering
        batch = new SpriteBatch();

        // Load background texture
        background = new Texture("level.png");

        // Initialize game objects
        slingshot = new Slingshot(world, 2, 1.9f, 2.3f, 2.5f); // Adjust slingshot position as needed
        angryBird = new AngryBird(world, 2f, 2.4f); // Bird starts here

        // Initialize boxes
        boxes = new Array<>();
        boxes.add(new Box(world, 9, 2.5f)); // First box
        boxes.add(new Box(world, 11, 2.5f)); // Second box

        // Define box and pig dimensions
        float boxHeight = 0.5f; // Adjust based on box size (height)
        float pigRadius = 0.3f; // Approximate radius of the pig (or use half-height if rectangular)

        // Initialize pigs and place them on top of the boxes
        pigs = new Array<>();
        pigs.add(new Pig(world, 9, 2.5f + boxHeight / 2 + pigRadius)); // Pig on first box
        pigs.add(new Pig(world, 11, 2.5f + boxHeight / 2 + pigRadius)); // Pig on second box

        slingshotAnchor = new Vector2(2.9f, 3.9f); // Anchor for the slingshot
        dragging = false;
        snappedToSlingshot = false; // Bird hasn't snapped to the slingshot yet

        // Initialize UI stage
        stage = new Stage(viewport, batch);

        // Create pause button
        backButtonTexture = new Texture(Gdx.files.internal("pause.png"));
        pausemenuButton = new ImageButton(new TextureRegionDrawable(backButtonTexture));
        pausemenuButton.setPosition(0, camera.viewportHeight - backButtonTexture.getHeight()); // Position at top left

        // Add button listener to switch to the pause menu
        pausemenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PauseMenuScreen(game, new Level1Screen(game)));
            }
        });

        // Add the pause button to the stage
        stage.addActor(pausemenuButton);

        // Create ground and boundaries
        createBoundaries();
    }

    private void setupContactListener() {
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Object userDataA = contact.getFixtureA().getBody().getUserData();
                Object userDataB = contact.getFixtureB().getBody().getUserData();

                if ((userDataA instanceof AngryBird && userDataB instanceof Pig) ||
                    (userDataB instanceof AngryBird && userDataA instanceof Pig)) {

                    Pig pig = (userDataA instanceof Pig) ? (Pig) userDataA : (Pig) userDataB;
                    pig.reduceHealth(50); // Example damage for pig collisions
                }

                if ((userDataA instanceof AngryBird && userDataB instanceof Box) ||
                    (userDataB instanceof AngryBird && userDataA instanceof Box)) {

                    Box box = (userDataA instanceof Box) ? (Box) userDataA : (Box) userDataB;
                    box.reduceHealth(50); // Example damage for box collisions
                }
            }

            @Override
            public void endContact(Contact contact) {}

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        });
    }

    private void createBoundaries() {
        // Ground
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(8, 1.8f));
        Body groundBody = world.createBody(groundBodyDef);

        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(8, 0.1f);
        groundBody.createFixture(groundBox, 0.0f);
        groundBox.dispose();

        // Left wall
        BodyDef leftWallDef = new BodyDef();
        leftWallDef.position.set(new Vector2(0, camera.viewportHeight / 2));
        Body leftWall = world.createBody(leftWallDef);

        PolygonShape leftWallBox = new PolygonShape();
        leftWallBox.setAsBox(0.1f, camera.viewportHeight / 2);
        leftWall.createFixture(leftWallBox, 0.0f);
        leftWallBox.dispose();

        // Right wall
        BodyDef rightWallDef = new BodyDef();
        rightWallDef.position.set(new Vector2(camera.viewportWidth, camera.viewportHeight / 2));
        Body rightWall = world.createBody(rightWallDef);

        PolygonShape rightWallBox = new PolygonShape();
        rightWallBox.setAsBox(0.1f, camera.viewportHeight / 2);
        rightWall.createFixture(rightWallBox, 0.0f);
        rightWallBox.dispose();

        // Top boundary
        BodyDef topWallDef = new BodyDef();
        topWallDef.position.set(new Vector2(camera.viewportWidth / 2, camera.viewportHeight));
        Body topWall = world.createBody(topWallDef);

        PolygonShape topWallBox = new PolygonShape();
        topWallBox.setAsBox(camera.viewportWidth / 2, 0.1f);
        topWall.createFixture(topWallBox, 0.0f);
        topWallBox.dispose();
    }

    @Override
    public void render(float delta) {
        // Update the Box2D world
        world.step(1 / 60f, 6, 2);

        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update camera
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Start rendering
        batch.begin();

        // Draw background, scaled to fit the screen
        batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);

        // Render game objects
        slingshot.render(batch);
        angryBird.render(batch);
        for (Pig pig : pigs) {
            if (!pig.isEliminated()) {
                pig.render(batch);
            }
        }
        for (Box box : boxes) {
            if (!box.isEliminated()) {
                box.render(batch);
            }
        }

        batch.end();

        handleInput();

        // Draw the UI elements (pause button)
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Limit update time for smooth performance
        stage.draw();
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (!angryBird.isLaunched() && !dragging) {
                Vector2 birdPosition = angryBird.getBody().getPosition();
                if (new Vector2(touchPos.x, touchPos.y).dst(birdPosition) <= 1f) {
                    dragging = true;
                }
            }

            if (dragging && !snappedToSlingshot) {
                Vector2 direction = new Vector2(touchPos.x, touchPos.y).sub(slingshotAnchor);
                angryBird.setPosition(slingshotAnchor.x + direction.x, slingshotAnchor.y + direction.y);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage); // Set input processor to the stage
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        world.dispose();
        stage.dispose(); // Dispose the stage
    }
}
