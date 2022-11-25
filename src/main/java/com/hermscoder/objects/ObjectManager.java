package com.hermscoder.objects;

import com.hermscoder.gamestates.Playing;
import com.hermscoder.levels.Level;
import com.hermscoder.levels.LevelManager;
import com.hermscoder.main.Game;
import com.hermscoder.utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static com.hermscoder.utils.ObjectConstants.*;
import static com.hermscoder.utils.Sprite.ContainersSpriteAtlas;
import static com.hermscoder.utils.Sprite.PotionSpriteAtlas;

public class ObjectManager {
    private final Playing playing;
    private LevelManager levelManager;
    private BufferedImage[][] potionImgs, containerImgs;

    private ArrayList<Potion> potions;
    private ArrayList<Container> containers;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImages();
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
                    c.setDoAnimation(true);

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
        playing.getPlayer().changeHealth(p.getObjectConstants().getValue());
        playing.getPlayer().changePower(p.getObjectConstants().getPower());
    }

    public void loadObjects(Level newLevel) {
        potions = new ArrayList<>(newLevel.getPotions());
        containers = new ArrayList<>(newLevel.getContainers());
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
    }

    public void update() {
        for (Potion potion : potions) {
            if (potion.isActive())
                potion.update();
        }

        for (Container container : containers) {
            if (container.isActive())
                container.update();
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawPotions(g, xLvlOffset);
        drawContainers(g, xLvlOffset);
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
                container.drawHitBox(g, xLvlOffset);
            }
        }
    }

    private void drawPotions(Graphics g, int xLvlOffset) {
        for (Potion potion : potions) {
            if (potion.isActive()) {
                int type = potion.getObjectType() == RED_POTION ? 0 : 1;

                g.drawImage(potionImgs[type][potion.getAnimationIndex()],
                        (int) (potion.getHitBox().x - potion.getxDrawOffset() - xLvlOffset),
                        (int) (potion.getHitBox().y - potion.getyDrawOffset()),
                        PotionSpriteAtlas.getTileWidth(Game.SCALE),
                        PotionSpriteAtlas.getTileHeight(Game.SCALE), null);

                potion.drawHitBox(g, xLvlOffset);
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
    }
}
