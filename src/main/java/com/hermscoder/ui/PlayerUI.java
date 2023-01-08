package com.hermscoder.ui;

import com.hermscoder.entities.Player;
import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.hermscoder.main.Game.SCALE;
import static com.hermscoder.utils.Sprite.StatusBar;

public class PlayerUI {

    private static final int POWER_GROW_SPEED = 20;

    //StatusBar UI
    private BufferedImage statusBarImg;

    private int statusBarX = (int) (10 * SCALE);
    private int statusBarY = (int) (10 * SCALE);

    private int healthBarWidth = (int) (150 * SCALE);
    private int healthBarHeight = (int) (4 * SCALE);
    private int healthBarXStart = (int) (34 * SCALE);
    private int healthBarYStart = (int) (14 * SCALE);
    private int healthWidth = healthBarWidth;

    private int powerBarWidth = (int) (104 * SCALE);
    private int powerBarHeight = (int) (2 * SCALE);
    private int powerBarXStart = (int) (44 * SCALE);
    private int powerBarYStart = (int) (34 * SCALE);
    private int powerWidth = healthBarWidth;
    private int powerMaxValue;

    public PlayerUI(int powerMaxValue) {
        this.powerMaxValue = powerMaxValue;
        statusBarImg = LoadSave.getSpriteAtlas(StatusBar.getFilename());
    }
    public void update(Player player) {
        updateHealthBar(player.getCurrentHealth(), player.getMaxHealth());
        updatePowerBar(player.getPowerValue());
    }

    private void updateHealthBar(int currentHealth, int maxHealth) {
        healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
    }

    private void updatePowerBar(int powerValue) {
        powerWidth = (int) ((powerValue / (float) powerMaxValue) * powerBarWidth);
    }

    public void draw(Graphics g) {
        g.drawImage(statusBarImg,
                statusBarX,
                statusBarY,
                StatusBar.getTileWidth(SCALE),
                StatusBar.getTileHeight(SCALE),
                null);
        //filling health rectangle
        g.setColor(Color.RED);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);

        //filling power rectangle
        g.setColor(Color.YELLOW);
        g.fillRect(powerBarXStart + statusBarX, powerBarYStart + statusBarY, powerWidth, powerBarHeight);
    }
}
