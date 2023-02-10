package com.hermscoder.ui.mapview;

import com.hermscoder.levels.Level;
import com.hermscoder.main.Game;
import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.hermscoder.utils.Sprite.LevelButtonSpriteAtlas;

public class LevelButton extends UIAnimated {
    private final int xPos;
    private final int yPos;
    private final int xOffsetCenter;
    private final Level level;
    private final BigText bigText;
    private final PadLock padLock;

    private BufferedImage[][] imgs;
    private int index = 0;
    private int state = LOCKED;
    private boolean mouseOver;
    private boolean mousePressed;
    private boolean unlocking;

    // HitBox of the button
    private Rectangle bounds;

    private final Supplier<Boolean> checkUnlockIsDoableAction;
    private final Consumer<Level> onClickAction;

    private static final int LOCKED = 0;
    private static final int UNLOCKED = 1;
    private static final int COMPLETED = 2;

    public LevelButton(int xPos, int yPos, Level level, Supplier<Boolean> checkUnlockIsDoableAction, Consumer<Level> onClickAction) {
        super(25, 1, false);
        this.xPos = xPos;
        this.yPos = yPos;
        this.level = level;
        this.xOffsetCenter = LevelButtonSpriteAtlas.getTileWidth(Game.SCALE) / 2;

        this.checkUnlockIsDoableAction = checkUnlockIsDoableAction;
        this.onClickAction = onClickAction;

        loadImages();
        initBounds();
        this.bigText = new BigText(xPos, 5 + yPos, String.valueOf(level.getIndex()));
        this.padLock = new PadLock(5 + xPos, 5 + yPos);
    }

    private void initBounds() {
        bounds = new Rectangle(xPos - xOffsetCenter, yPos, LevelButtonSpriteAtlas.getTileWidth(Game.SCALE), LevelButtonSpriteAtlas.getTileHeight(Game.SCALE));
    }

    private void loadImages() {
        imgs = new BufferedImage[LevelButtonSpriteAtlas.getHeightInSprites()][LevelButtonSpriteAtlas.getWidthInSprites()];
        BufferedImage temp = LoadSave.getSpriteAtlas(LevelButtonSpriteAtlas.getFilename());

        for (var j = 0; j < imgs.length; j++) {
            for (var i = 0; i < imgs[j].length; i++) {
                imgs[j][i] = temp.getSubimage(
                        i * LevelButtonSpriteAtlas.getTileWidth(),
                        j * LevelButtonSpriteAtlas.getTileHeight(),
                        LevelButtonSpriteAtlas.getTileWidth(),
                        LevelButtonSpriteAtlas.getTileHeight());
            }
        }
    }

    public void draw(Graphics g, int numberOfMapFragmentsCollected) {
        if (level.isLocked()) {
            g.drawImage(imgs[state][2], xPos - xOffsetCenter, yPos, LevelButtonSpriteAtlas.getTileWidth(Game.SCALE), LevelButtonSpriteAtlas.getTileHeight(Game.SCALE), null);
            if (level.getLevelCost() > 0) {
                g.drawString(numberOfMapFragmentsCollected + " / " + level.getLevelCost(), xPos - xOffsetCenter, yPos);
            }
            padLock.draw(g);
        } else {
            g.drawImage(imgs[state][index], xPos - xOffsetCenter, yPos, LevelButtonSpriteAtlas.getTileWidth(Game.SCALE), LevelButtonSpriteAtlas.getTileHeight(Game.SCALE), null);
            bigText.draw(g);
        }

    }

    public void update() {
        index = 0;
        if (mouseOver) {
            index = 1;
        }
        if (mousePressed)
            index = 2;

        if (unlocking) {
            padLock.update();
            updateAnimationTick();
        }

        if (level.isCompleted())
            state = COMPLETED;

    }

    @Override
    public void afterAnimationFinishedAction() {
        if (unlocking) {
            padLock.fireUnlock(() -> {
                state = UNLOCKED;
                level.unlock();
                unlocking = false;
            });
        }
    }

    public void resetBooleans() {
        mouseOver = false;
        mousePressed = false;
    }


    public void executeAction() {
        if (level.isLocked()) {
            if (Boolean.TRUE.equals(checkUnlockIsDoableAction.get())) {
                unlocking = true;
            } else {
                System.out.println("YOU CAN NOT UNLOCK THIS YET!");
                //TODO fire unable to unlock animation
            }
        } else {
            onClickAction.accept(level);
        }
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
