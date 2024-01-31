package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * The Player class represents the player character in the Maze Runner game.
 * It handles the player's movement, collisions, animations, and game state.
 */
public class Player {
    private Vector2 position;
    private final Texture playerTexture;
    private final Sprite spritePlayer;
    private final Sprite spriteEntry;
    private final float playerSpeed = 40f;
    private boolean gotKey = false;
    private int lives;
    private Animation<TextureRegion> characterDownAnimation;
    private Animation<TextureRegion> characterUpAnimation;
    private Animation<TextureRegion> characterLeftAnimation;
    private Animation<TextureRegion> characterRightAnimation;

    private float lastCollisionTime = -2.0f; // Initialize to -2.0 so that the first collision can occur immediately

    /**
     * Constructs a new Player instance with the specified initial position.
     *
     * @param position The initial position of the player.
     */
    public Player(Vector2 position) {
        this.position = position;

        this.playerTexture = new Texture(Gdx.files.internal("character.png"));
        this.spritePlayer = new Sprite(playerTexture,0,0,16,32);
        Texture entryTexture = new Texture(Gdx.files.internal("basictiles.png"));
        this.spriteEntry = new Sprite(entryTexture,16,16*11,16,16);
        int frameWidth = 16;
        int frameHeight = 32;
        int animationFrames = 4;
        lives=5;

        // Initialize animations for each direction
        characterDownAnimation = createAnimation(0, 0,frameWidth, frameHeight, animationFrames);
        characterUpAnimation = createAnimation(2, 0,frameWidth, frameHeight, animationFrames);
        characterLeftAnimation = createAnimation(3, 0,frameWidth, frameHeight, animationFrames);
        characterRightAnimation = createAnimation(1, 0,frameWidth, frameHeight, animationFrames);


    }
    /**
     * Creates a new animation based on the specified parameters.
     *
     * @param row          The row index of the animation frames in the texture.
     * @param column       The column index of the first frame in the texture.
     * @param frameWidth   The width of each frame.
     * @param frameHeight  The height of each frame.
     * @param frames       The total number of frames in the animation.
     * @return The created animation.
     */
    private Animation<TextureRegion> createAnimation(int row, int column, int frameWidth, int frameHeight, int frames) {
        Array<TextureRegion> walkFrames = new Array<>(TextureRegion.class);
        for (int col = column; col < frames; col++) {
            walkFrames.add(new TextureRegion(playerTexture, col * frameWidth, row * frameHeight, frameWidth, frameHeight));
        }
        return new Animation<>(0.1f, walkFrames, Animation.PlayMode.LOOP);
    }

    /**
     * Moves the player by the specified delta values.
     *
     * @param deltaX The change in the x-coordinate.
     * @param deltaY The change in the y-coordinate.
     */
    public void move(float deltaX, float deltaY) {
        position.x += deltaX;
        position.y += deltaY;
    }

    /**
     * Renders the entry point.
     *
     * @param spriteBatch The sprite batch used for rendering.
     * @param x           The x-coordinate for rendering.
     * @param y           The y-coordinate for rendering.
     */
    public void renderEntry(SpriteBatch spriteBatch, float x, float y) {
        spriteBatch.draw(spriteEntry, x, y);
    }



    /**
     * Checks if the player is colliding with walls.
     *
     * @param position The current position of the player.
     * @param walls    The array of walls in the game.
     * @return True if colliding with walls, false otherwise.
     */

    public boolean isCollidingWithWalls(Vector2 position, Array<Wall> walls) {
        Rectangle playerRect = new Rectangle(position.x, position.y, 12, 10);

        for (Wall wall : walls) {
            Rectangle wallRect = new Rectangle(wall.getPosition().x, wall.getPosition().y, 12, 12);
            if (playerRect.overlaps(wallRect)) {
                return true; // Collision detected with at least one wall
            }
        }

        return false; // No collision with any wall
    }

    /**
     * Checks if the player is colliding with traps.
     *
     * @param position The current position of the player.
     * @param traps    The array of traps in the game.
     * @param delta    The time passed since the last frame.
     * @return True if colliding with traps, false otherwise.
     */
    public boolean isCollidingWithTraps(Vector2 position, Array<Trap> traps, float delta) {
        // Update the time since the last collision
        lastCollisionTime += delta;

        Rectangle playerRect = new Rectangle(position.x, position.y, 12, 10);

        for (Trap trap : traps) {
            Rectangle rect = new Rectangle(trap.getPosition().x, trap.getPosition().y, 5, 5);
            if (playerRect.overlaps(rect)) {
                if (lastCollisionTime >= 2.0f) {
                    lives--;
                    lastCollisionTime = 0; // Reset the timer
                    return true; // Collision detected with at least one wall
                }
            }
        }

        return false; // No collision with any wall
    }


