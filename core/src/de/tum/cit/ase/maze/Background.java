package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


/**
 * The Background class represents a background element in the game.
 * It provides methods for rendering the background.
 */
public class Background extends GameObject{

    private Sprite spriteBackground;    // Sprite for rendering the background
    private Vector2 position;           // Position of the background


    /**
     * Constructor for the Background class with a specified position.
     * Initializes the background with a given position and default texture.
     *
     * @param position The position vector for the background.
     */
    public Background(Vector2 position) {
        super(position);
        this.position = new Vector2();
        super.texture = new Texture(Gdx.files.internal("basictiles.png"));

        // Initialize the sprite for this wall
        this.spriteBackground = new Sprite(texture, 16, 9*16, 16, 16);
        this.spriteBackground.setPosition(position.x, position.y);
    }

    /**
     * Updates the background (if needed) based on the elapsed time.
     *
     * @param deltaTime The time passed since the last frame.
     */

    public void update(float deltaTime) {
            // Walls are static, no need for updates
        }


    /**
     * Renders the background at a specified position.
     *
     * @param spriteBatch The SpriteBatch used for rendering.
     * @param x            The x-coordinate where the background should be rendered.
     * @param y            The y-coordinate where the background should be rendered.
     */
    public void render(SpriteBatch spriteBatch, float x, float y) {
            spriteBatch.draw(spriteBackground, x, y);
        }



    /**
     * Renders the background at its current position.
     *
     * @param spriteBatch The SpriteBatch used for rendering.
     */

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(spriteBackground, position.x, position.y);
    }


    /**
     * Gets the Sprite object representing the background.
     *
     * @return The Sprite object used for rendering the background.
     */

    public Sprite getSpriteBackground() {
        return spriteBackground;
    }


}
