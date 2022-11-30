package com.hermscoder.entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static com.hermscoder.main.Game.SCALE;

public abstract class Entity {
    protected float x,y;
    protected int width, height;
    protected Rectangle2D.Float hitBox;
    protected int animationTick, animationIndex;
    protected int state;

    protected float airSpeed = 0f;
    protected boolean inAir = false;

    protected int maxHealth = 100;
    protected int currentHealth = maxHealth;

    protected Rectangle2D.Float attackBox;

    protected float walkSpeed;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void drawHitBox(Graphics g, int xLevelOffset){
        //For debugging the hitbox
        g.setColor(Color.PINK);
        g.drawRect((int) hitBox.x - xLevelOffset, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
    }

    protected void drawAttackBox(Graphics g, int xLevelOffset) {
        g.setColor(Color.RED);
        g.drawRect((int) attackBox.x - xLevelOffset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    protected void initHitBox(int width, int height) {
        hitBox = new Rectangle2D.Float(x, y, (int)(width * SCALE), (int)(height * SCALE));
    }

//    protected void updateHitBox() {
//        hitBox.x = (int) x;
//        hitBox.y = (int) y;
//    }


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
