package com.hermscoder.objects;

import com.hermscoder.entities.Player;
import com.hermscoder.main.Game;
import com.hermscoder.objects.type.Touchable;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class MapFragment extends Touchable {
    public static final int STATIC_FRAGMENT_INDEX = 0;
    public static final int STATIC_FRAGMENT_MISSING_INDEX = 1;

    private float hoverOffset;
    private int maxHoverOffset, hoverDirection = 1;
    private final int fragmentNumber;
    public MapFragment(int x, int y, int objectType, int fragmentNumber) {
        super(x, y, objectType);
        this.fragmentNumber = fragmentNumber;
        maxHoverOffset = (int) (10 * Game.SCALE);
    }


    @Override
    public void update() {
        if (doAnimation)
            updateAnimationTick();
        updateHover();
    }
    @Override
    public void draw(Graphics g, int xLvlOffset) {
        g.drawImage(objectConstants.getAnimationImage(fragmentNumber - 1, STATIC_FRAGMENT_INDEX),
                (int) (hitBox.x - xDrawOffset - xLvlOffset),
                (int) (hitBox.y - yDrawOffset),
                objectConstants.getSpriteAtlas().getTileWidth(Game.SCALE),
                objectConstants.getSpriteAtlas().getTileHeight(Game.SCALE), null);
//        drawHitBox(g, xLvlOffset);
    }

    @Override
    protected void initHitBox(int width, int height) {
        hitBox = new Rectangle2D.Float(x, y, width, height);
    }

    @Override
    public void onTouch(Player player) {
        active = false;
        player.addMapFragmentToCollection(this);
    }

    private void updateHover() {
        hoverOffset += (0.1f * Game.SCALE * hoverDirection);
        if(hoverOffset >= maxHoverOffset)
            hoverDirection = -1;
        else if(hoverOffset < 0)
            hoverDirection = 1;

        hitBox.y = y + hoverOffset;
    }

    public BufferedImage getStaticImage() {
        return objectConstants.getAnimationImage(fragmentNumber - 1, STATIC_FRAGMENT_INDEX);
    }

    public int getFragmentNumber() {
        return fragmentNumber;
    }
}
