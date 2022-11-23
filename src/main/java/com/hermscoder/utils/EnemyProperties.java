package com.hermscoder.utils;

import java.util.HashMap;
import java.util.Map;

public class EnemyProperties {
    private final Map<Integer, Integer> enemiesSpritesAmount;
    private final int maxHealth;
    private final int damage;

    private EnemyProperties(EnemyPropertiesBuilder enemyPropertiesBuilder) {
        this.enemiesSpritesAmount = enemyPropertiesBuilder.enemiesSpritesAmount;
        this.maxHealth = enemyPropertiesBuilder.maxHealth;
        this.damage = enemyPropertiesBuilder.damage;
    }
    public static EnemyPropertiesBuilder newBuilder() {
        return new EnemyPropertiesBuilder();
    }

    public Map<Integer, Integer> getEnemiesSpritesAmount() {
        return enemiesSpritesAmount;
    }
    public int getSpriteAmount(int playerAction) {
        return enemiesSpritesAmount.getOrDefault(playerAction, 0);
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getDamage() {
        return damage;
    }

    static class EnemyPropertiesBuilder {
        private final Map<Integer, Integer> enemiesSpritesAmount = new HashMap<>();
        private int maxHealth;
        private int damage;

        public EnemyPropertiesBuilder sprite(int animationIndex, int spritesQuantity) {
            enemiesSpritesAmount.put(animationIndex, spritesQuantity);
            return this;
        }

        public EnemyPropertiesBuilder maxHealth(int maxHealth) {
            this.maxHealth = maxHealth;
            return this;
        }

        public EnemyPropertiesBuilder damage(int damage) {
            this.damage = damage;
            return this;
        }

        public EnemyProperties build() {
            return new EnemyProperties(this);
        }
    }
}
