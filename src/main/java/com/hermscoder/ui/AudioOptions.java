package com.hermscoder.ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import static com.hermscoder.main.Game.SCALE;
import static com.hermscoder.utils.Sprite.*;

public class AudioOptions {
    private PauseButton audioButtons[];

    public AudioOptions() {
        createSoundButtons();
        createVolumeButton();
    }

    private void createSoundButtons() {
        int soundBtnX = (int) (450 * SCALE);
        int musicBtnY = (int) (140 * SCALE);
        int sfxY = (int) (186 * SCALE);
        audioButtons = new PauseButton[]{
                new SoundButton(soundBtnX, musicBtnY, SoundButtonsSpriteAtlas.getTileWidth(SCALE), SoundButtonsSpriteAtlas.getTileHeight(SCALE)),
                new SoundButton(soundBtnX, sfxY, SoundButtonsSpriteAtlas.getTileWidth(SCALE), SoundButtonsSpriteAtlas.getTileHeight(SCALE))
        };
    }

    private void createVolumeButton() {
        int volumeX = (int) (309 * SCALE);
        int volumeY = (int) (278 * SCALE);

        audioButtons = concat(audioButtons, new PauseButton[]{
                new VolumeButton(volumeX, volumeY, VolumeSliderSprite.getTileWidth(SCALE), VolumeButtonsSpriteAtlas.getTileHeight(SCALE))
        });
    }

    public void update() {
        for (PauseButton pauseButton : audioButtons) {
            pauseButton.update();
        }
    }

    public void draw(Graphics g) {
        for (PauseButton pauseButton : audioButtons) {
            pauseButton.draw(g);
        }
    }

    public void mousePressed(MouseEvent e) {
        for (PauseButton pauseButton : audioButtons) {
            if (isIn(e, pauseButton)) {
                pauseButton.setMousePressed(true);
                break;
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        for (PauseButton pauseButton : audioButtons) {
            if (isIn(e, pauseButton)) {
                pauseButton.onClickAction(e);
                break;
            }
        }
        for (PauseButton pauseButton : audioButtons) {
            pauseButton.resetBooleans();
        }
    }

    public void mouseMoved(MouseEvent e) {
        for (PauseButton pauseButton : audioButtons) {
            pauseButton.setMouseOver(false);

            if (isIn(e, pauseButton)) {
                pauseButton.setMouseOver(true);
                break;
            }
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (audioButtons[2].isMousePressed())
            ((VolumeButton) audioButtons[2]).changeX(e.getX());
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
