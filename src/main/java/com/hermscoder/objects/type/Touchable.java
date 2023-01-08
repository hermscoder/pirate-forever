package com.hermscoder.objects.type;

import com.hermscoder.entities.Player;
import com.hermscoder.objects.GameObject;

import java.awt.*;

public abstract class Touchable extends GameObject {

    protected Touchable(int x, int y, int objectType) {
        super(x, y, objectType);
    }

    public abstract void onTouch(Player player);

    public abstract void update();

    public abstract void draw(Graphics g, int xLvlOffset);
}
