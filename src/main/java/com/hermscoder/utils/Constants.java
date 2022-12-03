package com.hermscoder.utils;

import com.hermscoder.main.Game;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static final String LEVELS_FOLDER = "/levels";
    public static final String LEVEL_DESIGN_FOLDER = "/level_design";
    public static final String AUDIO_FOLDER = "/audio";

    public static final float GRAVITY = 0.04f * Game.SCALE;

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class CrabbyConstants {
        public static final int CRABBY = 1;

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
                .animation(PlayerConstants.DEAD, 8)
                .animation(PlayerConstants.RUNNING, 6)
                .animation(PlayerConstants.IDLE, 5)
                .animation(PlayerConstants.HIT, 4)
                .animation(PlayerConstants.JUMP, 3)
                .animation(PlayerConstants.ATTACK_1, 3)
                .animation(PlayerConstants.ATTACK_JUMP_1, 3)
                .animation(PlayerConstants.ATTACK_JUMP_2, 3)
                .animation(PlayerConstants.GROUND, 2)
                .animation(PlayerConstants.FALLING, 1)
                .maxHealth(100)
                .damage(10)
                .gravity(GRAVITY)
                .animationSpeed(25)
                .walkSpeed(1f * Game.SCALE)
                .build()
        );
        put(CrabbyConstants.CRABBY, EntityConstants.newBuilder()
                .animation(CrabbyConstants.IDLE, 9)
                .animation(CrabbyConstants.RUNNING, 6)
                .animation(CrabbyConstants.ATTACK, 7)
                .animation(CrabbyConstants.HIT, 4)
                .animation(CrabbyConstants.DEAD, 5)
                .maxHealth(10)
                .damage(10)
                .gravity(GRAVITY)
                .animationSpeed(25)
                .walkSpeed(0.35f * Game.SCALE)
                .build()
        );
    }};

    private static final Map<Integer, ObjectConstants> objectConstants = new HashMap<>() {{
        put(ObjectConstants.RED_POTION, ObjectConstants.newBuilder()
                .spriteAtlas(Sprite.PotionSpriteAtlas)
                .animationSprite(ObjectConstants.RED_POTION_ANIMATION, 7)
                .value(40)
                .gravity(GRAVITY)
                .animationSpeed(25)
                .startAnimated(true)
                .hitboxWidth(7)
                .hitboxHeight(14)
                .xDrawOffset((int) (3 * Game.SCALE))
                .yDrawOffset((int) (2 * Game.SCALE))
                .build()
        );
        put(ObjectConstants.BLUE_POTION, ObjectConstants.newBuilder()
                .spriteAtlas(Sprite.PotionSpriteAtlas)
                .animationSprite(ObjectConstants.BLUE_POTION_ANIMATION, 7)
                .power(40)
                .gravity(GRAVITY)
                .animationSpeed(25)
                .startAnimated(true)
                .hitboxWidth(7)
                .hitboxHeight(14)
                .xDrawOffset((int) (3 * Game.SCALE))
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
                .xDrawOffset((int) (8 * Game.SCALE))
                .yDrawOffset((int) (5 * Game.SCALE))
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
        put(ObjectConstants.CANNON_LEFT, ObjectConstants.newBuilder()
                .spriteAtlas(Sprite.CannonSpriteAtlas)
                .animationSprite(ObjectConstants.CANNON_LEFT, 7)
                .gravity(GRAVITY)
                .value(50)
                .animationSpeed(25)
                .hitboxWidth(40)
                .hitboxHeight(26)
                .xDrawOffset(-4)
                .yDrawOffset((int) (1 * Game.SCALE))
                .build()
        );
        put(ObjectConstants.CANNON_RIGHT, ObjectConstants.newBuilder()
                .spriteAtlas(Sprite.CannonSpriteAtlas)
                .animationSprite(ObjectConstants.CANNON_RIGHT, 7)
                .gravity(GRAVITY)
                .value(50)
                .animationSpeed(25)
                .startAnimated(false)
                .hitboxWidth(40)
                .hitboxHeight(26)
                .xDrawOffset(-4)
                .yDrawOffset((int) (1 * Game.SCALE))
                .build()
        );

        put(ObjectConstants.CANNON_BALL, ObjectConstants.newBuilder()
                .spriteAtlas(Sprite.CannonBallSprite)
                .animationSprite(ObjectConstants.CANNON_BALL, 1)
                .gravity(GRAVITY)
                .value(-100)
                .hitboxWidth(15)
                .hitboxHeight(15)
                .yDrawOffset((int) (5 * Game.SCALE))
                .build()
        );
    }};

    public static EntityConstants getEntityConstants(int entityType) {
        EntityConstants entityConstants = entitiesConstants
                .get(entityType);
        if (entityConstants == null)
            throw new RuntimeException("No properties found for entity type " + entityType);

        return entityConstants;
    }

    public static ObjectConstants getObjectConstants(int objectType) {
        ObjectConstants objConstants = objectConstants
                .get(objectType);
        if (objConstants == null)
            throw new RuntimeException("No properties found for object type " + objectType);

        return objConstants;
    }
}
