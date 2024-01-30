package de.tum.cit.ase.maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import games.spooky.gdx.nativefilechooser.NativeFileChooser;

/**
 * The MazeRunnerGame class represents the core of the Maze Runner game.
 * It manages the screens and global resources like SpriteBatch and Skin.
 */
public class MazeRunnerGame extends Game {
    // Screens
    private MenuScreen menuScreen;
    private GameScreen gameScreen;
    private MapSelectScreen mapSelectScreen;
    private WonScreen wonScreen;
    private LoseScreen loseScreen;

    // Sprite Batch for rendering
    private SpriteBatch spriteBatch;
    private AudioManager audioManager;

    // UI Skin
    private Skin skin;

    private Maze maze;
    private String playerName;

    /**
     * Constructor for MazeRunnerGame.
     *
     * @param fileChooser The file chooser for the game, typically used in desktop environment.
     */
    public MazeRunnerGame(NativeFileChooser fileChooser) {
        super();
    }

    /**
     * Called when the game is created. Initializes the SpriteBatch and Skin.
     */
    @Override
    public void create() {
        spriteBatch = new SpriteBatch(); // Create SpriteBatch
        skin = new Skin(Gdx.files.internal("craft/craftacular-ui.json")); // Load UI skin

        // Play some background music
        this.audioManager = new AudioManager();
        audioManager.playBackgroundMusic();

        goToMenu(); // Navigate to the menu screen
    }

    /**
     * Switches to the menu screen.
     */
    public void goToMenu() {
        menuScreen = new MenuScreen(this);
        this.setScreen(menuScreen); // Set the current screen to MenuScreen

        if (gameScreen != null) {
            gameScreen.dispose(); // Dispose the game screen if it exists
            gameScreen = null;
        }

        if (wonScreen != null) {
            wonScreen.dispose(); // Dispose the game screen if it exists
            wonScreen = null;
        }

        if (loseScreen != null) {
            loseScreen.dispose(); // Dispose the game screen if it exists
            loseScreen = null;
        }

        audioManager.playBackgroundMusic();
        audioManager.stopGameBackgroundMusic();
    }

    /**
     * Switches to the game screen.
     */
    public void goToGame() {
        this.setScreen(new GameScreen(this)); // Set the current screen to GameScreen
        if (mapSelectScreen != null) {

            mapSelectScreen.dispose(); // Dispose the menu screen if it exists
            mapSelectScreen = null;
        }

        audioManager.stopBackgroundMusic();
        audioManager.playGameBackgroundMusic();

    }
    /**
     * Switches to the won menu screen.
     */
    public void goToWonMenu() {
        this.setScreen(new WonScreen(this)); // Set the current screen to GameScreen
        if (gameScreen != null) {

            gameScreen.dispose(); // Dispose the menu screen if it exists
            gameScreen = null;
        }

        audioManager.playVictorySound();

    }
    /**
     * Switches to the lose menu screen.
     */
    public void goToloseMenu() {
        this.setScreen(new LoseScreen(this)); // Set the current screen to GameScreen
        if (gameScreen != null) {

            gameScreen.dispose(); // Dispose the menu screen if it exists
            gameScreen = null;
        }

        audioManager.playGameOverSound();

    }
    /**
     * Switches to the Map Selection screen.
     */
    public void goToMapSelection() {


        if (menuScreen != null) {
            playerName = menuScreen.getPlayerName();
            if (playerName!=null) {
                System.out.println("not null and " + playerName);
            }

            menuScreen.dispose(); // Dispose the menu screen if it exists
            menuScreen = null;
        }

        // Create the MapSelectScreen before setting the player name
        MapSelectScreen mapSelectScreen = new MapSelectScreen(this);

        // Set the player name in the MapSelectScreen
        mapSelectScreen.setPlayerName(playerName);

        // Set the current screen to MapSelectScreen
        this.setScreen(mapSelectScreen);
    }

    /**
     * Cleans up resources when the game is disposed.
     */
    @Override
    public void dispose() {
        getScreen().hide(); // Hide the current screen
        getScreen().dispose(); // Dispose the current screen
        spriteBatch.dispose(); // Dispose the spriteBatch
        skin.dispose(); // Dispose the skin
    }

    // Getter methods
    public Skin getSkin() {
        return skin;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public Maze getMaze() {
        return maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    public String getPlayerName() {
        return playerName;
    }

}
