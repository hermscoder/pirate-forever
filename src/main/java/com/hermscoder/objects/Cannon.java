package com.hermscoder.objects;

import com.hermscoder.main.Game;

public class Cannon extends GameObject{
    public int tileY;

    public Cannon(int x, int y, int objectType) {
        super(x, y, objectType);
        //check the Y position that the cannon is using tiles.
        this.tileY = y / Game.TILES_SIZE;
    }

    public void update() {
        if(doAnimation)
            updateAnimationTick();
    }

    public int getTileY() {
        return tileY;
    }
}
