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
    private final Main game;
    private Stage stage;
    private SpriteBatch batch;
    private Texture background;
    private Viewport viewport;

    public MainMenu(Main game) {
        this.game = game;


        viewport = new StretchViewport(640, 480);
        stage = new Stage(viewport);
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("angry.png"));

        Texture angrybirdTextTexture = new Texture(Gdx.files.internal("angrybirdtext.png"));
        Image angrybirdTextImage = new Image(angrybirdTextTexture);


        Texture startTexture = new Texture(Gdx.files.internal("start.png"));
        TextureRegionDrawable startDrawable = new TextureRegionDrawable(startTexture);

        ImageButton startButton = new ImageButton(startDrawable);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
            }
        });


        Texture menuTexture = new Texture(Gdx.files.internal("menu.png"));
        TextureRegionDrawable menuDrawable = new TextureRegionDrawable(menuTexture);

        ImageButton menuButton = new ImageButton(menuDrawable);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Menu button clicked");
            }
        });


        Texture exitTexture = new Texture(Gdx.files.internal("exit.png"));
        TextureRegionDrawable exitDrawable = new TextureRegionDrawable(exitTexture);

        ImageButton exitButton = new ImageButton(exitDrawable);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });


        Table table = new Table();
        table.setFillParent(true);


        table.add(angrybirdTextImage).padBottom(20).center().height(70).width(500);
        table.row();
        table.add(startButton).padTop(10).padBottom(10).width(200).height(100).center();


        Table bottomLeftTable = new Table();
        bottomLeftTable.setFillParent(true);
        bottomLeftTable.bottom().left();
        bottomLeftTable.add(menuButton).width(50).height(50).pad(10);


        Table bottomRightTable = new Table();
        bottomRightTable.setFillParent(true);
        bottomRightTable.bottom().right();
        bottomRightTable.add(exitButton).width(50).height(50).pad(10);


        stage.addActor(table);
        stage.addActor(bottomLeftTable);
        stage.addActor(bottomRightTable);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);


        viewport.apply();
        batch.setProjectionMatrix(stage.getCamera().combined);

        batch.begin();

        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
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
        background.dispose();
    }
}
