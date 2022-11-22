package com.hermscoder.entities;

import com.hermscoder.main.Game;

import java.awt.geom.Rectangle2D;

import static com.hermscoder.utils.Constants.Directions.LEFT;
import static com.hermscoder.utils.Constants.Directions.RIGHT;
import static com.hermscoder.utils.Constants.EnemyConstants.*;
import static com.hermscoder.utils.HelpMethods.*;


public abstract class Enemy extends Entity{

    private int animationTick, animationIndex, animationSpeed = 25;
    private int enemyState;
    private int enemyType;
    private boolean firstUpdate = true;
    private boolean inAir;
    private float fallSpeed;
    private float gravity = 0.04f * Game.SCALE;
    private float walkSpeed = 0.35f * Game.SCALE;
    private float walkingDirection = LEFT;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitBox(x, y, (int) (20 * Game.SCALE), (int) (27*Game.SCALE) );
    }

    protected void initHitBox(float x, float y, int width, int height) {
        hitBox = new Rectangle2D.Float( x, y, width, height);
    }

    public void update(int[][] levelData) {
        updateMove(levelData);
        updateAnimationTick();
    }

    private void updateMove(int[][] levelData) {
        if(firstUpdate) {
            if (!isEntityOnFloor(hitBox, levelData))
                inAir = true;
            firstUpdate = false;
        }

        if(inAir) {
            if(canMoveHere(hitBox.x, hitBox.y, hitBox.width, hitBox.height, levelData)){
                hitBox.y += fallSpeed;
                fallSpeed += gravity;
            } else {
                inAir = false;
                hitBox.y = getEntityYPosUnderRoofOrAboveFloor(hitBox, fallSpeed);
            }
        } else {
            patrol(levelData);
        }
    }

    private void patrol(int[][] levelData) {
        switch (enemyState) {
            case IDLE:
                enemyState = RUNNING;
            case RUNNING:
                float xSpeed = 0;
                if(walkingDirection == LEFT) {
                    xSpeed = -walkSpeed;
                } else {
                    xSpeed = walkSpeed;
                }
                if(canMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, levelData))
                    if(isFloor(hitBox, xSpeed, levelData)) {
                        hitBox.x += xSpeed;
                        return;
                    }

                changeWalkDir();
                break;
        }
    }

    private void changeWalkDir() {
        if(walkingDirection == LEFT)
            walkingDirection = RIGHT;
        else
            walkingDirection = LEFT;
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
