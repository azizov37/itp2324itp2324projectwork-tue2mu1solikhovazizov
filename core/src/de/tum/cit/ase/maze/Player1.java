//package de.tum.cit.ase.maze;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.*;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.graphics.g2d.Animation;
//
///**
// *  Player1 is a class for a player character in a Maze Runner game.
// */
//
//
//public class Player1 {
//    private final Animation<TextureRegion> characterWonAnimation;
//    //characterWonAnimation: Represents the animation when the character wins the game.
//    private Vector2 position;
//    //position: Represents the 2D position of the player in the game world, using a Vector2
//    private Texture playerTexture;
//    //Holds the texture of the player character loaded from an image file (character.png).
//    private Sprite spritePlayer;
//
//    //A Sprite object created from the player texture, specifying the region to be used.
//    private Texture entryTexture;
//    //Holds the texture for the entry point.
//    private Sprite spriteEntry;
//    // Sprite object created from the entry texture.
//    private float playerSpeed = 40f;
//    //Represents the movement speed of the player character.
//    private boolean isMoving = false;
//
//    //Indicates whether the player is currently moving.
//    private boolean gotKey = false;
//
//    //Indicates whether the player has obtained a key.
//    private int lives = 5;
//
//    //Represents the number of lives the player has.
//    private float sinusInput = 0f;
//
//    //Used for animation or movement purposes.
//
//    private float stateTime;
//
//    //A variable to keep track of the time for animation purposes
//    private Animation<TextureRegion> characterDownAnimation;
//    private Animation<TextureRegion> characterUpAnimation;
//    private Animation<TextureRegion> characterLeftAnimation;
//    private Animation<TextureRegion> characterRightAnimation;
//
//    //Animations for player movement in different directions.
//
//    private float lastCollisionTime = -2.0f; // Initialize to -2.0 so that the first collision can occur immediately
//
//    /**
//     *
//     * Constructor for Player1
//     * The constructor loads textures for the player and entry point from image files.
//     * It creates "Sprite" objects with specified regions, and sets up animations for player movement (up, down, left, right) and winning.
//     * @param position
//     *
//     */
//    public Player1(Vector2 position) {
//        this.position = position;
//        this.playerTexture = new Texture(Gdx.files.internal("character.png"));
//        this.spritePlayer = new Sprite(playerTexture,0,0,16,32);
//        this.entryTexture = new Texture(Gdx.files.internal("basictiles.png"));
//        this.spriteEntry = new Sprite(entryTexture,16,16*11,16,16);
//        int frameWidth = 16;
//        int frameHeight = 32;
//        int animationFrames = 4;
//
//        //Initializes the player's position and loads the player texture and entry texture from specified files.
//        //Creates Sprite objects for the player and entry textures.
//        //Initializes animations for each direction and the winning animation.
//        //Initializes other variables
//
//        // Initialize animations for each direction
//        characterDownAnimation = createAnimation(0, 0,frameWidth, frameHeight, animationFrames);
//        characterUpAnimation = createAnimation(2, 0,frameWidth, frameHeight, animationFrames);
//        characterLeftAnimation = createAnimation(3, 0,frameWidth, frameHeight, animationFrames);
//        characterRightAnimation = createAnimation(1, 0,frameWidth, frameHeight, animationFrames);
//        characterWonAnimation = createAnimation(1, 9,frameWidth, frameHeight, animationFrames);
//
//        this.stateTime = 0f;
//    }
//
//    private Animation<TextureRegion> createAnimation(int row, int column, int frameWidth, int frameHeight, int frames) {
//        Array<TextureRegion> walkFrames = new Array<>(TextureRegion.class);
//        for (int col = column; col < frames; col++) {
//            walkFrames.add(new TextureRegion(playerTexture, col * frameWidth, row * frameHeight, frameWidth, frameHeight));
//        }
//        return new Animation<>(0.1f, walkFrames, Animation.PlayMode.LOOP);
//    }
//
//
//    /**
//     *
//     * This method (move) updates the position of the player by adding the specified values (deltaX for horizontal movement and deltaY for vertical movement) to its current coordinates.
//     * @param deltaX
//     * @param deltaY
//     */
//    public void move(float deltaX, float deltaY) {
//        position.x += deltaX;
//        position.y += deltaY;
////        currentDirection = getDirection(deltaX, deltaY);
//    }
//
////    private Direction getDirection(float deltaX, float deltaY) {
////        if (deltaX > 0) return Direction.RIGHT;
////        if (deltaX < 0) return Direction.LEFT;
////        if (deltaY > 0) return Direction.UP;
////        if (deltaY < 0) return Direction.DOWN;
////        return Direction.IDLE;
////    }
//
//
//    /**
//     *
//     *
//     This render method is responsible for rendering the player's sprite on the game screen using a SpriteBatch. It takes the spriteBatch as a parameter, along with the specified coordinates (x and y) where the player's sprite should be drawn.
//     *
//     * @param spriteBatch
//     * @param x
//     * @param y
//     */
//    public void render(SpriteBatch spriteBatch, float x, float y) {
////        TextureRegion currentFrame = getCurrentFrame();
//        spriteBatch.draw(spritePlayer, x, y);
//    }
////    public void renderEntry(SpriteBatch spriteBatch, float x, float y) {
//////        TextureRegion currentFrame = getCurrentFrame();
////        spriteBatch.draw(spriteEntry, x, y);
////    }
//
//
//
////    private TextureRegion getCurrentFrame() {
////        switch (currentDirection) {
////            case UP: return characterUpAnimation.getKeyFrame(stateTime, true);
////            case DOWN: return characterDownAnimation.getKeyFrame(stateTime, true);
////            case LEFT: return characterLeftAnimation.getKeyFrame(stateTime, true);
////            case RIGHT: return characterRightAnimation.getKeyFrame(stateTime, true);
////            case IDLE: // Fall back to a default frame, for example the first frame of down animation
////            default: return characterDownAnimation.getKeyFrames()[0];
////        }
////    }
//
//
//    /**
//     *  This isCollidingWithWalls method checks for collisions between the player and walls in the game
//     *
//     * @param position
//     * @param walls
//     * @return
//     */
//    public boolean isCollidingWithWalls(Vector2 position, Array<Wall> walls) {
//        Rectangle playerRect = new Rectangle(position.x, position.y, 12, 10);
//
//        //For each wall in the array, it creates rectangles representing the player (playerRect) and the current wall (wallRect)
//        for (Wall wall : walls) {
//            Rectangle wallRect = new Rectangle(wall.getPosition().x, wall.getPosition().y, 12, 12);
//            if (playerRect.overlaps(wallRect)) {
//                System.out.println("Collision detected at: Player [" + position.x + ", " + position.y + "] Wall [" + wall.getPosition().x + ", " + wall.getPosition().y + "]");
//                return true; // Collision detected with at least one wall
//            }
//
//
//        }
//
//        return false; // No collision with any wall
//    }
//
//
//    /**
//     * This method checks for collisions between the player and  traps in the game
//     * @param position
//     * @param traps
//     * @param delta
//     * @return
//     */
//    public boolean isCollidingWithTraps(Vector2 position, Array<Trap> traps, float delta) {
//        // Update the time since the last collision
//        lastCollisionTime += delta;
//
//        Rectangle playerRect = new Rectangle(position.x, position.y, 12, 10);
//
//        for (Trap trap : traps) {
//            Rectangle rect = new Rectangle(trap.getPosition().x, trap.getPosition().y, 12, 12);
//            if (playerRect.overlaps(rect)) {
//                if (lastCollisionTime >= 2.0f) {
//                    lives--;
//
//                    System.out.println("TRAP, live-1");
//                    lastCollisionTime = 0; // Reset the timer
//                    return true; // Collision detected with at least one wall
//                }
//                // If there is an overlap between these rectangles and a sufficient time has passed since the last collision (2.0 seconds),
//                // it decrements the player's lives, prints a message, resets the collision timer, and returns true to indicate a collision
//            }
//        }
//
//        return false; // No collision with any wall
//    }
//
//
//    /**
//     *
//     * The isCollidingWithEnemies method checks for collisions between the player and enemies in the game
//     * @param position player's current position
//     * @param enemies an array of enemies
//     * @param delta the time elapsed since the last collision
//     * @return
//     */
//
//    public boolean isCollidingWithEnemies (Vector2 position, Array<Enemy> enemies, float delta) {
//        // Update the time since the last collision
//        lastCollisionTime += delta;
//        Rectangle playerRect = new Rectangle(position.x, position.y, 12, 10);
//        //It iterates through the enemy array, creating rectangles representing the player (playerRect) and each enemy (rect)
//        for (Enemy enemy : enemies) {
//            Rectangle rect = new Rectangle(enemy.getPosition().x, enemy.getPosition().y, 12, 12);
//            if (playerRect.overlaps(rect)) {
//                if (lastCollisionTime >= 2.0f) {
//                    lives--;
//
//                    System.out.println("ENEMY, live-1");
//                    lastCollisionTime = 0; // Reset the timer
//                    return true; // Collision detected with at least one wall
//                }
//                // If there is an overlap between these rectangles and a sufficient time has passed since the last collision (2.0 seconds),
//                // it decrements the player's lives, prints a message, resets the collision timer, and returns true to indicate a collision
//            }
//        }
//
//        return false; // No collision with any wall
//    }
//
//    /**
//     * The playerLost method is a simple boolean check to determine if the player has lost the game.
//     * It evaluates whether the player's remaining lives (lives) are less than or equal to zero
//     * @return
//     */
//    public boolean playerLost(){
//        if (lives<=0){
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     *
//     * The isCollidingWithExits method checks if the player is colliding with any of the exits in the game
//     * @param position
//     * @param exits an array of exits
//     * @return
//     */
//
//    public boolean isCollidingWithExits (Vector2 position, Array<Exit> exits) {
//        Rectangle playerRect = new Rectangle(position.x, position.y, 12, 10);
//
//
//        //For each exit in the array, it creates a rectangle (Rectangle rect) representing the exit's position and size. It then checks if the player's rectangle (playerRect) overlaps with the exit's rectangle using the overlaps method.
//        for (Exit exit : exits) {
//            Rectangle rect = new Rectangle(exit.getPosition().x, exit.getPosition().y, 12, 12);
//
//            if (playerRect.overlaps(rect)) {
//
//                if (exit.isClosed()==false) {
//                    return false;
//                } else {
//                    return true; // Collision detected with at least one wall
//                }
//            }
//        }
//
//        return false; // No collision with any wall
//    }
//
//
//    /**
//     *
//     *  The playerWon method is responsible for checking if the player has won the game
//     * @param position
//     * @param exits an array of exits
//     * @return
//     */
//    public boolean playerWon (Vector2 position, Array<Exit> exits) {
//        Rectangle playerRect = new Rectangle(position.x, position.y, 12, 10);
//
//        for (Exit exit : exits) {
//            Rectangle rect = new Rectangle(exit.getPosition().x, exit.getPosition().y, 12, 12);
//
//            if (playerRect.overlaps(rect)) {
//
//                if (isGotKey()==true&&exit.isClosed()==false)
//                    System.out.println("You won");
//                return true; // Collision detected with at least one wall
//            }
//        }
//
//        return false; // No collision with any wall
//    }
//
//
//    /**
//     * The isCollidingWithKey method is responsible for checking if the player's position overlaps with the position of a key.
//     * @param position
//     * @param key instance of the Key class (Key key)
//     * @return
//     */
//    public boolean isCollidingWithKey (Vector2 position, Key key) {
//        Rectangle playerRect = new Rectangle(position.x, position.y, 12, 10);
//        //it creates a rectangle (playerRect) representing the player's position and size, using the specified dimensions(12 units in width and 10 units in height).
//
//            Rectangle rect = new Rectangle(key.getPosition().x, key.getPosition().y, 12, 12);
//            if (playerRect.overlaps(rect)) {
//                System.out.println("GOT KEY");
//                return true; // Collision detected with at least one wall
//            }//it creates another rectangle (rect) representing the position and size of the key. This information is obtained by querying the key object for its position using key.getPosition().
//
//
//        return false; // No collision with any wall
//    }
//
//
//    /**
//     * The update method is responsible for updating the internal state of the Player1 object based on the elapsed time (deltaTime) since the last update.
//     * @param deltaTime
//     */
//    public void update(float deltaTime) {
//        stateTime += deltaTime;
//    }
//
//    // Dispose, getPosition, setPosition, getPlayerSpeed methods...
//
//
//
//    public Vector2 getPosition() {
//        return position;
//    }
//
//    public void setPosition(Vector2 position) {
//        this.position = position;
//    }
//
//    public float getPlayerSpeed() {
//        return playerSpeed;
//    }
//
//    public void setPlayerSpeed(float playerSpeed) {
//        this.playerSpeed = playerSpeed;
//    }
//
//    public Texture getPlayerTexture() {
//        return playerTexture;
//    }
//
//    public void setPlayerTexture(Texture playerTexture) {
//        this.playerTexture = playerTexture;
//    }
//
//    public Sprite getSpritePlayer() {
//        return spritePlayer;
//    }
//
//    public void setSpritePlayer(Sprite spritePlayer) {
//        this.spritePlayer = spritePlayer;
//    }
//
//    public float getStateTime() {
//        return stateTime;
//    }
//
//    public void setStateTime(float stateTime) {
//        this.stateTime = stateTime;
//    }
//
//    public Animation<TextureRegion> getCharacterDownAnimation() {
//        return characterDownAnimation;
//    }
//
//    public void setCharacterDownAnimation(Animation<TextureRegion> characterDownAnimation) {
//        this.characterDownAnimation = characterDownAnimation;
//    }
//
//    public Animation<TextureRegion> getCharacterUpAnimation() {
//        return characterUpAnimation;
//    }
//
//    public void setCharacterUpAnimation(Animation<TextureRegion> characterUpAnimation) {
//        this.characterUpAnimation = characterUpAnimation;
//    }
//
//    public Animation<TextureRegion> getCharacterLeftAnimation() {
//        return characterLeftAnimation;
//    }
//
//    public void setCharacterLeftAnimation(Animation<TextureRegion> characterLeftAnimation) {
//        this.characterLeftAnimation = characterLeftAnimation;
//    }
//
//    public Animation<TextureRegion> getCharacterRightAnimation() {
//        return characterRightAnimation;
//    }
//
//    public void setCharacterRightAnimation(Animation<TextureRegion> characterRightAnimation) {
//        this.characterRightAnimation = characterRightAnimation;
//    }
//
//
//
//    public Animation<TextureRegion> getCharacterWonAnimation() {
//        return characterWonAnimation;
//    }
//
//    public boolean isMoving() {
//        return isMoving;
//    }
//
//    public void setMoving(boolean moving) {
//        isMoving = moving;
//    }
//
//    public boolean isGotKey() {
//        return gotKey;
//    }
//
//    public void setGotKey(boolean gotKey) {
//        this.gotKey = gotKey;
//    }
//
//    public int getLives() {
//        return lives;
//    }
//}