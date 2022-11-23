package com.hermscoder.entities;

import com.hermscoder.gamestates.Playing;
import com.hermscoder.levels.LevelManager;
import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static com.hermscoder.utils.Constants.EnemyConstants.CRABBY_DRAWOFFSET_X;
import static com.hermscoder.utils.Constants.EnemyConstants.CRABBY_DRAWOFFSET_Y;
import static com.hermscoder.utils.Sprite.CrabbySpriteAtlas;

public class EnemyManager {
    private Playing playing;
    private LevelManager levelManager;
    private BufferedImage[][] crabbyArray;
    private ArrayList<Crabby> crabbies = new ArrayList<>();

    public EnemyManager(Playing playing, LevelManager levelManager) {
        this.playing = playing;
        this.levelManager = levelManager;
        loadEnemyImages();
        addEnemies();
    }

    private void addEnemies() {
        crabbies = LoadSave.getCrabs(levelManager.getCurrentLevel().getId());
        System.out.println("size of crabs: " + crabbies.size());
    }

    public void update(int[][] levelData, Player player) {
        for (Crabby c : crabbies) {
            if (c.isActive())
                c.update(levelData, player);
        }
    }

    public void draw(Graphics g, int xLevelOffset) {
        drawCrabs(g, xLevelOffset);
    }

    private void drawCrabs(Graphics g, int xLevelOffset) {
        for (Crabby c : crabbies)
            if (c.isActive()) {
                g.drawImage(
                        crabbyArray[c.getEnemyState()][c.getAnimationIndex()],
                        (int) c.getHitBox().x - xLevelOffset - CRABBY_DRAWOFFSET_X + c.flipX(),
                        (int) c.getHitBox().y - CRABBY_DRAWOFFSET_Y,
                        CrabbySpriteAtlas.getTileWidth() * c.flipW(),
                        CrabbySpriteAtlas.getTileHeight(), null);
//            c.drawHitBox(g, xLevelOffset);
//            c.drawAttackBox(g, xLevelOffset);
            }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Crabby c : crabbies) {
            if(c.isActive()) {
                if (attackBox.intersects(c.getHitBox())) {
                    c.hurt(10);
                    return;
                }
            }
        }
    }

    private void loadEnemyImages() {
        crabbyArray = new BufferedImage[CrabbySpriteAtlas.getHeightInSprites()][CrabbySpriteAtlas.getWidthInSprites()];
        BufferedImage temp = LoadSave.getSpriteAtlas(CrabbySpriteAtlas.getFilename());
        for (int j = 0; j < crabbyArray.length; j++) {
            for (int i = 0; i < crabbyArray[j].length; i++) {
                crabbyArray[j][i] = temp.getSubimage(
                        i * CrabbySpriteAtlas.getTileWidth(),
                        j * CrabbySpriteAtlas.getTileHeight(),
                        CrabbySpriteAtlas.getTileWidth(),
                        CrabbySpriteAtlas.getTileHeight());
            }
        }
    }

    public void resetAllEnemies() {
        for (Crabby c : crabbies) {
            c.resetEnemy();
        }
    }
}
