package com.hermscoder.levels;

import com.hermscoder.entities.Crabby;
import com.hermscoder.entities.Enemy;
import com.hermscoder.entities.Entity;
import com.hermscoder.entities.Shark;
import com.hermscoder.main.Game;
import com.hermscoder.objects.Cannon;
import com.hermscoder.objects.Container;
import com.hermscoder.objects.GameObject;
import com.hermscoder.objects.Touchable;
import com.hermscoder.utils.HelpMethods;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Level {

    private int index;
    private BufferedImage image;
    private int[][] lvlData;
    //Entities
    private ArrayList<Enemy> enemies;
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
        loadLevelFromImage();
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
        sharks = HelpMethods.getShark(image);
    }

    private void loadLevelFromImage() {
        LoadedData loadedData = HelpMethods.loadLevelFromImage(image);
        lvlData = loadedData.getLvlData();

        processGameObjects(loadedData.getGameObjects());
        processEntities(loadedData.getEntities());
    }

    private void processEntities(ArrayList<Entity> entities) {
        enemies = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Enemy) {
                enemies.add((Enemy) entity);
            }
        }
    }

    private void processGameObjects(List<GameObject> gameObjects) {
        touchables = new ArrayList<>();
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

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
