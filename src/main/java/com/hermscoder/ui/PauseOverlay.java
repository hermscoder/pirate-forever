package com.hermscoder.ui;

import com.hermscoder.gamestates.GameState;
import com.hermscoder.gamestates.Playing;
import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import static com.hermscoder.main.Game.GAME_WIDTH;
import static com.hermscoder.main.Game.SCALE;
import static com.hermscoder.ui.UrmButton.*;
import static com.hermscoder.utils.Sprite.PauseMenuBackgroundSprite;
import static com.hermscoder.utils.Sprite.UrmButtonsSpriteAtlas;

public class PauseOverlay {
    private BufferedImage backgroundImage;
    private int bgX, bgY;
    private AudioOptions audioOptions;

    private PauseButton pauseButtons[];
    private final Playing playing;

    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        audioOptions = playing.getGame().getAudioOptions();
        createUrmButtons();
    }

    private void createUrmButtons() {
        int menuX = (int) (313 * SCALE);
        int replayX = (int) (387 * SCALE);
        int unpauseX = (int) (462 * SCALE);
        int urmY = (int) (325 * SCALE);
        int buttonWidth = UrmButtonsSpriteAtlas.getTileWidth(SCALE);
        int buttonHeight = UrmButtonsSpriteAtlas.getTileHeight(SCALE);

        pauseButtons = new PauseButton[]{
                new UrmButton(menuX, urmY, buttonWidth, buttonHeight, MENU_BUTTON, () -> {
                    GameState.state = GameState.MENU;
                    playing.unpauseGame();
                }),
                new UrmButton(replayX, urmY, buttonWidth, buttonHeight, REPLAY_BUTTON, () -> {
                    playing.resetAll();
                    playing.unpauseGame();
                }),
                new UrmButton(unpauseX, urmY, buttonWidth, buttonHeight, PLAY_BUTTON, playing::unpauseGame)
        };
    }

    private void loadBackground() {
        backgroundImage = LoadSave.getSpriteAtlas(PauseMenuBackgroundSprite.getFilename());
        bgX = GAME_WIDTH / 2 - PauseMenuBackgroundSprite.getTileWidth(SCALE) / 2;
        bgY = (int) (25 * SCALE);
    }

    public void update() {
        for (PauseButton pauseButton : pauseButtons) {
            pauseButton.update();
        }
        audioOptions.update();
    }

    public void draw(Graphics g) {
        // Backgrounds
        g.drawImage(backgroundImage, bgX, bgY,
                PauseMenuBackgroundSprite.getTileWidth(SCALE),
                PauseMenuBackgroundSprite.getTileHeight(SCALE), null);

        for (PauseButton pauseButton : pauseButtons) {
            pauseButton.draw(g);
        }
        audioOptions.draw(g);
    }

    public void mousePressed(MouseEvent e) {
        for (PauseButton pauseButton : pauseButtons) {
            if (isIn(e, pauseButton)) {
                pauseButton.setMousePressed(true);
                break;
            }
        }
        audioOptions.mousePressed(e);
    }

    public void mouseReleased(MouseEvent e) {
        for (PauseButton pauseButton : pauseButtons) {
            if (isIn(e, pauseButton)) {
                pauseButton.onClickAction(e);
                break;
            }
        }

        audioOptions.mouseReleased(e);

        for (PauseButton pauseButton : pauseButtons) {
            pauseButton.resetBooleans();
        }
    }

    public void mouseMoved(MouseEvent e) {
        for (PauseButton pauseButton : pauseButtons) {
            pauseButton.setMouseOver(false);

            if (isIn(e, pauseButton)) {
                pauseButton.setMouseOver(true);
                break;
            }
        }
        audioOptions.mouseMoved(e);
    }

    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged(e);
    }

    public boolean isIn(MouseEvent e, PauseButton pauseButton) {
        return pauseButton.getBounds().contains(e.getX(), e.getY());
    }

    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
