package com.hermscoder.entities;

import com.hermscoder.utils.Constants;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static com.hermscoder.main.Game.SCALE;
import static com.hermscoder.utils.Constants.Directions.RIGHT;
import static com.hermscoder.utils.Constants.CrabbyConstants.*;
import static com.hermscoder.utils.Sprite.CrabbySpriteAtlas;

public class Crabby extends Enemy {
    private int attackBoxOffsetX;


    public Crabby(float x, float y) {
        super(x, y,
                CrabbySpriteAtlas.getTileWidth(SCALE),
                CrabbySpriteAtlas.getTileHeight(SCALE),
                Constants.CrabbyConstants.CRABBY);
        initHitBox(22, 19);
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (82 * SCALE), (int) (19 * SCALE));
        attackBoxOffsetX = (int) (30 * SCALE);
    }


    public void update(int[][] levelData, Player player) {
        updateBehavior(levelData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        attackBox.x = hitBox.x - attackBoxOffsetX;
        attackBox.y = hitBox.y;
    }

    public int flipX() {
        if (walkingDirection == RIGHT)
            return width;
        else
            return 0;
    }

    public int flipW() {
        if(walkingDirection == RIGHT)
            return -1;
        else
            return 1;
    }

    private void updateBehavior(int[][] levelData, Player player) {
        if (firstUpdate) {
            firstUpdateCheck(levelData);
        }

        if (inAir) {
            updateInAir(levelData);
        } else {
            switch (state) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if (canSeePlayer(levelData, player)) {
                        turnTowardsPlayer(player);
                        if (isPlayerCloseForAttack(player))
                            newState(ATTACK);
                    }

                    move(levelData);
                    break;
                case ATTACK:
                    if(animationIndex == 0)
                        attackChecked = false;
                    //checking if we are hurting the player when we are in the attack moment of the animation
                    if(animationIndex == 3 && !attackChecked)
                        checkEnemyHit(attackBox, player);
                    break;
                case HIT:
                    break;
            }
        }
    }

}
