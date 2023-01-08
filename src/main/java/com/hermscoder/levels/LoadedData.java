package com.hermscoder.levels;

import com.hermscoder.entities.Entity;
import com.hermscoder.objects.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class LoadedData {
    private Point playerSpawn;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private ArrayList<Entity> entities = new ArrayList<>();
    private int[][] lvlData;

    public LoadedData(BufferedImage levelImage) {
        this.lvlData = new int[levelImage.getHeight()][levelImage.getWidth()];
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void addTileToLevelData(int x, int y, int value) {
        lvlData[x][y] = value;
    }

    public ArrayList<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public ArrayList<Entity> getEntities() {
        return new ArrayList<>(entities);
    }

    public int[][] getLvlData() {
        return Arrays.copyOf(lvlData, lvlData.length);
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }

    public void setPlayerSpawn(Point playerSpawn) {
        this.playerSpawn = playerSpawn;
    }
}
