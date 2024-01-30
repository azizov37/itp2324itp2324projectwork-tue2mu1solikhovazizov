package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Random;


/**
 * The IntelligentEnemy class represents an enemy with advanced movement behavior.
 * It can detect the player, move towards the player, and handle collisions with walls.
 */
public class IntelligentEnemy extends GameObject {

    private Texture textureEnemy;
    private Sprite spriteEnemy;
    private Sprite spriteEnemySleeping;
    private Animation<TextureRegion> moveAnimation; // Animation for movement
    private float speed; // Speed at which the enemy moves
    private float detectionRadius; // Radius to detect the player
    private Vector2 targetPosition; // Position of the player
    private float moveDuration = 10f; // Adjust this duration as needed
    private float breakDuration = 5f; // Adjust this duration as needed
    private float timer = 0f;
    private float glitchTimer = 0f;
    private float glitchDuration = 0.1f;
    private boolean isMoving = true;
    private boolean movingThroughObstacle = false;
    private boolean playerDetected = false;
    private float lastCollisionTime = -2.0f;


    /**
     * Constructs an IntelligentEnemy object with the specified initial position.
     *
     * @param position The initial position of the enemy.
     */
    public IntelligentEnemy(Vector2 position) {

        super(position);

        this.textureEnemy = new Texture(Gdx.files.internal("mobs.png"));

        this.spriteEnemy = new Sprite(textureEnemy, 6*16, 4*16, 16, 16);
        this.spriteEnemySleeping = new Sprite(textureEnemy, 6*16, 7*16, 16, 16);
        // Create key animation
        Array<TextureRegion> keyFrames = new Array<>();


        // Assuming your frames are arranged horizontally in the texture
        for (int i = 0; i < 3; i++) {
            keyFrames.add(new TextureRegion(textureEnemy, (6 + i)*16, 4*16, 16, 16));
        }

        moveAnimation = new Animation<>(0.1f, keyFrames);
        this.speed = 30f;
        this.detectionRadius = 50f;
        this.targetPosition = new Vector2();
    }
    /**
     * Updates the state of the enemy based on the player's position and surrounding walls.
     *
     * @param deltaTime      The time passed since the last update.
     * @param playerPosition The current position of the player.
     * @param walls          The array of walls in the game.
     */
    public void update(float deltaTime, Vector2 playerPosition, Array<Wall> walls) {
        timer+=deltaTime;
        lastCollisionTime += deltaTime;
        // Calculate distance to player
        float distanceToPlayer = position.dst(playerPosition);




        if (distanceToPlayer <= detectionRadius) {
            playerDetected=true;
            if (isMoving) {

                // Set target position as player position
                targetPosition.set(playerPosition);
                moveTowardsTarget(deltaTime, walls);
                if (isCollidingWithWalls(position,walls)) {
                    movingThroughObstacle=true;
                }

                if (timer >= moveDuration) {
                    isMoving = false; // Stop moving after the moveDuration
                    timer = 0f;
                }

            }

        } else {

            // If not moving, take a break
            if (timer >= breakDuration) {
                timer = 0f;
                isMoving=true;
                playerDetected=false;
            }
        }

    }
    /**
     * Checks if the enemy has detected the player and is currently moving.
     *
     * @return True if the enemy detected the player and is moving; otherwise, false.
     */
    public boolean checker() {
        if (playerDetected&&isMoving) {
            return true;
        }
        return false;
    }


    /**
     * Moves the enemy towards the target position while avoiding obstacles.
     *
     * @param deltaTime The time passed since the last movement update.
     * @param walls     The array of walls in the game.
     * @return True if the movement was successful; otherwise, false.
     */
    private boolean moveTowardsTarget(float deltaTime, Array<Wall> walls) {
        // Calculate direction towards target
        Vector2 direction = new Vector2(targetPosition.x - position.x, targetPosition.y - position.y).nor();
        Vector2 newPosition = new Vector2(position).add(direction.scl(speed * deltaTime));

        position.set(newPosition); // Update position if no collision
        return true; // Movement successful
    }



    /**
     * Checks if the enemy is colliding with any walls in the specified array.
     *
     * @param position The current position of the enemy.
     * @param walls    The array of walls in the game.
     * @return True if a collision is detected; otherwise, false.
     */

    private boolean isCollidingWithWalls(Vector2 position, Array<Wall> walls) {
        Rectangle enemyBounds = new Rectangle(position.x, position.y, 12, 12);

        for (Wall wall : walls) {
            Rectangle wallBounds = new Rectangle(wall.getPosition().x, wall.getPosition().y, 12, 12);
            if (enemyBounds.overlaps(wallBounds)) {

                if (lastCollisionTime >= 0.03f){

                    lastCollisionTime = -1.0f; // Reset the timer
                    return true; // Collision detected
                }
            }
        }
        return false; // No collision
    }


    /**
     * Renders the enemy on the screen, applying animations and glitch effects if necessary.
     *
     * @param batch The SpriteBatch used for rendering.
     * @param delta The time passed since the last rendering update.
     */
    public void render(SpriteBatch batch,float delta) {
        if (movingThroughObstacle) {
            System.out.println("Not rendered");
            update(delta);
            if (glitchTimer >= glitchDuration) {
                System.out.println("glitchTimer");
                movingThroughObstacle = false; // End glitch effect
                glitchTimer = 0;
            }
        } else {
            if (isMoving) {
                if (playerDetected) {
                    TextureRegion keyFrames = moveAnimation.getKeyFrame(timer,true);
                    batch.draw(keyFrames,position.x,position.y);
                } else {
                    batch.draw(spriteEnemy,position.x,position.y);
                }
            } else {
                batch.draw(spriteEnemySleeping,position.x,position.y);
            }
        }
    }
    /**
     * Updates the internal state of the enemy over time, including glitch effects.
     *
     * @param deltaTime The time passed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        glitchTimer += deltaTime;
    }

    // Additional methods like getters, setters, dispose, etc.
}