package com.tests;

import com.angrybirds.Pig;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PigTest {

    private World world;
    private Pig pig;
    private SpriteBatch batch;
    private Texture texture;

    @BeforeEach
    void setUp() {
        world = new World(new Vector2(0, -9.8f), true); // Create the physics world with gravity
        batch = new SpriteBatch();
        texture = new Texture("pig.png"); // Use an actual texture file for testing

        pig = new Pig(world, 10.0f, 10.0f);
        pig.texture = texture; // Set the texture for rendering
    }

    @Test
    void testConstructor() {
        assertNotNull(pig, "Pig object should be created.");
        assertEquals(100.0f, pig.getHealth(), "Initial health should be 100.");
        assertFalse(pig.isEliminated(), "Pig should not be eliminated by default.");
    }



    @Test
    void testRender() {
        pig.render(batch);
        // Since we cannot directly test graphical output in a unit test, we just assert that no errors occur during rendering
        assertDoesNotThrow(() -> pig.render(batch), "Render method should not throw exceptions.");
    }

    @Test
    void testSetEliminated() {
        pig.setEliminated(true);
        assertTrue(pig.isEliminated(), "Pig should be marked as eliminated.");
    }




    @Test
    void testPigCreationWithPhysics() {
        Body body = pig.getBody();
        assertNotNull(body, "Pig should have a body created in the world.");
        assertEquals(BodyDef.BodyType.DynamicBody, body.getType(), "Body should be a dynamic body.");
    }
}
