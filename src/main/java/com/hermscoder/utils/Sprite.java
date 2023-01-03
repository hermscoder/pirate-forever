package com.hermscoder.utils;

import static com.hermscoder.main.Game.*;

public enum Sprite {
    //Entities
    PlayerSpriteAtlas("sprites/player_no_weapon_sprites.png", 10, 8, 64, 40),
    CrabbySpriteAtlas("sprites/crabby_sprite.png", 5, 9, 72, 32),
    SharkSpriteAtlas("sprites/shark_sprites.png", 10, 8, 34, 30),

    //Objects
    PotionSpriteAtlas("sprites/potions_sprites.png", 2, 7, 12, 16),
    ContainersSpriteAtlas("sprites/objects_sprites.png", 2, 8, 40, 30),
    SpikeTrapSpriteAtlas("sprites/trap_atlas.png", 1, 1, 32, 32),
    CannonSpriteAtlas("sprites/cannon_atlas.png", 1, 7, 40, 26),
    CannonBallSprite("sprites/ball.png", 1, 1, 15, 15),
    KeySpriteAtlas("sprites/key-sprite.png", 1, 8, 24, 24),
        //Weapons
    BareHandsSpriteAtlas("sprites/weapons/bare_hands_sprites.png", 10, 9, 64, 40),
    SwordSpriteAtlas("sprites/weapons/sword_sprites.png", 11, 8, 64, 40),
    FireSwordSpriteAtlas("sprites/weapons/fire_sword_sprites.png", 11, 8, 64, 40),


    //UI
    StartMenuBackground("sprites/start_menu_bg.gif", 1, 1, TILES_DEFAULT_SIZE * TILES_IN_WIDTH, TILES_DEFAULT_SIZE * TILES_IN_HEIGHT),
    MenuButtonsSpriteAtlas("sprites/button_atlas.png", 3, 3, 140, 56),
    MenuBackgroundSprite("sprites/menu_background.png", 3, 3, 282, 336),
    PauseMenuBackgroundSprite("sprites/pause_menu.png", 1, 1, 258, 389),
    SoundButtonsSpriteAtlas("sprites/sound_button.png", 2, 3, 42, 42),
    UrmButtonsSpriteAtlas("sprites/urm_buttons.png", 3, 3, 56, 56),
    VolumeButtonsSpriteAtlas("sprites/volume_buttons.png", 1, 3, 28, 44),
    VolumeSliderSprite("sprites/volume_buttons.png", 1, 1, 215, 44),
    StatusBar("sprites/health_power_bar.png", 1, 1, 192, 58),
    DeathScreenSprite("sprites/death_screen.png", 1, 1, 235, 225),
    CompletedLevel("sprites/completed_sprite.png", 1, 1, 224, 204),
    OptionsBackgroundSprite("sprites/options_background.png", 1, 1, 282, 393),

    //Levels
    LevelSpriteAtlas("sprites/outside_sprites.png", 4, 12, 32, 32),
    PlayingBackgroundImage("sprites/playing_bg_img.png", 1, 1, TILES_DEFAULT_SIZE * TILES_IN_WIDTH, TILES_DEFAULT_SIZE * TILES_IN_HEIGHT),
    BigCloudImage("sprites/big_clouds.png", 1, 1, 448, 101),
    SmallCloudImage("sprites/small_clouds.png", 1, 1, 74, 24);

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