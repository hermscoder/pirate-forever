package com.hermscoder.gamestates;

import com.hermscoder.main.Game;
import com.hermscoder.ui.AudioOptions;
import com.hermscoder.ui.PauseButton;
import com.hermscoder.ui.UrmButton;
import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static com.hermscoder.main.Game.SCALE;
import static com.hermscoder.utils.Sprite.*;

public class GameOptions extends State implements StateMethods {

    private final static int MENU_MARGIN_TOP = 33;

    private AudioOptions audioOptions;
    private Image backgroundImage;
    private BufferedImage optionsBackgroundImage;
    private int bgX, bgY;
    private UrmButton menuButton;

    public GameOptions(Game game) {
        super(game);
        loadImages();
        loadButtons();
        this.audioOptions = game.getAudioOptions();
    }

    private void loadButtons() {
        int menuX = (int) (387 * SCALE);
        int menuY = (int) (325 * SCALE);

        menuButton = new UrmButton(
                menuX, menuY,
                MenuButtonsSpriteAtlas.getTileWidth(Game.SCALE),
                MenuButtonsSpriteAtlas.getTileHeight(Game.SCALE),
                UrmButton.MENU_BUTTON, () -> game.getPlaying().setGameState(GameState.MENU));
    }

    private void loadImages() {
        backgroundImage = LoadSave.getGifImage(StartMenuBackground.getFilename());

        optionsBackgroundImage = LoadSave.getSpriteAtlas(OptionsBackgroundSprite.getFilename());
        bgX = (Game.GAME_WIDTH / 2) - OptionsBackgroundSprite.getTileWidth(SCALE) / 2;
        bgY = (int) (MENU_MARGIN_TOP * SCALE);
    }

    @Override
    public void update() {
        menuButton.update();
        audioOptions.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, StartMenuBackground.getTileWidth(SCALE), StartMenuBackground.getTileHeight(SCALE), null);

        g.drawImage(optionsBackgroundImage, bgX, bgY,
                OptionsBackgroundSprite.getTileWidth(SCALE),
                OptionsBackgroundSprite.getTileHeight(SCALE), null);

        menuButton.draw(g);
        audioOptions.draw(g);
    }

    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuButton))
            menuButton.setMousePressed(true);
        else
            audioOptions.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuButton)) {
            menuButton.onClickAction(e);
        } else
            audioOptions.mouseReleased(e);

        menuButton.resetBooleans();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menuButton.setMouseOver(false);

        if (isIn(e, menuButton)) {
            menuButton.setMouseOver(true);
        } else
            audioOptions.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            game.getPlaying().setGameState(GameState.MENU);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public boolean isIn(MouseEvent e, PauseButton pauseButton) {
        return pauseButton.getBounds().contains(e.getX(), e.getY());
    }
}
