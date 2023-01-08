package com.hermscoder.entities;

import com.hermscoder.main.Game;
import com.hermscoder.utils.Constants;

import java.awt.*;

import static com.hermscoder.main.Game.SCALE;
import static com.hermscoder.utils.Constants.CrabbyConstants.*;
import static com.hermscoder.utils.Constants.Directions.RIGHT;
import static com.hermscoder.utils.ObjectConstants.BLUE_POTION;
import static com.hermscoder.utils.Sprite.CrabbySpriteAtlas;

public class Crabby extends Enemy {
    private int attackBoxOffsetX;


    public Crabby(float x, float y) {
        super(x, y,
                CrabbySpriteAtlas.getTileWidth(SCALE),
                CrabbySpriteAtlas.getTileHeight(SCALE),
                Constants.CrabbyConstants.CRABBY);
        initHitBox(entityConstants.getHitBoxWidth(), entityConstants.getHitBoxHeight());
        initAttackBox();
        attackBoxOffsetX = (int) (30 * SCALE);
    }

    @Override
    void update(int[][] levelData, Player player) {
        updateBehavior(levelData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    @Override
    void draw(Graphics g, int xLvlOffset) {
        g.drawImage(entityConstants.getAnimationImage(state, animationIndex),
                (int) (hitBox.x - xLvlOffset - xDrawOffset + flipW()),
                (int) (hitBox.y - yDrawOffset),
                entityConstants.getSpriteAtlas().getTileWidth(Game.SCALE),
                entityConstants.getSpriteAtlas().getTileHeight(Game.SCALE), null);
//        drawHitBox(g, xLevelOffset);
//        drawAttackBox(g, xLevelOffset);
    }

    private void updateAttackBox() {
        attackBox.x = hitBox.x - attackBoxOffsetX;
        attackBox.y = hitBox.y;
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
