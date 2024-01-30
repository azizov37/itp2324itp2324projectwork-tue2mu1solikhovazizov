package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


/**
 * The GameObject class represents a generic object in the game world.
 * It serves as a base class for more specific game objects and provides common functionality.
 */
public abstract class GameObject {
    protected Vector2 position;
    protected Texture texture; // Texture for rendering the game object

    /**
     * Constructs a new GameObject with a specified position.
     *
     * @param position The position vector of the game object.
     */
    public GameObject(Vector2 position) {
        this.position = position;
        this.texture = texture;
    }


    /**
     * Renders the game object using the provided SpriteBatch.
     *
     * @param batch The SpriteBatch used for rendering.
     */
    public void render(SpriteBatch batch) {
        // Implementation specific to each subclass
    }


    /**
     * Updates the game object based on the elapsed time.
     *
     * @param deltaTime The time passed since the last frame.
     */
    public abstract void update(float deltaTime);


    /**
     * Gets the position of the game object.
     *
     * @return The position vector of the game object.
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Sets the position of the game object.
     *
     * @param position The new position vector for the game object.
     */
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    /**
     * Disposes of resources used by the game object.
     */
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}

