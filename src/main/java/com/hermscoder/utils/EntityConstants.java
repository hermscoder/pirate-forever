package com.hermscoder.utils;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class EntityConstants {
    private final Map<Integer, Integer> spritesAmount;
    private final int maxHealth;
    private final int damage;
    private final int viewRangeInTiles;
    private float gravity;
    private final int animationSpeed;
    private final float walkSpeed;
    private final int hitBoxWidth;
    private final int hitBoxHeight;
    private final int attackBoxWidth;
    private final int attackBoxHeight;
    private final int xDrawOffset;
    private final int yDrawOffset;
    private final Sprite spriteAtlas;
    private BufferedImage[][] animationImages;

    private EntityConstants(EntityConstantsBuilder entityConstantsBuilder) {
        this.spritesAmount = entityConstantsBuilder.entitiesSpritesAmount;
        this.maxHealth = entityConstantsBuilder.maxHealth;
        this.damage = entityConstantsBuilder.damage;
        this.viewRangeInTiles = entityConstantsBuilder.viewRangeInTiles;
        this.gravity = entityConstantsBuilder.gravity;
        this.animationSpeed = entityConstantsBuilder.animationSpeed;
        this.walkSpeed = entityConstantsBuilder.walkSpeed;
        this.hitBoxWidth = entityConstantsBuilder.hitBoxWidth;
        this.hitBoxHeight = entityConstantsBuilder.hitBoxHeight;
        this.attackBoxWidth = entityConstantsBuilder.attackBoxWidth;
        this.attackBoxHeight = entityConstantsBuilder.attackBoxHeight;
        this.xDrawOffset = entityConstantsBuilder.xDrawOffset;
        this.yDrawOffset = entityConstantsBuilder.yDrawOffset;
        this.spriteAtlas = entityConstantsBuilder.spriteAtlas;
        this.animationImages = entityConstantsBuilder.animationImages;
    }

    public static EntityConstantsBuilder newBuilder() {
        return new EntityConstantsBuilder();
    }

    public int getSpriteAmount(int state) {
        return spritesAmount.getOrDefault(state, 0);
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getDamage() {
        return damage;
    }

    public float getGravity() {
        return gravity;
    }

    public int getAnimationSpeed() {
        return animationSpeed;
    }

    public float getWalkSpeed() {
        return walkSpeed;
    }

    public int getHitBoxWidth() {
        return hitBoxWidth;
    }

    public int getHitBoxHeight() {
        return hitBoxHeight;
    }

    public int getAttackBoxWidth() {
        return attackBoxWidth;
    }

    public int getAttackBoxHeight() {
        return attackBoxHeight;
    }

    public int getViewRangeInTiles() {
        return this.viewRangeInTiles;
    }

    public int getxDrawOffset() {
        return xDrawOffset;
    }

    public int getyDrawOffset() {
        return yDrawOffset;
    }

    public Sprite getSpriteAtlas() {
        return spriteAtlas;
    }

    public BufferedImage getAnimationImage(int state, int animationIndex) {
        return animationImages[state][animationIndex];
    }

    static class EntityConstantsBuilder {
        private final Map<Integer, Integer> entitiesSpritesAmount = new HashMap<>();
        private int maxHealth;
        private int damage;
        public int viewRangeInTiles;
        private float gravity;
        private int animationSpeed;
        private float walkSpeed;
        private int hitBoxWidth;
        private int hitBoxHeight;
        private int attackBoxWidth;
        private int attackBoxHeight;
        private int xDrawOffset;
        private int yDrawOffset;
        private Sprite spriteAtlas;
        private BufferedImage[][] animationImages;

        public EntityConstantsBuilder animationSprite(int animation, int spritesQuantity) {
            entitiesSpritesAmount.put(animation, spritesQuantity);
            return this;
        }

        public EntityConstantsBuilder maxHealth(int maxHealth) {
            this.maxHealth = maxHealth;
            return this;
        }

        public EntityConstantsBuilder damage(int damage) {
            this.damage = damage;
            return this;
        }

        public EntityConstantsBuilder gravity(float gravity) {
            this.gravity = gravity;
            return this;
        }

        public EntityConstantsBuilder animationSpeed(int animationSpeed) {
            this.animationSpeed = animationSpeed;
            return this;
        }

        public EntityConstantsBuilder hitBox(int width, int height) {
            this.hitBoxWidth = width;
            this.hitBoxHeight = height;
            return this;
        }

        public EntityConstantsBuilder attackBox(int width, int height) {
            this.attackBoxWidth = width;
            this.attackBoxHeight = height;
            return this;
        }

        public EntityConstantsBuilder viewRangeInTiles(int viewRangeInTiles) {
            this.viewRangeInTiles = viewRangeInTiles;
            return this;
        }

        public EntityConstantsBuilder walkSpeed(float walkSpeed) {
            this.walkSpeed = walkSpeed;
            return this;
        }

        public EntityConstantsBuilder xDrawOffset(int xDrawOffset) {
            this.xDrawOffset = xDrawOffset;
            return this;
        }

        public EntityConstantsBuilder yDrawOffset(int yDrawOffset) {
            this.yDrawOffset = yDrawOffset;
            return this;
        }

        public EntityConstantsBuilder spriteAtlas(Sprite spriteAtlas) {
            this.spriteAtlas = spriteAtlas;

            BufferedImage temporary = LoadSave.getSpriteAtlas(spriteAtlas.getFilename());
            animationImages = new BufferedImage[spriteAtlas.getHeightInSprites()][spriteAtlas.getWidthInSprites()];
            for (int j = 0; j < animationImages.length; j++) {
                for (int i = 0; i < animationImages[j].length; i++) {
                    animationImages[j][i] = temporary.getSubimage(
                            i * spriteAtlas.getTileWidth(),
                            j * spriteAtlas.getTileHeight(),
                            spriteAtlas.getTileWidth(),
                            spriteAtlas.getTileHeight());
                }
            }
            return this;
        }

        public EntityConstants build() {
            return new EntityConstants(this);
        }


    }
}
