package com.hermscoder.ui;

import com.hermscoder.gamestates.GameState;
import com.hermscoder.gamestates.Playing;
import com.hermscoder.main.Game;
import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static com.hermscoder.main.Game.SCALE;
import static com.hermscoder.utils.Sprite.CompletedLevel;
import static com.hermscoder.utils.Sprite.UrmButtonsSpriteAtlas;

public class LevelCompletedOverlay {
    private final Playing playing;
    private BufferedImage img;
    private int bgX;
    private int bgY;

    private UrmButton[] urmButtons;

    public LevelCompletedOverlay(Playing playing) {
        this.playing = playing;
        initImage();
        initButtons();
    }

    private void initButtons() {
        int menuX = (int) (330 * SCALE);
        int nextX = (int) (445 * SCALE);
        int y = (int) (195 * SCALE);

        urmButtons = new UrmButton[]{
                new UrmButton(
                        nextX, y,
                        UrmButtonsSpriteAtlas.getTileWidth(SCALE),
                        UrmButtonsSpriteAtlas.getTileHeight(SCALE), 0,
                        () -> {
                            playing.getLevelManager().getCurrentLevel().setCompleted(true);
                            playing.getGame()
                                    .getPlayerStats()
                                    .setLevelAsCompleted(playing.getLevelManager().getCurrentLevel().getIndex());
                            playing.getGame()
                                    .getPlayerStats()
                                    .addMapFragmentsToCollection(playing.getPlayer().getMapFragmentsCollected());

                            //TODO record the time to complete the level and add it here
                            playing.getGame()
                                    .getPlayerStats()
                                    .addLevelTime(playing.getLevelManager().getCurrentLevel().getIndex(), 0L);

                            playing.resetAll();
                            playing.setGameState(GameState.MAP_VIEW);

                        }),
                new UrmButton(menuX, y,
                        UrmButtonsSpriteAtlas.getTileWidth(SCALE),
                        UrmButtonsSpriteAtlas.getTileHeight(SCALE), 2,
                        () -> {
                            playing.resetAll();
                            playing.setGameState(GameState.MENU);
                        }
                )
        };
    }

    private void initImage() {
        img = LoadSave.getSpriteAtlas(CompletedLevel.getFilename());
        bgX = Game.GAME_WIDTH / 2 - CompletedLevel.getTileWidth(Game.SCALE) / 2;
        bgY = (int) (75 * Game.SCALE);
    }

    public void draw(Graphics g) {
        g.drawImage(img, bgX, bgY,
                CompletedLevel.getTileWidth(SCALE),
                CompletedLevel.getTileHeight(SCALE), null);

        for (UrmButton urmButton : urmButtons) {
            urmButton.draw(g);
        }
    }

    public void update() {
        for (UrmButton urmButton : urmButtons) {
            urmButton.update();
        }
    }

    public void mousePressed(MouseEvent e) {
        for (UrmButton urmButton : urmButtons) {
            if (isIn(e, urmButton)) {
                urmButton.setMousePressed(true);
                break;
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        for (UrmButton urmButton : urmButtons) {
            if (isIn(e, urmButton)) {
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

            if (isIn(e, urmButton)) {
                urmButton.setMouseOver(true);
                break;
            }
        }
    }

    public boolean isIn(MouseEvent e, UrmButton urmButton) {
        return urmButton.getBounds().contains(e.getX(), e.getY());
    }
}
