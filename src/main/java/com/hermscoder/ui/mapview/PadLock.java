package com.hermscoder.ui.mapview;

import com.hermscoder.main.Game;
import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.hermscoder.utils.Sprite.PadLockSprite;

public class PadLock extends UIAnimated{

    private final int xPos;
    private final int yPos;
    private final int xOffsetCenter;
    private BufferedImage[] imgs;
    private boolean unlocking;
    private Runnable afterUnlockAction;

    public PadLock(int xPos, int yPos) {
        super(25, PadLockSprite.getWidthInSprites(), false);

        this.xPos = xPos;
        this.yPos = yPos;
        this.xOffsetCenter = PadLockSprite.getTileWidth(Game.SCALE) / 2;

        loadImages();

    }

    @Override
    public void afterAnimationFinishedAction() {
        afterUnlockAction.run();
    }

    private void loadImages() {
        imgs = new BufferedImage[PadLockSprite.getWidthInSprites()];
        BufferedImage temp = LoadSave.getSpriteAtlas(PadLockSprite.getFilename());
        for (var i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(
                    i * PadLockSprite.getTileWidth(),
                    0,
                    PadLockSprite.getTileWidth(),
                    PadLockSprite.getTileHeight());
        }
    }

    public void draw(Graphics g) {
        if(active)
            g.drawImage(imgs[animationIndex], xPos - xOffsetCenter, yPos, PadLockSprite.getTileWidth(Game.SCALE), PadLockSprite.getTileHeight(Game.SCALE), null);
    }

    public void update() {
        if(active && unlocking) {
            updateAnimationTick();
        }
    }

    public void fireUnlock(Runnable afterUnlock) {
        unlocking = true;
        afterUnlockAction = afterUnlock;
    }
}
