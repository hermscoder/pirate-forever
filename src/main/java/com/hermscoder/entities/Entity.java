package com.hermscoder.entities;

import com.hermscoder.utils.Constants;
import com.hermscoder.utils.EntityConstants;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    protected final EntityConstants entityConstants;

    protected float x, y;
    protected int width, height;
    protected int entityType;
    protected Rectangle2D.Float hitBox;
    protected int animationTick, animationIndex;
    protected int state;

    protected float airSpeed = 0f;
    protected boolean inAir = false;

    protected int maxHealth;
    protected int currentHealth;

    protected Rectangle2D.Float attackBox;

    protected float walkSpeed;

    public Entity(float x, float y, int width, int height, int entityType) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.entityType = entityType;
        this.entityConstants = Constants.getEntityConstants(entityType);
        this.maxHealth = entityConstants.getMaxHealth();
        this.currentHealth = maxHealth;
        this.walkSpeed = entityConstants.getWalkSpeed();
    }

    public abstract void afterAnimationFinishedAction(int state);

    protected void drawHitBox(Graphics g, int xLevelOffset) {
        //For debugging the hitbox
        g.setColor(Color.PINK);
        g.drawRect((int) hitBox.x - xLevelOffset, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
    }

    protected void drawAttackBox(Graphics g, int xLevelOffset) {
        g.setColor(Color.BLUE);
        g.drawRect((int) attackBox.x - xLevelOffset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    protected void initHitBox(int width, int height) {
        hitBox = new Rectangle2D.Float(x, y, width, height);
    }

    protected void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, entityConstants.getAttackBoxWidth(), entityConstants.getAttackBoxHeight());
    }

    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    public int getState() {
        return state;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }
}
