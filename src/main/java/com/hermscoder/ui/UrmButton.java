package com.hermscoder.ui;

import com.hermscoder.main.Game;
import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static com.hermscoder.main.Game.SCALE;
import static com.hermscoder.utils.Sprite.UrmButtonsSpriteAtlas;

public class UrmButton extends PauseButton {
    public static final int PLAY_BUTTON = 0;
    public static final int REPLAY_BUTTON = 1;
    public static final int MENU_BUTTON = 2;

    private BufferedImage[] imgs;
    private int rowIndex, columnIndex;
    private final Runnable onClickAction;

    public UrmButton(int x, int y, int width, int height, int rowIndex, Runnable onClickAction) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadSoundImages();
        this.onClickAction = onClickAction;
    }

    private void loadSoundImages() {
        BufferedImage temp = LoadSave.getSpriteAtlas(UrmButtonsSpriteAtlas.getFilename());
        imgs = new BufferedImage[UrmButtonsSpriteAtlas.getHeightInSprites()];
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(
                    i * UrmButtonsSpriteAtlas.getTileWidth(),
                    rowIndex * UrmButtonsSpriteAtlas.getTileHeight(),
                    UrmButtonsSpriteAtlas.getTileWidth(),
                    UrmButtonsSpriteAtlas.getTileHeight());
        }
    }

    @Override
    public void update() {
        columnIndex = 0;
        if(mouseOver)
            columnIndex = 1;
        if(mousePressed)
            columnIndex = 2;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(imgs[columnIndex], x, y,
                UrmButtonsSpriteAtlas.getTileWidth(SCALE),
                UrmButtonsSpriteAtlas.getTileHeight(SCALE), null);
    }

    @Override
    public void onClickAction(MouseEvent e) {
        if(mousePressed){
            onClickAction.run();
        }
    }
}
