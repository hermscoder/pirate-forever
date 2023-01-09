package com.hermscoder.utils;

import java.util.HashMap;
import java.util.Map;

import static com.hermscoder.main.Game.SCALE;

public class Constants {

    public static final String LEVELS_FOLDER = "levels";
    public static final String LEVEL_DESIGN_FOLDER = "/level_design";
    public static final String AUDIO_FOLDER = "/audio";

    public static final float GRAVITY = 0.04f * SCALE;
    public static final int LAST_SOLID_TILE_ID = 49;

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }


    public static class SharkConstants {
        public static final int SHARK = 2;

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK_ANTICIPATION = 5;
        public static final int ATTACK = 6;
        public static final int HIT = 7;
        public static final int DEAD = 8;

        public static final int SHARK_DRAWOFFSET_X = (int) (5 * SCALE);
        public static final int SHARK_DRAWOFFSET_Y = (int) (5 * SCALE);

    }

    public static class CrabbyConstants {
        public static final int CRABBY = 1;

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;
    }

    public static class PlayerConstants {
        public static final int FACING_LEFT = -1;
        public static final int FACING_RIGHT = 1;

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
                .animationSprite(PlayerConstants.DEAD, 8)
                .animationSprite(PlayerConstants.RUNNING, 6)
                .animationSprite(PlayerConstants.IDLE, 5)
                .animationSprite(PlayerConstants.HIT, 4)
                .animationSprite(PlayerConstants.JUMP, 3)
                .animationSprite(PlayerConstants.ATTACK_1, 3)
                .animationSprite(PlayerConstants.ATTACK_JUMP_1, 3)
                .animationSprite(PlayerConstants.ATTACK_JUMP_2, 3)
                .animationSprite(PlayerConstants.GROUND, 2)
                .animationSprite(PlayerConstants.FALLING, 1)
                .maxHealth(100)
                .damage(10)
                .gravity(GRAVITY)
                .animationSpeed(25)
                .walkSpeed(1f * SCALE)
                .hitBox((int) (20 * SCALE), (int) (27 * SCALE))
                .attackBox((int) (30 * SCALE), (int) (20 * SCALE))
                .build()
        );
        put(CrabbyConstants.CRABBY, EntityConstants.newBuilder()
                .spriteAtlas(Sprite.CrabbySpriteAtlas)
                .animationSprite(CrabbyConstants.IDLE, 9)
                .animationSprite(CrabbyConstants.RUNNING, 6)
                .animationSprite(CrabbyConstants.ATTACK, 7)
                .animationSprite(CrabbyConstants.HIT, 4)
                .animationSprite(CrabbyConstants.DEAD, 5)
                .maxHealth(10)
                .damage(10)
                .viewRangeInTiles(5)
                .gravity(GRAVITY)
                .animationSpeed(25)
                .walkSpeed(0.35f * SCALE)
                .hitBox((int) (22 * SCALE), (int) (19 * SCALE))
                .attackBox((int) (82 * SCALE), (int) (19 * SCALE))
                .xDrawOffset((int) (26 * SCALE))
                .yDrawOffset((int) (9 * SCALE))
                .build()
        );
        put(SharkConstants.SHARK, EntityConstants.newBuilder()
                .spriteAtlas(Sprite.SharkSpriteAtlas)
                .animationSprite(SharkConstants.IDLE, 8)
                .animationSprite(SharkConstants.RUNNING, 6)
                .animationSprite(SharkConstants.ATTACK_ANTICIPATION, 3)
                .animationSprite(SharkConstants.ATTACK, 5)
                .animationSprite(SharkConstants.HIT, 4)
                .animationSprite(SharkConstants.DEAD, 4)
                .maxHealth(10)
                .damage(10)
                .viewRangeInTiles(5)
                .gravity(GRAVITY)
                .animationSpeed(25)
                .walkSpeed(0.35f * SCALE)
                .hitBox((int) (22 * SCALE), (int) (21 * SCALE))
                .attackBox((int) (25 * SCALE), (int) (15 * SCALE))
                .xDrawOffset((int) (5 * SCALE))
                .yDrawOffset((int) (5 * SCALE))
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
                .xDrawOffset((int) (3 * SCALE))
                .yDrawOffset((int) (2 * SCALE))
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
                .xDrawOffset((int) (3 * SCALE))
                .yDrawOffset((int) (2 * SCALE))
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
                .xDrawOffset((int) (7 * SCALE))
                .yDrawOffset((int) (12 * SCALE))
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
                .xDrawOffset((int) (8 * SCALE))
                .yDrawOffset((int) (5 * SCALE))
                .build()
        );
        put(ObjectConstants.SPIKE_TRAP, ObjectConstants.newBuilder()
                .spriteAtlas(Sprite.SpikeTrapSpriteAtlas)
                .animationSprite(ObjectConstants.SPIKE_TRAP, 1)
                .damage(100)
                .gravity(GRAVITY)
                .animationSpeed(25)
                .startAnimated(false)
                .hitboxWidth(32)
                .hitboxHeight(16)
                .xDrawOffset(0)
                .yDrawOffset((int) (16 * SCALE))
                .build()
        );
        put(ObjectConstants.CANNON_LEFT, ObjectConstants.newBuilder()
                .spriteAtlas(Sprite.CannonSpriteAtlas)
                .animationSprite(ObjectConstants.CANNON_LEFT, 7)
                .gravity(GRAVITY)
                .damage(50)
                .animationSpeed(25)
                .hitboxWidth(40)
                .hitboxHeight(26)
                .xDrawOffset((int) (-4 * SCALE))
                .yDrawOffset((int) (1 * SCALE))
                .build()
        );
        put(ObjectConstants.CANNON_RIGHT, ObjectConstants.newBuilder()
                .spriteAtlas(Sprite.CannonSpriteAtlas)
                .animationSprite(ObjectConstants.CANNON_RIGHT, 7)
                .gravity(GRAVITY)
                .damage(50)
                .animationSpeed(25)
                .startAnimated(false)
                .hitboxWidth(40)
                .hitboxHeight(26)
                .xDrawOffset((int) (-4 * SCALE))
                .yDrawOffset((int) (1 * SCALE))
                .build()
        );

        put(ObjectConstants.CANNON_BALL, ObjectConstants.newBuilder()
                .spriteAtlas(Sprite.CannonBallSprite)
                .animationSprite(ObjectConstants.CANNON_BALL, 1)
                .gravity(GRAVITY)
                .hitboxWidth(15)
                .hitboxHeight(15)
                .yDrawOffset((int) (5 * SCALE))
                .build()
        );
        put(ObjectConstants.BARE_HANDS, ObjectConstants.newBuilder()
                .spriteAtlas(Sprite.BareHandsSpriteAtlas)
                .animationSprite(ObjectConstants.BARE_HANDS, 8)
                .gravity(GRAVITY)
                .damage(1)
                .animationSpeed(25)
                .startAnimated(true)
                .xDrawOffset((int) (23 * SCALE))
                .yDrawOffset((int) (15 * SCALE))
                .hitboxWidth((int) (20 * SCALE))
                .hitboxHeight((int) (10 * SCALE))
                .attackBox((int) (30 * SCALE), (int) (20 * SCALE))
                .build()
        );
        put(ObjectConstants.SWORD, ObjectConstants.newBuilder()
                .spriteAtlas(Sprite.SwordSpriteAtlas)
                .animationSprite(ObjectConstants.SWORD, 8)
                .gravity(GRAVITY)
                .damage(10)
                .animationSpeed(25)
                .startAnimated(true)
                .xDrawOffset((int) (23 * SCALE))
                .yDrawOffset((int) (15 * SCALE))
                .hitboxWidth((int) (20 * SCALE))
                .hitboxHeight((int) (10 * SCALE))
                .attackBox((int) (30 * SCALE), (int) (20 * SCALE))
                .build()
        );
        put(ObjectConstants.FIRE_SWORD, ObjectConstants.newBuilder()
                .spriteAtlas(Sprite.FireSwordSpriteAtlas)
                .animationSprite(ObjectConstants.FIRE_SWORD, 8)
                .gravity(GRAVITY)
                .damage(10)
                .animationSpeed(25)
                .startAnimated(true)
                .xDrawOffset((int) (23 * SCALE))
                .yDrawOffset((int) (15 * SCALE))
                .hitboxWidth((int) (20 * SCALE))
                .hitboxHeight((int) (10 * SCALE))
                .attackBox((int) (30 * SCALE), (int) (20 * SCALE))
                .build()
        );
        put(ObjectConstants.KEY, ObjectConstants.newBuilder()
                .spriteAtlas(Sprite.KeySpriteAtlas)
                .animationSprite(ObjectConstants.KEY, 8)
                .gravity(GRAVITY)
                .animationSpeed(25)
                .startAnimated(true)
                .hitboxWidth((int) (7 * SCALE))
                .hitboxHeight((int) (14 * SCALE))
                .xDrawOffset((int) (3 * SCALE))
                .yDrawOffset((int) (2 * SCALE))
                .build()
        );
        put(ObjectConstants.MAP_PIECE_1, ObjectConstants.newBuilder()
                .spriteAtlas(Sprite.MapFragmentsSpriteAtlas)
                .animationSprite(ObjectConstants.MAP_PIECE_1, 2)
                .gravity(GRAVITY)
                .animationSpeed(25)
                .startAnimated(true)
                .hitboxWidth((int) (15 * SCALE))
                .hitboxHeight((int) (15 * SCALE))
                .build()
        );
        put(ObjectConstants.MAP_PIECE_2, ObjectConstants.newBuilder()
                        .spriteAtlas(Sprite.MapFragmentsSpriteAtlas)
                        .animationSprite(ObjectConstants.MAP_PIECE_2, 2)
                        .gravity(GRAVITY)
                        .animationSpeed(25)
                        .startAnimated(true)
                        .hitboxWidth((int) (15 * SCALE))
                        .hitboxHeight((int) (15 * SCALE))
                        .xDrawOffset((int) (15 * SCALE))
                        .build()
        );
        put(ObjectConstants.MAP_PIECE_3, ObjectConstants.newBuilder()
                        .spriteAtlas(Sprite.MapFragmentsSpriteAtlas)
                        .animationSprite(ObjectConstants.MAP_PIECE_3, 2)
                        .gravity(GRAVITY)
                        .animationSpeed(25)
                        .startAnimated(true)
                        .hitboxWidth((int) (15 * SCALE))
                        .hitboxHeight((int) (15 * SCALE))
                        .yDrawOffset((int) (15 * SCALE))
                        .build()
        );
        put(ObjectConstants.MAP_PIECE_4, ObjectConstants.newBuilder()
                        .spriteAtlas(Sprite.MapFragmentsSpriteAtlas)
                        .animationSprite(ObjectConstants.MAP_PIECE_4, 2)
                        .gravity(GRAVITY)
                        .animationSpeed(25)
                        .startAnimated(true)
                        .hitboxWidth((int) (15 * SCALE))
                        .hitboxHeight((int) (15 * SCALE))
                        .xDrawOffset((int) (15 * SCALE))
                        .yDrawOffset((int) (15 * SCALE))
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
