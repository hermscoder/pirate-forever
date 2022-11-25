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

    private static final Map<Integer, ObjectConstants> objectConstants = new HashMap<>() {{
        put(ObjectConstants.RED_POTION, ObjectConstants.newBuilder()
                .spriteAtlas(Sprite.PotionSpriteAtlas)
                .animationSprite(ObjectConstants.RED_POTION, 7)
                .value(10)
                .gravity(GRAVITY)
                .animationSpeed(25)
                .startAnimated(true)
                .hitboxWidth(7)
                .hitboxHeight(14)
                .xDrawOffset((int) (3* Game.SCALE))
                .yDrawOffset((int) (2* Game.SCALE))
                .build()
        );
        put(ObjectConstants.BLUE_POTION, ObjectConstants.newBuilder()
                .spriteAtlas(Sprite.PotionSpriteAtlas)
                .animationSprite(ObjectConstants.BLUE_POTION, 7)
                .power(10)
                .gravity(GRAVITY)
                .animationSpeed(25)
                .startAnimated(true)
                .hitboxWidth(7)
                .hitboxHeight(14)
                .xDrawOffset((int) (3* Game.SCALE))
                .yDrawOffset((int) (2 * Game.SCALE))
                .build()
        );
        put(ObjectConstants.BOX, ObjectConstants.newBuilder()
                .spriteAtlas(Sprite.ContainersSpriteAtlas)
                .animationSprite(ObjectConstants.BOX, 7)
                .value(0)
                .gravity(GRAVITY)
                .animationSpeed(25)
                .startAnimated(false)
                .hitboxWidth(25)
                .hitboxHeight(18)
                .xDrawOffset((int) (7 * Game.SCALE))
                .yDrawOffset((int) (12 * Game.SCALE))
                .build()
        );
        put(ObjectConstants.BARREL, ObjectConstants.newBuilder()
                .spriteAtlas(Sprite.ContainersSpriteAtlas)
                .animationSprite(ObjectConstants.BARREL, 7)
                .value(0)
                .gravity(GRAVITY)
                .animationSpeed(25)
                .startAnimated(false)
                .hitboxWidth(23)
                .hitboxHeight(25)
                .xDrawOffset((int) (8* Game.SCALE))
                .yDrawOffset((int) (5* Game.SCALE))
                .build()
        );
        put(ObjectConstants.SPIKE_TRAP, ObjectConstants.newBuilder()
                .spriteAtlas(Sprite.SpikeTrapSpriteAtlas)
                .animationSprite(ObjectConstants.SPIKE_TRAP, 1)
                .value(-100)
                .gravity(GRAVITY)
                .animationSpeed(25)
                .startAnimated(false)
                .hitboxWidth(32)
                .hitboxHeight(16)
                .xDrawOffset(0)
                .yDrawOffset((int) (16 * Game.SCALE))
                .build()
        );
    }};

    public static EntityConstants getEntityConstants(int enemyType) {
        EntityConstants entityConstants = entitiesConstants
                .get(enemyType);
        if(entityConstants == null)
            throw new RuntimeException("No properties found for entity type " + enemyType);

        return entityConstants;
    }

    public static ObjectConstants getObjectConstants(int objectType) {
        ObjectConstants objConstants = objectConstants
                .get(objectType);
        if(objConstants == null)
            throw new RuntimeException("No properties found for object type " + objectType);

        return objConstants;
    }
}