    /**
     * Checks if the player is colliding with enemies.
     *
     * @param position The current position of the player.
     * @param enemies  The array of enemies in the game.
     * @param yovuz    The intelligent enemy in the game.
     * @param delta    The time passed since the last frame.
     * @return True if colliding with enemies, false otherwise.
     */
    public boolean isCollidingWithEnemies (Vector2 position, Array<Enemy> enemies, IntelligentEnemy yovuz, float delta) {
        // Update the time since the last collision
        lastCollisionTime += delta;
        Rectangle playerRect = new Rectangle(position.x, position.y, 12, 10);

        for (Enemy enemy : enemies) {
            Rectangle rect = new Rectangle(enemy.getPosition().x, enemy.getPosition().y, 5, 5);
            if (playerRect.overlaps(rect)) {
                if (lastCollisionTime >= 2.0f) {
                    lives--;
                    lastCollisionTime = 0; // Reset the timer
                    return true; // Collision detected with at least one wall
                }
            }
        }

        Rectangle rect = new Rectangle(yovuz.getPosition().x, yovuz.getPosition().y, 5, 5);
        if (playerRect.overlaps(rect)) {
            if (lastCollisionTime >= 3.0f) {
                lives--;
                lastCollisionTime = 0; // Reset the timer
                return true; // Collision detected with at least one wall
            }
        return false; // No collision with any wall
        }
        return false;
    }


    /**
     * Checks if the player has lost all lives.
     *
     * @return True if the player has lost, false otherwise.
     */
    public boolean playerLost(){
        return lives <= 0;
    }


    /**
     * Checks if the player is colliding with exits.
     *
     * @param position The current position of the player.
     * @param exits    The array of exits in the game.
     * @return True if colliding with exits, false otherwise.
     */
    public boolean isCollidingWithExits (Vector2 position, Array<Exit> exits) {
        Rectangle playerRect = new Rectangle(position.x, position.y, 12, 10);

        for (Exit exit : exits) {
            Rectangle rect = new Rectangle(exit.getPosition().x, exit.getPosition().y, 12, 12);

            if (playerRect.overlaps(rect)) {

                // Collision detected with at least one wall
                return exit.isClosed();
            }
        }

        return false; // No collision with any wall
    }

    /**
     * Checks if the player has won by reaching an open exit with a key.
     *
     * @param position The current position of the player.
     * @param exits    The array of exits in the game.
     * @return True if the player has won, false otherwise.
     */
    public boolean playerWon (Vector2 position, Array<Exit> exits) {
        Rectangle playerRect = new Rectangle(position.x, position.y, 12, 10);

        for (Exit exit : exits) {
            Rectangle rect = new Rectangle(exit.getPosition().x, exit.getPosition().y, 12, 12);

            if (playerRect.overlaps(rect)) {

                if (isGotKey() && !exit.isClosed()) {
                    return true; // Collision detected with at least one wall
                }
            }
        }

        return false; // No collision with any wall
    }

    /**
     * Checks if the player is colliding with a key.
     *
     * @param position The current position of the player.
     * @param key      The key in the game.
     * @return True if colliding with the key, false otherwise.
     */
    public boolean isCollidingWithKey (Vector2 position, Key key) {
        Rectangle playerRect = new Rectangle(position.x, position.y, 12, 10);


            Rectangle rect = new Rectangle(key.getPosition().x, key.getPosition().y, 5, 5);
        return playerRect.overlaps(rect); // Collision detected with at least one wall
// No collision with any wall
    }


    /**
     * Applies cheating to the player based on a cheat code.
     *
     * @param name The input cheat code.
     */
    public void cheating(String name) {

        String cheatCode = "UZBEKVIP";
        if (name!=null && name.equals(cheatCode)) {
            lives=lives+5;

        }
    }
    /**
     * Updates the state of the player.
     *
     * @param deltaTime The time passed since the last frame.
     */

    public void update(float deltaTime) {
    }

    // Dispose, getPosition, setPosition, getPlayerSpeed methods...

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public float getPlayerSpeed() {
        return playerSpeed;
    }

    public Sprite getSpritePlayer() {
        return spritePlayer;
    }

    public Animation<TextureRegion> getCharacterDownAnimation() {
        return characterDownAnimation;
    }


    public Animation<TextureRegion> getCharacterUpAnimation() {
        return characterUpAnimation;
    }

    public Animation<TextureRegion> getCharacterLeftAnimation() {
        return characterLeftAnimation;
    }

    public Animation<TextureRegion> getCharacterRightAnimation() {
        return characterRightAnimation;
    }

    public boolean isGotKey() {
        return gotKey;
    }

    public void setGotKey(boolean gotKey) {
        this.gotKey = gotKey;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

}