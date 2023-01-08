package com.hermscoder.objects;

import com.hermscoder.entities.Player;
import com.hermscoder.main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;


public abstract class Weapon extends Touchable {
    private float hoverOffset;
    private int maxHoverOffset, hoverDirection = 1;
    private static final int HOVER_DROPPED_EFFECT = 0;

    protected Rectangle2D.Float attackBox;

    protected Player player;

    public Weapon(int x, int y, int objectType) {
        super(x, y, objectType);

        maxHoverOffset = (int) (10 * Game.SCALE);
        initAttackBox();

    }

    protected void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, objectConstants.getAttackBoxWidth(), objectConstants.getAttackBoxHeight());
    }


    @Override
    void onTouch(Player player) {
        if (player.getCurrentWeapon() != null) {
            player.getCurrentWeapon().setActive(false);
        }
        player.changeWeapon(this);
        setPlayer(player);
    }

    public void update() {
        if (isDropped()) {
            updateAnimationTick();
            updateHover();
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        if (isDropped()) {
            g.drawImage(objectConstants.getAnimationImage(HOVER_DROPPED_EFFECT, animationIndex),
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
//        drawHitBox(g, xLvlOffset);
        drawAttackBox(g, xLvlOffset);
    }

    protected void updateHover() {
        hoverOffset += (0.1f * Game.SCALE * hoverDirection);
        if (hoverOffset >= maxHoverOffset)
            hoverDirection = -1;
        else if (hoverOffset < 0)
            hoverDirection = 1;

        hitBox.y = y + hoverOffset;
    }

    public void drawAttackBox(Graphics g, int xLevelOffset) {
        g.setColor(Color.YELLOW);
        g.drawRect((int) attackBox.x - xLevelOffset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
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

    public Rectangle2D.Float getAttackBox() {
        return attackBox;
    }

    @Override
    public void reset() {
        super.reset();
        player = null;
    }
}
