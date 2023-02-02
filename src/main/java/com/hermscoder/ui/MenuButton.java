package com.hermscoder.ui;

import com.hermscoder.gamestates.GameState;
import com.hermscoder.main.Game;
import com.hermscoder.utils.LoadSave;
import com.hermscoder.utils.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuButton {
    private final int xPos, yPos, rowIndex;
    private final int xOffsetCenter;
    private final Sprite buttonSprite;
    private final GameState state;

    private BufferedImage[] imgs;
    private int index = 0;
    private boolean mouseOver, mousePressed;

    // HitBox of the button
    private Rectangle bounds;

    public MenuButton(int xPos, int yPos, int rowIndex, GameState state, Sprite sprite) {
        this.buttonSprite = sprite;
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        this.xOffsetCenter = buttonSprite.getTileWidth(Game.SCALE) / 2;

        loadImages();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xPos - xOffsetCenter, yPos, buttonSprite.getTileWidth(Game.SCALE), buttonSprite.getTileHeight(Game.SCALE));
    }

    private void loadImages() {
        imgs = new BufferedImage[buttonSprite.getWidthInSprites()];
        BufferedImage temp = LoadSave.getSpriteAtlas(buttonSprite.getFilename());
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(
                    i * buttonSprite.getTileWidth(),
                    rowIndex * buttonSprite.getTileHeight(),
                    buttonSprite.getTileWidth(),
                    buttonSprite.getTileHeight());
        }
    }

    public void draw(Graphics g) {
        g.drawImage(imgs[index], xPos - xOffsetCenter, yPos, buttonSprite.getTileWidth(Game.SCALE), buttonSprite.getTileHeight(Game.SCALE), null);
    }

    public void update() {
        index = 0;
        if (mouseOver) {
            index = 1;
        }
        if (mousePressed)
            index = 2;
    }

    public void applyGameState() {
        GameState.state = state;
    }


    public void resetBooleans() {
        mouseOver = false;
        mousePressed = false;
    }

    // Getters and Setters

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public GameState getState() {
        return state;
    }
}
