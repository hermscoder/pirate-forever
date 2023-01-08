package com.hermscoder.objects;

import com.hermscoder.entities.Player;
import com.hermscoder.main.Game;

import java.awt.*;

public class Spike extends Touchable {
    public Spike(int x, int y, int objectType) {
        super(x, y, objectType);
    }

    @Override
    void onTouch(Player player) {
        player.hit(hitBox, objectConstants.getDamage());
    }

    @Override
    void update() {

    }

    @Override
    void draw(Graphics g, int xLvlOffset) {
        g.drawImage(objectConstants.getAnimationImage(0, 0),
                (int) (hitBox.x - xDrawOffset - xLvlOffset),
                (int) (hitBox.y - yDrawOffset),
                objectConstants.getSpriteAtlas().getTileWidth(Game.SCALE),
                objectConstants.getSpriteAtlas().getTileHeight(Game.SCALE), null);
    }
}
