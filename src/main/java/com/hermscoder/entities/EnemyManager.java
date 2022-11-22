package com.hermscoder.entities;

import com.hermscoder.gamestates.Playing;
import com.hermscoder.utils.LoadSave;
import com.hermscoder.utils.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static com.hermscoder.utils.Sprite.CrabbySpriteAtlas;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] crabbyArray;
    private ArrayList<Crabby> crabbies = new ArrayList<>();
    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImages();
        addEnemies();
    }

    private void addEnemies() {
        crabbies = LoadSave.getCrabs(playing.getCurrentLevel());
        System.out.println("size of crabs: " + crabbies.size());
    }

    public void update() {
        for(Crabby c : crabbies) {
            c.update();
        }
    }

    public void draw(Graphics g, int xLeveleOffset) {
        drawCrabs(g, xLeveleOffset);
    }

    private void drawCrabs(Graphics g, int xLeveleOffset) {
        for(Crabby c : crabbies) {
            g.drawImage(
                    crabbyArray[c.getEnemyState()][c.getAnimationIndex()],
                    (int)c.getHitBox().x - xLeveleOffset,
                    (int)c.getHitBox().y,
                    CrabbySpriteAtlas.getTileWidth(),
                    CrabbySpriteAtlas.getTileHeight(), null);
        }
    }

    private void loadEnemyImages() {
        crabbyArray = new BufferedImage[CrabbySpriteAtlas.getHeightInSprites()][CrabbySpriteAtlas.getWidthInSprites()];
        BufferedImage temp = LoadSave.getSpriteAtlas(CrabbySpriteAtlas.getFilename());
        for (int j = 0; j < crabbyArray.length; j++) {
            for (int i = 0; i < crabbyArray[j].length; i++) {
                crabbyArray[j][i] = temp.getSubimage(
                        i*CrabbySpriteAtlas.getTileWidth(),
                        j*CrabbySpriteAtlas.getTileHeight(),
                        CrabbySpriteAtlas.getTileWidth(),
                        CrabbySpriteAtlas.getTileHeight());
            }
        }
    }
}
