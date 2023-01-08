package com.hermscoder.objects;

import com.hermscoder.entities.Player;
import com.hermscoder.main.Game;

import java.awt.*;

import static com.hermscoder.utils.ObjectConstants.BLUE_POTION;

public class Potion extends Touchable {
    private float hoverOffset;
    private int maxHoverOffset, hoverDirection = 1;

    public Potion(int x, int y, int objectType) {
        super(x, y, objectType);

        maxHoverOffset = (int) (10 * Game.SCALE);
    }

    @Override
    void onTouch(Player player) {
        active = false;
        player.heal(getCureValue());
        player.changePower(getPowerValue());
    }

    @Override
    void draw(Graphics g, int xLvlOffset) {
        int objIndex = objectType == BLUE_POTION ? 0 : 1;
        g.drawImage(objectConstants.getAnimationImage(objIndex, animationIndex),
                (int) (hitBox.x - xDrawOffset - xLvlOffset),
                (int) (hitBox.y - yDrawOffset),
                objectConstants.getSpriteAtlas().getTileWidth(Game.SCALE),
                objectConstants.getSpriteAtlas().getTileHeight(Game.SCALE), null);
    }

    public void update() {
        updateAnimationTick();
        updateHover();
    }

    private void updateHover() {
        hoverOffset += (0.1f * Game.SCALE * hoverDirection);
        if (hoverOffset >= maxHoverOffset)
            hoverDirection = -1;
        else if (hoverOffset < 0)
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
