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
 * Represents an enemy in the maze game, including its animations, movements,
 * and interactions with the environment.
 */
public class Enemy extends GameObject{
    private Sprite spriteEnemy;
    private Animation<TextureRegion> enemyDownAnimation;
    private Animation<TextureRegion> enemyUpAnimation;
    private Animation<TextureRegion> enemyLeftAnimation;
    private Animation<TextureRegion> enemyRightAnimation;
    private static final float MOVEMENT_DISTANCE = 100f;
    private float elapsedTime;
    private Vector2 direction;
    private float distanceMoved;
    private Direction currentDirection;

    /**
     * Constructs a new Enemy at the specified position.
     * @param position The starting position of the enemy.
     */
    public Enemy(Vector2 position) {
        super(position);

        direction = getRandomDirection();
        distanceMoved = 0f;
        texture = new Texture(Gdx.files.internal("mobs.png"));

        this.spriteEnemy = new Sprite(texture, 96, 64, 16, 16);

        // Initialize animation frames
        Array<TextureRegion> enemyFramesDown = new Array<>();
        Array<TextureRegion> enemyFramesUp = new Array<>();
        Array<TextureRegion> enemyFramesLeft = new Array<>();
        Array<TextureRegion> enemyFramesRight = new Array<>();

        // Frames are arranged horizontally in the texture and frameSize is 16
        for (int i = 0; i < 3; i++) {
            enemyFramesDown.add(new TextureRegion(texture, ((i + 9) * 16), 16*4, 16, 16));
            enemyFramesUp.add(new TextureRegion(texture, ((i + 9) * 16), 7 * 16, 16, 16));
            enemyFramesLeft.add(new TextureRegion(texture, ((i + 9) * 16), 16*5, 16, 16));
            enemyFramesRight.add(new TextureRegion(texture, ((i + 9) * 16), 6 * 16, 16, 16));
        }

        // Initialize animations
        enemyDownAnimation = new Animation<>(0.2f, enemyFramesDown);
        enemyUpAnimation = new Animation<>(0.2f, enemyFramesUp);
        enemyLeftAnimation = new Animation<>(0.2f, enemyFramesLeft);
        enemyRightAnimation = new Animation<>(0.2f, enemyFramesRight);
        elapsedTime=0f;


    }

    /**
     * Generates and returns a random direction for enemy movement.
     * @return A Vector2 representing the new direction.
     */
    public Vector2 getRandomDirection() {
        Random random = new Random();
        int randomDirection = random.nextInt(4); // 0: up, 1: down, 2: left, 3: right

        return switch (randomDirection) {
            case 0 -> {
                currentDirection = Direction.UP;
                yield new Vector2(0, 0.5f);
            }
            case 1 -> {
                currentDirection = Direction.DOWN;
                yield new Vector2(0, -0.5f);
            }
            case 2 -> {
                currentDirection = Direction.LEFT;
                yield new Vector2(-0.5f, 0);
            }
            case 3 -> {
                currentDirection = Direction.RIGHT;
                yield new Vector2(0.5f, 0);
            }
            default -> {
                currentDirection = Direction.DOWN;
                yield new Vector2(0, 0);
            }
        };
    }


    /**
     * Enum representing possible movement directions.
     */
    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }


    /**
     * Checks if the enemy is colliding with any obstacles, including exits, walls, and traps.
     * @param position The current position of the enemy.
     * @param exits The exits in the game.
     * @param walls The walls in the game.
     * @param traps The traps in the game.
     * @return true if the enemy is colliding with an obstacle, false otherwise.
     */
    public boolean isCollidingWithObstacles  (Vector2 position, Array<Exit> exits, Array<Wall> walls, Array<Trap> traps) {
        Rectangle enemyRect = new Rectangle(position.x, position.y, 12, 12);

        for (Exit exit : exits) {
            Rectangle rect = new Rectangle(exit.getPosition().x, exit.getPosition().y, 15, 15);

            if (enemyRect.overlaps(rect)) {
                position.sub(direction);
                return true;
            }
        }
        for (Trap trap : traps) {
            Rectangle rect = new Rectangle(trap.getPosition().x, trap.getPosition().y, 15, 15);

            if (enemyRect.overlaps(rect)) {

                position.sub(direction);
                return true;
            }
        }

        for (Wall wall : walls) {
            Rectangle rect = new Rectangle(wall.getPosition().x, wall.getPosition().y, 15, 15);

            if (enemyRect.overlaps(rect)) {

                position.sub(direction);
                return true;
            }
        }
        return false; // No collision with any wall
    }

    /**
     * Renders the enemy on the screen with the correct animation frame based on its movement direction.
     * @param batch The SpriteBatch used for drawing.
     * @param deltaTime The time passed since the last render call.
     */
    public void render(SpriteBatch batch,float deltaTime) {
        elapsedTime += deltaTime;
        TextureRegion currentFrame;

        switch (currentDirection) {
            case UP -> currentFrame = enemyUpAnimation.getKeyFrame(elapsedTime, true);
            case LEFT -> currentFrame = enemyLeftAnimation.getKeyFrame(elapsedTime, true);
            case RIGHT -> currentFrame = enemyRightAnimation.getKeyFrame(elapsedTime, true);
            default -> currentFrame = enemyDownAnimation.getKeyFrame(elapsedTime, true);
        }

        batch.draw(currentFrame, position.x, position.y);
    }

    @Override
    public void update(float delta) {
        elapsedTime += delta;
    }

    public void render(SpriteBatch spriteBatch, float x, float y) {
        spriteBatch.draw(spriteEnemy, x, y);
    }

    /**
     * Updates the enemy's state, including its position, based on the elapsed time since the last update.
     * This method should be called every frame to ensure smooth movement and animation of the enemy.
     *
     * @param delta The time in seconds that has elapsed since the last update call. This is used to
     *              increment the total elapsed time for animations and to determine how far the enemy
     *              should move in this update cycle.
     */
    public void update(float delta, Array<Exit> exits, Array<Wall> walls, Array<Trap> traps) {
        elapsedTime += delta;
        move(exits, walls,traps);

    }

    /**
     * Moves the enemy in its current direction while checking for collisions with obstacles (exits, walls, and traps).
     * If a collision is detected or the enemy has moved its maximum allowed distance in the current direction,
     * it will randomly choose a new direction to move in. This method ensures that the enemy's movement is
     * confined within the bounds of the game world and respects the game's collision rules.
     *
     * @param exits An array of Exit objects in the game. The enemy's movement is checked against these
     *              to prevent it from passing through exit points.
     * @param walls An array of Wall objects in the game. The enemy's movement is checked against these
     *              to prevent it from passing through walls.
     * @param traps An array of Trap objects in the game. The enemy's movement is checked against these
     *              to prevent it from passing through traps.
     */
    private void move(Array<Exit> exits, Array<Wall> walls, Array<Trap> traps) {
        float distance = direction.len();

        if (isCollidingWithObstacles(position,exits,walls,traps) || distanceMoved >= MOVEMENT_DISTANCE) {
            // Change direction if there's a wall or reached the max movement distance

            direction = getRandomDirection();
            distanceMoved = 0f;
        } else {
            // Move in the current direction
            position.add(direction);
            distanceMoved += distance;

        }


    }



    public void dispose() {
        super.dispose(); // Dispose of the base GameObject resources
    }


}
