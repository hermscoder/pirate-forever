package com.hermscoder.objects;

import com.hermscoder.entities.Player;
import com.hermscoder.main.Game;
import com.hermscoder.objects.type.Interactable;
import com.hermscoder.objects.type.Touchable;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.hermscoder.utils.ObjectConstants.*;

public class Chest extends Interactable {
    private boolean opened;

    public Chest(int x, int y, int objectType) {
        super(x, y, objectType);
    }

    @Override
    public List<Touchable> onInteract(ObjectManager objectManager, Player player) {
        if(opened)
            return Collections.emptyList();
        if(player.getKeysCollected().isEmpty())
            //TODO add animation to show that user needs a key to open it
            return Collections.emptyList();


        player.getKeysCollected().remove(0);
        doAnimation = true;
        List<Touchable> drops = new ArrayList<>();
        int type = BLUE_POTION;
        if (objectType == BARREL)
            type = RED_POTION;


        drops.add(new Potion(
                (int) (getHitBox().x + getHitBox().width / 2),
                (int) (getHitBox().y - getHitBox().height / 2), type));

        opened = true;
        return drops;
    }

    public void update() {
        if (doAnimation)
            updateAnimationTick();
    }

    @Override
    public void draw(Graphics g, int xLvlOffset) {
        g.drawImage(objectConstants.getAnimationImage(0, animationIndex),
                (int) (hitBox.x - xDrawOffset - xLvlOffset),
                (int) (hitBox.y),
                (int) ((objectConstants.getSpriteAtlas().getTileWidth() - 1) * Game.SCALE),
                (int) ((objectConstants.getSpriteAtlas().getTileHeight() - 1) * Game.SCALE), null);
//        drawHitBox(g, xLvlOffset);
    }

    @Override
    public void reset() {
        super.reset();
        opened = false;
    }

    @Override
    public void afterAnimationFinishedAction() {
        doAnimation = false;
        animationIndex = objectConstants.getAnimationSpriteAmount(objectType) - 1;
    }

}
