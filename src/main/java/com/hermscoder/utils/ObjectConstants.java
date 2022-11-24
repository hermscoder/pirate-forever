package com.hermscoder.utils;

import java.util.HashMap;
import java.util.Map;

public class ObjectConstants {
    public static final int RED_POTION = 0;
    public static final int BLUE_POTION = 1;
    public static final int BARREL = 3;
    public static final int BOX = 2;

    public static final int RED_POTION_VALUE = 15;
    public static final int BLUE_POTION_VALUE = 10;

    private final Map<Integer, Integer> spritesAmount;
    private final int maxHealth;
    private final int damage;
    private float gravity;
    private final int animationSpeed;
    private final boolean startAnimated;
    private final int hitboxWidth;
    private final int hitboxHeight;
    private final int xDrawOffset;
    private final int yDrawOffset;

    private ObjectConstants(ObjectConstantsBuilder objectConstantsBuilder) {
        this.spritesAmount = objectConstantsBuilder.entitiesSpritesAmount;
        this.maxHealth = objectConstantsBuilder.maxHealth;
        this.damage = objectConstantsBuilder.damage;
        this.gravity = objectConstantsBuilder.gravity;
        this.animationSpeed = objectConstantsBuilder.animationSpeed;
        this.startAnimated = objectConstantsBuilder.startAnimated;
        this.hitboxWidth = objectConstantsBuilder.hitboxWidth;
        this.hitboxHeight = objectConstantsBuilder.hitboxHeight;
        this.xDrawOffset = objectConstantsBuilder.xDrawOffset;
        this.yDrawOffset = objectConstantsBuilder.yDrawOffset;
    }
    public static ObjectConstantsBuilder newBuilder() {
        return new ObjectConstantsBuilder();
    }

    public Map<Integer, Integer> getSpritesAmount() {
        return spritesAmount;
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

    static class ObjectConstantsBuilder {
        private final Map<Integer, Integer> entitiesSpritesAmount = new HashMap<>();
        private int maxHealth;
        private int damage;
        private float gravity;
        private int animationSpeed;
        public boolean startAnimated;
        private int hitboxWidth;
        private int hitboxHeight;
        private int xDrawOffset;
        private int yDrawOffset;

        public ObjectConstantsBuilder sprite(int animationIndex, int spritesQuantity) {
            entitiesSpritesAmount.put(animationIndex, spritesQuantity);
            return this;
        }

        public ObjectConstantsBuilder maxHealth(int maxHealth) {
            this.maxHealth = maxHealth;
            return this;
        }

        public ObjectConstantsBuilder damage(int damage) {
            this.damage = damage;
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
        public ObjectConstants build() {
            return new ObjectConstants(this);
        }
    }
}
