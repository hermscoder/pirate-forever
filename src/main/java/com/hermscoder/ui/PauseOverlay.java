package com.hermscoder.ui;

import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static com.hermscoder.main.Game.GAME_WIDTH;
import static com.hermscoder.main.Game.SCALE;
import static com.hermscoder.utils.Sprite.PauseMenuBackgroundSprite;
import static com.hermscoder.utils.Sprite.SoundButtonsSpriteAtlas;

public class PauseOverlay {
    private BufferedImage backgroundImage;
    private int bgX, bgY;
    private PauseButton pauseButtons[];

    public PauseOverlay() {
        loadBackground();
        createSoundButtons();
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
            }
        }
    }

    public boolean isIn(MouseEvent e, PauseButton pauseButton) {
        return pauseButton.getBounds().contains(e.getX(), e.getY());
    }
}
