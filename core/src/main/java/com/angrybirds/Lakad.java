package com.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class Lakad {
    private Body body;
    private Texture texture;
    private float health;
    private boolean eliminated;

    public Lakad(World world, float x, float y) {
        this.texture = new Texture("lakad.png"); // Load the lakad texture
        this.health = 100; // Initial health of the lakad
        this.eliminated = false;

        // Define the body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Lakads are dynamic
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);

        // Define the shape
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.6f, 0.2f); // Adjust dimensions for Lakad

        // Define the fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.8f; // Higher friction for stability
        fixtureDef.restitution = 0.1f; // Minimal bounce

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
            batch.draw(texture, body.getPosition().x - 0.6f, body.getPosition().y - 0.2f, 1.2f, 0.4f);
        }
    }

    public void dispose() {
        texture.dispose();
    }
}
