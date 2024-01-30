package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class HUD {
    public Stage stage;
    private Viewport viewport;


    private int playerLives;
    private boolean keyCollected;

    private Texture texture;
    private Sprite heartTextureRegion;
    private TextureRegion keyTexture;
    private Array<Image> hearts;
    private Image keyIcon;
    private ShapeRenderer shapeRenderer;
    private int width;
    private int height;




    public HUD(int width, int height) {
//        this.viewport = viewport;
//        this.stage = new Stage(viewport);
//        this.shapeRenderer = new ShapeRenderer();

        this.texture = new Texture(Gdx.files.internal("objects.png"));
        this.heartTextureRegion = new Sprite(texture, 16*4, 0, 16,16);

        this.hearts = new Array<>();

    }

    public void render(MazeRunnerGame game, Key key, Player player) {

        for (int i = 0; i < player.getLives(); i++) {
            game.getSpriteBatch().draw(heartTextureRegion, player.getPosition().x+(i*16)-30, player.getPosition().y+60);
        }
        if (!key.isVisible()) {
            game.getSpriteBatch().draw(key.getSpriteKey(), player.getPosition().x - 50, player.getPosition().y + 60);
        }

    }

//    float oldX = Gdx.graphics.getWidth(), Gdx.graphics.getHeight();

    public void dispose() {
        texture.dispose();
//        stage.dispose();
//        shapeRenderer.dispose(); // Add this line to dispose of the ShapeRenderer


    }
    public void resize(int width, int height) {
        // Update the HUD elements' positions

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // Add methods for rendering and updating the HUD as needed
}