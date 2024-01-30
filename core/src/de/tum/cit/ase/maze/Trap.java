package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;



/**
 * The Trap class represents a trap object in the game world.
 * It extends the GameObject class and adds trap-specific functionality.
 */
public class Trap extends GameObject{

    private final Sprite spriteTrap;
    private final Animation<TextureRegion> trapAnimation;
    private float stateTime;

    /**
     * Constructs a new Trap object.
     *
     * @param position The position of the trap in the game world.
    */
    public Trap(Vector2 position) {
        super(position);
        this.texture = new Texture(Gdx.files.internal("objects.png"));

        // Initialize the sprite for this trap
        this.spriteTrap = new Sprite(texture,64,48,16,16);
        this.spriteTrap.setPosition(position.x, position.y);

        // Create trap animation
        Array<TextureRegion> trapFrames = new Array<>();
        int frameWidth = 16;
        int frameHeight = 16;

        // Assuming your frames are arranged horizontally in the texture
        for (int i = 0; i < 7; i++) {
            trapFrames.add(new TextureRegion(texture, 64+(i * frameWidth), 48, frameWidth, frameHeight));
        }

        trapAnimation = new Animation<>(0.1f, trapFrames);
        stateTime = 0f;
    }

    /**
     * Updates the state of the trap.
     *
     * @param deltaTime The time passed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        // Update the state time for the animation
        stateTime += deltaTime;


    }

    /**
     * Renders the trap at the specified position.
     *
     * @param spriteBatch The sprite batch used for rendering.
     * @param x            The x-coordinate of the rendering position.
     * @param y            The y-coordinate of the rendering position.
     */
    public void render(SpriteBatch spriteBatch, float x, float y) {
        TextureRegion currentFrame = trapAnimation.getKeyFrame(stateTime, true);
        spriteBatch.draw(currentFrame, x, y);
    }


    /**
     * Renders the trap at its current position.
     *
     * @param spriteBatch The sprite batch used for rendering.
     */
    public void render(SpriteBatch spriteBatch) {
        TextureRegion currentFrame = trapAnimation.getKeyFrame(stateTime, true);
        spriteBatch.draw(currentFrame, position.x, position.y);
    }


    /**
     * Disposes of resources associated with the trap.
     */
    public void dispose() {
        super.dispose(); // Dispose of the base GameObject resources
    }

}
