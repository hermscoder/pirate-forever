package com.hermscoder.ui;

import com.hermscoder.entities.Player;
import com.hermscoder.main.Game;
import com.hermscoder.objects.MapFragment;
import com.hermscoder.utils.Constants;
import com.hermscoder.utils.LoadSave;
import com.hermscoder.utils.ObjectConstants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;

import static com.hermscoder.main.Game.SCALE;
import static com.hermscoder.utils.Sprite.*;

public class PlayerUI {

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


    private int keyCollectionX = (int) (210 * SCALE);
    private int keyCollectionY = (int) (14 * SCALE);

    private int mapFragmentCollectionX = Game.GAME_WIDTH / 2;
    private int mapFragmentCollectionY = (int) (14 * SCALE);

    private final Player player;

    public PlayerUI(Player player) {
        this.player = player;
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
        powerWidth = (int) ((powerValue / (float) player.getPowerMaxValue()) * powerBarWidth);
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

        drawKeyCollection(g);
        drawMapFragments(g);
    }

    private void drawMapFragments(Graphics g) {
        List<Integer> mapFragmentsCollected = player.getMapFragmentsCollected().stream().map(MapFragment::getFragmentNumber).collect(Collectors.toList());
        for (int i = 0; i < 4; i++) {
            int objectType = ObjectConstants.MAP_PIECE_1 + i;
            ObjectConstants objectConstants = Constants.getObjectConstants(objectType);
            if (mapFragmentsCollected.contains(i + 1)) {
                g.drawImage(objectConstants.getAnimationImage(i, MapFragment.STATIC_FRAGMENT_INDEX),
                        mapFragmentCollectionX,
                        mapFragmentCollectionY,
                        MapFragmentsSpriteAtlas.getTileWidth(SCALE),
                        MapFragmentsSpriteAtlas.getTileHeight(SCALE),
                        null);
            } else {
                g.drawImage(objectConstants.getAnimationImage(objectType - ObjectConstants.MAP_PIECE_1, MapFragment.STATIC_FRAGMENT_MISSING_INDEX),
                        mapFragmentCollectionX,
                        mapFragmentCollectionY,
                        MapFragmentsSpriteAtlas.getTileWidth(SCALE),
                        MapFragmentsSpriteAtlas.getTileHeight(SCALE),
                        null);
            }

        }
    }

    private void drawKeyCollection(Graphics g) {
        for (int i = 0; i < player.getKeysCollected().size(); i++) {
            g.drawImage(player.getKeysCollected().get(i).getStaticImage(),
                    keyCollectionX + i * (KeySpriteAtlas.getTileWidth(Game.SCALE)),
                    keyCollectionY,
                    KeySpriteAtlas.getTileWidth(Game.SCALE),
                    KeySpriteAtlas.getTileHeight(SCALE),
                    null);
        }
    }
}
