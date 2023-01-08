package com.hermscoder.objects;

import com.hermscoder.entities.Player;
import com.hermscoder.gamestates.Playing;
import com.hermscoder.levels.Level;
import com.hermscoder.main.Game;
import com.hermscoder.utils.HelpMethods;
import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static com.hermscoder.utils.HelpMethods.isHitBoxHittingLevel;
import static com.hermscoder.utils.ObjectConstants.*;
import static com.hermscoder.utils.Sprite.*;

public class ObjectManager {
    private final Playing playing;
    private BufferedImage[][] containerImgs;
    private BufferedImage cannonBallImg;
    private BufferedImage[] cannonImgs;

    private ArrayList<Touchable> touchableObjects;
    private ArrayList<Container> containers;
    private ArrayList<Cannon> cannons;
    private ArrayList<Projectile> projectiles = new ArrayList<>();


    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImages();
    }

    public void checkObjectsTouched(Player player) {
        for (Touchable touchable : touchableObjects) {
            if (touchable.isActive())
                if (player.getHitBox().intersects(touchable.getHitBox())) {
                    touchable.onTouch(player);
                }
        }
    }

    public void checkObjectHit(Rectangle2D.Float hitbox) {
        for (Container c : containers) {
            if (c.isActive() && !c.doAnimation)
                if (hitbox.intersects(c.getHitBox())) {
                    c.setAnimation(true);

                    int type = BLUE_POTION;
                    if (c.objectType == BARREL)
                        type = RED_POTION;

                    touchableObjects.add(new Potion(
                            (int) (c.getHitBox().x + c.getHitBox().width / 2),
                            (int) (c.getHitBox().y - c.getHitBox().height / 2), type));

                    return;
                }
        }
    }

    private void applyEffectToPlayer(Potion p) {
        playing.getPlayer().heal(p.getCureValue());
        playing.getPlayer().changePower(p.getPowerValue());
    }

    public void loadObjects(Level newLevel) {
        touchableObjects = new ArrayList<>(newLevel.getTouchables());
        containers = new ArrayList<>(newLevel.getContainers());
        cannons = new ArrayList<>(newLevel.getCannons());
        projectiles.clear();
    }

    private void loadImages() {
        BufferedImage containerSpriteAtlas = LoadSave.getSpriteAtlas(ContainersSpriteAtlas.getFilename());
        containerImgs = new BufferedImage[ContainersSpriteAtlas.getHeightInSprites()][ContainersSpriteAtlas.getWidthInSprites()];
        for (int j = 0; j < containerImgs.length; j++) {
            for (int i = 0; i < containerImgs[j].length; i++) {
                containerImgs[j][i] = containerSpriteAtlas.getSubimage(
                        i * ContainersSpriteAtlas.getTileWidth(),
                        j * ContainersSpriteAtlas.getTileHeight(),
                        ContainersSpriteAtlas.getTileWidth(),
                        ContainersSpriteAtlas.getTileHeight());
            }
        }

        cannonImgs = new BufferedImage[CannonSpriteAtlas.getWidthInSprites()];
        BufferedImage temp = LoadSave.getSpriteAtlas(CannonSpriteAtlas.getFilename());
        for (int i = 0; i < cannonImgs.length; i++) {
            cannonImgs[i] = temp.getSubimage(
                    i * CannonSpriteAtlas.getTileWidth(),
                    0, CannonSpriteAtlas.getTileWidth(),
                    CannonSpriteAtlas.getTileHeight());
        }

        cannonBallImg = LoadSave.getSpriteAtlas(CannonBallSprite.getFilename());
    }

    public void update(int[][] lvlData, Player player) {
        for (Touchable touchable : touchableObjects) {
            if (touchable.isActive())
                touchable.update();
        }

        for (Container container : containers) {
            if (container.isActive())
                container.update();
        }

        updateCannons(lvlData, player);
        updateProjectiles(lvlData, player);

    }

    private void updateProjectiles(int[][] lvlData, Player player) {
        for (Projectile projectile : projectiles) {
            if (projectile.isActive()) {
                projectile.updatePos();
                if (projectile.getHitBox().intersects(player.getHitBox())) {
                    player.hit(projectile.getHitBox(), projectile.getDamage());
                    projectile.setActive(false);
                } else if (isHitBoxHittingLevel(projectile.getHitBox(), lvlData)) {
                    projectile.setActive(false);
                }
            }
        }
    }

    private void updateCannons(int[][] lvlData, Player player) {
        for (Cannon cannon : cannons) {
            if (!cannon.doAnimation)
                if (cannon.getTileY() == player.getTileY()) {
                    if (isPlayerInRange(cannon, player))
                        if (isPlayerInFrontOfCannon(cannon, player))
                            if (HelpMethods.canCannonSeePlayer(lvlData, player.getHitBox(), cannon.getHitBox(), cannon.getTileY())) {
                                cannon.setAnimation(true);
                            }
                }
            cannon.update();
            if (cannon.getAnimationIndex() == 4 && cannon.getAnimationTick() == 0)
                shootCannon(cannon);
        }
    }

    private void shootCannon(Cannon cannon) {
        cannon.setAnimation(true);
        int direction = cannon.getObjectType() == CANNON_LEFT ? Projectile.LEFT : Projectile.RIGHT;
        projectiles.add(new Projectile((int) cannon.getHitBox().x, (int) cannon.getHitBox().y, direction, cannon.getObjectType()));
    }

    private boolean isPlayerInFrontOfCannon(Cannon cannon, Player player) {
        if (cannon.getObjectType() == CANNON_LEFT) {
            if (cannon.getHitBox().x > player.getHitBox().x)
                return true;
        } else if (cannon.getHitBox().x < player.getHitBox().x) {
            return true;
        }
        return false;
    }

    private boolean isPlayerInRange(Cannon cannon, Player player) {
        int absValue = (int) Math.abs(player.getHitBox().x - cannon.getHitBox().x);

        return absValue <= Game.TILES_SIZE * 5;
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawTouchables(g, xLvlOffset);
        drawContainers(g, xLvlOffset);
        drawCannons(g, xLvlOffset);
        drawProjectiles(g, xLvlOffset);
    }


    private void drawProjectiles(Graphics g, int xLvlOffset) {
        for (Projectile projectile : projectiles) {
            if (projectile.isActive()) {
                g.drawImage(cannonBallImg,
                        (int) (projectile.getHitBox().x - xLvlOffset),
                        (int) (projectile.getHitBox().y),
                        CannonBallSprite.getTileWidth(Game.SCALE),
                        CannonBallSprite.getTileHeight(Game.SCALE), null);
//                projectile.drawHitBox(g, xLvlOffset);
            }
        }
    }

    private void drawCannons(Graphics g, int xLvlOffset) {
        for (Cannon cannon : cannons) {
            int x = (int) (cannon.getHitBox().x - cannon.getxDrawOffset() - xLvlOffset);
            int width = CannonSpriteAtlas.getTileWidth(Game.SCALE);

            if (cannon.getObjectType() == CANNON_RIGHT) {
                x += width;
                width *= -1;
            }
            g.drawImage(cannonImgs[cannon.getAnimationIndex()], x,
                    (int) cannon.getHitBox().y, width,
                    CannonSpriteAtlas.getTileHeight(Game.SCALE), null);
//            cannon.drawHitBox(g, xLvlOffset);
        }
    }

    private void drawContainers(Graphics g, int xLvlOffset) {
        for (Container container : containers) {
            if (container.isActive()) {
                int type = container.getObjectType() == BOX ? 0 : 1;

                g.drawImage(containerImgs[type][container.getAnimationIndex()],
                        (int) (container.getHitBox().x - container.getxDrawOffset() - xLvlOffset),
                        (int) (container.getHitBox().y - container.getyDrawOffset()),
                        ContainersSpriteAtlas.getTileWidth(Game.SCALE),
                        ContainersSpriteAtlas.getTileHeight(Game.SCALE), null);
//                container.drawHitBox(g, xLvlOffset);
            }
        }
    }


    private void drawTouchables(Graphics g, int xLvlOffset) {
        for (Touchable touchable : touchableObjects) {
            if (touchable.isActive()) {
                touchable.draw(g, xLvlOffset);
//                potion.drawHitBox(g, xLvlOffset);
            }
        }
    }

    public void resetAllObjects() {
        loadObjects(playing.getLevelManager().getCurrentLevel());
        for (Touchable touchable : touchableObjects) {
            touchable.reset();
        }

        for (Container c : containers) {
            c.reset();
        }

        for (Cannon c : cannons) {
            c.reset();
        }
    }

}
