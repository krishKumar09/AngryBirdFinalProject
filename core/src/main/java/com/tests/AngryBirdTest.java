package com.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import com.angrybirds.AngryBird;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.Before;
import org.junit.Test;


public class AngryBirdTest {

    private AngryBird bird;
    private World world;

    @Before
    public void setUp() {
        world = new World(new Vector2(0, -9.8f), true);  // Create a world with gravity
        bird = new AngryBird(world, 0, 0); // Create an AngryBird at position (0, 0)
    }

    @Test
    public void testInitialPosition() {
        assertEquals(0.0f, bird.getPosition().x, 0.1);
        assertEquals(0.0f, bird.getPosition().y, 0.1);
    }

    @Test
    public void testLaunch() {
        Vector2 force = new Vector2(10, 10);
        bird.launch(force);

        assertTrue(bird.isLaunched());

        assertTrue(bird.getBody().getLinearVelocity().len() > 0);
    }

    @Test
    public void testResetPosition() {
        bird.launch(new Vector2(10, 10));

        assertTrue(bird.isLaunched());

        bird.resetPosition();

        assertEquals(0.0f, bird.getPosition().x, 0.1);
        assertEquals(0.0f, bird.getPosition().y, 0.1);

        assertFalse(bird.isLaunched());
    }

    @Test
    public void testSetPosition() {
        bird.setPosition(5, 5);

        assertEquals(5.0f, bird.getPosition().x, 0.1);
        assertEquals(5.0f, bird.getPosition().y, 0.1);

        assertFalse(bird.isLaunched());
    }

    @Test
    public void testDispose() {
        try {
            bird.dispose();
        } catch (Exception e) {
            // If dispose throws an exception, fail the test
            assertFalse("Dispose method threw an exception", true);
        }
    }

    @Test
    public void testUpdatePosition() {
        Vector2 newPosition = new Vector2(10, 10);
        bird.updatePosition(newPosition);

        assertEquals(10.0f, bird.getPosition().x, 0.1);
        assertEquals(10.0f, bird.getPosition().y, 0.1);
    }

    @Test
    public void testContains() {
        assertTrue(bird.contains(0, 0));
    }
}
