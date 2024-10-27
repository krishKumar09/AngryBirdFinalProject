package com.angrybirds;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Main extends Game {
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new MainMenu(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
