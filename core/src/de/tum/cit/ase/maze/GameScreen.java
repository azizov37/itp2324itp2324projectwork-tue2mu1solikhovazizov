package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * The GameScreen class is responsible for rendering the gameplay screen.
 * It handles the game logic and rendering of the game elements.
 */
public class GameScreen implements Screen {

    private final MazeRunnerGame game;
    private final OrthographicCamera camera;
    private final BitmapFont font;

    private float sinusInput = 0f;


    //Updated @Abdul

    private OrthographicCamera hudCamera;
    private SpriteBatch hudSpriteBatch;

    private AudioManager audioManager;
    private Player player;
    private String playerName;

    private IntelligentEnemy yovuz;

    private float glitchTimer = 0f;
    private float winInterval = 0.5f;
    private boolean playerDamage = false;
    private final float glitchDuration = 0.1f; //

    private Maze maze;


    private Stage stage;
    private Viewport stageViewport; // Add this line


    private int cellSize = 16; // Adjust the cell size as needed
    private boolean paused = false;
    private boolean usernameNotSubmitted = true;
    private Array<Wall> walls;
    private Array<Trap> traps;
    private Array<Enemy> enemies;
    private Array<Exit> exits;
    private Key key;

//    //HuD
    private Texture spriteSheet;
    private Sprite heartTextureRegion;



