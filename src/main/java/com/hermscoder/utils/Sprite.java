package com.hermscoder.utils;

import static com.hermscoder.main.Game.*;

public enum Sprite {
    PlayerSpriteAtlas("player_sprites.png", 10, 8, 64, 40),
    LevelSpriteAtlas("outside_sprites.png", 4, 12, 32, 32),
    StartMenuBackground("start_menu_bg.gif", 1, 1, TILES_DEFAULT_SIZE * TILES_IN_WIDTH, TILES_DEFAULT_SIZE * TILES_IN_HEIGHT),
    MenuButtonsSpriteAtlas("button_atlas.png", 3, 3, 140, 56),
    MenuBackgroundSprite("menu_background.png", 3, 3, 282, 336),
    PauseMenuBackgroundSprite("pause_menu.png", 1, 1, 258, 389),
    SoundButtonsSpriteAtlas("sound_button.png", 2, 3, 42, 42),
    UrmButtonsSpriteAtlas("urm_buttons.png", 3, 3, 56, 56),
    VolumeButtonsSpriteAtlas("volume_buttons.png", 1, 3, 28, 44),
    VolumeSliderSprite("volume_buttons.png", 1, 1, 215, 44),
    PlayingBackgroundImage("playing_bg_img.png", 1, 1, TILES_DEFAULT_SIZE * TILES_IN_WIDTH, TILES_DEFAULT_SIZE * TILES_IN_HEIGHT),
    BigCloudImage("big_clouds.png", 1, 1, 448, 101),
    SmallCloudImage("small_clouds.png", 1, 1, 74, 24),
    CrabbySpriteAtlas("crabby_sprite.png", 5, 9, 72, 32),
    StatusBar("health_power_bar.png", 1, 1, 192, 58),
    CompletedLevel("completed_sprite.png", 1, 1, 224, 204),
    PotionSpriteAtlas("potions_sprites.png", 2, 7, 12, 16),
    ContainersSpriteAtlas("objects_sprites.png", 2, 8, 40, 30);

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