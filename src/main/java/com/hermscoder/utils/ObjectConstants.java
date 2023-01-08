package com.hermscoder.utils;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ObjectConstants {
    public static final int BLUE_POTION = 1;
    public static final int BLUE_POTION_ANIMATION = 0;

    public static final int RED_POTION = 2;
    public static final int RED_POTION_ANIMATION = 1;

    public static final int BOX = 3;
    public static final int BARREL = 4;
    public static final int KEY = 5;


    public static final int SPIKE_TRAP = 50;
    public static final int CANNON_LEFT = 51;
    public static final int CANNON_RIGHT = 52;


    //Weapons
    public static final int BARE_HANDS = 100;
    public static final int SWORD = 101;
    public static final int FIRE_SWORD = 102;

    public static final int CANNON_BALL = 256;

    private final Map<Integer, Integer> objectAnimationsSpritesAmount;
    private final int maxHealth;
    private final int damage;
    private final int heal;
    private final int power;
    private float gravity;
    private final int animationSpeed;
    private final boolean startAnimated;
    private final int hitboxWidth;
    private final int hitboxHeight;
    private final int attackBoxWidth;
    private final int attackBoxHeight;
    private final int xDrawOffset;
    private final int yDrawOffset;
    private final Sprite spriteAtlas;
    private BufferedImage[][] animationImages;

    private ObjectConstants(ObjectConstantsBuilder objectConstantsBuilder) {
        this.objectAnimationsSpritesAmount = objectConstantsBuilder.entitiesAnimationSpritesAmount;
        this.maxHealth = objectConstantsBuilder.maxHealth;
        this.damage = objectConstantsBuilder.damage;
        this.heal = objectConstantsBuilder.heal;
        this.power = objectConstantsBuilder.power;
        this.gravity = objectConstantsBuilder.gravity;
        this.animationSpeed = objectConstantsBuilder.animationSpeed;
        this.startAnimated = objectConstantsBuilder.startAnimated;
        this.hitboxWidth = objectConstantsBuilder.hitboxWidth;
        this.hitboxHeight = objectConstantsBuilder.hitboxHeight;
        this.attackBoxWidth = objectConstantsBuilder.attackBoxWidth;
        this.attackBoxHeight = objectConstantsBuilder.attackBoxHeight;
        this.xDrawOffset = objectConstantsBuilder.xDrawOffset;
        this.yDrawOffset = objectConstantsBuilder.yDrawOffset;
        this.spriteAtlas = objectConstantsBuilder.spriteAtlas;
        this.animationImages = objectConstantsBuilder.animationImages;
    }

    public static ObjectConstantsBuilder newBuilder() {
        return new ObjectConstantsBuilder();
    }

    public int getAnimationSpriteAmount(int animation) {
        return objectAnimationsSpritesAmount.getOrDefault(animation, 0);
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHeal() {
        return heal;
    }

    public int getPower() {
        return power;
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

    public boolean isStartAnimated() {
        return startAnimated;
    }

    public int getHitboxWidth() {
        return hitboxWidth;
    }

    public int getHitboxHeight() {
        return hitboxHeight;
    }

    public int getAttackBoxWidth() {
        return attackBoxWidth;
    }

    public int getAttackBoxHeight() {
        return attackBoxHeight;
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

    static class ObjectConstantsBuilder {
        private final Map<Integer, Integer> entitiesAnimationSpritesAmount = new HashMap<>();
        private int maxHealth;
        private int damage;
        private int heal;
        private int power;
        private float gravity;
        private int animationSpeed;
        public boolean startAnimated;
        private int hitboxWidth;
        private int hitboxHeight;
        private int attackBoxWidth;
        private int attackBoxHeight;
        private int xDrawOffset;
        private int yDrawOffset;
        private Sprite spriteAtlas;
        private BufferedImage[][] animationImages;

        public ObjectConstantsBuilder animationSprite(int animation, int spritesQuantity) {
            entitiesAnimationSpritesAmount.put(animation, spritesQuantity);
            return this;
        }

        public ObjectConstantsBuilder maxHealth(int maxHealth) {
            this.maxHealth = maxHealth;
            return this;
        }

        public ObjectConstantsBuilder value(int value) {
            this.heal = value;
            return this;
        }

        public ObjectConstantsBuilder damage(int damage) {
            this.damage = damage;
            return this;
        }

        public ObjectConstantsBuilder power(int power) {
            this.power = power;
            return this;
        }

        public ObjectConstantsBuilder gravity(float gravity) {
            this.gravity = gravity;
            return this;
        }

        public ObjectConstantsBuilder animationSpeed(int animationSpeed) {
            this.animationSpeed = animationSpeed;
            return this;
        }

        public ObjectConstantsBuilder startAnimated(boolean startAnimated) {
            this.startAnimated = startAnimated;
            return this;
        }

        public ObjectConstantsBuilder hitboxWidth(int hitboxWidth) {
            this.hitboxWidth = hitboxWidth;
            return this;
        }

        public ObjectConstantsBuilder hitboxHeight(int hitboxHeight) {
            this.hitboxHeight = hitboxHeight;
            return this;
        }

        public ObjectConstantsBuilder attackBox(int width, int height) {
            this.attackBoxWidth = width;
            this.attackBoxHeight = height;
            return this;
        }

        public ObjectConstantsBuilder xDrawOffset(int xDrawOffset) {
            this.xDrawOffset = xDrawOffset;
            return this;
        }

        public ObjectConstantsBuilder yDrawOffset(int yDrawOffset) {
            this.yDrawOffset = yDrawOffset;
            return this;
        }

        public ObjectConstantsBuilder spriteAtlas(Sprite spriteAtlas) {
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


        public ObjectConstants build() {
            return new ObjectConstants(this);
        }


    }
}
