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
 * Represents a key object in the game world that the player can collect.
 * Extends the GameObject class and includes properties and methods specific to the Key object.
 */
public class Key extends GameObject{

        private Sprite spriteKey;
        private Animation<TextureRegion> keyAnimation;
        private float stateTime;
        private boolean isVisible;

    /**
     * Constructs a new Key object with a specified position.
     *
     * @param position The position of the key in the game world.
     */
    public Key(Vector2 position) {
            super(position);
            super.texture = new Texture(Gdx.files.internal("objects.png"));

            // Initialize the sprite for this key
            this.spriteKey = new Sprite(texture,0,64,16,16);
            this.spriteKey.setPosition(position.x, position.y);

            // Create key animation
            Array<TextureRegion> keyFrames = new Array<>();
            int frameWidth = 16;
            int frameHeight = 16;

            // Assuming your frames are arranged horizontally in the texture
            for (int i = 0; i < 4; i++) {
            keyFrames.add(new TextureRegion(texture, i * frameWidth, 64, frameWidth, frameHeight));
            }

            keyAnimation = new Animation<>(0.1f, keyFrames);
            stateTime = 0f;
            this.isVisible=true;
            }

    /**
     * Updates the state of the key over time.
     *
     * @param deltaTime The time passed since the last update.
     */
    @Override
        public void update(float deltaTime) {
            // Update the state time for the animation
            stateTime += deltaTime;
        }

    /**
     * Renders the key on the screen using the specified SpriteBatch.
     *
     * @param spriteBatch The SpriteBatch used for rendering.
     * @param x           The x-coordinate at which to render the key.
     * @param y           The y-coordinate at which to render the key.
     */

    public void render(SpriteBatch spriteBatch, float x, float y) {
        if (isVisible) {
            TextureRegion currentFrame = keyAnimation.getKeyFrame(stateTime, true);

            spriteBatch.draw(currentFrame, x, y);
        }
    }


    /**
     * Disposes of resources associated with the key.
     * This method should be called when the key is no longer needed.
     */
        public void dispose() {
            super.dispose(); // Dispose of the base GameObject resources
        }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public Sprite getSpriteKey() {
        return spriteKey;
    }
}
