package com.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
public class Pig {
    private Body body;
    private Texture texture;
    private boolean eliminated; // Tracks if the pig is eliminated
    private float health; // Health of the pig

    public Pig(World world, float x, float y) {
        // Define body properties
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Make the pig a dynamic body to respond to collisions
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);

        // Define shape and fixture
        CircleShape shape = new CircleShape();
        shape.setRadius(0.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.8f;
        fixtureDef.friction = 15.0f; // Higher friction to reduce sliding
        fixtureDef.restitution = 0.3f; // Lower bounciness to reduce rebounds


        body.createFixture(fixtureDef);
        shape.dispose();

        // Load texture
        texture = new Texture("pig.png");
        eliminated = false; // Default to not eliminated
        health = 100.0f; // Initialize health
    }

    public void reduceHealth(float damage) {
        if (!eliminated) {
            health -= damage;
            if (health <= 0) {
                setEliminated(true);
            }
        }
    }

    public void render(SpriteBatch batch) {
        if (!eliminated && texture != null) {
            batch.draw(texture, body.getPosition().x - 0.4f, body.getPosition().y - 0.5f, 0.8f, 0.8f);
        }
    }

    public Body getBody() {
        return body;
    }

    public void setEliminated(boolean eliminated) {
        this.eliminated = eliminated;
        if (eliminated) {
            body.getWorld().destroyBody(body);
        }
    }

    public boolean isEliminated() {
        return eliminated;
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}
