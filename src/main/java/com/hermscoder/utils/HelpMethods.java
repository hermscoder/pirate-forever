package com.hermscoder.utils;

import com.hermscoder.levels.LoadedData;
import com.hermscoder.main.Game;
import com.hermscoder.objects.Cannon;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class HelpMethods {
    public static boolean canMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        //top-left corner
        if (!isSolid(x, y, lvlData)) {
            //bottom-right corner
            if (!isSolid(x + width, y + height, lvlData)) {
                //top-right corner
                if (!isSolid(x + width, y, lvlData)) {
                    //bottom-left corner
                    if (!isSolid(x, y + height, lvlData)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isSightClear(int[][] levelData, Rectangle2D.Float firstHitBox, Rectangle2D.Float secondHitBox, int yTile) {
        int firstXTile = (int) (firstHitBox.x / Game.TILES_SIZE);
        int secondXTile = (int) (secondHitBox.x / Game.TILES_SIZE);

        int statTile = Math.min(firstXTile, secondXTile);
        int endTile = Math.max(firstXTile, secondXTile);

        isAllTilesWalkable(statTile, endTile, yTile, levelData);
        return true;
    }


    public static boolean isAllTilesWalkable(int xStart, int xEnd, int y, int[][] levelData) {
        if (isAllTilesClear(xStart, xEnd, y, levelData))
            for (int i = 0; i < xEnd - xStart; i++) {
                if (!isTileSolid(xStart + i, y + 1, levelData))
                    return false;

            }
        return true;
    }

    public static boolean isAllTilesClear(int xStart, int xEnd, int y, int[][] levelData) {
        for (int i = 0; i < xEnd - xStart; i++)
            if (isTileSolid(xStart + i, y, levelData))
                return false;
        return true;
    }

    private static boolean isSolid(float x, float y, int[][] lvlData) {
        int maxLevelWidth = lvlData[0].length * Game.TILES_SIZE;

        if (x < 0 || x >= maxLevelWidth)
            return true;
        if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        return isTileSolid((int) xIndex, (int) yIndex, lvlData);
    }

    public static boolean isTileSolid(int xTile, int yTile, int[][] lvlData) {
        if (yTile >= lvlData.length) {
            return true;
        }
        int value = lvlData[yTile][xTile];

        if (value >= Constants.LAST_SOLID_TILE_ID || value < 0 || value != 11)
            return true;

        return false;
    }

    public static boolean canCannonSeePlayer(int[][] levelData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
        int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);

        int statTile = Math.min(firstXTile, secondXTile);
        int endTile = Math.max(firstXTile, secondXTile);

        isAllTilesClear(statTile, endTile, yTile, levelData);
        return true;
    }

    public static float getEntityXPosNextToWall(Rectangle2D.Float hitBox, float xSpeed) {
        int currentTile = (int) (hitBox.x / Game.TILES_SIZE);
        if (xSpeed > 0) {
            // Right
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffSet = (int) (Game.TILES_SIZE - hitBox.width);

            return tileXPos + xOffSet - 1;
        } else {
            // Left
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static float getEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitBox, float airSpeed) {
        int currentTile = (int) (hitBox.y / Game.TILES_SIZE);
        if (airSpeed > 0) {
            // Falling - touchin floor
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffSet = (int) (Game.TILES_SIZE - hitBox.height);

            return tileYPos + yOffSet - 1;
        } else {
            // Jumping
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static boolean isEntityOnFloor(Rectangle2D.Float hitBox, int[][] lvlData) {
        // Check if the pixel below bottom-left and bottom-right is solid
        if (!isSolid(hitBox.x, hitBox.y + hitBox.height + 1, lvlData)) {
            if (!isSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, lvlData)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
        if (xSpeed > 0)
            return isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData);
        else
            return isSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
    }

    public static Point getPlayerSpawn(BufferedImage levelImage) {
        for (int j = 0; j < levelImage.getHeight(); j++) {
            for (int i = 0; i < levelImage.getWidth(); i++) {
                Color color = new Color(levelImage.getRGB(i, j));
                int value = color.getGreen();
                if (value == 100)
                    return new Point(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
            }
        }
        return new Point(1, 1);
    }


    public static LoadedData loadLevelFromImage(BufferedImage levelImage) {
        LoadedData loadedData = new LoadedData(levelImage);
        for (int j = 0; j < levelImage.getHeight(); j++) {
            for (int i = 0; i < levelImage.getWidth(); i++) {
                Color color = new Color(levelImage.getRGB(i, j));
                int objectType = color.getBlue();
                if (objectType > 0)
                    loadedData.addGameObject(ObjectFactory.newGameObject(i * Game.TILES_SIZE, j * Game.TILES_SIZE, objectType));

                int entityType = color.getGreen();
                if (entityType > 0) {
                    if (entityType != 100)
                        loadedData.addEntity(EntityFactory.newEntity(i * Game.TILES_SIZE, j * Game.TILES_SIZE, entityType));
                    else
                        loadedData.setPlayerSpawn(new Point(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
                }

                int value = color.getRed();
                if (value >= Constants.LAST_SOLID_TILE_ID)
                    value = 0;
                loadedData.addTileToLevelData(j, i, value);
            }
        }
        return loadedData;
    }

    public static boolean isHitBoxHittingLevel(Rectangle2D.Float hitBox, int[][] lvlData) {
        FloatPoint2D middle = getMiddlePositionOfHitBox(hitBox);
        return isSolid(middle.getX(), middle.getY(), lvlData);
    }

    private static FloatPoint2D getMiddlePositionOfHitBox(Rectangle2D.Float hitBox) {
        return new FloatPoint2D(
                hitBox.x + hitBox.width / 2,
                hitBox.y + hitBox.height / 2);
    }

}