    /**
     * Constructor for GameScreen. Sets up the camera and font.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public GameScreen(MazeRunnerGame game) {

        this.game = game;

        // Create and configure the camera for the game view
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.zoom = 0.2f;

        // Get the font from the game's skin
        font = game.getSkin().getFont("font");
        font.getData().setScale(camera.zoom+0.3f);

        //Updated @Abdul

        // Initialize the stage and skin
        stageViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Use FitViewport
        stage = new Stage(stageViewport);
        Gdx.input.setInputProcessor(stage);

//        // Initialize the stage and skin
//        stage = new Stage();
//        Gdx.input.setInputProcessor(stage);

        this.audioManager = new AudioManager();
        this.player = new Player(new Vector2());
        this.walls = new Array<Wall>();
        this.enemies = new Array<Enemy>();
        this.traps = new Array<Trap>();
        this.exits = new Array<Exit>();

        this.maze = game.getMaze();

        initializePlayer(maze);


        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                Cell cell = maze.getCell(x, y);
                if (cell != null) {
                    // Render each cell with the desired size
                    // Render each cell with the desired size
                    float cellX = x * cellSize;
                    float cellY = y * cellSize;

                    if (cell.getType()==0||cell.getType()==6) {
                        Wall temp;
                        if (cell.getType()==0) {
                            temp=cell.getWall();
                        } else {
                            temp=cell.getWall();
                            temp.setBackWall(true);

                        }

                        temp.position.set(cellX,cellY);
                        walls.add(temp);
                        }


                    if (cell.getType()==2) {
                        Exit temp=cell.getExit();
                        temp.position.set(cellX,cellY);
                        exits.add(temp);
                    }
                    if (cell.getType()==3) {
                        Trap temp=cell.getTrap();
                        temp.position.set(cellX,cellY);
                        traps.add(temp);
                    }
                    if (cell.getType()==4) {

                        Enemy temp=cell.getEnemy();
                        temp.position.set(cellX,cellY);
                        enemies.add(temp);
                    }
                    if (cell.getType()==5) {
                        this.key = cell.getKey();
                        key.setPosition(new Vector2(cellX,cellY));
                    }
                    walls.add(new Wall(new Vector2(player.getPosition().x-16,player.getPosition().y)));
                }

            }
        }
        this.yovuz = new IntelligentEnemy(new Vector2(key.getPosition().x,key.getPosition().y));


        spriteSheet = new Texture(Gdx.files.internal("objects.png"));
        heartTextureRegion = new Sprite(spriteSheet, 16*4, 0, 16,16);

        player.cheating(game.getPlayerName());

        // Create a camera and sprite batch for the HUD
        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false);
        hudSpriteBatch = new SpriteBatch();
    }





    // Screen interface methods with necessary functionality
    @Override
    public void render(float delta) {

        // Check for escape key press to go back to the menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE )) {
            game.goToMenu();
        }

        if (glitchTimer==0){
            playerDamage = player.isCollidingWithEnemies(player.getPosition(),enemies,yovuz,delta) || player.isCollidingWithTraps(player.getPosition(),traps,delta);
        }


        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen

        camera.position.set(player.getPosition().x, player.getPosition().y, 0);
        camera.update(); // Update the camera

        // Set up and begin drawing with the sprite batch
        game.getSpriteBatch().setProjectionMatrix(camera.combined);

        game.getSpriteBatch().begin(); // Important to call this before drawing anything


        sinusInput += delta;


//        // Set the projection matrix to ensure the text is rendered in screen coordinates
//        game.getSpriteBatch().setProjectionMatrix(camera.combined);




//


//            playerRender(delta);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            paused = true; // Pause the game when the spacebar is pressed

        } else if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
            paused = false; // Resume the game when Backspace is pressed
        }

//        hud.render(game.getSpriteBatch(),key,player);

        if (!paused) {


//            if (playerName != null && !playerName.isEmpty()) {
//                float i = player.getPosition().x; // Adjust the X position as needed
//                float j = player.getPosition().y + 10; // Adjust the Y position as needed
//                font.draw(game.getSpriteBatch(), playerName, i, j);
//            }

            if (!player.playerLost()) {
                mazeRender(maze,game.getSpriteBatch(),cellSize);
                yovuz.render(game.getSpriteBatch(),delta);
                update(delta);

//                renderHUD();
                if (playerDamage){
                audioManager.playLifeLostSound();
                }
                if (player.playerWon(player.getPosition(),exits)) {


                    winInterval-=delta;
                    if (winInterval<=0){
                        audioManager.playVictorySound();
                        paused=true;
                    }
                }

                handleInput(delta);
            }else  {

//                renderLoseMessage();
                game.goToloseMenu();
                audioManager.playGameOverSound();


            }

        }

        if (yovuz.checker()) {
            audioManager.playGhostSound();
        }


        if (paused) {

            if (player.playerWon(player.getPosition(),exits)) {
//                renderWonMessage();
                game.goToWonMenu();
            } else {
                stage.act(); // Update the stage
                stage.draw(); // Draw the stage (buttons)
            }



        }
//        player.isCollidingWithExits(player.getPosition(),exits);
//
//        player.isCollidingWithKey(player.getPosition(),key);




        game.getSpriteBatch().end(); // Important to call this after drawing everything

        hudSpriteBatch.setProjectionMatrix(hudCamera.combined);
        hudCamera.position.set(player.getPosition(), 0);
        hudCamera.zoom=0.2f;
        hudSpriteBatch.begin();

        // Render HUD elements here
        // For example, you can use hudSpriteBatch to draw HUD components
        renderHUD();

        hudSpriteBatch.end();
    }

    private void renderPauseMessage() {
        GlyphLayout layout = new GlyphLayout(font, "PAUSED");
        float x = camera.position.x - layout.width / 2;
        float y = (camera.position.y + layout.height) -10;

        font.draw(game.getSpriteBatch(), "PAUSED", x,y);

    }

    private void setupPauseMenu() {
        Table table = new Table();
        table.setFillParent(true); // Makes the table the size of the stage
        Skin skin = game.getSkin();

        // Create buttons
        TextButton resumeButton = new TextButton("Resume", skin);
        TextButton mainMenuButton = new TextButton("Main Menu", skin);
        TextButton restartButton = new TextButton("Restart", skin);

        // Add click listeners to the buttons
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Resume the game
                paused = false;
            }
        });

        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Go back to the main menu
                game.goToMapSelection();
            }
        });

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                restart();

                game.setScreen(new GameScreen(game)); // Create a new GameScreen

            }
        });

        // Add buttons to the table
        table.add(resumeButton).padBottom(20).row();
        table.add(mainMenuButton).padBottom(20).row();
        table.add(restartButton);

        stage.addActor(table);
    }

    private void restart() {
        // Restart the game
        key.setVisible(true);
        player.setGotKey(false);
        for (Exit exit : exits) {
            exit.setClosed(true);
        }
        player.setLives(5);
    }

    public void mazeRender(Maze maze, SpriteBatch batch, int cellSize) {
        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                Cell cell = maze.getCell(x, y);
                if (cell != null) {
                    // Render each cell with the desired size
                    float cellX = x * cellSize;
                    float cellY = y * cellSize;
                    cell.render(batch, cellX, cellY);


                }
            }
        }
    }

    private void renderHUD() {


        for (int i = 0; i < player.getLives(); i++) {
            // Scale the heart texture to be larger (e.g., 2 times the original size)
            float scale = 3.0f; // Adjust the scale factor as needed
            hudSpriteBatch.draw(
                    heartTextureRegion,
                    (i * 50) + 600,
                    800,
                    heartTextureRegion.getRegionWidth() * scale,
                    heartTextureRegion.getRegionHeight() * scale
            );
        }

        if (!key.isVisible()) {
            // Scale the key texture to be larger (e.g., 2 times the original size)
            float scale = 3.5f; // Adjust the scale factor as needed
            hudSpriteBatch.draw(
                    key.getSpriteKey(),
                    560,
                    795,
                    key.getSpriteKey().getWidth() * scale,
                    key.getSpriteKey().getHeight() * scale
            );
        }

        float oldX = font.getScaleX();
        float oldY = font.getScaleY();

        float newScale = 1.2f;
        String message = "PLAYER: " + game.getPlayerName();
        font.getData().setScale(newScale);

        font.draw(hudSpriteBatch, message, 10, 840);

        font.getData().setScale(oldX,oldY);

    }

    private void updateHUD( int width, int height) {

    }


    private void update(float delta) {

        key.update(delta);
        audioManager.update(delta);



        for (Enemy enemy : enemies) {
//            enemy.update(delta);

            enemy.update(delta, exits, walls,traps);

            enemy.render(game.getSpriteBatch(),delta);




        }

        for (Trap trap : traps) {

            trap.update(delta);


        }
        for (Wall wall : walls) {

            wall.update(delta);


        }

        player.update(delta);
        yovuz.update(delta,player.getPosition(),walls);

    }






    @Override
    public void resize(int width, int height) {
//        stageViewport.update(width, height);
        camera.setToOrtho(false);
//        hud.resize(width, height);


//        stage.getViewport().update(width, height, true);


    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {
        setupPauseMenu();
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        audioManager.dispose();
    }

    private void initializePlayer(Maze maze) {
        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                Cell cell = maze.getCell(x, y);
                if (cell != null && cell.getPlayer() != null) {
                    this.player = cell.getPlayer();

                    this.player.setPosition(new Vector2(x*cellSize,y*cellSize));
                    break; // Assuming there's only one player in the maze
                }
            }
        }
    }

    // Additional methods and logic can be added as needed for the game screen


    private void handleInput(float delta) {
        if (player == null) {
            return; // Player not initialized
        }

        float moveAmount = player.getPlayerSpeed() * delta;
        float deltaX = 0, deltaY = 0;
        boolean isMoving = false; // Flag to check if the player is moving

        if (playerDamage) {
            glitchTimer += delta;
            if (glitchTimer >= glitchDuration) {
                playerDamage = false; // End glitch effect
                glitchTimer = 0;
            }
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                if (!playerDamage) {

                game.getSpriteBatch().draw(player.getCharacterUpAnimation().getKeyFrame(sinusInput),player.getPosition().x,player.getPosition().y);
                }
                deltaY = moveAmount;
                isMoving = true;
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                if (!playerDamage) {


                    game.getSpriteBatch().draw(player.getCharacterDownAnimation().getKeyFrame(sinusInput), player.getPosition().x, player.getPosition().y);
                }
                deltaY = -moveAmount;
                isMoving = true;
            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                if (!playerDamage) {

                    game.getSpriteBatch().draw(player.getCharacterLeftAnimation().getKeyFrame(sinusInput), player.getPosition().x, player.getPosition().y);
                }
                deltaX = -moveAmount;
                isMoving = true;
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                if (!playerDamage) {

                    game.getSpriteBatch().draw(player.getCharacterRightAnimation().getKeyFrame(sinusInput), player.getPosition().x, player.getPosition().y);
                }
                deltaX = moveAmount;
                isMoving = true;
            }

            if (key.isVisible()&&player.isCollidingWithKey(new Vector2(player.getPosition().x+deltaX,player.getPosition().y+deltaY),key)) {
                key.setVisible(false);
                audioManager.playKeyCollectedSound();
                player.setGotKey(true);
                for (Exit exit : exits) {
                    exit.setClosed(false);
                }
                System.out.println("This case");
            }





            // Check if moving in the new direction would cause a collision
            if (!player.isCollidingWithWalls(new Vector2(player.getPosition().x + deltaX, player.getPosition().y + deltaY), walls) &&
                    (!player.isCollidingWithExits(new Vector2(player.getPosition().x + deltaX, player.getPosition().y + deltaY), exits))) {
                // Only move the player if there is no collision
                player.move(deltaX, deltaY);
                if (isMoving) {
                    audioManager.playMoveSound();
                }

            } else {
                audioManager.playHitSound();
            }

                if (!isMoving && !playerDamage) {
                    game.getSpriteBatch().draw(player.getSpritePlayer(), player.getPosition().x, player.getPosition().y);

                }



        }


    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }


}
