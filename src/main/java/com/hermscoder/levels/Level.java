package com.hermscoder.levels;

public class Level {

    private int id;
    private int[][] lvlData;

    public Level(int id, int[][] lvlData) {
        this.id = id;
        this.lvlData = lvlData;
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
}
