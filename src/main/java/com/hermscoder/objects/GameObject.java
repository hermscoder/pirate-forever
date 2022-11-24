package com.hermscoder.objects;

import com.hermscoder.utils.Constants;
import com.hermscoder.utils.ObjectConstants;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static com.hermscoder.main.Game.*;
import static com.hermscoder.utils.ObjectConstants.BARREL;
import static com.hermscoder.utils.ObjectConstants.BOX;

public class GameObject {
    protected final int x, y, objectType;
    protected Rectangle2D.Float hitBox;
    protected boolean doAnimation, active = true;
    protected int animationTick, animationIndex;
    protected final int xDrawOffset, yDrawOffset;
    protected final ObjectConstants objectConstants;

    public GameObject(int x, int y, int objectType) {
        this.x = x;
        this.y = y;
        this.objectType = objectType;
        this.objectConstants = Constants.getObjectConstants(objectType);
        this.xDrawOffset = objectConstants.getxDrawOffset();
        this.yDrawOffset = objectConstants.getyDrawOffset();
        initHitBox(objectConstants.getHitboxWidth(), objectConstants.getHitboxHeight());
    }

    protected void updateAnimationTick() {
        animationTick++;
        if (animationTick >= objectConstants.getAnimationSpeed()) {
            animationTick = 0;
            animationIndex++;

            if (animationIndex >= objectConstants.getAnimationSpriteAmount(objectType)) {
                animationIndex = 0;
                if (objectType == BARREL || objectType == BOX) {
                    doAnimation = false;
                    active = false;
                }
            }
        }
    }

    public void reset() {
        animationIndex = 0;
        animationTick = 0;
        active = true;

        doAnimation = objectConstants.isStartAnimated();

    }

    protected void initHitBox(int width, int height) {
        hitBox = new Rectangle2D.Float(x, y, (int) (width * SCALE), (int) (height * SCALE));
        hitBox.y += yDrawOffset + differenceBetweenSpriteAndTileSize();
        hitBox.x += xDrawOffset / 2;
    }

    private int differenceBetweenSpriteAndTileSize() {
        return (int) (SCALE * (TILES_DEFAULT_SIZE - objectConstants.getSpriteAtlas().getTileHeight()));
    }

    public void drawHitBox(Graphics g, int xLevelOffset) {
        //For debugging the hitbox
        g.setColor(Color.PINK);
        g.drawRect((int) hitBox.x - xLevelOffset, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
    }

    public int getObjectType() {
        return objectType;
    }

    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getxDrawOffset() {
        return xDrawOffset;
    }

    public int getyDrawOffset() {
        return yDrawOffset;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public ObjectConstants getObjectConstants() {
        return objectConstants;
    }

    public void setDoAnimation(boolean doAnimation) {
        this.doAnimation = doAnimation;
    }
}
