package com.hermscoder.entities;

import com.hermscoder.gamestates.Playing;
import com.hermscoder.levels.Level;
import com.hermscoder.levels.LevelManager;
import com.hermscoder.main.Game;
import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static com.hermscoder.utils.Constants.CrabbyConstants.CRABBY_DRAWOFFSET_X;
import static com.hermscoder.utils.Constants.CrabbyConstants.CRABBY_DRAWOFFSET_Y;
import static com.hermscoder.utils.Constants.SharkConstants.SHARK_DRAWOFFSET_X;
import static com.hermscoder.utils.Constants.SharkConstants.SHARK_DRAWOFFSET_Y;
import static com.hermscoder.utils.Sprite.CrabbySpriteAtlas;
import static com.hermscoder.utils.Sprite.SharkSpriteAtlas;

public class EnemyManager {
    private Playing playing;
    private LevelManager levelManager;
    private BufferedImage[][] crabbyImagesArray;
    private BufferedImage[][] sharkImagesArray;
    private ArrayList<Crabby> crabbies = new ArrayList<>();
    private ArrayList<Shark> sharks = new ArrayList<>();

    public EnemyManager(Playing playing, LevelManager levelManager) {
        this.playing = playing;
        this.levelManager = levelManager;
        loadEnemyImages();
    }

    public void loadEnemies(Level level) {
        crabbies = level.getCrabs();
        sharks = level.getSharks();
    }

    public void update(int[][] levelData, Player player) {
        boolean isAnyActive = false;
        for (Crabby c : crabbies) {
            if (c.isActive()) {
                c.update(levelData, player);
                isAnyActive = true;
            }
        }
        for (Shark s : sharks) {
            if (s.isActive()) {
                s.update(levelData, player);
                isAnyActive = true;
            }
        }
        if (!isAnyActive) {
            playing.setLevelCompleted(true);
        }
    }

    public void draw(Graphics g, int xLevelOffset) {
        drawCrabs(g, xLevelOffset);
    }

    private void drawCrabs(Graphics g, int xLevelOffset) {
        for (Crabby c : crabbies)
            if (c.isActive()) {
                g.drawImage(
                        crabbyImagesArray[c.getState()][c.getAnimationIndex()],
                        (int) c.getHitBox().x - xLevelOffset - CRABBY_DRAWOFFSET_X + c.flipX(),
                        (int) c.getHitBox().y - CRABBY_DRAWOFFSET_Y,
                        CrabbySpriteAtlas.getTileWidth(Game.SCALE) * c.flipW(),
                        CrabbySpriteAtlas.getTileHeight(Game.SCALE), null);
//            c.drawHitBox(g, xLevelOffset);
//            c.drawAttackBox(g, xLevelOffset);
            }

        for (Shark s : sharks)
            if (s.isActive()) {
                g.drawImage(
                        sharkImagesArray[s.getState()][s.getAnimationIndex()],
                        (int) s.getHitBox().x - xLevelOffset - SHARK_DRAWOFFSET_X + s.flipX(),
                        (int) s.getHitBox().y - SHARK_DRAWOFFSET_Y,
                        SharkSpriteAtlas.getTileWidth(Game.SCALE) * s.flipW(),
                        SharkSpriteAtlas.getTileHeight(Game.SCALE), null);
//                s.drawHitBox(g, xLevelOffset);
//                s.drawAttackBox(g, xLevelOffset);
            }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Crabby c : crabbies) {
            if (c.isActive() && c.getCurrentHealth() > 0) {
                if (attackBox.intersects(c.getHitBox())) {
                    c.hurt(10);
                    return;
                }
            }
        }

        for (Shark s : sharks) {
            if (s.isActive() && s.getCurrentHealth() > 0) {
                if (attackBox.intersects(s.getHitBox())) {
                    s.hurt(10);
                    return;
                }
            }
        }
    }

    private void loadEnemyImages() {
        crabbyImagesArray = new BufferedImage[CrabbySpriteAtlas.getHeightInSprites()][CrabbySpriteAtlas.getWidthInSprites()];
        BufferedImage temp = LoadSave.getSpriteAtlas(CrabbySpriteAtlas.getFilename());
        for (int j = 0; j < crabbyImagesArray.length; j++) {
            for (int i = 0; i < crabbyImagesArray[j].length; i++) {
                crabbyImagesArray[j][i] = temp.getSubimage(
                        i * CrabbySpriteAtlas.getTileWidth(),
                        j * CrabbySpriteAtlas.getTileHeight(),
                        CrabbySpriteAtlas.getTileWidth(),
                        CrabbySpriteAtlas.getTileHeight());
            }
        }

        sharkImagesArray = new BufferedImage[SharkSpriteAtlas.getHeightInSprites()][SharkSpriteAtlas.getWidthInSprites()];
        temp = LoadSave.getSpriteAtlas(SharkSpriteAtlas.getFilename());
        for (int j = 0; j < sharkImagesArray.length; j++) {
            for (int i = 0; i < sharkImagesArray[j].length; i++) {
                sharkImagesArray[j][i] = temp.getSubimage(
                        i * SharkSpriteAtlas.getTileWidth(),
                        j * SharkSpriteAtlas.getTileHeight(),
                        SharkSpriteAtlas.getTileWidth(),
                        SharkSpriteAtlas.getTileHeight());
            }
        }
    }

    public void resetAllEnemies() {
        for (Crabby c : crabbies) {
            c.resetEnemy();
        }

        for (Shark s : sharks) {
            s.resetEnemy();
        }
    }
}
