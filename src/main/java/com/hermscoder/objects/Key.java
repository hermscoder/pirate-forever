package com.hermscoder.objects;

import com.hermscoder.entities.Player;
import com.hermscoder.main.Game;

import java.awt.*;

public class Key extends Touchable {
    private float hoverOffset;
    private int maxHoverOffset, hoverDirection = 1;

    public Key(int x, int y, int objectType) {
        super(x, y, objectType);

        maxHoverOffset = (int) (10 * Game.SCALE);
    }


    @Override
    public void update() {
        updateAnimationTick();
        updateHover();
    }
    @Override
    public void draw(Graphics g, int xLvlOffset) {
        g.drawImage(objectConstants.getAnimationImage(0, animationIndex),
                (int) (hitBox.x - xDrawOffset - xLvlOffset),
                (int) (hitBox.y - yDrawOffset),
                objectConstants.getSpriteAtlas().getTileWidth(Game.SCALE),
                objectConstants.getSpriteAtlas().getTileHeight(Game.SCALE), null);
    }


    @Override
    public void onTouch(Player player) {
        active = false;
        player.addKeyToCollection(this);
    }

    private void updateHover() {
        hoverOffset += (0.1f * Game.SCALE * hoverDirection);
        if(hoverOffset >= maxHoverOffset)
            hoverDirection = -1;
        else if(hoverOffset < 0)
            hoverDirection = 1;

        hitBox.y = y + hoverOffset;
    }

    public int getCureValue() {
        return objectConstants.getHeal();
    }

    public int getPowerValue() {
        return objectConstants.getPower();
    }

}
