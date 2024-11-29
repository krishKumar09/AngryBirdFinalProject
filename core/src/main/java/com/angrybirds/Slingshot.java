package com.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class Slingshot {
    public Texture texture;
    private float x, y;
    private float width, height; // Size of the slingshot

    public Slingshot(World world, float x, float y,float width , float height) {
        // Load texture
        texture = new Texture("slingshot.png");
        this.x = x;
        this.y = y;
        this.width = width; // Adjust as needed
        this.height = height; // Adjust as needed
    }

    public void render(SpriteBatch batch) {
        if (texture != null) {
            batch.draw(texture, x, y, width, height);
        }
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }

    public boolean contains(float x, float y) {
        return true;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return  y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return  height;
    }
}
