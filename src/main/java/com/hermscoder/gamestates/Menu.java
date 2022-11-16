package com.hermscoder.gamestates;

import com.hermscoder.main.Game;
import com.hermscoder.ui.MenuButton;
import com.hermscoder.utils.LoadSave;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static com.hermscoder.main.Game.*;
import static com.hermscoder.utils.Sprite.*;

public class Menu extends State implements StateMethods{

    private final static int NUMBER_OF_MENU_BUTTONS = 3;
    private final static GameState[] BUTTON_STATES = new GameState[] { GameState.PLAYING, GameState.OPTIONS, GameState.QUIT };
    private final static int VERTICAL_SPACE_BETWEEN_BUTTONS = (int) (14 * SCALE);
    private final static int FIRST_MENU_ITEM_MARGIN_TOP = (int) (150 * SCALE);
    private final static int MENU_MARGIN_TOP = 45;

    private Image backgroundImage;
    private BufferedImage menuBackgroundImage;
    private int menuX, menuY;

    private MenuButton[] buttons = new MenuButton[NUMBER_OF_MENU_BUTTONS];

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
    }

    private void loadBackground() {
        backgroundImage = LoadSave.getGifImage(StartMenuBackground.getFilename());

        menuBackgroundImage = LoadSave.getSpriteAtlas(MenuBackgroundSprite.getFilename());
        menuX = (Game.GAME_WIDTH / 2) - (MenuBackgroundSprite.getTileWidth(SCALE) / 2);
        menuY = (int) (MENU_MARGIN_TOP * SCALE);
    }

    private void loadButtons() {
        for (int i =0; i < buttons.length; i++) {
            buttons[i] = new MenuButton(
                    Game.GAME_WIDTH / 2,
                    (int) (FIRST_MENU_ITEM_MARGIN_TOP + ((MenuButtonsSpriteAtlas.getTileHeight(SCALE) + VERTICAL_SPACE_BETWEEN_BUTTONS) * i)),
                    i,
                    BUTTON_STATES[i]);
        }
    }

    @Override
    public void update() {
        for (MenuButton button : buttons) {
            button.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, StartMenuBackground.getTileWidth(), StartMenuBackground.getTileHeight(), null);

        g.drawImage(menuBackgroundImage, menuX, menuY,
                MenuBackgroundSprite.getTileWidth(SCALE),
                MenuBackgroundSprite.getTileHeight(SCALE), null);

        for (MenuButton button : buttons) {
            button.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton menuButton : buttons) {
            if(isIn(e, menuButton)) {
                menuButton.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton menuButton : buttons) {
            if(isIn(e, menuButton)) {
                if(menuButton.isMousePressed()) {
                    menuButton.applyGameState();
                    break;
                }
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for (MenuButton menuButton : buttons) {
            menuButton.resetBooleans();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton menuButton : buttons) {
            menuButton.setMouseOver(false);
            if(isIn(e, menuButton)) {
                menuButton.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            GameState.state = GameState.PLAYING;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
