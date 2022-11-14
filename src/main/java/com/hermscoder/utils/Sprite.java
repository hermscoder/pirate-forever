package com.hermscoder.utils;

import static com.hermscoder.main.Game.SCALE;
import static com.hermscoder.main.Game.TILES_SIZE;

public enum Sprite {
    PlayerSpriteAtlas("player_sprites.png", 9, 6, 64, 40),
    LevelSpriteAtlas("outside_sprites.png", 4, 12, 32, 32),
    MenuButtonsSpriteAtlas("button_atlas.png", 3, 3, 140, 56),
    MenuBackgroundSprite("menu_background.png", 3, 3, 282, 336);

    private String filename;
    private int heightInSprites;
    private int widthInSprites;
    private int tileWidth;
    private int tileHeight;

    Sprite(String fileName, int heightInSprites, int widthInSprites, int tileWidth, int tileHeight) {
        this.filename = fileName;
        this.heightInSprites = heightInSprites;
        this.widthInSprites = widthInSprites;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public String getFilename() {
        return filename;
    }

    public int getHeightInSprites() {
        return heightInSprites;
    }

    public int getWidthInSprites() {
        return widthInSprites;
    }

    public int getTileWidth() {
        return getTileWidth(1);
    }
    public int getTileWidth(float scale) {
        return (int) (tileWidth * scale);
    }

    public int getTileHeight() {
        return getTileHeight(1);
    }

    public int getTileHeight(float scale) {
        return (int) (tileHeight * scale);
    }
}