package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
/**
 * The LoseScreen class represents the screen that is displayed when the player loses the game.
 * It contains a "You Lost!" label and an animation of the player character.
 */
public class LoseScreen implements Screen {
    private final Stage stage;
    private final MazeRunnerGame game;
    private Animation<TextureRegion> characterAnimation;
    private float animationTime;

    /**
     * Constructs a new LoseScreen associated with the given MazeRunnerGame instance.
     *
     * @param game The MazeRunnerGame instance.
     */
    public LoseScreen(MazeRunnerGame game) {
        this.game = game;
        var camera = new OrthographicCamera();
        camera.zoom = 1.0f;

        Viewport viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.getSpriteBatch());

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.add(new Label("You Lost!", game.getSkin(), "title")).padBottom(150).row();

        TextButton backButton = new TextButton("Back to Menu", game.getSkin());
        table.add(backButton).width(300).row();
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToMapSelection(); // Go back to the main menu
            }
        });

        Texture walkSheet = new Texture(Gdx.files.internal("character.png"));

        int frameWidth = 16;
        int frameHeight = 32;
        int animationFrames = 7;


        Array<TextureRegion> walkFrames = new Array<>();

        // Add all frames to the down animation
        for (int i = 5; i < animationFrames; i++) {
            walkFrames.add(new TextureRegion(walkSheet, i * frameWidth, 0, frameWidth, frameHeight));
        }

        characterAnimation = new Animation<>(0.3f, walkFrames);
        animationTime = 0;
    }
    /**
     * Render method called on each frame to update and draw the screen.
     *
     * @param delta Time elapsed since the last frame.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        // Update the animation time
        animationTime += delta;

        // Get the current frame from the animation
        TextureRegion currentFrame = characterAnimation.getKeyFrame(animationTime, true);

        // Scale factor for the animation (adjust as needed)
        float scaleFactor = 5.0f; // You can change this to make it bigger

        // Calculate the scaled width and height
        float scaledWidth = currentFrame.getRegionWidth() * scaleFactor;
        float scaledHeight = currentFrame.getRegionHeight() * scaleFactor;

        // Draw the scaled animation above the "You Won!" label
        float animationX = Gdx.graphics.getWidth() / 2 - scaledWidth / 2;
        float animationY = Gdx.graphics.getHeight() / 2 -50; // Adjust the Y position as needed
        game.getSpriteBatch().begin();
        game.getSpriteBatch().draw(currentFrame, animationX, animationY, scaledWidth, scaledHeight);
        game.getSpriteBatch().end();
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


}
