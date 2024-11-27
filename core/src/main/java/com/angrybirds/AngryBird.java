package com.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class AngryBird {
    private Body body;
    private Texture texture;
    private boolean launched;
    private Vector2 startPosition; // Store the initial position

    public AngryBird(World world, float x, float y) {
        // Load texture for the bird
        texture = new Texture("angry_bird.png");

        // Store the start position
        startPosition = new Vector2(x, y);

        // Define the bird's body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);

        // Define the bird's shape
        CircleShape shape = new CircleShape();
        shape.setRadius(0.3f); // Example radius, adjust as needed

        // Create a fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f; // Keeps the mass the same
        fixtureDef.friction = 15.0f; // Increase friction (default is 0.2f)
        fixtureDef.restitution = 0.5f; // Controls bounciness
        // Bounciness
        body.createFixture(fixtureDef);

        // Set user data for collision detection
        body.setUserData(this);

        shape.dispose();
        launched = false; // Initially not launched
    }

    /**
     * Renders the bird using the provided SpriteBatch.
     *
     * @param batch the SpriteBatch used for rendering
     */
    public void render(SpriteBatch batch) {
        Vector2 position = body.getPosition();
        float width = 1.2f;  // Adjust based on texture size
        float height = 1.2f; // Adjust based on texture size
        batch.draw(texture, position.x - width / 2, position.y - height/4, width, height);
    }

    /**
     * Returns the Box2D body associated with the bird.
     *
     * @return the body
     */
    public Body getBody() {
        return body;
    }

    /**
     * Sets the bird's position and resets its velocity and state.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public void setPosition(float x, float y) {
        body.setTransform(x, y, 0);
        body.setLinearVelocity(0, 0); // Reset velocity
        body.setAngularVelocity(0); // Reset angular velocity
        launched = false; // Reset launch status
    }

    /**
     * Launches the bird with a specified force.
     *
     * @param force the force vector to apply
     */
    public void launch(Vector2 force) {
        if (!launched) { // Ensure bird is not launched multiple times
            body.applyLinearImpulse(force, body.getWorldCenter(), true);
            launched = true; // Mark as launched
        }
    }

    /**
     * Resets the bird to its starting position and state.
     */
    public void resetPosition() {
        setPosition(startPosition.x, startPosition.y);
        launched = false; // Reset launched status
    }

    /**
     * Checks whether the bird has been launched.
     *
     * @return true if launched, false otherwise
     */
    public boolean isLaunched() {
        return launched;
    }

    /**
     * Disposes of the bird's resources.
     */
    public void dispose() {
        texture.dispose();
    }

    public void launch(float x, float y) {
        // Reset velocity
        body.setLinearVelocity(0, 0);
        body.setAngularVelocity(0);

        // Scale down the impulse (adjust scaling factor as needed)
        float scalingFactor = 0.7f; // Try values between 0.1f and 1.0f for fine-tuning
        Vector2 launchImpulse = new Vector2(x, y).scl(scalingFactor);

        // Apply launch impulse
        body.applyLinearImpulse(launchImpulse, body.getWorldCenter(), true);

        launched = true;
    }
    public void updatePosition(Vector2 newPosition) {
        // Update the bird's body position in the world
        if (body != null) {
            body.setTransform(newPosition, body.getAngle());
        }

        // Optionally, you can also update the graphical position of the bird here
        // depending on how you render the bird (e.g., by updating sprite position).
    }


    public boolean contains(float x, float y) {
        return true;
    }

    public Vector2 getPosition() {
        if (body != null) {
            // Get the current position of the bird's body in the physics world
            return body.getPosition();
        }
        return new Vector2(0, 0); // Return a default value if the body is null
    }

}
