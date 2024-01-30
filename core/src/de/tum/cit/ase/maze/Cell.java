package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


/**
 * The Cell class represents a cell in the game maze.
 * Each cell can have different types, such as walls, players, enemies, traps, keys, exits, and background.
 * It provides methods for rendering and updating the cell based on its type.
 */
public class Cell {
    private int type;
    private Player player;
    private Enemy enemy;
    private Trap trap;
    private Key key;
    private Wall wall;
    private Exit exit;
    private Background background;
    private Background backgrounded;


    /**
     * Constructor for the Cell class.
     * Initializes a cell with a specified type and position.
     *
     * @param type The type of the cell.
     * @param x    The x-coordinate of the cell in the maze.
     * @param y    The y-coordinate of the cell in the maze.
     */
    public Cell(int type, int x, int y) {
        this.backgrounded = new Background(new Vector2(x,y));
        this.type = type;

        // Initialize objects based on the type
        switch (type) {
            case 0,6:

                this.wall = new Wall(new Vector2(x,y));
                break;
            case 1:
                this.player = new Player(new Vector2(x, y));
                break;
            case 2:
                this.exit = new Exit(new Vector2(x,y));
                break;
            case 3:
                this.trap = new Trap(new Vector2(x, y));
                break;
            case 4:
                this.enemy = new Enemy(new Vector2(x, y));
                break;
            case 5:
                this.key = new Key(new Vector2(x, y));
                break;

            case 7:
                this.background = new Background(new Vector2(x,y));
                break;
        }
    }

    /**
     * Renders the cell at a specified position using the provided SpriteBatch.
     *
     * @param spriteBatch The SpriteBatch used for rendering.
     * @param x            The x-coordinate where the cell should be rendered.
     * @param y            The y-coordinate where the cell should be rendered.
     */
    public void render(SpriteBatch spriteBatch, float x, float y) {
        spriteBatch.draw(backgrounded.getSpriteBackground(),x,y);
        // Render objects based on the type
        switch (type) {
            case 0,6:
                wall.render(spriteBatch,x,y);
                break;
            case 1:
                player.renderEntry(spriteBatch,x,y);
                break;
            case 2:
                exit.render(spriteBatch,x,y);
                break;
            case 3:
                trap.render(spriteBatch, x, y);
                break;
            case 4:
//                Enemy is rendered within GameScreen
                break;
            case 5:
                key.render(spriteBatch, x, y);
                break;

        }
    }


    /**
     * Updates the cell based on the elapsed time.
     *
     * @param delta The time passed since the last frame.
     */
    public void update(float delta) {
        switch (type) {
            case 0,6:
                wall.update(delta);
                break;
            case 1:
                player.update(delta);
                break;
            case 2:
                exit.update(delta);
                break;
            case 3:
                trap.update(delta);
                break;
            case 4:
//                Enemy update is implemented within GameScreen
                break;
            case 5:
                key.update(delta);
                break;

        }
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Player getPlayer() {
        return player;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public Trap getTrap() {
        return trap;
    }

    public Key getKey() {
        return key;
    }

    public Wall getWall() {
        return wall;
    }

    public Exit getExit() {
        return exit;
    }

}


