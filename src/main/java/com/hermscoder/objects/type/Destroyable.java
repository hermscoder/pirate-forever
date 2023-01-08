package com.hermscoder.objects.type;

import com.hermscoder.objects.GameObject;
import com.hermscoder.objects.ObjectManager;

import java.awt.*;
import java.util.List;

public abstract class Destroyable extends GameObject {

    protected Destroyable(int x, int y, int objectType) {
        super(x, y, objectType);
    }

    public abstract List<Touchable> onHit(ObjectManager player);

    public abstract void update();

    public abstract void draw(Graphics g, int xLvlOffset);
}
