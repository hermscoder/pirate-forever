package com.hermscoder.objects;

import com.hermscoder.entities.Player;
import com.hermscoder.main.Game;

import java.awt.*;

public abstract class Weapon extends GameObject {
    private float hoverOffset;
    private int maxHoverOffset, hoverDirection = 1;

    private Player player;

    public Weapon(int x, int y, int objectType) {
        super(x, y, objectType);

        maxHoverOffset = (int) (10 * Game.SCALE);
    }

    public void update() {
        if (isDropped()) {
            updateAnimationTick();
            updateHover();
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        if (isDropped()) {
            g.drawImage(objectConstants.getAnimationImage(0, animationIndex),
                    (int) (hitBox.x - xDrawOffset - xLvlOffset),
                    (int) (hitBox.y - yDrawOffset),
                    objectConstants.getSpriteAtlas().getTileWidth(Game.SCALE),
                    objectConstants.getSpriteAtlas().getTileHeight(Game.SCALE), null);
        } else {
            g.drawImage(objectConstants.getAnimationImage(player.getState() + 1, player.getAnimationIndex()),
                    (int) (player.getHitBox().x - player.getxDrawOffset() - xLvlOffset + player.getFlipX()),
                    (int) (player.getHitBox().y - player.getyDrawOffset()),
                    objectConstants.getSpriteAtlas().getTileWidth(Game.SCALE) * player.getFlipW(),
                    objectConstants.getSpriteAtlas().getTileHeight(Game.SCALE), null);
        }
        drawHitBox(g, xLvlOffset);
    }

    protected void updateHover() {
        hoverOffset += (0.1f * Game.SCALE * hoverDirection);
        if (hoverOffset >= maxHoverOffset)
            hoverDirection = -1;
        else if (hoverOffset < 0)
            hoverDirection = 1;

        hitBox.y = y + hoverOffset;
    }

    public int getDamageValue() {
        return objectConstants.getDamage();
    }


    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isDropped() {
        return player == null;
    }

    @Override
    public void reset() {
        super.reset();
        player = null;
    }
}
