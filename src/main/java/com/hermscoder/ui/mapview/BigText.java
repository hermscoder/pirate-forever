package com.hermscoder.ui.mapview;

import com.hermscoder.main.Game;
import com.hermscoder.utils.UIConstants;

import java.awt.*;

import static com.hermscoder.utils.Sprite.BigTextLettersSpriteAtlas;

public class BigText {

    private final int xPos;
    private final int yPos;
    private final int xOffsetCenter;
    private final String text;

    public BigText(int xPos, int yPos, String text) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xOffsetCenter = BigTextLettersSpriteAtlas.getTileWidth(Game.SCALE) / 2;
        this.text = text;
    }

    public void draw(Graphics g) {
        for (char c : text.toCharArray()) {
            g.drawImage(UIConstants.getCharacterImage(c), xPos - xOffsetCenter, yPos, BigTextLettersSpriteAtlas.getTileWidth(Game.SCALE), BigTextLettersSpriteAtlas.getTileHeight(Game.SCALE), null);
        }
    }
}
