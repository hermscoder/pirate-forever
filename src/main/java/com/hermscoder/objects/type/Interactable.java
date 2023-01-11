package com.hermscoder.objects.type;

import com.hermscoder.entities.Player;
import com.hermscoder.objects.GameObject;
import com.hermscoder.objects.ObjectManager;

import java.awt.*;
import java.util.List;

public abstract class Interactable extends GameObject {

    protected Interactable(int x, int y, int objectType) {
        super(x, y, objectType);
    }

    public abstract List<Touchable> onInteract(ObjectManager player, Player player1);

    public abstract void update();

    public abstract void draw(Graphics g, int xLvlOffset);
}
