package com.tests;

import com.angrybirds.Slingshot;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SlingshotTest {

    private World world;
    private Slingshot slingshot;
    private SpriteBatch batch;
    private Texture texture;

    @BeforeEach
    void setUp() {
        world = new World(new Vector2(0, -9.8f), true); // Create the physics world with gravity
        batch = new SpriteBatch();
        texture = new Texture("slingshot.png"); // Use an actual texture file for testing

        slingshot = new Slingshot(world, 100.0f, 100.0f, 2.0f, 4.0f);
        slingshot.texture = texture; // Set the texture for rendering
    }

    @Test
    void testConstructor() {
        assertNotNull(slingshot, "Slingshot object should be created.");
        assertEquals(100.0f, slingshot.getX(), "X position should be 100.0f.");
        assertEquals(100.0f, slingshot.getY(), "Y position should be 100.0f.");
        assertEquals(2.0f, slingshot.getWidth(), "Width should be 2.0f.");
        assertEquals(4.0f, slingshot.getHeight(), "Height should be 4.0f.");
    }

    @Test
    void testRender() {
        slingshot.render(batch);
        // Since we cannot directly test graphical output in a unit test, we just assert that no errors occur during rendering
        assertDoesNotThrow(() -> slingshot.render(batch), "Render method should not throw exceptions.");
    }




    @Test
    void testContains() {
        boolean contains = slingshot.contains(110.0f, 110.0f); // Point inside slingshot
        assertTrue(contains, "Slingshot should contain the point (110.0f, 110.0f).");

        contains = slingshot.contains(200.0f, 200.0f); // Point outside slingshot
        assertFalse(contains, "Slingshot should not contain the point (200.0f, 200.0f).");
    }
}
