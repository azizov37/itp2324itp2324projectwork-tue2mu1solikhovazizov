package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;



/**
 * The Wall class represents a wall object in the game world. It extends the GameObject class
 * and adds wall-specific functionality.
 */
public class Wall extends GameObject{
    private Texture textureWall;
    private Sprite spriteWall;
    private Sprite spriteWallBack;
    private boolean isBackWall;
    private float stateTime = 0.0f;


    /**
     * Constructs a new Wall object.
     *
     * @param position The position of the wall in the game world.
     */
    public Wall(Vector2 position) {
        super(position);
        this.textureWall = new Texture(Gdx.files.internal("basictiles.png"));

        // Initialize the sprite for this wall
        this.spriteWall = new Sprite(textureWall, 32, 0, 16, 16);
        this.spriteWallBack = new Sprite(textureWall, 48, 0, 16, 16);

        this.spriteWall.setPosition(position.x, position.y);

    }


    /**
     * Updates the state of the wall.
     *
     * @param deltaTime The time passed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        // Walls are static, no need for updates
        stateTime+=deltaTime;
    }

    /**
     * Renders the wall at its current position.
     *
     * @param spriteBatch The sprite batch used for rendering.
     */
    public void render(SpriteBatch spriteBatch) {
        if (isBackWall==false) {
            spriteBatch.draw(spriteWall, position.x, position.y);
        } else if (isBackWall==true) {
            spriteBatch.draw(spriteWallBack, position.x, position.y);
        }


    }

    /**
     * Renders the wall at the specified position.
     *
     * @param spriteBatch The sprite batch used for rendering.
     * @param x            The x-coordinate of the rendering position.
     * @param y            The y-coordinate of the rendering position.
     */
    public void render(SpriteBatch spriteBatch, float x, float y) {

        if (isBackWall==false) {
            spriteBatch.draw(spriteWall, x, y);
        } else if (isBackWall==true) {
            spriteBatch.draw(spriteWallBack, x,y);
        }

    }

    /**
     * Disposes of resources associated with the wall.
     */
    @Override
    public void dispose() {
        super.dispose(); // Dispose of the base GameObject resources
        textureWall.dispose();
    }

    public void setBackWall(boolean backWall) {
        isBackWall = backWall;
    }
}


