package com.hermscoder.levels;

import com.hermscoder.entities.Crabby;
import com.hermscoder.entities.Shark;
import com.hermscoder.main.Game;
import com.hermscoder.objects.Container;
import com.hermscoder.objects.*;
import com.hermscoder.utils.HelpMethods;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Level {

    private int index;
    private BufferedImage image;
    private int[][] lvlData;
    //Entities
    private ArrayList<Crabby> crabs;
    private ArrayList<Shark> sharks;
    //Objects
    private ArrayList<Touchable> touchables;
    private ArrayList<Container> containers;
    private ArrayList<Cannon> cannons;

    private int levelTilesWide;
    private int maxTilesOffset;
    private int maxLevelOffsetX;
    private Point playerSpawn;

    public Level(int index, BufferedImage image) {
        this.index = index;
        this.image = image;

        createLevelData();
        createEnemies();
        createEnemies();
        createGameObjects();
        createContainers();
        createCannons();
        calculateOffsets();
        calculatePlayerSpawn();
    }

    private void createCannons() {
        cannons = HelpMethods.getCannons(image);
    }

    private void calculatePlayerSpawn() {
        playerSpawn = HelpMethods.getPlayerSpawn(image);
    }

    private void createLevelData() {
        lvlData = HelpMethods.getLevelData(image);
    }

    private void createEnemies() {
        crabs = HelpMethods.getCrabs(image);
        sharks = HelpMethods.getShark(image);
    }

    private void createGameObjects() {
        touchables = new ArrayList<>();
        ArrayList<GameObject> gameObjects = HelpMethods.getGameObjects(image);
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Touchable) {
                touchables.add((Touchable) gameObject);
            }
        }
    }

    private void createContainers() {
        containers = HelpMethods.getContainers(image);
    }

    private void calculateOffsets() {
        levelTilesWide = image.getWidth();
        maxTilesOffset = levelTilesWide - Game.TILES_IN_WIDTH;
        maxLevelOffsetX = Game.TILES_SIZE * maxTilesOffset;

    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int[][] getLvlData() {
        return lvlData;
    }

    public int getMaxLevelOffsetX() {
        return maxLevelOffsetX;
    }

    public ArrayList<Crabby> getCrabs() {
        return crabs;
    }

    public ArrayList<Shark> getSharks() {
        return sharks;
    }

    public ArrayList<Container> getContainers() {
        return containers;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }


    public ArrayList<Cannon> getCannons() {
        return cannons;
    }

    public int getIndex() {
        return index;
    }

    public ArrayList<Touchable> getTouchables() {
        return touchables;
    }
}
