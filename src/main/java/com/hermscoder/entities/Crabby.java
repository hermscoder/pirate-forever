package com.hermscoder.entities;

import com.hermscoder.utils.Constants;

import static com.hermscoder.main.Game.SCALE;
import static com.hermscoder.utils.Constants.EnemyConstants.*;
import static com.hermscoder.utils.Sprite.CrabbySpriteAtlas;

public class Crabby extends Enemy {
    public Crabby(float x, float y) {
        super(x, y,
                CrabbySpriteAtlas.getTileWidth(SCALE),
                CrabbySpriteAtlas.getTileHeight(SCALE),
                Constants.EnemyConstants.CRABBY);
        initHitBox(x, y, (int) (22 * SCALE), (int) (19 * SCALE));
    }


    public void update(int[][] levelData, Player player) {
        updateMove(levelData, player);
        updateAnimationTick();
    }

    private void updateMove(int[][] levelData, Player player) {
        if (firstUpdate) {
            firstUpdateCheck(levelData);
        }

        if (inAir) {
            updateInAir(levelData);
        } else {
            switch (enemyState) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if (canSeePlayer(levelData, player))
                        turnTowardsPlayer(player);
                    if (isPlayerCloseForAttack(player))
                        newState(ATTACK);

                    move(levelData);
                    break;
            }
        }
    }
}
