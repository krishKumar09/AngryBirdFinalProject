package com.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Box {
    private Body body;
    private Texture texture;
    private float health;
    private boolean eliminated;

    public Box(World world, float x, float y) {
        this.texture = new Texture("boxx.png"); // Load the box texture
        this.health = 100; // Initial health of the box
        this.eliminated = false;

        // Define the body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Boxes are dynamic
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);

        // Define the shape
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.5f); // Box dimensions (adjust as needed)

        // Define the fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 1.0f; // Increase friction
        fixtureDef.restitution = 0.2f; // Minimal bounce
        // Slight bounce

        body.createFixture(fixtureDef);
        body.setUserData(this);

        shape.dispose(); // Clean up the shape
    }

    public void reduceHealth(float damage) {
        health -= damage;
        if (health <= 0) {
            eliminated = true;
        }
    }

    public boolean isEliminated() {
        return eliminated;
    }

    public void render(SpriteBatch batch) {
        if (!eliminated) {
            batch.draw(texture, body.getPosition().x - 0.5f, body.getPosition().y - 0.5f, 1, 1);
        }
    }
    public Vector2 getPosition() {
        if (body != null) {
            // Get the current position of the bird's body in the physics world
            return body.getPosition();
        }
        return new Vector2(0, 0); // Return a default value if the body is null
    }

    public void dispose() {
        texture.dispose();
    }
}
