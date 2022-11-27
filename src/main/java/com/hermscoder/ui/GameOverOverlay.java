package com.hermscoder.ui;

import com.hermscoder.gamestates.GameState;
import com.hermscoder.gamestates.Playing;
import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static com.hermscoder.main.Game.*;
import static com.hermscoder.ui.UrmButton.MENU_BUTTON;
import static com.hermscoder.ui.UrmButton.PLAY_BUTTON;
import static com.hermscoder.utils.Sprite.*;

public class GameOverOverlay {

    private BufferedImage backgroundImage;
    private int bgX, bgY;
    private UrmButton urmButtons[];
    private final Playing playing;


    public GameOverOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        createUrmButtons();
    }

    private void createUrmButtons() {
        int menuX = (int) (335 * SCALE);
        int playX = (int) (440 * SCALE);
        int urmY = (int) (195 * SCALE);
        int buttonWidth = UrmButtonsSpriteAtlas.getTileWidth(SCALE);
        int buttonHeight = UrmButtonsSpriteAtlas.getTileHeight(SCALE);

        urmButtons = new UrmButton[] {
                new UrmButton(menuX, urmY, buttonWidth, buttonHeight, MENU_BUTTON, () -> {
                    playing.resetAll();
                    GameState.state = GameState.MENU;
                }),
                new UrmButton(playX, urmY, buttonWidth, buttonHeight, PLAY_BUTTON, () -> {
                    playing.resetAll();
                }),
        };
    }

    private void loadBackground() {
        backgroundImage = LoadSave.getSpriteAtlas(DeathScreenSprite.getFilename());
        bgX = GAME_WIDTH / 2 - DeathScreenSprite.getTileWidth(SCALE) / 2;
        bgY = (int) (100 * SCALE);
    }

    public void update() {
        for (UrmButton urmButton : urmButtons) {
            urmButton.update();
        }
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        // Backgrounds
        g.drawImage(backgroundImage, bgX, bgY,
                DeathScreenSprite.getTileWidth(SCALE),
                DeathScreenSprite.getTileHeight(SCALE), null);

        for (UrmButton urmButton : urmButtons) {
            urmButton.draw(g);
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playing.resetAll();
            GameState.state = GameState.MENU;
        }
    }

    public void mousePressed(MouseEvent e) {
        for (UrmButton urmButton : urmButtons) {
            if(isIn(e, urmButton)) {
                urmButton.setMousePressed(true);
                break;
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        for (UrmButton urmButton : urmButtons) {
            if(isIn(e, urmButton)) {
                urmButton.onClickAction(e);
                break;
            }
        }
        for (UrmButton urmButton : urmButtons) {
            urmButton.resetBooleans();
        }
    }

    public void mouseMoved(MouseEvent e) {
        for (UrmButton urmButton : urmButtons) {
            urmButton.setMouseOver(false);

            if(isIn(e, urmButton)) {
                urmButton.setMouseOver(true);
                break;
            }
        }
    }

    public boolean isIn(MouseEvent e, UrmButton urmButton) {
        return urmButton.getBounds().contains(e.getX(), e.getY());
    }
}
