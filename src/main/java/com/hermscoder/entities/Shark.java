package com.hermscoder.entities;


import com.hermscoder.main.Game;

import java.awt.*;

import static com.hermscoder.main.Game.SCALE;
import static com.hermscoder.utils.Constants.Directions.LEFT;
import static com.hermscoder.utils.Constants.Directions.RIGHT;
import static com.hermscoder.utils.Constants.SharkConstants.*;
import static com.hermscoder.utils.HelpMethods.*;
import static com.hermscoder.utils.Sprite.SharkSpriteAtlas;

public class Shark extends Enemy {

    //Jumping / Gravity
    private float jumpSpeed = -1f * SCALE;

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

    @Override
    void draw(Graphics g, int xLvlOffset) {
        g.drawImage(entityConstants.getAnimationImage(state, animationIndex),
                (int) (hitBox.x - xLvlOffset - xDrawOffset + flipX()),
                (int) (hitBox.y - yDrawOffset),
                entityConstants.getSpriteAtlas().getTileWidth(Game.SCALE) * flipW(),
                entityConstants.getSpriteAtlas().getTileHeight(Game.SCALE), null);
//        drawHitBox(g, xLevelOffset);
//        drawAttackBox(g, xLevelOffset);
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
            case ATTACK_ANTICIPATION:
                jump();
                newState(ATTACK);
                break;
            case ATTACK:
                attackChecked = false;
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
            move(levelData);
        } else {
            switch (state) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if (canSeePlayer(levelData, player)) {
                        turnTowardsPlayer(player);
                        if (isPlayerCloseForAttack(player))
                            newState(ATTACK_ANTICIPATION);
                    }
                    move(levelData);
                    break;
                case ATTACK:
                    //checking if we are hurting the player when we are in the attack moment of the animation
                    if (animationIndex < 3 && !attackChecked)
                        checkEnemyHit(attackBox, player);
                    break;
                case HIT:
                    break;
            }
        }
    }

    private void jump() {
        if (inAir)
            return;
//        playing.getGame().getAudioPlayer().playEffect(SoundEffect.JUMP);
        inAir = true;
        airSpeed = jumpSpeed;
    }

}
