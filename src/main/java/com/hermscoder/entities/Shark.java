package com.hermscoder.entities;


import static com.hermscoder.main.Game.SCALE;
import static com.hermscoder.utils.Constants.Directions.LEFT;
import static com.hermscoder.utils.Constants.Directions.RIGHT;
import static com.hermscoder.utils.Constants.SharkConstants.*;
import static com.hermscoder.utils.Sprite.SharkSpriteAtlas;

public class Shark extends Enemy {


    public Shark(float x, float y) {
        super(x, y,
                SharkSpriteAtlas.getTileWidth(SCALE),
                SharkSpriteAtlas.getTileHeight(SCALE),
                SHARK);
        initHitBox(entityConstants.getHitBoxWidth(), entityConstants.getHitBoxHeight());
        initAttackBox();
    }


    public void update(int[][] levelData, Player player) {
        updateBehavior(levelData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        if (walkingDirection == RIGHT) {
            attackBox.x = hitBox.x + SHARK_DRAWOFFSET_X;
        } else if (walkingDirection == LEFT) {
            attackBox.x = hitBox.x - SHARK_DRAWOFFSET_X;
        }

        attackBox.y = hitBox.y + (0 * SCALE);
    }

    @Override
    public void hurt(int amount) {
        currentHealth -= amount;
        if (currentHealth <= 0) {
            newState(DEAD);
        } else
            newState(HIT);
    }

    @Override
    public void afterAnimationFinishedAction(int state) {
        switch (state) {
            case ATTACK:
            case HIT:
                newState(IDLE);
                break;
            case DEAD:
                setActive(false);
                break;
        }
    }

    public int flipX() {
        if (walkingDirection == RIGHT)
            return width;
        else
            return 0;
    }

    public int flipW() {
        if (walkingDirection == RIGHT)
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
                    if (animationIndex == 0)
                        attackChecked = false;
                    //checking if we are hurting the player when we are in the attack moment of the animation
                    if (animationIndex == 3 && !attackChecked)
                        checkEnemyHit(attackBox, player);
                    break;
                case HIT:
                    break;
            }
        }
    }

}
