package com.hermscoder.utils;

import java.util.HashMap;
import java.util.Map;

public class EntityConstants {
    private final Map<Integer, Integer> spritesAmount;
    private final int maxHealth;
    private final int damage;
    private float gravity;
    private final int animationSpeed;
    private final float walkSpeed;

    private EntityConstants(EntityConstantsBuilder entityConstantsBuilder) {
        this.spritesAmount = entityConstantsBuilder.entitiesSpritesAmount;
        this.maxHealth = entityConstantsBuilder.maxHealth;
        this.damage = entityConstantsBuilder.damage;
        this.gravity = entityConstantsBuilder.gravity;
        this.animationSpeed = entityConstantsBuilder.animationSpeed;
        this.walkSpeed = entityConstantsBuilder.walkSpeed;
    }

    public static EntityConstantsBuilder newBuilder() {
        return new EntityConstantsBuilder();
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

    public float getWalkSpeed() {
        return walkSpeed;
    }

    static class EntityConstantsBuilder {
        private final Map<Integer, Integer> entitiesSpritesAmount = new HashMap<>();
        private int maxHealth;
        private int damage;
        private float gravity;
        private int animationSpeed;
        private float walkSpeed;

        public EntityConstantsBuilder animation(int animationIndex, int spritesQuantity) {
            entitiesSpritesAmount.put(animationIndex, spritesQuantity);
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

        public EntityConstants build() {
            return new EntityConstants(this);
        }

        public EntityConstantsBuilder walkSpeed(float walkSpeed) {
            this.walkSpeed = walkSpeed;
            return this;
        }
    }
}