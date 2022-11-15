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
import static com.hermscoder.utils.Sprite.*;

public class PauseOverlay {
    private BufferedImage backgroundImage;
    private int bgX, bgY;
    private PauseButton pauseButtons[];
    private final Playing playing;
    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        createSoundButtons();
        createUrmButtons();
        createVolumeButton();
    }

    private void createSoundButtons() {
        int soundBtnX = (int) (450 * SCALE);
        int musicBtnY = (int) (140 * SCALE);
        int sfxY = (int) (186 * SCALE);
        pauseButtons = new PauseButton[] {
            new SoundButton(soundBtnX, musicBtnY, SoundButtonsSpriteAtlas.getTileWidth(SCALE), SoundButtonsSpriteAtlas.getTileHeight(SCALE)),
            new SoundButton(soundBtnX, sfxY, SoundButtonsSpriteAtlas.getTileWidth(SCALE), SoundButtonsSpriteAtlas.getTileHeight(SCALE))
        };
    }

    private void createUrmButtons() {
        int menuX = (int) (313 * SCALE);
        int replayX = (int) (387 * SCALE);
        int unpauseX = (int) (462 * SCALE);
        int urmY = (int) (325 * SCALE);
        int buttonWidth = UrmButtonsSpriteAtlas.getTileWidth(SCALE);
        int buttonHeight = UrmButtonsSpriteAtlas.getTileHeight(SCALE);

        pauseButtons = concat(pauseButtons, new PauseButton[] {
                new UrmButton(menuX, urmY, buttonWidth, buttonHeight, 2, () -> {
                    GameState.state = GameState.MENU; playing.unpauseGame();
                }),
                new UrmButton(replayX, urmY, buttonWidth, buttonHeight, 1, () -> System.out.println("Replay level!")),
                new UrmButton(unpauseX, urmY, buttonWidth, buttonHeight, 0, playing::unpauseGame)
        });
    }

    private void createVolumeButton() {
        int volumeX = (int) (309 * SCALE);
        int volumeY = (int) (278 * SCALE);

        pauseButtons = concat(pauseButtons, new PauseButton[] {
                new VolumeButton(volumeX, volumeY, VolumeSliderSprite.getTileWidth(SCALE), VolumeButtonsSpriteAtlas.getTileHeight(SCALE))
        });
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
    }

    public void draw(Graphics g) {
        // Backgrounds
        g.drawImage(backgroundImage, bgX, bgY,
                PauseMenuBackgroundSprite.getTileWidth(SCALE),
                PauseMenuBackgroundSprite.getTileHeight(SCALE), null);

        for (PauseButton pauseButton : pauseButtons) {
            pauseButton.draw(g);
        }
    }

    public void mousePressed(MouseEvent e) {
        for (PauseButton pauseButton : pauseButtons) {
            if(isIn(e, pauseButton)) {
                pauseButton.setMousePressed(true);
                break;
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        for (PauseButton pauseButton : pauseButtons) {
            if(isIn(e, pauseButton)) {
                pauseButton.onClickAction(e);
                break;
            }
        }
        for (PauseButton pauseButton : pauseButtons) {
            pauseButton.resetBooleans();
        }
    }

    public void mouseMoved(MouseEvent e) {
        for (PauseButton pauseButton : pauseButtons) {
            pauseButton.setMouseOver(false);

            if(isIn(e, pauseButton)) {
                pauseButton.setMouseOver(true);
                break;
            }
        }
    }

    public void mouseDragged(MouseEvent e) {
        if(pauseButtons[5].isMousePressed())
            ((VolumeButton)pauseButtons[5]).changeX(e.getX());
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
