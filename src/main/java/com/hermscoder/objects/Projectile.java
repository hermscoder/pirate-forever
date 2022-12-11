package com.hermscoder.objects;

import com.hermscoder.main.Game;
import com.hermscoder.utils.Constants;
import com.hermscoder.utils.ObjectConstants;
import com.hermscoder.utils.Sprite;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Projectile {
    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    private Rectangle2D.Float hitBox;
    private int direction;
    private boolean active = true;
    private int damage;

    protected final ObjectConstants objectConstants;

    private float SPEED = (.9f * Game.SCALE);

    public Projectile(int x, int y, int direction, int parentObjectType) {
        this.direction = direction;
        this.objectConstants = Constants.getObjectConstants(ObjectConstants.CANNON_BALL);
        this.damage = Constants.getObjectConstants(parentObjectType).getDamage();

        int xOffset = direction == LEFT ? (int) (-3 * Game.SCALE) : (int) (29 * Game.SCALE);

        hitBox = new Rectangle2D.Float(x + xOffset, y + objectConstants.getyDrawOffset(),
                Sprite.CannonBallSprite.getTileWidth(Game.SCALE),
                Sprite.CannonBallSprite.getTileHeight(Game.SCALE));
    }

    public void updatePos() {
        hitBox.x += direction * SPEED;
    }

    public void setPosition(int x, int y) {
        hitBox.x = x;
        hitBox.y = y;
    }

    public boolean isActive() {
        return active;
    }

    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    public void drawHitBox(Graphics g, int xLvlOffset) {
        //For debugging the hitbox
        g.setColor(Color.PINK);
        g.drawRect((int) hitBox.x - xLvlOffset, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
    }

    public int getDamage() {
        return damage;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
