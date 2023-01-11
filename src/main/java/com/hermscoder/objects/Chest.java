package com.hermscoder.objects;

import com.hermscoder.main.Game;
import com.hermscoder.objects.type.Destroyable;
import com.hermscoder.objects.type.Interactable;
import com.hermscoder.objects.type.Touchable;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import static com.hermscoder.main.Game.SCALE;
import static com.hermscoder.main.Game.TILES_DEFAULT_SIZE;
import static com.hermscoder.utils.ObjectConstants.*;

public class Chest extends Interactable {
    public Chest(int x, int y, int objectType) {
        super(x, y, objectType);
    }

    @Override
    protected void initHitBox(int width, int height) {
        hitBox = new Rectangle2D.Float(x, y, width, height);
        hitBox.y += yDrawOffset + differenceBetweenSpriteAndTileSize();
        hitBox.x += xDrawOffset / 2;
    }

    @Override
    public List<Touchable> onInteract(ObjectManager objectManager) {
        doAnimation = true;
        List<Touchable> drops = new ArrayList<>();
        int type = BLUE_POTION;
        if (objectType == BARREL)
            type = RED_POTION;


        drops.add(new Potion(
                (int) (getHitBox().x + getHitBox().width / 2),
                (int) (getHitBox().y - getHitBox().height / 2), type));

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
                (int)((objectConstants.getSpriteAtlas().getTileWidth() - 1) * Game.SCALE),
                (int)((objectConstants.getSpriteAtlas().getTileHeight() - 1) * Game.SCALE), null);
//        drawHitBox(g, xLvlOffset);
    }
}
