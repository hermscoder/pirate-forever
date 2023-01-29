package com.hermscoder.objects;

import com.hermscoder.entities.Player;
import com.hermscoder.main.Game;
import com.hermscoder.utils.HelpMethods;

import java.awt.*;
import java.util.ArrayList;

import static com.hermscoder.utils.ObjectConstants.CANNON_LEFT;
import static com.hermscoder.utils.ObjectConstants.CANNON_RIGHT;

public class Cannon extends GameObject {
    public int tileY;
    private ArrayList<Projectile> projectiles = new ArrayList<>();

    public Cannon(int x, int y, int objectType) {
        super(x, y, objectType);
        //check the Y position that the cannon is using tiles.
        this.tileY = y / Game.TILES_SIZE;
    }

    public void update(int[][] lvlData, Player player) {
        if (!doAnimation)
            if (getTileY() == player.getTileY()) {
                if (isPlayerInRange(player))
                    if (isPlayerInFrontOfCannon(player))
                        if (HelpMethods.canCannonSeePlayer(lvlData, player.getHitBox(), getHitBox(), getTileY())) {
                            setAnimation(true);
                        }
            }
        if (doAnimation)
            updateAnimationTick();
        if (getAnimationIndex() == 4 && getAnimationTick() == 0)
            shootCannon();

        updateProjectiles(lvlData, player);
    }

    public void draw(Graphics g, int xLvlOffset) {
        int x = (int) (hitBox.x - xDrawOffset - xLvlOffset);
        int width = objectConstants.getSpriteAtlas().getTileWidth(Game.SCALE);

        if (objectType == CANNON_RIGHT) {
            x += width;
            width *= -1;
        }
        g.drawImage(objectConstants.getAnimationImage(0, animationIndex),
                x, (int) hitBox.y,
                width, objectConstants.getSpriteAtlas().getTileHeight(Game.SCALE), null);

        projectiles.forEach(p -> p.draw(g, xLvlOffset));
//        drawHitBox(g, xLvlOffset);
    }

    private void updateProjectiles(int[][] lvlData, Player player) {
        for (Projectile projectile : projectiles) {
            if (projectile.isActive()) {
                projectile.update(lvlData, player);
            }
        }
    }

    private boolean isPlayerInFrontOfCannon(Player player) {
        if (objectType == CANNON_LEFT) {
            if (hitBox.x > player.getHitBox().x)
                return true;
        } else if (hitBox.x < player.getHitBox().x) {
            return true;
        }
        return false;
    }

    private boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.getHitBox().x - hitBox.x);

        return absValue <= Game.TILES_SIZE * 5;
    }

    private void shootCannon() {
        setAnimation(true);
        int direction = objectType == CANNON_LEFT ? Projectile.LEFT : Projectile.RIGHT;
        projectiles.add(new Projectile((int) hitBox.x, (int) hitBox.y, direction, objectType));
    }


    @Override
    public void afterAnimationFinishedAction() {
        doAnimation = false;
    }

    @Override
    public void reset() {
        super.reset();
        projectiles.clear();
    }
    public int getTileY() {
        return tileY;
    }
}
