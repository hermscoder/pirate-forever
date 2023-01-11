package com.hermscoder.objects;

import com.hermscoder.entities.Player;
import com.hermscoder.gamestates.Playing;
import com.hermscoder.levels.Level;
import com.hermscoder.objects.type.Destroyable;
import com.hermscoder.objects.type.Interactable;
import com.hermscoder.objects.type.Touchable;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class ObjectManager {
    private final Playing playing;

    private ArrayList<Touchable> touchableObjects;
    private ArrayList<Destroyable> destroyableObjects;
    private ArrayList<Interactable> interactableObjects;
    private ArrayList<Cannon> cannons;


    public ObjectManager(Playing playing) {
        this.playing = playing;
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
        for (Destroyable d : destroyableObjects) {
            if (d.isActive() && !d.doAnimation)
                if (hitbox.intersects(d.getHitBox())) {
                    List<Touchable> drops = d.onHit(this);
                    if (drops != null) {
                        touchableObjects.addAll(drops);
                    }
                    return;
                }
        }
    }

    public void loadObjects(Level newLevel) {
        touchableObjects = new ArrayList<>(newLevel.getTouchables());
        destroyableObjects = new ArrayList<>(newLevel.getDestroyables());
        interactableObjects = new ArrayList<>(newLevel.getInteractables());
        cannons = new ArrayList<>(newLevel.getCannons());
    }

    public void update(int[][] lvlData, Player player) {
        for (Touchable touchable : touchableObjects) {
            if (touchable.isActive())
                touchable.update();
        }

        for (Destroyable destroyable : destroyableObjects) {
            if (destroyable.isActive())
                destroyable.update();
        }

        for (Interactable interactable : interactableObjects) {
            if (interactable.isActive())
                interactable.update();
        }

        updateCannons(lvlData, player);
    }


    private void updateCannons(int[][] lvlData, Player player) {
        for (Cannon cannon : cannons) {
            cannon.update(lvlData, player);
        }
    }


    public void draw(Graphics g, int xLvlOffset) {
        drawTouchables(g, xLvlOffset);
        drawDestroyables(g, xLvlOffset);
        drawInteractables(g, xLvlOffset);
        drawCannons(g, xLvlOffset);
    }

    private void drawCannons(Graphics g, int xLvlOffset) {
        for (Cannon cannon : cannons) {
            cannon.draw(g, xLvlOffset);
        }
    }

    private void drawDestroyables(Graphics g, int xLvlOffset) {
        for (Destroyable destroyable : destroyableObjects) {
            if (destroyable.isActive()) {
                destroyable.draw(g, xLvlOffset);
            }
        }
    }


    private void drawTouchables(Graphics g, int xLvlOffset) {
        for (Touchable touchable : touchableObjects) {
            if (touchable.isActive()) {
                touchable.draw(g, xLvlOffset);
            }
        }
    }

    private void drawInteractables(Graphics g, int xLvlOffset) {
        for (Interactable interactable : interactableObjects) {
            if (interactable.isActive()) {
                interactable.draw(g, xLvlOffset);
            }
        }
    }

    public void resetAllObjects() {
        loadObjects(playing.getLevelManager().getCurrentLevel());
        for (Touchable touchable : touchableObjects) {
            touchable.reset();
        }

        for (Destroyable d : destroyableObjects) {
            d.reset();
        }

        for (Interactable i : interactableObjects) {
            i.reset();
        }

        for (Cannon c : cannons) {
            c.reset();
        }
    }

}
