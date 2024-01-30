package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


/**
 * The Exit class represents an exit object in the game maze.
 * It extends the GameObject class and provides methods for rendering and updating the exit.
 */
public class Exit extends GameObject{
    private Texture textureExit;
    private Sprite spriteExitClosed;
    private Sprite spriteExitOpen;

    private boolean closed;

    private float stateTime;

    /**
     * Constructs a new Exit object with a specified position.
     *
     * @param position The position of the exit in the game world.
     */
     public Exit(Vector2 position) {
        super(position);
        this.stateTime = 0.0f;
         this.textureExit = new Texture(Gdx.files.internal("things.png"));

         // Initialize the sprite for this trap
         this.spriteExitClosed = new Sprite(textureExit,0,0,16,16);
         // Initialize the sprite for the open exit
         this.spriteExitOpen = new Sprite(textureExit,0,48,16,16);

         this.closed = true;
     }
    /**
     * Updates the exit based on the elapsed time.
     *
     * @param deltaTime The time passed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        // Update the state time for the animation
        stateTime += deltaTime;

    }

    /**
     * Renders the exit at a specified position using the provided SpriteBatch.
     *
     * @param spriteBatch The SpriteBatch used for rendering.
     * @param x            The x-coordinate where the exit should be rendered.
     * @param y            The y-coordinate where the exit should be rendered.
     */

    public void render(SpriteBatch spriteBatch, float x, float y) {
        if (closed) {

            spriteBatch.draw(spriteExitClosed, x, y);
        } else {
            spriteBatch.draw(spriteExitOpen, x, y);

        }
    }
    /**
     * Disposes of the resources used by the exit.
     */
    public void dispose() {
        super.dispose(); // Dispose of the base GameObject resources

    }
    /**
     * Checks if the exit is closed.
     *
     * @return true if the exit is closed, false otherwise.
     */
    public boolean isClosed() {
        return closed;
    }
    /**
     * Sets the state of the exit to closed or open.
     *
     * @param closed true to close the exit, false to open it.
     */
    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
