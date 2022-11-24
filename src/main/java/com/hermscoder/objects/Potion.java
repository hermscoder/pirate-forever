package com.hermscoder.objects;

import com.hermscoder.main.Game;

public class Potion extends GameObject {
    private float hoverOffset;
    private int maxHoverOffset, hoverDirection = 1;

    public Potion(int x, int y, int objectType) {
        super(x, y, objectType);

        maxHoverOffset = (int) (10 * Game.SCALE);
    }

    public void update() {
        updateAnimationTick();
        updateHover();
    }

    private void updateHover() {
        hoverOffset += (0.1f * Game.SCALE * hoverDirection);
        if(hoverOffset >= maxHoverOffset)
            hoverDirection = -1;
        else if(hoverOffset < 0)
            hoverDirection = 1;

        hitBox.y = y + hoverOffset;
    }
}
