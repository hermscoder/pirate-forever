package com.hermscoder.ui;

import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static com.hermscoder.main.Game.SCALE;
import static com.hermscoder.utils.Sprite.VolumeButtonsSpriteAtlas;
import static com.hermscoder.utils.Sprite.VolumeSliderSprite;

public class VolumeButton extends PauseButton {
    private BufferedImage[] imgs;
    private BufferedImage sliderImg;
    private int index = 0;
    private int buttonX;
    private int sliderX;
    private int sliderWidth;
    private int minSliderX, maxSliderX;

    public VolumeButton(int x, int y, int width, int height) {
        super(x + width / 2, y, VolumeButtonsSpriteAtlas.getTileWidth(SCALE), height);
        bounds.x -= VolumeButtonsSpriteAtlas.getTileWidth(SCALE) / 2;
        buttonX = x + width / 2;
        sliderX = x;
        sliderWidth = width;
        minSliderX = x + VolumeButtonsSpriteAtlas.getTileWidth(SCALE) / 2;
        maxSliderX = x + width - VolumeButtonsSpriteAtlas.getTileWidth(SCALE) / 2;
        loadImages();
    }

    private void loadImages() {
        BufferedImage temp = LoadSave.getSpriteAtlas(VolumeButtonsSpriteAtlas.getFilename());
        imgs = new BufferedImage[VolumeButtonsSpriteAtlas.getWidthInSprites()];
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(
                    i * VolumeButtonsSpriteAtlas.getTileWidth(),
                    0,
                    VolumeButtonsSpriteAtlas.getTileWidth(),
                    VolumeButtonsSpriteAtlas.getTileHeight());
        }

        temp = LoadSave.getSpriteAtlas(VolumeSliderSprite.getFilename());
        var sliderX = temp.getWidth() - VolumeSliderSprite.getTileWidth();
        sliderImg = temp.getSubimage(sliderX, 0, VolumeSliderSprite.getTileWidth(), VolumeSliderSprite.getTileHeight());
    }

    @Override
    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(sliderImg, sliderX, y, sliderWidth, height, null);
        g.drawImage(imgs[index], buttonX - VolumeButtonsSpriteAtlas.getTileWidth(SCALE)/2, y, VolumeButtonsSpriteAtlas.getTileWidth(SCALE), height, null);
    }

    public void changeX(int x) {
        if(x < minSliderX)
            buttonX = minSliderX;
        else if(x > maxSliderX)
            buttonX = maxSliderX;
        else
            buttonX = x ;

        bounds.x = buttonX - VolumeButtonsSpriteAtlas.getTileWidth(SCALE)/2;
    }

    @Override
    public void onClickAction(MouseEvent e) {
        if (mousePressed) {
        }
    }
}
