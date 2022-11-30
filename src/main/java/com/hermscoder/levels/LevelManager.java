package com.hermscoder.levels;

import com.hermscoder.gamestates.GameState;
import com.hermscoder.main.Game;
import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static com.hermscoder.main.Game.TILES_IN_HEIGHT;
import static com.hermscoder.main.Game.TILES_SIZE;
import static com.hermscoder.utils.Sprite.LevelSpriteAtlas;

public class LevelManager {

    private final Game game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private Level currentLevel;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();
        changeLevel(0);
    }

    public void loadNextLevel() {
        int nextLevelIndex = getCurrentLevel().getIndex() + 1;
        if (nextLevelIndex >= levels.size()) {
            nextLevelIndex = 0;
            System.out.println("NO MORE LEVELS! GAME COMPLETED!");
            game.getPlaying().setGameState(GameState.MENU);
        }

        changeLevel(nextLevelIndex);
        game.getPlaying().getEnemyManager().loadEnemies(getCurrentLevel());
        game.getPlaying().getPlayer().loadLvlData(getCurrentLevel().getLvlData());
        game.getPlaying().getLevelRender().setMaxLevelOffsetX(getCurrentLevel().getMaxLevelOffsetX());
        game.getPlaying().getPlayer().setSpawn(getCurrentLevel().getPlayerSpawn());
        game.getPlaying().getObjectManager().loadObjects(getCurrentLevel());
        game.getPlaying().resetAll();
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.getAllLevels();
        int index = 0;
        for (BufferedImage levelImage : allLevels) {
            levels.add(new Level(index, levelImage));
            index++;
        }
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.getSpriteAtlas(LevelSpriteAtlas.getFilename());
        int size = LevelSpriteAtlas.getHeightInSprites() * LevelSpriteAtlas.getWidthInSprites();
        levelSprite = new BufferedImage[size];
        for (int j = 0; j < LevelSpriteAtlas.getHeightInSprites(); j++) {
            for (int i = 0; i < LevelSpriteAtlas.getWidthInSprites(); i++) {
                int index = j * LevelSpriteAtlas.getWidthInSprites() + i;
                levelSprite[index] = img.getSubimage(
                        i * LevelSpriteAtlas.getTileWidth(),
                        j * LevelSpriteAtlas.getTileHeight(),
                        LevelSpriteAtlas.getTileWidth(),
                        LevelSpriteAtlas.getTileHeight());
            }
        }
    }

    public void draw(Graphics g, int xLevelOffset) {
        for (int j = 0; j < TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < getCurrentLevel().getLvlData()[0].length; i++) {
                int index = getCurrentLevel().getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], i * TILES_SIZE - xLevelOffset, j * TILES_SIZE, TILES_SIZE, TILES_SIZE, null);
            }
        }
    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void changeLevel(int levelIndex) {
        this.currentLevel = levels.get(levelIndex);
    }

    public int getAmountOfLevels() {
        return levels.size();
    }
}
