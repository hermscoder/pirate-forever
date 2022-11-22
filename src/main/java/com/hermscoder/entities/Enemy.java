package com.hermscoder.entities;

import com.hermscoder.main.Game;
import com.hermscoder.utils.Constants;
import com.hermscoder.utils.Constants.EnemyConstants;

import java.awt.geom.Rectangle2D;

import static com.hermscoder.utils.Constants.EnemyConstants.*;


public abstract class Enemy extends Entity{

    private int animationTick, animationIndex, animationSpeed = 25;
    private int enemyState;
    private int enemyType;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitBox(x, y, (int) (20 * Game.SCALE), (int) (27*Game.SCALE) );
    }

    protected void initHitBox(float x, float y, int width, int height) {
        hitBox = new Rectangle2D.Float( x, y, width, height);
    }

    public void update() {
        updateAnimationTick();
    }

    public void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= getSpriteAmount(enemyType, enemyState)) {
                animationIndex = 0;
            }
        }
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }
}
