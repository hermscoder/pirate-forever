package com.hermscoder.utils;

import com.hermscoder.main.Game;

import java.awt.geom.Rectangle2D;

public class HelpMethods {
    public static boolean canMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        //top-left corner
        if(!isSolid(x,y,lvlData)) {
            //bottom-right corner
            if(!isSolid(x+width, y+height, lvlData)) {
                //top-right corner
                if(!isSolid(x+width, y, lvlData)) {
                    //bottom-left corner
                    if(!isSolid(x,y+height, lvlData)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isSolid(float x, float y, int[][] lvlData) {
        if(x < 0 || x >= Game.GAME_WIDTH)
            return true;
        if(y < 0 || y >= Game.GAME_HEIGHT)
            return true;

        float xIndex = x/ Game.TILES_SIZE;
        float yIndex = y/ Game.TILES_SIZE;

        int value = lvlData[(int)yIndex][(int)xIndex];

        if(value >= 48 || value < 0 || value != 11)
            return true;

        return false;
    }

    public static float getEntityXPosNextToWall(Rectangle2D.Float hitBox, float xSpeed) {
        int currentTile = (int)(hitBox.x/Game.TILES_SIZE);
        if(xSpeed > 0) {
            // Right
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffSet = (int)(Game.TILES_SIZE - hitBox.width);

            return tileXPos + xOffSet - 1;
        } else {
            // Left
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static float getEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitBox, float airSpeed) {
        int currentTile = (int)(hitBox.y/Game.TILES_SIZE);
        if(airSpeed > 0) {
            // Falling - touchin floor
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffSet = (int)(Game.TILES_SIZE - hitBox.height);

            return tileYPos + yOffSet - 1;
        } else {
            // Jumping
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static boolean isEntityOnFloor(Rectangle2D.Float hitBox, int[][] lvlData) {
        // Check if the pixel below bottom-left and bottom-right is solid
        if(!isSolid(hitBox.x, hitBox.y + hitBox.height + 1, lvlData)){
            if(!isSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, lvlData)){
                return false;
            }
        }
        return true;
    }
}