package com.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class Slingshot {
    private Texture texture;
    private float x, y; // Position of the slingshot
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
            // Render the slingshot
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
}
