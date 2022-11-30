package com.hermscoder.objects;

import com.hermscoder.entities.Player;
import com.hermscoder.gamestates.Playing;
import com.hermscoder.levels.Level;
import com.hermscoder.levels.LevelManager;
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
    private LevelManager levelManager;
    private BufferedImage[][] potionImgs, containerImgs;
    private BufferedImage spikeImg, cannonBallImg;
    private BufferedImage[] cannonImgs;

    private ArrayList<Potion> potions;
    private ArrayList<Container> containers;
    private ArrayList<Spike> spikes;
    private ArrayList<Cannon> cannons;
    private ArrayList<Projectile> projectiles = new ArrayList<>();

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImages();
    }

    public void checkSpikesTouched(Player player) {
        for (Spike s : spikes) {
            if (s.getHitBox().intersects(player.getHitBox())) {
                player.kill();
            }
        }
    }

    public void checkObjectTouched(Rectangle2D.Float hitbox) {
        for (Potion p : potions) {
            if (p.isActive())
                if (hitbox.intersects(p.getHitBox())) {
                    p.setActive(false);
                    applyEffectToPlayer(p);
                }
        }
    }

    public void checkObjectHit(Rectangle2D.Float hitbox) {
        for (Container c : containers) {
            if (c.isActive() && !c.doAnimation)
                if (hitbox.intersects(c.getHitBox())) {
                    c.setAnimation(true);

                    int type = 0;
                    if (c.objectType == BARREL)
                        type = 1;

                    potions.add(new Potion(
                            (int) (c.getHitBox().x + c.getHitBox().width / 2),
                            (int) (c.getHitBox().y - c.getHitBox().height / 2), type));

                    return;
                }
        }
    }

    private void applyEffectToPlayer(Potion p) {
        playing.getPlayer().changeHealth(p.getCureValue());
        playing.getPlayer().changePower(p.getPowerValue());
    }

    public void loadObjects(Level newLevel) {
        potions = new ArrayList<>(newLevel.getPotions());
        containers = new ArrayList<>(newLevel.getContainers());
        spikes = newLevel.getSpikes();
        cannons = newLevel.getCannons();
        projectiles.clear();
    }

    private void loadImages() {
        BufferedImage potionSpriteAtlas = LoadSave.getSpriteAtlas(PotionSpriteAtlas.getFilename());
        potionImgs = new BufferedImage[PotionSpriteAtlas.getHeightInSprites()][PotionSpriteAtlas.getWidthInSprites()];

        for (int j = 0; j < potionImgs.length; j++) {
            for (int i = 0; i < potionImgs[j].length; i++) {
                potionImgs[j][i] = potionSpriteAtlas.getSubimage(
                        i * PotionSpriteAtlas.getTileWidth(),
                        j * PotionSpriteAtlas.getTileHeight(),
                        PotionSpriteAtlas.getTileWidth(),
                        PotionSpriteAtlas.getTileHeight());
            }
        }

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

        spikeImg = LoadSave.getSpriteAtlas(SpikeTrapSpriteAtlas.getFilename());

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
        for (Potion potion : potions) {
            if (potion.isActive())
                potion.update();
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
            if(projectile.isActive()) {
                projectile.updatePos();
                if(projectile.getHitBox().intersects(player.getHitBox())) {
                    player.changeHealth(projectile.getDamage());
                    projectile.setActive(false);
                } else if(isHitBoxHittingLevel(projectile.getHitBox(), lvlData)) {
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
                            if(HelpMethods.canCannonSeePlayer(lvlData, player.getHitBox(), cannon.getHitBox(), cannon.getTileY())) {
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
        int direction = cannon.getObjectType() == CANNON_LEFT ? -1 : 1;
        projectiles.add(new Projectile((int) cannon.getHitBox().x, (int) cannon.getHitBox().y, direction));
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
        drawPotions(g, xLvlOffset);
        drawContainers(g, xLvlOffset);
        drawTraps(g, xLvlOffset);
        drawCannons(g, xLvlOffset);
        drawProjectiles(g, xLvlOffset);
    }

    private void drawProjectiles(Graphics g, int xLvlOffset) {
        for (Projectile projectile : projectiles) {
            if(projectile.isActive()) {
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
                    (int) cannon.getHitBox().y , width,
                    CannonSpriteAtlas.getTileHeight(Game.SCALE), null);
//            cannon.drawHitBox(g, xLvlOffset);
        }
    }

    private void drawTraps(Graphics g, int xLvlOffset) {
        for (Spike s : spikes) {
            g.drawImage(spikeImg,
                    (int) (s.getHitBox().x - s.getxDrawOffset() - xLvlOffset),
                    (int) (s.getHitBox().y - s.getyDrawOffset()),
                    SpikeTrapSpriteAtlas.getTileWidth(Game.SCALE),
                    SpikeTrapSpriteAtlas.getTileHeight(Game.SCALE), null);
//            s.drawHitBox(g, xLvlOffset);
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

    private void drawPotions(Graphics g, int xLvlOffset) {
        for (Potion potion : potions) {
            if (potion.isActive()) {
                g.drawImage(potionImgs[potion.getObjectType()][potion.getAnimationIndex()],
                        (int) (potion.getHitBox().x - potion.getxDrawOffset() - xLvlOffset),
                        (int) (potion.getHitBox().y - potion.getyDrawOffset()),
                        PotionSpriteAtlas.getTileWidth(Game.SCALE),
                        PotionSpriteAtlas.getTileHeight(Game.SCALE), null);

//                potion.drawHitBox(g, xLvlOffset);
            }
        }
    }


    public void resetAllObjects() {
        loadObjects(playing.getLevelManager().getCurrentLevel());
        for (Potion p : potions) {
            p.reset();
        }

        for (Container c : containers) {
            c.reset();
        }

        for (Cannon c : cannons) {
            c.reset();
        }
    }
}
