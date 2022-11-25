package com.hermscoder.levels;

import com.hermscoder.entities.Crabby;
import com.hermscoder.main.Game;
import com.hermscoder.objects.Container;
import com.hermscoder.objects.Potion;
import com.hermscoder.objects.Spike;
import com.hermscoder.utils.HelpMethods;
import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Level {

    private int id;
    private BufferedImage image;
    private int[][] lvlData;
    private ArrayList<Crabby> crabs;
    private ArrayList<Potion> potions;
    private ArrayList<Container> containers;
    private ArrayList<Spike> spikes;

    private int levelTilesWide;
    private int maxTilesOffset;
    private int maxLevelOffsetX;
    private Point playerSpawn;

    public Level(BufferedImage image) {
        this.image = image;

        createLevelData();
        createEnemies();
        createEnemies();
        createPotions();
        createContainers();
        createSpikes();
        calculateOffsets();
        calculatePlayerSpawn();
    }

    private void createSpikes() {
        spikes = HelpMethods.getSpikes(image);
    }

    private void calculatePlayerSpawn() {
        playerSpawn = HelpMethods.getPlayerSpawn(image);
    }

    private void createLevelData() {
        lvlData = HelpMethods.getLevelData(image);
    }

    private void createEnemies() {
        crabs = HelpMethods.getCrabs(image);
    }

    private void createPotions() {
        potions = HelpMethods.getPotions(image);
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

    public int getId() {
        return id;
    }

    public int getMaxLevelOffsetX() {
        return maxLevelOffsetX;
    }

    public ArrayList<Crabby> getCrabs() {
        return crabs;
    }

    public ArrayList<Potion> getPotions() {
        return potions;
    }

    public ArrayList<Container> getContainers() {
        return containers;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }

    public ArrayList<Spike> getSpikes() {
        return spikes;
    }
}
