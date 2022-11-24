package com.hermscoder.objects;

import com.hermscoder.main.Game;

public class Container extends GameObject{
    public Container(int x, int y, int objectType) {
        super(x, y, objectType);
    }

    public void update() {
        if(doAnimation)
            updateAnimationTick();
    }
}
