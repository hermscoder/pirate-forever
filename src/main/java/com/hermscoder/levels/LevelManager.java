package com.hermscoder.levels;

import com.hermscoder.gamestates.GameState;
import com.hermscoder.main.Game;
import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static com.hermscoder.main.Game.*;
import static com.hermscoder.utils.Sprite.LevelSpriteAtlas;

public class LevelManager {

    private final Game game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int levelIndex = 0;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    public void loadNextLevel() {
        levelIndex++;
        if(levelIndex >= levels.size()) {
            levelIndex = 0;
            System.out.println("NO MORE LEVELS! GAME COMPLETED!");
            GameState.state = GameState.MENU;
        }

        Level newLevel = levels.get(levelIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLvlData(newLevel.getLvlData());
        game.getPlaying().getLevelRender().setMaxLevelOffsetX(newLevel.getMaxLevelOffsetX());
        game.getPlaying().getPlayer().setSpawn(newLevel.getPlayerSpawn());
        game.getPlaying().getObjectManager().loadObjects(newLevel);
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.getAllLevels();
        for (BufferedImage levelImage : allLevels) {
            levels.add(new Level(levelImage));
        }
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
            for(int i = 0; i < levels.get(levelIndex).getLvlData()[0].length; i++) {
                int index = levels.get(levelIndex).getSpriteIndex(i,j);
                g.drawImage(levelSprite[index], i * TILES_SIZE - xLevelOffset, j * TILES_SIZE, TILES_SIZE, TILES_SIZE, null);
            }
        }
    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return levels.get(levelIndex);
    }

    public int getAmountOfLevels() {
        return levels.size();
    }
}
