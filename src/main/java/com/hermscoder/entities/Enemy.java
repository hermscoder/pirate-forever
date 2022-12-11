package com.hermscoder.entities;

import com.hermscoder.main.Game;

import java.awt.geom.Rectangle2D;

import static com.hermscoder.utils.Constants.Directions.LEFT;
import static com.hermscoder.utils.Constants.Directions.RIGHT;
import static com.hermscoder.utils.HelpMethods.*;


public abstract class Enemy extends Entity {

    protected boolean firstUpdate = true;
    protected float walkingDirection = LEFT;
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE;

    private boolean active = true;
    protected boolean attackChecked;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height, enemyType);
    }

    public abstract void hurt(int amount);

    protected void firstUpdateCheck(int[][] levelData) {
        if (!isEntityOnFloor(hitBox, levelData))
            inAir = true;
        firstUpdate = false;
    }

    protected void updateInAir(int[][] levelData) {
        if (canMoveHere(hitBox.x, hitBox.y, hitBox.width, hitBox.height, levelData)) {
            hitBox.y += airSpeed;
            airSpeed += entityConstants.getGravity();
        } else {
            inAir = false;
            hitBox.y = getEntityYPosUnderRoofOrAboveFloor(hitBox, airSpeed);
            tileY = (int) hitBox.y / Game.TILES_SIZE;
        }
    }

    protected void move(int[][] levelData) {
        float xSpeed = 0;
        if (walkingDirection == LEFT) {
            xSpeed = -walkSpeed;
        } else {
            xSpeed = walkSpeed;
        }
        if (canMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, levelData) && (isFloor(hitBox, xSpeed, levelData) || inAir)) {
            hitBox.x += xSpeed;
        } else {
            if(!inAir)
                changeWalkDir();
        }
    }

    protected void turnTowardsPlayer(Player player) {
        if (player.hitBox.x > hitBox.x) {
            walkingDirection = RIGHT;
        } else {
            walkingDirection = LEFT;
        }
    }

    protected boolean canSeePlayer(int[][] levelData, Player player) {
        int playerTileY = (int) (player.getHitBox().y / Game.TILES_SIZE);
        if (playerTileY == tileY) {
            if (isPlayerInRange(player)) {
                if (isSightClear(levelData, hitBox, player.hitBox, tileY)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);

        return absValue <= attackDistance * entityConstants.getViewRangeInTiles();
    }

    protected boolean isPlayerCloseForAttack(Player player) {
        int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);

        return absValue <= attackDistance;
    }

    protected void changeWalkDir() {
        if (walkingDirection == LEFT)
            walkingDirection = RIGHT;
        else
            walkingDirection = LEFT;
    }

    protected void newState(int enemyState) {
        this.state = enemyState;
        animationTick = 0;
        animationIndex = 0;
    }


    protected void checkEnemyHit(Rectangle2D.Float attackBox, Player player) {
        if (attackBox.intersects(player.hitBox)) {
            player.hit(hitBox, entityConstants.getDamage());
            attackChecked = true;
        }

    }

    protected void updateAnimationTick() {
        animationTick++;
        if (animationTick >= entityConstants.getAnimationSpeed()) {
            animationTick = 0;
            animationIndex++;

            if (animationIndex >= entityConstants.getSpriteAmount(state)) {
                animationIndex = 0;
                afterAnimationFinishedAction(state);
            }
        }
    }

    public boolean isActive() {
        return active;
    }

    protected void setActive(boolean active) {
        this.active = active;
    }

    public void resetEnemy() {
        hitBox.x = x;
        hitBox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(0);// IDLE
        active = true;
        airSpeed = 0;
    }
}
