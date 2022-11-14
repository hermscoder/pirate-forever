package com.hermscoder.ui;

import com.hermscoder.gamestates.GameState;
import com.hermscoder.main.Game;
import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.hermscoder.utils.Sprite.MenuButtonsSpriteAtlas;

public class MenuButton {
    private final int xPos, yPos, rowIndex;
    private final int xOffsetCenter = MenuButtonsSpriteAtlas.getTileWidth(Game.SCALE)/2;
    private final GameState state;

    private BufferedImage[] imgs;
    private int index = 0;
    private boolean mouseOver, mousePressed;

    // HitBox of the button
    private Rectangle bounds;

    public MenuButton(int xPos, int yPos, int rowIndex, GameState state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;

        loadImages();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xPos - xOffsetCenter, yPos, MenuButtonsSpriteAtlas.getTileWidth(Game.SCALE), MenuButtonsSpriteAtlas.getTileHeight(Game.SCALE));
    }

    private void loadImages() {
        imgs = new BufferedImage[MenuButtonsSpriteAtlas.getHeightInSprites()];
        BufferedImage temp = LoadSave.getSpriteAtlas(MenuButtonsSpriteAtlas.getFilename());
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(
                    i * MenuButtonsSpriteAtlas.getTileWidth(),
                    rowIndex * MenuButtonsSpriteAtlas.getTileHeight(),
                    MenuButtonsSpriteAtlas.getTileWidth(),
                    MenuButtonsSpriteAtlas.getTileHeight());
        }
    }

    public void draw(Graphics g) {
        g.drawImage(imgs[index], xPos - xOffsetCenter, yPos, MenuButtonsSpriteAtlas.getTileWidth(Game.SCALE), MenuButtonsSpriteAtlas.getTileHeight(Game.SCALE), null);
    }

    public void update() {
        index = 0;
        if(mouseOver) {
            index = 1;
        }
        if(mousePressed)
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
}
