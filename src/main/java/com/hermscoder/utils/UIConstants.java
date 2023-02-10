package com.hermscoder.utils;

import java.awt.image.BufferedImage;

import static com.hermscoder.utils.Sprite.BigTextLettersSpriteAtlas;
import static com.hermscoder.utils.Sprite.BigTextNumbersSpriteAtlas;

public class UIConstants {

    private static BufferedImage[] bigTextLetterImgs = new BufferedImage[95];
    private static BufferedImage[] bigTextNumbersImgs = new BufferedImage[10];

    private UIConstants() {
    }

    static {
        loadLetterImages();
        loadNumbersImages();
    }

    private static void loadLetterImages() {

        bigTextLetterImgs = new BufferedImage[BigTextLettersSpriteAtlas.getWidthInSprites()];
        BufferedImage temp = LoadSave.getSpriteAtlas(BigTextLettersSpriteAtlas.getFilename());
        for (var i = 0; i < bigTextLetterImgs.length; i++) {
            bigTextLetterImgs[i] = temp.getSubimage(
                    i * BigTextLettersSpriteAtlas.getTileWidth(),
                    0,
                    BigTextLettersSpriteAtlas.getTileWidth(),
                    BigTextLettersSpriteAtlas.getTileHeight());
        }
    }

    private static void loadNumbersImages() {

        bigTextNumbersImgs = new BufferedImage[BigTextNumbersSpriteAtlas.getWidthInSprites()];
        BufferedImage temp = LoadSave.getSpriteAtlas(BigTextNumbersSpriteAtlas.getFilename());
        for (var i = 0; i < bigTextNumbersImgs.length; i++) {
            bigTextNumbersImgs[i] = temp.getSubimage(
                    i * BigTextNumbersSpriteAtlas.getTileWidth(),
                    0,
                    BigTextNumbersSpriteAtlas.getTileWidth(),
                    BigTextNumbersSpriteAtlas.getTileHeight());
        }
    }

    public static BufferedImage getCharacterImage(char c) {
        if (c >= 65) {
            return getLetterCharImage(c);
        } else if (c >= 48) {
            return getNumberCharImage(c);
        }
        return null;
    }

    private static BufferedImage getLetterCharImage(char c) {
        return bigTextLetterImgs[c - 65];
    }

    private static BufferedImage getNumberCharImage(char c) {
        return bigTextNumbersImgs[c - 48];
    }

}
