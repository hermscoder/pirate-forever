package com.hermscoder.objects;

import com.hermscoder.entities.Player;

import java.awt.*;

public abstract class Touchable extends GameObject {

    protected Touchable(int x, int y, int objectType) {
        super(x, y, objectType);
    }

    abstract void onTouch(Player player);

    abstract void update();

    abstract void draw(Graphics g, int xLvlOffset);
}
