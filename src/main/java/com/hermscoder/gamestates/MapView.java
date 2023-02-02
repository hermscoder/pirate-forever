package com.hermscoder.gamestates;

import com.hermscoder.main.Game;
import com.hermscoder.ui.MenuButton;
import com.hermscoder.ui.PauseButton;
import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static com.hermscoder.main.Game.SCALE;
import static com.hermscoder.utils.Sprite.*;

public class MapView extends State implements StateMethods {

    private final static int MENU_MARGIN_TOP = 33;
    private final static int MAP_MARGIN_TOP = MENU_MARGIN_TOP + 112;

    private Image backgroundImage;
    private BufferedImage mapViewMenunBackgroundImage;
    private BufferedImage[] mapLevelsImages;
    private int bgX, bgY;
    private int mapX, mapY;
    private MenuButton menuButton;

    public MapView(Game game) {
        super(game);
        loadImages();
        loadButtons();
    }

    private void loadButtons() {
        int menuX = (int) (260 * SCALE);
        int menuY = (int) (325 * SCALE);

        menuButton = new MenuButton(
                menuX, menuY,
                2,
                GameState.MENU,
                UrmButtonsSpriteAtlas
        );
    }

    private void loadImages() {
        backgroundImage = LoadSave.getGifImage(StartMenuBackground.getFilename());

        mapViewMenunBackgroundImage = LoadSave.getSpriteAtlas(MapViewBackgroundSprite.getFilename());
        bgX = (Game.GAME_WIDTH / 2) - MapViewBackgroundSprite.getTileWidth(SCALE) / 2;
        bgY = (int) (MENU_MARGIN_TOP * SCALE);

        mapX = (Game.GAME_WIDTH / 2) - MapLevelsSpriteAtlas.getTileWidth(SCALE) / 2;
        mapY = (int) (MAP_MARGIN_TOP * SCALE);

        loadBigMapImages();

    }

    private void loadBigMapImages() {
        mapLevelsImages = new BufferedImage[MapLevelsSpriteAtlas.getWidthInSprites()];
        BufferedImage temp = LoadSave.getSpriteAtlas(MapLevelsSpriteAtlas.getFilename());
        for (int i = 0; i < mapLevelsImages.length; i++) {
            mapLevelsImages[i] = temp.getSubimage(
                    i * MapLevelsSpriteAtlas.getTileWidth(),
                    0,
                    MapLevelsSpriteAtlas.getTileWidth(),
                    MapLevelsSpriteAtlas.getTileHeight());
        }
    }

    @Override
    public void update() {
        menuButton.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, StartMenuBackground.getTileWidth(SCALE), StartMenuBackground.getTileHeight(SCALE), null);

        g.drawImage(mapViewMenunBackgroundImage, bgX, bgY,
                MapViewBackgroundSprite.getTileWidth(SCALE),
                MapViewBackgroundSprite.getTileHeight(SCALE), null);

        g.drawImage(mapLevelsImages[18], mapX, mapY,
                MapLevelsSpriteAtlas.getTileWidth(SCALE),
                MapLevelsSpriteAtlas.getTileHeight(SCALE), null);
//        g.drawString(menuButton.isMouseOver() ? "mouse over" :  "mouse not over", (int)(387 * SCALE), (int)(340 * SCALE));
        menuButton.draw(g);
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuButton))
            menuButton.setMousePressed(true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuButton)) {
            menuButton.applyGameState();
        }

        menuButton.resetBooleans();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menuButton.setMouseOver(false);

        if (isIn(e, menuButton)) {
            menuButton.setMouseOver(true);
        }
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
