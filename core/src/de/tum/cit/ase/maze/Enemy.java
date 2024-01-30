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

public class Enemy extends GameObject{
    private Texture textureEnemy;
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




    public Enemy(Vector2 position) {
        super(position);


        direction = getRandomDirection();
        distanceMoved = 0f;
        textureEnemy = new Texture(Gdx.files.internal("mobs.png"));

        this.spriteEnemy = new Sprite(textureEnemy, 96, 64, 16, 16);


        Array<TextureRegion> enemyFramesDown = new Array<>();
        Array<TextureRegion> enemyFramesUp = new Array<>();
        Array<TextureRegion> enemyFramesLeft = new Array<>();
        Array<TextureRegion> enemyFramesRight = new Array<>();

        // Frames are arranged horizontally in the texture and frameSize is 16
        for (int i = 0; i < 3; i++) {
            enemyFramesDown.add(new TextureRegion(textureEnemy, ((i + 9) * 16), 16*4, 16, 16));
            enemyFramesUp.add(new TextureRegion(textureEnemy, ((i + 9) * 16), 7 * 16, 16, 16));
            enemyFramesLeft.add(new TextureRegion(textureEnemy, ((i + 9) * 16), 16*5, 16, 16));
            enemyFramesRight.add(new TextureRegion(textureEnemy, ((i + 9) * 16), 6 * 16, 16, 16));
        }

        enemyDownAnimation = new Animation<>(0.2f, enemyFramesDown);
        enemyUpAnimation = new Animation<>(0.2f, enemyFramesUp);
        enemyLeftAnimation = new Animation<>(0.2f, enemyFramesLeft);
        enemyRightAnimation = new Animation<>(0.2f, enemyFramesRight);
        elapsedTime=0f;


    }



    public Vector2 getRandomDirection() {
        Random random = new Random();
        int randomDirection = random.nextInt(4); // 0: up, 1: down, 2: left, 3: right

        switch (randomDirection) {
            case 0:
                currentDirection=Direction.UP;
                return new Vector2(0, 0.5f);
            case 1:
                currentDirection=Direction.DOWN;
                return new Vector2(0, -0.5f);
            case 2:
                currentDirection=Direction.LEFT;
                return new Vector2(-0.5f, 0);
            case 3:
                currentDirection=Direction.RIGHT;
                return new Vector2(0.5f, 0);
            default:
                currentDirection=Direction.DOWN;
                return new Vector2(0, 0);
        }
    }




    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }



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




    private Animation<TextureRegion> getCurrentAnimation() {
        // Determine which animation to use based on the current direction
        if (direction.x > 0) {
            return enemyRightAnimation;
        } else if (direction.x < 0) {
            return enemyLeftAnimation;
        } else if (direction.y > 0) {
            return enemyUpAnimation;
        } else if (direction.y < 0) {
            return enemyDownAnimation;
        } else {
            // Default to the down animation if no specific direction
            return enemyDownAnimation;
        }
    }


    public void render(SpriteBatch batch,float deltaTime) {
        elapsedTime += deltaTime;
        TextureRegion currentFrame;

        switch (currentDirection) {
            case UP -> currentFrame = enemyUpAnimation.getKeyFrame(elapsedTime, true);
            case DOWN -> currentFrame = enemyDownAnimation.getKeyFrame(elapsedTime, true);
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

    public void update(float delta, Array<Exit> exits, Array<Wall> walls, Array<Trap> traps) {
        elapsedTime += delta;
        move(exits, walls,traps);

    }

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
        textureEnemy.dispose();
    }


}
