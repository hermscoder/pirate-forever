package com.hermscoder.objects;

import com.hermscoder.main.Game;

public class Potion extends GameObject{
    public Potion(int x, int y, int objectType) {
        super(x, y, objectType);
    }

    public void update() {
        updateAnimationTick();
    }
}
