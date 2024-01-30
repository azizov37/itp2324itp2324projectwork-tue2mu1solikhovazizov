package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The MapSelectScreen class represents the screen for selecting a map before starting the game.
 * It allows the player to choose from predefined levels or load a custom map file.
 */
public class MapSelectScreen implements Screen {
    private final Stage stage;
    private final MazeRunnerGame game;
    private String playerName;


    /**
     * Constructs a new MapSelectScreen associated with the given MazeRunnerGame instance.
     *
     * @param game The MazeRunnerGame instance.
     */
    public MapSelectScreen(MazeRunnerGame game) {
        this.game = game;
        var camera = new OrthographicCamera();
        camera.zoom = 1.0f;

        Viewport viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.getSpriteBatch());

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.add(new Label("Select a Map:", game.getSkin(), "title")).padBottom(80).row();

        // Create buttons for each level (map)
        for (int i = 1; i <= 5; i++) {
            final int level = i;
            TextButton levelButton = new TextButton("Level " + i, game.getSkin());
            table.add(levelButton).width(300).row();

            levelButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    String mapFilePath = "maps/level-"+level+".properties"; // Set the path for the selected level
                    System.out.println(level+" is created for Maze");
                    game.setMaze(new Maze(Gdx.files.internal(mapFilePath).file().getAbsolutePath()));; // Load the selected level's map
                    game.goToGame(); // Switch to the game screen
                }
            });
        }

        TextButton chooseFileButton = new TextButton("Choose File", game.getSkin());
        table.add(chooseFileButton).width(300).row();

        chooseFileButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Open a file chooser dialog for selecting a file from the local computer
//                selectLocalFile();
                game.setMaze(new Maze(Gdx.files.internal(selectLocalFile()).file().getAbsolutePath()));; // Load the selected level's map
                game.goToGame(); // Switch to the game screen
            }
        });


        TextButton backButton = new TextButton("Back to Menu", game.getSkin());
        table.add(backButton).width(300);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToMenu(); // Go back to the main menu
            }
        });
    }

    // Method to open a file chooser dialog for selecting a local file
    private String selectLocalFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "properties");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            // Handle the selected file path (e.g., pass it to the Maze class)
            // You can implement this logic here or in the calling code
            System.out.println("Selected file: " + selectedFilePath);
            return selectedFilePath;
        }

        return null;
    }
    /**
     * Renders the MapSelectScreen. Called on each frame to update and draw the screen.
     *
     * @param delta Time elapsed since the last frame.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}