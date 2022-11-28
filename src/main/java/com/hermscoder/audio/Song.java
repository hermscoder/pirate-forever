package com.hermscoder.audio;

public enum Song {
    MENU_1(0, "menu_1"),
    LEVEL_1(1, "level_1"),
    LEVEL_2(2, "level_2");

    private final int index;
    private final String fileName;

    Song(int index, String fileName) {
        this.index = index;
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public int getIndex() {
        return index;
    }
}
