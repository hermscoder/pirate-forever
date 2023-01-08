package com.hermscoder.entities;

import com.hermscoder.gamestates.Playing;
import com.hermscoder.levels.Level;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class EnemyManager {
    private Playing playing;
    private ArrayList<Enemy> enemies = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
    }

    public void loadEnemies(Level level) {
        enemies = level.getEnemies();
    }

    public void update(int[][] levelData, Player player) {
        boolean isAnyActive = false;
        for (Enemy e : enemies) {
            if (e.isActive()) {
                e.update(levelData, player);
                isAnyActive = true;
            }
        }
        if (!isAnyActive) {
            playing.setLevelCompleted(true);
        }
    }

    public void draw(Graphics g, int xLevelOffset) {
        drawEnemies(g, xLevelOffset);
    }

    private void drawEnemies(Graphics g, int xLevelOffset) {
        for (Enemy e : enemies)
            if (e.isActive()) {
                e.draw(g, xLevelOffset);
            }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox, int damageValue) {
        for (Enemy e : enemies) {
            if (e.isActive() && e.getCurrentHealth() > 0) {
                if (attackBox.intersects(e.getHitBox())) {
                    e.hurt(damageValue);
                    return;
                }
            }
        }
    }

    public void resetAllEnemies() {
        for (Enemy e : enemies) {
            e.resetEnemy();
        }
    }
}
