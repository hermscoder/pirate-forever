package com.hermscoder.utils;

import com.hermscoder.objects.GameObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ObjectConstants {
    public static final int RED_POTION = 0;
    public static final int BLUE_POTION = 1;
    public static final int BARREL = 3;
    public static final int BOX = 2;

    public static final int SPIKE_TRAP = 50;
    public static final int CANNON_LEFT = 51;
    public static final int CANNON_RIGHT = 52;

    public static final int CANNON_BALL = 256;

    private final Map<Integer, Integer> entitiesAnimationSpritesAmount;
    private final int maxHealth;
    private final int value;
    private final int power;
    private float gravity;
    private final int animationSpeed;
    private final boolean startAnimated;
    private final int hitboxWidth;
    private final int hitboxHeight;
    private final int xDrawOffset;
    private final int yDrawOffset;
    private final Sprite spriteAtlas;

    private ObjectConstants(ObjectConstantsBuilder objectConstantsBuilder) {
        this.entitiesAnimationSpritesAmount = objectConstantsBuilder.entitiesAnimationSpritesAmount;
        this.maxHealth = objectConstantsBuilder.maxHealth;
        this.value = objectConstantsBuilder.value;
        this.power = objectConstantsBuilder.power;
        this.gravity = objectConstantsBuilder.gravity;
        this.animationSpeed = objectConstantsBuilder.animationSpeed;
        this.startAnimated = objectConstantsBuilder.startAnimated;
        this.hitboxWidth = objectConstantsBuilder.hitboxWidth;
        this.hitboxHeight = objectConstantsBuilder.hitboxHeight;
        this.xDrawOffset = objectConstantsBuilder.xDrawOffset;
        this.yDrawOffset = objectConstantsBuilder.yDrawOffset;
        this.spriteAtlas = objectConstantsBuilder.spriteAtlas;
    }
    public static ObjectConstantsBuilder newBuilder() {
        return new ObjectConstantsBuilder();
    }

    public int getAnimationSpriteAmount(int state) {
        return entitiesAnimationSpritesAmount.getOrDefault(state, 0);
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getValue() {
        return value;
    }

    public int getPower() {
        return power;
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

    public int getxDrawOffset() {
        return xDrawOffset;
    }

    public int getyDrawOffset() {
        return yDrawOffset;
    }

    public Sprite getSpriteAtlas() {
        return spriteAtlas;
    }

    static class ObjectConstantsBuilder {
        private final Map<Integer, Integer> entitiesAnimationSpritesAmount = new HashMap<>();
        private int maxHealth;
        private int value;
        private int power;
        private float gravity;
        private int animationSpeed;
        public boolean startAnimated;
        private int hitboxWidth;
        private int hitboxHeight;
        private int xDrawOffset;
        private int yDrawOffset;
        private Sprite spriteAtlas;

        public ObjectConstantsBuilder animationSprite(int animationIndex, int spritesQuantity) {
            entitiesAnimationSpritesAmount.put(animationIndex, spritesQuantity);
            return this;
        }

        public ObjectConstantsBuilder maxHealth(int maxHealth) {
            this.maxHealth = maxHealth;
            return this;
        }

        public ObjectConstantsBuilder value(int value) {
            this.value = value;
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
            return this;
        }

        public ObjectConstants build() {
            return new ObjectConstants(this);
        }


    }
}
