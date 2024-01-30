package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * The MenuScreen class is responsible for displaying the main menu of the game.
 * It extends the LibGDX Screen class and sets up the UI components for the menu.
 */
public class MenuScreen implements Screen {
    private final Stage stage;

//    private Player player;
    private String playerName;



    /**
     * Constructor for MenuScreen. Sets up the camera, viewport, stage, and UI elements.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public MenuScreen(MazeRunnerGame game) {


        var camera = new OrthographicCamera();
        camera.zoom = 1.0f; // Set camera zoom for a closer view

        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements

        // Load the background texture and create an image actor
        final Texture[] backgroundTexture = {new Texture(Gdx.files.internal("backgroundMaze.png"))};
        Image backgroundImage = new Image(backgroundTexture[0]);
        backgroundImage.setFillParent(true); // Make the image fill the whole screen
        stage.addActor(backgroundImage); // Add the background image first so it's drawn behind everything else


        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage



        // Add a label as a title
        table.add(new Label("Maze Runner", game.getSkin(), "title")).padBottom(80).row();

        // Create and add a button to go to the game screen
//        TextButton goToGameButton = new TextButton("Go To Game", game.getSkin());
//        table.add(goToGameButton).width(300).row();
//
//        goToGameButton.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                game.goToGame(); // Change to the game screen when button is pressed
//            }
//        });

        // Add a text field for the player's name
        TextField playerNameTextField = new TextField("", game.getSkin());
        table.add(playerNameTextField).width(300).padBottom(20).row();

        Label submitMessageLabel = new Label("Submit Username to start the Game", game.getSkin(), "default");
//        submitMessageLabel.setColor(Color.PINK); // Set the color to red (you can change it)
        submitMessageLabel.setVisible(false); // Initially, hide the message label

        table.add(submitMessageLabel).padBottom(20).row();


        TextButton goToMapSelectionButton = new TextButton("START", game.getSkin());
        table.add(goToMapSelectionButton).width(300).row();

        goToMapSelectionButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playerName = playerNameTextField.getText().trim();
                if (!playerName.isEmpty()) {

                    game.goToMapSelection(); // Switch to the map selection screen

                } else {
                    submitMessageLabel.setVisible(true); // Show the message label

                }

            }
        });



//        // Set the input processor
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Update the stage
        stage.draw(); // Draw the stage
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Update the stage viewport on resize
    }

    @Override
    public void dispose() {
        // Dispose of the stage when screen is disposed
        stage.dispose();
    }

    @Override
    public void show() {
        // Set the input processor so the stage can receive input events
        Gdx.input.setInputProcessor(stage);


    }

    // The following methods are part of the Screen interface but are not used in this screen.
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    public String getPlayerName() {
        return playerName;
    }
}
