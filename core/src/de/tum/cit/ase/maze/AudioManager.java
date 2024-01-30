package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * The AudioManager class manages audio resources and sound effects for the game.
 */
public class AudioManager {
    private final Music backgroundMusic;
    private final Music gameBackgroundMusic;
    private final Sound lifeLostSound;
    private final Sound keyCollectedSound;
    private final Sound doorOpens;
    private final Sound victorySound;
    private final Sound gameOverSound;
    private final Sound moveSound;
    private final Sound hitSound;
    private final Sound ghostSound;
    private float timeSinceLastHit = 0f; // Timer for hit sound
    private boolean gameOverSoundPlayed = false;
    private final float hitSoundInterval = 0.2f;

    /**
     * Constructor for AudioManager. Initializes audio resources.
     */
    public AudioManager() {
        // Load background music and set it to loop
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("background.mp3"));
        backgroundMusic.setLooping(true);
        gameBackgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("game_background.mp3"));
        gameBackgroundMusic.setLooping(true);

        gameBackgroundMusic.setVolume(0.02f);
        backgroundMusic.setVolume(0.01f);

        // Load sound effects
        lifeLostSound = Gdx.audio.newSound(Gdx.files.internal("life_lost.mp3"));
        keyCollectedSound = Gdx.audio.newSound(Gdx.files.internal("key_collect.mp3"));
        doorOpens = Gdx.audio.newSound(Gdx.files.internal("door_opens.mp3"));
        moveSound = Gdx.audio.newSound(Gdx.files.internal("move.mp3"));
        victorySound = Gdx.audio.newSound(Gdx.files.internal("you_won.mp3"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("lost_game_over.mp3"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("collision.mp3"));
        ghostSound = Gdx.audio.newSound(Gdx.files.internal("ghost.mp3"));
    }

    /**
     * Plays the background music.
     */
    public void playBackgroundMusic() {
        if (!backgroundMusic.isPlaying()) {
            backgroundMusic.play();

        }
    }

    /**
     * Stops the background music.
     */
    public void stopBackgroundMusic() {
        if (backgroundMusic.isPlaying()) {
            backgroundMusic.stop();
        }
    }

    /**
     * Plays the game background music.
     */
    public void playGameBackgroundMusic() {
        if (!gameBackgroundMusic.isPlaying()) {
            gameBackgroundMusic.play();

        }
    }

    /**
     * Stops the game background music.
     */
    public void stopGameBackgroundMusic() {
        if (gameBackgroundMusic.isPlaying()) {
            gameBackgroundMusic.stop();
        }
    }

    /**
     * Plays the life lost sound effect with a time interval.
     */
    public void playLifeLostSound() {
        float lostSoundInterval = 0.1f;
        if (timeSinceLastHit >= lostSoundInterval) {
            lifeLostSound.play(0.3f);
            timeSinceLastHit = 0; // Reset the timer
        }
    }

    /**
     * Plays the move sound effect with a time interval.
     */
    public void playMoveSound() {
        if (timeSinceLastHit >= hitSoundInterval) {
            moveSound.play(0.3f);
            timeSinceLastHit = 0; // Reset the timer
        }
    }

    /**
     * Plays the hit sound effect with a time interval.
     */
    public void playHitSound() {

        if (timeSinceLastHit >= hitSoundInterval) {
            hitSound.play(0.3f);
            timeSinceLastHit = 0; // Reset the timer
        }
    }

    /**
     * Plays the ghost sound effect with a time interval.
     */
    public void playGhostSound() {


        if (timeSinceLastHit >= 0.2f) {
            ghostSound.play(0.3f);
            timeSinceLastHit = 0; // Reset the timer
        }
    }

    /**
     * Updates the audio manager with the elapsed time.
     *
     * @param deltaTime The time elapsed since the last frame.
     */
    public void update(float deltaTime) {
        // Increment the timer
        timeSinceLastHit += deltaTime;
    }

    /**
     * Plays the key collected sound and door opens sound simultaneously.
     */
    public void playKeyCollectedSound() {
        keyCollectedSound.play(0.5f);
        doorOpens.play(0.5f);
    }

    /**
     * Plays the victory sound.
     */
    public void playVictorySound() {
        victorySound.play(0.5f);
    }

    /**
     * Plays the game over sound once.
     */
    public void playGameOverSound() {
        if (!gameOverSoundPlayed) {
            gameOverSound.play();
            gameOverSoundPlayed = true;
        }
    }

    /**
     * Disposes of all music and sound assets when no longer needed.
     */
    public void dispose() {
        // Dispose of all music and sound assets when no longer needed
        backgroundMusic.dispose();
        lifeLostSound.dispose();
        keyCollectedSound.dispose();
        victorySound.dispose();
        gameOverSound.dispose();
    }
}
