package com.hermscoder.levels;

import com.hermscoder.main.Game;
import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.hermscoder.main.Game.*;
import static com.hermscoder.utils.Sprite.LevelSpriteAtlas;

public class LevelManager {

    private final Game game;
    private BufferedImage[] levelSprite;
    private Level levelOne;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        levelOne = new Level(LoadSave.getLevelData(1));
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.getSpriteAtlas(LevelSpriteAtlas.getFilename());
        int size = LevelSpriteAtlas.getHeightInSprites() * LevelSpriteAtlas.getWidthInSprites();
        levelSprite = new BufferedImage[size];
        for(int j = 0; j < LevelSpriteAtlas.getHeightInSprites(); j ++) {
            for(int i = 0; i < LevelSpriteAtlas.getWidthInSprites(); i++) {
                int index = j* LevelSpriteAtlas.getWidthInSprites() + i;
                levelSprite[index] = img.getSubimage(
                        i * LevelSpriteAtlas.getTileWidth(),
                        j * LevelSpriteAtlas.getTileHeight(),
                        LevelSpriteAtlas.getTileWidth(),
                        LevelSpriteAtlas.getTileHeight());
            }
        }
    }

    public void draw(Graphics g, int xLevelOffset) {
        for(int j = 0; j < TILES_IN_HEIGHT; j ++) {
            for(int i = 0; i < levelOne.getLvlData()[0].length; i++) {
                int index = levelOne.getSpriteIndex(i,j);
                g.drawImage(levelSprite[index], i * TILES_SIZE - xLevelOffset, j * TILES_SIZE, TILES_SIZE, TILES_SIZE, null);
            }
        }
    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return levelOne;
    }
}
