package com.hermscoder.levels;

import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static com.hermscoder.main.Game.SCALE;
import static com.hermscoder.utils.Sprite.*;

public class LevelBackground {

    private final BufferedImage backgroundImage;
    private final BufferedImage bigClouds;
    private final BufferedImage smallClouds;

    private int[] smallCloudsPos;
    private Random random = new Random();

    public LevelBackground() {
        backgroundImage = LoadSave.getSpriteAtlas(PlayingBackgroundImage.getFilename());
        bigClouds = LoadSave.getSpriteAtlas(BigCloudImage.getFilename());
        smallClouds = LoadSave.getSpriteAtlas(SmallCloudImage.getFilename());
        smallCloudsPos = new int[8];
        for (int i = 0; i < smallCloudsPos.length; i++) {
            smallCloudsPos[i] = (int) ((90 * SCALE) + random.nextInt((int) (100 * SCALE)));
        }
    }

    public void draw(Graphics g, int xLevelOffset) {
        g.drawImage(backgroundImage, 0, 0,
                PlayingBackgroundImage.getTileWidth(SCALE),
                PlayingBackgroundImage.getTileHeight(SCALE), null);
        drawClouds(g, xLevelOffset);
    }

    public void drawClouds(Graphics g, int xLevelOffset) {
        int bigCloudWidth = BigCloudImage.getTileWidth(SCALE);
        int bigCloudHeight = BigCloudImage.getTileHeight(SCALE);
        for (int i = 0; i < 3; i++) {
            g.drawImage(bigClouds, i * bigCloudWidth - (int)(xLevelOffset * 0.3), (int) (204 * SCALE),
                    bigCloudWidth,
                    bigCloudHeight, null);

        }

        int smallCloudWidth = SmallCloudImage.getTileWidth(SCALE);
        for (int i = 0; i < smallCloudsPos.length; i++) {
            g.drawImage(smallClouds, smallCloudWidth * 4 * i - (int)(xLevelOffset * 0.7), smallCloudsPos[i],
                    smallCloudWidth,
                    SmallCloudImage.getTileHeight(SCALE), null);
        }
    }
}
