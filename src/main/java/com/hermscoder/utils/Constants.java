package com.hermscoder.utils;

import com.hermscoder.main.Game;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static final float GRAVITY = 0.04f * Game.SCALE;

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class CrabbyConstants {
        public static final int CRABBY = 0;

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        public static final int CRABBY_DRAWOFFSET_X = (int) (26 * Game.SCALE);
        public static final int CRABBY_DRAWOFFSET_Y = (int) (9 * Game.SCALE);

    }

    public static class PlayerConstants {
        public static final int PLAYER = 100;

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int FALLING = 3;
        public static final int GROUND = 4;
        public static final int HIT = 5;
        public static final int ATTACK_1 = 6;
        public static final int ATTACK_JUMP_1 = 7;
        public static final int ATTACK_JUMP_2 = 8;
        public static final int DEAD = 9;
    }

    private static final Map<Integer, EntityConstants> entitiesConstants = new HashMap<>() {{
        put(PlayerConstants.PLAYER, EntityConstants.newBuilder()
                .sprite(PlayerConstants.DEAD, 8)
                .sprite(PlayerConstants.RUNNING, 6)
                .sprite(PlayerConstants.IDLE, 5)
                .sprite(PlayerConstants.HIT, 4)
                .sprite(PlayerConstants.JUMP, 3)
                .sprite(PlayerConstants.ATTACK_1, 3)
                .sprite(PlayerConstants.ATTACK_JUMP_1, 3)
                .sprite(PlayerConstants.ATTACK_JUMP_2, 3)
                .sprite(PlayerConstants.GROUND, 2)
                .sprite(PlayerConstants.FALLING, 1)
                .maxHealth(100)
                .damage(10)
                .gravity(GRAVITY)
                .animationSpeed(25)
                .build()
        );
        put(CrabbyConstants.CRABBY, EntityConstants.newBuilder()
                .sprite(CrabbyConstants.IDLE, 9)
                .sprite(CrabbyConstants.RUNNING, 6)
                .sprite(CrabbyConstants.ATTACK, 7)
                .sprite(CrabbyConstants.HIT, 4)
                .sprite(CrabbyConstants.DEAD, 5)
                .maxHealth(10)
                .damage(10)
                .gravity(GRAVITY)
                .animationSpeed(25)
                .build()
        );
    }};

    public static EntityConstants getEntityConstants(int enemyType) {
        EntityConstants entityConstants = entitiesConstants
                .get(enemyType);
        if(entityConstants == null)
            throw new RuntimeException("No properties found for enemyType " + enemyType);

        return entityConstants;
    }

    public static int getSpriteAmount(int enemyType, int playerAction) {
        return getEntityConstants(enemyType).getSpriteAmount(playerAction);
    }

    public static int getMaxHealth(int enemyType) {
        return getEntityConstants(enemyType).getMaxHealth();
    }

    public static int getEnemyDamage(int enemyType) {
        return getEntityConstants(enemyType).getDamage();
    }

}
