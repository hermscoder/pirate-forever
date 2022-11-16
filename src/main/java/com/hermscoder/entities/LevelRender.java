package com.hermscoder.entities;

import com.hermscoder.gamestates.Playing;
import com.hermscoder.levels.LevelManager;
import com.hermscoder.main.Game;
import com.hermscoder.utils.LoadSave;

import java.awt.*;

public class LevelRender {

    private final Playing playing;
    private final LevelManager levelManager;

    private int xLevelOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int levelTilesWide = LoadSave.getLevelData(1)[0].length;
    private int maxTilesOffset = levelTilesWide - Game.TILES_IN_WIDTH;
    private int maxLevelOffsetX = maxTilesOffset * Game.TILES_SIZE;



    public LevelRender(Playing playing, LevelManager levelManager) {
        this.playing = playing;
        this.levelManager = levelManager;
    }

    private void checkCloseToBorder() {
        int playerX = (int) playing.getPlayer().getHitBox().x;
        int diff = playerX - xLevelOffset;

        if(diff > rightBorder)
            xLevelOffset += diff - rightBorder;
        else if(diff < leftBorder)
            xLevelOffset += diff - leftBorder;

        if(xLevelOffset > maxLevelOffsetX)
            xLevelOffset = maxLevelOffsetX;
        else if(xLevelOffset < 0)
            xLevelOffset = 0;
    }

    public void update() {
        checkCloseToBorder();

    }

    public void draw(Graphics g) {
        levelManager.draw(g, xLevelOffset);
        drawCloseToBorder(g);
    }
    protected void drawCloseToBorder(Graphics g){
        //For debugging the border
        g.setColor(Color.BLUE);
        g.drawRect((int) xLevelOffset, (int) 10, (int) 10, (int) Game.GAME_HEIGHT);

        g.setColor(Color.RED);
        g.drawRect(leftBorder, 10, leftBorder, (int) Game.GAME_HEIGHT - 10);
        g.setColor(Color.GREEN);
        g.drawRect(rightBorder, 0, rightBorder, (int) Game.GAME_HEIGHT-10);
    }

    public int getxLevelOffset() {
        return xLevelOffset;
    }
}
