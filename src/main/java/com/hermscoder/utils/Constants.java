package com.hermscoder.utils;

import com.hermscoder.main.Game;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class EnemyConstants {
        public static final int CRABBY = 0;

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        public static final int CRABBY_DRAWOFFSET_X = (int) (26 * Game.SCALE);
        public static final int CRABBY_DRAWOFFSET_Y = (int) (9 * Game.SCALE);

        private static final Map<Integer, EnemyProperties> enemiesSpritesAmount = new HashMap<>() {{
            put(CRABBY, EnemyProperties.newBuilder()
                            .sprite(IDLE, 9)
                            .sprite(RUNNING, 6)
                            .sprite(ATTACK, 7)
                            .sprite(HIT, 4)
                            .sprite(DEAD, 5)
                            .maxHealth(10)
                            .damage(10).build()
            );
        }};

        public static EnemyProperties getEnemyProperties(int enemyType) {
            EnemyProperties enemyProperties = enemiesSpritesAmount
                    .get(enemyType);
            if(enemyProperties == null)
                throw new RuntimeException("No properties found for enemyType " + enemyType);

            return enemyProperties;
        }

        public static int getSpriteAmount(int enemyType, int playerAction) {
            return getEnemyProperties(enemyType).getSpriteAmount(playerAction);
        }

        public static int getMaxHealth(int enemyType) {
            return getEnemyProperties(enemyType).getMaxHealth();
        }

        public static int getEnemyDamage(int enemyType) {
            return getEnemyProperties(enemyType).getDamage();
        }
    }

    public static class PlayerConstants {
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

        public static int getSpriteAmount(int playerAction) {
            switch (playerAction) {
                case DEAD:
                    return 8;
                case RUNNING:
                    return 6;
                case IDLE:
                    return 5;
                case HIT:
                    return 4;
                case JUMP:
                case ATTACK_1:
                case ATTACK_JUMP_1:
                case ATTACK_JUMP_2:
                    return 3;
                case GROUND:
                    return 2;
                case FALLING:
                default:
                    return 1;
            }
        }
    }

}
