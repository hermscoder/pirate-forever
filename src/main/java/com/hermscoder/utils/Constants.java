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

        public static final Map<Integer, Map<Integer, Integer>> enemiesSpritesAmount = new HashMap<>() {{
            put(CRABBY, new HashMap<>() {{
                put(IDLE, 9);
                put(RUNNING, 6);
                put(ATTACK, 7);
                put(HIT, 4);
                put(DEAD, 5);
            }});

        }};


        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        public static final int CRABBY_DRAWOFFSET_X = (int) (26 * Game.SCALE);
        public static final int CRABBY_DRAWOFFSET_Y = (int) (9 * Game.SCALE);

        public static int getSpriteAmount(int enemyType, int playerAction) {
            return enemiesSpritesAmount
                    .getOrDefault(enemyType, Collections.emptyMap())
                    .getOrDefault(playerAction, 0);
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

        public static int getSpriteAmount(int playerAction) {
            switch (playerAction) {
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
