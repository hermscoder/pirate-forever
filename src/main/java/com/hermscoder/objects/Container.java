package com.hermscoder.objects;

import com.hermscoder.main.Game;
import com.hermscoder.objects.type.Destroyable;
import com.hermscoder.objects.type.Touchable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.hermscoder.utils.ObjectConstants.*;

public class Container extends Destroyable {
    public Container(int x, int y, int objectType) {
        super(x, y, objectType);
    }

    @Override
    public List<Touchable> onHit(ObjectManager objectManager) {
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
        int type = objectType == BOX ? 0 : 1;
        g.drawImage(objectConstants.getAnimationImage(type, animationIndex),
                (int) (hitBox.x - xDrawOffset - xLvlOffset),
                (int) (hitBox.y - yDrawOffset),
                objectConstants.getSpriteAtlas().getTileWidth(Game.SCALE),
                objectConstants.getSpriteAtlas().getTileHeight(Game.SCALE), null);
//        drawHitBox(g, xLvlOffset);
    }
}
